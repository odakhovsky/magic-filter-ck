package ck.magicfilter.filters;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;

public interface Filter {
    String name();
    SobelFilter.Pair<String, BufferedImage> filter(MultipartFile image);
}
