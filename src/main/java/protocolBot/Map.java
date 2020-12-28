package protocolBot;

import com.github.steveice10.mc.protocol.data.game.chunk.BlockStorage;
import com.github.steveice10.mc.protocol.data.game.chunk.Chunk;
import com.github.steveice10.mc.protocol.data.game.chunk.Column;
import com.github.steveice10.mc.protocol.data.game.chunk.FlexibleStorage;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockState;

import javax.swing.text.Position;
import java.util.LinkedList;

public class Map {
    private LinkedList<Column> columns = new LinkedList<Column>();

    public void addColumn(Column column){
        columns.addLast(column);
    }

    public Column getColumn(int x, int z){
        for(Column column : columns){
            if(column.getX() == x && column.getZ() == z){
                return column;
            }
        }
        return null;
    }

    public int getBlock(int x, int y, int z) throws Exception {
        if(y < 0 || y > 256){
            throw new Exception("y < 0 && y > 256");
        }

        //System.out.println();
        int cX = (int) Math.floor((double)x/16);
        int cZ = (int) Math.floor((double)z/16);
        int cY = (int) Math.floor((double)y/16);
        Column column = getColumn(cX, cZ);
        Chunk[] chunks = column.getChunks();

        //System.out.println(cX+":"+cY+":"+cZ);
        int bX = x%16; if(bX < 0) bX+=16;
        int bY = y%16;
        int bZ = z%16; if(bZ < 0) bZ+=16;
        Chunk chunk = chunks[cY];
        //System.out.println(bX+":"+bY+":"+bZ);
        BlockState block = chunk.getBlocks().get(bX,bY,bZ);
        //System.out.println(block.getId());
        return block.getId();
    }
}
