package co.edu.eam.ingesoft.microempresa.negocio.beansDW;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	private EntityManager emO;
	
	
}
