package net.toujoustudios.gameoflife.main;

import net.toujoustudios.gameoflife.log.LogLevel;
import net.toujoustudios.gameoflife.log.Logger;
import net.toujoustudios.gameoflife.util.GIFSequenceWriter;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This file was created by IanToujou.
 * Date: 04/06/2021
 * Time: 11:11
 * Project: GameOfLife
 */
public class Main {

    private final static String imageFile = "export_image_GEN.png";
    private final static String gifFile = "export.gif";
    private static int resolution = 10;
    private static int maxGeneration = 100;
    private static int columns = 100;
    private static int rows = 100;

    private static int[][] grid;
    private static int generation;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Logger.log(LogLevel.INFORMATION, "Please enter the amount of columns and rows. (Default: 100)");
        int input = 100;
        try {
            input = scanner.nextInt();
        } catch(Exception exception) {
            Logger.log(LogLevel.ERROR, "The input value is not an integer. Using the default value.");
        }
        columns = input;
        rows = input;

        Logger.log(LogLevel.INFORMATION, "Please specify up to which generation it should be calculated. (Default: 100)");
        try {
            maxGeneration = scanner.nextInt();
        } catch(Exception exception) {
            Logger.log(LogLevel.ERROR, "The input value is not an integer. Using the default value.");
        }

        Logger.log(LogLevel.INFORMATION, "Please specify the resolution of one single cell. (Default: 10)");
        try {
            resolution = scanner.nextInt();
        } catch(Exception exception) {
            Logger.log(LogLevel.ERROR, "The input value is not an integer. Using the default value.");
        }

        scanner.close();

        grid = create2DArray(columns, rows);

        for(int i = 0; i < columns; i++) {
            for(int j = 0; j < rows; j++) {

                int random = new Random().nextInt(2);
                grid[i][j] = (int) Math.floor(random);

            }
        }

        Logger.log(LogLevel.INFORMATION, "Columns: " + columns + " | Rows: " + rows + " | Resolution: " + resolution);
        Logger.log(LogLevel.INFORMATION, "Starting simulation up to generation " + maxGeneration + "...");
        start();

    }

    public static void start() {

        draw(columns * resolution, rows * resolution);
        generation = 0;

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {

                if(generation >= maxGeneration) {
                    try {
                        createGIF(gifFile);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                }

                Logger.log(LogLevel.INFORMATION, "Calculating and drawing generation " + generation + "...");

                int[][] nextGrid = create2DArray(columns, rows);

                for(int i = 0; i < columns; i++) {

                    for(int j = 0; j < rows; j++) {

                        //Ignore edges
                        if(i == 0 || i == (columns - 1) || j == 0 || j == (rows - 1)) {

                            nextGrid[i][j] = grid[i][j];

                        } else {

                            int neighbors = countNeighbors(grid, i, j);
                            int state = grid[i][j];

                            if(state == 0 && neighbors == 3) {
                                nextGrid[i][j] = 1;
                            } else if(state == 1 && neighbors < 2 || neighbors > 3) {
                                nextGrid[i][j] = 0;
                            } else {
                                nextGrid[i][j] = grid[i][j];
                            }

                        }

                    }

                }

                grid = nextGrid;
                generation++;
                draw(columns * resolution, rows * resolution);

            }

        };

        timer.schedule(task, 3000, 100);

    }

    public static int[][] create2DArray(int columns, int rows) {

        return new int[columns][rows];

    }

    public static void draw(int width, int height) {

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        for(int i = 0; i < columns; i++) {
            for(int j = 0; j < rows; j++) {

                int x = j * resolution;
                int y = i * resolution;

                graphics.setColor(new Color(248, 30, 109));
                if(grid[i][j] == 1) graphics.fillRect(x, y, resolution, resolution);

                graphics.setColor(Color.BLACK);
                graphics.drawLine(x, y, x, height);
                graphics.drawLine(x, y, width, y);

            }
        }

        graphics.dispose();
        File file = new File(imageFile.replace("GEN", String.valueOf(generation)));

        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public static int countNeighbors(int[][] grid, int x, int y) {

        int sum = 0;

        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                sum += grid[x + i][y + j];
            }
        }

        sum -= grid[x][y];
        return sum;

    }

    public static void createGIF(String fileName) throws IOException {

        Logger.log(LogLevel.INFORMATION, "Generating a gif out of the PNG sequence...");

        File file = new File(fileName);
        BufferedImage first = ImageIO.read(new File(imageFile.replace("GEN", "0")));
        ImageOutputStream outputStream = new FileImageOutputStream(file);

        GIFSequenceWriter sequenceWriter = new GIFSequenceWriter(outputStream, first.getType(), 1, true);

        sequenceWriter.writeToSequence(first);
        for(int i = 0; i < generation; i++) {

            Logger.log(LogLevel.INFORMATION, "Generating frame " + i + "/" + generation + "...");
            BufferedImage nextImage = ImageIO.read(new File("export_image_" + i + ".png"));
            sequenceWriter.writeToSequence(nextImage);

        }

        sequenceWriter.close();
        outputStream.close();

        Logger.log(LogLevel.INFORMATION, "GIF generation finished.");

    }

}
