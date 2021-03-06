package protocolBot;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.entity.player.Hand;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockFace;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientTabCompletePacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPlaceBlockPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientCraftingBookDataPacket;
import com.github.steveice10.packetlib.Session;
import lombok.SneakyThrows;

public class GameLoop implements Runnable {
    private Session session;
    private double speed = 0.28d;
    private int wait = 100;

    private double fallingSpeed = 0.08;

    public GameLoop(Session session) {
        this.session = session;
    }

    int block = 0;
    @SneakyThrows
    public void run() {
        //Position p = Data.player.getPositionOfPlayerBlock();
        //Data.map.getBlock(p.getX(), p.getY()-1, p.getZ());
        Thread.sleep(4000);
        System.out.println("start");
        while (true) {
            //Data.player.setFall(true);
            //jump();
            //jumping();
            //startFalling();
            //fall();
            //move();


            //session.send(new ClientPlayerPositionPacket(true, Data.player.getX(), Data.player.getY(), Data.player.getZ()));
            //System.out.println(Data.player.getX()+":"+ Data.player.getY()+":"+ Data.player.getZ());
           /* Position p1 = new Position(57999, 255, -45990);
            Position p2 = new Position(57998, 255, -45990);
            if(block == 0){
                session.send(new ClientPlayerChangeHeldItemPacket(0));
                session.send(new ClientPlayerPlaceBlockPacket(p1, BlockFace.WEST, Hand.MAIN_HAND, 0.5f, 0.5f, 0.5f));
                block = 1;
            }else if(block == 1){
                session.send(new ClientPlayerChangeHeldItemPacket(1));
                session.send(new ClientPlayerActionPacket(PlayerAction.START_DIGGING, p2, BlockFace.UP));
                block = 0;
            }*/
            //Position p = Data.player.getPositionOfPlayerBlock();
            //p = new Position(15703, 50, 632);
            //session.send(new ClientPlayerPlaceBlockPacket(p, BlockFace.UP, Hand.MAIN_HAND, 0.5f, 0.5f, 0.5f));

            //session.send(new ClientChatPacket("(\"value\":{\"text\":\"la\",\"color\":\"red\"})"));
            session.getPacketProtocol().registerOutgoing(0x0f, MyPacket.class);
            session.send(new MyPacket());
            //new ClientCraftingBookDataPacket();

            //session.send(new ClientTabCompletePacket("/is invite ", false));
            Thread.sleep(2000);
        }
    }

    private int jumpTime = 12;
    private int currentTime = 0;
    private void jumping(){
        if(currentTime == jumpTime){
            Data.player.setJump(false);
            currentTime=0;
            return;
        }
        if(Data.player.isJump()){
            Data.player.addToY(0.1667);
        }
        currentTime++;
    }

    private void jump() throws Exception {
        if(Data.player.isFall()){
            return;
        }

        Position p = Data.player.getPositionOfPlayerBlock();

        if(Data.map.getBlock(p.getX(), p.getY()-1, p.getZ()) != 0){
            if((double)p.getY() == Data.player.getY()){
                Data.player.setJump(true);
            }
        }
    }

    private void startFalling() throws Exception {
        if(Data.player.isJump()){
            return;
        }
        Position p = Data.player.getPositionOfPlayerBlock();
        if(Data.map.getBlock(p.getX(), p.getY()-1, p.getZ()) == 0){
            Data.player.setFall(true);
        }else if((double)p.getY() < Data.player.getY()){
            Data.player.setFall(true);
        }
    }

    private void fall() throws Exception {
        if(!Data.player.isFall()){
            return;
        }
        Position p = Data.player.getPositionOfPlayerBlock();
        fallingSpeed+=0.08;
      /*  if(Data.map.getBlock(p.getX(), p.getY(), p.getZ()) != 0){
            Data.player.setY(p.getY());
        }else */
        if(Data.map.getBlock(p.getX(), p.getY(), p.getZ()) == 0){
            Data.player.addToY(-fallingSpeed);
            if((double)p.getY() > Data.player.getY()){
                Data.player.setY(p.getY());
                if(Data.map.getBlock(p.getX(), p.getY()-1, p.getZ()) != 0){
                    Data.player.setY(p.getY());
                    Data.player.setFall(false);
                    fallingSpeed=0.08;
                }else if(Data.map.getBlock(p.getX(), p.getY()-1, p.getZ()) == 0){
                    Data.player.addToY(-fallingSpeed);
                }
            }
        }/*else if(Data.map.getBlock(p.getX(), p.getY()-1, p.getZ()) == 0){
            Data.player.addToY(-0.08);
        }*/
        //Bot.y -= 0.08;
       /* if(Data.player.isFall()){
            Bot.y += 0.08;
        }*/
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
        Data.player.addToZ(speed);
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
