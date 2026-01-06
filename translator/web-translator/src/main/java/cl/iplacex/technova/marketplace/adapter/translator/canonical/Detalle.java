package cl.iplacex.technova.marketplace.adapter.translator.canonical;

/**
 * Representa los metadatos estandarizados de un pedido.
 * Esta clase es parte del Modelo Canónico, asegurando que todos los pedidos
 * tengan la misma estructura básica independientemente de su origen.
 */
import java.util.List;

public class Detalle {

    private List<Item> items;
    private Financiero financiero;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Financiero getFinanciero() {
        return financiero;
    }

    public void setFinanciero(Financiero financiero) {
        this.financiero = financiero;
    }
}