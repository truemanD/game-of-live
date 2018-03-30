/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflive.screen;

import gameoflive.PlayingField;
import gameoflive.PlayingFieldIface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author diyanov-a
 */
public class GameFrame extends JFrame {

    private int fieldSize;
    private static GameFrame INSTANCE;
    private static GameField gfld;

    private GameFrame(int newFieldSize, int days) {
        this.fieldSize = newFieldSize;
        init(days);
        initField();
    }

    public static synchronized GameFrame getInstance() {
        return getInstance(10, 0);
    }

    public static synchronized GameFrame getInstance(int newFieldSize, int days) {
        if (INSTANCE == null) {
            INSTANCE = new GameFrame(newFieldSize, days);
        }
        return INSTANCE;
    }

    private void init(int days) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("Game of Live " + fieldSize + "x" + fieldSize);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gfld = GameField.getInstance(fieldSize, screenSize);
        gfld.addMouseListener(new MyMouseListener());
        Dimension frameSize = new Dimension(gfld.getComponentSize().width, gfld.getComponentSize().height);
        if (days > 0) {
            InteractiveScrollPanel intp = new InteractiveScrollPanel(days);
            intp.setBackground(Color.LIGHT_GRAY);
            frameSize = new Dimension(gfld.getComponentSize().width, gfld.getComponentSize().height + intp.getComponentSize().height);
            add(intp, BorderLayout.AFTER_LAST_LINE);
        } else if (days == 0) {
            InteractivePanel ip = new InteractivePanel(this);
            ip.setBackground(Color.LIGHT_GRAY);
            frameSize = new Dimension(gfld.getComponentSize().width, gfld.getComponentSize().height + ip.getComponentSize().height);
            add(ip, BorderLayout.AFTER_LAST_LINE);
        }
        setMinimumSize(frameSize);
        setMaximumSize(screenSize);
        add(gfld, BorderLayout.CENTER);
        setVisible(true);
    }

    public void draw(List<List<Boolean>> field) {
        gfld.setField(field);
        gfld.repaint();
    }

    private void initField() {
        List<List<Boolean>> field = new ArrayList<>();
        for (int x = 0; x < fieldSize; x++) {
            List<Boolean> row = new ArrayList<>();
            for (int y = 0; y < fieldSize; y++) {
                row.add(Boolean.FALSE);
            }
            field.add(row);
        }
        gfld.setField(field);
    }

    static GameField getGfld() {
        return gfld;
    }

    private class MyMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            int ss = (int) gfld.getSquareSize();
            int x = e.getPoint().x / ss;
            int y = e.getPoint().y / ss;
            repaintSquare(x, y);
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
//            System.out.println(e);

        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        void repaintSquare(int x, int y) {
            PlayingFieldIface pf = PlayingField.getInstance();
            List<List<Boolean>> field = pf.getField();
            System.out.println("Before set: "+pf.toString());
            List<Boolean> row = field.get(y);
            System.out.println(row.get(x));
            if (row.get(x)) {
                row.set(x, Boolean.FALSE);
            }
            if (!row.get(x)) {
                row.set(x, Boolean.TRUE);
            }
            pf.setField(field);
            System.out.println("After set: "+pf.toString());
            gfld.setField(pf.getField());
            gfld.repaint();
        }
    }
}
