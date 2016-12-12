package ck.magicfilter.statistic;

import java.awt.image.BufferedImage;

/**
 * <p>Критерій мінімуму квадрата середньоквадратичного відхилення помилки.</p>
 */
public class SquareRMSDeviation extends ImageConverter {
    /**
     * <p>Створює екземпляр SquareRMSDeviation.<br>
     * Критерій мінімуму квадрата середньоквадратичного відхилення помилки.</p>
     * @param imgMain основне зображення.
     * @param imgsFilter масив відфільтрованих зображень.
     */
    public SquareRMSDeviation(BufferedImage imgMain, BufferedImage[] imgsFilter) {
        super(imgMain, imgsFilter);
    }
    /**
     * <p>Обчислює мінімум квадрата середньоквадратичного відхилення для кожного зображення.</p>
     * @return Масив з результатами обчислення мінімуму квадрата середньоквадратичного відхилення.
     */
    public double[] Analyze() {
        int w = super.getWidth(), h = getHeight(), n = getQuantityImage();
        int[][] pixelMain = getPixelMain();
        int[][][] pixelsFilter = getPixelsFilter();
        // Масив з відповідями
        double[] result = new double[n];
        // Проходимо по формулі для n зображень:
        // sqrt(1 / N * sum(y' - y)^2)
        for(int k = 0; k < n; ++k) {
            result[k] = 0;
            for(int i = 0; i < h; ++i) {
                for(int j = 0; j < w; ++j) {
                    // Сума квадратів: ((y'-y)^2)
                    result[k] += Math.pow((pixelsFilter[k][i][j] - pixelMain[i][j]), 2);
                }
            }
            // Корінь з 1/N помноженого на суму квадратів
            // Нормалізація до компактних чисел (/100000.)
            result[k] = Math.sqrt(1. / (h * w) * result[k]) / 100000.;
        }
        return result;
    }
}