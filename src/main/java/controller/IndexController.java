/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.UsuarioFacadeLocal;
import java.io.Serializable;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Usuario;

/**
 *
 * @author Miguel Ángel
 */

@Named
@ViewScoped
public class IndexController implements Serializable {
    
    private Usuario usuario;
    
    @EJB
    private UsuarioFacadeLocal usuarioEJB;
    
    @PostConstruct
    public void init() {
        usuario = new Usuario();
    }
    
    public String verificarUsuario() {
        String navegacion = "";
        try {
            Usuario r = usuarioEJB.verificarUsuario(usuario);
            if(r != null) {
                // Existe el usuario: se loggea
                // Guardar datos del usuario de forma global
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", r);
                navegacion = "privado/inicio.xhtml?faces-redirect=true";
            } else {
                // No existe el usuario: permisos insuficientes
                navegacion = "publico/permisosinsuficientes.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            System.out.println("Error al verificar usuario " + e.getMessage());
        }
        
        return navegacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UsuarioFacadeLocal getUsuarioEJB() {
        return usuarioEJB;
    }

    public void setUsuarioEJB(UsuarioFacadeLocal usuarioEJB) {
        this.usuarioEJB = usuarioEJB;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.usuario);
        hash = 17 * hash + Objects.hashCode(this.usuarioEJB);
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
        final IndexController other = (IndexController) obj;
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.usuarioEJB, other.usuarioEJB)) {
            return false;
        }
        return true;
    }
    
    
}