package cl.iplacex.technova.marketplace.adapter.translator;

import cl.iplacex.technova.marketplace.adapter.translator.canonical.CanonicalOrder;
import com.google.gson.Gson;

public class WebOrderTranslatorApp {

    public static void main(String[] args) {

        System.out.println("📥 Esperando mensaje desde lre_web_pedidos...");

        String xmlPedido = JmsConsumer.receive("lre_web_pedidos");

        if (xmlPedido == null) {
            System.out.println("❌ No se recibió mensaje");
            return;
        }

        WebOrderTranslator translator = new WebOrderTranslator();
        CanonicalOrder canonicalOrder = translator.translate(xmlPedido);

        Gson gson = new Gson();
        String canonicalJson = gson.toJson(canonicalOrder);


        JmsProducer.send("lre_pedidos", canonicalJson);

        System.out.println(canonicalJson);

        System.out.println("✅ Pedido WEB traducido y enviado a lre_pedidos");
    }
}
