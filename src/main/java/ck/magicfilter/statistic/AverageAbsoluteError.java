package ck.magicfilter.statistic;

import java.awt.image.BufferedImage;

/**
 * <p>Критерій середньої абсолютної помилки.</p>
 */
public class AverageAbsoluteError extends ImageConverter {
    /**
     * <p>Створює екземпляр AverageAbsoluteError.<br>
     * Критерій середньої абсолютної помилки.</p>
     * @param imgMain основне зображення.
     * @param imgsFilter масив відфільтрованих зображень.
     */
    public AverageAbsoluteError(BufferedImage imgMain, BufferedImage[] imgsFilter) {
        super(imgMain, imgsFilter);
    }
    /**
     * <p>Обчислює середньої абсолютної помилки для кожного зображення.</p>
     * @return Масив з результатами обчислення середньої абсолютної помилки.
     */
    public double[] Analyze() {
        int w = super.getWidth(), h = getHeight(), n = getQuantityImage();
        int[][] pixelMain = getPixelMain();
        int[][][] pixelsFilter = getPixelsFilter();
        // Масив з відповідями
        double[] result = new double[n];
        // Проходимо по формулі для n зображень:
        // 1 / N * sum(abs(y'(rgb) - y(rgb)))
        for(int k = 0; k < n; ++k) {
            result[k] = 0;
            for(int i = 0; i < h; ++i) {
                for(int j = 0; j < w; ++j) {
                    // Сума модулів по rgb каналах: sum(abs(y'(rgb) - y(rgb)))
                    result[k] += Math.abs(((-pixelMain[i][j] >> 16) & 0xff) - ((-pixelsFilter[k][i][j] >> 16) & 0xff));
                    result[k] += Math.abs(((-pixelMain[i][j] >> 8) & 0xff) - ((-pixelsFilter[k][i][j] >> 8) & 0xff));
                    result[k] += Math.abs(((-pixelMain[i][j]) & 0xff) - ((-pixelsFilter[k][i][j]) & 0xff));
                }
            }
            // Корінь з 1/N помноженого на суму
            // Нормалізація до компактних чисел (/10.)
            result[k] = 1. / (h * w) * result[k] / 10.;
        }
        return result;
    }
}