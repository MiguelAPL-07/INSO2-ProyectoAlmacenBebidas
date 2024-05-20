/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.EstadoPedido;

/**
 *
 * @author PC
 */
@Local
public interface EstadoPedidoFacadeLocal {

    void create(EstadoPedido estadoPedido);

    void edit(EstadoPedido estadoPedido);

    void remove(EstadoPedido estadoPedido);

    EstadoPedido find(Object id);

    List<EstadoPedido> findAll();

    List<EstadoPedido> findRange(int[] range);

    int count();
    
}
