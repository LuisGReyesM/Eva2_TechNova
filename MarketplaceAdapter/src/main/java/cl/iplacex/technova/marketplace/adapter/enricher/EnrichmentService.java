package cl.iplacex.technova.marketplace.adapter.enricher;

import cl.iplacex.technova.marketplace.adapter.client.MarketplaceRestClient;
import com.google.gson.JsonObject;

public class EnrichmentService {

    private final MarketplaceRestClient restClient;

    public EnrichmentService(MarketplaceRestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Aplica el patrón Content Enricher:
     * - Obtiene el costo de envío
     * - Lo inyecta al JSON original del pedido
     */
    public JsonObject enriquecerPedido(JsonObject pedidoOriginal) {

        String idPedido = pedidoOriginal
                .get("id")
                .getAsString();

        JsonObject costoEnvio =
                restClient.obtenerCostoEnvio(idPedido);

        pedidoOriginal.add("shipping", costoEnvio);

        return pedidoOriginal;
    }
}
