/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.EstadoPedidoFacadeLocal;
import EJB.PedidoFacadeLocal;
import EJB.ProductoFacadeLocal;
import EJB.ProductoPedidoFacadeLocal;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.EstadoPedido;
import modelo.Pedido;
import modelo.Producto;
import modelo.ProductoPedido;

/**
 *
 * @author Miguel Ángel
 */

@Named
@ViewScoped
public class EditarPedidoController implements Serializable {
    
    @Inject
    private ListarPedidosController listPedCon;
    
    @EJB
    private PedidoFacadeLocal pedidoEJB;
    
    @EJB
    private EstadoPedidoFacadeLocal estadoPedidoEJB;
    
    @EJB
    private ProductoPedidoFacadeLocal productoPedidoEJB;
    
    @EJB 
    private ProductoFacadeLocal productoEJB;
    
    private Pedido pedido;
    
    private List<Producto> listaProductos;
    
    private List<String> listaEstados;
    
    private String estado;
    
    @PostConstruct
    public void init() {
        pedido = listPedCon.getPedido();
        
        listaProductos = obtenerProductosPedido(pedido.getIdPedido());
        
        estado = pedido.getEstadoPedido().getDescripcion();
        
        listaEstados = new ArrayList<>();
        
        List<EstadoPedido> estadoPedidoBD = estadoPedidoEJB.findAll();
        for(EstadoPedido epActual : estadoPedidoBD) {
            listaEstados.add(epActual.getDescripcion());
        }
    }
    
    public String actualizarPedido() {
        String navegacion = "visualizarPedidosAsignados.xhtml";
        if(estado.equalsIgnoreCase("Recibido")) {
            pedido.setEmpleado(null);
        } else if(estado.equalsIgnoreCase("Enviado")) {
            LocalDateTime ld = LocalDateTime.now();
            pedido.setFechaEnvio(new Date(ld.getYear()-1900, ld.getMonthValue()-1, ld.getDayOfMonth(), ld.getHour(), ld.getMinute(), ld.getSecond()));
        }
        pedido.setEstadoPedido(estadoPedidoEJB.obtenerEstadoPedidoPorDescripcion(estado));
        try {
            pedidoEJB.edit(pedido);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualización correcta", "Producto actualizado correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al actualizar", "Error al actualizar el producto"));
            System.out.println("Error al insertar la publicación " + e.getMessage());
        }
        
        return navegacion;
    }
    
    public List<Producto> obtenerProductosPedido(int idPedido) {
        List<Producto> productos = new ArrayList<>();
        List<ProductoPedido> pp = productoPedidoEJB.obtenerProductosPedidosPorPedido(idPedido);
        for(ProductoPedido ppActual : pp) {
            Producto p = productoEJB.obtenerProductoPorID(ppActual.getProducto().getIdProducto());
            p.setCantidad(ppActual.getCantidad());
            productos.add(p);        
        }
        return productos;
    }

    public ListarPedidosController getListPedCon() {
        return listPedCon;
    }

    public void setListPedCon(ListarPedidosController listPedCon) {
        this.listPedCon = listPedCon;
    }

    public PedidoFacadeLocal getPedidoEJB() {
        return pedidoEJB;
    }

    public void setPedidoEJB(PedidoFacadeLocal pedidoEJB) {
        this.pedidoEJB = pedidoEJB;
    }

    public EstadoPedidoFacadeLocal getEstadoPedidoEJB() {
        return estadoPedidoEJB;
    }

    public void setEstadoPedidoEJB(EstadoPedidoFacadeLocal estadoPedidoEJB) {
        this.estadoPedidoEJB = estadoPedidoEJB;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<String> getListaEstados() {
        return listaEstados;
    }

    public void setListaEstados(List<String> listaEstados) {
        this.listaEstados = listaEstados;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ProductoPedidoFacadeLocal getProductoPedidoEJB() {
        return productoPedidoEJB;
    }

    public void setProductoPedidoEJB(ProductoPedidoFacadeLocal productoPedidoEJB) {
        this.productoPedidoEJB = productoPedidoEJB;
    }

    public ProductoFacadeLocal getProductoEJB() {
        return productoEJB;
    }

    public void setProductoEJB(ProductoFacadeLocal productoEJB) {
        this.productoEJB = productoEJB;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.listPedCon);
        hash = 29 * hash + Objects.hashCode(this.pedidoEJB);
        hash = 29 * hash + Objects.hashCode(this.estadoPedidoEJB);
        hash = 29 * hash + Objects.hashCode(this.pedido);
        hash = 29 * hash + Objects.hashCode(this.listaEstados);
        hash = 29 * hash + Objects.hashCode(this.estado);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EditarPedidoController other = (EditarPedidoController) obj;
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.listPedCon, other.listPedCon)) {
            return false;
        }
        if (!Objects.equals(this.pedidoEJB, other.pedidoEJB)) {
            return false;
        }
        if (!Objects.equals(this.estadoPedidoEJB, other.estadoPedidoEJB)) {
            return false;
        }
        if (!Objects.equals(this.pedido, other.pedido)) {
            return false;
        }
        if (!Objects.equals(this.listaEstados, other.listaEstados)) {
            return false;
        }
        return true;
    }
    
    
}
