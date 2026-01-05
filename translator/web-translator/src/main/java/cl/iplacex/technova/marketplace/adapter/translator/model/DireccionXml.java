package cl.iplacex.technova.marketplace.adapter.translator.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class DireccionXml {
    @XmlElement(name = "calleYNumero")
    private String calleYNumero;
    @XmlElement(name = "comuna")
    private String comuna;

    public String getCalleYNumero() { return calleYNumero; }
    public void setCalleYNumero(String calleYNumero) { this.calleYNumero = calleYNumero; }

    public String getComuna() { return comuna; }
    public void setComuna(String comuna) { this.comuna = comuna; }
}
