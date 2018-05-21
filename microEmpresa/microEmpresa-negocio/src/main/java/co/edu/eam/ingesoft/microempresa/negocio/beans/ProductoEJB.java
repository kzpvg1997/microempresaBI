/**
 * 
 */
package co.edu.eam.ingesoft.microempresa.negocio.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.edu.eam.ingesoft.microempresa.negocio.persistencia.Persistencia;
import co.edu.ingesoft.microempresa.persistencia.entidades.Inventario;
import co.edu.ingesoft.microempresa.persistencia.entidades.InventarioProducto;
import co.edu.ingesoft.microempresa.persistencia.entidades.InventarioProductoPK;
import co.edu.ingesoft.microempresa.persistencia.entidades.Producto;
import excepciones.ExcepcionNegocio;

/**
 * @author TOSHIBAP55W
 *
 */
@LocalBean
@Stateless
public class ProductoEJB {

	@EJB
	private Persistencia conexion;
	
	@EJB
	private InventarioEJB inventarioEJB;
	
	public void crear(Producto pro, int bd){
		conexion.setBd(bd);
		// Buscamos si existe un producto con el mismo nombre
		List<Object> lista = conexion.listarConParametroString(Producto.buscarByNombre,pro.getNombre());
		if(lista.size() == 0){
			conexion.crear(pro);
		}else{
			throw new excepciones.ExcepcionNegocio("Ya existe unproducto con el nombre: "+pro.getNombre());
		}
	}
	
	public void editar(Producto pro, int bd){
		conexion.setBd(bd);
		Producto r = buscarByNombre(pro.getNombre(), bd);
		if(r.getCodigo() == pro.getCodigo()){
			conexion.editar(pro);
		}else{
			throw new ExcepcionNegocio("Ya existe un producto con el nombre: "+pro.getNombre());
		}
	}
	

	public void eliminar(Producto pro, int bd){
		conexion.setBd(bd);
		Producto r = buscar(pro.getCodigo(), bd);
		if(r != null){
			conexion.eliminar(r);
		}else{
			throw new ExcepcionNegocio("No existe un producto con el codigo "+pro.getCodigo());
		}
	}
	
	
	public Producto buscar(int id, int bd){
		conexion.setBd(bd);
		return (Producto) conexion.buscar(Producto.class, id);
	}
	

	public List<Producto> listar(int bd){
		conexion.setBd(bd);
		return (List<Producto>)(Object)conexion.listar(Producto.listarProductos);
	}
	
	public List<InventarioProducto> listarInventarioProducto(int bd){
		conexion.setBd(bd);
		return (List<InventarioProducto>)(Object)conexion.listar(Producto.listarInventarioProducto);
	}
	

	public Producto buscarByNombre(String nombre, int bd){
		conexion.setBd(bd);
		List<Object> lista = conexion.listarConParametroString(Producto.buscarByNombre,nombre);
		if(lista.size() > 0){
			return (Producto)lista.get(0);
		}else{
			return null;
		}
	}
	
	public List<InventarioProducto> productosXInventario (int CodigoInventario, int bd){
		conexion.setBd(bd);
		List<Object> lista = conexion.listarConParametroInteger(Producto.listarProductosXInventario,CodigoInventario);
		return (List<InventarioProducto>)(Object)lista;
	}
	
	public void asignarAInventario(InventarioProducto ip,int bd)throws ExcepcionNegocio{
		conexion.setBd(bd);
		//Buscamos el producto		
		Producto pro = buscar(ip.getProducto().getCodigo(), bd);
		if(pro!=null){
			conexion.crear(ip);
		}
	}
	
	public InventarioProducto buscarInventarioProducto(int idProducto, int idInventario,int bd){
		conexion.setBd(bd);
		InventarioProductoPK ip = new InventarioProductoPK();
		ip.setInventario(idInventario);
		ip.setProducto(idProducto);
		return (InventarioProducto)conexion.buscar(InventarioProducto.class, ip);
	}
	
	public void eliminarProductoInventario(InventarioProducto ip,int bd){
		conexion.setBd(bd);
		InventarioProducto ivp = buscarInventarioProducto(ip.getProducto().getCodigo(), ip.getInventario().getCodigo(), bd);
		if(ivp != null){
			conexion.eliminar(ivp);
		}else{
			throw new ExcepcionNegocio("No existe un producto asignado a este inventario");
		}
	}
	
	
}
