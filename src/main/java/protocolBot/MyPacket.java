package protocolBot;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class MyPacket implements Packet {
    public void read(NetInput in) throws IOException {

    }

    public void write(NetOutput out) throws IOException {
        char[] chars = new char[16];
        byte[] bytes = new byte[12];
       /* for(int i=0; i<)
        out.writeString();*/
       /* StringBuilder msg = new StringBuilder("");
        for(int i=0; i<10000; i++){
            msg.append("elo+");
        }
        out.writeString("ciekawe so sie stanie? ELOELOELOELOELOELOELOELOELO");*/
    }

    public boolean isPriority() {
        return false;
    }
}
