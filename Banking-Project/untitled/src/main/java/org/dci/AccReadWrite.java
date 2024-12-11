package org.dci;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

public class AccReadWrite {
    private ArrayList<CheckingAccount> accounts = new ArrayList<>();

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
                CheckingAccount currentAcc = new CheckingAccount(nextLine[0],
                        nextLine[1],
                        Float.parseFloat(nextLine[2]),
                        Integer.parseInt(nextLine[3]),
                        Integer.parseInt(nextLine[4]),
                        nextLine[5],
                        Boolean.parseBoolean(nextLine[6]),
                        Boolean.parseBoolean(nextLine[7]),
                        Float.parseFloat(nextLine[8]));
                accounts.add(currentAcc);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } {
            System.out.println("Finally reached");
        }
    }
    public ArrayList<CheckingAccount> getAccounts() {
        return accounts;
    }

    public void write(File file) throws Exception {


        try {
//          creates a filewrite object
            FileWriter outputfile = new FileWriter(file);
//          Opencsv writer with filewriter as input
            CSVWriter writer = new CSVWriter(outputfile, ';',CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            ArrayList<String[]> data = new ArrayList<>();
//          this should be refactored
            for(CheckingAccount account: accounts) {
                data.add(new String [] {
                        account.getUserName(),
                        account.getPassword(),
                        String.valueOf(account.getBalance()),
                        String.valueOf(account.getPin()),
                        String.valueOf(account.getId()),
                        account.getTypeOfAccount(),
                        String.valueOf(account.isCheckingAccount()),
                        String.valueOf(account.isActive()),
                        String.valueOf(account.getWithdrawLimit())
                });

            }
//            this writes the data all at once
            writer.writeAll(data);
//           closing writer
            writer.close();

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}