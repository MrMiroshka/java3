package ru.miroshka.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import ru.miroshka.message.*;

public class ClientHandler {
    private final Socket socket;
    private final ChatServer server;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final AuthService authService;
    private boolean isClose;
    private final int timeAuthLimit;

    private String nick;

    public ClientHandler(Socket socket, ChatServer server, AuthService authService, int timeAuthLimit) {
        try {
            this.nick = "";
            this.socket = socket;
            this.server = server;
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.authService = authService;
            this.isClose = false;
            this.timeAuthLimit = timeAuthLimit;


            server.getService().execute(() -> {
                server.getListActiveThreads().add(Thread.currentThread());
                server.getService().execute(() -> {
                    server.getListActiveThreads().add(Thread.currentThread());
                    try {
                        Thread.sleep(timeAuthLimit);
                        if ("".equals(ClientHandler.this.nick)) {
                            System.out.println("Вышло время для авторизации, соединение с клиентом будет прервано.");
                            closeConnection();

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        server.getListActiveThreads().remove(Thread.currentThread());
                        //System.out.println("Завершился - " + Thread.currentThread().getName());
                    }
                });
                authenticate();
                server.getLogger().info("Клиент ({}) подключился", () -> this.getNick());
                readMessages();
                server.getListActiveThreads().remove(Thread.currentThread());
                //System.out.println("Завершился - " + Thread.currentThread().getName());
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void closeConnection() {

        if (!isClose) {
            isClose = true;
            sendMessage(EndMessage.of());
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (socket != null) {
                    server.unsubscribe(this);
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void authenticate() {
        while (true && !Thread.currentThread().isInterrupted()) {
            try {
                final AbstractMessage message = (AbstractMessage) in.readObject();
                if (message.getCommand() == Command.AUTH) {
                    final AuthMessage authMessage = (AuthMessage) message;
                    final String login = authMessage.getLogin();
                    final String password = authMessage.getPassword();
                    final String nick = authService.getNickByLoginAndPassword(login, password);
                    if (nick != null) {
                        if (server.isNickBusy(nick)) {
                            sendMessage(ErrorMessage.of("Пользователь уже авторизован"));
                            continue;
                        }
                        sendMessage(AuthOkMessage.of(nick));
                        this.nick = nick;
                        server.subscribe(this);
                        LinkedList<PrivateMessage> pm = ChatsArhive.readMessageFromFile(this.nick, (byte) 2);
                        if (pm != null) {
                            for (PrivateMessage privateMessage : pm) {
                                server.sendMessageToClientArhive(privateMessage.getNickFrom(), privateMessage.getNickTo(), privateMessage.getMessage(), this.nick);
                            }
                        }

                        server.broadcast(SimpleMessage.of(nick, "Пользователь " + nick + " зашел в чат"));
                        break;
                    } else {
                        sendMessage(ErrorMessage.of("Неверные логин и пароль"));
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void sendMessage(AbstractMessage message) {
        try {
            System.out.println("SERVER: Send message: " + message);
            out.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readMessages() {
        try {
            while (true && !Thread.currentThread().isInterrupted()) {
                final AbstractMessage message = (AbstractMessage) in.readObject();
                System.out.println("Receive message: " + message);
                if (message.getCommand() == Command.END) {
                    break;
                }
                if (message.getCommand() == Command.CHANGENICK) {
                    final ChangeNick changeNick = (ChangeNick) message;
                    String tempNick = this.nick;

                    if (authService.changeNick(changeNick.getNick(), changeNick.getNickNew())) {
                        this.nick = changeNick.getNickNew();
                        this.server.changeClient(this, changeNick.getNick());
                        server.broadcast(SimpleMessage.of("Ник - " + changeNick.getNick() + " сменился успешно на - " + changeNick.getNickNew() + " !", changeNick.getNickNew()));
                        server.sendMessageToClient(null, changeNick.getNickNew(), "Ник - " + changeNick.getNick() + " сменился успешно на - " + changeNick.getNickNew() + " !");
                        server.getLogger().info("Клиент ({}) сменил ник на - '{}'", () -> tempNick, () -> this.nick);
                    } else {
                        server.sendMessageToClient(null, changeNick.getNick(), "Смена ника завершилась не удачно (" + changeNick.getNick() + " на " + changeNick.getNickNew() + ")!");
                        server.getLogger().warn("У Клиент ({}) смена ника на - '{}', прошла не удачно!", () -> tempNick, () -> this.nick);
                    }
                }
                if (message.getCommand() == Command.MESSAGE) {
                    final SimpleMessage simpleMessage = (SimpleMessage) message;
                    server.broadcast(simpleMessage);
                    server.getLogger().info("Клиент ({}) отправил всем сообщение - '{}'", () -> this.nick, () -> simpleMessage.getMessage());
                }
                if (message.getCommand() == Command.PRIVATE_MESSAGE) {
                    final PrivateMessage privateMessage = (PrivateMessage) message;
                    server.sendMessageToClient(this, privateMessage.getNickTo(), privateMessage.getMessage());
                    ChatsArhive.writeMessageToFile(privateMessage.getNickFrom(), privateMessage.getNickTo(), (AbstractMessage) privateMessage, (byte) 2);
                    server.getLogger().info("Клиент ({}) отправил  сообщение  клиенту - '{}' --- сообщение: '{}'", () -> this.nick, () -> privateMessage.getNickTo(), () -> privateMessage.getMessage());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNick() {
        return nick;
    }
}
