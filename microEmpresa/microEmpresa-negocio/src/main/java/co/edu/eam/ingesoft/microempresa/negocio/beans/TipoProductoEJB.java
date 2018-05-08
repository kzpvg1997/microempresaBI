package co.edu.eam.ingesoft.microempresa.negocio.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.edu.eam.ingesoft.microempresa.negocio.persistencia.Persistencia;
import co.edu.ingesoft.microempresa.persistencia.entidades.TipoProducto;
import excepciones.ExcepcionNegocio;

/**
 * 
 * @author carlos martinez 
 * Se encarga de todas las operaciones a la tabla tipo producto
 */
@LocalBean
@Stateless
public class TipoProductoEJB {
	
	@EJB
	private Persistencia conexion;
	
	/**
	 * Registrar
	 */
	public void crear(TipoProducto objeto, int bd){
		conexion.setBd(bd);
		if(buscarByNombre(objeto.getNombre(), bd) == null){
			conexion.crear(objeto);
		}else{
			throw new ExcepcionNegocio("Ya existe un tipo producto con el nombre "+objeto.getNombre());
		}
	}
	
	/**
	 * Editar 
	 */
	public void editar(TipoProducto objeto, int bd){
		conexion.setBd(bd);
		TipoProducto q = buscarByNombre(objeto.getNombre(), bd);
		if(objeto.getCodigo() == q.getCodigo()){
			conexion.editar(objeto);
		}else{
			throw new ExcepcionNegocio("Ya existe un tipo de producto con el nombre "+objeto.getNombre());
		}
	}
	
	/**
	 * Buscar 
	 */
	public TipoProducto buscar(int codigo, int bd){
		conexion.setBd(bd);
		return (TipoProducto)conexion.buscar(TipoProducto.class, codigo);
	}
	
	/**
	 * Buscar por nombre
	 */
	public TipoProducto buscarByNombre(String nombre, int bd){
		conexion.setBd(bd);
		List<Object> lista = conexion.listarConParametroString(TipoProducto.buscarByNombre,nombre);
		if(lista.size() > 0){
			return (TipoProducto)lista.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * Listar
	 */
	public List<TipoProducto> listar(int bd){
		conexion.setBd(bd);
		return (List<TipoProducto>)(Object)conexion.listar(TipoProducto.listar);
	}
	
	/**
	 * Eliminar 
	 */
	public void eliminar(TipoProducto objeto, int bd){
		conexion.setBd(bd);
		if(buscar(objeto.getCodigo(), bd) != null){
			conexion.eliminar(objeto);
		}else{
			throw new ExcepcionNegocio("No existe un tipo producto con codigo "+objeto.getCodigo());
		}
	}
}
