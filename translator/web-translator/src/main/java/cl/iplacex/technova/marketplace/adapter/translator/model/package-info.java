/**
 * Configuración global JAXB: Normaliza el formato XML eliminando
 * prefijos y Namespaces para maximizar la interoperabilidad
 * con sistemas receptores.
 */
@jakarta.xml.bind.annotation.XmlSchema(
        // Define el espacio de nombres (namespace) del XML. 
        // Al estar vacío (""), indica que el XML no usará un prefijo como <ns2:elemento>
        namespace = "",

        // Controla si los elementos hijos deben estar calificados con el namespace.
        // UNQUALIFIED significa que el XML será más limpio, sin repetir el namespace en cada etiqueta.
        elementFormDefault = jakarta.xml.bind.annotation.XmlNsForm.UNQUALIFIED
)
package cl.iplacex.technova.marketplace.adapter.translator.model;