/**
 * 
 */
package controladores.Administrador;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.validator.constraints.Length;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import co.edu.eam.ingesoft.microempresa.negocio.beans.AuditoriaEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.RolEJB;
import co.edu.ingesoft.microempresa.persistencia.entidades.Auditoria;
import co.edu.ingesoft.microempresa.persistencia.entidades.Rol;
import excepciones.ExcepcionNegocio;
import session.SessionController;

/**
 * @author Carlos Martinez
 *
 */

@ViewScoped
@Named("GestionRolesController")
public class GestionRolesController implements Serializable {

	@Inject
	private SessionController sesion;

	@EJB
	private RolEJB rolEJB;

	@EJB
	private AuditoriaEJB auditoriaEJB;

	private int codigo;

	@Length(min = 2, max = 100, message = "longitud entre 2 y 100 caracteres")
	private String nombre;

	@Length(min = 10, max = 500, message = "longitud entre 10 y 500 caracteres")
	private String descripcion;

	private List<Rol> roles;

	private List<Auditoria> auditorias;

	@PostConstruct
	public void inicializar() {
		listar();
	}

	/**
	 * Registrar en la base de datos
	 */
	public void registrar() {
		try {
			if (nombre.isEmpty() || descripcion.isEmpty()) {
				Messages.addFlashGlobalWarn("Por favor ingrese toda la informacion");
			} else {
				Rol rol = new Rol();
				rol.setNombre(nombre);
				rol.setDescripcion(descripcion);
				rolEJB.crear(rol, sesion.getBd());
				Messages.addFlashGlobalInfo("El rol " + nombre + " se ha registrado exitosamente!");
				listar();
				limpiar();
				// Guardamos en la auditoria
				auditoria(rol.getNombre(), "Crear");
			}
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalWarn(e.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Editar
	 */
	public void editar() {
		try {
			if (codigo == 0) {
				Messages.addFlashGlobalWarn("Para editar por favor busque primero el rol de la empresa");
			} else {
				Rol rol = rolEJB.buscar(codigo, sesion.getBd());
				if (rol == null) {
					Messages.addFlashGlobalError("No existe ningun rol con codigo " + codigo);
				} else {
					if (nombre.isEmpty() || descripcion.isEmpty()) {
						Messages.addFlashGlobalWarn("Por favor ingrese toda la informacion");
					} else {
						rol.setNombre(nombre);
						rol.setDescripcion(descripcion);
						rolEJB.editar(rol, sesion.getBd());
						// Guardamos en la auditoria
						auditoria(rol.getNombre(), "Editar");
						limpiar();
						listar();
						Messages.addFlashGlobalInfo(
								"El rol con codigo: " + codigo + " se ha actualizado exitosamente!");
					}
				}
			}
		} catch (NullPointerException ex) {
			Messages.addFlashGlobalWarn("Por favor ingrese todos los datos");
		} catch (NumberFormatException ex) {
			Messages.addFlashGlobalWarn("Por favor ingrese solo datos numericos en la busqueda");
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalWarn(e.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * buscar
	 */
	public void buscar() {
		try {
			if (codigo == 0) {
				Messages.addFlashGlobalWarn("Por favor ingrese el codigo del rol de la empresa a buscar");
			} else {
				Rol rol = rolEJB.buscar(codigo, sesion.getBd());
				if (rol != null) {
					nombre = rol.getNombre();
					descripcion = rol.getDescripcion();
					// Guardamos en la auditoria
					auditoria(rol.getNombre(), "Buscar");
				} else {
					Messages.addFlashGlobalError("No existe ningun rol con codigo: " + codigo);
				}
			}
		} catch (NumberFormatException ex) {
			Messages.addFlashGlobalWarn("Por favor ingrese solo datos numericos");
		} catch (NullPointerException e) {
			Messages.addFlashGlobalWarn("Por favor ingrese el codigo del rol");
		}
	}

	/**
	 * Eliminar
	 */
	public void eliminar(Rol rol) {
		try {
			// Guardamos en la auditoria
			auditoria(rol.getNombre(), "Eliminar");
			// Eliminamos el Rol seleccionado
			rolEJB.eliminar(rol, sesion.getBd());
			limpiar();
			listar();
			Messages.addFlashGlobalInfo("El rol Se ha eliminado correctamente");
		} catch (ExcepcionNegocio e) {
			Messages.addGlobalError(e.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Lista con todas las areas de la empresa
	 */
	public void listar() {
		roles = rolEJB.listar(sesion.getBd());
		auditorias = auditoriaEJB.listarByTabla("Roles", sesion.getBd());
	}

	/**
	 * Limpiar campos en la interfaz
	 */
	public void limpiar() {
		nombre = "";
		descripcion = "";
	}

	/**
	 * Proceso de registro de la auditoria de la tabla roles
	 */
	public void auditoria(String nombre, String accion) {
		Rol r = rolEJB.buscarByNombre(nombre, sesion.getBd());
		Date fecha = new Date();
		String origen = "PC";
		String navegador = "Mozilla";
		Auditoria auditoria = new Auditoria();
		auditoria.setTabla("Roles");
		auditoria.setAccion(accion);
		auditoria.setRegistro(r.getCodigo());
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
	 * @param codigo
	 *            the codigo to set
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
	 * @param nombre
	 *            the nombre to set
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
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the auditorias
	 */
	public List<Auditoria> getAuditorias() {
		return auditorias;
	}

	/**
	 * @param auditorias
	 *            the auditorias to set
	 */
	public void setAuditorias(List<Auditoria> auditorias) {
		this.auditorias = auditorias;
	}

	/**
	 * @return the roles
	 */
	public List<Rol> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}
}
