package cl.iplacex.technova.marketplace.adapter.translator.canonical;

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
