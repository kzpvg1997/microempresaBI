package controladores.ETL;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

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
	
	List<Auditoria> auditorias;
	
	List<Venta> ventas;
	
	List<AuditoriaDW> auditoriasDW;
	
	List<VentaDW> ventasDW;
	
	@PostConstruct
	public void inicializar(){
		listar();
	}
	
	/**
	 * Lista la informacion de las tablas ya transformadas
	 */
	public void listar(){
		try{
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
			dataWarehouseEJB.load(auditoriasDW, ventasDW);
			Messages.addFlashGlobalInfo("Proceso de ETL finalizado correctamente");
			return "/paginas/seguro/administrador/GestionEmpresa.xhtml?faces-redirect=true";
		}catch(ExcepcionFuncional ef){
			Messages.addFlashGlobalInfo(ef.getMessage());
		}catch(Exception e){
			Messages.addFlashGlobalInfo("Ups! esto no deberia haber pasado");
		}
		return null;
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
	
}
