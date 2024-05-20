/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author Miguel Ángel
 */

@Entity
@Table(name="productospedidos")
public class ProductoPedido implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @JoinColumn(name="idPedido")
    @ManyToMany
    private Pedido pedido;
    
    @JoinColumn(name="idProducto")
    @ManyToMany
    private Producto producto;
    
    @Column(name="cantidad")
    private int cantidad;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + this.id;
        hash = 31 * hash + Objects.hashCode(this.pedido);
        hash = 31 * hash + Objects.hashCode(this.producto);
        hash = 31 * hash + this.cantidad;
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
        final ProductoPedido other = (ProductoPedido) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.cantidad != other.cantidad) {
            return false;
        }
        if (!Objects.equals(this.pedido, other.pedido)) {
            return false;
        }
        if (!Objects.equals(this.producto, other.producto)) {
            return false;
        }
        return true;
    }
    
    
}
