/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Producto;

/**
 *
 * @author Miguel Ángel
 */
@Local
public interface ProductoFacadeLocal {

    void create(Producto producto);

    void edit(Producto producto);

    void remove(Producto producto);

    Producto find(Object id);

    List<Producto> findAll();

    List<Producto> findRange(int[] range);

    int count();
    
    List<Producto> obtenerProductoPorCategoria(String nombreCategoria);
    
    Producto obtenerProductoPorNombre(String nombre);
    
    Producto obtenerProductoPorID(int idProducto);
    
}
