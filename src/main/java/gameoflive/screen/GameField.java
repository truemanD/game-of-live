/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflive.screen;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author diyanov-a
 */
public class GameField extends JPanel {

    private static GameField INSTANCE;
    private List<List<Boolean>> field;
    private Dimension componentSize;
    private Dimension squareDimension;
    private double squareSize = 6;

    public static GameField getInstance(int newFieldSize, Dimension screenSize) {
        if (INSTANCE == null) {
            INSTANCE = new GameField(newFieldSize, screenSize);
        }
        return INSTANCE;
    }

    public static GameField getInstance(){
        return getInstance(0,new Dimension());
    }

    private GameField(int newFieldSize, Dimension screenSize) {
        initSquareSize(screenSize, newFieldSize);
        squareDimension = new Dimension((int) squareSize, (int) squareSize);
        componentSize = new Dimension(newFieldSize * squareDimension.width + 16, 38 + newFieldSize * squareDimension.height);
        setMinimumSize(componentSize);
        setBackground(Color.WHITE);
    }

    public void setField(List<List<Boolean>> field) {
        this.field = field;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        setBackground(Color.DARK_GRAY);
        for (int x = 0; x < field.size(); x++) {
            List<Boolean> row = field.get(x);
            for (int y = 0; y < row.size(); y++) {
                if (row.get(y)) {
                    g.setColor(Color.ORANGE);
                    g.fill3DRect(y * squareDimension.width, x * squareDimension.height, squareDimension.width , squareDimension.height,true);
                }
            }
        }
    }

     Dimension getComponentSize() {
        return componentSize;
    }

    private void initSquareSize(Dimension screenSize, int newFieldSize) {
        int screenHeight = screenSize.height - 100;
        squareSize = screenHeight / newFieldSize > 1 ? screenHeight / newFieldSize : 1;
    }


    double getSquareSize() {
        return squareSize;
    }

    public List<List<Boolean>> getField() {
        return field;
    }
}
