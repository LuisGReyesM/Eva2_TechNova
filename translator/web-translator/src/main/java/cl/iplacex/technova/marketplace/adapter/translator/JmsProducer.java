package cl.iplacex.technova.marketplace.adapter.translator;

import jakarta.jms.Destination;
import jakarta.jms.JMSContext;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

/**
 * Utilidad estática para el envío de mensajes a ActiveMQ Artemis.
 * Permite despachar contenido de forma rápida a cualquier destino definido.
 */
public class JmsProducer {
    // Configuración de red para alcanzar el Broker de mensajería
    private static final String BROKER_URL = "tcp://192.168.1.167:61616";

    /**
     * Envía un mensaje de texto a la cola especificada.
     * @param queueName Nombre de la cola de destino (Endpoint).
     * @param message Contenido del mensaje (generalmente JSON o XML).
     */
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
