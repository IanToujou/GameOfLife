package net.toujoustudios.gameoflife.main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * This file was created by IanToujou.
 * Date: 04/06/2021
 * Time: 11:11
 * Project: GameOfLife
 */
public class Main {

    private final static String fileName = "export_image.png";

    private final static int columns = 20;
    private final static int rows = 20;
    private static int[][] grid;

    public static void main(String[] args) {

        grid = create2DArray(columns, rows);

        for(int i = 0; i < columns; i++) {
            for(int j = 0; j < rows; j++) {

                int random = new Random().nextInt(2);
                grid[i][j] = (int) Math.floor(random);

            }
        }

        String print = Arrays.deepToString(grid).replace("], ", "]\n");
        System.out.println(print);

        draw(columns, rows);

    }

    public static int[][] create2DArray(int columns, int rows) {

        return new int[columns][rows];

    }

    public static void draw(int width, int height) {

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);

        for(int i = 0; i < columns; i++) {
            for(int j = 0; j < rows; j++) {

                if(grid[i][j] == 1) graphics.drawRect(j, i, 0, 0);

            }
        }

        graphics.dispose();
        File file = new File(fileName);

        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

}
