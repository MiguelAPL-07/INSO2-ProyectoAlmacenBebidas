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
import modelo.Usuario;

/**
 *
 * @author Miguel Ángel
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "AlmacenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    @Override
    public Usuario verificarUsuario(Usuario usuario) {
        Usuario r = null;
        try {
            // Consulta que se quiere realizar
            String consulta = "FROM Usuario u WHERE u.usuario=:param1 and u.contrasena=:param2";

            // Crear consulta
            Query query = em.createQuery(consulta);

            // Cambiar parametros del WHERE
            query.setParameter("param1", usuario.getUsuario());
            query.setParameter("param2", usuario.getContrasena());

            // Ejecutar consulta
            List<Usuario> resultado = query.getResultList();

            
            if(resultado.size() > 0) {
                r = resultado.get(0);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return r;
    }
}
