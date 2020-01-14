package com.brackeen.jdb.graphics;

import java.awt.*;

public class Sprite {

    private Animation anim;
    // position (pixels)
    private float x;
    private float y;
    // velocity (pixels per millisecond)
    private float dx;
    private float dy;

    /**
     * Creates a new Sprite object with the specified Animation.
     *
     * @param anim Animation to assign to this Sprite
     */
    public Sprite(Animation anim) {
        this.anim = anim;
    }

    /**
     * Updates this Sprite's Animation and its position based on the velocity.
     *
     * @param elapsedTime time in milliseconds since last update() call
     */
    public void update(long elapsedTime) {
        x += dx * elapsedTime;
        y += dy * elapsedTime;
        anim.update(elapsedTime);
    }

    /**
     * Gets this Sprite's current x position.
     *
     * @return Sprite's current x-coordinate value.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets this Sprite's current y position.
     *
     * @return Sprite's current y-coordinate value.
     */
    public float getY() {
        return y;
    }

    /**
     * Sets this Sprite's current x position.
     *
     * @param x coordinate value to update.
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Sets this Sprite's current y position.
     *
     * @param y coordinate value to update.
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Gets this Sprite's width, based on the size of the current image.
     *
     * @return width of the Sprite
     */
    public int getWidth() {
        return anim.getImage().getWidth(null);
    }

    /**
     * Gets this Sprite's height, based on the size of the current image.
     *
     * @return height of the Sprite
     */
    public int getHeight() {
        return anim.getImage().getHeight(null);
    }

    /**
     * Gets the horizontal velocity of this Sprite in pixels per millisecond.
     *
     * @return Sprite's x-coordinate velocity component.
     */
    public float getVelocityX() {
        return dx;
    }

    /**
     * Gets the vertical velocity of this Sprite in pixels per millisecond.
     *
     * @return Sprite's y-coordinate velocity component.
     */
    public float getVelocityY() {
        return dy;
    }

    /**
     * Sets the horizontal velocity of this Sprite in pixels per millisecond.
     *
     * @param dx x-coordinate velocity component to set
     */
    public void setVelocityX(float dx) {
        this.dx = dx;
    }

    /**
     * Sets the vertical velocity of this Sprite in pixels per millisecond.
     *
     * @param dy y-coordinate velocity component to set
     */
    public void setVelocityY(float dy) {
        this.dy = dy;
    }

    /**
     * Gets this Sprite's current frame Image.
     *
     * @return Image of the Sprite's current Animation frame.
     */
    public Image getImage() {
        return anim.getImage();
    }
}
