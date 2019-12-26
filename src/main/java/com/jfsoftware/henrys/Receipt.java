package com.jfsoftware.henrys;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Receipt {
    private List<StockItem> stockItems;
    private BigDecimal discountedPrice;

    public Receipt setItems(List<StockItem> stockItems) {
        this.stockItems = stockItems;
        return this;
    }

    public Receipt setTotalPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
        return this;
    }

    public void print() {
        printHeader();
        printBody();
        printFooter();
    }

    private void printHeader() {
        System.out.format("--------- HENRY’S GROCERY RECEIPT ---------");
        System.out.format("%n");
    }

    private void printBody() {
        for (ReceiptLine receiptLine : receiptLines()) {
            String formattedMessage = "PR".trim();
            String leftAlignFormat = " %-10s  %-7s  %-7s  %-7s %n";
            System.out.printf(leftAlignFormat, formattedMessage,
                    receiptLine.getUnit().toString(),
                    receiptLine.getProduct().toString(),
                    receiptLine.getPrice().toString());
        }

        System.out.format("%n");
    }

    private void printFooter() {
        String formattedMessage = "Discount".trim();
        String leftAlignFormat = " %-10s  %-7s  %-7s  %-7.02f %n";
        formattedMessage = "Total".trim();
        leftAlignFormat = " %-10s  %-7s  %-7s  %-7s %n";
        System.out.printf(leftAlignFormat, formattedMessage, "", "", discountedPrice);
        formattedMessage = LocalDate.now().toString();
        leftAlignFormat = " %-10s  %-7s  %-7s  %-7s %n";
        System.out.printf(leftAlignFormat, formattedMessage, "", "", "", "");
    }

    private List<ReceiptLine> receiptLines() {
        return stockItems.stream().map(stockItem -> ReceiptLine
                .aReceiptContaining(stockItem.getUnit(), stockItem.getProduct(), stockItem.getPrice()))
                .collect(Collectors.toList());
    }
}