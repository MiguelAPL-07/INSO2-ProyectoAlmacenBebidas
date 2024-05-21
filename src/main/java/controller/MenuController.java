/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.MenuFacadeLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import modelo.Menu;
import modelo.Usuario;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author Miguel Ángel
 */

@Named
@SessionScoped
public class MenuController implements Serializable {
    
    @EJB
    private MenuFacadeLocal menuEJB;
    
    private MenuModel modelo;
    
    private String usuario;
    
    @PostConstruct
    public void init() {
        modelo = obtenerMenu();      
    }
    
    public MenuModel obtenerMenu() {
        // Se crea el menuModel por defecto
        MenuModel modeloUsuario = new DefaultMenuModel();
        // Se coge el usuario actual de la variable global
        Usuario usuarioActual = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        // Se solicita los menus segun el usuario
        List<Menu> menusUsuario = menuEJB.obtenerMenusUsuario(usuarioActual);
        // Se obtiene el arbol del menu
        for(Menu mActual : menusUsuario) {
            if(mActual.getTipo() == 'S') {
                // Es de tipo submenu, no tiene padre
                DefaultSubMenu submenu = DefaultSubMenu.builder().label(mActual.getNombre()).build();
                // Se busca y agrega los hijos
                for(Menu restoMenus : menusUsuario) {
                    // Se comprueba si tiene padre. Si no tiene es un item porque ningun submenu tiene padre
                    if(restoMenus.getMenu() != null) {
                        // Se comprueba si es hijo del submenu
                        if(restoMenus.getMenu().getIdMenu() == mActual.getIdMenu()) {
                            // Es hijo. Se agrega al submenu
                            DefaultMenuItem item = DefaultMenuItem.builder().value(restoMenus.getNombre()).url("/ProyectoAlmacenBebidas/faces" + restoMenus.getUrl()).build();
                            submenu.getElements().add(item);
                        }
                    }
                }
                // Se agrega el submenu al model
                modeloUsuario.getElements().add(submenu);
            } else {
                // Es de tipo item, se comprueba si tiene padre
                if(mActual.getMenu()==null) {
                    // No tiene padre, se crea el item
                    DefaultMenuItem item = DefaultMenuItem.builder().value(mActual.getNombre()).url("/ProyectoAlmacenBebidas/faces" + mActual.getUrl()).build();
                    // Se agrega al model
                    modeloUsuario.getElements().add(item);
                }
            }
        }
        return modeloUsuario;
    }
    
    public void destrurirSesionActual() {
        try {
            // Se destruye la sesion del usuario
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath() + "/faces/index.xhtml");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public MenuFacadeLocal getMenuEJB() {
        return menuEJB;
    }

    public void setMenuEJB(MenuFacadeLocal menuEJB) {
        this.menuEJB = menuEJB;
    }

    public MenuModel getModelo() {
        return modelo;
    }

    public void setModelo(MenuModel modelo) {
        this.modelo = modelo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.menuEJB);
        hash = 47 * hash + Objects.hashCode(this.modelo);
        hash = 47 * hash + Objects.hashCode(this.usuario);
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
        final MenuController other = (MenuController) obj;
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.menuEJB, other.menuEJB)) {
            return false;
        }
        if (!Objects.equals(this.modelo, other.modelo)) {
            return false;
        }
        return true;
    }
    
    
}