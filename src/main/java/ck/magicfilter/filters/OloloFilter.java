package ck.magicfilter.filters;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class OloloFilter implements Filter {

    @Override
    public String name() {
        return "ololo_filter";
    }

    @Override
    public String filter(MultipartFile image) {
        try {
            return Base64.encodeBase64String(image.getBytes());
        } catch (IOException e) {
            return "";
        }
    }
}
