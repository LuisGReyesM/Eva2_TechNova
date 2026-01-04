package cl.iplacex.technova.marketplace.adapter.translator;

import jakarta.jms.*;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class JmsConsumer {

    private static final String BROKER_URL = "tcp://192.168.1.167:61616";

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
