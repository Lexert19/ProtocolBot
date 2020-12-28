package protocolBot;

import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.game.entity.player.Hand;
import com.github.steveice10.mc.protocol.data.game.setting.ChatVisibility;
import com.github.steveice10.mc.protocol.data.game.setting.SkinPart;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientSettingsPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientConfirmTransactionPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.world.ClientTeleportConfirmPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerConfirmTransactionPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerChunkDataPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerSpawnPositionPacket;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;

import java.net.Proxy;

public class Bot {
    private static final boolean SPAWN_SERVER = true;
    private static final boolean VERIFY_USERS = false;
    private static final String HOST = "192.168.0.121";
    private static final String USERNAME = "lagger123";
    private static final int PORT = 25565;
    private static final Proxy AUTH_PROXY = Proxy.NO_PROXY;

    public static double x;
    public static double y;
    public static double z;

    //private Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("217.173.202.37",42617));
    private Proxy proxy = Proxy.NO_PROXY;

    private Client client;

    public static void main(String[] args) throws InterruptedException {
        new Thread() {
            @Override
            public void run() {
                Bot bot = new Bot();
                bot.tryLogin();
            }
        }.start();
    }

    public void tryLogin() {
        MinecraftProtocol protocol;
        protocol = new MinecraftProtocol("lagger124");

        client = new Client("192.168.56.104", 25565, protocol, new TcpSessionFactory(proxy));
        client.getSession().addListener((new SessionAdapter() {
            @Override
            public void packetReceived(PacketReceivedEvent event) {

                if (event.getPacket() instanceof ServerJoinGamePacket) {
                    client.getSession().send(new ClientSettingsPacket("en_EB",2, ChatVisibility.FULL,true, new SkinPart[]{SkinPart.CAPE}, Hand.MAIN_HAND));

                } else if (event.getPacket() instanceof ServerSpawnPositionPacket) {
                    ServerSpawnPositionPacket p = event.getPacket();

                    //startScript();
                    startScriptMyServer();


                }else if (event.getPacket() instanceof ServerPlayerPositionRotationPacket) {
                    ServerPlayerPositionRotationPacket p = event.getPacket();
                    event.getSession().send(new ClientTeleportConfirmPacket(p.getTeleportId()));
                    Data.player.setX(p.getX());
                    Data.player.setY(p.getY());
                    Data.player.setZ(p.getZ());


                } else if (event.getPacket() instanceof ServerChatPacket) {
                    Methods.tpacceptAndPrintChat(client.getSession(),((ServerChatPacket) event.getPacket()).getMessage());

                } else if (event.getPacket() instanceof ServerChunkDataPacket) {
                    Data.map.addColumn(((ServerChunkDataPacket) event.getPacket()).getColumn());

                } else if (event.getPacket() instanceof ServerConfirmTransactionPacket) {
                    final ServerConfirmTransactionPacket p = event.getPacket();
                    client.getSession().send(new ClientConfirmTransactionPacket(p.getWindowId(),
                            p.getActionId(), p.getAccepted()));

                } else if (event.getPacket() instanceof ServerConfirmTransactionPacket) {
                    final ServerConfirmTransactionPacket p = event.getPacket();
                    client.getSession().send(new ClientConfirmTransactionPacket(p.getWindowId(),
                            p.getActionId(), p.getAccepted()));

                }
            }

            @Override
            public void disconnected(DisconnectedEvent event) {
                System.out.println("Disconnected: " + event.getReason());
                System.out.println(event.getCause());
                System.out.println();
                if (event.getCause() != null) {
                    event.getCause().printStackTrace();
                }
                System.exit(0);
            }
        }));
        client.getSession().connect(true);


    }

    private boolean startedMyServer = false;
    public void startScriptMyServer(){
        if(startedMyServer){
            return;
        }
        startedMyServer = true;
        final GameLoop[] botSender = new GameLoop[1];
        new Thread() {
            public void run() {
                botSender[0] = new GameLoop(client.getSession());
                Thread thread = new Thread(botSender[0]);
                thread.start();

            }
        }.start();
    }

    private boolean started = false;
    public void startScript(){
        if(started){
            return;
        }
        started = true;

        final GameLoop[] botSender = new GameLoop[1];
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Methods.clickItem(client.getSession(), 38);
                client.getSession().send(new ClientChatPacket("/l chuj123"));
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //client.getSession().send(new ClientPlayerChangeHeldItemPacket(0));
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //client.getSession().send(new ClientPlayerUseItemPacket(Hand.MAIN_HAND));
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Methods.clickItem(client.getSession(), 38);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //client.getSession().send(new ClientChatPacket("/warp kopalnia"));

                botSender[0] = new GameLoop(client.getSession());
                Thread thread = new Thread(botSender[0]);
                thread.start();

            }
        }.start();
    }
}
