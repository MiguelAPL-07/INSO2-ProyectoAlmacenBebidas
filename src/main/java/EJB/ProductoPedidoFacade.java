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
import modelo.Pedido;
import modelo.ProductoPedido;

/**
 *
 * @author Miguel Ángel
 */
@Stateless
public class ProductoPedidoFacade extends AbstractFacade<ProductoPedido> implements ProductoPedidoFacadeLocal {

    @PersistenceContext(unitName = "AlmacenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoPedidoFacade() {
        super(ProductoPedido.class);
    }
    
    @Override
    public List<ProductoPedido> obtenerProductosPedidosPorPedido(int idPedido) {
        List<ProductoPedido> productosPedido = new ArrayList<>();
        try {
            // Consulta que se quiere realizar
            String consulta = "FROM ProductoPedido p WHERE p.pedido.idPedido=:param1";

            // Crear consulta
            Query query = em.createQuery(consulta);

            // Cambiar parametros del WHERE
            query.setParameter("param1", idPedido);

            // Ejecutar consulta
            productosPedido = query.getResultList();

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return productosPedido;
    }
}
