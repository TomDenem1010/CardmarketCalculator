package com.home.cardmarket;

import java.util.Map;

import com.home.cardmarket.common.CommandLineTypeEnum;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Slf4j
@Command(name = "card-market-calculator", mixinStandardHelpOptions = true, version = "1.0", description = "A command-line application for calculating card market values.")
public class CardmarketApplication implements Runnable {

	@Option(names = "--type")
	private String type;

	@Option(names = "--filePath")
	private String filePath;

	@Override
	public void run() {
		CommandLineTypeEnum.executeCommand(type, Map.of("filePath", getOrEmpty(filePath)));
	}

	public static void main(String[] args) {
		int exitCode = new CommandLine(new CardmarketApplication()).execute(args);
		System.exit(exitCode);
	}

	private String getOrEmpty(String value) {
		return value != null ? value : "";
	}
}
