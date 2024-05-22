/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.CategoriaFacadeLocal;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Categoria;

/**
 *
 * @author Miguel Ángel
 */

@Named
@ViewScoped
public class AdministrarCategoriasController implements Serializable {
    
    @EJB
    private CategoriaFacadeLocal categoriaEJB;
    
    // Almacena todos los roles que hay en base de datos
    private List<Categoria> listaCategoriasBD;
    
    // Indica el rol seleccionado
    private Categoria categoria;
    
    // Variable que indica la accion a realizar en la capa vista
    private String accion;
    
    @PostConstruct
    public void init() {
        listaCategoriasBD = categoriaEJB.findAll();
    }
    
    public void establecerCategoriaInsertar() {
        this.categoria = new Categoria();
        this.accion = "I";
    }
    
    public void establecerCategoriaModificar(Categoria categoria) {
        this.categoria = categoria;
        this.accion = "M";
    }
    
    public void establecerCategoriaEliminar(Categoria categoria) {
        this.categoria = categoria;
        this.accion = "E";
    }
    
    public void insertarCategoria() {
         try {
            categoriaEJB.create(categoria);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado correcto", "Categoria añadida correctamente"));
            // Se actualiza la lista de categorias
            listaCategoriasBD = categoriaEJB.findAll();
         } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al agregar", "Error al añadir la categoria"));
            System.out.println("Error al insertar la categoria " + e.getMessage());
        }
    }
    
    public void modificarCategoria() {
        try {
            categoriaEJB.edit(categoria);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado correcto", "Categoria modificada correctamente"));
            // Se actualiza la lista de categorias
            listaCategoriasBD = categoriaEJB.findAll();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al modificar", "Error al modificar la categoria"));
            System.out.println("Error al modificar la categoria " + e.getMessage());
        }
    }
    
    public void eliminarCategoria() {
        try {
            categoriaEJB.remove(categoria);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado correcto", "Categoria eliminada correctamente"));
            // Se actualiza la lista de categorias
            listaCategoriasBD = categoriaEJB.findAll();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al eliminar", "Error al eliminar la categoria"));
            System.out.println("Error al eliminar la categoria " + e.getMessage());
        }
    }

    public CategoriaFacadeLocal getCategoriaEJB() {
        return categoriaEJB;
    }

    public void setCategoriaEJB(CategoriaFacadeLocal categoriaEJB) {
        this.categoriaEJB = categoriaEJB;
    }

    public List<Categoria> getListaCategoriasBD() {
        return listaCategoriasBD;
    }

    public void setListaCategoriasBD(List<Categoria> listaCategoriasBD) {
        this.listaCategoriasBD = listaCategoriasBD;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.categoriaEJB);
        hash = 37 * hash + Objects.hashCode(this.listaCategoriasBD);
        hash = 37 * hash + Objects.hashCode(this.categoria);
        hash = 37 * hash + Objects.hashCode(this.accion);
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
        final AdministrarCategoriasController other = (AdministrarCategoriasController) obj;
        if (!Objects.equals(this.accion, other.accion)) {
            return false;
        }
        if (!Objects.equals(this.categoriaEJB, other.categoriaEJB)) {
            return false;
        }
        if (!Objects.equals(this.listaCategoriasBD, other.listaCategoriasBD)) {
            return false;
        }
        if (!Objects.equals(this.categoria, other.categoria)) {
            return false;
        }
        return true;
    }
    
    
}