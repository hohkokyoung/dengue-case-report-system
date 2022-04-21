/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import classes.DengueCase;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsxReader {
    private FileInputStream fileInputStream;
    private XSSFWorkbook workbook;
    
    //A constructor to assign and initiate the reading of the excel file paths given.
    public XlsxReader(String filePath) {
        try {
                fileInputStream = new FileInputStream(filePath);
                workbook = new XSSFWorkbook(fileInputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    //A function specialsied in dissecting the excel file and return java data list.
    public List<DengueCase> readFile(int fileNth) {
        int startingIndex = fileNth == 1 ? 4 : 5;
        List<Integer> years = fileNth == 1 
                ? Arrays.asList(2014, 2015, 2016) 
                : Arrays.asList(2017, 2018, 2019);
        List<DengueCase> partialDengueCases = new ArrayList<>();
        for (Row row : workbook.getSheetAt(0)) {
            if (row.getRowNum() < startingIndex) continue;
            if (row.getCell(1).getStringCellValue().toLowerCase().contains("pahang")) continue;
            if (row.getCell(2) == null) continue;
            TreeMap<Integer, Integer> tempDengueCasesPerYear = new TreeMap<>();
            tempDengueCasesPerYear.put(years.get(0), (int) row.getCell(2).getNumericCellValue());
            tempDengueCasesPerYear.put(years.get(1), (int) row.getCell(3).getNumericCellValue());
            tempDengueCasesPerYear.put(years.get(2), (int) row.getCell(4).getNumericCellValue());
            partialDengueCases.add(
                new DengueCase(row.getCell(1).getStringCellValue().trim(), tempDengueCasesPerYear)
            );
        }
        return partialDengueCases;
    }
    
    //A function to combine the two partially read data from the two files into one single list.
    public List<DengueCase> combinePartialDengueCasesData
        (List<DengueCase> firstPartialDengueCases, List<DengueCase> secondPartialDengueCases) {
        for(int counter = 0; counter < firstPartialDengueCases.size(); counter++){
                firstPartialDengueCases.stream().collect(Collectors.toList())
                    .get(counter)
                    .getDengueCasePerYear()
                    .putAll(secondPartialDengueCases
                    .get(counter)
                    .getDengueCasePerYear());
        }

        return firstPartialDengueCases;
    }

}
