package cl.iplacex.technova.marketplace.adapter.translator.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class ProductoXml {
    @XmlElement(name = "sku")
    private String sku;
    @XmlElement(name = "nombre")
    private String nombre;
    @XmlElement(name = "precioLista")
    private long precioLista;
    @XmlElement(name = "categoria")
    private CategoriaXml categoria;

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public long getPrecioLista() { return precioLista; }
    public void setPrecioLista(long precioLista) { this.precioLista = precioLista; }

    public CategoriaXml getCategoria() { return categoria; }
    public void setCategoria(CategoriaXml categoria) { this.categoria = categoria; }
}
