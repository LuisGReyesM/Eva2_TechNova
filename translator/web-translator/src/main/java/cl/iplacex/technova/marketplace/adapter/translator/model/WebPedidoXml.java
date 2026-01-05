package cl.iplacex.technova.marketplace.adapter.translator.model;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "pedido")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class WebPedidoXml {
    @XmlElement(name = "fecha")
    private String fecha;

    @XmlElement(name = "comprador")
    private ClienteXml comprador;

    @XmlElement(name = "direccionDespacho")
    private DireccionXml direccionDespacho;

    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    private List<ItemXml> items;

    // Getters y Setters
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public ClienteXml getComprador() { return comprador; }
    public void setComprador(ClienteXml comprador) { this.comprador = comprador; }

    public DireccionXml getDireccionDespacho() { return direccionDespacho; }
    public void setDireccionDespacho(DireccionXml direccionDespacho) { this.direccionDespacho = direccionDespacho; }

    public List<ItemXml> getItems() { return items; }
    public void setItems(List<ItemXml> items) { this.items = items; }
}
