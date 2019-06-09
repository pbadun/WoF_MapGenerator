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

    public static final int TYPE_SOURCE_RIVER = 5; // Источник реки
    public static final int TYPE_SOURCE_MINE_A = 6; // Шахта - горная. (разнородные материалы - рандом.)
    public static final int TYPE_SOURCE_MINE_B = 7; // Шахта - степная. (разнородные материалы - рандом.)

    public static final int TYPE_RIVER = 8; // Река.

    // Трова
    // Лес
    //------------------

    private int type;// Тип поверхности
    private int heightLive; // Уровень высоты 1,2,3,4
    private double height; // высота от 1.
    private int waterLevel; // Уровень воды в текущем месте. т.е. есть дна!

    public BPoint(double height) {
        this.height = height;
        this.type = TYPE_NO;
    }

    /**
     * Цвет пикселя
     *
     * @return
     */
    public Color getColor() {
        int r = (int) height;
        int g = (int) height;
        int b = (int) height;
        switch (this.type) {
            case TYPE_NO: // Н/А
                break;
            case TYPE_OCEAN: // Океан
                b = 255;
                break;
            case TYPE_SAND: // Песок
                r = 255;
                g = 255;
                break;
            case TYPE_LAND: // Степь
                g = 255;
                r = 255 - r;
                b = 255 - b;
                break;
            case TYPE_ROCK: // Гора
                r = 123;
                g = 64;
                break;
            case TYPE_SOURCE_RIVER: // Родник
                r = 0;
                g = 0;
                b = 255;
                break;
            case TYPE_SOURCE_MINE_A: // Шахта
                r = 0;
                b = g = 255;
                break;
            case TYPE_SOURCE_MINE_B: // Шахта
                r = 0;
                b = g = 127;
                break;
            case TYPE_RIVER: // Река.
                r = 64;
                g = 64;
                b = 255;
                break;
        }
        Color c = new Color(r, g, b);
        return c;
    }

    /**
     * Установить тип
     *
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Получить высоту
     *
     * @return
     */
    public double getHeight() {
        if (height < 0) {
            return 0;
        } else if (height > 254) {
            return 254;
        }
        return height;
    }

    /**
     * Тип биомы
     *
     * @return
     */
    public int getType() {
        return type;
    }

    /**
     * Уровень воды в данном месте
     *
     * @return
     */
    public int getWaterLevel() {
        return waterLevel;
    }

    /**
     * Установить уровень воды в текущем месте.
     *
     * @param waterLevel
     */
    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }
}
