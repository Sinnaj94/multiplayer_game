package com.network.common;

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
    private Player p;
    private ServerGameLogic serverGameLogic;
    private MoveCommand c;
    public MoveMessageHandler() {
        c = new MoveCommand();
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

    public void registerPlayer(Player p) {
        this.p = p;
        c.addGameObject(p);
    }

    public void registerServerGameLogic(ServerGameLogic serverGameLogic) {
        this.serverGameLogic = serverGameLogic;
    }

    @Override
    public void handle(MoveMessage networkMessage) {
        // TODO: Why doesn't it work when overwriting the settings?
        System.out.println("Move message erhalten.");
        MoveCommand test = new MoveCommand();
        test.addGameObject(p);
        test.setStrength(networkMessage.getDirection());
        serverGameLogic.addCommand(test);
    }
}
