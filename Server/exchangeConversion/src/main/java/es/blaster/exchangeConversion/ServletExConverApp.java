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

public class ServletExConverApp extends HttpServlet {

    // Definir tasas como constantes
    private static final double EURO_TO_DOLLAR_RATE = 1.06;
    private static final double DOLLAR_TO_EURO_RATE = 1.0 / 1.06;
    private static final double EURO_TO_POUND_RATE = 0.87;
    private static final double POUND_TO_EURO_RATE = 1.0 / 0.87;
    private static final double DOLLAR_TO_POUND_RATE = 0.82;
    private static final double POUND_TO_DOLLAR_RATE = 1.0 / 0.82;
    private double rate = 0.0;
    private double result = 0.0;

    private double obtenerTasaDeCambio(String currencyFrom, String currencyTo) {

        if (currencyFrom.equals(currencyTo)) {
            rate = 1.0; // Conversión de una moneda a sí misma, tasa de cambio 1:1
        } else {
            String conversionKey = currencyFrom + "-" + currencyTo;

            switch (conversionKey) {
                case "EUR-USD":
                    rate = EURO_TO_DOLLAR_RATE;
                    break;
                case "USD-EUR":
                    rate = DOLLAR_TO_EURO_RATE;
                    break;
                case "EUR-GBP":
                    rate = EURO_TO_POUND_RATE;
                    break;
                case "GBP-EUR":
                    rate = POUND_TO_EURO_RATE;
                    break;
                case "USD-GBP":
                    rate = DOLLAR_TO_POUND_RATE;
                    break;
                default:
                    rate = POUND_TO_DOLLAR_RATE;
                    break;
            }
        }
        return rate;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        Gson oGson = new Gson();
        PrintWriter out = response.getWriter();

        if (request.getParameter("op").equalsIgnoreCase("login")) {

            ResponseBeanSession oRBSession = oGson.fromJson(request.getReader(), ResponseBeanSession.class);
            if (oRBSession.getUsername().equals("admin") && oRBSession.getPassword().equals("72b37a5cce60840d1392a19392165d1e8531e4e0b6bbeb122588e73a20024ebd")) {
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

        } else if (request.getParameter("op").contentEquals("conversion")) {

            try {
                ResponseBean oRB = oGson.fromJson(request.getReader(), ResponseBean.class);
                double amount = oRB.getAmount();
                String currencyFrom = oRB.getCurrencyFrom();
                String currencyTo = oRB.getCurrencyTo();
                double conversionRate = obtenerTasaDeCambio(currencyFrom, currencyTo);
                result = amount * conversionRate;
                double conversionRateFromTo = obtenerTasaDeCambio(currencyFrom, currencyTo);
                double conversionRateToFrom = obtenerTasaDeCambio(currencyTo, currencyFrom);
                oRB.setConversionRateFromTo(conversionRateFromTo);
                oRB.setConversionRateToFrom(conversionRateToFrom);
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
            try {
                ResponseBean oRB = oGson.fromJson(request.getReader(), ResponseBean.class);
                String amountStr = String.valueOf(oRB.getAmount()); // Convertir el valor a cadena
                if (amountStr.matches("^\\d{1,10}$")) {
                    // El valor es un número válido con un máximo de 10 dígitos
                    double amount = Double.parseDouble(amountStr); // Convertir de nuevo a double
                    String currencyFrom = oRB.getCurrencyFrom();
                    String currencyTo = oRB.getCurrencyTo();
                    double conversionRate = obtenerTasaDeCambio(currencyFrom, currencyTo);
                    result = amount * conversionRate;
                    oRB.setResult(result);

                    response.setStatus(200);
                    out.print(oGson.toJson(oRB));
                } else {
                    response.setStatus(500);
                    oRB = new ResponseBean();
                    oRB.setErrorDescription("El campo 'amount' debe tener solo dígitos y un máximo de 10 caracteres.");
                    out.print(oGson.toJson(oRB));
                }
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

        } else if (request.getParameter("op").contentEquals("reset")) {
            rate = 0.0;
            result = 0.0;

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
