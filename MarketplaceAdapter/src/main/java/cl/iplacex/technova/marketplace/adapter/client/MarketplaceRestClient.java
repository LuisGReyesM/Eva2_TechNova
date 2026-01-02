package cl.iplacex.technova.marketplace.adapter.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class MarketplaceRestClient {

    private static final String BASE_URL = "http://localhost:8091";

    private final HttpClient httpClient;

    public MarketplaceRestClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Consume el endpoint /orders/today
     */
    public List<JsonObject> obtenerPedidosDelDia() {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/orders/today"))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JsonArray pedidosJson = JsonParser
                    .parseString(response.body())
                    .getAsJsonArray();

            List<JsonObject> pedidos = new ArrayList<>();

            pedidosJson.forEach(e ->
                    pedidos.add(e.getAsJsonObject())
            );

            return pedidos;

        } catch (Exception e) {
            throw new RuntimeException("Error al obtener pedidos del Marketplace", e);
        }
    }

    /**
     * Consume el endpoint /orders/{id}/shipping-cost
     */
    public JsonObject obtenerCostoEnvio(String idPedido) {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/orders/" + idPedido + "/shipping-cost"))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return JsonParser
                    .parseString(response.body())
                    .getAsJsonObject();

        } catch (Exception e) {
            throw new RuntimeException("Error al obtener costo de envío", e);
        }
    }
}
