package protocolBot;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.entity.player.Hand;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockFace;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.*;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.packetlib.Session;
import lombok.SneakyThrows;

import java.util.Random;

public class BotSender implements Runnable {
    private Session session;
    private float speed = 0.28f;
    private int wait = 100;

    public BotSender(Session session) {
        this.session = session;
    }

    @SneakyThrows
    public void run() {
        Thread.sleep(4000);
        while (true) {
            //move();

            //session.send(new ClientPlayerPositionPacket(true, Bot.x, Bot.y, Bot.z));
            session.send(new ClientPlayerPlaceBlockPacket(new Position(4140,39,15686), BlockFace.UP, Hand.MAIN_HAND, 0.5f, 0.5f, 0.5f));
            Thread.sleep(10);
        }
    }

    private void move(){
       /* if(wait == 0){
            if(Bot.x > 5){
                speed = -speed;
            }else if(Bot.x < -4) {
                speed = -speed;
            }
            wait = 80;
        }else {
            Bot.z += speed;
            wait--;
        }*/
        if(wait == 0){
            wait = 100;
            speed = -speed;
            System.out.println("reverse");
        }
        wait --;
        Bot.z += speed;
        //System.out.println(":"+Bot.z);
       /* wait--;
        if(Bot.y < 51){
            Bot.y += 0.1;
        }else if(Bot.y > 51){
            Bot.y = 51;
        }*/

    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
