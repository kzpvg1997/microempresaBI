package co.edu.eam.ingesoft.microempresa.negocio.beansDW;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import co.edu.eam.ingesoft.microempresa.negocio.persistencia.Persistencia;
import co.edu.ingesoft.microempresa.persistencia.datawarehouse.AuditoriaDW;
import co.edu.ingesoft.microempresa.persistencia.datawarehouse.UsuarioDW;
import co.edu.ingesoft.microempresa.persistencia.entidades.Auditoria;
import co.edu.ingesoft.microempresa.persistencia.entidades.Departamento;
import co.edu.ingesoft.microempresa.persistencia.entidades.Usuario;
import co.edu.ingesoft.microempresa.persistencia.entidades.Venta;

/**
 * Proceso de ETL: La Extraccion de los datos de la base de datos
 * el data warehouse estara alojado en MySql
 * @author Carlos Martinez & Kevin Zapata & Monica Sepulveda
 *
 */
@LocalBean
@Stateless
public class ExtractionETL {
	
	@EJB
	private Persistencia conexion;
	
	/**
	 * Extrae todos los registros de una determinada tabla de la bd especificada
	 * @param bd base de datos de la cual va a extraer los registros
	 * @param tabla consulta de la tabla
	 * @return lista de registros
	 */
	public List<Object> extraer(int bd, String tabla){
		conexion.setBd(bd);
		return conexion.listar(tabla);
	}
	
	/**
	 * Extrae todos los registros de una determinada tabla de la bd especificada
	 */
	public List<Object> extraerByParametros(int bd, String tabla, Object[] parametros){
		conexion.setBd(bd);
		return conexion.listarByParametros(tabla, parametros);
	}
	
	/**
	 * Extrae todos los registros de una determinada tabla de las 2 bases de datos
	 * @param tabla consulta de la tabla
	 * @return lista de registros
	 */
	public List<Object> extraerAll(String tabla){
		List<Object> lista = new ArrayList<Object>(extraer(1, tabla));
		if(lista.addAll(extraer(2, tabla))){
			return lista;
		}else{
			throw new excepciones.ExcepcionFuncional("Extraction ETL: Union de los listados");
		}
	}
	
	
	
	/**
	 * Extrae todos los registros de una determinada tabla de las 2 bases de datos
	 * @param tabla consulta de la tabla
	 * @return lista de registros
	 */
	public List<Object> extraerAllByFechas(String tabla, Object[] fechas){
		List<Object> lista = new ArrayList<Object>(extraerByParametros(1, tabla, fechas));
		System.out.println("tamanio --------------- "+lista.size());
		if(lista.addAll(extraerByParametros(2, tabla, fechas))){
			System.out.println("tamanio --------------- "+lista.size());
			return lista;
		}else{
			throw new excepciones.ExcepcionFuncional("Extraction ETL: Union de los listados");
		}
	}
	
	// Estos metodos son creador por machete
	
	public List<Auditoria> auditoriaFecha(Date fechainicio, Date fechaFin){
		List<Auditoria> lista = new ArrayList<Auditoria>();
		List<Auditoria> auditorias = (List<Auditoria>)(Object)extraerAll(Auditoria.todo);
		for (Auditoria a : auditorias) {
			System.out.println("FECHA "+a.getFecha());
			if(a.getFecha().compareTo(fechainicio) >= 0 && a.getFecha().compareTo(fechaFin) <= 0){
				lista.add(a);
			}
		}
		return lista;
	}
	
	public List<Venta> ventaFecha(Date fechainicio, Date fechaFin){
		List<Venta> lista = new ArrayList<Venta>();
		List<Venta> ventas = (List<Venta>)(Object)extraerAll(Venta.todo);
		for (Venta v : ventas) {
			if(v.getFachaVenta().compareTo(fechainicio) >= 0 && v.getFachaVenta().compareTo(fechaFin) <= 0){
				lista.add(v);
			}
		}
		return lista;
	}
	
	/**
	 * Obtiene los usuarios asociados a las auditorias
	 * @param lista
	 * @return
	 */
	public List<Usuario> usuariosAuditoria(List<Auditoria> lista){
		List<Usuario> listado = new ArrayList<Usuario>();
		if(!lista.isEmpty()){
		for (Auditoria a : lista) {
			if(!buscarSiExiste(listado, a.getUsuario().getPersona().getCedula())){
				listado.add(a.getUsuario());
			}
		}
		}
		return listado;
	}
	
	/**
	 * Busca si ya existe un usuario auditoria en el listado
	 */
	public boolean buscarSiExiste(List<Usuario> lista, String cedula){
		for (Usuario u : lista) {
			if(u.getPersona().getCedula().equals(cedula)){
				// si existe
				return true;
			}
		}
		// no existe
		return false;
	}

}
