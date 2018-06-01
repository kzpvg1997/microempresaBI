/**
 * 
 */
package controladores.Administrador;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.component.output.Url;
import org.omnifaces.util.Messages;
import org.primefaces.component.graphicimage.GraphicImage;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.edu.eam.ingesoft.microempresa.negocio.beans.AuditoriaEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beansDW.DataWarehouseEJB;
import co.edu.ingesoft.microempresa.persistencia.entidades.ReporteDW;
import excepciones.ExcepcionNegocio;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import session.SessionController;

/**
 * @author TOSHIBAP55W
 *
 */
@ViewScoped
@Named("ReportesDWController")
public class ReportesDWController implements Serializable{

	@Inject
	private SessionController sesion;

	@EJB
	private AuditoriaEJB auditoriaEJB;
	
	@EJB
	private DataWarehouseEJB dataEJB;
	
	private List<ReporteDW> listaReportes;
	
	private String nombreReporte;
	
	private String descripcion;
	
	private String imagen; 
	
	
	
	public static String guardarBlobEnFicheroTemporal(byte[] bytes, String nomnreArchivo){
		
	String ubicacionImagen=null;	
	ServletContext servletContext = (ServletContext) FacesContext
			.getCurrentInstance().getExternalContext().getContext();
	String path = servletContext.getRealPath("")+File.separatorChar
			+ "img"+File.separatorChar+"tmp"+File.separatorChar+nomnreArchivo;
	
	File f = null;
	InputStream in = null;
	
	try{
		f = new File(path);
		in = new ByteArrayInputStream(bytes);
		FileOutputStream out = new FileOutputStream(f.getAbsolutePath());
		
		int c=0;
		while ((c=in.read())>=0) {
			out.write(c);	
		}
		out.flush();
		out.close();
		ubicacionImagen= "/img/tmp/"+nomnreArchivo;
		
	}catch (Exception e) {
		Messages.addFlashGlobalError("No se ha podidocargar la imagen");
	}
	return ubicacionImagen;
	}
	
	public void subirReporte(FileUploadEvent event){
		try{
		
			ReporteDW rep = new ReporteDW();
			rep.setNombreReporte(nombreReporte);
			rep.setDescripcion(descripcion);
			Date d = new Date();
			rep.setFechaReporte(d);
			rep.setImagen(event.getFile().getContents());
			//dataEJB.generarReporteDW(rep);
			imagen = guardarBlobEnFicheroTemporal(rep.getImagen(), event.getFile().getFileName());
			Messages.addFlashGlobalInfo("El Reporte se ha cargado exitosamente!");
			
		}catch (Exception e){
			Messages.addFlashGlobalError("problemas al subir reporte");
		}
	}

	public void eliminar(ReporteDW reporte){
		
	}
	
	public void listar(){
		listaReportes = dataEJB.listarReportesDW(sesion.getUsuario().getCodigo());
	}

	/**
	 * @return the nombreReporte
	 */
	public String getNombreReporte() {
		return nombreReporte;
	}

	/**
	 * @param nombreReporte the nombreReporte to set
	 */
	public void setNombreReporte(String nombreReporte) {
		this.nombreReporte = nombreReporte;
	}

	/**
	 * @return the listaReportes
	 */
	public List<ReporteDW> getListaReportes() {
		return listaReportes;
	}

	/**
	 * @param listaReportes the listaReportes to set
	 */
	public void setListaReportes(List<ReporteDW> listaReportes) {
		this.listaReportes = listaReportes;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	/**
	 * @return the imagen
	 */
	public String getImagen() {
		return imagen;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	
}
