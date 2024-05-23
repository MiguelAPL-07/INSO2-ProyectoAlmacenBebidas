/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Categoria;

/**
 *
 * @author Miguel Ángel
 */
@Stateless
public class CategoriaFacade extends AbstractFacade<Categoria> implements CategoriaFacadeLocal {

    @PersistenceContext(unitName = "AlmacenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoriaFacade() {
        super(Categoria.class);
    }

    @Override
    public Categoria obtenerCategoriaPorNombre(String nombre) {
        Categoria c = new Categoria();
        try {
            // Consulta que se quiere realizar
            String consulta = "FROM Categoria c WHERE c.nombreCategoria=:param1";
            
            // Crear consulta
            Query query = em.createQuery(consulta);
            
            // Cambiar parametros del WHERE
            query.setParameter("param1", nombre);
            
            // Ejecutar consulta
            c = (Categoria) query.getResultList().get(0);
            
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return c;
    }
    
}
