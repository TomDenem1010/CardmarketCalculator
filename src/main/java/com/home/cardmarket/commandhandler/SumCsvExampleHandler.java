package com.home.cardmarket.commandhandler;

import java.util.Map;

import com.home.cardmarket.TypeHandler;

public class SumCsvExampleHandler implements TypeHandler {

    @Override
    public void handle(Map<String, String> params) {
        System.out.println("Generating summary CSV example file with parameters: " + params);
    }
}
