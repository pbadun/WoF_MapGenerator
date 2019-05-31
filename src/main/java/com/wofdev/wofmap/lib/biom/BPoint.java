package com.wofdev.wofmap.lib.biom;

import java.awt.*;

/**
 * Еденица биома (точка 1х1 на карте)
 */
public class BPoint {

    public static final int TYPE_NO = 0; // Океан
    public static final int TYPE_OCEAN = 1; // Океан
    public static final int TYPE_SAND = 2; // Песок
    public static final int TYPE_LAND = 3;// Замля
    public static final int TYPE_ROCK = 4;// Горы - камень
    public static final int TYPE_SOURCE = 5; // Источник

    //------------------
    // Трова
    // Лес
    // Вода - река


    private int type;// Тип поверхности
    private int heightLive; // Уровень высоты 1,2,3,4

    private double height; // высота от 1.

    public BPoint(double height) {
        this.height = height;
        this.type = TYPE_NO;
    }

    public Color getColor() {
        int r = (int) height;
        int g = (int) height;
        int b = (int) height;
        switch (this.type) {
            case TYPE_NO:
                break;
            case TYPE_OCEAN:
                b = 255;
                break;
            case TYPE_SAND:
                r = 255;
                g = 255;
                break;
            case TYPE_LAND:
                g = 255;
                r = 255 - r;
                b = 255 - b;
                break;
            case TYPE_ROCK:
                r = 123;
                g = 64;
                break;
        }
        Color c = new Color(r, g, b);
        return c;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getHeight() {
        if (height < 0) {
            return 0;
        } else if (height > 254) {
            return 254;
        }
        return height;
    }
}
