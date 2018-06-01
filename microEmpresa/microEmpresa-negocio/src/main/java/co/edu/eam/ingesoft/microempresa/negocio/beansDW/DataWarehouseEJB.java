package co.edu.eam.ingesoft.microempresa.negocio.beansDW;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.edu.ingesoft.microempresa.persistencia.datawarehouse.AuditoriaDW;
import co.edu.ingesoft.microempresa.persistencia.datawarehouse.ClienteDW;
import co.edu.ingesoft.microempresa.persistencia.datawarehouse.EmpleadoDW;
import co.edu.ingesoft.microempresa.persistencia.datawarehouse.UsuarioDW;
import co.edu.ingesoft.microempresa.persistencia.datawarehouse.VentaDW;
import co.edu.ingesoft.microempresa.persistencia.entidades.ReporteDW;
import co.edu.ingesoft.microempresa.persistencia.entidades.Rol;
import co.edu.ingesoft.microempresa.persistencia.entidades.Usuario;
import excepciones.ExcepcionNegocio;

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
		/* Inicia el proceso de almacenamiento de auditoria en el Data warehouse */
		if(auditoriasDW != null){
			for (AuditoriaDW auditoriaDW : auditoriasDW) {
				// Buscamos si ya existe el usuario, para asi no crearlo mas de 1 vez
				UsuarioDW u = (UsuarioDW)buscarByCedula(UsuarioDW.byCedula, auditoriaDW.getUsuario().getCedula());
				if(u == null){
					//si no existe, lo creamos
					emM.persist(auditoriaDW.getUsuario());
					u = (UsuarioDW)buscarByCedula(UsuarioDW.byCedula, auditoriaDW.getUsuario().getCedula());
				}
				// seteamos el usuario ya persistido en el objeto de auditoria a persistir
				auditoriaDW.setUsuario(u);
				emM.persist(auditoriaDW);
			}
		}
		/* Inicia el proceso de almacenamiento de ventas en el Data warehouse */
		if(ventasDW != null){
			for (VentaDW ventaDW : ventasDW) {
				//Buscamos si ya existe el empleado, para asi no crearlo mas de 1 vez
				EmpleadoDW e = (EmpleadoDW)buscarByCedula(EmpleadoDW.byCedula, ventaDW.getPersonaEmpleado().getCedula());
				if(e == null){
					//si no existe, lo creamos
					emM.persist(ventaDW.getPersonaEmpleado());
					e = (EmpleadoDW)buscarByCedula(EmpleadoDW.byCedula, ventaDW.getPersonaEmpleado().getCedula());
				}
				// seteamos el empleado ya persistido en el objeto de ventaDW a persistir
				ventaDW.setPersonaEmpleado(e);
				/* Repetimos el mismo proceso para cliente */
				//Buscamos si ya existe el cliente, para asi no crearlo mas de 1 vez
				ClienteDW c = (ClienteDW)buscarByCedula(ClienteDW.byCedula, ventaDW.getPersonaCliente().getCedula());
				if(c == null){
					//si no existe, lo creamos
					emM.persist(ventaDW.getPersonaCliente());
					c = (ClienteDW)buscarByCedula(ClienteDW.byCedula, ventaDW.getPersonaCliente().getCedula());
				}
				// seteamos el cliente ya persistido en el objeto de ventaDW a persistir
				ventaDW.setPersonaCliente(c);
				emM.persist(ventaDW);
			}
		}
	}
	
	/**
	 * Elimina todos los registros de las tablas del data warehouse
	 */
	public void removeAllDW(){
		String[] tablas = {"Auditoria_DW","Ventas_DW","Clientes_DW","Empleados_DW","Usuarios_DW"};
		for (int i = 0; i < tablas.length; i++) {
			Query q = emM.createNativeQuery("DELETE FROM "+tablas[i]+"");
			q.executeUpdate();
		}
	}
	
	
	/**
	 * Buscar persona por cedula
	 */
	public Object buscarByCedula(String sql,String parametro){
		Query p = emM.createNamedQuery(sql);
		p.setParameter(1, parametro);
		List<Object> lista = p.getResultList();
		if(lista.size() > 0){
			return lista.get(0);
		}else{
			return null;
		}
	}
	

	/**
	 * Metodo que registra las ventas del Data Wherehouse
	 * @return lista de ventas del DW
	 */
	public List<VentaDW> listarVentasDW (){
		Query q = emM.createNamedQuery(VentaDW.listarVentasDW);
		List<VentaDW> lista = q.getResultList();
		return lista;
	}
	
	/**
	 * Netodo que lista las auditorias del Data Wherehouse
	 * @return lista de auditorias del DW
	 */
	public List<AuditoriaDW> listarAruditoriasDW(){
		Query q = emM.createNamedQuery(AuditoriaDW.listarAuditoriasDW);
		List<AuditoriaDW> lista = q.getResultList();
		return lista;
	}
	
	/**
	 * Metodo que lista los reportes del DW
	 * @return reporte DW
	 */
	public List<ReporteDW> listarReportesDW(int codigoUsuario){
		Query q = emM.createNamedQuery(ReporteDW.listarReportesDWXUsuario);
		q.setParameter(1, codigoUsuario);
		List<ReporteDW> lista = q.getResultList();
		return lista;
	}
	
	public void generarReporteDW(ReporteDW reporte)throws ExcepcionNegocio{
		ReporteDW rep = buscarReporteDW(reporte.getNombreReporte());
		if(rep!=null){
			emM.persist(reporte);
		}else{
			throw new ExcepcionNegocio("El reporte :"+reporte.getNombreReporte()+" ya fue realizado");
		}
	}
	
	public ReporteDW buscarReporteDW(String nombre){
		List<ReporteDW> lista = (List<ReporteDW>) emM.createNamedQuery(ReporteDW.BuscarByNombre);
		if(lista.size()>0){
			return lista.get(0);
		}else{
			return null;
		}
	}
	
}
