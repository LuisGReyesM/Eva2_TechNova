package cl.iplacex.technova.marketplace.adapter.translator.canonical;
/**
 * Representa los metadatos estandarizados de un pedido.
 * Esta clase es parte del Modelo Canónico, asegurando que todos los pedidos
 * tengan la misma estructura básica independientemente de su origen.
 */
public class Cabecera {

    private String sistemaOrigen;
    private String idPedidoExterno;
    private String fechaPedido;

    public String getSistemaOrigen() {
        return sistemaOrigen;
    }

    public void setSistemaOrigen(String sistemaOrigen) {
        this.sistemaOrigen = sistemaOrigen;
    }

    public String getIdPedidoExterno() {
        return idPedidoExterno;
    }

    public void setIdPedidoExterno(String idPedidoExterno) {
        this.idPedidoExterno = idPedidoExterno;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }
}
