package protocolBot.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class HandshakePacket {
    public static byte [] create(String host, int port) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        DataOutputStream handshake = new DataOutputStream(buffer);
        handshake.writeByte(0x00); //packet id for handshake
        Packet.writeVarInt(handshake, 340); //protocol version
        Packet.writeString(handshake, host);
        handshake.writeShort(port); //port
        Packet.writeVarInt(handshake, 2); //state (1 for handshake)

        return buffer.toByteArray();
    }
}
