package ru.miroshka.server;

import ru.miroshka.message.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

public class ChatServer {

    private final Map<String, ClientHandler> clients;
    private final ExecutorService service;

    public ExecutorService getService() {
        return service;
    }


    public ChatServer() {
        this.clients = new HashMap<>();
        this.service = Executors.newCachedThreadPool();
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(8386);
             AuthService authService = new SQLiteAuthService()) {
            boolean asd = false;
            while (true) {
                System.out.println("Wait client connection...");
                final Socket socket = serverSocket.accept();
                new ClientHandler(socket, this, authService, 120000);
                System.out.println("Client connected");
                if (asd){break;}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Runnable> runnables = service.shutdownNow();


    }

    public boolean isNickBusy(String nick) {
        return clients.containsKey(nick);
    }

    public void subscribe(ClientHandler client) {
        clients.put(client.getNick(), client);
        broadcastClientList();
    }

    public void changeClient(ClientHandler client, String oldNick) {
        clients.remove(oldNick);
        clients.put(client.getNick(), client);
        broadcastClientList();
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client.getNick());
        broadcastClientList();
    }

    private void broadcastClientList() {
        final List<String> nicks = clients.values().stream()
                .map(ClientHandler::getNick)
                .collect(Collectors.toList());
        broadcast(ClientListMessage.of(nicks));
    }

    public void broadcast(AbstractMessage message) {
        for (ClientHandler client : clients.values()) {
            client.sendMessage(message);
        }
    }

    public void sendMessageToClient(ClientHandler sender, String to, String message) {
        final ClientHandler receiver = clients.get(to);
        if (receiver != null) {
            if (sender != null) {
                //receiver.sendMessage(SimpleMessage.of("от " + sender.getNick() + ": " + message, sender.getNick()));
                sender.sendMessage(SimpleMessage.of("участнику " + to + ": " + message, sender.getNick()));
                receiver.sendMessage(SimpleMessage.of(" " + message, sender.getNick()));
            } else {
                receiver.sendMessage(ChangeNick.of(receiver.getNick(), receiver.getNick(), message));
            }
        } else {
            sender.sendMessage(ErrorMessage.of("Участника с ником " + to + " нет в чате!"));
        }
    }

    public void sendMessageToClientArhive(String from, String to, String message,String nickFromArhive) {
        final ClientHandler receiver = clients.get(to);
        final ClientHandler sender = clients.get(from);
        if (sender != null && sender.getNick()==nickFromArhive) {
            sender.sendMessage(SimpleMessage.of("участнику " + to + ": " + message, sender.getNick()));
        }
        if (receiver != null && receiver.getNick()==nickFromArhive ) {
            receiver.sendMessage(SimpleMessage.of(" " + message, from));
        }
    }

}
