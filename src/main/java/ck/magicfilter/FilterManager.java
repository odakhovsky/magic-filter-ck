package ck.magicfilter;

import ck.magicfilter.filters.Filter;
import ck.magicfilter.filters.OloloFilter;
import ck.magicfilter.filters.TestFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class FilterManager {
    private Filter[] filters;

    public FilterManager() {
        this.filters = new Filter[]{new TestFilter(), new OloloFilter()};
    }

    public List<FilterResult> apply(MultipartFile image, String[] filterNames) {
        int length = !Objects.isNull(filterNames) ? filterNames.length : 0;
        List<String> filterNamesList = new ArrayList<>(Arrays.asList(filterNames));
        List<FilterResult> filterResults = new ArrayList<>(length);

        for (Filter filter : this.filters) {
            if (filterNamesList.contains(filter.name())) {
                filterResults.add(new FilterResult(filter.name(), filter.filter(image)));
            }
        }

        return filterResults;
    }
}
