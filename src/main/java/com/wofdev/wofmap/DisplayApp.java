package com.wofdev.wofmap;


import com.wofdev.wofmap.lib.biom.BPoint;
import com.wofdev.wofmap.ux.JPanelCallBack;
import com.wofdev.wofmap.ux.JPanelMapGenerate;
import com.wofdev.wofmap.ux.JPanelMapShow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by jb on 03.12.17.
 */
public class DisplayApp implements JPanelCallBack {

    private static boolean bQuit = false;
    private BPoint[][] bPoints;
    private JPanelMapGenerate main;
    private JPanelMapShow show;
    private JFrame frame;


    public static void main(String[] args) {
        new DisplayApp();
    }

    public DisplayApp() {
        frame = new JFrame("JustGame");
        main = new JPanelMapGenerate(frame, this);
        bPoints = main.getSimplexMap().getbPoints();
        frame.add(main);

        frame.setSize(1024, 1024);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                bQuit = true;
                System.exit(0);
            }
        });
        frame.setVisible(true);

        while (!bQuit) {
            try {
                Thread.sleep(100l);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void closeProcess() {
        frame.remove(main);
        show = new JPanelMapShow(frame, bPoints, new JPanelCallBack() {
            @Override
            public void closeProcess() {
                closeShow();
            }
        });
        frame.add(show);
        frame.setVisible(true);
    }

    private void closeShow() {
        frame.remove(show);
        main = new JPanelMapGenerate(frame, DisplayApp.this);
        bPoints = main.getSimplexMap().getbPoints();
        frame.add(main);
        frame.setVisible(true);
    }
}
