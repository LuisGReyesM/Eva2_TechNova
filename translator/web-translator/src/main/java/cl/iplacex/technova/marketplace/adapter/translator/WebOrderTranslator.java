package cl.iplacex.technova.marketplace.adapter.translator;

import cl.iplacex.technova.marketplace.adapter.translator.canonical.*;
import cl.iplacex.technova.marketplace.adapter.translator.model.*;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WebOrderTranslator {

    /**
     * Método público: recibe el XML como String
     */
    public CanonicalOrder translate(String xml) {
        WebPedidoXml webPedidoXml = unmarshall(xml);
        return translate(webPedidoXml);
    }

    /**
     * Traducción XML -> Canonical
     */
    private CanonicalOrder translate(WebPedidoXml webPedidoXml) {

        CanonicalOrder order = new CanonicalOrder();

        /* =======================
         * CABECERA
         * ======================= */
        Cabecera cabecera = new Cabecera();
        cabecera.setSistemaOrigen("WEB");
        cabecera.setIdPedidoExterno(UUID.randomUUID().toString());
        cabecera.setFechaPedido(webPedidoXml.getFecha());
        order.setCabecera(cabecera);

        /* =======================
         * CLIENTE (defensivo)
         * ======================= */
        if (webPedidoXml.getComprador() != null) {
            Cliente cliente = new Cliente();
            cliente.setNombreCompleto(webPedidoXml.getComprador().getNombre());
            cliente.setIdentificador(webPedidoXml.getComprador().getRut());

            DireccionXml dir = webPedidoXml.getDireccionDespacho();
            if (dir != null) {
                cliente.setDireccion(dir.getCalleYNumero() + ", " + dir.getComuna());
            }

            order.setCliente(cliente);
        } else {
            order.setCliente(new Cliente());
        }

        /* =======================
         * ITEMS
         * ======================= */
        List<Item> itemsCanonicos = new ArrayList<>();

        if (webPedidoXml.getItems() != null) {
            for (ItemXml itemXml : webPedidoXml.getItems()) {

                if (itemXml == null || itemXml.getProducto() == null) {
                    continue;
                }

                Item item = new Item();
                item.setSku(itemXml.getProducto().getSku());
                item.setNombre(itemXml.getProducto().getNombre());
                item.setCantidad(itemXml.getCantidad());
                item.setPrecioUnitario(itemXml.getProducto().getPrecioLista());

                if (itemXml.getProducto().getCategoria() != null) {
                    Categoria categoria = new Categoria();
                    categoria.setId(itemXml.getProducto().getCategoria().getId());
                    categoria.setNombre(itemXml.getProducto().getCategoria().getNombre());
                    item.setCategoria(categoria);
                }

                itemsCanonicos.add(item);
            }
        }

        /* =======================
         * FINANCIERO
         * ======================= */
        long subtotal = itemsCanonicos.stream()
                .mapToLong(i -> i.getPrecioUnitario() * i.getCantidad())
                .sum();

        Financiero financiero = new Financiero();
        financiero.setSubtotal(subtotal);
        financiero.setCostoEnvio(0);
        financiero.setTotalFinal(subtotal);

        Detalle detalle = new Detalle();
        detalle.setItems(itemsCanonicos);
        detalle.setFinanciero(financiero);
        order.setDetalle(detalle);

        return order;
    }

    /**
     * JAXB: XML String -> WebPedidoXml
     */
    private WebPedidoXml unmarshall(String xml) {
        try {
            System.out.println("====== XML RECIBIDO ======");
            System.out.println(xml);
            System.out.println("==========================");

            JAXBContext context = JAXBContext.newInstance(WebPedidoXml.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            WebPedidoXml pedido =
                    (WebPedidoXml) unmarshaller.unmarshal(new StringReader(xml));

            System.out.println("CLASE USADA POR JAXB: " + pedido.getClass().getName());
            System.out.println("Comprador JAXB: " + pedido.getComprador());
            System.out.println("Items JAXB: " + pedido.getItems());

            return pedido;
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir XML a objeto WebPedidoXml", e);
        }
    }

}
