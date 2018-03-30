/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflive.screen;

import gameoflive.GameOfLiveException;
import gameoflive.PlayingField;
import gameoflive.PlayingFieldIface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 *
 * @author diyanov-a
 */
class InteractiveScrollPanel extends JPanel {

    private JLabel label;
    private Dimension componentSize;

    InteractiveScrollPanel(int days) {
        super(true);
        componentSize = new Dimension(getWidth(), 35);

        add(prepareScrollBar(days), BorderLayout.SOUTH);
    }

    private JScrollBar prepareScrollBar(int days) {
        label = new JLabel();
        setLayout(new BorderLayout());

        JScrollBar hbar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 20, 0, 300);

        hbar.setUnitIncrement(1);
        hbar.setBlockIncrement(1);
        hbar.setMinimum(1);
        System.out.println("MaxDays:" + days);
        hbar.setMaximum(days+20);

        hbar.addAdjustmentListener(new MyAdjustmentListener());
        add(prepareGenerateButton(), BorderLayout.CENTER);

        add(label, BorderLayout.CENTER);
        return hbar;
    }

    private JButton prepareGenerateButton() {
        JButton b_generate = new JButton("generate");
        b_generate.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PlayingFieldIface pf = PlayingField.getInstance().generateRandom();
                GameFrame gf = GameFrame.getInstance(pf.getField().size(), 100);
                gf.draw(pf.getField());
            }
        });
        return b_generate;
    }

    class MyAdjustmentListener implements AdjustmentListener {

        public void adjustmentValueChanged(AdjustmentEvent e) {
            label.setText("Day: " + e.getValue() + "      ");
            PlayingFieldIface pf = PlayingField.getInstance();
            GameFrame gf = GameFrame.getInstance();
            try {
                gf.draw(pf.goToDay(e.getValue()).getField());
            } catch (GameOfLiveException ex) {
                gf.draw(pf.getField());
            }
            repaint();
        }
    }

    Dimension getComponentSize() {
        return componentSize;
    }
}
