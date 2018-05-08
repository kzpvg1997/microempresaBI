package co.edu.eam.ingesoft.microempresa.negocio.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.edu.eam.ingesoft.microempresa.negocio.persistencia.Persistencia;
import co.edu.ingesoft.microempresa.persistencia.entidades.Rol;
import excepciones.ExcepcionNegocio;

/**
 * 
 * @author Carlos Martinez
 * Se encarga de todas las operaciones a la tabla roles
 */
@LocalBean
@Stateless
public class RolEJB {
	
	@EJB
	private Persistencia conexion;
	
	/**
	 * Registrar rol en la base de datos
	 * @param rol el rol a registrar
	 * @param bd base de datos en la que sera guardada la informacion
	 */
	public void crear(Rol rol, int bd){
		conexion.setBd(bd);
		// Buscamos si existe un rol con el mismo nombre
		List<Object> lista = conexion.listarConParametroString(Rol.buscarByNombre, rol.getNombre());
		if(lista.size() == 0){
			conexion.crear(rol);
		}else{
			throw new excepciones.ExcepcionNegocio("Ya existe un rol con el nombre: "+rol.getNombre());
		}
	}
	
	/**
	 * Editar
	 * @param rol, el rol a registrar
	 * @param bd base de datos en la que sera guardada la informacion
	 */
	public void editar(Rol rol, int bd){
		conexion.setBd(bd);
		Rol r = buscarByNombre(rol.getNombre(), bd);
		if(r.getCodigo() == rol.getCodigo()){
			conexion.editar(rol);
		}else{
			throw new ExcepcionNegocio("Ya existe un rol con el nombre "+rol.getNombre());
		}
	}
	
	/**
	 * Eliminar
	 * @param rol el rol a eliminar
	 * @param bd base de datos en la que sera eliminada la informacion
	 */
	public void eliminar(Rol rol, int bd){
		conexion.setBd(bd);
		Rol r = buscar(rol.getCodigo(), bd);
		if(r != null){
			conexion.eliminar(r);
		}else{
			throw new ExcepcionNegocio("No existe un rol con el codigo "+rol.getCodigo());
		}
	}
	
	/**
	 * Buscar rol por id
	 * @param id id del rol
	 * @param bd base de datos en la que buscara
	 * @return
	 */
	public Rol buscar(int id, int bd){
		conexion.setBd(bd);
		return (Rol) conexion.buscar(Rol.class, id);
	}
	
	/**
	 * Listar
	 * @param bd base de datos en la que obtendra los roles
	 * @return lista de roles
	 */
	public List<Rol> listar(int bd){
		conexion.setBd(bd);
		return (List<Rol>)(Object)conexion.listar(Rol.listarRoles);
	}
	
	/**
	 * Buscar rol por nombre
	 */
	public Rol buscarByNombre(String nombre, int bd){
		conexion.setBd(bd);
		List<Object> lista = conexion.listarConParametroString(Rol.buscarByNombre,nombre);
		if(lista.size() > 0){
			return (Rol)lista.get(0);
		}else{
			return null;
		}
	}

}
