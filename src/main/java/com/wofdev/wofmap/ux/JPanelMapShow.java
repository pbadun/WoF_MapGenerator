package com.wofdev.wofmap.ux;

import com.wofdev.wofmap.lib.biom.BPoint;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class JPanelMapShow extends JPanel implements ActionListener, MouseInputListener {
    final String WATER = "water";
    final String SAND = "sand";
    final String DIRT = "dirt";
    final String GRASS = "grass";
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;


    private JPanelCallBack callBack;

    private JFrame frame;
    private BPoint[][] bPoints;
    private Timer timer = new Timer(30, this);
    private HashMap<String, Image> imageMap;
    private int bX;
    private int bY;
    private int bXM, bYM;
    private int mX, mY;

    public JPanelMapShow(JFrame frame, BPoint[][] bPoints, JPanelCallBack callBack) {
        this.callBack = callBack;
        this.frame = frame;
        this.bPoints = bPoints;
        this.imageMap = new HashMap<>();
        imageMap.put(WATER, new ImageIcon("iso/water.png").getImage());
        imageMap.put(SAND, new ImageIcon("iso/sand.png").getImage());
        imageMap.put(DIRT, new ImageIcon("iso/dirt.png").getImage());
        imageMap.put(GRASS, new ImageIcon("iso/grass.png").getImage());
        timer.start();
        bX = 500;
        bY = 500;
        mX = 0;
        mY = 0;
        addMouseListener(this);
        addMouseMotionListener(this);
        frame.addKeyListener(new KeyInputHandler());
        //addKeyListener(new KeyInputHandler());
    }

    //-----------------------------------------------------------------------------
    @Override
    public void paint(Graphics g) {
        try {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight()); //заполнить прямоугольник


            render(g);

            //g.setFont(new Font("default", Font.BOLD, 16));
            //g.drawString(simplexMap.getStep(), 10, 20);
            //g.drawString("Mouse left: reset, right: save/reset", 10, 40);

            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void render(Graphics g) {
        int xx, yy, zz;
        int type;
        Image image;

        int h = 64;
        int w = 100;
        int hh = 25;//h / 2;
        int ww = w / 2;

        //output = new BufferedImage(BOARD_SIZE, BOARD_SIZE, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 128; y++) {
                type = bPoints[x][y].getType();
                zz = (int) bPoints[x][y].getHeight();
                switch (type) {
                    case BPoint.TYPE_OCEAN:  // Океан
                        zz = bPoints[x][y].getWaterLevel();
                        image = imageMap.get(WATER);
                        break;
                    case BPoint.TYPE_SAND: // Песок
                        image = imageMap.get(SAND);
                        break;
                    case BPoint.TYPE_LAND:// Замля
                        image = imageMap.get(GRASS);
                        break;
                    case BPoint.TYPE_ROCK:// Горы - камень
                        image = imageMap.get(DIRT);
                        break;
                    case BPoint.TYPE_SOURCE_RIVER:// Источник реки
                        image = imageMap.get(WATER);
                        zz = bPoints[x][y].getWaterLevel();
                        break;
                    case BPoint.TYPE_SOURCE_MINE_A:// Шахта - горная. (разнородные материалы - рандом.)
                        image = imageMap.get(DIRT);
                        break;
                    case BPoint.TYPE_SOURCE_MINE_B:// Шахта - степная. (разнородные материалы - рандом.)
                        image = imageMap.get(DIRT);
                        break;
                    case BPoint.TYPE_RIVER:// Река.
                        image = imageMap.get(WATER);
                        zz = bPoints[x][y].getWaterLevel();
                        break;
                    default:
                        image = imageMap.get(GRASS);
                        break;
                }
                xx = x * ww - y * ww;
                yy = (int)((x * hh + y * hh) - zz * 1.5f);
                int xC = bX - mX;
                int yC = bY + mY;
                g.drawImage(image, xx + xC, yy - yC, w, h, null);
            }
        }
    }


    //-----------------------------------------------------------------------------

    /**
     * Событие таймера, обнавляет экран.
     *
     * @param actionEvent
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (upPressed) {
            bY -= 10;
        }
        if (downPressed) {
            bY += 10;
        }
        if (leftPressed) {
            bX += 10;
        }
        if (rightPressed) {
            bX -= 10;
        }
        //System.out.println(bX + ":" + bY);
        repaint();
    }

    //-----------------------------------------------------------------------------

    // при нажатии одной из кнопок мыши
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        int mm = mouseEvent.getButton();
        if (mm == MouseEvent.BUTTON1) {
            bXM = mouseEvent.getX();
            bYM = mouseEvent.getY();
        } else if (mm == MouseEvent.BUTTON2) {

        } else if (mm == MouseEvent.BUTTON3) {
            callBack.closeProcess();
        }
    }

    //-----------------------------------------------------------------------------

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        bX -= mX;
        bY += mY;
        mX = 0;
        mY = 0;
        if (bX < 0) {
            //bX = 0;
        }
        if (bX > 1000) {
            //bX = 1000;
        }
        if (bY < 0) {
            //bY = 0;
        }
        if (bY > 1000) {
            //bY = 1000;
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mX = bXM - mouseEvent.getX();
        mY = bYM - mouseEvent.getY();
        System.out.println("x:" + mX + " y:" + mY + "  " + bX + ":" + bY);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        // перемещение без нажатия
    }
    //-----------------------------------------------------------------------------

    class KeyInputHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
            System.out.println("+:" + e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = false;
            }
            System.out.println("-:" + e.getKeyCode());
        }
    }

}

