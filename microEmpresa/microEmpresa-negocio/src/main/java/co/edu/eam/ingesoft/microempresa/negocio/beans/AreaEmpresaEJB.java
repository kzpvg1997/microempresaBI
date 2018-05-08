package co.edu.eam.ingesoft.microempresa.negocio.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.edu.eam.ingesoft.microempresa.negocio.persistencia.Persistencia;
import co.edu.ingesoft.microempresa.persistencia.entidades.AreasEmpresa;
import excepciones.ExcepcionNegocio;

/**
 * 
 * @author carlos martinez 
 * Se encarga de todas las operaciones a la tabla empresas
 */
@LocalBean
@Stateless
public class AreaEmpresaEJB {
	
	@EJB
	private Persistencia conexion;
	
	/**
	 * Registrar area empresa en la base de datos
	 * @param areaEmpresa la area empresa a registrar
	 * @param bd base de datos en la que sera guardada la informacion
	 */
	public void crear(AreasEmpresa areaEmpresa, int bd){
		conexion.setBd(bd);
		if(buscarByNombre(areaEmpresa.getNombre(), bd) == null){
			conexion.crear(areaEmpresa);
		}else{
			throw new ExcepcionNegocio("Ya existe un area empresa con el nombre "+areaEmpresa.getNombre());
		}
	}
	
	/**
	 * Editar Area de la empresa
	 * @param areaEmpresa el area de la empresa a editar
	 * @param bd base de datos en la que sera guardada la informacion
	 */
	public void editar(AreasEmpresa areaEmpresa, int bd){
		conexion.setBd(bd);
		AreasEmpresa q = buscarByNombre(areaEmpresa.getNombre(), bd);
		if(areaEmpresa.getCodigo() == q.getCodigo()){
			conexion.editar(areaEmpresa);
		}else{
			throw new ExcepcionNegocio("Ya existe un area empresa con el nombre "+areaEmpresa.getNombre());
		}
	}
	
	/**
	 * Buscar Area empresa por codigo
	 * @param codigo el codigo del area de la empresa a buscar
	 * @param bd base de datos en la que buscara la empresa
	 */
	public AreasEmpresa buscar(int codigo, int bd){
		conexion.setBd(bd);
		return (AreasEmpresa)conexion.buscar(AreasEmpresa.class, codigo);
	}
	
	/**
	 * Buscar Area empresa por nombre
	 * @param nombre el nombre del area de la empresa a buscar
	 * @param bd base de datos en la que buscara la empresa
	 */
	public AreasEmpresa buscarByNombre(String nombre, int bd){
		conexion.setBd(bd);
		List<Object> lista = conexion.listarConParametroString(AreasEmpresa.buscarByNombre,nombre);
		if(lista.size() > 0){
			return (AreasEmpresa)lista.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * Listado de todas las areas de una empresa determinada
	 * @param bd
	 * @param codigoEmpresa
	 * @return
	 */
	public List<AreasEmpresa> listarAreasEmpresa(int bd,int codigoEmpresa){
		conexion.setBd(bd);
		return (List<AreasEmpresa>)(Object)conexion.listarConParametroInteger(AreasEmpresa.listarXEmpresa,codigoEmpresa);
	}
	
	/**
	 * Eliminar Area Empresa
	 * @param areaEmpresa el area de la empresa a eliminar
	 * @param bd base de datos en la que sera eliminada la informacion
	 */
	public void eliminar(AreasEmpresa areaEmpresa, int bd){
		conexion.setBd(bd);
		if(buscar(areaEmpresa.getCodigo(), bd) != null){
			conexion.eliminar(areaEmpresa);
		}else{
			throw new ExcepcionNegocio("Noexiste un area empresa con el codigo "+areaEmpresa.getCodigo());
		}
	}
}
