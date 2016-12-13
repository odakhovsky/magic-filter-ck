package ck.magicfilter.filters;

import ck.magicfilter.Pair;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;

public interface Filter {
    String name();
    Pair<String, BufferedImage> filter(MultipartFile image);
}
