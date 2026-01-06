package cl.iplacex.technova.marketplace.adapter.translator;

import jakarta.jms.*;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

/**
 * Consumidor JMS encargado de extraer mensajes desde el Broker (ActiveMQ).
 * Implementa una recepción de tipo bloqueante (síncrona).
 */
public class JmsConsumer {
    // Dirección del servidor de mensajería Artemis
    private static final String BROKER_URL = "tcp://192.168.1.167:61616";

    /**
     * Se conecta a una cola específica y extrae el primer mensaje disponible.
     * @param queueName Nombre de la cola de la cual se desea leer.
     * @return El contenido del mensaje en formato String o null si hay error.
     */
    public static String receive(String queueName) {

        try (ActiveMQConnectionFactory factory =
                     new ActiveMQConnectionFactory(BROKER_URL);
             JMSContext context = factory.createContext()) {

            Destination destination = context.createQueue(queueName);
            JMSConsumer consumer = context.createConsumer(destination);

            Message message = consumer.receive();

            if (message instanceof TextMessage textMessage) {
                return textMessage.getText();
            }

        } catch (Exception e) {
            System.err.println("Error JMS Consumer: " + e.getMessage());
        }

        return null;
    }
}
