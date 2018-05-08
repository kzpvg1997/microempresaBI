/**
 * 
 */
package controladores.Administrador;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import co.edu.eam.ingesoft.microempresa.negocio.beans.AccesoEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.AccesosRolEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.AuditoriaEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.RolEJB;
import co.edu.ingesoft.microempresa.persistencia.entidades.Acceso;
import co.edu.ingesoft.microempresa.persistencia.entidades.Auditoria;
import co.edu.ingesoft.microempresa.persistencia.entidades.Rol;
import co.edu.ingesoft.microempresa.persistencia.entidades.RolAcceso;
import excepciones.ExcepcionNegocio;
import session.SessionController;


/**
 * @author Monica Sepulveda & Kevin Zapata & Carlos Martinez
 * clase encargada de controlar la interfaz de gestionAccesosRol
 */


@ViewScoped
@Named("GestionAccesosRolController")
public class GestionAccesosRolController implements Serializable{
	
	@Inject
	private SessionController sesion;
	
	@EJB
	private AccesosRolEJB accesosRolEJB;

	@EJB
	private RolEJB rolEJB;
	
	@EJB
	private AccesoEJB accesoEJB;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;
	
	private List<Rol> roles;
	
	private Rol rol;
	
	private List<Acceso> accesos;
	
	private Acceso acceso;
	
	private List<RolAcceso> rolAccesos;
	
	private List<Auditoria> auditorias;
	
	@PostConstruct
	public void inicializar(){
		listar();
	}
	
	/**
	 * Otorga el acceso a un rol
	 */
	public void otorgar(){
		try{
			if(rol != null && acceso != null){
				RolAcceso rolAcceso = new RolAcceso(rol, acceso);
				accesosRolEJB.crear(rolAcceso, sesion.getBd());
				Messages.addFlashGlobalInfo("Se ha asignado el acceso "+acceso.getNombreAcceso()+" a el rol "+rol.getNombre());
				// Guardamos en la auditoria
				auditoria(rolAcceso.getRol().getCodigo(), "Otorgar permiso");
				accesosByRol();
			}else{
				Messages.addFlashGlobalInfo("Seleccione el rol y el acceso");
			}
		}catch (ExcepcionNegocio e){
			Messages.addFlashGlobalWarn(e.getMessage());
		}catch (Exception ex){
			ex.printStackTrace();
		}
			
	}
	
	/**
	 * Quita el permiso a un rol
	 */
	public void quitar(RolAcceso rolAcceso){
		try{
			accesosRolEJB.eliminar(rolAcceso, sesion.getBd());
			Messages.addFlashGlobalInfo("Se ha eliminado el acceso del rol correctamente");
			accesosByRol();
			auditoria(rolAcceso.getRol().getCodigo(), "Quitar permiso");
			accesosByRol();
		}catch (ExcepcionNegocio e){
			Messages.addFlashGlobalWarn(e.getMessage());
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * Proceso de registro de la auditoria de la tabla AccesosRol	
	 */
	public void auditoria(int id, String accion){
		Date fecha = new Date();
		String origen = "PC";
		String navegador = "Chrome";
		Auditoria auditoria = new Auditoria();
		auditoria.setTabla("RolAccesos");
		auditoria.setAccion(accion);
		auditoria.setRegistro(id);
		auditoria.setFecha(fecha);
		auditoria.setOrigen(origen);
		auditoria.setNavegador(navegador);
		auditoriaEJB.crear(auditoria, sesion.getBd());
	}
	
	/**
	 * Lista de accesosRol de un determinado rol
	 */
	public void accesosByRol(){
		if(rol != null){
			rolAccesos = accesosRolEJB.listarAccesosRolByRol(rol, sesion.getBd());
		}
	}
	
	/**
	 * Listar en los combobox
	 */
	public void listar(){
		roles = rolEJB.listar(sesion.getBd());
		accesos = accesoEJB.listar(sesion.getBd());
		rolAccesos = accesosRolEJB.listarAccesosRolByRol(roles.get(0), sesion.getBd());
		auditorias = auditoriaEJB.listarByTabla("RolAccesos", sesion.getBd());
	}

	/**
	 * @return the roles
	 */
	public List<Rol> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	/**
	 * @return the accesos
	 */
	public List<Acceso> getAccesos() {
		return accesos;
	}

	/**
	 * @param accesos the accesos to set
	 */
	public void setAccesos(List<Acceso> accesos) {
		this.accesos = accesos;
	}

	/**
	 * @return the rol
	 */
	public Rol getRol() {
		return rol;
	}

	/**
	 * @param rol the rol to set
	 */
	public void setRol(Rol rol) {
		this.rol = rol;
	}

	/**
	 * @return the acceso
	 */
	public Acceso getAcceso() {
		return acceso;
	}

	/**
	 * @param acceso the acceso to set
	 */
	public void setAcceso(Acceso acceso) {
		this.acceso = acceso;
	}

	/**
	 * @return the rolAccesos
	 */
	public List<RolAcceso> getRolAccesos() {
		return rolAccesos;
	}

	/**
	 * @param rolAccesos the rolAccesos to set
	 */
	public void setRolAccesos(List<RolAcceso> rolAccesos) {
		this.rolAccesos = rolAccesos;
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
