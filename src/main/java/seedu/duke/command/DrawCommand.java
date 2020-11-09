package seedu.duke.command;

import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.XDDFChartAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartLegend;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLineChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLineSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea;

import seedu.duke.data.Data;
import seedu.duke.data.Item;
import seedu.duke.data.SpendingList;
import seedu.duke.ui.Ui;
import seedu.duke.utilities.FileExplorer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * This class represents the command for generating charts for specific data.
 */
//@@author Wu-Haitao
public class DrawCommand extends DateCommand {
    private final String filePath = "Charts.xlsx";
    private final FileExplorer fileExplorer = new FileExplorer(filePath);
    private boolean isOpening;
    private String timePeriod;

    public DrawCommand() {
        this(true);
    }

    public DrawCommand(boolean isOpening) {
        timePeriod = "";
        this.isOpening = isOpening;
    }

    public DrawCommand(String year, String month) {
        this(year, month, true);
    }

    public DrawCommand(String year, String month, boolean isOpening) {
        if (month == null) {
            timePeriod = year;
        } else {
            timePeriod = year + "-" + month;
        }
        this.isOpening = isOpening;
    }

    /**
     * Filters the data and generates charts accordingly.
     *
     * @param data current data stored by the application
     * @param ui user interface of the application
     */
    @Override
    public void execute(Data data, Ui ui) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet0 = workbook.createSheet("Sheet 0");
        XSSFSheet sheet1 = workbook.createSheet("Sheet 1");
        sheet0.setDefaultColumnWidth(5);
        sheet1.setDefaultColumnWidth(5);
        ArrayList<Item> items = new ArrayList<>();
        SpendingList targetSpendingList = new SpendingList(items);

