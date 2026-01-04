package cl.iplacex.technova.marketplace.adapter.translator.canonical;

public class Financiero {

    private long subtotal;
    private long costoEnvio;
    private long totalFinal;

    public long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(long subtotal) {
        this.subtotal = subtotal;
    }

    public long getCostoEnvio() {
        return costoEnvio;
    }

    public void setCostoEnvio(long costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public long getTotalFinal() {
        return totalFinal;
    }

    public void setTotalFinal(long totalFinal) {
        this.totalFinal = totalFinal;
    }
}