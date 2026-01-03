package cl.iplacex.technova.marketplace.adapter.translator;

public class MarketplaceOrderTranslator {

    public CanonicalOrder translate(String jsonOrder) {

        CanonicalOrder order = new CanonicalOrder();
        order.setSistemaOrigen("MARKETPLACE");
        order.setIdPedido("MKP-001");
        order.setTotal(1805000);

        return order;
    }
}
