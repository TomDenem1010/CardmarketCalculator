package com.home.cardmarket.common;

import com.home.cardmarket.common.commandhandler.SumCsvHandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public enum CommandLineTypeEnum {

    SUM_CSV_HANDLER("sum-csv-file", new SumCsvHandler());

    public final String command;
    public final TypeHandler handler;

    public static void executeCommand(String command, java.util.Map<String, String> params) {
        for (CommandLineTypeEnum type : CommandLineTypeEnum.values()) {
            if (type.command.equals(command)) {
                log.debug("Executing command: {} with parameters: {}", command, params);
                type.handler.handle(params);
                return;
            }
        }
        log.error("Unknown command: {}", command);
    }
}
