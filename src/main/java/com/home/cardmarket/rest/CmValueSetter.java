package com.home.cardmarket.rest;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.home.cardmarket.commandhandler.CsvHeaderEnum;
import com.microsoft.playwright.Browser;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class CmValueSetter {

    public void setValue(Map<CsvHeaderEnum, String> params, Browser browser) {
        Document document = new Caller().callWithPlaywright(params.get(CsvHeaderEnum.URL), browser);
        params.put(
                CsvHeaderEnum.PRICE_FROM,
                getValue(document, "From"));
        params.put(
                CsvHeaderEnum.TOTAL_FROM,
                getTotal(params.get(CsvHeaderEnum.PRICE_FROM), params.get(CsvHeaderEnum.QUANTITY)));
        params.put(
                CsvHeaderEnum.PRICE_TREND,
                getValue(document, "Price Trend"));
        params.put(
                CsvHeaderEnum.TOTAL_TREND,
                getTotal(params.get(CsvHeaderEnum.PRICE_TREND), params.get(CsvHeaderEnum.QUANTITY)));
    }

    private String getValue(Document document, String label) {
        Element dt = document.select("dt")
                .stream()
                .filter(e -> label.equals(e.text().trim()))
                .findFirst()
                .orElse(null);

        return Objects.nonNull(dt) ? parseEuroToDoubleString(dt.nextElementSibling().text().trim()) : "0";
    }

    private String parseEuroToDoubleString(String value) {
        return value.replace("€", "")
                .replace(".", "")
                .replace(",", ".")
                .trim();
    }

    private String getTotal(String price, String quantity) {
        return new BigDecimal(price).multiply(new BigDecimal(quantity)).toString();
    }
}
