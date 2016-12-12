package ck.magicfilter;

import ck.magicfilter.statistic.CalculationResult;
import ck.magicfilter.statistic.StatisticElement;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class FilterResult {
    private String filterName;
    private String base64Image;
    private List<StatisticElement> statistics;

    public FilterResult() {

    }

    public FilterResult(String filterName, String base64Image) {
        this.filterName = filterName;
        this.base64Image = base64Image;
        this.statistics = new ArrayList<>();
    }

    public String getFilterName() {
        return filterName;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void addCalculation(CalculationResult calculationResult) {
        String[] labels = calculationResult.labels();
        double[] values = calculationResult.values();

        for (int i = 0; i < labels.length; i++) {
            this.statistics.add(new StatisticElement(labels[i], values[i]));
        }
    }

    public List<StatisticElement> getStatistics() {
        return this.statistics;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public void setStatistics(List<StatisticElement> statistics) {
        this.statistics = statistics;
    }
}
