package com.brackeen.jdb.graphics;

import java.awt.*;
import java.util.ArrayList;

/**
 * The Animation class manages a series of images (frames) and the amount of time to display each frame.
 */
public class Animation {

    private ArrayList<AnimFrame> frames;
    private int currFrameIndex;
    private long animTime;
    private long totalDuration;

    /**
     * Creates a new, empty Animation.
     */
    public Animation() {
        frames = new ArrayList<AnimFrame>();
        totalDuration = 0;
        start();
    }

    /**
     * Adds an image to the animation with the specified duration.
     *
     * @param image    to be added to the Animation
     * @param duration as the amount of time to display the frame
     */
    public synchronized void addFrame(Image image, long duration) {
        totalDuration += duration;
        frames.add(new AnimFrame(image, totalDuration));
    }

    /**
     * Starts this animation over from the beginning.
     */
    public synchronized void start() {
        animTime = 0;
        currFrameIndex = 0;
    }

    /**
     * Updates this animation's current image (frame), if necessary.
     *
     * @param elapsedTime as time in milliseconds since last update() call
     */
    public synchronized void update(long elapsedTime) {
        if (frames.size() > 1) {
            animTime += elapsedTime;
            if (animTime >= totalDuration) {
                animTime = animTime % totalDuration;
                currFrameIndex = 0;
            }

            while (animTime > getFrame(currFrameIndex).endTime) {
                currFrameIndex++;
            }
        }
    }

    /**
     * Get this Animation's current frame image.
     *
     * @return current frame image or null if the Animation has no frames.
     */
    public synchronized Image getImage() {
        if (frames.size() == 0) {
            return null;
        } else {
            return getFrame(currFrameIndex).image;
        }
    }

    private AnimFrame getFrame(int i) {
        return frames.get(i);
    }

    private class AnimFrame {

        Image image;
        long endTime;

        public AnimFrame(Image image, long endTime) {
            this.image = image;
            this.endTime = endTime;
        }
    }
}
