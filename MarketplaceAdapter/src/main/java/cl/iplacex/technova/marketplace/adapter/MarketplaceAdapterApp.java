package cl.iplacex.technova.marketplace.adapter;

import cl.iplacex.technova.marketplace.adapter.client.MarketplaceRestClient;
import cl.iplacex.technova.marketplace.adapter.enricher.EnrichmentService;
import cl.iplacex.technova.marketplace.adapter.messaging.JmsProducer;
import com.google.gson.JsonObject;

import java.util.List;

public class MarketplaceAdapterApp {

    public static void main(String[] args) {

        System.out.println("=== Iniciando Marketplace Adapter ===");

        MarketplaceRestClient restClient = new MarketplaceRestClient();
        EnrichmentService enrichmentService = new EnrichmentService(restClient);
        JmsProducer producer = new JmsProducer();

        List<JsonObject> pedidos = restClient.obtenerPedidosDelDia();

        for (JsonObject pedido : pedidos) {
            JsonObject pedidoEnriquecido =
                    enrichmentService.enriquecerPedido(pedido);

            producer.enviarPedido(pedidoEnriquecido);
        }

        System.out.println("=== Procesamiento finalizado ===");
    }
}
