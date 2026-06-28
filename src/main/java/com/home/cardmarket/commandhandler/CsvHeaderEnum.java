package com.home.cardmarket.commandhandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CsvHeaderEnum {

    ID("id"),
    URL("url"),
    QUANTITY("quantity"),
    PRICE_FROM("price from"),
    PRICE_TREND("price trend"),
    TOTAL_FROM("total from"),
    TOTAL_TREND("total trend");

    public final String header;

    public static List<String> getHeaders() {
        return Arrays.stream(CsvHeaderEnum.values())
                .map(headerEnum -> headerEnum.header)
                .collect(Collectors.toList());
    }

    public static List<String> getRowValues(Map<CsvHeaderEnum, String> row) {
        return Arrays.stream(CsvHeaderEnum.values())
                .map(headerEnum -> row.getOrDefault(headerEnum, ""))
                .collect(Collectors.toList());
    }
}
