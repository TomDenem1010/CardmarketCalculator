package com.home.cardmarket.commandhandler;

import java.util.Map;

import com.home.cardmarket.TypeHandler;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SumCsvHandler implements TypeHandler {

    @Override
    public void handle(Map<String, String> params) {
        System.out.println("Generating summary CSV file with parameters: " + params);
    }
}
