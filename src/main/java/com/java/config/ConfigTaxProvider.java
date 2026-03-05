package com.java.config;

public class ConfigTaxProvider implements TaxProvider {

    @Override
    public double getTaxRate() {
        return ConfigLoader.loadTaxRate();
    }
}