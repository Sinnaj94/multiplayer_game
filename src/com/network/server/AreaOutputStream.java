package com.network.server;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class AreaOutputStream extends OutputStream {
    private JTextArea jTextArea;

    public AreaOutputStream(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }

    @Override
    public void write(int b) throws IOException {
        jTextArea.append(String.valueOf((char)b));
        // scrolls the text area to the end of data
        jTextArea.setCaretPosition(jTextArea.getDocument().getLength());
    }
}
