package protocolBot;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.mc.protocol.data.game.window.ClickItemParam;
import com.github.steveice10.mc.protocol.data.game.window.WindowAction;
import com.github.steveice10.mc.protocol.data.game.window.WindowActionParam;
import com.github.steveice10.mc.protocol.data.message.Message;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientWindowActionPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.packetlib.Session;

public class Methods {
    public static void dropAll(Session session){
        int actionId = 0;
        int windowId = 0; // The ID of the window which was clicked. 0 for player inventory.
        WindowActionParam param = ClickItemParam.LEFT_CLICK;
        WindowAction action = WindowAction.DROP_ITEM;
        ItemStack clicked = new ItemStack(0, 64); // The clicked slot. Has to be empty (item ID = -1) for drop mode.
        for(int slot=0; slot<45; slot++){
            session.send(new ClientWindowActionPacket(windowId, actionId++, slot, clicked, action, param));
        }
    }
    public static void clickItem(Session session, int slot){
        int actionId = 0;
        int windowId = 1; // The ID of the window which was clicked. 0 for player inventory.
        WindowActionParam param = ClickItemParam.LEFT_CLICK;
        WindowAction action = WindowAction.CLICK_ITEM;
        ItemStack clicked = null; // The clicked slot. Has to be empty (item ID = -1) for drop mode.
        session.send(new ClientWindowActionPacket(windowId, actionId++, slot, clicked, action, param));

    }

    public static void tpacceptAndPrintChat(Session session, Message message){
        String text = message.getFullText();
        if(text.contains("tpaccept")){
            session.send(new ClientChatPacket("/tpaccept"));
        }
        System.out.println("> " + text);
    }
}
