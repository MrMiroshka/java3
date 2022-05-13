package ru.miroshka.message;

public class ChangeNick extends AbstractMessage {
    private final String nick;
    private final String newNick;
    private final String message;

    public ChangeNick(String nick, String newNick, String message) {
        super(Command.CHANGENICK);
        this.nick = nick;
        this.newNick = newNick;
        this.message = message;
    }

    public String getNick() {
        return nick;
    }

    public String getNickNew() {
        return newNick;
    }

    public String getMessage() {
        return message;
    }

    public static ChangeNick of(String nickFrom, String nickNew, String message) {
        return new ChangeNick(nickFrom, nickNew, message);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BroadcastMessage{");
        sb.append("timestamp=").append(getTimestamp());
        sb.append(", Смена nick='").append(this.newNick).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
