package cl.iplacex.technova.marketplace.adapter.messaging;

import com.google.gson.JsonObject;
import jakarta.jms.Destination;
import jakarta.jms.JMSContext;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class JmsProducer {

    private static final String BROKER_URL = "tcp://192.168.1.167:61616";
    private static final String QUEUE_NAME = "lre_mkp_pedidos";

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
