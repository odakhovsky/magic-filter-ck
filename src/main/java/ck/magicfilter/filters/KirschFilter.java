package ck.magicfilter.filters;

import ck.magicfilter.Pair;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class KirschFilter implements Filter {

    private int[][] Kirschx = {
            {-1, 0, 1},
                    {-2, 0, 2},
                    {-1, 0, 1}};
    private int[][] Kirschy = {
            {1, 2, 1},
            {0, 0, 0},
            {-1, -2, -1}};

    public int lum(int r, int g, int b) {
        return (r + r + r + b + g + g + g + g) >> 3;
    }

    public int rgb_to_luminance(int rgb) {
        int r = (rgb & 0xff0000) >> 16;
        int g = (rgb & 0xff00) >> 8;
        int b = (rgb & 0xff);
        return lum(r, g, b);
    }

    public static int level_to_greyscale(int level) {
        return (level << 16) | (level << 8) | level;
    }

    public BufferedImage cloneImageGray(BufferedImage image) {
        return new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
    }

    public BufferedImage KirschOperator(MultipartFile multipartFile) throws IOException {
        int level;
        InputStream in = new ByteArrayInputStream(multipartFile.getBytes());
        BufferedImage image = ImageIO.read(in);
        BufferedImage ret = cloneImageGray(image);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                level = 255;
                if ((x > 0) && (x < (width - 1)) && (y > 0) && (y < (height - 1))) {
                    int sumX = 0;
                    int sumY = 0;
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            sumX += rgb_to_luminance(image.getRGB(x+i, y+j)) * Kirschx[i+1][j+1];
                            sumY += rgb_to_luminance(image.getRGB(x+i, y+j)) * Kirschy[i+1][j+1];
                        }
                    }
                    level = Math.abs(sumX) + Math.abs(sumY);
                    if (level < 0) {
                        level = 0;
                    } else if (level > 255) {
                        level = 255;
                    }
                    level = 255 - level;
                }
                ret.setRGB(x, y, level);
            }
        }
        return ret;
    }

    @Override
    public String name() {
        return "Kirsch_Filter";
    }

    @Override
    public Pair<String, BufferedImage> filter(MultipartFile image) {
        try {
            BufferedImage img = KirschOperator(image);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            return new Pair<String, BufferedImage>(Base64.encodeBase64String(imageInByte), img);
        } catch (IOException e) {
            return null;
        }
    }
}