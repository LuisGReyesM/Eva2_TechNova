package cl.iplacex.technova.marketplace.adapter.translator;

public class WebOrderTranslator {

    public CanonicalOrder translate(String xmlOrder) {

        CanonicalOrder order = new CanonicalOrder();
        order.setSistemaOrigen("WEB");
        order.setIdPedido("WEB-001");
        order.setTotal(1800000);

        return order;
    }
}
