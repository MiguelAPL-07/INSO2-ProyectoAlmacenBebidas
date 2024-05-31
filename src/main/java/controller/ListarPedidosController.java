/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.EstadoPedidoFacadeLocal;
import EJB.PedidoFacadeLocal;
import EJB.PersonaFacadeLocal;
import EJB.UsuarioFacadeLocal;
import EJB.ProductoFacadeLocal;
import EJB.ProductoPedidoFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import modelo.EstadoPedido;
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
@RequestScoped
public class ListarPedidosController {
    
    @EJB
    private PedidoFacadeLocal pedidoEJB;
    
    @EJB
    private EstadoPedidoFacadeLocal estadoPedidoEJB;
    
    @EJB
    private PersonaFacadeLocal personaEJB;
    
    @EJB

    private UsuarioFacadeLocal usuarioEJB;
  
    private ProductoFacadeLocal productoEJB;
    
    @EJB
    private ProductoPedidoFacadeLocal productoPedidoEJB;

    
    private List<Pedido> listaPedidosPendientes;
    
    private List<Pedido> listaPedidosAsignados;
    
    private List<Pedido> listaPedidosCliente;
    
    private String estadoSeleccionado;
    
    private List<String> descripcionEstadosBD;
    

    // Para ver el administrador
    private List<Pedido> listaPedidosEmpleados;
    
    private List<String> nombreEmpleadosBD;
    
    private String empleadoSeleccionado;
    
    private List<Pedido> pedidosClientes;
    
    private List<String> nombreClientesBD;
    
    private String clienteSeleccionado;
  
    private Pedido pedido;
    
    private List<Producto> listaProductos;
    
    private int totalProductos;
    
    private int cantidadTotal;
    
    @PostConstruct
    public void init() {
        listaPedidosPendientes = pedidoEJB.obtenerPedidosPorEstado("Recibido");
        
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        
        listaPedidosAsignados = pedidoEJB.obtenerPedidosPorEmpleadoYEstado(usuario.getPersona().getIdPersona(), 2);
        
        listaPedidosCliente = pedidoEJB.obtenerPedidosPorCliente(usuario.getPersona().getIdPersona());
        
        descripcionEstadosBD = new ArrayList<>();
        
        descripcionEstadosBD.add("Todos");
        
        List<EstadoPedido> estadosBD = estadoPedidoEJB.findAll();
        for(EstadoPedido epActual : estadosBD) {
            descripcionEstadosBD.add(epActual.getDescripcion());
        }
        estadoSeleccionado = "Todos";
        
        nombreEmpleadosBD = new ArrayList<>();
        
        nombreEmpleadosBD.add("Todos");
        
        List<Usuario> empleadosBD = usuarioEJB.obtenerUsuariosRol(2);
        for(Usuario uActual : empleadosBD) {
            nombreEmpleadosBD.add(uActual.getPersona().getIdPersona() + " " + uActual.getPersona().getNombre() + " " + uActual.getPersona().getApellido1());
        }
        empleadoSeleccionado = "Todos";
        
        nombreClientesBD = new ArrayList<>();
        
        nombreClientesBD.add("Todos");
        
        List<Usuario> clientesBD = usuarioEJB.obtenerUsuariosRol(1);
        for(Usuario uActual : clientesBD) {
            nombreClientesBD.add(uActual.getPersona().getIdPersona() + " " + uActual.getPersona().getNombre() + " " + uActual.getPersona().getApellido1());
        }
        clienteSeleccionado = "Todos";
    }
    
    public void filtrarPedidosEmpleados() {
        if(empleadoSeleccionado.equalsIgnoreCase("Todos")) {
            listaPedidosEmpleados = pedidoEJB.findAll();
        } else {
            listaPedidosEmpleados = pedidoEJB.obtenerPedidosPorEmpleado(Integer.parseInt(empleadoSeleccionado.split(" ")[0]));
        }
    }
    
    public void filtrarPedidosClientes() {
        if(clienteSeleccionado.equalsIgnoreCase("Todos")) {
            pedidosClientes = pedidoEJB.findAll();
        } else {
            pedidosClientes = pedidoEJB.obtenerPedidosPorCliente(Integer.parseInt(clienteSeleccionado.split(" ")[0]));
        }
        listaProductos = new ArrayList<>();
    }
    
