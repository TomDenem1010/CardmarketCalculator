package com.home.cardmarket.commandhandler;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.home.cardmarket.InputEnum;
import com.home.cardmarket.TypeHandler;
import com.home.cardmarket.file.CsvReader;
import com.home.cardmarket.file.CsvWriter;
import com.home.cardmarket.rest.CmValueSetter;
import com.home.cardmarket.rest.PlaywrightBrowserContext;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class SumCsvHandler implements TypeHandler {

    @Override
    public void handle(Map<InputEnum, String> params) {
        List<Map<CsvHeaderEnum, String>> rows = new CsvReader().read(params.get(InputEnum.FILE_PATH));
        setRowValues(rows);
        List<List<String>> csvRows = new ArrayList<>(List.of(CsvHeaderEnum.getHeaders()));
        List<List<String>> csvValueRows = new ArrayList<>();
        setcsvRows(rows, csvValueRows);
        sortRows(csvValueRows);
        csvRows.addAll(csvValueRows);

        new CsvWriter().write(
                Path.of(params.get(InputEnum.FILE_PATH)).getParent().toString(),
                "sumCsv",
                csvRows);
    }

    private void setRowValues(List<Map<CsvHeaderEnum, String>> rows) {
        PlaywrightBrowserContext context = new PlaywrightBrowserContext();
        rows.forEach(row -> new CmValueSetter().setValue(row, context.getBrowser()));
        context.close();
    }

    private void setcsvRows(List<Map<CsvHeaderEnum, String>> rows, List<List<String>> csvRows) {
        rows.forEach(row -> csvRows.add(CsvHeaderEnum.getRowValues(row)));
    }

    private void sortRows(List<List<String>> csvValueRows) {
        csvValueRows.sort((row1, row2) -> {
            if (!isInteger(row1.get(0)) || !isInteger(row2.get(0))) {
                return row1.get(0).compareTo(row2.get(0));
            }

            return Integer.compare(Integer.parseInt(row1.get(0)), Integer.parseInt(row2.get(0)));
        });
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
