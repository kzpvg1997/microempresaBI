/**
 * 
 */
package controladores.Administrador;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import co.edu.eam.ingesoft.microempresa.negocio.beans.AuditoriaEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beansDW.DataWarehouseEJB;
import co.edu.ingesoft.microempresa.persistencia.datawarehouse.AuditoriaDW;
import co.edu.ingesoft.microempresa.persistencia.datawarehouse.VentaDW;
import co.edu.ingesoft.microempresa.persistencia.entidades.Auditoria;
import session.SessionController;

/**
 * @author TOSHIBAP55W
 *
 */
@ViewScoped
@Named("ExportarVentasDW")
@ManagedBean
public class ExportarVentasDWController implements Serializable  {

	@Inject
	private SessionController sesion;
	
	@EJB
	private DataWarehouseEJB dataEJB;;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;
	
	
	private List<VentaDW> listaVentasDW;
	
	private List<AuditoriaDW> listaAuditoriasDW;
	
	/**
	 * @return the listaAuditoriasDW
	 */
	public List<AuditoriaDW> getListaAuditoriasDW() {
		return listaAuditoriasDW;
	}

	/**
	 * @param listaAuditoriasDW the listaAuditoriasDW to set
	 */
	public void setListaAuditoriasDW(List<AuditoriaDW> listaAuditoriasDW) {
		this.listaAuditoriasDW = listaAuditoriasDW;
	}

	@PostConstruct
	public void inicializar() {
	
		listar();
	}
	
	public void auditoriaExcelVentasDW() {
		Date fecha = new Date();
		String origen = "Celular";
		String navegador = "Chrome";
		Auditoria auditoria = new Auditoria();
		auditoria.setTabla("VentasDW");
		auditoria.setAccion("Exportacion EXCEL");
		auditoria.setRegistro(listaVentasDW.get(0).getCodigo());
		auditoria.setFecha(fecha);
		auditoria.setOrigen(origen);
		auditoria.setNavegador(navegador);
		auditoria.setUsuario(sesion.getUsuario());
		auditoriaEJB.crear(auditoria, sesion.getBd());
	}
	
	public void auditoriaCSVVentasDW() {
		Date fecha = new Date();
		String origen = "Celular";
		String navegador = "Chrome";
		Auditoria auditoria = new Auditoria();
		auditoria.setTabla("VentasDW");
		auditoria.setAccion("Exportacion CSV");
		auditoria.setRegistro(listaVentasDW.get(0).getCodigo());
		auditoria.setFecha(fecha);
		auditoria.setOrigen(origen);
		auditoria.setNavegador(navegador);
		auditoria.setUsuario(sesion.getUsuario());
		auditoriaEJB.crear(auditoria, sesion.getBd());
	}
	
	public void auditoriaExcelAuditoriasDW() {
		Date fecha = new Date();
		String origen = "PC";
		String navegador = "Mozilla";
		Auditoria auditoria = new Auditoria();
		auditoria.setTabla("AuditoriasDW");
		auditoria.setAccion("Exportacion EXCEL");
		auditoria.setRegistro(listaAuditoriasDW.get(0).getId());
		auditoria.setFecha(fecha);
		auditoria.setOrigen(origen);
		auditoria.setNavegador(navegador);
		auditoria.setUsuario(sesion.getUsuario());
		auditoriaEJB.crear(auditoria, sesion.getBd());
	}
	
	public void auditoriaCSVAuditoriasDW() {
		Date fecha = new Date();
		String origen = "PC";
		String navegador = "Mozilla";
		Auditoria auditoria = new Auditoria();
		auditoria.setTabla("AuditoriaDW");
		auditoria.setAccion("Exportacion CSV");
		auditoria.setRegistro(listaAuditoriasDW.get(0).getId());
		auditoria.setFecha(fecha);
		auditoria.setOrigen(origen);
		auditoria.setNavegador(navegador);
		auditoria.setUsuario(sesion.getUsuario());
		auditoriaEJB.crear(auditoria, sesion.getBd());
	}
	
	public void listar() {
		
		listaVentasDW = dataEJB.listarVentasDW();
		listaAuditoriasDW =  dataEJB.listarAruditoriasDW();
	}

	/**
	 * @return the listaVentasDW
	 */
	public List<VentaDW> getListaVentasDW() {
		return listaVentasDW;
	}

	/**
	 * @param listaVentasDW the listaVentasDW to set
	 */
	public void setListaVentasDW(List<VentaDW> listaVentasDW) {
		this.listaVentasDW = listaVentasDW;
	}

	
	
}


