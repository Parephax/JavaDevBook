package com.brackeen.jdb.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * The ScreenManager class manages initializing and displaying full screen graphics mode.
 */
public class ScreenManager {

    private GraphicsDevice device;

    /**
     * Creates a new ScreenManager object.
     */
    public ScreenManager() {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = environment.getDefaultScreenDevice();
    }

    /**
     * Returns a list of compatible display modes for the default device on the system.
     *
     * @return array of device compatible display modes
     */
    public DisplayMode[] getCompatibleDisplayModes() {
        return device.getDisplayModes();
    }

    /**
     * Returns the first compatible mode in a list of modes.
     *
     * @param modes array of display modes to attempt
     * @return first compatible DisplayMode, null if no modes are compatible.
     */
    public DisplayMode findFirstCompatibleMode(DisplayMode[] modes) {
        DisplayMode[] goodModes = device.getDisplayModes();
        for (DisplayMode mode : modes) {
            for (DisplayMode goodMode : goodModes) {
                if (displayModesMatch(mode, goodMode)) {
                    return mode;
                }
            }
        }
        return null;
    }

    /**
     * Returns the current device display mode.
     *
     * @return the device's current DisplayMode
     */
    public DisplayMode getCurrentDisplayMode() {
        return device.getDisplayMode();
    }

    /**
     * Determines if two DisplayMode objects "match." Two display modes match if they have the same resolution,
     * bit depth, and refresh rate. The bit depth is ignored if one of the modes has a bit depth of
     * DisplayMode.BIT_DEPTH_MULTI. Likewise, the refresh rate is ignored if one of the modes has a refresh rate of
     * DisplayMode.REFRESH_RATE_UNKNOWN.
     *
     * @param mode1 DisplayMode to compare
     * @param mode2 Display to compare
     * @return true if DisplayModes are identical, false otherwise.
     */
    public boolean displayModesMatch(DisplayMode mode1, DisplayMode mode2) {
        if (mode1.getWidth() != mode2.getWidth() || mode1.getHeight() != mode2.getHeight()) {
            return false;
        }
        if (mode1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
                mode2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
                mode1.getBitDepth() != mode2.getBitDepth()) {
            return false;
        }
        return mode1.getRefreshRate() == DisplayMode.REFRESH_RATE_UNKNOWN ||
                mode2.getRefreshRate() == DisplayMode.REFRESH_RATE_UNKNOWN ||
                mode1.getRefreshRate() == mode2.getRefreshRate();
    }

    /**
     * Enters full screen mode and changes the DisplayMode. If the DisplayMode is null or not compatible with this
     * device, or if the display mode cannot be changed on this system, the current display mode is used.
     * <p>
     * The display uses a BufferStrategy with 2 buffers.
     *
     * @param displayMode as the DisplayMode to attempt on the device
     */
    public void setFullScreen(DisplayMode displayMode) {
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setIgnoreRepaint(true);
        frame.setResizable(false);

        device.setFullScreenWindow(frame);
        if (displayMode != null && device.isDisplayChangeSupported()) {
            try {
                device.setDisplayMode(displayMode);
            } catch (IllegalArgumentException ignored) {
            }
        }
        frame.createBufferStrategy(2);
    }

    /**
     * Gets the graphics context for the display. The ScreenManager uses double buffering, so applications must
     * call update() to show any graphics drawn.
     * <p>
     * The application must dispose of the graphics object.
     *
     * @return Graphics2D object as display's graphics context
     */
    public Graphics2D getGraphics() {
        Window window = device.getFullScreenWindow();
        if (window != null) {
            BufferStrategy strategy = window.getBufferStrategy();
            return (Graphics2D) strategy.getDrawGraphics();
        } else {
            return null;
        }
    }

    /**
     * Updates the display using a BufferStrategy.
     */
    public void update() {
        Window window = device.getFullScreenWindow();
        if (window != null) {
            BufferStrategy strategy = window.getBufferStrategy();
            if (!strategy.contentsLost()) {
                strategy.show();
            }
            // Sync the display on some systems (on Linux this fixes event queue problems)
            Toolkit.getDefaultToolkit().sync();
        }
    }

    /**
     * Returns the Window currently used in fullscreen mode.
     *
     * @return Window object currently used in fullscreen, null if device is not in fullscreen mode.
     */
    public Window getFullScreenWindow() {
        return device.getFullScreenWindow();
    }

    /**
     * Returns the width of the Window currently used in fullscreen mode.
     *
     * @return int as width of the Window object currently used in fullscreen, 0 if device is not in fullscreen mode.
     */
    public int getWidth() {
        Window window = device.getFullScreenWindow();
        if (window != null) {
            return window.getWidth();
        } else {
            return 0;
        }
    }

    /**
     * Returns the height of the Window currently used in fullscreen mode.
     *
     * @return int as height of the Window object currently used in fullscreen, 0 if device is not in fullscreen mode.
     */
    public int getHeight() {
        Window window = device.getFullScreenWindow();
        if (window != null) {
            return window.getHeight();
        } else {
            return 0;
        }
    }

    /**
     * Restores the screen's display mode.
     */
    public void restoreScreen() {
        Window window = device.getFullScreenWindow();
        if (window != null) {
            window.dispose();
        }
        device.setFullScreenWindow(null);
    }

    /**
     * Creates an image compatible with the current display.
     *
     * @param width        the width of the returned <code>BufferedImage</code>
     * @param height       the height of the returned <code>BufferedImage</code>
     * @param transparency the specified transparency mode
     * @return <code>BufferedImage</code> that is compatible with the display
     */
    public BufferedImage createCompatibleImage(int width, int height, int transparency) {
        Window window = device.getFullScreenWindow();
        if (window != null) {
            GraphicsConfiguration gc = window.getGraphicsConfiguration();
            return gc.createCompatibleImage(width, height, transparency);
        }
        return null;
    }
}
