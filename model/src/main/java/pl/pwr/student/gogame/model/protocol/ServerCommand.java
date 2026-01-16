package pl.pwr.student.gogame.model.protocol;

/**
 * Komunikacja klient-serwer
 */
public class ServerCommand {    
    public enum Type {
        SAY,                 // wypisz wiadomość od serwera
        UPDATE_GAMEINFO,     // przesłanie zserializowanego obiektu GameInfo
    }
}
