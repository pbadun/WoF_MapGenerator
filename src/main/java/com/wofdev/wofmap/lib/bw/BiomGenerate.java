package com.wofdev.wofmap.lib.bw;

import com.wofdev.wofmap.lib.biom.BPoint;

import java.util.Random;

public class BiomGenerate extends IBW {

    private int height;
    private int[] liv;
    private int[] lp;
    private Random random;
    private int oceanPrc;
    private int sandPrc;
    private int landPrc;
    private long prc;

    public BiomGenerate(BPoint[][] bPoints, long randomKey) {
        super(bPoints, randomKey);
        this.randomKey = randomKey;
        this.bPoints = bPoints;
        this.height = 0;
        this.end = false;
        this.liv = new int[4];
        this.random = new Random(randomKey);
        this.oceanPrc = 15 + random.nextInt(20); // 35 - 45
        this.sandPrc = 1 + random.nextInt(5); // 2-5
        this.landPrc = 100 - oceanPrc - sandPrc - random.nextInt(30) - 5;
        prc = bPoints.length * bPoints[0].length;
    }

    /**
     * Процесс подсчета
     */
    @Override
    public void calc() {
        if (height >= 255) {
            end = true;
        } else {
            height++;
            calcBiome();
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    private void calcWater() {

    }
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Рассчет уровня вады,песка, земли, гор.
     */
    private void calcBiome() {
        this.lp = new int[4];
        int xIn;
        for (int x = 0; x < bPoints.length; x++) {
            for (int y = 0; y < bPoints[x].length; y++) {
                xIn = (int) bPoints[x][y].getHeight();
                if (xIn < liv[0]) { // Море
                    bPoints[x][y].setType(BPoint.TYPE_OCEAN);
                    bPoints[x][y].setWaterLevel(liv[0]);
                    lp[0]++; // Всего точек по этому правилу
                } else if (liv[1] > xIn) {// Песок
                    bPoints[x][y].setType(BPoint.TYPE_SAND);
                    lp[1]++;
                } else if (liv[2] > xIn) { // Земля
                    bPoints[x][y].setType(BPoint.TYPE_LAND);
                    lp[2]++;
                } else if (liv[3] > xIn) { // Горы
                    bPoints[x][y].setType(BPoint.TYPE_ROCK);
                    lp[3]++;
                }
            }
        }
        if (!testH(prc, lp[0], oceanPrc)) {
            liv[0] = height;
        }
        if (!testH(prc, lp[1], sandPrc)) {
            liv[1] = height;
        }
        if (!testH(prc, lp[2], landPrc)) {
            liv[2] = height;
        }
        if (!testH(prc, lp[3], 100)) {
            liv[3] = height;
        }
    }

    /**
     * Оценка занятого пространства биомой.
     *
     * @param total - всего точек
     * @param th    - Сколько зането беомой
     * @param prc   - допустимый процент который можно занять
     * @return
     */
    private boolean testH(long total, long th, long prc) {
        long r = th * 100l / total;
        return r > prc;
    }

}
