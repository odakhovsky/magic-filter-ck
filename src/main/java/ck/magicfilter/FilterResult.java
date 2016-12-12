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
    private String[] labels;
    private double[] values;

    public FilterResult() {

    }

    public FilterResult(String filterName, String base64Image) {
        this.filterName = filterName;
        this.base64Image = base64Image;
    }

    public String getFilterName() {
        return filterName;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void addCalculation(CalculationResult calculationResult) {
        this.labels = calculationResult.labels();
        this.values = calculationResult.values();
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) {
        this.values = values;
    }
}
