package co.edu.eam.ingesoft.microempresa.negocio.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.edu.eam.ingesoft.microempresa.negocio.persistencia.Persistencia;
import co.edu.ingesoft.microempresa.persistencia.entidades.AreasEmpresa;
import co.edu.ingesoft.microempresa.persistencia.entidades.Auditoria;
import excepciones.ExcepcionNegocio;

/**
 * 
 * @author carlos martinez 
 * Se encarga de todas las operaciones a la tabla Auditoria
 */
@LocalBean
@Stateless
public class AuditoriaEJB {
	
	@EJB
	private Persistencia conexion;
	
	/**
	 * registrar
	 */
	public void crear(Auditoria objeto, int bd){
		conexion.setBd(bd);
		conexion.crear(objeto);
	}
	
	/**
	 * Buscar 
	 */
	public Auditoria buscar(int codigo, int bd){
		conexion.setBd(bd);
		return (Auditoria)conexion.buscar(Auditoria.class, codigo);
	}
	
	/**
	 * Listar la Auditoria de una determinada Tabla
	 */
	public List<Auditoria> listarByTabla(String tabla, int bd){
		conexion.setBd(bd);
		return (List<Auditoria>)(Object)conexion.listarConParametroString(Auditoria.ByTabla, tabla);
	}
	
}
