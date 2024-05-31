/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.RolFacadeLocal;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Rol;

/**
 *
 * @author Miguel Ángel
 */

@Named
@ViewScoped
public class AdministrarRolesController implements Serializable {
    
    @EJB
    private RolFacadeLocal rolEJB;
    
    // Almacena todos los roles que hay en base de datos
    private List<Rol> listaRolesBD;
    
    // Indica el rol seleccionado
    private Rol rol;
    
    // Variable que indica la accion a realizar en la capa vista
    private String accion;
    
    @PostConstruct
    public void init() {
        listaRolesBD = rolEJB.findAll();
    }
    
    public void establecerRolInsertar() {
        this.rol = new Rol();
        this.accion = "I";
    }
    
    public void establecerRolModificar(Rol rol) {
        this.rol = rol;
        this.accion = "M";
    }
    
    public void establecerRolEliminar(Rol rol) {
        this.rol = rol;
        this.accion = "E";
    }
    
    // Descripcion y tipoUsuario
    public void insertarRol() {
         try {
             // Se crea el rol
            rolEJB.create(rol);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Rol añadido correctament", "Rol añadido correctamente"));
            // Se actualiza la lista de roles
            listaRolesBD = rolEJB.findAll();
         } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al añadir el rol", "Error al añadir el rol"));
            System.out.println("Error al insertar el rol " + e.getMessage());
        }
    }
    
    public void modificarRol() {
        try {
            rolEJB.edit(rol);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Rol modificado correctamente", "Rol modificado correctamente"));
            // Se actualiza la lista de roles
            listaRolesBD = rolEJB.findAll();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al modificar el rol", "Error al modificar el rol"));
            System.out.println("Error al modificar el rol " + e.getMessage());
        }
    }
    
    public void eliminarRol() {
        try {
            rolEJB.remove(rol);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Rol eliminado correctamente", "Rol eliminado correctamente"));
            // Se actualiza la lista de roles
            listaRolesBD = rolEJB.findAll();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al eliminar el rol", "Error al eliminar el rol"));
            System.out.println("Error al eliminar el rol " + e.getMessage());
        }
    }

    public RolFacadeLocal getRolEJB() {
        return rolEJB;
    }

    public void setRolEJB(RolFacadeLocal rolEJB) {
        this.rolEJB = rolEJB;
    }

    public List<Rol> getListaRolesBD() {
        return listaRolesBD;
    }

    public void setListaRolesBD(List<Rol> listaRolesBD) {
        this.listaRolesBD = listaRolesBD;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.rolEJB);
        hash = 31 * hash + Objects.hashCode(this.listaRolesBD);
        hash = 31 * hash + Objects.hashCode(this.rol);
        hash = 31 * hash + Objects.hashCode(this.accion);
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
        final AdministrarRolesController other = (AdministrarRolesController) obj;
        if (!Objects.equals(this.accion, other.accion)) {
            return false;
        }
        if (!Objects.equals(this.rolEJB, other.rolEJB)) {
            return false;
        }
        if (!Objects.equals(this.listaRolesBD, other.listaRolesBD)) {
            return false;
        }
        if (!Objects.equals(this.rol, other.rol)) {
            return false;
        }
        return true;
    }
    
}