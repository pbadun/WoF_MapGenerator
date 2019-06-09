package com.wofdev.wofmap.lib.biom;

/**
 *
 */
public class WaterPoint {
    public int x;
    public int y;
    public int height;
    public boolean consider;

    /**
     * @param x      - Координаты
     * @param y      - Координаты
     * @param height - Высота
     */
    public WaterPoint(int x, int y, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.consider = true;
    }

    /**
     * Поднять высоту
     */
    public void incHeight() {
        this.height++;
    }

    /**
     * Рассматривать при расчетах
     *
     * @return
     */
    public boolean isConsider() {
        return consider;
    }

    /**
     * Координаты Х
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Координаты У
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Получить высоту.
     *
     * @return
     */
    public int getHeight() {
        return height;
    }
}
