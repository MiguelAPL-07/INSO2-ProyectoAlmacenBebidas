/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.EstadoPedido;

/**
 *
 * @author Miguel Ángel
 */
@Stateless
public class EstadoPedidoFacade extends AbstractFacade<EstadoPedido> implements EstadoPedidoFacadeLocal {

    @PersistenceContext(unitName = "AlmacenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadoPedidoFacade() {
        super(EstadoPedido.class);
    }
    
    @Override
    public EstadoPedido obtenerEstadoPedidoPorDescripcion(String descripcion) {
        EstadoPedido ep = null;
        try {
            // Consulta que se quiere realizar
            String consulta = "FROM EstadoPedido e WHERE e.descripcion=:param1";

            // Crear consulta
            Query query = em.createQuery(consulta);

            // Cambiar parametros del WHERE
            query.setParameter("param1", descripcion);

            // Ejecutar consulta
            List<EstadoPedido> resultado = query.getResultList();

            
            if(resultado.size() > 0) {
                ep = resultado.get(0);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return ep;
    }
    
    @Override
    public EstadoPedido obtenerEstadoPedidoPorID(int id) {
        EstadoPedido ep = null;
        try {
            // Consulta que se quiere realizar
            String consulta = "FROM EstadoPedido e WHERE e.idEstado=:param1";

            // Crear consulta
            Query query = em.createQuery(consulta);

            // Cambiar parametros del WHERE
            query.setParameter("param1", id);

            // Ejecutar consulta
            List<EstadoPedido> resultado = query.getResultList();

            
            if(resultado.size() > 0) {
                ep = resultado.get(0);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return ep;
    }
}
