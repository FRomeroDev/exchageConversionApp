/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.blaster.exchangeConversion;

/**
 *
 * @author anon
 * @version The following has evaluated to null or missing: ==> version [in
 * template "Templates/Classes/Class.java" at line 25, column 15]
 *
 * ---- Tip: If the failing expression is known to legally refer to something
 * that's sometimes null or missing, either specify a default value like
 * myOptionalVar!myDefault, or use
 * <#if myOptionalVar??>when-present<#else>when-missing</#if>. (These only cover
 * the last step of the expression; to cover the whole expression, use
 * parenthesis: (myOptionalVar.foo)!myDefault, (myOptionalVar.foo)?? ----
 *
 * ---- FTL stack trace ("~" means nesting-related): - Failed at: ${version} [in
 * template "Templates/Classes/Class.java" at line 25, column 13] ----
 */
public class ConversionData {

    private double amount;
    private String currencyFrom;
    private String currencyTo;

    // Constructores, getters y setters
    public ConversionData(double amount, String currencyFrom, String currencyTo) {
        this.amount = amount;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }
}
