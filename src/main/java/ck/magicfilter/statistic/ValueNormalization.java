package ck.magicfilter.statistic;

/**
 * <p>Нормалізація значень в масиві від 0 до 1.</p>
 */
public class ValueNormalization {
    /**
     * <p>Масив для нормування.</p>
     */
    private double[] valueArray;
    /**
     * <p>Розмір масивудля нормування.</p>
     */
    private int sizeArray;
    /**
     * <p>Створює екземпляр Value ValueNormalization.<br>
     * Нормалізація значень в масиві від 0 до 1.</p>
     * @param inputValue Масив значень для нормаування.
     */
    public ValueNormalization(double[] inputValue) {
        valueArray = inputValue;
        sizeArray = inputValue.length;
    }
    /**
     * <p>Нормалізація масиву в проміжку від 0 до 1.</p>
     * @return Нормалізований масив.
     */
    public double[] Normalize() {
        double min = Min(), max = Max();
        for(int i = 0; i < sizeArray; ++i) {
            valueArray[i] = (valueArray[i] - min) / (max - min);
        }
        return valueArray;
    }
    /**
     * <p>Знаходить мінімальне значення вхідного масиву.</p>
     * @return Мінімальне значення вхідного масиву.
     */
    private double Min() {
        double min = valueArray[0];
        for(int i = 1; i < sizeArray; ++i) {
            if(valueArray[i] < min) {
                min = valueArray[i];
            }
        }
        return min;
    }
    /**
     * <p>Знаходить максимальне значення вхідного масиву.</p>
     * @return Максимальне значення вхідного  масиву.
     */
    private double Max() {
        double max = valueArray[0];
        for(int i = 1; i < sizeArray; ++i) {
            if(valueArray[i] > max) {
                max = valueArray[i];
            }
        }
        return max;
    }
}