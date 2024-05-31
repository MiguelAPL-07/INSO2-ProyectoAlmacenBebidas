/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.PersonaFacadeLocal;
import EJB.RolFacadeLocal;
import EJB.UsuarioFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Persona;
import modelo.Rol;
import modelo.Usuario;

/**
 *
 * @author Miguel Ángel
 */

@Named
@ViewScoped
public class EditarUsuarioController implements Serializable {
        
    @Inject
    private ListarUsuariosController listUserCon;
    
    @EJB
    private UsuarioFacadeLocal usuarioEJB;
    
    @EJB
    private PersonaFacadeLocal personaEJB;
    
    @EJB
    private RolFacadeLocal rolEJB;
    
    private Usuario usuario;
    
    private Persona persona;
    
    private String rol;
    
    private List<String> roles;
    
    @PostConstruct
    public void init(){
        usuario = listUserCon.getUsuario();
        if(usuario == null) {
            usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        }
        persona = usuario.getPersona();
        rol = usuario.getRol().getDescripcion();
        roles = new ArrayList<>();
        
        // Recupera todos los roles de la base de datos
        List<Rol> rolesBD = rolEJB.findAll();
        for(Rol rActual : rolesBD) {
            roles.add(rActual.getDescripcion());
        }
    }
    
    public String editarUsuario(){
        String navegacion = "visualizarUsuarios.xhtml";
        usuario.setRol(rolEJB.findByDescripcion(rol));
        usuario.setPersona(persona);
        try {
            usuarioEJB.edit(usuario);
            personaEJB.edit(persona);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificación correcta", "Usuario modificado con éxito"));
        } catch(Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al modficar", "Error al modificar el usuario"));
            System.out.println("Error al modificar el usuario "+e.getMessage());
        }
        return navegacion;
    }
    
    public String volverListarUsuarios() {
        return "visualizarUsuarios.xhtml";
    }
    
    public String volverInicio() {
        return "inicio.xhtml";
    }

    public ListarUsuariosController getListUserCon() {
        return listUserCon;
    }

    public void setListUserCon(ListarUsuariosController listUserCon) {
        this.listUserCon = listUserCon;
    }

    public UsuarioFacadeLocal getUsuarioEJB() {
        return usuarioEJB;
    }

    public void setUsuarioEJB(UsuarioFacadeLocal usuarioEJB) {
        this.usuarioEJB = usuarioEJB;
    }

    public PersonaFacadeLocal getPersonaEJB() {
        return personaEJB;
    }

    public void setPersonaEJB(PersonaFacadeLocal personaEJB) {
        this.personaEJB = personaEJB;
    }

    public RolFacadeLocal getRolEJB() {
        return rolEJB;
    }

    public void setRolEJB(RolFacadeLocal rolEJB) {
        this.rolEJB = rolEJB;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.listUserCon);
        hash = 29 * hash + Objects.hashCode(this.usuarioEJB);
        hash = 29 * hash + Objects.hashCode(this.personaEJB);
        hash = 29 * hash + Objects.hashCode(this.rolEJB);
        hash = 29 * hash + Objects.hashCode(this.usuario);
        hash = 29 * hash + Objects.hashCode(this.persona);
        hash = 29 * hash + Objects.hashCode(this.rol);
        hash = 29 * hash + Objects.hashCode(this.roles);
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
        final EditarUsuarioController other = (EditarUsuarioController) obj;
        if (!Objects.equals(this.rol, other.rol)) {
            return false;
        }
        if (!Objects.equals(this.listUserCon, other.listUserCon)) {
            return false;
        }
        if (!Objects.equals(this.usuarioEJB, other.usuarioEJB)) {
            return false;
        }
        if (!Objects.equals(this.personaEJB, other.personaEJB)) {
            return false;
        }
        if (!Objects.equals(this.rolEJB, other.rolEJB)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.persona, other.persona)) {
            return false;
        }
        if (!Objects.equals(this.roles, other.roles)) {
            return false;
        }
        return true;
    }
    
}