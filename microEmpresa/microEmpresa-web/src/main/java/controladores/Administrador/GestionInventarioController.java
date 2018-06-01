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
import co.edu.eam.ingesoft.microempresa.negocio.beans.InventarioEJB;
import co.edu.ingesoft.microempresa.persistencia.entidades.Auditoria;
import co.edu.ingesoft.microempresa.persistencia.entidades.Empresa;
import co.edu.ingesoft.microempresa.persistencia.entidades.Inventario;
import co.edu.ingesoft.microempresa.persistencia.entidades.Rol;
import excepciones.ExcepcionNegocio;
import session.SessionController;

/**
 * @author TOSHIBAP55W
 *
 */
@ViewScoped
@Named("GestionInventarioController")
public class GestionInventarioController implements Serializable {

	@Inject
	private SessionController sesion;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;
	
	@EJB
	private EmpresaEJB empEJB;
	
	@EJB
	private InventarioEJB invenEJB;
	
	private List<Inventario> listaInventarios;
	
	private List<Empresa> listaEmpresas;
	
	private int invetarioSeleccionado;
	
	private int empresaSeleccionada;
	
	private String localizacion;

	
	@PostConstruct
	public void inicializar() {
		listarTodo();
	}
	
	public void listarTodo(){
		listaEmpresas = empEJB.listar(sesion.getBd());
		listaInventarios = invenEJB.listar(sesion.getBd());
	}
	
	public void crear(){
	
		if(localizacion.isEmpty() || empresaSeleccionada==0){
			Messages.addFlashGlobalWarn("Por favor ingrese todos los datos");
			return;
		}else{
			Empresa emp = empEJB.buscar(empresaSeleccionada, sesion.getBd());
			Inventario i = new Inventario();
			i.setEmpresa(emp);
			i.setLocalizacion(localizacion);
			invenEJB.crear(i, sesion.getBd());
			auditoria(i.getCodigo(), "Crear");
			Messages.addFlashGlobalInfo("El inventario: " +i.getCodigo()+" se ha registrado exitosamente");
			limpiar();
			listarTodo();
		}
		
	}
	
	
	public void eliminar (Inventario i){
	try{
		invenEJB.eliminar(i, sesion.getBd());
		auditoria(i.getCodigo(), "Eliminar");
		Messages.addFlashGlobalInfo("El inventario: " +i.getCodigo()+" se ha eliminado exitosamente");
		listarTodo();
	} catch (ExcepcionNegocio e) {
		Messages.addGlobalError(e.getMessage());
	} catch (Exception ex) {
		ex.printStackTrace();
	}
	}
	
	public void limpiar(){
		localizacion ="";
		empresaSeleccionada =0;
	}
	
	public void auditoria(int codigoInv ,String accion) {
		Date fecha = new Date();
		String origen = "CElular";
		String navegador = "Chrome";
		Auditoria auditoria = new Auditoria();
		auditoria.setTabla("Inventario");
		auditoria.setAccion(accion);
		auditoria.setRegistro(codigoInv);
		auditoria.setFecha(fecha);
		auditoria.setOrigen(origen);
		auditoria.setNavegador(navegador);
		auditoria.setUsuario(sesion.getUsuario());
		auditoriaEJB.crear(auditoria, sesion.getBd());
	}
	
	/**
	 * @return the listaInventarios
	 */
	public List<Inventario> getListaInventarios() {
		return listaInventarios;
	}

	/**
	 * @param listaInventarios the listaInventarios to set
	 */
	public void setListaInventarios(List<Inventario> listaInventarios) {
		this.listaInventarios = listaInventarios;
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
	 * @return the invetarioSeleccionado
	 */
	public int getInvetarioSeleccionado() {
		return invetarioSeleccionado;
	}

	/**
	 * @param invetarioSeleccionado the invetarioSeleccionado to set
	 */
	public void setInvetarioSeleccionado(int invetarioSeleccionado) {
		this.invetarioSeleccionado = invetarioSeleccionado;
	}

	/**
	 * @return the localizacion
	 */
	public String getLocalizacion() {
		return localizacion;
	}

	/**
	 * @param localizacion the localizacion to set
	 */
	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	/**
	 * @return the empresaSeleccionada
	 */
	public int getEmpresaSeleccionada() {
		return empresaSeleccionada;
	}

	/**
	 * @param empresaSeleccionada the empresaSeleccionada to set
	 */
	public void setEmpresaSeleccionada(int empresaSeleccionada) {
		this.empresaSeleccionada = empresaSeleccionada;
	}
	
	
	
}
