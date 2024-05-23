/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.CategoriaFacadeLocal;
import EJB.ProductoFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Categoria;
import modelo.Producto;

/**
 *
 * @author Miguel Ángel
 */

@Named
@ViewScoped

public class NuevoProductoController implements Serializable {
    
    @EJB
    private ProductoFacadeLocal productoEJB;
    
    @EJB
    private CategoriaFacadeLocal categoriaEJB;
    
    private Producto producto;
    
    private List<String> listaCategorias;
    
    private String categoria;
    
    @PostConstruct
    public void init() {
        producto = new Producto();
        
        listaCategorias = new ArrayList<>();
        
        // Recupera todos los roles de la base de datos
        List<Categoria> categoriasBD = categoriaEJB.findAll();
        for(Categoria cActual : categoriasBD) {
            listaCategorias.add(cActual.getNombreCategoria());
        }
    }
    
    public void insertarProducto() {
        producto.setCategoria(categoriaEJB.obtenerCategoriaPorNombre(categoria));
        try {
            productoEJB.create(producto);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Insercion correcta", "PRoducto registrado correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al insertar", "Error al registrar el producto"));
            System.out.println("Error al insertar el usuario " + e.getMessage());
        }
    }

    public ProductoFacadeLocal getProductoEJB() {
        return productoEJB;
    }

    public void setProductoEJB(ProductoFacadeLocal productoEJB) {
        this.productoEJB = productoEJB;
    }

    public CategoriaFacadeLocal getCategoriaEJB() {
        return categoriaEJB;
    }

    public void setCategoriaEJB(CategoriaFacadeLocal categoriaEJB) {
        this.categoriaEJB = categoriaEJB;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<String> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<String> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.productoEJB);
        hash = 13 * hash + Objects.hashCode(this.categoriaEJB);
        hash = 13 * hash + Objects.hashCode(this.producto);
        hash = 13 * hash + Objects.hashCode(this.listaCategorias);
        hash = 13 * hash + Objects.hashCode(this.categoria);
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
        final NuevoProductoController other = (NuevoProductoController) obj;
        if (!Objects.equals(this.categoria, other.categoria)) {
            return false;
        }
        if (!Objects.equals(this.productoEJB, other.productoEJB)) {
            return false;
        }
        if (!Objects.equals(this.categoriaEJB, other.categoriaEJB)) {
            return false;
        }
        if (!Objects.equals(this.producto, other.producto)) {
            return false;
        }
        if (!Objects.equals(this.listaCategorias, other.listaCategorias)) {
            return false;
        }
        return true;
    }
   
}
