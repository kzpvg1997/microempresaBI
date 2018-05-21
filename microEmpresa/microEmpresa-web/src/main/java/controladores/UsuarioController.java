/**
 * 
 */
package controladores;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import javax.enterprise.context.SessionScoped;

import co.edu.eam.ingesoft.microempresa.negocio.beans.AuditoriaEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.UsuarioEJB;
import co.edu.ingesoft.microempresa.persistencia.entidades.Auditoria;
import co.edu.ingesoft.microempresa.persistencia.entidades.Usuario;
import excepciones.ExcepcionNegocio;
import session.SessionController;

/**
 * @author Carlos Martinez & Kevin Zapata & Monica Sepulveda
 *
 */
@Named("usuarioController")
@ViewScoped
public class UsuarioController implements Serializable{

	@Inject
	private SessionController sesion;
	
	@EJB
	private UsuarioEJB usuarioEJB;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;
	
	private String username;
	
	private String password;
	
	private Usuario usuario;
	
	private List<Usuario> usuarios;
	
	private List<Auditoria> auditorias;
	
	@PostConstruct
	public void inicializar(){
		listar();
	}
	
	/**
	 * Llenar las listar
	 */
	public void listar(){
		usuarios = usuarioEJB.listar(sesion.getBd());
		auditorias = auditoriaEJB.listarByTabla("UsuariosEstado", sesion.getBd());
	}
	
	/**
	 * Cambia el estado de un usuario
	 * True: activo
	 * False: Inactivo 
	 */
	public void cambiarEstado(Usuario u, boolean elestado){
		try{
			u.setEstado(elestado);
			usuarioEJB.editar(u, sesion.getBd());
			String estado = "Desactivar";
			if(elestado){
				estado = "Activar";
			}
			auditoria(u.getCodigo(), estado);
			Messages.addFlashGlobalInfo("Operacion de "+estado+" usuario exitosa!");
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
		auditoria.setTabla("UsuariosEstado");
		auditoria.setAccion(accion);
		auditoria.setRegistro(id);
		auditoria.setFecha(fecha);
		auditoria.setOrigen(origen);
		auditoria.setNavegador(navegador);
		auditoria.setUsuario(sesion.getUsuario());
		auditoriaEJB.crear(auditoria, sesion.getBd());
	}
	
	/**
	 * Iniciar sesion
	 */
	public void login() {
		if(username.isEmpty() || password.isEmpty()){
			Messages.addFlashGlobalError("Por favor, ingrese todos los campos");
		}else{
			Usuario u = usuarioEJB.buscarByUsername(username, 1);
			if(u != null){
				if(u.getPassword().equals(password)){
					usuario = u;
					Faces.setSessionAttribute("usuario", usuario);
					Messages.addFlashGlobalInfo("Ha iniciado sesion correctamente");
				}else{
					// Contraseña incorrecta
					Messages.addFlashGlobalError("Username o contraseña incorrectos");
				}
			}else{
				// Usuario no existe
				Messages.addFlashGlobalError("Username o contraseña incorrectos");
			}
		}
	}

	/**
	 * Cierra la sesion en la aplicacion
	 * @return la pagina de login
	 */
	public String cerrarSesion() {
		usuario = null;
		HttpSession sesion;
		sesion = (HttpSession) Faces.getSession();
		sesion.invalidate();
		return "/paginas/publico/login.xhtml?faces-redirect=true";
	}

	/**
	 * Determina si existe la sesion del usuario
	 * @return
	 */
	public boolean isSesion() {
		return usuario != null;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
