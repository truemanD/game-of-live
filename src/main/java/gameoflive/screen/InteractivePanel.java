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

/**
 * @author diyanov-a
 */
public class InteractivePanel extends JPanel {

    private static Dimension componentSize;
    private static int day;
    private GameField gfld;
    private GameFrame gf;
    private PlayingFieldIface pf;


    public InteractivePanel(GameFrame gf) {
        this.gf = gf;
        this.gfld = GameFrame.getGfld();
        day = 0;
        this.pf = PlayingField.getInstance();
        add(prepareRunButton());
        componentSize = new Dimension(getWidth(), 35);
    }


    private JButton prepareRunButton() {
        JButton b_generate = new JButton("Go");
        b_generate.addActionListener(e -> {
            for (; ; ) {
                System.out.println("prepareRunButton\n Day: " + pf.getStepCounter());
                try {
                    Thread.sleep(1000);
                    gf.draw(pf.goNextDay().getField());
                } catch (GameOfLiveException ex) {
                    System.out.println(ex.getMessage());
                    gf.draw(pf.getField());
                    break;
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
        return b_generate;
    }

    Dimension getComponentSize() {
        System.out.println(componentSize);
        return componentSize;
    }

}
