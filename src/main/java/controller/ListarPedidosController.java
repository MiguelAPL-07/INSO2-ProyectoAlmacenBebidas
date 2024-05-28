/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.EstadoPedidoFacadeLocal;
import EJB.PedidoFacadeLocal;
import EJB.PersonaFacadeLocal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
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
    
    @PostConstruct
    public void init() {
        listaPedidosPendientes = pedidoEJB.obtenerPedidosPorEstado("Recibido");
        
        Usuario empleado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        
        listaPedidosAsignados = pedidoEJB.obtenerPedidosPorEmpleadoYEstado(empleado.getPersona().getIdPersona(), 2);
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
    
    
    
}
