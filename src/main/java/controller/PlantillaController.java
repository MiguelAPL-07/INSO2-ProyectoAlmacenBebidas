/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Miguel Ángel
 */

@Named
@ViewScoped
public class PlantillaController implements Serializable {
    
    public void verificarYMostrar() {
        try {
            Object usuario = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            if(usuario == null) {
                String rutaRelativa = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath() + "/publico/permisosinsuficientes.xhtml?faces-redirect=true";
                FacesContext.getCurrentInstance().getExternalContext().redirect(rutaRelativa);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
