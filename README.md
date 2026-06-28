# Card Market Calculator

A command-line application for calculating and aggregating card market values from CSV files.

## Prerequisites

- Java 25+

## Build

```bash
mvn clean package
```

## Usage

### Sum CSV Example
Generate a sample CSV:

```bash
java -jar .\target\cardmarket.jar --type sum-csv-example --filePath "path\to\folder"
```

### Sum CSV
Process a CSV file and generate aggregated output:

```bash
java -jar .\target\cardmarket.jar --type sum-csv --filePath "path\to\file"
```
