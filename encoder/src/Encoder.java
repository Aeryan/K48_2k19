import java.awt.Color;
import java.awt.image.DataBufferByte;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Encoder {

    public static void main(String[] args) throws IOException{

        int[][] pixels;
        pixels = encode("tester.png");

        PrintWriter writer = new PrintWriter(new File("test.csv"));
        StringBuilder sb = new StringBuilder();
        String prefix;
        for (int[] i : pixels) {
            prefix = "";
            for (int j: i) {
                sb.append(prefix);
                prefix = ",";
                sb.append(j);
            }
            sb.append("\n");
        }

        writer.write(sb.toString());

    }

    // https://stackoverflow.com/a/9470843
    private static int[][] encode(String imagepath) throws IOException{

        File imagefile = new File("tester.png");
        BufferedImage image = ImageIO.read(imagefile);

        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        int[][] result = new int[height][width];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }

        return result;
    }

}
