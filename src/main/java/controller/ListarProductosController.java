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
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import modelo.Categoria;
import modelo.Producto;

/**
 *
 * @author Miguel Ángel
 */

@Named
@RequestScoped
public class ListarProductosController implements Serializable {
    
    @EJB
    private ProductoFacadeLocal productoEJB;
    
    @EJB
    private CategoriaFacadeLocal categoriaEJB;
    
    private List<Producto> productosBD;
    
    private List<String> nombreCategoriasBD;
    
    private Producto producto;
    
    private String categoriaSeleccionada;
    
    private int cantidad;
    
    @PostConstruct
    public void init() {
        productosBD = productoEJB.findAll();
        
        nombreCategoriasBD = new ArrayList<>();
        
        nombreCategoriasBD.add("Todas");
        
        // Recupera todos las categorias de la base de datos
        List<Categoria> categoriasBD = categoriaEJB.findAll();
        for(Categoria cActual : categoriasBD) {
            nombreCategoriasBD.add(cActual.getNombreCategoria());
        }
        cantidad = 0;
        categoriaSeleccionada = "Todas";
    }
    
    public void filtrarProductosPorCategoria() {
        if(categoriaSeleccionada.equalsIgnoreCase("Todas")) {
            productosBD = productoEJB.findAll();
        } else {
            productosBD = productoEJB.obtenerProductoPorCategoria(categoriaSeleccionada);
        }
    } 
    
    public void establecerProducto(Producto producto) {
        this.producto = producto;
    }
    
    public void agregarStock() {
        producto.setCantidad(producto.getCantidad() + cantidad);
        try {
            productoEJB.edit(producto);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Insercion correcta", "PRoducto registrado correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al insertar", "Error al registrar el producto"));
            System.out.println("Error al insertar el usuario " + e.getMessage());
        }
    }
    
    public void eliminarProducto(Producto producto) {
        try {
            productoEJB.remove(producto);
            filtrarProductosPorCategoria();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminación correcta", "Producto eliminado con éxito"));
        } catch(Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al eliminar", "Error al eliminar el producto"));
            System.out.println("Error al eliminar la publicación "+e.getMessage());
        }
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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

    public List<Producto> getProductosBD() {
        return productosBD;
    }

    public void setProductosBD(List<Producto> productosBD) {
        this.productosBD = productosBD;
    }

    public List<String> getNombreCategoriasBD() {
        return nombreCategoriasBD;
    }

    public void setNombreCategoriasBD(List<String> nombreCategoriasBD) {
        this.nombreCategoriasBD = nombreCategoriasBD;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getCategoriaSeleccionada() {
        return categoriaSeleccionada;
    }

    public void setCategoriaSeleccionada(String categoriaSeleccionada) {
        this.categoriaSeleccionada = categoriaSeleccionada;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.productoEJB);
        hash = 71 * hash + Objects.hashCode(this.categoriaEJB);
        hash = 71 * hash + Objects.hashCode(this.productosBD);
        hash = 71 * hash + Objects.hashCode(this.nombreCategoriasBD);
        hash = 71 * hash + Objects.hashCode(this.producto);
        hash = 71 * hash + Objects.hashCode(this.categoriaSeleccionada);
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
        final ListarProductosController other = (ListarProductosController) obj;
        if (!Objects.equals(this.categoriaSeleccionada, other.categoriaSeleccionada)) {
            return false;
        }
        if (!Objects.equals(this.productoEJB, other.productoEJB)) {
            return false;
        }
        if (!Objects.equals(this.categoriaEJB, other.categoriaEJB)) {
            return false;
        }
        if (!Objects.equals(this.productosBD, other.productosBD)) {
            return false;
        }
        if (!Objects.equals(this.nombreCategoriasBD, other.nombreCategoriasBD)) {
            return false;
        }
        if (!Objects.equals(this.producto, other.producto)) {
            return false;
        }
        return true;
    }

}
