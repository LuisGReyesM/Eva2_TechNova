package cl.iplacex.technova.marketplace.adapter.translator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cl.iplacex.technova.marketplace.adapter.translator.canonical.*;

public class MarketplaceOrderTranslator {

    public CanonicalOrder translate(String json) {

        JsonObject original = JsonParser.parseString(json).getAsJsonObject();

        // === CABECERA ===
        Cabecera cabecera = new Cabecera();
        cabecera.setSistemaOrigen("MARKETPLACE");
        cabecera.setIdPedidoExterno(original.get("id").getAsString());
        cabecera.setFechaPedido(original.get("fecha").getAsString());

        // === CLIENTE ===
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

        for (var itemElem : original.getAsJsonArray("items")) {
            JsonObject itemJson = itemElem.getAsJsonObject();

            Item item = new Item();
            item.setNombre(itemJson.get("producto").getAsString());
            item.setCantidad(itemJson.get("cantidad").getAsInt());
            item.setPrecioUnitario(itemJson.get("precioUnitario").getAsLong());

            subtotal += item.getCantidad() * item.getPrecioUnitario();
            detalle.getItems().add(item);
        }

        JsonObject shippingJson = original.getAsJsonObject("shipping");
        long costoEnvio = shippingJson.get("costoEnvio").getAsLong();

        Financiero financiero = new Financiero();
        financiero.setSubtotal(subtotal);
        financiero.setCostoEnvio(costoEnvio);
        financiero.setTotalFinal(subtotal + costoEnvio);

        detalle.setFinanciero(financiero);

        // === CANONICAL ===
        CanonicalOrder order = new CanonicalOrder();
        order.setCabecera(cabecera);
        order.setCliente(cliente);
        order.setDetalle(detalle);

        return order;
    }
}