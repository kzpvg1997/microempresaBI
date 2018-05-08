/**
 * 
 */
package controladores.Administrador;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.constraints.Length;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import co.edu.eam.ingesoft.microempresa.negocio.beans.AreaEmpresaEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.AuditoriaEJB;
import co.edu.ingesoft.microempresa.persistencia.entidades.AreasEmpresa;
import co.edu.ingesoft.microempresa.persistencia.entidades.Auditoria;
import co.edu.ingesoft.microempresa.persistencia.entidades.Empresa;
import excepciones.ExcepcionNegocio;
import session.SessionController;


/**
 * @author TOSHIBAP55W
 *
 */


@ViewScoped
@Named("GestionAreaEmpresaController")
public class GestionAreaEmpresaController implements Serializable{
	
	@Inject
	private SessionController sesion;

	@EJB
	private AreaEmpresaEJB areaEmpresaEJB;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;
	
	private int codigo;
	
	@Length(min=2,max=100,message="longitud entre 2 y 100 caracteres")
	private String nombre;

	@Length(min=10,max=500,message="longitud entre 10 y 500 caracteres")
	private String descripcion;

	private List<AreasEmpresa> listaAreasEmpresa;
	
	private List<Auditoria> auditorias;
	
	@PostConstruct
	public void inicializar(){
		listarAreasEmpresas();
	}

