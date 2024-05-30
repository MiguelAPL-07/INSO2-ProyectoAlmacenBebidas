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
import javax.inject.Named;
import modelo.Pedido;
import modelo.Persona;
import modelo.Producto;
import modelo.ProductoPedido;
import modelo.Usuario;

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
    
    private String cantidad;
    
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
        
        LocalDateTime ld = LocalDateTime.now();
        fechaCreacion = new Date(ld.getYear()-1900, ld.getMonthValue()-1, ld.getDayOfMonth(), ld.getHour(), ld.getMinute(), ld.getSecond());
    }
    
    public void actualizarProductoSeleccionado() {
        productoSelecionado = productoEJB.obtenerProductoPorNombre(producto);
        cantidad = "";
    }
    
    public void eliminarProductoSeleccionadoLista(String nombre, int cantidad) {
        for(int i = 0; i < productosSelecionados.size(); i++) {
            if(productosSelecionados.get(i).getNombre().equalsIgnoreCase(nombre) && productosSelecionados.get(i).getCantidad() == cantidad) {
                totalPagar -= productosSelecionados.get(i).getCantidad()*productosSelecionados.get(i).getPrecio()*(productosSelecionados.get(i).getIva()+100)/100;
                productosSelecionados.remove(productosSelecionados.get(i));
            }
        }
    }
    
    public void agregarProductoLista() {        
        try {
            Integer.parseInt(cantidad);
            int c = Integer.parseInt(cantidad);
            if(c > 0) {
                Producto p = productoEJB.obtenerProductoPorNombre(producto);
                if(c <= p.getCantidad()) {
                    p.setCantidad(c);
                    productosSelecionados.add(p);
                    totalPagar += c*p.getPrecio()*(p.getIva()+100)/100;
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "No hay suficiente stock", "Error al registrar el producto"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error. Tienes que introducir un numero mayor de 0", "Error al registrar el producto"));
            }
        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error. Tienes que introducir un numero en la cantidad", "Error al registrar el producto"));
        }
    }
    
    public String realizarPedido() {
        String navegacion = "visualizarPedidosPendientes.xhtml";
        Persona cliente = clienteEJB.obtenerPersonaPorDNI(dniCliente);
        pedido.setFechaCreacion(fechaCreacion);
        pedido.setCliente(cliente);
        pedido.setEstadoPedido(estadoPedidoEJB.obtenerEstadoPedidoPorDescripcion("Recibido"));
        try {
            pedidoEJB.create(pedido);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Insercion correcta", "PRoducto registrado correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al insertar", "Error al registrar el producto"));
            System.out.println("Error al insertar el usuario " + e.getMessage());
        }
        guardarProductosPedidos(cliente);
        return navegacion;
    }
    
    public String realizarPedidoCliente() {
        String navegacion = "visualizarPedidos.xhtml";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Insercion correcta", "PRoducto registrado correctamente"));
        Usuario cliente = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        pedido.setFechaCreacion(fechaCreacion);
        pedido.setCliente(cliente.getPersona());
        pedido.setEstadoPedido(estadoPedidoEJB.obtenerEstadoPedidoPorDescripcion("Recibido"));
        try {
            pedidoEJB.create(pedido);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Insercion correcta", "PRoducto registrado correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al insertar", "Error al registrar el producto"));
            System.out.println("Error al insertar el usuario " + e.getMessage());
        }
        guardarProductosPedidos(cliente.getPersona());
        return navegacion;
    }
    
    public void guardarProductosPedidos(Persona cliente) {
        //Guardar en productos pedidos
        for(Producto pActual : productosSelecionados) {
            ProductoPedido pp = new ProductoPedido();
            actualizarCantidadProducto(pActual.getCantidad(), pActual.getIdProducto());
            pp.setCantidad(pActual.getCantidad());
            pp.setPedido(pedidoEJB.obtenerPedidoPorFechaCreacionYUsuario(fechaCreacion, cliente));
            pp.setProducto(productoEJB.obtenerProductoPorNombre(pActual.getNombre()));
            try {
                productoPedidoEJB.create(pp);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Insercion correcta", "PRoducto registrado correctamente"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, e.toString(), "Error al registrar el producto"));
                System.out.println("Error al insertar el usuario " + e.getMessage());
            }
        }
    }

    public void actualizarCantidadProducto(int cantidad, int id) {
        Producto p = productoEJB.obtenerProductoPorID(id);
        p.setCantidad(p.getCantidad() - cantidad);
        try {
            productoEJB.edit(p);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualización correcta", "Producto actualizado correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al actualizar", "Error al actualizar el producto"));
            System.out.println("Error al insertar la publicación " + e.getMessage());
        }
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

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(double totalPagar) {
        this.totalPagar = totalPagar;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.pedidoEJB);
        hash = 59 * hash + Objects.hashCode(this.pedido);
        hash = 59 * hash + Objects.hashCode(this.clienteEJB);
        hash = 59 * hash + Objects.hashCode(this.dniCliente);
        hash = 59 * hash + Objects.hashCode(this.estadoPedidoEJB);
        hash = 59 * hash + Objects.hashCode(this.productoPedidoEJB);
        hash = 59 * hash + Objects.hashCode(this.productoEJB);
        hash = 59 * hash + Objects.hashCode(this.listaDescripcionProductos);
        hash = 59 * hash + Objects.hashCode(this.productosSelecionados);
        hash = 59 * hash + Objects.hashCode(this.producto);
        hash = 59 * hash + Objects.hashCode(this.productoSelecionado);
        hash = 59 * hash + Objects.hashCode(this.cantidad);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.totalPagar) ^ (Double.doubleToLongBits(this.totalPagar) >>> 32));
        hash = 59 * hash + Objects.hashCode(this.fechaCreacion);
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
        if (Double.doubleToLongBits(this.totalPagar) != Double.doubleToLongBits(other.totalPagar)) {
            return false;
        }
        if (!Objects.equals(this.dniCliente, other.dniCliente)) {
            return false;
        }
        if (!Objects.equals(this.producto, other.producto)) {
            return false;
        }
        if (!Objects.equals(this.cantidad, other.cantidad)) {
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
        if (!Objects.equals(this.fechaCreacion, other.fechaCreacion)) {
            return false;
        }
        return true;
    }

    
    
}
