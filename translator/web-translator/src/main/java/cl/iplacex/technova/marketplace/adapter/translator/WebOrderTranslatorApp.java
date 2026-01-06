package cl.iplacex.technova.marketplace.adapter.translator;

import cl.iplacex.technova.marketplace.adapter.translator.canonical.CanonicalOrder;
import com.google.gson.Gson;

/**
 *  * Aplicación principal del componente Traductor.
 * Se encarga de la recepción XML y su posterior normalización a JSON Canónico.
 */
public class WebOrderTranslatorApp {

    public static void main(String[] args) {

        System.out.println("📥 Esperando mensaje desde lre_web_pedidos...");
        //RECEPCIÓN: Obtiene el mensaje XML crudo desde la cola de ActiveMQ
        String xmlPedido = JmsConsumer.receive("lre_web_pedidos");

        if (xmlPedido == null) {
            System.out.println("❌ No se recibió mensaje");
            return;
        }
        System.out.println("📥 Mensaje recibido desde lre_web_pedidos");
        System.out.println("📄 XML recibido desde Tienda Web");

        //TRANSFORMACIÓN: Usa el WebOrderTranslator para ejecutar el Unmarshalling JAXB
        System.out.println("🔄 Transformando pedido XML a modelo canónico...");
        WebOrderTranslator translator = new WebOrderTranslator();
        CanonicalOrder canonicalOrder = translator.translate(xmlPedido);

        //SERIALIZACIÓN: Convierte el objeto canónico de Java a formato JSON
        Gson gson = new Gson();
        String canonicalJson = gson.toJson(canonicalOrder);

        //ENVÍO: Publica el resultado en la cola central de pedidos procesados
        System.out.println("📨 Enviando pedido canónico a lre_pedidos...");
        JmsProducer.send("lre_pedidos", canonicalJson);

        //// Log de auditoría para visualizar el objeto final transformado
        System.out.println("📦 Payload canónico generado:");
        System.out.println(canonicalJson);

        System.out.println("✅ Pedido WEB traducido y enviado a lre_pedidos");
    }
}