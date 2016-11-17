package utils;
import sample.Settings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    public static void writeToUniqueFile(String output) {
        try {

            File dir = new File("results");

            dir.mkdir();

            File file = new File("results/"+Settings.MAP.name+".txt");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(output);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