        for (int i = 0; i < data.spendingList.getListSize(); i++) {
            if (data.spendingList.getItem(i).getDate().startsWith(timePeriod)) {
                targetSpendingList.getSpendingList().add(data.spendingList.getItem(i));
            }
        }
        if (targetSpendingList.getListSize() != 0) {
            TreeMap<Integer, Double> dateMap;
            if (timePeriod.equals("")) {
                dateMap = getYearMap(targetSpendingList);
            } else if (timePeriod.length() == 4) {
                dateMap = getMonthMap(targetSpendingList);
            } else {
                dateMap = getDayMap(targetSpendingList);
            }
            Integer[] dates = dateMap.keySet().toArray(new Integer[0]);
            Double[] amountsForDates = dateMap.values().toArray(new Double[0]);
            drawChart(sheet0, dates, amountsForDates, 0, 0, 15, 10);

            TreeMap<String, Double> categoryMap = getCategoryMap(targetSpendingList);
            String[] categories = categoryMap.keySet().toArray(new String[0]);
            Double[] amountsForCategories = categoryMap.values().toArray(new Double[0]);
            drawChart(sheet1, categories, amountsForCategories, 0, 0, 8, 12);

            try {
                FileOutputStream fileOut = new FileOutputStream(filePath);
                workbook.write(fileOut);
                fileOut.flush();
                fileOut.close();
            } catch (Exception e) {
                assert false : "Failed to create Excel file";
            }

            ui.printDrawMessage(true);

            if (isOpening) {
                try {
                    fileExplorer.openFile();
                } catch (IOException e) {
                    ui.printOpenFileFailedMessage();
                }
            }
        } else {
            ui.printDrawMessage(false);
        }
    }

    private TreeMap<String, Double> getCategoryMap(SpendingList spendingList) {
        TreeMap<String, Double> map = new TreeMap<>();
        for (int i = 0; i < spendingList.getListSize(); i++) {
            Item item = spendingList.getItem(i);
            if (map.containsKey(item.getCategory())) {
                map.replace(item.getCategory(), map.get(item.getCategory()) + item.getAmount());
            } else {
                map.put(item.getCategory(), item.getAmount());
            }
        }
        return map;
    }

    private TreeMap<Integer, Double> getYearMap(SpendingList spendingList) {
        TreeMap<Integer, Double> map = new TreeMap<>();
        int minYear = Integer.parseInt(spendingList.getItem(0).getDate().substring(0, 4));
        int maxYear = minYear;
        for (int i = 0; i < spendingList.getListSize(); i++) {
            Item item = spendingList.getItem(i);
            int year = Integer.parseInt(item.getDate().substring(0, 4));
            if (year > maxYear) {
                maxYear = year;
            }
            if (year < minYear) {
                minYear = year;
            }
            if (map.containsKey(year)) {
                map.replace(year, map.get(year) + item.getAmount());
            } else {
                map.put(year, item.getAmount());
            }
        }
        for (int i = minYear; i <= maxYear; i++) {
            map.putIfAbsent(i, 0.0);
        }
        return map;
    }

    private TreeMap<Integer, Double> getMonthMap(SpendingList spendingList) {
        TreeMap<Integer, Double> map = new TreeMap<>();
        final int minMonth = 1;
        final int maxMonth = 12;
        for (int i = 0; i < spendingList.getListSize(); i++) {
            Item item = spendingList.getItem(i);
            int month = Integer.parseInt(item.getDate().substring(5, 7));
            if ((month < minMonth) || (month > maxMonth)) {
                continue;
            }
            if (map.containsKey(month)) {
                map.replace(month, map.get(month) + item.getAmount());
            } else {
                map.put(month, item.getAmount());
            }
        }
        for (int i = minMonth; i <= maxMonth; i++) {
            map.putIfAbsent(i, 0.0);
        }
        return map;
    }

    private TreeMap<Integer, Double> getDayMap(SpendingList spendingList) {
        TreeMap<Integer, Double> map = new TreeMap<>();
        final int minDay = 1;
        final int maxDay = 31;
        for (int i = 0; i < spendingList.getListSize(); i++) {
            Item item = spendingList.getItem(i);
            int day = Integer.parseInt(item.getDate().substring(8, 10));
            if ((day < minDay) || (day > maxDay)) {
                continue;
            }
            if (map.containsKey(day)) {
                map.replace(day, map.get(day) + item.getAmount());
            } else {
                map.put(day, item.getAmount());
            }
        }
        for (int i = minDay; i <= maxDay; i++) {
            map.putIfAbsent(i, 0.0);
        }
        return map;
    }

    /**
     * Plots a pie chart in a sheet according to the given data.
     *
     * @param sheet target sheet which the chart will be positioned in
     * @param category categories of the chart
     * @param values values of the chart
     * @param x1 x value of the upper left corner of the chart
     * @param y1 y value of the upper left corner of the chart
     * @param x2 x value of the lower right corner of the chart
     * @param y2 y value of the lower right corner of the chart
     */
    private void drawChart(XSSFSheet sheet, String[] category, Double[] values, int x1, int y1, int x2, int y2) {
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, x1, y1, x2, y2);
        XSSFChart chart = drawing.createChart(anchor);
        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(org.apache.poi.xddf.usermodel.chart.LegendPosition.TOP_RIGHT);
        XDDFDataSource<String> cat = XDDFDataSourcesFactory.fromArray(category);
        XDDFNumericalDataSource<Double> val = XDDFDataSourcesFactory.fromArray(values);
        XDDFChartData chartData = chart.createData(ChartTypes.PIE, null, null);
        chartData.addSeries(cat, val);
        chart.plot(chartData);
    }

    /**
     * Plots a line chart in a sheet according to the given data.
     *
     * @param sheet target sheet which the chart will be positioned in
     * @param category categories of the chart
     * @param values values of the chart
     * @param x1 x value of the upper left corner of the chart
     * @param y1 y value of the upper left corner of the chart
     * @param x2 x value of the lower right corner of the chart
     * @param y2 y value of the lower right corner of the chart
     */
    private void drawChart(XSSFSheet sheet, Integer[] category, Double[] values, int x1, int y1, int x2, int y2) {
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, x1, y1, x2, y2);
        XSSFChart chart = drawing.createChart(anchor);
        XDDFDataSource<Integer> cat = XDDFDataSourcesFactory.fromArray(category);
        XDDFNumericalDataSource<Double> val = XDDFDataSourcesFactory.fromArray(values);
        XDDFChartAxis chartAxis = chart.createCategoryAxis(AxisPosition.LEFT);
        XDDFValueAxis valueAxis = chart.createValueAxis(AxisPosition.BOTTOM);
        XDDFChartData chartData = chart.createData(ChartTypes.LINE, chartAxis, valueAxis);
        chartData.addSeries(cat, val);
        chart.plot(chartData);
        CTPlotArea plotArea = chart.getCTChart().getPlotArea();
        for (CTLineChart ch : plotArea.getLineChartList()) {
            for (CTLineSer ser : ch.getSerList()) {
                CTBoolean ctBool = CTBoolean.Factory.newInstance();
                ctBool.setVal(false);
                ser.setSmooth(ctBool);
            }
        }
    }

}
