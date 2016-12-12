package ck.magicfilter.statistic;

/**
 * Created by volodymyro on 13.12.2016.
 */
public class StatisticElement {
    private String label;
    private double value;

    public StatisticElement(String label, double val) {
        this.label = label;
        this.value = val;
    }

    public StatisticElement() {

    }

    public String getLabel() {
        return label;
    }

    public double getValue() {
        return value;
    }
}
