package cl.iplacex.technova.marketplace.adapter.translator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cl.iplacex.technova.marketplace.adapter.translator.canonical.*;

/**
 * Traductor encargado de transformar el JSON crudo del Marketplace
 * al Modelo de Datos Canónico de la empresa.
 */
public class MarketplaceOrderTranslator {
    /**
     * Mapea los campos del JSON a objetos Java estandarizados.
     * @param json Cadena de texto en formato JSON recibida de la cola.
     * @return Objeto CanonicalOrder listo para ser procesado internamente.
     */
    public CanonicalOrder translate(String json) {

        JsonObject original = JsonParser.parseString(json).getAsJsonObject();

        // === CABECERA ===
        // Normalizamos los metadatos del pedido
        Cabecera cabecera = new Cabecera();
        cabecera.setSistemaOrigen("MARKETPLACE");
        cabecera.setIdPedidoExterno(original.get("id").getAsString());
        cabecera.setFechaPedido(original.get("fecha").getAsString());

        // === CLIENTE ===
        // Combinamos datos de diferentes nodos JSON en un único objeto Cliente
        JsonObject clienteJson = original.getAsJsonObject("cliente");
        JsonObject direccionJson = original.getAsJsonObject("direccion");

        Cliente cliente = new Cliente();
        cliente.setNombreCompleto(
                clienteJson.get("nombre").getAsString() + " " +
                        clienteJson.get("apellido").getAsString()
        );
        cliente.setIdentificador(clienteJson.get("rut").getAsString());
        cliente.setDireccion(
                direccionJson.get("calle").getAsString() + " " +
                        direccionJson.get("numero").getAsString() + ", " +
                        direccionJson.get("comuna").getAsString()
        );

        // === DETALLE ===
        Detalle detalle = new Detalle();

        long subtotal = 0;
        // Iteramos por la lista de productos para calcular totales y mapear items
        for (var itemElem : original.getAsJsonArray("items")) {
            JsonObject itemJson = itemElem.getAsJsonObject();

            Item item = new Item();
            item.setNombre(itemJson.get("producto").getAsString());
            item.setCantidad(itemJson.get("cantidad").getAsInt());
            item.setPrecioUnitario(itemJson.get("precioUnitario").getAsLong());

            subtotal += item.getCantidad() * item.getPrecioUnitario();
            detalle.getItems().add(item);
        }
        // === SECCIÓN 4: INFORMACIÓN FINANCIERA ===
        // Aquí se integra el dato que inyectamos antes con el Content Enricher
        JsonObject shippingJson = original.getAsJsonObject("shipping");
        long costoEnvio = shippingJson.get("costoEnvio").getAsLong();

        Financiero financiero = new Financiero();
        financiero.setSubtotal(subtotal);
        financiero.setCostoEnvio(costoEnvio);
        financiero.setTotalFinal(subtotal + costoEnvio);

        detalle.setFinanciero(financiero);

        // === RESULTADO FINAL: ENSAMBLE DEL OBJETO CANÓNICO ===
        CanonicalOrder order = new CanonicalOrder();
        order.setCabecera(cabecera);
        order.setCliente(cliente);
        order.setDetalle(detalle);

        return order;
    }
}