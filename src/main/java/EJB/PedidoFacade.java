/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Pedido;
import modelo.Persona;

/**
 *
 * @author Miguel Ángel
 */
@Stateless
public class PedidoFacade extends AbstractFacade<Pedido> implements PedidoFacadeLocal {

    @PersistenceContext(unitName = "AlmacenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PedidoFacade() {
        super(Pedido.class);
    }
    
    @Override
    public Pedido obtenerPedidoPorFechaCreacionYUsuario(Date fechaCreacion, Persona persona) {
        Pedido p = null;
        try {
            // Consulta que se quiere realizar
            String consulta = "FROM Pedido p WHERE p.fechaCreacion=:param1 AND p.cliente.idPersona=:param2";

            // Crear consulta
            Query query = em.createQuery(consulta);

            // Cambiar parametros del WHERE
            query.setParameter("param1", fechaCreacion);
            query.setParameter("param2", persona.getIdPersona());

            // Ejecutar consulta
            List<Pedido> resultado = query.getResultList();

            
            if(resultado.size() > 0) {
                p = resultado.get(0);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return p;
    }
}
