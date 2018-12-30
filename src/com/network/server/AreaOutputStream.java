package com.network.server;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class AreaOutputStream extends OutputStream {
    private JTextArea jTextArea;
    private boolean error;

    public AreaOutputStream(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
        this.error = false;
    }

    public AreaOutputStream(JTextArea jTextArea, boolean error) {
        this.jTextArea = jTextArea;
        this.error = error;
    }

    @Override
    public void write(int b) throws IOException {
        jTextArea.append(String.valueOf((char) b));
        // scrolls the text area to the end of data
        jTextArea.setCaretPosition(jTextArea.getDocument().getLength());
    }
}

