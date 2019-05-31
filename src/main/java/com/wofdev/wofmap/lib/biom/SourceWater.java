package com.wofdev.wofmap.lib.biom;

/**
 * Источник вады.
 */
public class SourceWater {
    private int x, y;

    public SourceWater(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // ----------------------------------------------------

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
