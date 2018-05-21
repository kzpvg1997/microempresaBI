package co.edu.eam.ingesoft.microempresa.negocio.beansDW;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.edu.ingesoft.microempresa.persistencia.datawarehouse.AuditoriaDW;
import co.edu.ingesoft.microempresa.persistencia.datawarehouse.VentaDW;

/**
 * EJB que se encargara de todas las operaciones al data warehouse 
 * el data warehouse estara alojado en MySql
 * @author Carlos Martinez & Kevin Zapata & Monica Sepulveda
 *
 */
@LocalBean
@Stateless
public class DataWarehouseEJB {
	
	/**
	 * Instancia a MySql para gestionar las transacciones
	 */
	@PersistenceContext(unitName = "mysql")
	private EntityManager emM;
	
	/**
	 * Ultimo proceso de Etl load, para el data warehouse
	 * @param auditoriasDW
	 * @param ventasDW
	 */
	public void load(List<AuditoriaDW> auditoriasDW, List<VentaDW> ventasDW){
		if(auditoriasDW != null){
			for (AuditoriaDW auditoriaDW : auditoriasDW) {
				emM.persist(auditoriaDW);
			}
		}
		if(ventasDW != null){
			for (VentaDW ventaDW : ventasDW) {
				emM.persist(ventaDW);
			}
		}
	}
	
	/**
	 * Elimina todos los registros de las tablas del data warehouse
	 */
	public void removeAllDW(){
		emM.createNativeQuery("DELETE * FROM Auditoria_DW,Clientes_DW,Empleados_DW,Ventas_DW");
	}
}
