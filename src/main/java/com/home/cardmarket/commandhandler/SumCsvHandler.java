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
        setcsvRows(rows, csvRows);

        new CsvWriter().write(
                Path.of(params.get(InputEnum.FILE_PATH)).getParent().toString(),
                "sumCsv",
                csvRows);
    }

    private void setRowValues(List<Map<CsvHeaderEnum, String>> rows) {
        rows.forEach(row -> new CmValueSetter().setValue(row));
    }

    private void setcsvRows(List<Map<CsvHeaderEnum, String>> rows, List<List<String>> csvRows) {
        rows.forEach(row -> csvRows.add(CsvHeaderEnum.getRowValues(row)));
    }
}
