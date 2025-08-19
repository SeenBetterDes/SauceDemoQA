package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    public static Object[][] getData(String excelFilePath, String sheetName) {
        List<Object[]> dataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) return new Object[0][0];

            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

            // Loop through all rows, starting from row 1 (skip header)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue; // skip completely empty row

                Cell firstCell = row.getCell(0); // assuming TestCaseID is in first column
                String testCaseId = getCellValueAsString(firstCell);

                if (testCaseId.isEmpty()) continue; // skip rows with empty TestCaseID

                Object[] rowData = new Object[colCount];
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);
                    rowData[j] = getCellValueAsString(cell);
                }
                dataList.add(rowData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert List<Object[]> to Object[][]
        Object[][] data = new Object[dataList.size()][];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i);
        }

        return data;
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
            default:
                return "";
        }
    }
}
