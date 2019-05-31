package com.wofdev.wofmap.lib.bw;

import com.wofdev.wofmap.lib.biom.BPoint;


/**
 * Задача обработки биомы.
 */
public abstract class IBW {
    public BPoint[][] bPoints;
    public long randomKey;
    public boolean end;

    public IBW(BPoint[][] bPoints, long randomKey) {
        this.bPoints = bPoints;
        this.randomKey = randomKey;
        this.end = false;
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
