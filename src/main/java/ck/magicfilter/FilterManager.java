package ck.magicfilter;

import ck.magicfilter.filters.Filter;
import ck.magicfilter.filters.OloloFilter;
import ck.magicfilter.filters.SobelFilter;
import ck.magicfilter.filters.TestFilter;
import ck.magicfilter.statistic.Calculate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class FilterManager {
    private Filter[] filters;

    public FilterManager() {
        this.filters = new Filter[]{new SobelFilter()};
    }

    public List<FilterResult> apply(MultipartFile image, String[] filterNames) throws IOException {
        int length = !Objects.isNull(filterNames) ? filterNames.length : 0;
        List<String> filterNamesList = new ArrayList<>(Arrays.asList(filterNames));
        List<FilterResult> filterResults = new ArrayList<>(length);
        BufferedImage[] filteredImages = new BufferedImage[length];

        int i = 0;
        for (Filter filter : this.filters) {
            if (filterNamesList.contains(filter.name())) {
                FilterResult filterResult = new FilterResult(filter.name(), filter.filter(image));
                filterResults.add(filterResult);

                filteredImages[i] = getFromBase64(filterResult.getBase64Image());
                i++;
            }
        }



        Calculate calculate = new Calculate(getFromMultipart(image), filteredImages);

        System.out.println(calculate.getResultString());
        return filterResults;
    }

    public BufferedImage getFromBase64(String base64Image) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decodedBytes = decoder.decodeBuffer(base64Image);
        return ImageIO.read(new ByteArrayInputStream(decodedBytes));
    }

    public BufferedImage getFromMultipart(MultipartFile multipartFile) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(multipartFile.getBytes()));
    }
}
