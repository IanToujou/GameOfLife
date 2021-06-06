package net.toujoustudios.gameoflife.window;

import net.toujoustudios.gameoflife.main.GameOfLife;

import javax.swing.*;
import java.awt.*;

/**
 * This file was created by IanToujou.
 * Date: 06/06/2021
 * Time: 17:47
 * Project: GameOfLife
 */
public class GridPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics graphics) {

        int resolution = GameOfLife.getResolution();
        int rows = GameOfLife.getRows();
        int columns = GameOfLife.getColumns();
        int height = resolution * rows;
        int width = resolution * columns;

        super.paintComponent(graphics);

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        for(int i = 0; i < columns; i++) {
            for(int j = 0; j < rows; j++) {

                int x = j * resolution;
                int y = i * resolution;

                graphics.setColor(new Color(248, 30, 109));
                if(GameOfLife.getGrid()[i][j] == 1) graphics.fillRect(x, y, resolution, resolution);

                graphics.setColor(Color.BLACK);
                graphics.drawLine(x, y, x, height);
                graphics.drawLine(x, y, width, y);

            }
        }

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GameOfLife.getColumns() * GameOfLife.getResolution(), GameOfLife.getRows() * GameOfLife.getResolution());
    }

}
