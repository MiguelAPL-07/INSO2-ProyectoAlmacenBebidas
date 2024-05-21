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
import modelo.Menu;
import modelo.Usuario;

/**
 *
 * @author Miguel Ángel
 */
@Stateless
public class MenuFacade extends AbstractFacade<Menu> implements MenuFacadeLocal {

    @PersistenceContext(unitName = "AlmacenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MenuFacade() {
        super(Menu.class);
    }
    
    @Override
    public List<Menu> obtenerMenusUsuario(Usuario usuario) {
        List<Menu> resultado = new ArrayList<>();
        try {
            // Consulta a realizar
            String consulta = "FROM Menu m WHERE m.rol.idRol=:param1";
            // Crear consulta
            Query query = em.createQuery(consulta);
            // Cambiar parametros del WHERE
            query.setParameter("param1", usuario.getRol().getIdRol());
            // Ejecutar consulta
            resultado = query.getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return resultado;
    }
}
