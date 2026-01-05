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

        System.out.println("📥 Mensaje recibido desde lre_web_pedidos");
        System.out.println("📄 XML recibido desde Tienda Web");

        System.out.println("🔄 Transformando pedido XML a modelo canónico...");
        WebOrderTranslator translator = new WebOrderTranslator();
        CanonicalOrder canonicalOrder = translator.translate(xmlPedido);

        Gson gson = new Gson();
        String canonicalJson = gson.toJson(canonicalOrder);

        System.out.println("📨 Enviando pedido canónico a lre_pedidos...");
        JmsProducer.send("lre_pedidos", canonicalJson);

        System.out.println("📦 Payload canónico generado:");
        System.out.println(canonicalJson);

        System.out.println("✅ Pedido WEB traducido y enviado a lre_pedidos");
    }
}