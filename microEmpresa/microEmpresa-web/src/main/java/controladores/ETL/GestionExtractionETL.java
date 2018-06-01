package controladores.ETL;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import co.edu.eam.ingesoft.microempresa.negocio.beans.AuditoriaEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beansDW.ExtractionETL;
import co.edu.ingesoft.microempresa.persistencia.entidades.Auditoria;
import co.edu.ingesoft.microempresa.persistencia.entidades.Persona;
import co.edu.ingesoft.microempresa.persistencia.entidades.Usuario;
import co.edu.ingesoft.microempresa.persistencia.entidades.Venta;
import session.SessionController;

/**
 * @author Extraccion proceso de etl
 *
 */
@ViewScoped
@Named("extractionETL")
public class GestionExtractionETL implements Serializable{
	
	@Inject
	private SessionController sesion;
	
	@EJB
	private ExtractionETL extractionETL;
	
	@EJB
	private AuditoriaEJB auditoriaEJB; 
	
	public static List<Auditoria> auditorias;
	
	private List<Usuario> usuarios;
	
	public static List<Venta> ventas;
	
	public static int tipoCargaDatos;
	
	public static Date fechaInicio;
	
	public static Date fechaFin;
	
	@PostConstruct
	public void inicializar(){
		listar();
		auditoria("Extraccion");
	}
	
	/**
	 * Listar todos los registros en las diferentes tablas de la interfaz
	 */
	public void listar(){
		try{
			// Formamos el vector de objects para pasar como parametros al metodo
			Object[] parametros = new Object[2];
			parametros[0] = fechaInicio;
			parametros[1] = fechaFin;
			
			switch (tipoCargaDatos) {
			case 1:
				System.out.println("-------------- ENTRO A ACUMULACION SIMPLE --------------");
				System.out.println(fechaInicio+"   ------------  "+fechaFin);
				// Acumulacion simple
//				auditorias = (List<Auditoria>)(Object)extractionETL.extraerAllByFechas(Auditoria.ByFechas, parametros);
//				ventas = (List<Venta>)(Object)extractionETL.extraerAllByFechas(Venta.ByFechas, parametros);
				auditorias = (List<Auditoria>)(Object)extractionETL.auditoriaFecha(fechaInicio, fechaFin);
				ventas = (List<Venta>)(Object)extractionETL.ventaFecha(fechaInicio, fechaFin);
				usuarios = extractionETL.usuariosAuditoria(auditorias);
				break;
			case 2:
				// Rolling
				auditorias = (List<Auditoria>)(Object)extractionETL.extraerAll(Auditoria.todo);
				usuarios = extractionETL.usuariosAuditoria(auditorias);
				ventas = (List<Venta>)(Object)extractionETL.extraerAll(Venta.todo);
				System.out.println("------------ VENTAS ---- "+ventas.size());
				
				break;
			default:
				break;
			}
		}catch (excepciones.ExcepcionFuncional e) {
			Messages.addFlashGlobalInfo(e.getMessage());
		}catch(Exception e){
			Messages.addFlashGlobalInfo("El sistema ha hecho BUM!");
		}
	}

	/**
	 * Proceso de registro de la auditoria de etl
	 */
	public void auditoria(String accion){
		Date fecha = new Date();
		String origen = "Celular";
		String navegador = "Google chrome";
		Auditoria auditoria = new Auditoria();
		auditoria.setTabla("ETL");
		auditoria.setAccion(accion);
		auditoria.setRegistro(0);
		auditoria.setFecha(fecha);
		auditoria.setOrigen(origen);
		auditoria.setNavegador(navegador);
		auditoria.setUsuario(sesion.getUsuario());
		auditoriaEJB.crear(auditoria, sesion.getBd());
	}
	
	public List<Auditoria> getAuditorias() {
		return auditorias;
	}

	public void setAuditorias(List<Auditoria> auditorias) {
		this.auditorias = auditorias;
	}

	public List<Venta> getVentas() {
		return ventas;
	}

	public void setVentas(List<Venta> ventas) {
		this.ventas = ventas;
	}

	/**
	 * @return the tipoCargaDatos
	 */
	public int getTipoCargaDatos() {
		return tipoCargaDatos;
	}

	/**
	 * @param tipoCargaDatos the tipoCargaDatos to set
	 */
	public void setTipoCargaDatos(int tipoCargaDatos) {
		GestionExtractionETL.tipoCargaDatos = tipoCargaDatos;
	}

	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		GestionExtractionETL.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		GestionExtractionETL.fechaFin = fechaFin;
	}

	/**
	 * @return the usuarios
	 */
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
}
