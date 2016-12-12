package ck.magicfilter.statistic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by lefka on 04.12.2016.
 */
public class Main {
    public void main (String[] args) throws IOException {
        BufferedImage imgMain = ImageIO.read(new File("img//original//img1.png"));
        BufferedImage[] imgsFilter = new BufferedImage[3];
        imgsFilter[0] = ImageIO.read(new File("img//pervita//img1.png"));
        imgsFilter[1] = ImageIO.read(new File("img//roberts//img1.png"));
        imgsFilter[2] = ImageIO.read(new File("img//sobela//img1.png"));
        Calculate calculate = new Calculate(imgMain, imgsFilter);
        double[] resDob = calculate.getResultDouble();
        String[] resStr = calculate.getResultString();

        for(double el : resDob) {
            System.out.println(el);
        }
        for(String el : resStr) {
            System.out.println(el);
        }
        /*
        resDob:                 resStr:                                                                 Картинка:
        0.9199154720677186      Критерій мінімуму квадрата середньоквадратичного відхилення помилки     1(pervita)
        0.49404357112417985     Критерій мінімуму квадрата середньоквадратичного відхилення помилки     2(roberts)
        0.9192527537820754      Критерій мінімуму квадрата середньоквадратичного відхилення помилки     3(sobela)
        0.15777210248897977     Критерій середньої абсолютної помилки                                   1(pervita)
        0.0                     Критерій середньої абсолютної помилки                                   2(roberts)
        0.15996701475793496     Критерій середньої абсолютної помилки                                   3(sobela)
        1.0                     Критерій нормалізованої кольорової відмінності                          1(pervita)
        0.9989913082490012      Критерій нормалізованої кольорової відмінності                          2(roberts)
        0.9992991115677649      Критерій нормалізованої кольорової відмінності                          3(sobela)
        */
    }
}