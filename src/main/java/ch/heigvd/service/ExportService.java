package ch.heigvd.service;

import ch.heigvd.dto.TemperatureMeasureDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.util.List;

@ApplicationScoped
public class ExportService {

    @Inject
    TemperatureService temperatureService;

    public byte[] exportTemperatureMeasure() {

        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ) {

            Sheet sheet = workbook.createSheet("Température");

            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle dataStyle = workbook.createCellStyle();
            //dataStyle.setWrapText(true);

            String[] headerValues = {"No", "id", "Date", "Lieu", "Auteur", "Degré"};
            Row header = sheet.createRow(0);
            for (int index = 0; index < headerValues.length; index++) {
                Cell cell = header.createCell(index);
                cell.setCellValue(headerValues[index]);
                cell.setCellStyle(headerStyle);
            }

            int rowIndex = 1;
            List<TemperatureMeasureDTO> measures = temperatureService.getAllTemperatureMeasures();
            for (TemperatureMeasureDTO measure : measures) {
                //sheet.setColumnWidth(rowIndex, headerValues.length);
                Row row = sheet.createRow(rowIndex);

                //No
                Cell cell0 = row.createCell(0);
                cell0.setCellStyle(dataStyle);
                cell0.setCellValue(rowIndex++);
                //id
                Cell cell1 = row.createCell(1);
                cell1.setCellValue(measure.id());
                cell1.setCellStyle(dataStyle);
                //Date
                Cell cell2 = row.createCell(2);
                cell2.setCellValue(measure.date());
                cell2.setCellStyle(dataStyle);
                //Lieu
                Cell cell3 = row.createCell(3);
                cell3.setCellValue(measure.location());
                cell3.setCellStyle(dataStyle);
                //Auteur
                Cell cell4 = row.createCell(4);
                cell4.setCellValue(measure.author());
                cell4.setCellStyle(dataStyle);
                //Degré
                Cell cell5 = row.createCell(5);
                cell5.setCellValue(measure.degree());
                cell5.setCellStyle(dataStyle);
            }
            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (Exception e) {
            return null;
        }

    }


    private CellStyle createHeaderStyle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);

        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);

        return style;
    }
    /*
    private CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        return style;
    }
     */
}
