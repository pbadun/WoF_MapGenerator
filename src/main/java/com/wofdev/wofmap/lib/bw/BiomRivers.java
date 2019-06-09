package com.wofdev.wofmap.lib.bw;

import com.wofdev.wofmap.lib.biom.BPoint;
import com.wofdev.wofmap.lib.biom.Water;

import java.util.ArrayList;
import java.util.List;

/**
 * Генератор
 */
public class BiomRivers extends IBW {

    // Реки (Точка - высота)
    private List<Water> waters;

    public BiomRivers(BPoint[][] bPoints, long randomKey) {
        super(bPoints, randomKey);
        this.waters = new ArrayList<>();
        searchRive();
    }

    /**
     * Найти все источники.
     */
    private void searchRive() {
        for (int x = 0; x < bPoints.length; x++) {
            for (int y = 0; y < bPoints[0].length; y++) {
                if (bPoints[x][y].getType() == BPoint.TYPE_SOURCE_RIVER) {
                    waters.add(new Water(x, y, (int) bPoints[x][y].getHeight(), bPoints));
                }
            }
        }
    }

    @Override
    public void calc() {
        List<Water> wremov = new ArrayList<>();
        for (Water w : waters) {
            if (w.calc(bPoints.length, bPoints[0].length)) {
                wremov.add(w);
            }
        }
        waters.removeAll(wremov);
        if (waters.size() == 0) {
            end = true;
        }
    }

}
