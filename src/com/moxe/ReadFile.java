package com.moxe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ReadFile Model Object
 *
 * Takes care of reading in a text file and returns
 * each line in an ArrayList
 *
 * @author Eric Wallat
 */
public class ReadFile {

    /**
     * Reads the input file line by line.
     *
     * @param filePath  the absolute file path of the input file
     * @return ArrayList containing each line from the input text
     */
    public ArrayList<String> read(String filePath) {
        ArrayList<String> items = new ArrayList<>();
        File file = new File(filePath);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                items.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return items;
    }
}
