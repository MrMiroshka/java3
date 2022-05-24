package ru.miroshka.server;

import java.io.IOException;
import java.sql.*;

public class SQLiteAuthService implements AuthService{
    private static final String URL_TO_BD = "jdbc:sqlite:chats.db";
    private Connection connection;

    public SQLiteAuthService(){
        run();
    }


    @Override
    public String getNickByLoginAndPassword(String login, String password) {
        try (final PreparedStatement statement = connection.prepareStatement("SELECT nick FROM clients WHERE login = ? AND password = ?")) {
            statement.setString(1, login);
            statement.setString(2, password);
            final ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getString("nick");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean changeNick(String nick,String newNick) {
        try (final PreparedStatement statement = connection.prepareStatement("UPDATE clients SET nick = ? WHERE nick = ? ")) {
            statement.setString(1, newNick);
            statement.setString(2, nick);
            int result = statement.executeUpdate();
            if (result !=0) {
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void run() {
        try {
            connection = DriverManager.getConnection(URL_TO_BD);
        } catch (SQLException exp) {
            throw new RuntimeException("Ошибка подключения к БД", exp);
        }
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException exp) {
            throw new RuntimeException("Ошибка при закрытии соединения с БД", exp);
        }
    }
}
