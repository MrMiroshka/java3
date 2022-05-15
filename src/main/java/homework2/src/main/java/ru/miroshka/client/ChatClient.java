package ru.miroshka.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import ru.miroshka.message.*;

public class ChatClient {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String nick;

    private final Controller controller;

    public ChatClient(Controller controller) {
        this.controller = controller;
    }

    public void openConnection() throws Exception {
        socket = new Socket("localhost", 8386);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        final Thread readThread = new Thread(() -> {
            try {
                waitAuthenticate();
                readMessage();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                closeConnection();
            }
        });
        readThread.setDaemon(true);
        readThread.start();
    }

    private void readMessage() throws Exception {
        while (true) {
            final AbstractMessage message = (AbstractMessage) in.readObject();
            System.out.println("Receive message: " + message);
            if (message.getCommand() == Command.END) {
                controller.setAuth(false);
                break;
            }
            if (message.getCommand() == Command.ERROR) {
                final ErrorMessage errorMessage = (ErrorMessage) message;
                Platform.runLater(() -> controller.showError(errorMessage.getError()));
            } else if (message.getCommand() == Command.CLIENTS) {
                final ClientListMessage clientListMessage = (ClientListMessage) message;
                //this.nick = clientListMessage.getClients();
                Service service = new Service() {
                    @Override
                    protected Task createTask() {
                        return new Task() {
                            @Override
                            protected Object call() throws Exception {
                                Platform.runLater(() -> {
                                    controller.updateClientList(clientListMessage.getClients());
                                });
                                return null;
                            }
                        };
                    }
                };
                service.start();


            } else if (message.getCommand() == Command.MESSAGE) {
                final SimpleMessage simpleMessage = (SimpleMessage) message;
                controller.addMessage(simpleMessage.getNickFrom() + ": " + simpleMessage.getMessage());
            } else if (message.getCommand() == Command.CHANGENICK) {
                final ChangeNick changeNick = (ChangeNick) message;
                if (!this.nick.equals(changeNick.getNickNew())) {
                    this.nick = changeNick.getNickNew();
                }
                else {
                    controller.addMessage(changeNick.getNick() + ": " + changeNick.getMessage());}
                }
            }
        }

        private void waitAuthenticate () throws IOException, ClassNotFoundException {
            while (true) {
                final AbstractMessage message = (AbstractMessage) in.readObject();
                if (message.getCommand() == Command.AUTHOK) {
                    this.nick = ((AuthOkMessage) message).getNick();
                    controller.addMessage("Успешная авторизация под ником " + nick);
                    controller.setAuth(true);
                    break;
                }
                if (message.getCommand() == Command.ERROR) {
                    final ErrorMessage errorMessage = (ErrorMessage) message;
                    Platform.runLater(() -> controller.showError(errorMessage.getError()));
                }
            }
        }

        private void closeConnection () {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.exit(0);
        }

        public void sendMessage (AbstractMessage message){
            try {
                System.out.println("Send message: " + message);
                out.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getNick () {
            return nick;
        }
    }
