package cl.iplacex.technova.marketplace.adapter.messaging;

import com.google.gson.JsonObject;
import jakarta.jms.*;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class JmsProducer {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String QUEUE_NAME = "lrm_mkp_pedidos";

    public void enviarPedido(JsonObject pedido) {

        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(BROKER_URL);

        try (
                Connection connection = connectionFactory.createConnection();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)
        ) {
            Destination destination = session.createQueue(QUEUE_NAME);
            MessageProducer producer = session.createProducer(destination);

            TextMessage message =
                    session.createTextMessage(pedido.toString());

            producer.send(message);

            System.out.println("Mensaje enviado a cola " + QUEUE_NAME);

        } catch (JMSException e) {
            throw new RuntimeException("Error enviando mensaje JMS", e);
        }
    }
}
