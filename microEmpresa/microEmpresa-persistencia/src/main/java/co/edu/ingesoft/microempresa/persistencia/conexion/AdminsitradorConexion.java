/**
 * 
 */
package co.edu.ingesoft.microempresa.persistencia.conexion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 * @author TOSHIBAP55W
 *
 *         Calse encargada de la conexion a las multiples bases de datos
 */

public class AdminsitradorConexion {

	public static String dataBase;

	@PersistenceContext
	private static EntityManager em;

	@PersistenceContext(unitName = "postgress")
	private static EntityManager emP;

	@PersistenceContext(unitName = "oracle")
	private static EntityManager emO;

	@PersistenceContext(unitName = "mysql")
	private static EntityManager emMy;

	public static EntityManager getEntityManager() {

//		if (dataBase != null) {
//			if (dataBase.equals("postgress")) {
//				em = emP;
//			} else if (dataBase.equals("mysql")) {
//				em = emMy;
//			}
//		}
		
//		if(em==null){
//			EntityManagerFactory emf= Persistence.generateSchema("oracle", emO.getProperties());
//			
//		    em= emf.createEntityManager();
//		}
		return em;
	}
}
