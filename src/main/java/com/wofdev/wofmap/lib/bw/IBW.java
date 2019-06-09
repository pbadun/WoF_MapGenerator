package com.wofdev.wofmap.lib.bw;

import com.wofdev.wofmap.lib.biom.BPoint;

import java.util.Random;


/**
 * Задача обработки биомы.
 */
public abstract class IBW {
    public BPoint[][] bPoints;
    public long randomKey;
    public boolean end;
    public Random random;

    /**
     * @param bPoints   - Массив высот
     * @param randomKey - Стартовый ключ генератора улучайных чисел.
     */
    public IBW(BPoint[][] bPoints, long randomKey) {
        this.bPoints = bPoints;
        this.randomKey = randomKey;
        this.end = false;
        this.random = new Random(randomKey);
    }

    public abstract void calc();

    /**
     * Окончание процесса обработки текущей задачи
     *
     * @return
     */
    public boolean isEnd() {
        return end;
    }
}
