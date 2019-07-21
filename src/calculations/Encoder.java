package calculations;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Encoder {

    public static void main(String[] args) throws IOException{

        double[][] pixels;
        pixels = encode("map_computer.png");

        PrintWriter writer = new PrintWriter(new File("map.csv"));
        StringBuilder sb = new StringBuilder();
        String prefix;
        for (double[] i : pixels) {
            prefix = "";
            for (double j: i) {
                sb.append(prefix);
                prefix = ",";
                sb.append(j);
            }
            sb.append("\n");
        }

        writer.write(sb.toString());

    }

    // https://stackoverflow.com/a/9470843
    private static double[][] encode(String imagepath) throws IOException{

        File imagefile = new File(imagepath);
        BufferedImage image = ImageIO.read(imagefile);

        final int width = image.getWidth();
        final int height = image.getHeight();

        HashMap<String, Double> colorMap = new HashMap<>();
        colorMap.put("#22b14c", 7.); // mets
        colorMap.put("#c3c3c3", 4.); // lageala
        colorMap.put("#ffc90e", 1.); // sillutatud tee
        colorMap.put("#a349a4", 1.2); // kruusatee
        colorMap.put("#ed1c24", 50.); // hoone
        colorMap.put("#00a2e8", 30.); // vesi
        colorMap.put("#b5e61d", 9.); // võsa

        double[][] result = new double[height][width];

        for (int i = 0; i < height; i ++) {
            for (int j = 0; j < width; j ++) {
                int rgb = image.getRGB(j, i);
                String hex = String.format("#%02x%02x%02x",
                        (rgb >> 16) & 0x000000FF, (rgb >>8 ) & 0x000000FF,
                        rgb & 0x000000FF);
                result[i][j] = colorMap.get(hex);
            }
        }

        return result;
    }

}