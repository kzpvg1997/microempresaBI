package session;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import co.edu.eam.ingesoft.microempresa.negocio.beans.AuditoriaEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.EmpresaEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.UsuarioEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beansSeguridad.SeguridadEJB;
import co.edu.ingesoft.microempresa.persistencia.entidades.Acceso;
import co.edu.ingesoft.microempresa.persistencia.entidades.Auditoria;
import co.edu.ingesoft.microempresa.persistencia.entidades.Usuario;


@Named("sessionController")
@SessionScoped
public class SessionController implements Serializable {

	private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SessionController.class);
	
	@EJB
	private UsuarioEJB usuarioEJB;
	
	@EJB
	private SeguridadEJB seguridadEJB;
	
	@EJB
	private EmpresaEJB empresaEJB;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;
		
	private String username;
	
	private String password;
	
	/**
	 * Es la base de datos que el sistema esta usando actualmente
	 */
	private int bd;
	
	/**
	 * El usuario que ha iniciado sesion en la aplicacion
	 */
	private Usuario usuario;

	/**
	 * Listado de accesos que tiene el usuario que inicio sesion
	 */
	private List<Acceso> accesos;
	
	@PostConstruct
	public void inicializar() {
		username="admin";
	}
	
	/**
	 * Iniciar sesion
	 */
	public String login() {
		try{
			if(username.isEmpty() || password.isEmpty()){
				Messages.addFlashGlobalError("Por favor, ingrese todos los campos");
			}else{
				Usuario u = usuarioEJB.buscarByUsername(username, 1);
				if(u != null){
					if(u.getPassword().equals(password)){
						if(u.isEstado()){
							usuario = u;
							accesos = seguridadEJB.listarAccesosRol(usuario.getPersona().getRol(), 1);
							Faces.setSessionAttribute("usuario", usuario);
							// Buscamos en que bd esta trabajando el sistema, buscamos en la base de datos por defecto
							// que es oracle, y despues la guardamos como variable de sesion
							bd = empresaEJB.buscar(1, 1).getBd();
							Faces.setSessionAttribute("bd", bd);
							// Guardamos en la auditoria
							auditoria(u.getCodigo(), "Ingreso al sistema");
							Messages.addFlashGlobalInfo("Bienvenido "+u.getPersona().getNombre()+" "+u.getPersona().getApellido());
							return "/template/template.xhtml?faces-redirect=true";
						}else{
							/* Contraseña incorrecta */
							// Guardamos en la auditoria
							auditoria(u.getCodigo(), "No ingreso, usuario desactivado");
							Messages.addFlashGlobalError("Joder! Estas desactivado");
						}
					}else{
						/* Contraseña incorrecta */
						// Guardamos en la auditoria
						auditoria(u.getCodigo(), "No ingreso, contraseña incorrecta");
						Messages.addFlashGlobalError("Username o contraseña incorrectos");
					}
				}else{
					// Usuario no existe
					Messages.addFlashGlobalError("Username o contraseña incorrectos");
				}
			}
		}catch (excepciones.ExcepcionFuncional f){
			Messages.addFlashGlobalWarn(f.getMessage());
		}
		return null;
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
	 * Proceso de registro de la auditoria de la tabla usuario intentos de inicio de sesion
	 */
	public void auditoria(int id, String accion){
		Date fecha = new Date();
		String origen = "PC";
		String navegador = "Chrome";
		Auditoria auditoria = new Auditoria();
		auditoria.setTabla("Usuarios login");
		auditoria.setAccion(accion);
		auditoria.setRegistro(id);
		auditoria.setFecha(fecha);
		auditoria.setOrigen(origen);
		auditoria.setNavegador(navegador);
		Usuario u = new Usuario();
		u.setCodigo(id);
		auditoria.setUsuario(u);
		if(bd == 0){
			bd = 1;
		}
		auditoriaEJB.crear(auditoria,bd);
	}
	
	public String obtenerBD (){
	String estado="";
		if(bd==1){
			estado = "Conectado a: ORACLE";
		}else if(bd==2){
			estado = "Conectado a: POSTGRESS";
		}
		return estado;
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
	 * @return the bd
	 */
	public int getBd() {
		// Si no hay ninguna bd definida, mandamos la por defecto 1 que es oracle
		if(bd == 0){
			return 1;
		}
		return bd;
	}

	/**
	 * @param bd the bd to set
	 */
	public void setBd(int bd) {
		this.bd = bd;
	}

	/**
	 * @return the logger
	 */
	public org.apache.log4j.Logger getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(org.apache.log4j.Logger logger) {
		this.logger = logger;
	}	
	
}
