package com.brackeen.jdb.generic;

import com.brackeen.jdb.graphics.ScreenManager;

import javax.swing.*;
import java.awt.*;

/**
 * Simple abstract class used for testing. Subclasses should implement the draw() method.
 */
public abstract class GameCore {

    protected static final int FONT_SIZE = 24;

    private static final DisplayMode[] POSSIBLE_MODES = {
            new DisplayMode(800, 600, 32, 0),
            new DisplayMode(800, 600, 24, 0),
            new DisplayMode(800, 600, 16, 0),
            new DisplayMode(640, 480, 32, 0),
            new DisplayMode(640, 480, 24, 0),
            new DisplayMode(640, 480, 16, 0)
    };

    private boolean isRunning;
    protected ScreenManager screen;

    /**
     * Signals the game loop that it's time to quit.
     */
    public void stop() {
        isRunning = false;
    }

    /**
     * Calls init() and gameLoop()
     */
    public void run() {
        try {
            init();
            gameLoop();
        } finally {
            screen.restoreScreen();
        }
    }

    /**
     * Sets full screen mode and initiates objects.
     */
    public void init() {
        screen = new ScreenManager();
        DisplayMode displayMode = screen.findFirstCompatibleMode(POSSIBLE_MODES);
        screen.setFullScreen(displayMode);

        Window window = screen.getFullScreenWindow();
        window.setFont(new Font("Dialog", Font.PLAIN, FONT_SIZE));
        window.setBackground(Color.BLUE);
        window.setForeground(Color.WHITE);

        isRunning = true;
    }

    /**
     * Loads an image from a given filename.
     *
     * @param fileName as a string for file path
     * @return Image object loaded from path
     */
    public Image loadImage(String fileName) {
        return new ImageIcon(fileName).getImage();
    }

    /**
     * Runs through the game loop until stop() is called.
     */
    public void gameLoop() {
        long currTime = System.currentTimeMillis();

        while (isRunning) {
            long elapsedTime = System.currentTimeMillis() - currTime;
            currTime += elapsedTime;

            // update
            update(elapsedTime);

            // draw the screen
            Graphics2D g = screen.getGraphics();
            draw(g);
            g.dispose();
            screen.update();

            // take a nap
            try {
                Thread.sleep(20);
            } catch (InterruptedException ignored) {
            }
        }
    }

    /**
     * Updates the state of the game based on the amount of elapsed time that has passed.
     *
     * @param elapsedTime time in ms since the last update() call.
     */
    public void update(long elapsedTime) {
        // do nothing
    }

    /**
     * Draws to the screen. Subclasses must override this method.
     *
     * @param g as Graphics2D object to draw to screen.
     */
    public abstract void draw(Graphics2D g);
}
