package ch.heigvd.service;

import ch.heigvd.dto.BirdMigrationMeasureDTO;
import ch.heigvd.dto.EggsLayingMeasureDTO;
import ch.heigvd.dto.SnowHeightMeasureDTO;
import ch.heigvd.dto.TemperatureMeasureDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

/**
 * Service to convert the data into Excel files.
 */
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

    /**
     * Export all Temperature Measure into an Excel file.
     * @return a byte array containing the generated Excel file.
     */
    public byte[] exportTemperatureMeasures() {

        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ) {
            List<TemperatureMeasureDTO> measures = temperatureService.getAllTemperatureMeasures();

            Sheet sheet = workbook.createSheet("Température");
            String[] headerValues = {"No", "id", "Date", "Lieu", "Auteur", "Degré (°C)"};
            createTitle(sheet, headerValues.length, "Température");
            createHeaderInfo(sheet, headerValues.length, measures.size());
            createHeader(sheet, headerValues);

            int rowIndex = 3;
            int no = 1;
            for (TemperatureMeasureDTO measure : measures) {
                Row row = sheet.createRow(rowIndex++);

                //No
                createNumericCell(row, 0, no++);
                //id
                createNumericCell(row, 1, measure.id());
                //Date
                createDateCell(row, 2, measure.date());
                //Lieu
                createTextCell(row, 3, measure.location());
                //Auteur
                createTextCell(row, 4, measure.author());
                //Degré
                createNumericCell(row, 5, measure.degree());
            }

            formatSheet(sheet, headerValues.length, rowIndex);

            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Export all SnowHeight Measure into an Excel file.
     * @return a byte array containing the generated Excel file.
     */
    public byte[] exportSnowHeightMeasures() {

        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ) {
            List<SnowHeightMeasureDTO> measures = snowHeightService.getAllSnowHeightMeasure();

            Sheet sheet = workbook.createSheet("Hauteur des neiges");
            String[] headerValues = {"No", "id", "Date", "Lieu", "Auteur", "Hauteur de la neige", "Conditions météo", "Précipitations"};
            createTitle(sheet, headerValues.length, "Hauteur des neiges");
            createHeaderInfo(sheet, headerValues.length, measures.size());
            createHeader(sheet, headerValues);

            int rowIndex = 3;
            int no = 1;
            for (SnowHeightMeasureDTO measure : measures) {
                Row row = sheet.createRow(rowIndex++);

                //No
                createNumericCell(row, 0, no++);
                //id
                createNumericCell(row, 1, measure.id());
                //Date
                createDateCell(row, 2, measure.date());
                //Lieu
                createTextCell(row, 3, measure.location());
                //Auteur
                createTextCell(row, 4, measure.author());
                //Hauteur
                createNumericCell(row, 5, measure.height());
                //Conditions météo
                createTextCell(row, 6, measure.weather().getLabel());
                //Precipitations
                createNumericCell(row, 7, measure.precipitation());
            }

            formatSheet(sheet, headerValues.length, rowIndex);


            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Export all BirdMigration Measure into an Excel file.
     * @return a byte array containing the generated Excel file.
     */
    public byte[] exportBirdMigrationMeasures() {
        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ) {
            List<BirdMigrationMeasureDTO> measures = birdMigrationService.getAllBirdMigrationMeasures();

            Sheet sheet = workbook.createSheet("Hauteur des neiges");
            String[] headerValues = {"No", "id", "Date", "Lieu", "Auteur", "Espèce d'oiseau", "Evénement"};
            createTitle(sheet, headerValues.length, "Migration des oiseaux");
            createHeaderInfo(sheet, headerValues.length, measures.size());
            createHeader(sheet, headerValues);

            int rowIndex = 3;
            int no = 1;
            for (BirdMigrationMeasureDTO measure : measures) {
                Row row = sheet.createRow(rowIndex++);

                //No
                createNumericCell(row, 0, no++);
                //id
                createNumericCell(row, 1, measure.id());
                //Date
                createDateCell(row, 2, measure.date());
                //Lieu
                createTextCell(row, 3, measure.location());
                //Auteur
                createTextCell(row, 4, measure.author());
                //Espèce
                createTextCell(row, 5, measure.specie().getLabel());
                //Evenement
                createTextCell(row, 6, measure.event().getLabel());
            }

            formatSheet(sheet, headerValues.length, rowIndex);


            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Export all EggsLaying Measure into an Excel file.
     * @return a byte array containing the generated Excel file.
     */
    public byte[] exportEggsLayingMeasures() {
        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ) {
            List<EggsLayingMeasureDTO> measures = eggsLayingService.getAllEggsLayingMeasure();

            Sheet sheet = workbook.createSheet("Hauteur des neiges");
            String[] headerValues = {"No", "id", "Date", "Lieu", "Auteur", "Nombre de pontes"};
            createTitle(sheet, headerValues.length, "Hauteur des neiges");
            createHeaderInfo(sheet, headerValues.length, measures.size());
            createHeader(sheet, headerValues);

            int rowIndex = 3;
            int no = 1;
            for (EggsLayingMeasureDTO measure : measures) {
                Row row = sheet.createRow(rowIndex++);

                //No
                createNumericCell(row, 0, no++);
                //id
                createNumericCell(row, 1, measure.id());
                //Date
                createDateCell(row, 2, measure.date());
                //Lieu
                createTextCell(row, 3, measure.location());
                //Auteur
                createTextCell(row, 4, measure.author());
                //Nombre de pontes
                createNumericCell(row, 5, measure.number());
            }

            formatSheet(sheet, headerValues.length, rowIndex);

            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (Exception e) {
            return null;
        }
    }


    private void createTitle(Sheet sheet, int columnSize, String title) {

        CellStyle titleStyle = createTitleStyle(sheet.getWorkbook());
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(32);

        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);
        titleCell.setCellStyle(titleStyle);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnSize-1));
    }

    private void createHeaderInfo(Sheet sheet, int columnSize, int nMeasures) {

        CellStyle infoStyle = createInfoStyle(sheet.getWorkbook());

        Row infoRow = sheet.createRow(1);
        infoRow.setHeightInPoints(22);

        Cell dateCell = infoRow.createCell(0);
        dateCell.setCellValue(
                "Date d'export : " + LocalDate.now()
        );
        dateCell.setCellStyle(infoStyle);

        sheet.addMergedRegion(
                new CellRangeAddress(1, 1, 0, columnSize/2)
        );

        Cell countCell = infoRow.createCell((columnSize/2)+1);
        countCell.setCellValue(
                "Nombre de mesures : " + nMeasures
        );
        countCell.setCellStyle(infoStyle);

        sheet.addMergedRegion(
                new CellRangeAddress(1, 1, (columnSize/2)+1, columnSize-1)
        );
    }

    private void createHeader(Sheet sheet, String[] headerValues) {

        CellStyle headerStyle = createHeaderStyle(sheet.getWorkbook());
        addBorders(headerStyle);

        Row header = sheet.createRow(2);
        header.setHeightInPoints(26);
        for (int index = 0; index < headerValues.length; index++) {
            Cell cell = header.createCell(index);
            cell.setCellValue(headerValues[index]);
            cell.setCellStyle(headerStyle);
        }
    }

    private void createTextCell(Row row, int columnIndex, String value) {
        CellStyle style = createValueStyle(row.getSheet().getWorkbook());

        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private void createNumericCell(Row row, int columnIndex, Number value) {
        CellStyle style = createValueStyle(row.getSheet().getWorkbook());
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value.doubleValue());
        cell.setCellStyle(style);
    }

    private void createDateCell(Row row, int columnIndex, LocalDate date) {
        CellStyle style = createDateStyle(row.getSheet().getWorkbook());

        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(date);
        cell.setCellStyle(style);
    }

    private void addBorders(CellStyle style) {
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        short borderColor = IndexedColors.GREY_25_PERCENT.getIndex();

        style.setTopBorderColor(borderColor);
        style.setBottomBorderColor(borderColor);
        style.setLeftBorderColor(borderColor);
        style.setRightBorderColor(borderColor);
    }

    private CellStyle createTitleStyle(Workbook workbook) {
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 18);
        titleFont.setColor(IndexedColors.DARK_GREEN.getIndex());

        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFillForegroundColor(
                IndexedColors.LIGHT_GREEN.getIndex()
        );
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return titleStyle;
    }

    private CellStyle createInfoStyle(Workbook workbook) {
        Font infoFont = workbook.createFont();
        infoFont.setItalic(true);
        infoFont.setColor(IndexedColors.DARK_GREEN.getIndex());

        CellStyle infoStyle = workbook.createCellStyle();
        infoStyle.setFont(infoFont);
        infoStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        infoStyle.setFillForegroundColor(
                IndexedColors.LIGHT_GREEN.getIndex()
        );
        infoStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        infoStyle.setBorderBottom(BorderStyle.THIN);
        infoStyle.setBottomBorderColor(
                IndexedColors.DARK_GREEN.getIndex()
        );
        infoStyle.setAlignment(HorizontalAlignment.CENTER);

        return infoStyle;
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        headerStyle.setFillForegroundColor(
                IndexedColors.GREEN.getIndex()
        );
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return headerStyle;
    }

    private CellStyle createValueStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        addBorders(style);
        return style;
    }

    private CellStyle createDateStyle(Workbook workbook) {
        CellStyle dateStyle = createValueStyle(workbook);

        CreationHelper helper = workbook.getCreationHelper();
        dateStyle.setDataFormat(helper.createDataFormat().getFormat("dd.MM.yyyy"));

        return dateStyle;
    }

    private void formatSheet(
            Sheet sheet,
            int numberOfColumns,
            int lastDataRow
    ) {
        //sheet.createFreezePane(0, 3);

        if (lastDataRow >= 3) {
            sheet.setAutoFilter(
                    new CellRangeAddress(
                            2,
                            lastDataRow,
                            0,
                            numberOfColumns - 1
                    )
            );
        }

        for (int columnIndex = 0;
             columnIndex < numberOfColumns;
             columnIndex++) {

            sheet.autoSizeColumn(columnIndex);

            int currentWidth = sheet.getColumnWidth(columnIndex);
            int widthWithMargin = currentWidth + 1200;

            sheet.setColumnWidth(
                    columnIndex,
                    Math.min(widthWithMargin, 255 * 256)
            );
        }

        sheet.setColumnWidth(0, 8 * 256);
        sheet.setColumnWidth(1, 10 * 256);
        sheet.setColumnWidth(2, 14 * 256);
        sheet.setColumnWidth(3, 24 * 256);
        sheet.setColumnWidth(4, 22 * 256);
        sheet.setColumnWidth(5, 20 * 256);
        sheet.setColumnWidth(6, 20 * 256);
        sheet.setColumnWidth(7, 20 * 256);

        sheet.setDefaultRowHeightInPoints(20);
        sheet.setDisplayGridlines(false);
    }
}
