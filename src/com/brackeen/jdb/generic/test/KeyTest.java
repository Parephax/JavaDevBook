package com.brackeen.jdb.generic.test;

import com.brackeen.jdb.generic.GameCore;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

/**
 * A simple keyboard test. Displays keys pressed and released to the screen. Useful for debugging key input, too.
 */
public class KeyTest extends GameCore implements KeyListener {

    public static void main(String[] args) {
        new KeyTest().run();
    }

    private LinkedList<String> messages = new LinkedList<String>();

    @Override
    public void init() {
        super.init();

        Window window = screen.getFullScreenWindow();

        // allow input fo the TAB key and other keys normally used for focus traversal
        window.setFocusTraversalKeysEnabled(false);

        // register this object as a key listener for the window
        window.addKeyListener(this);

        addMessage("KeyInputTest. Press Escape to exit.");
    }

    /**
     * Draw the list of messages.
     *
     * @param g as Graphics2D object to draw to screen.
     */
    @Override
    public synchronized void draw(Graphics2D g) {
        Window window = screen.getFullScreenWindow();

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // draw background
        g.setColor(window.getBackground());
        g.fillRect(0, 0, screen.getWidth(), screen.getHeight());

        // draw messages
        g.setColor(window.getForeground());
        int y = FONT_SIZE;
        for (String message : messages) {
            g.drawString(message, 5, y);
            y += FONT_SIZE;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // this is called after the key is released - ignore it
        // make sure the key isn't processed for anything else
        e.consume();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // exit the program
        if (keyCode == KeyEvent.VK_ESCAPE) {
            stop();
        } else {
            addMessage("Pressed: " + KeyEvent.getKeyText(keyCode));

            // make sure the key isn't processed for anything else
            e.consume();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        addMessage("Released: " + KeyEvent.getKeyText(keyCode));

        // make sure the key isn't processed for anything else
        e.consume();
    }

    /**
     * Add a message to the list of messages.
     *
     * @param message as String to add to the message list
     */
    public synchronized void addMessage(String message) {
        messages.add(message);
        if (messages.size() >= (screen.getHeight() - FONT_SIZE * 2) / FONT_SIZE) {
            messages.removeFirst();
        }
    }
}
