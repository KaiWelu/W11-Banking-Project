package org.dci;

import java.io.File;
import java.io.FileReader;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class AccountsReader {
// gets a file to read
    public void read(File file) throws Exception {
        CSVReader csvReader = null;
        String [] nextLine;

        try {
//            this builds a custom reader with ; as a separator
            csvReader = new CSVReaderBuilder(new FileReader(file)).
                    withCSVParser(new CSVParserBuilder()
                            .withSeparator(';').build())
                    .build();

            while ((nextLine = csvReader.readNext()) != null) {
                for(String output : nextLine) {
                    System.out.println("Entry:");
                    System.out.println(output);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } {
            System.out.println("Finally reached");
        }
    }

}
