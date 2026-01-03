package cl.iplacex.technova.marketplace.adapter.translator;

import com.google.gson.Gson;

public class WebOrderTranslatorApp {

    public static void main(String[] args) {

        String xmlPedido = "<order>pedido web</order>";

        WebOrderTranslator translator = new WebOrderTranslator();
        CanonicalOrder canonicalOrder = translator.translate(xmlPedido);

        Gson gson = new Gson();
        String canonicalJson = gson.toJson(canonicalOrder);

        JmsProducer.send("lre_pedidos", canonicalJson);

        System.out.println("✅ Pedido WEB traducido y enviado");
    }
}
