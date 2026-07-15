package ch.heigvd.service;

import ch.heigvd.dto.BirdMigrationMeasureDTO;
import ch.heigvd.dto.EggsLayingMeasureDTO;
import ch.heigvd.dto.SnowHeightMeasureDTO;
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

    @Inject
    SnowHeightService snowHeightService;

    @Inject
    BirdMigrationService birdMigrationService;

    @Inject
    EggsLayingService eggsLayingService;

    public byte[] exportTemperatureMeasures() {

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

    public byte[] exportSnowHeightMeasures() {

        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ) {

            Sheet sheet = workbook.createSheet("Hauteur des neiges");

            CellStyle headerStyle = createHeaderStyle(workbook);

            String[] headerValues = {"No", "id", "Date", "Lieu", "Auteur", "Hauteur de la neige", "Conditions météo", "Précipitations"};
            Row header = sheet.createRow(0);
            for (int index = 0; index < headerValues.length; index++) {
                Cell cell = header.createCell(index);
                cell.setCellValue(headerValues[index]);
                cell.setCellStyle(headerStyle);
            }

            int rowIndex = 1;
            List<SnowHeightMeasureDTO> measures = snowHeightService.getAllSnowHeightMeasure();
            for (SnowHeightMeasureDTO measure : measures) {
                Row row = sheet.createRow(rowIndex);

                //No
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(rowIndex++);
                //id
                Cell cell1 = row.createCell(1);
                cell1.setCellValue(measure.id());
                //Date
                Cell cell2 = row.createCell(2);
                cell2.setCellValue(measure.date());
                //Lieu
                Cell cell3 = row.createCell(3);
                cell3.setCellValue(measure.location());
                //Auteur
                Cell cell4 = row.createCell(4);
                cell4.setCellValue(measure.author());
                //Hauteur
                Cell cell5 = row.createCell(5);
                cell5.setCellValue(measure.height());
                //Conditions météo
                Cell cell6 = row.createCell(6);
                cell6.setCellValue(measure.weather().getLabel());
                //Precipitations
                Cell cell7 = row.createCell(7);
                cell7.setCellValue(measure.precipitation());
            }
            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (Exception e) {
            return null;
        }
    }

    public byte[] exportBirdMigrationMeasures() {
        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ) {

            Sheet sheet = workbook.createSheet("Hauteur des neiges");

            CellStyle headerStyle = createHeaderStyle(workbook);

            String[] headerValues = {"No", "id", "Date", "Lieu", "Auteur", "Espèce d'oiseau", "Evénement"};
            Row header = sheet.createRow(0);
            for (int index = 0; index < headerValues.length; index++) {
                Cell cell = header.createCell(index);
                cell.setCellValue(headerValues[index]);
                cell.setCellStyle(headerStyle);
            }

            int rowIndex = 1;
            List<BirdMigrationMeasureDTO> measures = birdMigrationService.getAllBirdMigrationMeasures();
            for (BirdMigrationMeasureDTO measure : measures) {
                Row row = sheet.createRow(rowIndex);

                //No
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(rowIndex++);
                //id
                Cell cell1 = row.createCell(1);
                cell1.setCellValue(measure.id());
                //Date
                Cell cell2 = row.createCell(2);
                cell2.setCellValue(measure.date());
                //Lieu
                Cell cell3 = row.createCell(3);
                cell3.setCellValue(measure.location());
                //Auteur
                Cell cell4 = row.createCell(4);
                cell4.setCellValue(measure.author());
                //Espèce
                Cell cell5 = row.createCell(5);
                cell5.setCellValue(measure.specie().getLabel());
                //Evenement
                Cell cell6 = row.createCell(6);
                cell6.setCellValue(measure.event().getLabel());
            }
            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (Exception e) {
            return null;
        }
    }

    public byte[] exportEggsLayingMeasures() {
        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ) {

            Sheet sheet = workbook.createSheet("Hauteur des neiges");

            CellStyle headerStyle = createHeaderStyle(workbook);

            String[] headerValues = {"No", "id", "Date", "Lieu", "Auteur", "Nombre de pontes"};
            Row header = sheet.createRow(0);
            for (int index = 0; index < headerValues.length; index++) {
                Cell cell = header.createCell(index);
                cell.setCellValue(headerValues[index]);
                cell.setCellStyle(headerStyle);
            }

            int rowIndex = 1;
            List<EggsLayingMeasureDTO> measures = eggsLayingService.getAllEggsLayingMeasure();
            for (EggsLayingMeasureDTO measure : measures) {
                Row row = sheet.createRow(rowIndex);

                //No
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(rowIndex++);
                //id
                Cell cell1 = row.createCell(1);
                cell1.setCellValue(measure.id());
                //Date
                Cell cell2 = row.createCell(2);
                cell2.setCellValue(measure.date());
                //Lieu
                Cell cell3 = row.createCell(3);
                cell3.setCellValue(measure.location());
                //Auteur
                Cell cell4 = row.createCell(4);
                cell4.setCellValue(measure.author());
                //Nombre de pontes
                Cell cell5 = row.createCell(5);
                cell5.setCellValue(measure.number());
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
}
