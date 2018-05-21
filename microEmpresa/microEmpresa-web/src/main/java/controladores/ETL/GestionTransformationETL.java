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
import co.edu.eam.ingesoft.microempresa.negocio.beansDW.DataWarehouseEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beansDW.TransformationETL;
import co.edu.ingesoft.microempresa.persistencia.datawarehouse.AuditoriaDW;
import co.edu.ingesoft.microempresa.persistencia.datawarehouse.ClienteDW;
import co.edu.ingesoft.microempresa.persistencia.datawarehouse.EmpleadoDW;
import co.edu.ingesoft.microempresa.persistencia.datawarehouse.VentaDW;
import co.edu.ingesoft.microempresa.persistencia.entidades.Auditoria;
import co.edu.ingesoft.microempresa.persistencia.entidades.Persona;
import co.edu.ingesoft.microempresa.persistencia.entidades.Venta;
import excepciones.ExcepcionFuncional;
import session.SessionController;

/**
 * @author Extraccion proceso de etl
 *
 */
@ViewScoped
@Named("transformationETL")
public class GestionTransformationETL implements Serializable{
	
	@Inject
	private SessionController sesion;
	
	@Inject
	GestionExtractionETL extractionETL;
	
	@EJB
	private TransformationETL transformationETL;
	
	@EJB
	private DataWarehouseEJB dataWarehouseEJB;
	
	@EJB
	private AuditoriaEJB auditoriaEJB; 
	
	List<Auditoria> auditorias;
	
	List<Venta> ventas;
	
	List<AuditoriaDW> auditoriasDW;
	
	List<VentaDW> ventasDW;
	
	private List<Auditoria> auditoriaETL;
	
	@PostConstruct
	public void inicializar(){
		listar();
		auditoria("Transformacion");
	}
	
	/**
	 * Lista la informacion de las tablas ya transformadas
	 */
	public void listar(){
		try{
			auditoriaETL = auditoriaEJB.listarByTabla("ETL", sesion.getBd());
			auditorias = extractionETL.getAuditorias();
			ventas = extractionETL.getVentas();
			if(auditorias == null && ventas == null){
				Messages.addFlashGlobalInfo("Vamos colega! realiza el paso 1 de ETL");
			}else{
				if(!auditorias.isEmpty()){
					auditoriasDW = transformationETL.auditoriaDW(auditorias);
				}
				if(!ventas.isEmpty()){
					ventasDW = transformationETL.ventaDW(ventas);
				}
			}
		}catch(Exception e){
			Messages.addFlashGlobalInfo("Todo va bien");
		}
	}
	
	/**
	 * Cargar datos al data warehouse
	 */
	public String cargar(){
		try{
			String msj = "Este tipo de carga de datos no existe";
			switch (extractionETL.getTipoCargaDatos()) {
			case 1:
				// Proceso de carga por acumulacion simple
				msj = "Proceso de ETL usando tipo de carga acumulacion simple finalizado correctamente";
				dataWarehouseEJB.load(auditoriasDW, ventasDW);
				auditoria("Carga: acumulacion simple");
				break;
			case 2:
				// Proceso de carga por rolling
				msj = "Proceso de ETL usando tipo de carga rolling finalizado correctamente";
				// Vaciamos los registros de las tablas del Data Warehouse
				dataWarehouseEJB.removeAllDW();
				dataWarehouseEJB.load(auditoriasDW, ventasDW);
				auditoria("Carga: rolling");
				break;
			}
			Messages.addFlashGlobalInfo(msj);
			return "/paginas/seguro/administrador/GestionEmpresa.xhtml?faces-redirect=true";
		}catch(ExcepcionFuncional ef){
			Messages.addFlashGlobalInfo(ef.getMessage());
		}catch(Exception e){
			Messages.addFlashGlobalInfo("Ups! ha pasado algo interesante");
		}
		return null;
	}

	
	/**
	 * Proceso de registro de la auditoria de etl
	 */
	public void auditoria(String accion){
		Date fecha = new Date();
		String origen = "PC";
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
	
	/**
	 * @return the auditoriasDW
	 */
	public List<AuditoriaDW> getAuditoriasDW() {
		return auditoriasDW;
	}

	/**
	 * @param auditoriasDW the auditoriasDW to set
	 */
	public void setAuditoriasDW(List<AuditoriaDW> auditoriasDW) {
		this.auditoriasDW = auditoriasDW;
	}

	/**
	 * @return the ventasDW
	 */
	public List<VentaDW> getVentasDW() {
		return ventasDW;
	}

	/**
	 * @param ventasDW the ventasDW to set
	 */
	public void setVentasDW(List<VentaDW> ventasDW) {
		this.ventasDW = ventasDW;
	}

	/**
	 * @return the auditoriaETL
	 */
	public List<Auditoria> getAuditoriaETL() {
		return auditoriaETL;
	}

	/**
	 * @param auditoriaETL the auditoriaETL to set
	 */
	public void setAuditoriaETL(List<Auditoria> auditoriaETL) {
		this.auditoriaETL = auditoriaETL;
	}
}
