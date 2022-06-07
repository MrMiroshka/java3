package ru.miroshka.server;

import ru.miroshka.message.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ChatServer {

    private final Map<String, ClientHandler> clients;
    private final ExecutorService service;
    private final LinkedList<Thread> listActiveThreads;
    private static final Logger LOGGER = LogManager.getLogger("FirstLogger");

    public Logger getLogger() {
        return LOGGER;
    }

    public ExecutorService getService() {
        return service;
    }

    public LinkedList<Thread> getListActiveThreads() {
        return listActiveThreads;
    }

    public ChatServer() {
        this.clients = new HashMap<>();
        this.service = Executors.newCachedThreadPool();
        this.listActiveThreads = new LinkedList<>();
    }

    public void run() {
        LOGGER.info("Сервер запущен");
        try (ServerSocket serverSocket = new ServerSocket(8386);
             AuthService authService = new SQLiteAuthService()) {
            boolean asd = false;
            while (true) {
                System.out.println("Wait client connection...");
                final Socket socket = serverSocket.accept();
                ClientHandler cH = new ClientHandler(socket, this, authService, 120000);
                System.out.println("Client connected");
                //{break;} - для воссозданияошибки- когда основной поток завершился, а другие еще нет
            }
        } catch (IOException e) {
            LOGGER.error("Произошла ошибка - {}", () -> e.getMessage());
            e.printStackTrace();
        }

        //помечаю все активные потоки - на завершение. Везде где sleep,wait - потоки завершаются сразу (блок try/catch)
        //но там где бесконечный цикл, нужно дождаться новой итерации.
        for (Thread thread : this.getListActiveThreads()) {
            thread.interrupt();
        }
        service.shutdownNow();
        //System.out.println(this.getListThreads().size());

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
                LOGGER.info("Клиент ({}) отправил сообщение {}", () -> sender.getNick(), () -> to);
                receiver.sendMessage(SimpleMessage.of(" " + message, sender.getNick()));
            } else {
                receiver.sendMessage(ChangeNick.of(receiver.getNick(), receiver.getNick(), message));
            }
        } else {
            sender.sendMessage(ErrorMessage.of("Участника с ником " + to + " нет в чате!"));
        }
    }

    public void sendMessageToClientArhive(String from, String to, String message, String nickFromArhive) {
        final ClientHandler receiver = clients.get(to);
        final ClientHandler sender = clients.get(from);
        if (sender != null && sender.getNick() == nickFromArhive) {
            sender.sendMessage(SimpleMessage.of("участнику " + to + ": " + message, sender.getNick()));
        }
        if (receiver != null && receiver.getNick() == nickFromArhive) {
            receiver.sendMessage(SimpleMessage.of(" " + message, from));
        }
    }

}
