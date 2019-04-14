package com.teenspirit88.taskx.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

public class Price implements Serializable {

    private String amount;
    private String currency;

    public Price(String amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmountAndSymbol() {

        Currency _currency = Currency.getInstance(currency);
        BigDecimal bd = new BigDecimal(amount);
        //Проставляем разделить дробной части исходя их типа валюты полученного из JSON
        bd = bd.movePointLeft(_currency.getDefaultFractionDigits());
        //Получаем символ валюты
        String s = _currency.getSymbol();

        return bd.toString() + " " + _currency.getSymbol();
    }
}
