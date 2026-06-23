package com.home.cardmarket.common.commandhandler;

import java.util.Map;

import com.home.cardmarket.common.TypeHandler;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SumCsvHandler implements TypeHandler {

    @Override
    public void handle(Map<String, String> params) {
        // Implement the logic to generate a summary CSV file based on the provided
        // parameters.
        // This is a placeholder for the actual implementation.
        System.out.println("Generating summary CSV file with parameters: " + params);
    }

}
