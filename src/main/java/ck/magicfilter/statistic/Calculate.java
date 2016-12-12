package ck.magicfilter.statistic;

import java.awt.image.BufferedImage;

/**
 * <p>Обчислення всіх значень критеріїв.</p>
 */
public class Calculate implements CalculationResult{
    /**
     * <p>Нормалізовані дані обрахованих критеріїв.</p>
     */
    private double[] resultDouble;
    private String[] resultString;
    /**
     * <p>Створює еземпляр класу Calculate і обраховує значення критеріїв.</p>
     * @param imgMain основне зображення.
     * @param imgsFilter масив відфільтрованих зображень.
     */
    public Calculate(BufferedImage imgMain, BufferedImage[] imgsFilter) {
        SquareRMSDeviation statistic1 = new SquareRMSDeviation(imgMain, imgsFilter);
        AverageAbsoluteError statistic2 = new AverageAbsoluteError(imgMain, imgsFilter);
        NormalizeColorDifference statistic3 = new NormalizeColorDifference(imgMain, imgsFilter);
        resultDouble = Join(Join(statistic1.Analyze(), statistic2.Analyze()), statistic3.Analyze());
        ValueNormalization normalize = new ValueNormalization(resultDouble);
        resultDouble = normalize.Normalize();
        setResultString(imgsFilter.length);
    }
    /**
     * <p>З'єднує два масиви в один.</p>
     * @param array1
     * @param array2
     * @return
     */
    private double[] Join(double[] array1, double[] array2) {
        int size1 = array1.length, size2 = array2.length;
        double[] result = new double[size1 + size2];
        int i = 0;
        for(; i < size1; ++i) {
            result[i] = array1[i];
        }
        for(; i < size1 + size2; ++i) {
            result[i] = array2[i - size1];
        }
        return result;
    }
    /**
     * <p>Повертає масив із значення обрахованих критеріїв.</p>
     * @return Масив із значення обрахованих критеріїв.
     */
    public double[] getResultDouble() {
        return this.resultDouble;
    }
    /**
     * <p>Створює масив з підписами до значень критеріїв.</p>
     * @param size Кількість вхідни відфільтрованих зображень.
     */
    private void setResultString(int size) {
        // *3 тому що використовуємо три критерії помилки
        resultString = new String[size * 3];
        String[] error = new String[3];
        /*error[0] = "Критерій мінімуму квадрата середньоквадратичного відхилення помилки";
        error[1] = "Критерій середньої абсолютної помилки";
        error[2] = "Критерій нормалізованої кольорової відмінності";*/
        error[0] = "КМКСВП";
        error[1] = "КСАП";
        error[2] = "КНКВ";
        int c = 0;
        for(int i = 0; i < size; ++i) {
            for (int j = 0; j < 3; ++j) {
                resultString[i * 2 + i + j] = error[c];
            }
            ++c;
        }
    }
    /**
     * <p>Повертає масив з підписами до кожного критерія.</p>
     * @return Масив з підписами до кожного критерія.
     */
    public String[] getResultString() {
        return this.resultString;
    }

    @Override
    public String[] labels() {
        return resultString;
    }

    @Override
    public double[] values() {
        return resultDouble;
    }
}
