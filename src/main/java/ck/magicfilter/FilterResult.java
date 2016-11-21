package ck.magicfilter;

public class FilterResult {
    private String filterName;
    private String base64Image;

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
}
