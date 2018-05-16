package controladores.ETL;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import co.edu.eam.ingesoft.microempresa.negocio.beansDW.ExtractionETL;
import co.edu.ingesoft.microempresa.persistencia.entidades.Auditoria;
import co.edu.ingesoft.microempresa.persistencia.entidades.Persona;
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
	
	List<Auditoria> auditorias;
	
	List<Venta> ventas;
	
	@PostConstruct
	public void inicializar(){
		listar();
	}
	
	/**
	 * Listar todos los registros en las diferentes tablas de la interfaz
	 */
	public void listar(){
		try{
			auditorias = (List<Auditoria>)(Object)extractionETL.extraerAll(Auditoria.todo);
			ventas = (List<Venta>)(Object)extractionETL.extraerAll(Venta.todo);
		}catch (excepciones.ExcepcionFuncional e) {
			Messages.addFlashGlobalWarn(e.getMessage());
		}
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
	
}
