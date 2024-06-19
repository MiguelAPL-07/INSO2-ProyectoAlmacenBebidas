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
import modelo.Persona;

/**
 *
 * @author Miguel Ángel
 */
@Stateless
public class PersonaFacade extends AbstractFacade<Persona> implements PersonaFacadeLocal {

    @PersistenceContext(unitName = "AlmacenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonaFacade() {
        super(Persona.class);
    }
    
    @Override
    public Persona obtenerPersonaPorDNI(String dni) {
        Persona p = null;
        try {
            // Consulta que se quiere realizar
            String consulta = "FROM Persona p WHERE p.dni=:param1";

            // Crear consulta
            Query query = em.createQuery(consulta);

            // Cambiar parametros del WHERE
            query.setParameter("param1", dni);

            // Ejecutar consulta
            List<Persona> resultado = query.getResultList();

            
            if(resultado.size() > 0) {
                p = resultado.get(0);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return p;
    }
    
    @Override
    public Persona obtenerPersonaPorID(int id) {
        Persona p = null;
        try {
            // Consulta que se quiere realizar
            String consulta = "FROM Persona p WHERE p.idPersona=:param1";

            // Crear consulta
            Query query = em.createQuery(consulta);

            // Cambiar parametros del WHERE
            query.setParameter("param1", id);

            // Ejecutar consulta
            List<Persona> resultado = query.getResultList();

            
            if(resultado.size() > 0) {
                p = resultado.get(0);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return p;
    }
}
