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
import modelo.Rol;

/**
 *
 * @author Miguel Ángel
 */
@Stateless
public class RolFacade extends AbstractFacade<Rol> implements RolFacadeLocal {

    @PersistenceContext(unitName = "AlmacenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolFacade() {
        super(Rol.class);
    }
    
    @Override
    public Rol findByDescripcion(String descripcion) {
        Rol rol = new Rol();
        List<Rol> roles = findAll();
        for(Rol rActual : roles) {
            if(rActual.getDescripcion().equals(descripcion)) {
                rol = rActual;
            }
        }
        return rol;
    }
}
