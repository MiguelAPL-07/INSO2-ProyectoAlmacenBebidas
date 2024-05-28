/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.EstadoPedidoFacadeLocal;
import EJB.PedidoFacadeLocal;
import EJB.PersonaFacadeLocal;
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
    
    private List<Pedido> listaPedidosPendientes;
    
    private List<Pedido> listaPedidosAsignados;
    
    private List<Pedido> listaPedidosCliente;
    
    private String estadoSeleccionado;
    
    private List<String> descripcionEstadosBD;
    
    private Pedido pedido;
    
    @PostConstruct
    public void init() {
        listaPedidosPendientes = pedidoEJB.obtenerPedidosPorEstado("Recibido");
        
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        
        listaPedidosAsignados = pedidoEJB.obtenerPedidosPorEmpleadoYEstado(usuario.getPersona().getIdPersona(), 2);
        
        listaPedidosCliente = pedidoEJB.obtenerPedidosPorCliente(usuario.getPersona().getIdPersona());
        
        descripcionEstadosBD = new ArrayList<>();
        
        descripcionEstadosBD.add("Todos");
        
        // Recupera todos las categorias de la base de datos
        List<EstadoPedido> estadosBD = estadoPedidoEJB.findAll();
        for(EstadoPedido epActual : estadosBD) {
            descripcionEstadosBD.add(epActual.getDescripcion());
        }
        estadoSeleccionado = "Todos";
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
    
    public void establecerPedido(Pedido pedido) {
        this.pedido = pedido;
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

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.pedidoEJB);
        hash = 43 * hash + Objects.hashCode(this.estadoPedidoEJB);
        hash = 43 * hash + Objects.hashCode(this.personaEJB);
        hash = 43 * hash + Objects.hashCode(this.listaPedidosPendientes);
        hash = 43 * hash + Objects.hashCode(this.listaPedidosAsignados);
        hash = 43 * hash + Objects.hashCode(this.listaPedidosCliente);
        hash = 43 * hash + Objects.hashCode(this.estadoSeleccionado);
        hash = 43 * hash + Objects.hashCode(this.descripcionEstadosBD);
        hash = 43 * hash + Objects.hashCode(this.pedido);
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
        return true;
    }
    
    
    
}
