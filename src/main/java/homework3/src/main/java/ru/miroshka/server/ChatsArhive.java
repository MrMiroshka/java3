package ru.miroshka.server;

import ru.miroshka.message.AbstractMessage;
import ru.miroshka.message.PrivateMessage;

import java.io.*;
import java.util.ArrayList;

public class ChatsArhive {
    private static final String MKDIR_NAME = "Arhives";

    public static void writeToFile(AbstractMessage message, byte choiceMessage, String fileName) {
        boolean firstMessage = false;
        final File file = new File(MKDIR_NAME, fileName);
        if (file.exists()&&file.length()==0) {
            firstMessage = true;
        }

        try  (FileOutputStream fOS = new FileOutputStream(MKDIR_NAME + File.separator + fileName, true)){
            if (!firstMessage) {
                try (AppendingObjectOutputStream objOut = new AppendingObjectOutputStream(fOS)) {
                    objOut.writeObject((PrivateMessage) message);
                }
            } else {
                try (ObjectOutputStream objOut = new ObjectOutputStream(fOS)) {
                    objOut.writeObject((PrivateMessage) message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<PrivateMessage> readFromFile(byte choiceMessage, String fileName) {
        ArrayList<PrivateMessage> pm = null;
        try (ObjectInputStream objIn = new ObjectInputStream(new
                FileInputStream(MKDIR_NAME + File.separator + fileName))) {
            pm = new ArrayList<>();
            if (choiceMessage == 2) {
                for (int i = 0; i < 100; i++) {
                    PrivateMessage pmTemp = (PrivateMessage) objIn.readObject();
                    if (pmTemp == null) {
                        break;
                    }
                    pm.add(pmTemp);
                }
                return pm;
            }
        } catch (EOFException e) {
            return pm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    public static void createMkDir(String name) {
        final File file = new File(name);
        if (!file.exists()) {
            file.mkdir();
        }
    }


    public static void createFile(String name) {
        final File file = new File(MKDIR_NAME, name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeMessageToFile(String nameFrom, String nameTo, AbstractMessage message, byte choiceMessage) {
        createMkDir(MKDIR_NAME);
        String nameFromFull = "history_" + nameFrom + ".txt";
        String nameToFull = "history_" + nameTo + ".txt";
        createFile(nameFromFull);
        createFile(nameToFull);
        writeToFile(message, choiceMessage, nameFromFull);
        if (nameFrom != nameTo) {
            writeToFile(message, choiceMessage, nameToFull);
        }
        //        ArrayList<PrivateMessage> pm = readFromFile((byte) 2, nameFromFull);
    }


    public static ArrayList<PrivateMessage> readMessageFromFile(String nameFrom, byte choiceMessage) {
        String nameFromFull = "history_" + nameFrom + ".txt";
        if (choiceMessage == 2) {
            return readFromFile(choiceMessage, nameFromFull);
        }
        return null;
    }
}
