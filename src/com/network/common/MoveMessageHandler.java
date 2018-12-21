package com.network.common;

import com.game.gameworld.World;
import com.game.input.JumpCommand;
import com.game.input.MoveCommand;
import com.network.server.ServerGameLogic;
import com.game.gameworld.Player;
import com.helper.Vector2f;
import com.network.stream.MyDataInputStream;
import com.network.stream.MyDataOutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MoveMessageHandler implements NetworkMessageHandler<MoveMessage> {
    private int id;
    private ServerGameLogic serverGameLogic;
    private MoveCommand c;
    public MoveMessageHandler() {
        c = new MoveCommand();
    }

    public MoveMessageHandler(int id) {

    }

    @Override
    public void sendMessage(MoveMessage moveMessage, MyDataOutputStream dos) {
        try {
            dos.write(moveMessage.getMessageType().getByte());
            dos.writeFloat(moveMessage.getDirection().getX());
            dos.writeFloat(moveMessage.getDirection().getY());
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MoveMessage getNetworkMessage(MyDataInputStream dis) {
        try {
            float x = dis.readFloat();
            float y = dis.readFloat();
            return new MoveMessage(new Vector2f(x, y));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void handle(MoveMessage networkMessage) {
        // TODO: Why doesn't it work when overwriting the settings?
        //System.out.println(networkMessage.getDirection());

        /*if(networkMessage.getDirection().getX() != 0) {
            MoveCommand test = new MoveCommand();
            test.addGameObject(World.getInstance().getPlayers().get(0));
            test.setStrength(networkMessage.getDirection());
            test.execute();
        }
        if(networkMessage.getDirection().getY() != 0) {
            JumpCommand test = new JumpCommand();
            test.addGameObject(World.getInstance().getPlayers().get(0));
            test.execute();
        }*/

        /*MoveCommand test = new MoveCommand();

        //test.addGameObject(p);
        test.setStrength(networkMessage.getDirection());
        serverGameLogic.addCommand(test);*/
    }
}
