package com.wofdev.wofmap.lib.bw;

import com.wofdev.wofmap.lib.biom.BPoint;


/**
 * Герератор точек - ресурсов. Радники, входы в шахты, и т.п...
 */
public class BiomSource extends IBW {

    private int[] totalSource;

    public BiomSource(BPoint[][] bPoints, long randomKey) {
        super(bPoints, randomKey);
        this.totalSource = new int[3];
        totalSource[0] = 3 + random.nextInt(4); //  3-7 источников воды
        totalSource[1] = 4 + random.nextInt(6); // 4-10 шахт
        totalSource[2] = 10 + random.nextInt(5); //  ХЗ
    }

    @Override
    public void calc() {
        // Надо найти оптимальные-случайные точки для расположения источников.
        // Учитывать факт их размещения отнасительно биом.
        // прим:
        // - родник желательно пускать из гор. ?  или степи
        // - шахты в идеале размещать не далеко от гор
        //
        if (totalSource[0] != 0) {
            searchA1();
        } else if (totalSource[1] != 0) {
            searchA2();
        } else if (totalSource[2] != 0) {
            searchA3();
        } else {
            end = true;
        }
    }


    /**
     * Поиск месторасположения для родников.
     */
    private void searchA1() {
        int x, y;
        do {
            x = random.nextInt(bPoints[0].length);
            y = random.nextInt(bPoints.length);
            if (bPoints[x][y].getType() == BPoint.TYPE_ROCK
                //|| bPoints[x][y].getType() != BPoint.TYPE_LAND
            ) {
                bPoints[x][y].setType(BPoint.TYPE_SOURCE_RIVER);
                bPoints[x][y].setWaterLevel((int) bPoints[x][y].getHeight());
                break;
            }
        } while (true);
        totalSource[0]--;
    }

    /**
     * Поиск местоположения для шахр ресурсов - горная
     */
    private void searchA2() {
        int x, y;
        do {
            x = random.nextInt(bPoints[0].length);
            y = random.nextInt(bPoints.length);
            if (bPoints[x][y].getType() == BPoint.TYPE_ROCK
                //|| bPoints[x][y].getType() != BPoint.TYPE_LAND
            ) {
                bPoints[x][y].setType(BPoint.TYPE_SOURCE_MINE_A);
                break;
            }
        } while (true);

        totalSource[1]--;
    }

    /**
     * Поиск местоположения для шахр ресурсов - степная
     */
    private void searchA3() {
        int x, y;
        do {
            x = random.nextInt(bPoints[0].length);
            y = random.nextInt(bPoints.length);
            if (bPoints[x][y].getType() == BPoint.TYPE_LAND
            ) {
                bPoints[x][y].setType(BPoint.TYPE_SOURCE_MINE_B);
                break;
            }
        } while (true);
        totalSource[2]--;
    }

}
