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

    private static final String BASE_URL = "http://0.0.0.0:8091";

    private final HttpClient httpClient;

    public MarketplaceRestClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Consume el endpoint /orders/today
     */
    public List<JsonObject> obtenerPedidosDelDia() {

        try {
            System.out.println("🔹 [Marketplace Adapter] Consultando ventas del día...");
            System.out.println("➡️  GET " + BASE_URL + "/orders/today");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/orders/today"))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("✅ Respuesta recibida desde /orders/today");
            System.out.println("📦 Payload recibido:");
            System.out.println(response.body());

            JsonArray pedidosJson = JsonParser
                    .parseString(response.body())
                    .getAsJsonArray();

            List<JsonObject> pedidos = new ArrayList<>();

            pedidosJson.forEach(e -> {
                JsonObject pedido = e.getAsJsonObject();
                pedidos.add(pedido);

                System.out.println("🧾 Pedido obtenido - ID: " + pedido.get("id").getAsString());
            });

            return pedidos;

        } catch (Exception e) {
            System.err.println("❌ Error al obtener pedidos del Marketplace");
            throw new RuntimeException("Error al obtener pedidos del Marketplace", e);
        }
    }

    /**
     * Consume el endpoint /orders/{id}/shipping-cost
     */
    public JsonObject obtenerCostoEnvio(String idPedido) {

        try {
            System.out.println("➡️  Consultando costo de envío para pedido ID: " + idPedido);
            System.out.println("➡️  GET " + BASE_URL + "/orders/" + idPedido + "/shipping-cost");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/orders/" + idPedido + "/shipping-cost"))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject costoEnvio = JsonParser
                    .parseString(response.body())
                    .getAsJsonObject();

            System.out.println("💰 Costo de envío obtenido:");
            System.out.println(costoEnvio.toString());

            return costoEnvio;

        } catch (Exception e) {
            System.err.println("❌ Error al obtener costo de envío para pedido ID: " + idPedido);
            throw new RuntimeException("Error al obtener costo de envío", e);
        }
    }
}
