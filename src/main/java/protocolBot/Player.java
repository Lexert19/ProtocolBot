package protocolBot;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;

public class Player {
    private double x;
    private double y;
    private double z;

    private boolean jump;
    private boolean fall;


    public Position getPositionOfPlayerBlock(){
        int x;
        int z;
        if(this.x < 0){
            x = (int)-Math.ceil(Math.abs(this.x));
        }else {
            x = (int) Math.ceil(this.x);
        }
        if(this.z < 0){
            z = (int)-Math.ceil(Math.abs(this.z));
        }else {
            z = (int) Math.floor(this.z);
        }
        int y = (int) Math.floor(this.y);
        Position position = new Position(x,y,z);
        return position;
    }

    public void addToX(double value){
        x += value;
    }

    public void addToY(double value){
        y += value;
    }

    public void addToZ(double value){
        z += value;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }


    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public boolean isFall() {
        return fall;
    }

    public void setFall(boolean fall) {
        this.fall = fall;
    }
}
