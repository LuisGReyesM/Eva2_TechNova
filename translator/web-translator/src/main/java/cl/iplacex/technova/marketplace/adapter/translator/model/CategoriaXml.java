package cl.iplacex.technova.marketplace.adapter.translator.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Representa la categoría de un producto en formato XML.
 * Utiliza anotaciones JAXB para definir cómo se transformará de Objeto a XML y viceversa.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class CategoriaXml {

    private int id;
    private String nombre;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
