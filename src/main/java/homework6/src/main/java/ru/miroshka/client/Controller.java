package ru.miroshka.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import ru.miroshka.message.AuthMessage;
import ru.miroshka.message.ChangeNick;
import ru.miroshka.message.PrivateMessage;
import ru.miroshka.message.SimpleMessage;

import java.util.Collection;
import java.util.Optional;

public class Controller {

    @FXML
    private ListView<String> clientList;
    @FXML
    private HBox messageBox;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField loginField;
    @FXML
    private HBox loginBox;
    @FXML
    private TextField textField;
    @FXML
    private TextArea textArea;

    private final ChatClient client;

    private String nickTo;

    public Controller() {
        client = new ChatClient(this);
        while (true) {
            try {
                client.openConnection();
                break;
            } catch (Exception e) {
                showNotification();
            }
        }
    }

    public void btnSendClick(ActionEvent event) {
        final String message = textField.getText().trim();
        if (message.isEmpty()) {
            return;
        }
        if (nickTo != null) {
            client.sendMessage(PrivateMessage.of(nickTo, client.getNick(), message));
            nickTo = null;
        } else {
            String[] tempMessage = message.split(" ", 2);
            if ("\\chnick".equals(tempMessage[0]) && tempMessage.length == 2 && tempMessage[1].length() < 20) {
                client.sendMessage(ChangeNick.of(client.getNick(), tempMessage[1],""));
            } else {
                client.sendMessage(SimpleMessage.of(message, client.getNick()));
            }
        }
        textField.clear();
        textField.requestFocus();
    }

    public void addMessage(String message) {
        textArea.appendText(message + "\n");
    }

    public void btnAuthClick(ActionEvent actionEvent) {
        client.sendMessage(AuthMessage.of(loginField.getText(), passwordField.getText()));
    }

    public void setAuth(boolean success) {
        loginBox.setVisible(!success);
        messageBox.setVisible(success);
    }

    private void showNotification() {
        final Alert alert = new Alert(Alert.AlertType.ERROR,
                "Не могу подключится к серверу.\n" +
                        "Проверьте, что сервер запущен",
                new ButtonType("Попробовать еще", ButtonBar.ButtonData.OK_DONE),
                new ButtonType("Выйти", ButtonBar.ButtonData.CANCEL_CLOSE));
        alert.setTitle("Ошибка подключения");
        final Optional<ButtonType> buttonType = alert.showAndWait();
        final Boolean isExit = buttonType.map(btn -> btn.getButtonData().isCancelButton()).orElse(false);
        if (isExit) {
            System.exit(0);
        }
    }

    public void showError(String error) {
        final Alert alert = new Alert(Alert.AlertType.ERROR, error, new ButtonType("OK", ButtonBar.ButtonData.OK_DONE));
        alert.setTitle("Ошибка!");
        alert.showAndWait();
    }

    public void selectClient(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            final String message = textField.getText();
            final String nick = clientList.getSelectionModel().getSelectedItem();
            if (nick != null) {
                this.nickTo = nick;
            }
            textField.requestFocus();
            textField.selectEnd();
        }
    }

    public void updateClientList(Collection<String> clients) {
        clientList.getItems().clear();
        clientList.getItems().addAll(clients);
    }
}
