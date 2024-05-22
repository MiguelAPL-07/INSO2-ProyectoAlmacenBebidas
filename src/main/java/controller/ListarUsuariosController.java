/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.UsuarioFacadeLocal;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import modelo.Usuario;

/**
 *
 * @author Miguel Ángel
 */

@Named
@RequestScoped
public class ListarUsuariosController {
    
    @EJB
    private UsuarioFacadeLocal usuarioEJB;
    
    // Almacena todos los usuarios de la aplicacion
    private List<Usuario> usuariosBD;
    
    // Almacena todos los clientes de la aplicacion
    private List<Usuario> clientesBD;
    
    // Almacena todos los empleados de la aplicacion
    private List<Usuario> empleadosBD;

    private Usuario usuario;
    
    @PostConstruct
    public void init() {
        usuariosBD = usuarioEJB.findAll();
        
        clientesBD = usuarioEJB.obtenerUsuariosRol(1);
        
        empleadosBD = usuarioEJB.obtenerUsuariosRol(2);
    }
    
    public void establecerUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void eliminarUsuario(Usuario usuario) {
        try {
            usuarioEJB.remove(usuario);
            usuariosBD = usuarioEJB.findAll();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminación correcta", "Usuario eliminado con éxito"));
        } catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al eliminar", "Error al eliminar el usuario"));
            System.out.println("Error al eliminar el usuario " + e.getMessage());
        }
    }
    
    public void eliminarCliente(Usuario usuario) {
        try {
            usuarioEJB.remove(usuario);
            clientesBD = usuarioEJB.obtenerUsuariosRol(1);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminación correcta", "Usuario eliminado con éxito"));
        } catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al eliminar", "Error al eliminar el usuario"));
            System.out.println("Error al eliminar el usuario " + e.getMessage());
        }
    }
    
    public void eliminarEmpleado(Usuario usuario) {
        try {
            usuarioEJB.remove(usuario);
            empleadosBD = usuarioEJB.obtenerUsuariosRol(2);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminación correcta", "Usuario eliminado con éxito"));
        } catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al eliminar", "Error al eliminar el usuario"));
            System.out.println("Error al eliminar el usuario " + e.getMessage());
        }
    }

    public UsuarioFacadeLocal getUsuarioEJB() {
        return usuarioEJB;
    }

    public void setUsuarioEJB(UsuarioFacadeLocal usuarioEJB) {
        this.usuarioEJB = usuarioEJB;
    }

    public List<Usuario> getUsuariosBD() {
        return usuariosBD;
    }

    public void setUsuariosBD(List<Usuario> usuariosBD) {
        this.usuariosBD = usuariosBD;
    }

    public List<Usuario> getClientesBD() {
        return clientesBD;
    }

    public void setClientesBD(List<Usuario> clientesBD) {
        this.clientesBD = clientesBD;
    }

    public List<Usuario> getEmpleadosBD() {
        return empleadosBD;
    }

    public void setEmpleadosBD(List<Usuario> empleadosBD) {
        this.empleadosBD = empleadosBD;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.usuarioEJB);
        hash = 79 * hash + Objects.hashCode(this.usuariosBD);
        hash = 79 * hash + Objects.hashCode(this.clientesBD);
        hash = 79 * hash + Objects.hashCode(this.empleadosBD);
        hash = 79 * hash + Objects.hashCode(this.usuario);
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
        final ListarUsuariosController other = (ListarUsuariosController) obj;
        if (!Objects.equals(this.usuarioEJB, other.usuarioEJB)) {
            return false;
        }
        if (!Objects.equals(this.usuariosBD, other.usuariosBD)) {
            return false;
        }
        if (!Objects.equals(this.clientesBD, other.clientesBD)) {
            return false;
        }
        if (!Objects.equals(this.empleadosBD, other.empleadosBD)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        return true;
    }

}