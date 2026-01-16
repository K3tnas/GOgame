package pl.pwr.student.gogame.model.protocol;

import pl.pwr.student.gogame.model.utilities.GameInfo;

/**
 * Komunikacja klient-serwer
 */
public class ServerCommand {    
    public enum Type {
        SAY,                 // wypisz wiadomość od serwera
        UPDATE_GAMEINFO,     // przesłanie zserializowanego obiektu GameInfo
    }
    private Type type;
    private Byte[] gameInfo;
    private String message;
}
