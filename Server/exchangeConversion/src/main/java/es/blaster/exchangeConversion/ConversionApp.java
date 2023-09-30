package es.blaster.exchangeConversion;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConversionApp extends HttpServlet {

    private CurrencyData euro = new CurrencyData("EUR", "Euro", 1.0);
    private CurrencyData dollar = new CurrencyData("USD", "Dólar estadounidense", 1.18);
    private CurrencyData pound = new CurrencyData("GBP", "Libra esterlina", 0.85);

    public double obtenerTasaDeCambio(String currencyFrom, String currencyTo) {
        // Implementa la lógica para obtener la tasa de cambio entre currencyFrom y currencyTo
        // Utiliza los objetos CurrencyData predefinidos para realizar la conversión
        double rate = 0.0;

        if (currencyFrom.equals("EUR") && currencyTo.equals("USD")) {
            rate = dollar.getExchangeRate();
        } else if (currencyFrom.equals("USD") && currencyTo.equals("EUR")) {
            rate = 1.0 / dollar.getExchangeRate(); // Tasa inversa
        } else if (currencyFrom.equals("EUR") && currencyTo.equals("GBP")) {
            rate = pound.getExchangeRate();
        } else if (currencyFrom.equals("GBP") && currencyTo.equals("EUR")) {
            rate = 1.0 / pound.getExchangeRate(); // Tasa inversa
        }

        return rate;
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        Gson oGson = new Gson();
        PrintWriter out = response.getWriter();

        if (request.getParameter("op").equals("conversion")) { // Cambio de "calcula" a "conversion"

            if (request.getSession().getAttribute("alumnadodaw") == null) {

                ResponseBeanSession oRBSession = new ResponseBeanSession();
                oRBSession.setErrorMsg("No hay sesión");
                response.setStatus(500);
                out.print(oGson.toJson(oRBSession));

            } else {

                try {
                    ResponseBean oRB = oGson.fromJson(request.getReader(), ResponseBean.class);

                    // Lógica para realizar la conversión de moneda
                    // Asumo que tienes las tasas de cambio disponibles en alguna variable o base de datos
                    // Ejemplo de cálculo de conversión
                    double amount = oRB.getAmount();
                    String currencyFrom = oRB.getCurrencyFrom();
                    String currencyTo = oRB.getCurrencyTo();
                    double conversionRate = obtenerTasaDeCambio(currencyFrom, currencyTo); // Implementa esta función
                    double result = amount * conversionRate;
                    oRB.setResult(result);

                    response.setStatus(200);
                    out.print(oGson.toJson(oRB));

                } catch (JsonIOException e) {
                    response.setStatus(500);
                    ResponseBean oRB = new ResponseBean();
                    oRB.setErrorDescription("Error en JSON 1");
                    out.print(oGson.toJson(oRB));
                } catch (IOException e) {
                    response.setStatus(500);
                    ResponseBean oRB = new ResponseBean();
                    oRB.setErrorDescription("Error en JSON 2");
                    out.print(oGson.toJson(oRB));
                } catch (JsonSyntaxException e) {
                    response.setStatus(500);
                    ResponseBean oRB = new ResponseBean();
                    oRB.setErrorDescription("Error en JSON 3");
                    out.print(oGson.toJson(oRB));
                }
            }

        } else {

            if (request.getParameter("op").equalsIgnoreCase("login")) {
                ResponseBeanSession oRBSession = oGson.fromJson(request.getReader(), ResponseBeanSession.class);
                if (oRBSession.getUsername().equals("rafaelin") && oRBSession.getPassword().equals("72b37a5cce60840d1392a19392165d1e8531e4e0b6bbeb122588e73a20024ebd")) {
                    request.getSession().setAttribute("alumnadodaw", oRBSession.getUsername());
                    ResponseBeanSession oRBSession1 = new ResponseBeanSession();
                    oRBSession1.setUsername(oRBSession.getUsername());
                    oRBSession1.setPassword("");
                    response.setStatus(200);
                    out.print(oGson.toJson(oRBSession1));
                } else {
                    ResponseBeanSession oRBSession1 = new ResponseBeanSession();
                    oRBSession1.setUsername(oRBSession.getUsername());
                    oRBSession1.setPassword("");
                    oRBSession1.setErrorMsg("error en la autenticacion");
                    response.setStatus(500);
                    out.print(oGson.toJson(oRBSession1));

                }
            } else {
                if (request.getParameter("op").equalsIgnoreCase("logout")) {
                    request.getSession().invalidate();
                    response.setStatus(200);
                } else {
                    if (request.getParameter("op").equalsIgnoreCase("check")) {
                        if (request.getSession().getAttribute("alumnadodaw") == null) {
                            ResponseBeanSession oRBSession = new ResponseBeanSession();
                            oRBSession.setErrorMsg("No hay sesión");
                            response.setStatus(500);
                            out.print(oGson.toJson(oRBSession));
                        } else {
                            ResponseBeanSession oRBSession = new ResponseBeanSession();
                            oRBSession.setUsername(request.getSession().getAttribute("alumnadodaw").toString());
                            response.setStatus(200);
                            out.print(oGson.toJson(oRBSession));
                        }
                    } else {
                        response.setStatus(500);
                        ResponseBean oRB = new ResponseBean();
                        oRB.setErrorDescription("Error en parametro");
                        out.print(oGson.toJson(oRB));
                    }

                }
            }

        }

    }

}
