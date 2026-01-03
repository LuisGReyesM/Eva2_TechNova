package cl.iplacex.technova.marketplace.adapter.translator;

import jakarta.jms.Destination;
import jakarta.jms.JMSContext;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;


public class JmsProducer {

    private static final String BROKER_URL = "tcp://192.168.1.167:61616";

    public static void send(String queueName, String message) {

        try (ActiveMQConnectionFactory factory =
                     new ActiveMQConnectionFactory(BROKER_URL);
             JMSContext context = factory.createContext()) {

            Destination destination = context.createQueue(queueName);
            context.createProducer().send(destination, message);

            System.out.println("📨 Mensaje enviado a " + queueName);

        } catch (Exception e) {
            System.err.println("Error JMS: " + e.getMessage());
        }
    }
}
