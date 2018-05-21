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

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import co.edu.eam.ingesoft.microempresa.negocio.beans.AuditoriaEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.EmpresaEJB;
import co.edu.ingesoft.microempresa.persistencia.entidades.Auditoria;
import co.edu.ingesoft.microempresa.persistencia.entidades.Empresa;
import co.edu.ingesoft.microempresa.persistencia.entidades.Rol;
import session.SessionController;


/**
 * @author TOSHIBAP55W
 *
 */


@ViewScoped
@Named("gestionEmpresaController")
public class GestionEmpresaController implements Serializable{

	@Inject
	private SessionController sesion;
	
	@EJB
	private EmpresaEJB empEjb;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;
	
	private int codigo;
	
	private String nombre;

	private String telefono;
	
	private String direccion;

	private int bd;
	
	private List<Empresa> listaEmpresas;
	
	private List<Auditoria> auditorias;
	
	@PostConstruct
	public void inicializar(){
		codigo = 1;
		buscarEmpresa();
		listarEmpresas();
	}

		//process="@this :tablaDatosEmpresa:panelTablaEmp:tablaEmp"
	public void registrarEmpresa(){
			
		try{
		if(nombre.isEmpty()||direccion.isEmpty()||telefono.isEmpty()||bd==0){
			
			Messages.addFlashGlobalWarn("Por favor ingrese toda la informacion");
		}else{
			
			Empresa empresa = new Empresa(nombre, telefono, direccion, bd);
			empEjb.crear(empresa, bd);
			Messages.addFlashGlobalInfo("La empresa "+nombre+" se ha registrado exitosamente!");
			listarEmpresas();
			limpiar();
		}
		}catch (NullPointerException ex){
			Messages.addFlashGlobalWarn("Por favor ingrese toda la informacion");
		}
	}
	
	public String editarEmpresa(){
		
		try{
			if(codigo==0){
				Messages.addFlashGlobalWarn("Para editar por favor busque la empresa");
				return null;
			}else{
				Empresa em =new Empresa() ;
				em.setBd(bd);
				em.setCodigo(codigo);
				em.setNombre(nombre);
				em.setDireccion(direccion);
				em.setTelefono(telefono);
				Empresa emB = empEjb.buscar(em.getCodigo(), 1);
				if (emB==null){
					Messages.addFlashGlobalError("la empresa con codigo: "+codigo+" NO se encuentra registrada");
				}else{
				if(nombre.isEmpty()||direccion.isEmpty()||telefono.isEmpty()||bd == 0){
					Messages.addFlashGlobalWarn("Por favor ingrese toda la informacion");
				}else{	
					empEjb.editar(em, 1);
					limpiar();
					listarEmpresas();
					Messages.addFlashGlobalInfo("la empresa con codigo: "+codigo+" ha sido editada exitosamente!");
					if(emB.getBd() != em.getBd()){
						
						/* Guardamos en la auditoria */
						String accion = "Oracle";
						if(em.getBd() == 2){
							accion = "Postgress";
						}
						auditoria(em.getCodigo(), accion);
						// Destruimos la sesion
						return sesion.cerrarSesion();
					}
					
				}
				}
				
			}
		}catch (NullPointerException ex){
			Messages.addFlashGlobalWarn("Por favor ingrese todos los datos");
		}catch (NumberFormatException ex){
			Messages.addFlashGlobalWarn("Por favor ingrese solo datos numericos en la busqueda");
		}
		return null;
		
	}


	public void buscarEmpresa(){
		try{
			if(codigo==0){
				Messages.addFlashGlobalWarn("Por favor ingrese el codigo de la empresa");
			}else{
				//Aca se manejara la bd global para buscar solo a las empresas
				//que apuntan a una bd
				Empresa em = empEjb.buscar(codigo, 1);
				if(em!=null){
					nombre = em.getNombre();
					direccion =  em.getDireccion();
					telefono = em.getTelefono();
					bd = em.getBd();
				}else{
					Messages.addFlashGlobalError("la empresa con codigo: "+codigo+" NO se encuentra registrada");
				}
			}
			
		}catch (NumberFormatException ex){
			Messages.addFlashGlobalWarn("Por favor ingrese solo datos numericos");
		}catch (NullPointerException e) {
			Messages.addFlashGlobalWarn("Por favor ingrese el codigo de la empresa");
		}
	}

	public void listarEmpresas(){
		//Aca se manejara con variable global 
		//listando las empresas con las cuales el administrador se encuentra registrado
		/**
		 * @param 1 Oracle
		 */
		listaEmpresas = empEjb.listar(1);
		auditorias = auditoriaEJB.listarByTabla("Cambio BD", sesion.getBd());
	}
	
	
	/**
	 * Proceso de registro de la auditoria de la tabla empresa cambios de bd
	 */
	public void auditoria(int registro, String accion){
		Date fecha = new Date();
		String origen = "PC";
		String navegador = "Mozilla";
		Auditoria auditoria = new Auditoria();
		auditoria.setTabla("Cambio BD");
		auditoria.setAccion(accion);
		auditoria.setRegistro(registro);
		auditoria.setFecha(fecha);
		auditoria.setOrigen(origen);
		auditoria.setNavegador(navegador);
		auditoria.setUsuario(sesion.getUsuario());
		auditoriaEJB.crear(auditoria, sesion.getBd());
	}
	
	public void limpiar(){
		
		nombre ="";
		telefono="";
		direccion="";
		bd=0;
				
	}
	
	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}


	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
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
	 * @return the bd
	 */
	public int getBd() {
		return bd;
	}




	/**
	 * @param bd the bd to set
	 */
	public void setBd(int bd) {
		this.bd = bd;
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
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}




	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	/**
	 * @return the listaEmpresas
	 */
	public List<Empresa> getListaEmpresas() {
		return listaEmpresas;
	}

	/**
	 * @param listaEmpresas the listaEmpresas to set
	 */
	public void setListaEmpresas(List<Empresa> listaEmpresas) {
		this.listaEmpresas = listaEmpresas;
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
