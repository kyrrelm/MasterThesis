package utils;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Created by Kyrre on 15.12.2016.
 */
public class DataTransformer {

    static String path = "savedResults/step2/";

    static String value = "Time of completion:";

    public static void main(String[] args) throws IOException {


        try(Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    printOutput(filePath);
                }
            });
        }

    }

    public static void printOutput(Path path){
        try {
            Scanner input;
            File file = new File(path.toString());
            input = new Scanner(file);

            System.out.println(path.getFileName());

            while (input.hasNextLine()) {
                String line = input.nextLine();
                if (line.contains(value)){
                    String[] result = line.split(value);
                    System.out.println(result[result.length-1]);
                }
            }
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
