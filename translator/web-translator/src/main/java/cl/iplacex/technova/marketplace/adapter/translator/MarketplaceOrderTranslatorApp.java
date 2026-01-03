package cl.iplacex.technova.marketplace.adapter.translator;

import com.google.gson.Gson;

public class MarketplaceOrderTranslatorApp {

    public static void main(String[] args) {

        String jsonPedido = "{ \"id\": \"123\" }";

        MarketplaceOrderTranslator translator = new MarketplaceOrderTranslator();
        CanonicalOrder canonicalOrder = translator.translate(jsonPedido);

        Gson gson = new Gson();
        String canonicalJson = gson.toJson(canonicalOrder);

        JmsProducer.send("lre_pedidos", canonicalJson);

        System.out.println("✅ Pedido MARKETPLACE traducido y enviado");
    }
}