    public void filtrarPedidosPorEstado() {
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if(estadoSeleccionado.equalsIgnoreCase("Todos")) {
            listaPedidosCliente = pedidoEJB.obtenerPedidosPorCliente(usuario.getPersona().getIdPersona());
        } else {
            EstadoPedido ep = estadoPedidoEJB.obtenerEstadoPedidoPorDescripcion(estadoSeleccionado);
            listaPedidosCliente = pedidoEJB.obtenerPedidosPorClienteYEstado(usuario.getPersona().getIdPersona(), ep.getIdEstado());
        }
    } 
    
    public List<Producto> obtenerProductosPedido(int idPedido) {
        List<Producto> productos = new ArrayList<>();
        List<ProductoPedido> pp = productoPedidoEJB.obtenerProductosPedidosPorPedido(idPedido);
        for(ProductoPedido ppActual : pp) {
            Producto p = productoEJB.obtenerProductoPorID(ppActual.getProducto().getIdProducto());
            p.setCantidad(ppActual.getCantidad());
            productos.add(p);      
            totalProductos ++;
            cantidadTotal += ppActual.getCantidad();
        }
        return productos;
    }
    
    public void asignarPedidoEmpleado() {
        Usuario empleado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        pedido.setEmpleado(empleado.getPersona());
        pedido.setEstadoPedido(estadoPedidoEJB.obtenerEstadoPedidoPorID(2));
        try {
            pedidoEJB.edit(pedido);
            listaPedidosPendientes = pedidoEJB.obtenerPedidosPorEstado("Recibido");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pedido asignado correctamente", "Pedido asignado correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al asignar el pedido", "Error al asignar el pedido"));
            System.out.println("Error al insertar la publicación " + e.getMessage());
        }
        pedido = null;
    }

    public UsuarioFacadeLocal getUsuarioEJB() {
        return usuarioEJB;
    }

    public void setUsuarioEJB(UsuarioFacadeLocal usuarioEJB) {
        this.usuarioEJB = usuarioEJB;
    }

    public List<Pedido> getPedidosClientes() {
        return pedidosClientes;
    }

    public void setPedidosClientes(List<Pedido> pedidosClientes) {
        this.pedidosClientes = pedidosClientes;
    }

    public List<String> getNombreClientesBD() {
        return nombreClientesBD;
    }

    public void setNombreClientesBD(List<String> nombreClientesBD) {
        this.nombreClientesBD = nombreClientesBD;
    }

    public String getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(String clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }
    
    

    public List<Pedido> getListaPedidosEmpleados() {
        return listaPedidosEmpleados;
    }

    public void setListaPedidosEmpleados(List<Pedido> listaPedidosEmpleados) {
        this.listaPedidosEmpleados = listaPedidosEmpleados;
    }

    public List<String> getNombreEmpleadosBD() {
        return nombreEmpleadosBD;
    }

    public void setNombreEmpleadosBD(List<String> nombreEmpleadosBD) {
        this.nombreEmpleadosBD = nombreEmpleadosBD;
    }

