package co.edu.eam.ingesoft.microempresa.negocio.persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.edu.ingesoft.microempresa.persistencia.entidades.Inventario;
import co.edu.ingesoft.microempresa.persistencia.entidades.InventarioProducto;
import co.edu.ingesoft.microempresa.persistencia.entidades.Persona;
import co.edu.ingesoft.microempresa.persistencia.entidades.Producto;
import excepciones.ExcepcionFuncional;
import excepciones.ExcepcionNegocio;



/**
 * @author Carlos Martinez  
 * Esta clase se encarga de realizar todas las operaciones a la base de datos
 * de la cual este utilizando el sistema, sea oracle, mysql, postgress.
 */

@LocalBean
@Stateless
public class Persistencia  implements Serializable{
	
	/**
	 * Instancia a Oracle (1)
	 */
	@PersistenceContext(unitName = "oracle")
	private EntityManager emO;
	
	/**
	 * Instancia a postgress (2)
	 */
	@PersistenceContext(unitName = "postgress")
	private EntityManager emP;
	
//	@PersistenceContext(unitName = "mysql")
//	private EntityManager emM;
	
	/**
	 * Es la base de dato en la cual esta funcionando el sistema actualmente
	 */
	private int bd;
	
	/**
	 * Guarda en la base de datos
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void crear(Object objeto){
		switch (this.bd) {
		case 1:
			emO.persist(objeto);
			break;
		case 2:
			emP.persist(objeto);
			break;
		default:
			throw new excepciones.ExcepcionFuncional("La base de datos #"+this.bd+" no existe.");
		}
	}
	
	/**
	 * Edita en la base de datos
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void editar(Object objeto){
		switch (this.bd) {
		case 1:
			emO.merge(objeto);
			break;
		case 2:
			emP.merge(objeto);
			break;
		default:
			throw new excepciones.ExcepcionFuncional("La base de datos #"+this.bd+" no existe.");
		}
	}
	
	
	/**
	 * Elimina de la base de datos
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminar(Object objeto){
		switch (this.bd) {
		case 1:
			emO.remove(objeto);
			break;
		case 2:
			emP.remove(objeto);
			break;
		default:
			throw new excepciones.ExcepcionFuncional("La base de datos #"+this.bd+" no existe.");
		}
	}
	
	/**
	 * Busca en una base de datos determinada
	 * @param objeto el tipo de objeto a buscar
	 * @param pk el identificador del registro a buscar
	 * @return retorna el registro encontrado, de lo contrario null
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Object buscar(Class type, Object pk){
		switch (this.bd) {
		case 1:
			return emO.find(type, pk);
		case 2:
			return emP.find(type, pk);
		default:
			throw new excepciones.ExcepcionFuncional("La base de datos #"+this.bd+" no existe.");
		}
	}
	
	/**
	 * Metodo que permite buscar en alguna base de datos por un parametro de tipo Integer
	 * @param parametro el parametro por ql cual se desea busacar
	 * @return el objeto que se desea buscar
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Object buscarXParametroInt (Class type,int parametro){
		switch (this.bd) {
		case 1:
			Query q = emO.createNamedQuery(Persona.buscarXCedula);
			q.setParameter(1, parametro);
			List<Persona> persona = q.getResultList();
			if(persona.isEmpty()){
				return null;
			}else{
				return persona.get(0);
			}
		case 2:
			Query p = emO.createNamedQuery(Persona.buscarXCedula);
			p.setParameter(1, parametro);
			List<Persona> personaB = p.getResultList();
			if(personaB.isEmpty()){
				return null;
			}else{
				return personaB.get(0);
			}
		default:
			throw new excepciones.ExcepcionFuncional("La base de datos #"+this.bd+" no existe.");
		}
	}
	
	/**
	 * Listar objetos
	 * @param sql consulta a ejecutar, nos traera objetos de una determinada tabla
	 * @return lista de los objetos encontrados
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object> listar(String sql){
		switch (this.bd) {
		case 1:
			Query q = emO.createNamedQuery(sql);
			return q.getResultList();
		case 2:
			Query p = emP.createNamedQuery(sql);
			return p.getResultList();
		default:
			throw new excepciones.ExcepcionFuncional("La base de datos #"+this.bd+" no existe.");
		}
	}
	
	/**
	 * Listar objetos usando parametros
	 * @param sql consulta a ejecutar, nos traera objetos de una determinada tabla
	 * @parametros los parametros necesarios para la consulta
	 * @return lista de los objetos encontrados
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object> listarByParametros(String sql, Object[] parametros){
		switch (this.bd) {
		case 1:
			Query q = emO.createNamedQuery(sql);
			for (int i = 0; i < parametros.length; i++) {
				q.setParameter(i+1, parametros[i]);
				System.out.println("-------------- PARAMETRO "+i+" --------------");
			}
			return q.getResultList();
		case 2:
			Query p = emP.createNamedQuery(sql);
			for (int i = 0; i < parametros.length; i++) {
				p.setParameter(i+1, parametros[i]);
				System.out.println("-------------- PARAMETRO "+i+" --------------");
			}
			return p.getResultList();
		default:
			throw new excepciones.ExcepcionFuncional("La base de datos #"+this.bd+" no existe.");
		}
	}
	
	
	
	/**
	 * Listar objetos usando un parametro
	 * @param sql consulta a ejecutar, nos traera objetos de una determinada tabla
	 * @parametro el parametro necesario para la consulta
	 * @return lista de los objetos encontrados
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object> listarConParametroInteger(String sql, Object parametro){
		switch (this.bd) {
		case 1:
			Query q = emO.createNamedQuery(sql);
			q.setParameter(1, parametro);
			return q.getResultList();
		case 2:
			Query p = emP.createNamedQuery(sql);
			p.setParameter(1, parametro);
			return p.getResultList();
		default:
			throw new excepciones.ExcepcionFuncional("La base de datos #"+this.bd+" no existe.");
		}
	}
	
	/**
	 * Listar objetos usando un parametro String
	 * @param sql consulta a ejecutar, nos traera objetos de una determinada tabla
	 * @parametro el parametro necesario para la consulta
	 * @return lista de los objetos encontrados
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object> listarConParametroString(String sql, String parametro){
		switch (this.bd) {
		case 1:
			Query q = emO.createNamedQuery(sql);
			q.setParameter(1, parametro);
			return q.getResultList();
		case 2:
			Query p = emP.createNamedQuery(sql);
			p.setParameter(1, parametro);
			return p.getResultList();
		default:
			throw new excepciones.ExcepcionFuncional("La base de datos #"+this.bd+" no existe.");
		}
	}
	
	/**
	 * Listar objetos de una tabla usando las 2 bases de datos
	 * @param sql consulta a ejecutar, nos traera objetos de una determinada tabla
	 * @return lista de los objetos encontrados
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object> listarCon2BasesDatos(String sql){
			Query q = emO.createNamedQuery(sql);
			Query p = emP.createNamedQuery(sql);
			List<Object> lista = new ArrayList<Object>(q.getResultList());
			if(lista.addAll(p.getResultList())){
				return lista;
			}else{
				throw new excepciones.ExcepcionFuncional("No se pudo unir los dos listados de las bases de datos");
			}
	}
	
	/**
	 * Consulta nativa encargada de registrar un producto a un inventario
	 * @param invenProduc el enventario/producto al que se va asignar 
	 * @throws ExcepcionNegocio en caso de error con la base de datos
	 */
	public void asignarProductoAInventario(InventarioProducto invenProduc)throws ExcepcionNegocio{
		String sql = "INSERT INTO Inventario_Producto (inventario,producto,empleado,fecha_ingreso,cantidad) "
				+ "VALUES (?1,?2,?3,?4,?5)";
		Query q;
		
		switch (this.bd) {
		case 1:
			q =emO.createNativeQuery(sql);
			break;
		case 2: 
			q =emO.createNativeQuery(sql);
			break;
		default:
			throw new excepciones.ExcepcionFuncional("La base de datos #"+this.bd+" no existe.");
		}
		
		q.setParameter(1, invenProduc.getInventario().getCodigo());
		q.setParameter(2, invenProduc.getProducto().getCodigo());
		q.setParameter(3, invenProduc.getPersonaEmpleado().getCodigo());
		q.setParameter(4, invenProduc.getFechaIngreso());
		q.setParameter(5, invenProduc.getCantidad());
		q.executeUpdate();
	}
	
	public void eliminarProductoInventario (InventarioProducto invenProduc) throws ExcepcionNegocio{
		
		String sql = "DELETE FROM Inventario_Producto WHERE inventario=?1 AND producto=?2";
		Query q;
		
		switch (this.bd) {
		case 1:
			q =emO.createNativeQuery(sql);
			break;
		case 2: 
			q =emO.createNativeQuery(sql);
			break;
		default:
			throw new excepciones.ExcepcionFuncional("La base de datos #"+this.bd+" no existe.");
		}
		
		q.setParameter(1, invenProduc.getInventario().getCodigo());
		q.setParameter(2, invenProduc.getProducto().getCodigo());;
		q.executeUpdate();
	}

	/**
	 * Accesores Y Modificadores
	 */
	
	public int getBd() {
		return bd;
	}

	public void setBd(int bd) {
		this.bd = bd;
	}	

}
