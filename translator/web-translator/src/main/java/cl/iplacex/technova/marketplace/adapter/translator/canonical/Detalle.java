package cl.iplacex.technova.marketplace.adapter.translator.canonical;


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