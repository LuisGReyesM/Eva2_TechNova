package cl.iplacex.technova.marketplace.adapter.messaging;

import com.google.gson.JsonObject;
import jakarta.jms.Destination;
import jakarta.jms.JMSContext;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

/**
 * Clase encargada de enviar mensajes a una cola de mensajería (ActiveMQ).
 */
public class JmsProducer {
    // Configuración del Broker: Dirección IP y puerto donde corre Artemis
    private static final String BROKER_URL = "tcp://192.168.1.167:61616";
    // Nombre de la cola de destino para los pedidos
    private static final String QUEUE_NAME = "lre_mkp_pedidos";
    /**
     * Envía un objeto JSON como texto a la cola definida.
     * @param pedido Objeto Json que contiene la información de la orden.
     */
    public void enviarPedido(JsonObject pedido) {

        try (ActiveMQConnectionFactory factory =
                     new ActiveMQConnectionFactory(BROKER_URL);
             JMSContext context = factory.createContext()) {

            Destination destination = context.createQueue(QUEUE_NAME);

            context.createProducer()
                    .send(destination, pedido.toString());

            System.out.println("📨 Mensaje enviado a cola " + QUEUE_NAME);

        } catch (Exception e) {
            throw new RuntimeException("Error enviando mensaje JMS", e);
        }
    }
}
