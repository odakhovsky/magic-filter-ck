package ck.magicfilter.statistic;

import ck.magicfilter.statistic.ImageConverter;

import java.awt.image.BufferedImage;

/**
 * <p>Критерій нормалізованої кольорової відмінності.</p>
 */
public class NormalizeColorDifference extends ImageConverter {
    /**
     * <p>Створює екземпляр SquareRMSDeviation.<br>
     * Критерій мінімуму квадрата середньоквадратичного відхилення помилки.</p>
     * @param imgMain основне зображення.
     * @param imgsFilter масив відфільтрованих зображень.
     */
    public NormalizeColorDifference(BufferedImage imgMain, BufferedImage[] imgsFilter) {
        super(imgMain, imgsFilter);
    }
    /**
     * <p>Обчислюємо нормалізованої корльорової відмінності для кожного зображення.</p>
     * @return Масив з результатами нормалізованої корльорової відмінності.
     */
    public double[] Analyze() {
        int w = super.getWidth(), h = getHeight(), n = getQuantityImage();
        int[][] pixelMain = getPixelMain();
        int[][][] pixelsFilter = getPixelsFilter();
        // Масив з відповідями
        double[] result = new double[n];
        // Проходимо по формулі для n зображень:
        // sum(sqrt(sum(o-y)^2))/sum(sqrt(sum(o)^2))
        double o, y, num = 0, den = 0;
        for(int k = 0; k < n; ++k) {
            result[k] = 0;
            for(int i = 0; i < h; ++i) {
                for(int j = 0; j < w; ++j) {
                    // Сума модулів по rgb
                    o = RGBToVectorLengthXYZ((pixelMain[i][j] >> 16) & 255, (pixelMain[i][j] >> 8) & 255, pixelMain[i][j] & 255);
                    y = RGBToVectorLengthXYZ((pixelsFilter[k][i][j] >> 16) & 255, (pixelsFilter[k][i][j] >> 8) & 255, pixelsFilter[k][i][j] & 255);
                    // знаменник і чисельник
                    num += o - y;
                    den += o;
                }
            }
            // Нормалізація до компактних чисел (*100.)
            result[k] = num / (den == 0 ? 1 : den) * 100.;
        }
        return result;
    }
    /**
     * Обчислює довжину вектора RGB перетвореного в CIE Luv.
     * @param R red.
     * @param G green.
     * @param B blue.
     * @return Дійсний результат довжини вектора простору CIE Luv.
     */
    private double RGBToVectorLengthXYZ(int R, int G, int B) {
        // RGB -> XYZ
        // нормуємо R, G, B
        double _R = R / 255., _G = G / 255., _B = B / 255.;
        // Observer. = 2°, Illuminant = D65
        // Перетворення простору RGB в XYZ
        double X = _R * 0.4124 + _G * 0.3576 + _B * 0.1805;
        double Y = _R * 0.2126 + _G * 0.7152 + _B * 0.0722;
        double Z = _R * 0.0193 + _G * 0.1192 + _B * 0.9505;
        return VectorLength(X, Y, Z);
    }
    /**
     * Обчислює довжину вектора.
     * @param x перша координата.
     * @param y друга координата.
     * @param z третя координата.
     * @return Дійсний результат довжини вектора.
     */
    private double VectorLength(double x, double y, double z) {
        return Math.sqrt(x * x + y * y + z * z);
    }
}