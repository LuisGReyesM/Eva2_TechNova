package cl.iplacex.technova.marketplace.adapter.facturacion;

import jakarta.jms.*;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import com.google.gson.Gson;
import cl.iplacex.technova.marketplace.adapter.translator.canonical.CanonicalOrder;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Adapter de Facturación: Consume mensajes en formato Canónico (JSON) [cite: 152]
 * y realiza la integración con el sistema legado mediante SOAP[cite: 150].
 */
public class FacturacionApp {

    public static void main(String[] args) {

        // Configuración de conexión al Broker Artemis [cite: 43]
        String brokerUrl = "tcp://192.168.1.167:61616";
        String queueName = "lre_pedidos"; // Canal central unificado [cite: 58, 69]

        try (ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
             Connection connection = factory.createConnection()) {

            connection.start();

            // Configuración de la sesión y consumidor Jakarta Messaging
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);
            MessageConsumer consumer = session.createConsumer(queue);

            // Implementación del Messaging Endpoint
            consumer.setMessageListener(message -> {
                try {
                    if (message instanceof TextMessage textMessage) {

                        String jsonBody = textMessage.getText();
                        Gson gson = new Gson();

                        // 1. Extraer información desde el Modelo Canónico diseñado [cite: 152]
                        CanonicalOrder orden = gson.fromJson(jsonBody, CanonicalOrder.class);

                        String idPedido = orden.getCabecera().getIdPedidoExterno();
                        String rutCliente = orden.getCliente().getIdentificador();
                        long totalFinal = orden.getDetalle().getFinanciero().getTotalFinal();

                        System.out.println("\n📥 [MENSAJE RECIBIDO EN CANAL CENTRAL]");
                        System.out.println("ID Pedido: " + idPedido);
                        System.out.println("Monto a Facturar: $" + totalFinal);

                        // 2. Realizar la integración real con el servicio SOAP [cite: 152]
                        consumirServicioSoapFacturacion(rutCliente, totalFinal, idPedido);
                    }

                } catch (Exception e) {
                    System.err.println("❌ Error procesando mensaje de facturación");
                    e.printStackTrace();
                }
            });

            System.out.println("📑 Adapter de Facturación activo. Escuchando: " + queueName);

            // Mantener la ejecución para el MessageListener
            Thread.currentThread().join();

        } catch (Exception e) {
            System.err.println("❌ Error de conexión con el Broker");
            e.printStackTrace();
        }
    }

    /**
     * Realiza la llamada al servicio web SOAP de Facturación[cite: 152].
     */
    private static void consumirServicioSoapFacturacion(String rut, long monto, String id) {
        // Endpoint del sistema legado proporcionado [cite: 38, 150]
        String soapEndpoint = "http://localhost:8090/soap/facturacion";

        // Construcción del XML SOAP Envelope para el sistema legado [cite: 8, 152]
        String soapEnvelope =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                        "xmlns:ser=\"http://example.org/\">" + // Namespace extraído de tu consola
                        "   <soapenv:Header/>" +
                        "   <soapenv:Body>" +
                        "      <ser:ServicioFacturacion>" + // Nombre del método según el log
                        "         <arg0>" + id + "</arg0>" +    // Los sistemas legados suelen usar arg0 para ID
                        "         <arg1>" + rut + "</arg1>" +   // arg1 para RUT
                        "         <arg2>" + monto + "</arg2>" + // arg2 para Monto
                        "      </ser:ServicioFacturacion>" +
                        "   </soapenv:Body>" +
                        "</soapenv:Envelope>";

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(soapEndpoint))
                    .header("Content-Type", "text/xml; charset=utf-8")
                    .POST(HttpRequest.BodyPublishers.ofString(soapEnvelope))
                    .build();

            System.out.println("📡 Enviando solicitud SOAP al sistema de Facturación...");
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("✅ [SOAP SUCCESS] Documento emitido para: " + rut);
                System.out.println("📄 Respuesta: " + response.body());
            } else {
                System.err.println("⚠️ [SOAP WARNING] El servicio respondió con código: " + response.statusCode());
            }
            System.out.println("--------------------------------------------------");

        } catch (Exception e) {
            System.err.println("❌ Error de comunicación con el servicio SOAP legado: " + e.getMessage());
        }
    }
}