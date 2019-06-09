package com.wofdev.wofmap.lib;

import com.wofdev.wofmap.lib.biom.BPoint;
import com.wofdev.wofmap.lib.bw.BiomGenerate;
import com.wofdev.wofmap.lib.bw.BiomRivers;
import com.wofdev.wofmap.lib.bw.BiomSource;
import com.wofdev.wofmap.lib.bw.IBW;
import com.wofdev.wofmap.lib.ds.DSquare;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;

/**
 * https://habr.com/ru/post/111538/
 */
public class SimplexMap implements Runnable {

    private long randomKey; // Ключ генерации ландшафта
    private final static int BOARD_SIZE = 512 * 2 + 1;
    private BPoint[][] bPoints;
    // Текущая высота

    private int step = 1;
    private int i = 0;
    private BufferedImage output;
    private IBW bg;

    private Thread th;

    private final Object lock = new Object();

    public SimplexMap(long randomKey) {
        this.randomKey = randomKey;
        //this.randomKey = 7852911085795504060l;
    }

    //------------------------------------------------------------------------------------------------------------------

    /**
     * Алгоритм генерации карты.
     *
     * @return
     */
    public void diamondSquare() {
        DSquare ds = new DSquare(BOARD_SIZE, randomKey, 127d);
        double[][] data = ds.diamondSquare();
        bPoints = new BPoint[BOARD_SIZE][BOARD_SIZE];
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                bPoints[x][y] = new BPoint(data[x][y]);
            }
        }
        setBg();
    }

    /**
     * Перегон из буфера в изображение.
     *
     * @return
     */
    public BufferedImage getImage() {
        output = new BufferedImage(BOARD_SIZE, BOARD_SIZE, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                output.setRGB(x, y, bPoints[x][y].getColor().getRGB());
            }
        }
        return output;
    }

    //------------------------------------------------------------------------------------------------------------------

    private void setBg() {
        switch (step) {
            case 1:
                // форимрования высот: море, песок, горы.
                this.bg = new BiomGenerate(bPoints, randomKey);
                break;
            case 2:
                // Источники - родники, входы в шахты,
                bg = new BiomSource(bPoints, randomKey);
                break;
            case 3:
                // Течение воды.
                bg = new BiomRivers(bPoints, randomKey);
                break;
            default:
                this.bg = null;
        }
        if (bg != null) {
            this.th = new Thread(this);
            th.start();
        }
    }

    @Override
    public void run() {
        boolean work = true;
        while (work) {
            if (bg != null && !bg.isEnd()) {
                bg.calc();
                i++;
                continue;
            }
            step++;
            i = 0;
            setBg();
            work = false;
        }

    }

    /**
     * Поднять высоту на 1 еденицу.
     */
    public void incHeight() {

        if (bg == null) {
            // Процесс формирования биом завершен.
            return;
        }

    }


    //------------------------------------------------------------------------------------------------------------------

    /**
     * Счетчик шагов обработки, для контроля работы обработки.
     *
     * @return
     */
    public String getStep() {
        if (bg == null) {
            return "Step: END";
        }
        return "Step: " + i;
    }

    //------------------------------------------------------------------------------------------------------------------
    public void saveMap() {
        if (!bg.isEnd()) {
            return;
        }
        try {
            Calendar calendar = Calendar.getInstance();
            File outputfile = new File(
                    Integer.toString(BOARD_SIZE) + "_RandomKey_" + Long.toString(randomKey) + "_.png");
            ImageIO.write(output, "png", outputfile);
        } catch (Exception e) {
            System.out.println("Error writing file");
        }
    }
}
