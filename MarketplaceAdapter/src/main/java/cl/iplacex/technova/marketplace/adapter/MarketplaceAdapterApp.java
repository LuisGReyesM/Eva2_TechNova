package cl.iplacex.technova.marketplace.adapter;

import cl.iplacex.technova.marketplace.adapter.client.MarketplaceRestClient;
import cl.iplacex.technova.marketplace.adapter.enricher.EnrichmentService;
import cl.iplacex.technova.marketplace.adapter.messaging.JmsProducer;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * Clase principal que ejecuta el proceso de integración.
 * Actúa como el motor que mueve los datos desde el origen
 * hacia el destino (Cola de Mensajería).
 */
public class MarketplaceAdapterApp {

    public static void main(String[] args) {

        System.out.println("=== Iniciando Marketplace Adapter ===");

        MarketplaceRestClient restClient = new MarketplaceRestClient();
        // Servicio que añade datos extra
        EnrichmentService enrichmentService = new EnrichmentService(restClient);
        // Productor encargado de enviar el resultado final a la cola JMS
        JmsProducer producer = new JmsProducer();
        // 3. TRANSFORMACIÓN Y ENVÍO: Iteramos sobre cada pedido encontrado
        List<JsonObject> pedidos = restClient.obtenerPedidosDelDia();

        for (JsonObject pedido : pedidos) {
            JsonObject pedidoEnriquecido =
                    enrichmentService.enriquecerPedido(pedido);

            producer.enviarPedido(pedidoEnriquecido);
        }

        System.out.println("=== Procesamiento finalizado ===");
    }
}
