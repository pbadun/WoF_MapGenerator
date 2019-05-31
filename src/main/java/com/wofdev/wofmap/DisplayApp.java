package com.wofdev.wofmap;


import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by jb on 03.12.17.
 */
public class DisplayApp {

    private static boolean bQuit = false;

    public static void main(String[] args) {

        JFrame frame = new JFrame("JustGame");
        Main main = new Main(frame);

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
}
