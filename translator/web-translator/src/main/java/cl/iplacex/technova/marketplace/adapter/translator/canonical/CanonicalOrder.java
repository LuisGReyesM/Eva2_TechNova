package cl.iplacex.technova.marketplace.adapter.translator.canonical;

/**
 * Representa los metadatos estandarizados de un pedido.
 * Esta clase es parte del Modelo Canónico, asegurando que todos los pedidos
 * tengan la misma estructura básica independientemente de su origen.
 */
public class CanonicalOrder {

    private Cabecera cabecera;
    private Cliente cliente;
    private Detalle detalle;

    public Cabecera getCabecera() {
        return cabecera;
    }

    public void setCabecera(Cabecera cabecera) {
        this.cabecera = cabecera;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Detalle getDetalle() {
        return detalle;
    }

    public void setDetalle(Detalle detalle) {
        this.detalle = detalle;
    }
}