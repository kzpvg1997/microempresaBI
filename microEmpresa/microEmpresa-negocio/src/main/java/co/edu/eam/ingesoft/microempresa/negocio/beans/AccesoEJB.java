package co.edu.eam.ingesoft.microempresa.negocio.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.edu.eam.ingesoft.microempresa.negocio.persistencia.Persistencia;
import co.edu.ingesoft.microempresa.persistencia.entidades.Acceso;

/**
 * 
 * @author carlos
 * Se encarga de todas las operaciones a la tabla accesos
 */
@LocalBean
@Stateless
public class AccesoEJB {
	
	@EJB
	private Persistencia conexion;
	
	/**
	 * Buscar Acceso por id
	 * @param id id del Acceso
	 * @param bd base de datos en la que buscara
	 * @return
	 */
	public Acceso buscar(int id, int bd){
		conexion.setBd(bd);
		return (Acceso) conexion.buscar(Acceso.class, id);
	}
	
	/**
	 * Listar Accesos
	 * @param bd base de datos en la que obtendra los accesos
	 * @return lista de accesos
	 */
	public List<Acceso> listar(int bd){
		conexion.setBd(bd);
		return (List<Acceso>)(Object)conexion.listar(Acceso.listarAccesos);
	}

}