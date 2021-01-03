package protocolBot.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class StartLoginPacket {
    public static byte[] create(String username) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataOutputStream usernamePacket = new DataOutputStream(buffer);
        usernamePacket.writeByte(0x00);
        Packet.writeString(usernamePacket, username);
        return buffer.toByteArray();
    }
}
