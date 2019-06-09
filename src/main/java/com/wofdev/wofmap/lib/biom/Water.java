package com.wofdev.wofmap.lib.biom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Water {

    private BPoint[][] bp;
    private List<WaterPoint> waterPoints;
    private HashMap<BPoint, Boolean> fl;
    private int height;
    private boolean end;

    public Water(int x, int y, int height, BPoint[][] bp) {
        this.bp = bp;
        this.waterPoints = new ArrayList<>();
        this.fl = new HashMap<>();
        fl.put(bp[x][y], true);
        waterPoints.add(new WaterPoint(x, y, height));
        this.height = height;
        this.end = false;
    }

    public boolean calc(int maxX, int maxY) {
        List<WaterPoint> newWp = new ArrayList<>();
        List<WaterPoint> removeWp = new ArrayList<>();
        boolean hUp = false;
        boolean mm;
        int x, y;
        double h;
        for (WaterPoint wp : waterPoints) {
            if (wp.height > height) {
                continue;
            }
            wp.height = height;
            h = height;
            x = wp.x;
            y = wp.y;
            mm = false;

            if (wp.x + 1 < maxX && testWater(wp.x + 1, wp.y, h)) {
                x = wp.x + 1;
                y = wp.y;
                h = bp[x][y].getHeight();
                mm = true;
            }
            if (wp.x - 1 >= 0 && testWater(wp.x - 1, wp.y, h)) {
                x = wp.x - 1;
                y = wp.y;
                h = bp[x][y].getHeight();
                mm = true;
            }
            if (wp.y - 1 >= 0 && testWater(wp.x, wp.y - 1, h)) {
                x = wp.x;
                y = wp.y - 1;
                h = bp[x][y].getHeight();
                mm = true;
            }
            if (wp.y + 1 < maxY && testWater(wp.x, wp.y + 1, h)) {
                x = wp.x;
                y = wp.y + 1;
                h = bp[x][y].getHeight();
                mm = true;
            }
            if (mm) {
                height = (int) h;
                fl.put(bp[x][y], true);
                newWp.add(new WaterPoint(x, y, height));
                bp[x][y].setType(BPoint.TYPE_RIVER);
                bp[x][y].setWaterLevel(height);
                hUp = true;
            }
        }
        if (!hUp) {
            height++;
        }
        waterPoints.removeAll(removeWp);
        waterPoints.addAll(newWp);
        return end;
    }

    /**
     * Тестирование возможно ли течение в данном направлении.
     *
     * @param x
     * @param y
     * @param h
     * @return
     */
    private boolean testWater(int x, int y, double h) {
        // Впадает в океан.
        if (!testEnd(bp[x][y].getType())) {
            return false;
        }
        // Высота больще необходимой, вода течет только в низ.
        if (bp[x][y].getHeight() > h || bp[x][y].getHeight() > height) {
            return false;
        }
        if (fl.get(bp[x][y]) != null) {
            if (bp[x][y].getWaterLevel() < height) {
                bp[x][y].setWaterLevel(height);
            }
            return false;
        }
        return true;
    }


    private boolean testEnd(int type) {
        if (BPoint.TYPE_OCEAN == type) {
            end = true;
            return false;
        }
        return true;
    }

}
