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
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import modelo.EstadoPedido;
import modelo.Pedido;
import modelo.Persona;
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
    
    public void asignarPedidoEmpleado(Pedido pedido) {
        Usuario empleado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        pedido.setEmpleado(empleado.getPersona());
        pedido.setEstadoPedido(estadoPedidoEJB.obtenerEstadoPedidoPorID(2));
        try {
            pedidoEJB.edit(pedido);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualización correcta", "Producto actualizado correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al actualizar", "Error al actualizar el producto"));
            System.out.println("Error al insertar la publicación " + e.getMessage());
        }
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
    
    
    
    public void verPedidosPendientes() {
        listaPedidosPendientes = pedidoEJB.obtenerPedidosPorEstado("Recibido");
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
    
    
    
}
