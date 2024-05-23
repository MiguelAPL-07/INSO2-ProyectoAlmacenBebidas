/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Producto;

/**
 *
 * @author Miguel Ángel
 */
@Stateless
public class ProductoFacade extends AbstractFacade<Producto> implements ProductoFacadeLocal {

    @PersistenceContext(unitName = "AlmacenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoFacade() {
        super(Producto.class);
    }

    @Override
    public List<Producto> obtenerProductoPorCategoria(String nombreCategoria) {
        List<Producto> productos = new ArrayList<>();
        try {
            // Consulta que se quiere realizar
            String consulta = "FROM Producto p WHERE p.categoria.nombreCategoria=:param1";
            
            // Crear consulta
            Query query = em.createQuery(consulta);
            
            // Cambiar parametros del WHERE
            query.setParameter("param1", nombreCategoria);
            
            // Ejecutar consulta
            productos = query.getResultList();
            
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return productos;
    }
    
    @Override
    public Producto obtenerProductoPorNombre(String nombre) {
        Producto producto = new Producto();
        try {
            // Consulta que se quiere realizar
            String consulta = "FROM Producto p WHERE p.nombre=:param1";
            
            // Crear consulta
            Query query = em.createQuery(consulta);
            
            // Cambiar parametros del WHERE
            query.setParameter("param1", nombre);
            
            // Ejecutar consulta
            if(query.getResultList() != null) {
                producto = (Producto) query.getResultList().get(0);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return producto;
    }
}
