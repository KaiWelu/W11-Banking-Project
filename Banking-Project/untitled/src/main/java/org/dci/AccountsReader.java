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
                CheckingAccount testAccount = new CheckingAccount(nextLine[0],
                        nextLine[1],
                        Float.parseFloat(nextLine[2]),
                        Integer.parseInt(nextLine[3]),
                        Integer.parseInt(nextLine[4]),
                        nextLine[5],
                        Boolean.parseBoolean(nextLine[6]),
                        Boolean.parseBoolean(nextLine[7]),
                        Float.parseFloat(nextLine[8]));
                System.out.println(testAccount.toString());
//                System.out.println(nextLine[0]);
//                for(String output : nextLine) {
//                    System.out.println("Entry:");
//                    System.out.println(output);
//                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } {
            System.out.println("Finally reached");
        }
    }

}
