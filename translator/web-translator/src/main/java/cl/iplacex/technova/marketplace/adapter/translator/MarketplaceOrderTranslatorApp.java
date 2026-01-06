package cl.iplacex.technova.marketplace.adapter.translator;

import cl.iplacex.technova.marketplace.adapter.translator.canonical.CanonicalOrder;
import com.google.gson.Gson;
/**
 * Aplicación principal del componente Traductor.
 * Actúa como un puente (Bridge) entre la cola de entrada del Marketplace
 * y la cola de pedidos estandarizados de la empresa.
 */
public class MarketplaceOrderTranslatorApp {

    public static void main(String[] args) {

        System.out.println("📥 Esperando mensaje desde lre_mkp_pedidos...");
        //RECEPCIÓN: Consume de forma síncrona el mensaje JSON desde ActiveMQ
        String jsonPedido = JmsConsumer.receive("lre_mkp_pedidos");
        // Validación de seguridad para evitar NullPointerException si la cola está vacía
        if (jsonPedido == null) {
            System.out.println("❌ No se recibió mensaje");
            return;
        }
        /*TRADUCCIÓN: Instancia el traductor de negocio para convertir el JSON
        al Modelo de Datos Canónico*/
        MarketplaceOrderTranslator translator = new MarketplaceOrderTranslator();
        CanonicalOrder canonicalOrder = translator.translate(jsonPedido);

        /*SERIALIZACIÓN: Convierte el objeto canónico de Java de vuelta a un
        String JSON estandarizado para su transporte*/
        Gson gson = new Gson();
        String canonicalJson = gson.toJson(canonicalOrder);

        //ENVÍO: Publica el mensaje ya normalizado en la cola interna de destino
        JmsProducer.send("lre_pedidos", canonicalJson);
        System.out.println("Cuerpo recibido: " + jsonPedido);

        System.out.println("✅ Pedido MARKETPLACE traducido y enviado a lre_pedidos");
    }
}
