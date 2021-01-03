package protocolBot.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TeleportConfirmPacket {
    public static byte[] create(int teleportId) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataOutputStream packet = new DataOutputStream(buffer);
        packet.writeByte(0x00);
        Packet.writeVarInt(packet, teleportId);
        return buffer.toByteArray();
    }
}
