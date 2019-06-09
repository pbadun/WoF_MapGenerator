package com.wofdev.wofmap;

import com.wofdev.wofmap.lib.SimplexMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by jb on 03.12.17.
 */
public class Main extends JPanel implements ActionListener, MouseListener {

    private Random random = new Random(System.currentTimeMillis());

    private SimplexMap simplexMap;

    private JFrame frame;
    private Timer timer = new Timer(30, this);

    public Main(JFrame frame) {
        this.frame = frame;
        addMouseListener(this);
        resetMap();
        timer.start();
    }

    /**
     * Генерировать новую карту
     */
    private void resetMap() {
        simplexMap = new SimplexMap(Math.abs(random.nextLong()));
        simplexMap.diamondSquare();
    }

    /**
     * Отображение карты в фрагменте экрана
     *
     * @param g
     */
    public void paint(Graphics g) {
        try {
            g.drawImage(simplexMap.getImage(), 0, 0, frame.getWidth(), frame.getHeight(), null);
            g.setFont(new Font("default", Font.BOLD, 16));
            g.drawString(simplexMap.getStep(), 10, 20);
            g.drawString("Mouse left: reset, right: save/reset", 10, 40);

        } catch (Exception e) {
        }
    }

    /**
     * Обновление экрана.
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        simplexMap.incHeight();
        repaint();
    }

    //----------------------------------------------------------------------------------------------------
    // нажал и отпустил одну из кнопок,
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    // при нажатии одной из кнопок мыши
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        int mm = mouseEvent.getButton();
        if (mm == MouseEvent.BUTTON2) {
            simplexMap.saveMap();
            resetMap();
        } else if (mm == MouseEvent.BUTTON1) {
            resetMap();
        }
    }

    // Отпускаем кнопку
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    // Как только «залезли» курсором на компонент
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    // уводим курсор с компонента
    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

}