	/**
	 * Registrar en la base de datos
	 */
	public void registrar(){
		try{
			if(nombre.isEmpty()|| descripcion.isEmpty()){
				Messages.addFlashGlobalWarn("Por favor ingrese toda la informacion");
			}else{
				AreasEmpresa areaEmpresa = new AreasEmpresa();
				areaEmpresa.setNombre(nombre);
				areaEmpresa.setDescripcion(descripcion);
				Empresa empresa = new Empresa();
				empresa.setCodigo(1);
				//areaEmpresa.setEmpresa(sesion.getUsuario().getPersona().getAreaEmpresa().getEmpresa());
				areaEmpresa.setEmpresa(empresa);
				areaEmpresaEJB.crear(areaEmpresa, sesion.getBd());
				Messages.addFlashGlobalInfo("El area "+nombre+" se ha registrado exitosamente!");
				listarAreasEmpresas();
				limpiar();
				// Guardamos en la auditoria
				auditoria(areaEmpresa.getNombre(), "Crear");
			}
		}catch (ExcepcionNegocio e){
			Messages.addFlashGlobalWarn(e.getMessage());
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * Editar
	 */
	public void editar(){
		try{
			if(codigo == 0){
				Messages.addFlashGlobalWarn("Para editar por favor busque primero el Area de la empresa");
			}else{
				AreasEmpresa areaEmpresa = areaEmpresaEJB.buscar(codigo, sesion.getBd());
				if (areaEmpresa == null){
					Messages.addFlashGlobalError("No existe ningun area con codigo "+codigo);
				}else{
					if(nombre.isEmpty() || descripcion.isEmpty()){
						Messages.addFlashGlobalWarn("Por favor ingrese toda la informacion");
					}else{
						areaEmpresa.setNombre(nombre);
						areaEmpresa.setDescripcion(descripcion);
						areaEmpresaEJB.editar(areaEmpresa, sesion.getBd());
						// Guardamos en la auditoria
						auditoria(areaEmpresa.getNombre(), "Editar");
						limpiar();
						listarAreasEmpresas();
						Messages.addFlashGlobalInfo("El area con codigo: "+codigo+" se ha actualizado exitosamente!");
					}
				}
			}
		}catch (NullPointerException ex){
			Messages.addFlashGlobalWarn("Por favor ingrese todos los datos");
		}catch (NumberFormatException ex){
			Messages.addFlashGlobalWarn("Por favor ingrese solo datos numericos en la busqueda");
		}catch (ExcepcionNegocio e){
			Messages.addFlashGlobalWarn(e.getMessage());
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
	}

	/**
	 * buscar
	 */
	public void buscar(){
		try{
			if(codigo == 0){
				Messages.addFlashGlobalWarn("Por favor ingrese el codigo del Area de la empresa a buscar");
			}else{
				AreasEmpresa areaEmpresa = areaEmpresaEJB.buscar(codigo, sesion.getBd());
				if(areaEmpresa != null){
					nombre = areaEmpresa.getNombre();
					descripcion = areaEmpresa.getDescripcion();
					// Guardamos en la auditoria
					auditoria(areaEmpresa.getNombre(), "Buscar");
				}else{
					Messages.addFlashGlobalError("El area con codigo: "+codigo+" NO se encuentra registrada");
				}
			}
		}catch (NumberFormatException ex){
			Messages.addFlashGlobalWarn("Por favor ingrese solo datos numericos");
		}catch (NullPointerException e) {
			Messages.addFlashGlobalWarn("Por favor ingrese el codigo de la empresa");
		}
	}

	/**
	 * Eliminar
	 */
	public void eliminar(){
		try{
			if(codigo == 0){
				Messages.addFlashGlobalWarn("Por favor ingrese el codigo del Area de la empresa a eliminar");
			}else{
				AreasEmpresa areaEmpresa = areaEmpresaEJB.buscar(codigo, sesion.getBd());
				if(areaEmpresa != null){
					areaEmpresaEJB.eliminar(areaEmpresa, sesion.getBd());
					// Guardamos en la auditoria
					auditoria(areaEmpresa.getNombre(), "Eliminar");
					limpiar();
					listarAreasEmpresas();
					Messages.addFlashGlobalInfo("Se ha eliminado correctamente");
				}else{
					Messages.addFlashGlobalInfo("No hay ningun area con codigo: "+codigo);
				}
			}
		}catch(ExcepcionNegocio e){
			Messages.addGlobalError(e.getMessage());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * Lista con todas las areas de la empresa
	 */
	public void listarAreasEmpresas(){
		listaAreasEmpresa = areaEmpresaEJB.listarAreasEmpresa(sesion.getBd(), 1);
		auditorias = auditoriaEJB.listarByTabla("Areas_Empresa", sesion.getBd());
	}
	
	/**
	 * Limpiar campos en la interfaz
	 */
	public void limpiar(){
		nombre = "";
		descripcion = "";
	}
	
	/**
	 * Proceso de registro de la auditoria de la tabla Areas Empresa	
	 */
	public void auditoria(String nombre, String accion){
		AreasEmpresa b = areaEmpresaEJB.buscarByNombre(nombre, sesion.getBd());
		Date fecha = new Date();
		String origen = "PC";
		String navegador = "Mozilla";
		Auditoria auditoria = new Auditoria();
		auditoria.setTabla("Areas_Empresa");
		auditoria.setAccion(accion);
		auditoria.setRegistro(b.getCodigo());
		auditoria.setFecha(fecha);
		auditoria.setOrigen(origen);
		auditoria.setNavegador(navegador);
		auditoriaEJB.crear(auditoria, sesion.getBd());
	}

	/**
	 * @return the codigo
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	 * @return the listaAreasEmpresa
	 */
	public List<AreasEmpresa> getListaAreasEmpresa() {
		return listaAreasEmpresa;
	}

	/**
	 * @param listaAreasEmpresa the listaAreasEmpresa to set
	 */
	public void setListaAreasEmpresa(List<AreasEmpresa> listaAreasEmpresa) {
		this.listaAreasEmpresa = listaAreasEmpresa;
	}

	/**
	 * @return the auditorias
	 */
	public List<Auditoria> getAuditorias() {
		return auditorias;
	}

	/**
	 * @param auditorias the auditorias to set
	 */
	public void setAuditorias(List<Auditoria> auditorias) {
		this.auditorias = auditorias;
	}
}
