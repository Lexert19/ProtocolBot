package protocolBot.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PingPacket {
    public static byte[] create() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(1460);

        DataOutputStream packet = new DataOutputStream(buffer);

        for(int i=0; i<1; i++){
            //Packet.writeVarInt(packet, 1);
            packet.writeByte(0x01);
            packet.writeByte(0x00);
            //packet.writeByte(0xFE);
        }
        //packet.writeByte(0x01);
        return buffer.toByteArray();
    }
}
