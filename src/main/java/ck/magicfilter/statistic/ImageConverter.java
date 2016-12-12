package ck.magicfilter.statistic;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <p>Методи для створення даних для аналізу.</p>
 */
public class ImageConverter {
    /**
     * <p>Масив пікселей основного зображення.</p>
     */
    private int[][] pixelMain;
    /**
     * <p>Масив пікселей відфільтрованих зображень.</p>
     * <p>-зображення;<br>
     * -координата x;<br>
     * -координата y.</p>
     */
    private int[][][] pixelsFilter;
    /**
     * <p>Ширина зображення.</p>
     */
    private int width;
    /**
     * <p>Висота зображення.</p>
     */
    private int height;
    /**
     * <p>Кількість вхідних відфільтрованих зображень.</p>
     */
    private int quantityImage;
    /**
     * <p>Створює екземпляр ImageConverter.<br>
     * Критерій мінімуму квадрата середньоквадратичного відхилення помилки.</p>
     * @param imgMain основне зображення.
     * @param imgsFilter масив відфільтрованих зображень.
     */
    public ImageConverter(BufferedImage imgMain, BufferedImage[] imgsFilter) {
        width = imgMain.getWidth();
        height = imgMain.getHeight();
        quantityImage = imgsFilter.length;
        pixelMain = CreatePixelArray(width, height, imgMain);
        pixelsFilter = new int[quantityImage][][];
        for(int i = 0; i < quantityImage; ++i) {
            pixelsFilter[i] = CreatePixelArray(width, height, imgsFilter[i]);
        }
    }
    /**
     * <p>Створює масив, що складається з кольору кожного пікселя.</p>
     * @param w ширина вхідного зображення.
     * @param h висота вхідного зображення.
     * @param img вхідне зображення.
     * @return Масив (width на height) з кольором кожного пікселя.
     */
    private int[][] CreatePixelArray(int w, int h, BufferedImage img) {
        // Масив кольорів
        int[][] array = new int[h][w];
        // проходимо по кожному пікселю зображення
        for(int i = 0; i < h; ++i) {
            for(int j = 0; j < w; ++j) {
                // беремо колір пікселя
                array[i][j] = -new Color(img.getRGB(j, i)).getRGB();
            }
        }
        return array;
    }
    /**
     * <p>Повертає масив з кольором кожного пікселя основного зображення.</p>
     * @return Масив з кольором кожного пікселя основного зображення.
     */
    public int[][] getPixelMain() {
        return this.pixelMain;
    }
    /**
     * <p>Повертає масив двовимірних масивів з кольором кожного пікселя відфільтрованих зображень.</p>
     * @return Масив двовимірних масивів з колоьором кожного пікселя відфільрованих зображень.
     */
    public int[][][] getPixelsFilter() {
        return this.pixelsFilter;
    }
    /**
     * <p>Повертає ширину зображення.</p>
     * @return Ціле число, яке дорівнює ширині зображення.
     */
    public int getWidth() {
        return this.width;
    }
    /**
     * <p>Повертає висоту зображення.</p>
     * @return Ціле число, яке дорівнює висоті зображення.
     */
    public int getHeight() {
        return this.height;
    }
    /**
     * <p>Повертає кількість вхідних відфільтрованих зображень.</p>
     * @return Ціле число вхідних відфільтрованих зображень.
     */
    public int getQuantityImage() {
        return this.quantityImage;
    }
}