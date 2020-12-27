package protocolBot;

import com.github.steveice10.mc.auth.exception.request.RequestException;
import com.github.steveice10.mc.auth.service.AuthenticationService;
import com.github.steveice10.mc.auth.service.SessionService;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.SubProtocol;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.entity.player.Hand;
import com.github.steveice10.mc.protocol.data.game.entity.player.PlayerAction;
import com.github.steveice10.mc.protocol.data.game.setting.ChatVisibility;
import com.github.steveice10.mc.protocol.data.game.setting.SkinPart;
import com.github.steveice10.mc.protocol.data.game.window.WindowAction;
import com.github.steveice10.mc.protocol.data.game.window.WindowActionParam;
import com.github.steveice10.mc.protocol.data.game.window.WindowType;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockFace;
import com.github.steveice10.mc.protocol.data.message.Message;
import com.github.steveice10.mc.protocol.data.status.ServerStatusInfo;
import com.github.steveice10.mc.protocol.data.status.handler.ServerInfoHandler;
import com.github.steveice10.mc.protocol.data.status.handler.ServerPingTimeHandler;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientPluginMessagePacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientSettingsPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientTabCompletePacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.*;
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientConfirmTransactionPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientWindowActionPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.world.ClientTeleportConfirmPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityHeadLookPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerConfirmTransactionPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerChunkDataPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerSpawnPositionPacket;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import com.github.steveice10.packetlib.packet.Packet;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import static com.github.steveice10.mc.protocol.MinecraftConstants.SERVER_INFO_HANDLER_KEY;
import static com.github.steveice10.mc.protocol.data.SubProtocol.STATUS;
import static java.util.concurrent.TimeUnit.SECONDS;

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


    private Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("217.173.202.37",42617));

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
        proxy = Proxy.NO_PROXY;
        MinecraftProtocol protocol;
        protocol = new MinecraftProtocol("AniaaX__9");
        client = new Client("nssv.pl", 25565, protocol, new TcpSessionFactory(proxy));
        client.getSession().addListener((new SessionAdapter() {
            @Override
            public void packetReceived(PacketReceivedEvent event) {
                //System.out.println(event.getPacket().getClass());
                if (event.getPacket() instanceof ServerJoinGamePacket) {
                    client.getSession().send(new ClientSettingsPacket("en_EB",2, ChatVisibility.FULL,true, new SkinPart[]{SkinPart.CAPE}, Hand.MAIN_HAND));
                } else if (event.getPacket() instanceof ServerSpawnPositionPacket) {
                    ServerSpawnPositionPacket p = event.getPacket();
                    System.out.println(p);

                    startScript();


                }else if (event.getPacket() instanceof ServerPlayerPositionRotationPacket) {
                    ServerPlayerPositionRotationPacket p = event.getPacket();
                    event.getSession().send(new ClientTeleportConfirmPacket(p.getTeleportId()));
                    x = p.getX();
                    y = p.getY();
                    z = p.getZ();

                } else if (event.getPacket() instanceof ServerChatPacket) {
                    String message = event.<ServerChatPacket>getPacket().getMessage().getFullText();
                    if(message.contains("tpaccept")){
                        client.getSession().send(new ClientChatPacket("/tpaccept"));
                    }
                    System.out.println(">> " + message);

                } else if (event.getPacket() instanceof ServerChunkDataPacket) {
                    //System.out.println(event.getPacket());
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

    private boolean started = false;
    public void startScript(){
        if(started){
            return;
        }
        started = true;

        final BotSender[] botSender = new BotSender[1];
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

                botSender[0] = new BotSender(client.getSession());
                Thread thread = new Thread(botSender[0]);
                thread.start();

            }
        }.start();
    }
}
