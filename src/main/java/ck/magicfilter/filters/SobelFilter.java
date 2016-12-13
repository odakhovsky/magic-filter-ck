package ck.magicfilter.filters;

import ck.magicfilter.Pair;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SobelFilter implements Filter {

    private int width, height;

    @Override
    public String name() {
        return "Sobel-Filter";
    }

    @Override
    public Pair<String, BufferedImage> filter(MultipartFile image) {
        try {
            BufferedImage img = applyFilter(image);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            return new Pair<String, BufferedImage>(Base64.encodeBase64String(imageInByte), img);
        } catch (IOException e) {
            return null;
        }
    }

    private BufferedImage applyFilter(MultipartFile multipartFile) {
        try {
            InputStream in = new ByteArrayInputStream(multipartFile.getBytes());
            BufferedImage bImageFromConvert = ImageIO.read(in);
            width = bImageFromConvert.getWidth();
            height = bImageFromConvert.getHeight();
            return edgeDetection(bImageFromConvert);

        } catch (IOException e) { /* DO NOTHING */ }

        return null;
    }

    /* NPU APPROXIMATION START */
    private double sobel(double[][] window) {
        double x, y, r = 0;
        x = (window[0][0] + 2 * window[0][1] + window[0][2]);
        x -= (window[2][0] + 2 * window[2][1] + window[2][2]);
        y = (window[0][2] + 2 * window[1][2] + window[2][2]);
        y -= (window[0][0] + 2 * window[1][1] + window[2][0]);
        r = Math.sqrt((x * x) + (y * y));
        if (r > 255.0) r = 0;
        return r;
    }
    /* NPU APPROXIMATION END */

    private void printRGB(int clr) {
        int red = (clr & 0x00FF0000) >> 16;
        int green = (clr & 0x0000FF00) >> 8;
        int blue = (clr & 0x000000FF);
        System.out.println("Red: " + red + " Green: " + green + " Blue: " + blue);
    }

    private int getGreyScale(int clr) {
        int red = (clr & 0x00FF0000) >> 16;
        int green = (clr & 0x0000FF00) >> 8;
        int blue = (clr & 0x000000FF);
        if ((red == blue) && (blue == green)) return red;
        else return -1;
    }

    private int setGreyScaleValue(int clr) {
        return (clr) + (clr << 8) + (clr << 16);
    }

    private double[][] buildWindow(int x, int y, BufferedImage srcImg) {
        double[][] retVal = new double[3][3];
        for (int ypos = -1; ypos <= 1; ypos++) {
            for (int xpos = -1; xpos <= 1; xpos++) {
                int currX = xpos + x;
                int currY = ypos + y;
                if ((currX >= 0 && currX < width) && (currY >= 0 && currY < height)) {
                    int rgbRawValue = srcImg.getRGB(currX, currY);
                    int grayScaleValue = getGreyScale(rgbRawValue);
                    retVal[xpos + 1][ypos + 1] = grayScaleValue;
                } else
                    retVal[xpos + 1][ypos + 1] = 255;
            }
        }
        return retVal;
    }

    private BufferedImage edgeDetection(BufferedImage srcImg) {
        BufferedImage retVal = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        double[][] window = new double[3][3];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                window = buildWindow(x, y, srcImg);
                // double newValue = sobel(window);
                double newValue = srcImg.getRGB(x, y);
                double sobelValue = sobel(window);
                int sobelRGBValue = setGreyScaleValue((int) sobelValue);
                int grayScaleMag = getGreyScale((int) newValue);
                int greyscaleValue = setGreyScaleValue(grayScaleMag);
                retVal.setRGB(x, y, sobelRGBValue);
            }
        }
        return retVal;
    }


}