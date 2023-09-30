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
public class ExchangeRate {

    private double euroToDollar;
    private double dollarToEuro;
    private double euroToPound;
    private double poundToEuro;
    private double dollarToYen;

    public double getEuroToDollar() {
        return euroToDollar;
    }

    public void setEuroToDollar(double euroToDollar) {
        this.euroToDollar = euroToDollar;
    }

    public double getDollarToEuro() {
        return dollarToEuro;
    }

    public void setDollarToEuro(double dollarToEuro) {
        this.dollarToEuro = dollarToEuro;
    }

    public double getEuroToPound() {
        return euroToPound;
    }

    public void setEuroToPound(double euroToPound) {
        this.euroToPound = euroToPound;
    }

    public double getPoundToEuro() {
        return poundToEuro;
    }

    public void setPoundToEuro(double poundToEuro) {
        this.poundToEuro = poundToEuro;
    }

    public double getDollarToYen() {
        return dollarToYen;
    }

    public void setDollarToYen(double dollarToYen) {
        this.dollarToYen = dollarToYen;
    }
}
