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
public class CurrencyData {

    private String code;
    private String name;
    private double exchangeRate;

    public CurrencyData(String code, String name, double exchangeRate) {
        this.code = code;
        this.name = name;
        this.exchangeRate = exchangeRate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

}
