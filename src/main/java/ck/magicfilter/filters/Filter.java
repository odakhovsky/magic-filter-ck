package ck.magicfilter.filters;

import org.springframework.web.multipart.MultipartFile;

public interface Filter {
    String name();
    String filter(MultipartFile image);
}
