/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Miguel Ángel
 */

@Entity
@Table(name="pedidos")
public class Pedido implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idPedido;
    
    @Column(name="fechaCreacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    
    @Column(name="fechaEnvio")
    @Temporal(TemporalType.DATE)
    private Date fechaEnvio;
    
    @Column(name="calle")
    private String calle;
    
    @Column(name="numeroCalle")
    private String numeroCalle;
    
    @Column(name="codigoPostal")
    private String codigoPostal;
    
    @Column(name="ciudad")
    private String ciudad;
    
    @JoinColumn(name="idEmpleado")
    @ManyToOne
    private Persona empleado;
    
    @JoinColumn(name="idCliente")
    @ManyToOne
    private Persona cliente;
    
    @JoinColumn(name="idEstado")
    @ManyToOne
    private EstadoPedido estadoPedido;

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroCalle() {
        return numeroCalle;
    }

    public void setNumeroCalle(String numeroCalle) {
        this.numeroCalle = numeroCalle;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Persona getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Persona empleado) {
        this.empleado = empleado;
    }

    public Persona getCliente() {
        return cliente;
    }

    public void setCliente(Persona cliente) {
        this.cliente = cliente;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.idPedido;
        hash = 23 * hash + Objects.hashCode(this.fechaCreacion);
        hash = 23 * hash + Objects.hashCode(this.fechaEnvio);
        hash = 23 * hash + Objects.hashCode(this.calle);
        hash = 23 * hash + Objects.hashCode(this.numeroCalle);
        hash = 23 * hash + Objects.hashCode(this.codigoPostal);
        hash = 23 * hash + Objects.hashCode(this.ciudad);
        hash = 23 * hash + Objects.hashCode(this.empleado);
        hash = 23 * hash + Objects.hashCode(this.cliente);
        hash = 23 * hash + Objects.hashCode(this.estadoPedido);
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
        final Pedido other = (Pedido) obj;
        if (this.idPedido != other.idPedido) {
            return false;
        }
        if (!Objects.equals(this.calle, other.calle)) {
            return false;
        }
        if (!Objects.equals(this.numeroCalle, other.numeroCalle)) {
            return false;
        }
        if (!Objects.equals(this.codigoPostal, other.codigoPostal)) {
            return false;
        }
        if (!Objects.equals(this.ciudad, other.ciudad)) {
            return false;
        }
        if (!Objects.equals(this.fechaCreacion, other.fechaCreacion)) {
            return false;
        }
        if (!Objects.equals(this.fechaEnvio, other.fechaEnvio)) {
            return false;
        }
        if (!Objects.equals(this.empleado, other.empleado)) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.estadoPedido, other.estadoPedido)) {
            return false;
        }
        return true;
    }
    
    
}
