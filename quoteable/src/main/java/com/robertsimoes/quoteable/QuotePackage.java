package com.robertsimoes.quoteable;

/**
 * Copyright (c) 2017 Pressure Labs.
 */

public class QuotePackage {
    private String author;
    private String quote;

    public String getAuthor() {
        return author;
    }


    public String getQuote() {
        return quote;
    }


    public QuotePackage(String author, String quote) {

        this.author = author;
        this.quote = quote;
    }
}