    public String getEmpleadoSeleccionado() {
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(String empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }
    
    
    
    public void calcularTotales() {
        listaProductos = obtenerProductosPedido(pedido.getIdPedido());
    }
    
    
    public void establecerPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void verPedidosAsignados() {
        Usuario empleado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        listaPedidosAsignados = pedidoEJB.obtenerPedidosPorEmpleadoYEstado(empleado.getPersona().getIdPersona(), 2);
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

    public PersonaFacadeLocal getPersonaEJB() {
        return personaEJB;
    }

    public void setPersonaEJB(PersonaFacadeLocal personaEJB) {
        this.personaEJB = personaEJB;
    }

    public ProductoFacadeLocal getProductoEJB() {
        return productoEJB;
    }

    public void setProductoEJB(ProductoFacadeLocal productoEJB) {
        this.productoEJB = productoEJB;
    }

    public ProductoPedidoFacadeLocal getProductoPedidoEJB() {
        return productoPedidoEJB;
    }

    public void setProductoPedidoEJB(ProductoPedidoFacadeLocal productoPedidoEJB) {
        this.productoPedidoEJB = productoPedidoEJB;
    }

    public List<Pedido> getListaPedidosPendientes() {
        return listaPedidosPendientes;
    }

    public void setListaPedidosPendientes(List<Pedido> listaPedidosPendientes) {
        this.listaPedidosPendientes = listaPedidosPendientes;
    }

    public List<Pedido> getListaPedidosAsignados() {
        return listaPedidosAsignados;
    }

    public void setListaPedidosAsignados(List<Pedido> listaPedidosAsignados) {
        this.listaPedidosAsignados = listaPedidosAsignados;
    }

    public List<Pedido> getListaPedidosCliente() {
        return listaPedidosCliente;
    }

    public void setListaPedidosCliente(List<Pedido> listaPedidosCliente) {
        this.listaPedidosCliente = listaPedidosCliente;
    }

    public String getEstadoSeleccionado() {
        return estadoSeleccionado;
    }

    public void setEstadoSeleccionado(String estadoSeleccionado) {
        this.estadoSeleccionado = estadoSeleccionado;
    }

    public List<String> getDescripcionEstadosBD() {
        return descripcionEstadosBD;
    }

    public void setDescripcionEstadosBD(List<String> descripcionEstadosBD) {
        this.descripcionEstadosBD = descripcionEstadosBD;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public int getTotalProductos() {
        return totalProductos;
    }

    public void setTotalProductos(int totalProductos) {
        this.totalProductos = totalProductos;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.pedidoEJB);
        hash = 59 * hash + Objects.hashCode(this.estadoPedidoEJB);
        hash = 59 * hash + Objects.hashCode(this.personaEJB);
        hash = 59 * hash + Objects.hashCode(this.productoEJB);
        hash = 59 * hash + Objects.hashCode(this.productoPedidoEJB);
        hash = 59 * hash + Objects.hashCode(this.listaPedidosPendientes);
        hash = 59 * hash + Objects.hashCode(this.listaPedidosAsignados);
        hash = 59 * hash + Objects.hashCode(this.listaPedidosCliente);
        hash = 59 * hash + Objects.hashCode(this.estadoSeleccionado);
        hash = 59 * hash + Objects.hashCode(this.descripcionEstadosBD);
        hash = 59 * hash + Objects.hashCode(this.pedido);
        hash = 59 * hash + Objects.hashCode(this.listaProductos);
        hash = 59 * hash + this.totalProductos;
        hash = 59 * hash + this.cantidadTotal;
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
        final ListarPedidosController other = (ListarPedidosController) obj;
        if (this.totalProductos != other.totalProductos) {
            return false;
        }
        if (this.cantidadTotal != other.cantidadTotal) {
            return false;
        }
        if (!Objects.equals(this.estadoSeleccionado, other.estadoSeleccionado)) {
            return false;
        }
        if (!Objects.equals(this.pedidoEJB, other.pedidoEJB)) {
            return false;
        }
        if (!Objects.equals(this.estadoPedidoEJB, other.estadoPedidoEJB)) {
            return false;
        }
        if (!Objects.equals(this.personaEJB, other.personaEJB)) {
            return false;
        }
        if (!Objects.equals(this.productoEJB, other.productoEJB)) {
            return false;
        }
        if (!Objects.equals(this.productoPedidoEJB, other.productoPedidoEJB)) {
            return false;
        }
        if (!Objects.equals(this.listaPedidosPendientes, other.listaPedidosPendientes)) {
            return false;
        }
        if (!Objects.equals(this.listaPedidosAsignados, other.listaPedidosAsignados)) {
            return false;
        }
        if (!Objects.equals(this.listaPedidosCliente, other.listaPedidosCliente)) {
            return false;
        }
        if (!Objects.equals(this.descripcionEstadosBD, other.descripcionEstadosBD)) {
            return false;
        }
        if (!Objects.equals(this.pedido, other.pedido)) {
            return false;
        }
        if (!Objects.equals(this.listaProductos, other.listaProductos)) {
            return false;
        }
        return true;
    }

    
}
