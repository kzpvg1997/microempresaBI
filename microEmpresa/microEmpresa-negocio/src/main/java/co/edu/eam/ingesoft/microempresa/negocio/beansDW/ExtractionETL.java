package co.edu.eam.ingesoft.microempresa.negocio.beansDW;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import co.edu.eam.ingesoft.microempresa.negocio.persistencia.Persistencia;
import co.edu.ingesoft.microempresa.persistencia.entidades.Departamento;

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
	 * Extrae todos los registros de una determinada tabla de la bd
	 * @param bd base de datos de la cual va a extraer los registros
	 * @param tabla consulta de la tabla
	 * @return lista de registros
	 */
	public List<Object> extraer(int bd, String tabla){
		conexion.setBd(bd);
		return conexion.listar(tabla);
	}
	
	/**
	 * Extrae todos los registros de una determinada tabla de las 2 bases de datos
	 * @param tabla consulta de la tabla
	 * @return lista de registros
	 */
	public List<Object> extraerAll(String tabla){
		return extraer(2, tabla);
	//	List<Object> lista = new ArrayList<Object>(extraer(2, tabla)); 342 (1) -- 
//		if(lista.addAll(extraer(2, tabla))){
//			return lista;
//		}else{
//			throw new excepciones.ExcepcionFuncional("Extraction ETL: No se pudo unir los dos listados");
//		}
	}
	

}
