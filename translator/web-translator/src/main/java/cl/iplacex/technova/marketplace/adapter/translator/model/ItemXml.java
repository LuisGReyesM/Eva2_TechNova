package cl.iplacex.technova.marketplace.adapter.translator.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class ItemXml {
    @XmlElement(name = "producto")
    private ProductoXml producto;
    @XmlElement(name = "cantidad")
    private int cantidad;

    public ProductoXml getProducto() { return producto; }
    public void setProducto(ProductoXml producto) { this.producto = producto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
