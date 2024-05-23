/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.EstadoPedidoFacadeLocal;
import EJB.PedidoFacadeLocal;
import EJB.PersonaFacadeLocal;
import EJB.ProductoFacadeLocal;
import EJB.ProductoPedidoFacadeLocal;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Pedido;
import modelo.Persona;
import modelo.Producto;
import modelo.ProductoPedido;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

/**
 *
 * @author Miguel Ángel
 */

@Named
@ViewScoped
public class NuevoPedidoController implements Serializable {
    
    @EJB
    private PedidoFacadeLocal pedidoEJB;
    
    private Pedido pedido;
    
    @EJB
    private PersonaFacadeLocal clienteEJB;
    
    private String dniCliente;
    
    @EJB
    private EstadoPedidoFacadeLocal estadoPedidoEJB;
    
    @EJB
    private ProductoPedidoFacadeLocal productoPedidoEJB;
    
    @EJB
    private ProductoFacadeLocal productoEJB;
    
    private List<String> listaDescripcionProductos;
    
    private List<Producto> productosSelecionados;
    
    private String producto;
    
    private Producto productoSelecionado;
    
    private int cantidad;
    
    private double totalPagar;
    
    private Date fechaCreacion;
    
    @PostConstruct
    public void init() {
        pedido = new Pedido();
        
        listaDescripcionProductos = new ArrayList<>();
        
        productosSelecionados = new ArrayList<>();
        
        
        List<Producto> productosBD = productoEJB.findAll();
        for(Producto pActual : productosBD) {
            listaDescripcionProductos.add(pActual.getNombre());
        }
        
        producto = listaDescripcionProductos.get(0);
        actualizarProductoSeleccionado();
        
        fechaCreacion = new Date(System.currentTimeMillis());
    }
    
    public void actualizarProductoSeleccionado() {
        productoSelecionado = productoEJB.obtenerProductoPorNombre(producto);
    }
    
    public void agregarProductoLista() {
        Producto p = productoEJB.obtenerProductoPorNombre(producto);
        p.setCantidad(cantidad);
        productosSelecionados.add(p);
        totalPagar += cantidad*p.getPrecio()*(p.getIva()+100)/100;
    }
    
    public void realizarPedido() {
        Persona cliente = clienteEJB.obtenerPersonaPorDNI(dniCliente);
        pedido.setFechaCreacion(fechaCreacion);
        pedido.setCliente(cliente);
        pedido.setEstadoPedido(estadoPedidoEJB.obtenerEstadoPedidoPorDescripcion("Recibido"));
        try {
            pedidoEJB.create(pedido);
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Insercion correcta", "PRoducto registrado correctamente"));
        } catch (Exception e) {
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al insertar", "Error al registrar el producto"));
            System.out.println("Error al insertar el usuario " + e.getMessage());
        }
        guardarProductosPedidos();
    }
    
    public void guardarProductosPedidos() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "llega", "PRoducto registrado correctamente"));
        Persona cliente = clienteEJB.obtenerPersonaPorDNI(dniCliente);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, cliente.getNombre(), "PRoducto registrado correctamente"));
        //Guardar en productos pedidos
        for(Producto pActual : productosSelecionados) {
            ProductoPedido pp = new ProductoPedido();
            
            pp.setCantidad(pActual.getCantidad());
            pp.setPedido(pedidoEJB.obtenerPedidoPorFechaCreacionYUsuario(fechaCreacion, cliente));
            pp.setProducto(productoEJB.obtenerProductoPorNombre(pActual.getNombre()));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"si " + pp.getPedido().getIdPedido(), "PRoducto registrado correctamente"));
            try {
                productoPedidoEJB.create(pp);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Insercion correcta", "PRoducto registrado correctamente"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, e.toString(), "Error al registrar el producto"));
                System.out.println("Error al insertar el usuario " + e.getMessage());
            }
        }
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

   
    
    

    public PedidoFacadeLocal getPedidoEJB() {
        return pedidoEJB;
    }

    public void setPedidoEJB(PedidoFacadeLocal pedidoEJB) {
        this.pedidoEJB = pedidoEJB;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public PersonaFacadeLocal getClienteEJB() {
        return clienteEJB;
    }

    public void setClienteEJB(PersonaFacadeLocal clienteEJB) {
        this.clienteEJB = clienteEJB;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public EstadoPedidoFacadeLocal getEstadoPedidoEJB() {
        return estadoPedidoEJB;
    }

    public void setEstadoPedidoEJB(EstadoPedidoFacadeLocal estadoPedidoEJB) {
        this.estadoPedidoEJB = estadoPedidoEJB;
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

    public List<String> getListaDescripcionProductos() {
        return listaDescripcionProductos;
    }

    public void setListaDescripcionProductos(List<String> listaDescripcionProductos) {
        this.listaDescripcionProductos = listaDescripcionProductos;
    }

    public List<Producto> getProductosSelecionados() {
        return productosSelecionados;
    }

    public void setProductosSelecionados(List<Producto> productosSelecionados) {
        this.productosSelecionados = productosSelecionados;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Producto getProductoSelecionado() {
        return productoSelecionado;
    }

    public void setProductoSelecionado(Producto productoSelecionado) {
        this.productoSelecionado = productoSelecionado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(double totalPagar) {
        this.totalPagar = totalPagar;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.pedidoEJB);
        hash = 83 * hash + Objects.hashCode(this.pedido);
        hash = 83 * hash + Objects.hashCode(this.clienteEJB);
        hash = 83 * hash + Objects.hashCode(this.dniCliente);
        hash = 83 * hash + Objects.hashCode(this.estadoPedidoEJB);
        hash = 83 * hash + Objects.hashCode(this.productoPedidoEJB);
        hash = 83 * hash + Objects.hashCode(this.productoEJB);
        hash = 83 * hash + Objects.hashCode(this.listaDescripcionProductos);
        hash = 83 * hash + Objects.hashCode(this.productosSelecionados);
        hash = 83 * hash + Objects.hashCode(this.producto);
        hash = 83 * hash + Objects.hashCode(this.productoSelecionado);
        hash = 83 * hash + this.cantidad;
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.totalPagar) ^ (Double.doubleToLongBits(this.totalPagar) >>> 32));
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
        final NuevoPedidoController other = (NuevoPedidoController) obj;
        if (this.cantidad != other.cantidad) {
            return false;
        }
        if (Double.doubleToLongBits(this.totalPagar) != Double.doubleToLongBits(other.totalPagar)) {
            return false;
        }
        if (!Objects.equals(this.dniCliente, other.dniCliente)) {
            return false;
        }
        if (!Objects.equals(this.producto, other.producto)) {
            return false;
        }
        if (!Objects.equals(this.pedidoEJB, other.pedidoEJB)) {
            return false;
        }
        if (!Objects.equals(this.pedido, other.pedido)) {
            return false;
        }
        if (!Objects.equals(this.clienteEJB, other.clienteEJB)) {
            return false;
        }
        if (!Objects.equals(this.estadoPedidoEJB, other.estadoPedidoEJB)) {
            return false;
        }
        if (!Objects.equals(this.productoPedidoEJB, other.productoPedidoEJB)) {
            return false;
        }
        if (!Objects.equals(this.productoEJB, other.productoEJB)) {
            return false;
        }
        if (!Objects.equals(this.listaDescripcionProductos, other.listaDescripcionProductos)) {
            return false;
        }
        if (!Objects.equals(this.productosSelecionados, other.productosSelecionados)) {
            return false;
        }
        if (!Objects.equals(this.productoSelecionado, other.productoSelecionado)) {
            return false;
        }
        return true;
    }

    
    
}
