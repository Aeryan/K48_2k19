package calculations;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Encoder {

    public static void main(String[] args) throws IOException{

        double[][] pixels;
        pixels = encode("kaart.png");

        PrintWriter writer = new PrintWriter(new File("kaart.csv"));
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

        final int width = image.getWidth()/2;
        final int height = image.getHeight()/2;

        HashMap<String, Double> colorMap = new HashMap<>();
        colorMap.put("#a0c8e8", 50.);    // vesi
        colorMap.put("#d0d8a0", 7.);     // mets
        colorMap.put("#f0e8c0", 4.);     // lage
        colorMap.put("#c83840", 100.);   // hoone/õu
        colorMap.put("#508050", 6.);     // salu/metsane
        colorMap.put("#000000", 1.2);    // norm tee
        colorMap.put("#d860d0", 1.1);    // vilu tee
        colorMap.put("#d89850", 1.);     // suur tee
        colorMap.put("#d8b858", 1.3);    // veel keskmised teed, normist 1 tase all
        colorMap.put("#509464", 30.);    // soo 2 (raba)
        colorMap.put("#70c0d4", 25.);    // soo 1 (madalsoo)

        double[][] result = new double[height][width];

        for (int i = 0; i < height; i += 2) {
            for (int j = 0; j < width; j += 2) {
                int rgb = image.getRGB(j, i);
                String hex = String.format("#%02x%02x%02x",
                        (rgb >> 16) & 0x000000FF, (rgb >>8 ) & 0x000000FF,
                        rgb & 0x000000FF);
                if(!colorMap.containsKey(hex))
                    result[i/2][j/2] = 0;
                else result[i/2][j/2] = colorMap.get(hex);
            }
        }

        return result;
    }

}