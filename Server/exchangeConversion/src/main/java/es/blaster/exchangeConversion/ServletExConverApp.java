package es.blaster.exchangeConversion;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    Gson oGson = new Gson();

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
        PrintWriter out = response.getWriter();

        String operation = request.getParameter("op");

        try {
            if ("login".equalsIgnoreCase(operation)) {
                handleLogin(request, response, out);
            } else if ("conversion".equalsIgnoreCase(operation)) {
                handleConversion(request, response, out);
            } else if ("check".equalsIgnoreCase(operation)) {
                handleSessionCheck(request, response, out);
            } else if ("logout".equalsIgnoreCase(operation)) {
                handleLogout(request, response, out);
            } else {
                response.setStatus(500);
                ResponseBean closeSession = new ResponseBean();
                closeSession.setErrorDescription("Error en parámetro");
                out.print(oGson.toJson(closeSession));
            }
        } catch (Exception e) {
            // Manejo de excepciones genérico para mantener la consistencia en las respuestas de error.
            response.setStatus(500);
            ResponseBean errorResponse = new ResponseBean();
            errorResponse.setErrorDescription("Error en el servidor: " + e.getMessage());
            out.print(oGson.toJson(errorResponse));
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        ResponseBeanSession loginRequest = oGson.fromJson(request.getReader(), ResponseBeanSession.class);

        if ("admin".equals(loginRequest.getUsername()) && "72b37a5cce60840d1392a19392165d1e8531e4e0b6bbeb122588e73a20024ebd".equals(loginRequest.getPassword())) {
            HttpSession session = request.getSession(true);
            session.setAttribute("sessionAdmin", loginRequest.getUsername());
            ResponseBeanSession loginResponse = new ResponseBeanSession();
            loginResponse.setUsername(loginRequest.getUsername());
            loginResponse.setPassword("");
            response.setStatus(200);
            out.print(oGson.toJson(loginResponse));
        } else {
            ResponseBeanSession loginResponse = new ResponseBeanSession();
            loginResponse.setUsername(loginRequest.getUsername());
            loginResponse.setPassword("");
            loginResponse.setErrorMsg("error en la autenticación");
            response.setStatus(500);
            out.print(oGson.toJson(loginResponse));
        }
    }

    private void handleConversion(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        ResponseBean conversionRequest = oGson.fromJson(request.getReader(), ResponseBean.class);

        try {
            double amount = conversionRequest.getAmount();
            String currencyFrom = conversionRequest.getCurrencyFrom();
            String currencyTo = conversionRequest.getCurrencyTo();

            double conversionRateFromTo = obtenerTasaDeCambio(currencyFrom, currencyTo);
            double conversionRateToFrom = obtenerTasaDeCambio(currencyTo, currencyFrom);

            result = amount * conversionRateFromTo;

            conversionRequest.setConversionRateFromTo(conversionRateFromTo);
            conversionRequest.setConversionRateToFrom(conversionRateToFrom);
            conversionRequest.setResult(result);

            response.setStatus(200);
            out.print(oGson.toJson(conversionRequest));
        } catch (NumberFormatException e) {
            response.setStatus(400); // Bad Request
            conversionRequest = new ResponseBean();
            conversionRequest.setErrorDescription("El campo 'amount' debe ser un número válido.");
            out.print(oGson.toJson(conversionRequest));
        } catch (Exception e) {
            response.setStatus(500);
            conversionRequest = new ResponseBean();
            conversionRequest.setErrorDescription("Error en la conversión de moneda: " + e.getMessage());
            out.print(oGson.toJson(conversionRequest));
        }
    }

    private void handleSessionCheck(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        // Verificar si el atributo "sessionAdmin" está configurado en la sesión
        ResponseBeanSession sessionResponse = new ResponseBeanSession();
        HttpSession session = request.getSession(false);
        System.out.println("Verifying session...");

        if (session != null && session.getAttribute("sessionAdmin") != null) {
            System.out.println("Session found for user: " + session.getAttribute("sessionAdmin").toString());
            sessionResponse.setUsername(session.getAttribute("sessionAdmin").toString());
            response.setStatus(200);
            out.print(oGson.toJson(sessionResponse));
        } else {
            System.out.println("No encuentra sesión");
            sessionResponse.setErrorMsg("No hay sesión");
            response.setStatus(401); // Cambia el estado a 401 (No Autorizado)
            out.print(oGson.toJson(sessionResponse));
        }
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.setStatus(200);
    }
}
