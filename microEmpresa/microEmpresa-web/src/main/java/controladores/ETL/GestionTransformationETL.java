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
		auditoriasDW = transformationETL.auditoriaDW(auditorias);
		ventasDW = transformationETL.ventaDW(ventas);
	}
	
	/**
	 * Cargar datos al data warehouse
	 */
	public void cargar(){
		Messages.addFlashGlobalInfo("Trabajando en ello tio");
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
