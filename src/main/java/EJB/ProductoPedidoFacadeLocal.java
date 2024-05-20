/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.ProductoPedido;

/**
 *
 * @author PC
 */
@Local
public interface ProductoPedidoFacadeLocal {

    void create(ProductoPedido productoPedido);

    void edit(ProductoPedido productoPedido);

    void remove(ProductoPedido productoPedido);

    ProductoPedido find(Object id);

    List<ProductoPedido> findAll();

    List<ProductoPedido> findRange(int[] range);

    int count();
    
}
