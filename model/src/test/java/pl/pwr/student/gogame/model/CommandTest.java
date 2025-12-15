package pl.pwr.student.gogame.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pl.pwr.student.gogame.model.commands.CMDPass;
import pl.pwr.student.gogame.model.commands.CMDPut;
import pl.pwr.student.gogame.model.commands.ClientCommand;
import pl.pwr.student.gogame.model.exceptions.MangledMessageException;

public class CommandTest {
    @Test
    public void serializeDeserializePass() {
        String serializedPass = "PASS,3";
        CMDPass cmd;

        try {
            cmd = (CMDPass) ClientCommand.fromString(serializedPass);
        } catch (MangledMessageException e) {
            assertEquals("Nie powinno być błędu przy deserializacji CMDPass", "a jednak jest: " + e.getMessage());
            return;
        }

        assertEquals(serializedPass, cmd.toString());
    }

    @Test
    public void serializeDeserializePut() {
        String serializedPut = "PUT,3,1,4";
        CMDPut cmd;

        try {
            cmd = (CMDPut) ClientCommand.fromString(serializedPut);
        } catch (MangledMessageException e) {
            assertEquals("Nie powinno być błędu przy deserializacji CMDPut", "a jednak jest: " + e.getMessage());
            return;
        }

        assertEquals(serializedPut, cmd.toString());
    }
}
