/**
 * 
 */
package controladores.usuario;

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
import co.edu.eam.ingesoft.microempresa.negocio.beans.TipoProductoEJB;
import co.edu.ingesoft.microempresa.persistencia.entidades.AreasEmpresa;
import co.edu.ingesoft.microempresa.persistencia.entidades.Auditoria;
import co.edu.ingesoft.microempresa.persistencia.entidades.Empresa;
import co.edu.ingesoft.microempresa.persistencia.entidades.TipoProducto;
import excepciones.ExcepcionNegocio;
import session.SessionController;


/**
 * @author TOSHIBAP55W
 *
 */


@ViewScoped
@Named("TipoProductoController")
public class GestionTipoProductoController implements Serializable{
	
	@Inject
	private SessionController sesion;

	@EJB
	private TipoProductoEJB tipoProductoEJB;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;
	
	private int codigo;
	
	@Length(min=2,max=100,message="longitud entre 2 y 100 caracteres")
	private String nombre;

	@Length(min=10,max=500,message="longitud entre 10 y 500 caracteres")
	private String descripcion;

	private List<TipoProducto> lista;
	
	private List<Auditoria> auditorias;
	
	@PostConstruct
	public void inicializar(){
		listar();
	}

	/**
	 * Registrar en la base de datos
	 */
	public void registrar(){
		try{
			if(nombre.isEmpty()|| descripcion.isEmpty()){
				Messages.addFlashGlobalWarn("Por favor ingrese toda la informacion");
			}else{
				TipoProducto tp = new TipoProducto();
				tp.setNombre(nombre);
				tp.setDescripcion(descripcion);
				tipoProductoEJB.crear(tp, sesion.getBd());
				Messages.addFlashGlobalInfo("El tipo producto: "+nombre+" se ha registrado exitosamente!");
				listar();
				limpiar();
				// Guardamos en la auditoria
				auditoria(tp.getNombre(), "Crear");
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
				Messages.addFlashGlobalWarn("Para editar por favor busque primero el tipo producto");
			}else{
				TipoProducto tp = tipoProductoEJB.buscar(codigo, sesion.getBd());
				if (tp == null){
					Messages.addFlashGlobalError("No existe ningun tipo producto con codigo "+codigo);
				}else{
					if(nombre.isEmpty() || descripcion.isEmpty()){
						Messages.addFlashGlobalWarn("Por favor ingrese toda la informacion");
					}else{
						tp.setNombre(nombre);
						tp.setDescripcion(descripcion);
						tipoProductoEJB.editar(tp, sesion.getBd());
						// Guardamos en la auditoria
						auditoria(tp.getNombre(), "Editar");
						limpiar();
						listar();
						Messages.addFlashGlobalInfo("El tipo producto con codigo: "+codigo+" se ha actualizado exitosamente!");
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
				Messages.addFlashGlobalWarn("Por favor ingrese el codigo del tipo producto a buscar");
			}else{
				TipoProducto tp = tipoProductoEJB.buscar(codigo, sesion.getBd());
				if(tp != null){
					nombre = tp.getNombre();
					descripcion = tp.getDescripcion();
					// Guardamos en la auditoria
					auditoria(tp.getNombre(), "Buscar");
				}else{
					Messages.addFlashGlobalError("El tipo producto con codigo: "+codigo+" NO se encuentra registrado");
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
				Messages.addFlashGlobalWarn("Por favor ingrese el codigo del tipo producto a eliminar");
			}else{
				TipoProducto tp = tipoProductoEJB.buscar(codigo, sesion.getBd());
				if(tp != null){
					tipoProductoEJB.eliminar(tp, sesion.getBd());
					// Guardamos en la auditoria
					auditoria(tp.getNombre(), "Eliminar");
					limpiar();
					listar();
					Messages.addFlashGlobalInfo("Se ha eliminado correctamente");
				}else{
					Messages.addFlashGlobalInfo("No hay ningun tipo producto con codigo: "+codigo);
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
	public void listar(){
		lista= tipoProductoEJB.listar(sesion.getBd());
		auditorias = auditoriaEJB.listarByTabla("tipo_Producto", sesion.getBd());
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
		TipoProducto b = tipoProductoEJB.buscarByNombre(nombre, sesion.getBd());
		Date fecha = new Date();
		String origen = "PC";
		String navegador = "Mozilla";
		Auditoria auditoria = new Auditoria();
		auditoria.setTabla("tipo_Producto");
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
	 * @return the lista
	 */
	public List<TipoProducto> getLista() {
		return lista;
	}

	/**
	 * @param lista the lista to set
	 */
	public void setLista(List<TipoProducto> lista) {
		this.lista = lista;
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
