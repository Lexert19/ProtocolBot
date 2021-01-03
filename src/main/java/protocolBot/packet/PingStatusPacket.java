package protocolBot.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PingStatusPacket {
    public static byte[] create() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataOutputStream packet = new DataOutputStream(buffer);
        packet.writeByte(0x01);
        packet.writeByte(0x00);
        return buffer.toByteArray();
    }
}
