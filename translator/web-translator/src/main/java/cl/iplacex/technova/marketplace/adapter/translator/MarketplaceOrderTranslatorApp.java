package cl.iplacex.technova.marketplace.adapter.translator;

import cl.iplacex.technova.marketplace.adapter.translator.canonical.CanonicalOrder;
import com.google.gson.Gson;

public class MarketplaceOrderTranslatorApp {

    public static void main(String[] args) {

        System.out.println("📥 Esperando mensaje desde lre_mkp_pedidos...");

        String jsonPedido = JmsConsumer.receive("lre_mkp_pedidos");

        if (jsonPedido == null) {
            System.out.println("❌ No se recibió mensaje");
            return;
        }

        MarketplaceOrderTranslator translator = new MarketplaceOrderTranslator();
        CanonicalOrder canonicalOrder = translator.translate(jsonPedido);

        Gson gson = new Gson();
        String canonicalJson = gson.toJson(canonicalOrder);

        JmsProducer.send("lre_pedidos", canonicalJson);
        System.out.println("Cuerpo recibido: " + jsonPedido);

        System.out.println("✅ Pedido MARKETPLACE traducido y enviado a lre_pedidos");
    }
}
