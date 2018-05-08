package co.edu.eam.ingesoft.microempresa.negocio.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.edu.eam.ingesoft.microempresa.negocio.persistencia.Persistencia;
import co.edu.ingesoft.microempresa.persistencia.entidades.Acceso;
import co.edu.ingesoft.microempresa.persistencia.entidades.AreasEmpresa;
import co.edu.ingesoft.microempresa.persistencia.entidades.Rol;
import co.edu.ingesoft.microempresa.persistencia.entidades.RolAcceso;
import co.edu.ingesoft.microempresa.persistencia.entidades.RolAccesoPK;
import excepciones.ExcepcionNegocio;

/**
 * 
 * @author carlos
 * Se encarga de todas las operaciones a la tabla accesos
 */
@LocalBean
@Stateless
public class AccesosRolEJB {

	@EJB
	private Persistencia conexion;
	
	@EJB
	private RolEJB rolEJB;
	
	@EJB
	private AccesoEJB accesoEJB;
	
	/**
	 * Registrar
	 * @param bd base de datos en la que sera guardada la informacion
	 */
	public void crear(RolAcceso rolAcceso, int bd){
		conexion.setBd(bd);
		Rol rol = rolEJB.buscar(rolAcceso.getRol().getCodigo(), bd);
		if(rol != null){
			Acceso acceso = accesoEJB.buscar(rolAcceso.getAcceso().getCodigo(), bd);
			if(acceso != null){
				RolAcceso ra = buscar(rolAcceso, bd);
				if(ra == null){
					rolAcceso.setAcceso(acceso);
					rolAcceso.setRol(rol);
					conexion.crear(rolAcceso);
				}else{
					throw new ExcepcionNegocio("Ya existe este acceso en el rol "+rolAcceso.getRol().getNombre());
				}
			}else{
				throw new ExcepcionNegocio("No existe el acceso");
			}
		}else{
			throw new ExcepcionNegocio("No existe el rol");
		}
	}
	
	/**
	 * Eliminar 
	 */
	public void eliminar(RolAcceso rolAcceso, int bd){
		conexion.setBd(bd);
		Rol rol = rolEJB.buscar(rolAcceso.getRol().getCodigo(), bd);
		if(rol != null){
			Acceso acceso = accesoEJB.buscar(rolAcceso.getAcceso().getCodigo(), bd);
			if(acceso != null){
				RolAcceso ra = buscar(rolAcceso, bd);
				if(ra != null){
					conexion.eliminar(ra);
				}else{
					throw new ExcepcionNegocio("No existe este acceso en el rol "+rolAcceso.getRol().getNombre());
				}
			}else{
				throw new ExcepcionNegocio("No existe el acceso");
			}
		}else{
			throw new ExcepcionNegocio("No existe el rol");
		}
	}
	
	/**
	 * Buscar rol por id
	 * @param id id del rol
	 * @param bd base de datos en la que buscara
	 * @return
	 */
	public RolAcceso buscar(RolAcceso rolAcceso, int bd){
		conexion.setBd(bd);
		RolAccesoPK pk = new RolAccesoPK(rolAcceso.getRol().getCodigo(), rolAcceso.getAcceso().getCodigo());
		return (RolAcceso) conexion.buscar(RolAcceso.class, pk);
	}
	
	/**
	 * Listar Accesos por Rol
	 * @param bd base de datos en la que obtendra los accesos por rol
	 * @return lista de accesos de un determinado rol
	 */
	public List<Acceso> listarByRol(Rol rol, int bd){
		conexion.setBd(bd);
		List<Acceso> listado = new ArrayList<Acceso>();
		List<Object> lista = conexion.listarConParametroInteger(RolAcceso.listarAccesosByRol, rol.getCodigo());
		for (Object object : lista) {
			RolAcceso ra = (RolAcceso)object;
			listado.add(ra.getAcceso());
		}
		return listado;
	}
	
	/**
	 * Listar RolAccesos por Rol
	 */
	public List<RolAcceso> listarAccesosRolByRol(Rol rol, int bd){
		conexion.setBd(bd);
		return (List<RolAcceso>)(Object)conexion.listarConParametroInteger(RolAcceso.listarAccesosByRol, rol.getCodigo());
	}
	
}
