package cl.iplacex.technova.marketplace.adapter.facturacion;

import jakarta.jms.*;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import com.google.gson.Gson;
import cl.iplacex.technova.marketplace.adapter.translator.canonical.CanonicalOrder;

// Asegúrate de que estos imports coincidan con la ubicación de tus clases generadas por CXF
import cl.iplacex.technova.marketplace.adapter.facturacion.client.ServicioFacturacion;
import cl.iplacex.technova.marketplace.adapter.facturacion.client.Servicio;

public class FacturacionApp {

    public static void main(String[] args) {
        // 1. Configuración de conexión al Broker Artemis
        String brokerUrl = "tcp://192.168.1.167:61616";
        String queueName = "lre_pedidos";

        try (ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
             Connection connection = factory.createConnection()) {

            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);
            MessageConsumer consumer = session.createConsumer(queue);

            System.out.println("🚀 [ADAPTER] Escuchando pedidos canónicos en la cola: " + queueName);

            // 2. Definición del MessageListener para procesamiento asíncrono
            consumer.setMessageListener(message -> {
                try {
                    if (message instanceof TextMessage textMessage) {
                        // Recepción del mensaje en formato Canónico (JSON)
                        String json = textMessage.getText();
                        CanonicalOrder orden = new Gson().fromJson(json, CanonicalOrder.class);

                        // Extracción de datos para el sistema legado
                        String id = orden.getCabecera().getIdPedidoExterno();
                        String rut = orden.getCliente().getIdentificador();
                        String nombre = orden.getCliente().getNombreCompleto();
                        long total = orden.getDetalle().getFinanciero().getTotalFinal();

                        System.out.println("\n📦 Pedido recibido ID: " + id);

                        // 3. Invocación del servicio legado SOAP
                        invocarSoap(nombre, rut, total);
                    }
                } catch (Exception e) {
                    System.err.println("❌ Error procesando el mensaje: " + e.getMessage());
                }
            });

            // Mantiene la aplicación corriendo para seguir escuchando la cola
            Thread.currentThread().join();

        } catch (Exception e) {
            System.err.println("❌ Error crítico en el Adapter: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método que actúa como el Adapter hacia el sistema legado SOAP.
     * Utiliza las clases generadas (Stub) para realizar la comunicación RPC.
     */
    private static void invocarSoap(String cliente, String rut, long monto) {
        try {
            // Instanciación del cliente SOAP generado
            ServicioFacturacion service = new ServicioFacturacion();
            Servicio port = service.getServicioImplPort();

            System.out.println("📡 Enviando datos al Sistema Legado de Facturación...");

            // Consumo del servicio web SOAP
            String xmlBoleta = port.generarBoleta(cliente, rut, monto);

            System.out.println("✅ Documento Tributario generado con éxito.");
            System.out.println("📄 Respuesta Legada (DTE):\n" + xmlBoleta);
            System.out.println("--------------------------------------------------");

        } catch (Exception e) {
            System.err.println("❌ Error de comunicación SOAP con el sistema legado: " + e.getMessage());
        }
    }
}