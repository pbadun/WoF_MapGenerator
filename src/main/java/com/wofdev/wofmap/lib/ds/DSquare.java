package com.wofdev.wofmap.lib.ds;

import java.util.Random;

/**
 * Алгоритм гереации ландшафта схожего с квадратам поверхности планеты.
 */
public class DSquare {

    private final int BOARD_SIZE;
    private final long R_KEY;
    private final double SEED;


    /**
     * @param B_SIZE - размер карты
     * @param r_KEY  - стартовое значение генератора случайных чисел
     * @param seed   - порог начала генерации ланшафта
     */
    public DSquare(int B_SIZE, long r_KEY, double seed) {
        this.BOARD_SIZE = B_SIZE;
        this.R_KEY = r_KEY;
        this.SEED = seed;
    }

    public double[][] diamondSquare() {
        double[][] data = new double[BOARD_SIZE][BOARD_SIZE];

        // seed the data
        data[0][0] = SEED;
        data[0][BOARD_SIZE - 1] = SEED;
        data[BOARD_SIZE - 1][0] = SEED;
        data[BOARD_SIZE - 1][BOARD_SIZE - 1] = SEED;

        double h = SEED;
        Random r = new Random(R_KEY);
        for (int sideLength = BOARD_SIZE - 1; sideLength >= 2; sideLength /= 2, h /= 2.0) {
            int halfSide = sideLength / 2;
            for (int x = 0; x < BOARD_SIZE - 1; x += sideLength) {
                for (int y = 0; y < BOARD_SIZE - 1; y += sideLength) {
                    double avg = data[x][y] + // top left
                            data[x + sideLength][y] + // top right
                            data[x][y + sideLength] + // lower left
                            data[x + sideLength][y + sideLength];// lower right
                    avg /= 4.0;
                    // center is average plus random offset
                    data[x + halfSide][y + halfSide] = avg
                            + (r.nextDouble() * 2 * h) - h;
                }
            }
            for (int x = 0; x < BOARD_SIZE - 1; x += halfSide) {
                for (int y = (x + halfSide) % sideLength; y < BOARD_SIZE - 1; y += sideLength) {
                    double avg = data[(x - halfSide + BOARD_SIZE - 1)
                            % (BOARD_SIZE - 1)][y]
                            + // left of center
                            data[(x + halfSide) % (BOARD_SIZE - 1)][y] +
                            data[x][(y + halfSide) % (BOARD_SIZE - 1)] +
                            data[x][(y - halfSide + BOARD_SIZE - 1)
                                    % (BOARD_SIZE - 1)]; // above center
                    avg /= 4.0;
                    avg = avg + (r.nextDouble() * 2 * h) - h;
                    // update value for center of diamond
                    data[x][y] = avg;
                    if (x == 0)
                        data[BOARD_SIZE - 1][y] = avg;
                    if (y == 0)
                        data[x][BOARD_SIZE - 1] = avg;
                }
            }
        }
        return data;
    }

}
