package helpers;

import entity.Smartphone;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.LinkedList;
import java.util.Properties;

public class Utils {
    private static final org.slf4j.Logger Log = org.slf4j.LoggerFactory.getLogger("FILE");
    public static String url;
    public static String user;
    public static String password;

    public static void writeToFile(String text) {
        try (FileWriter writer = new FileWriter("report.txt", true)) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getConfig() {
        FileInputStream fis;
        Properties property = new Properties();
        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            try {
                property.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        user = property.getProperty("db_USER");
        password = property.getProperty("db_PASSWORD");
        url = property.getProperty("URL");
    }

    public static void deleteFile() {
        File file = new File("report.txt");
        if (file.delete()) {
            Log.info("File delete");
        } else {
            Log.error("Error file not delete");
        }
    }

    protected static LinkedList<String> readFile(String fileName) {
        LinkedList<String> emails = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                emails.add(sCurrentLine);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return emails;
    }

    public static void writeToExcel(LinkedList<Smartphone> itemsList, String nameSheet, LinkedList<Smartphone> itemsList2, String nameSheet2) {


        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheetTop = workbook.createSheet(nameSheet);
        XSSFSheet sheetSmartphone3000to6000 = workbook.createSheet(nameSheet2);
        int rownum = 0;
        for (Smartphone item : itemsList) {
            Row row = sheetTop.createRow(rownum++);
            row.createCell(0).setCellValue(item.getName());
            row.createCell(1).setCellValue(item.getPrice());
        }
        rownum = 0;
        for (Smartphone item : itemsList2) {
            Row row = sheetSmartphone3000to6000.createRow(rownum++);
            row.createCell(0).setCellValue(item.getName());
            row.createCell(1).setCellValue(item.getPrice());
        }

        try {
            FileOutputStream out = new FileOutputStream(new File("Test.xlsx"));
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
