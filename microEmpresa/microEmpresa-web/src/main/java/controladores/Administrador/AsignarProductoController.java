/**
 * 
 */
package controladores.Administrador;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.FeatureContext;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;
import org.primefaces.component.api.UIData;

import co.edu.eam.ingesoft.microempresa.negocio.beans.AuditoriaEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.InventarioEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.ProductoEJB;
import co.edu.eam.ingesoft.microempresa.negocio.persistencia.Persistencia;
import co.edu.ingesoft.microempresa.persistencia.entidades.Auditoria;
import co.edu.ingesoft.microempresa.persistencia.entidades.Inventario;
import co.edu.ingesoft.microempresa.persistencia.entidades.InventarioProducto;
import co.edu.ingesoft.microempresa.persistencia.entidades.Producto;
import excepciones.ExcepcionNegocio;
import session.SessionController;

/**
 * @author TOSHIBAP55W
 *
 */
@ViewScoped
@Named("AsignarProductoController")
public class AsignarProductoController implements Serializable{

	@Inject
	private SessionController sesion;
	
	@EJB
	private ProductoEJB productoEJB;
	
	@EJB
	private Persistencia persistenciaEJB;
	
	@EJB
	private InventarioEJB inventarioEJB;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;
	
	private int cantidad;
	
	private int codigoProducto;
	
	private int codigoInventario;
	
	private List<Inventario> listaInventarios;
	
	private List<Producto> listaProductos;
	
	private List<InventarioProducto> productosXInventarios;
	
	
	private int inventarioSeleccionado;
	
	private int productoSeleccionado;
	
	private UIData tabla;
	
	@PostConstruct
	public void inicializar() {
		listarTodo();
	}
	
	public void listarTodo(){
		try{
		listaInventarios = inventarioEJB.listar(sesion.getBd());
		listaProductos = productoEJB.listar(sesion.getBd());
		productosXInventarios=productoEJB.productosXInventario(listaInventarios.get(0).getCodigo(), sesion.getBd());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void asignarProducto(Producto p){
		//Messages.addFlashGlobalInfo(""+tabla.getRowIndex());
		try{
			//if(cantidad > 0){
				InventarioProducto ip = productoEJB.buscarInventarioProducto(p.getCodigo(),inventarioSeleccionado, sesion.getBd());
				if(ip==null){
					
					Inventario i = inventarioEJB.buscar(inventarioSeleccionado, sesion.getBd());
					InventarioProducto inv = new InventarioProducto();
					inv.setInventario(i);
					inv.setProducto(p);
					inv.setPersonaEmpleado(sesion.getUsuario().getPersona());
					Date d = new Date();
					inv.setFechaIngreso(d);
					inv.setCantidad(cantidad);
					persistenciaEJB.asignarProductoAInventario(inv);
					auditoria(inv.getProducto().getNombre(), "Aignado a inventario: "+inv.getInventario().getCodigo());
					
					Messages.addFlashGlobalInfo("El producto:"+inv.getProducto().getNombre()+" Se ha agregado correctamente");
					productoByInventario();
					
				}else{
					Messages.addFlashGlobalError("Este producto ya se encuentra registrado en el inventario");
				}
//			}else{
//				Messages.addFlashGlobalInfo("Por favor, ingrese una cantidad");
//			}
		} catch (ExcepcionNegocio e) {
			Messages.addGlobalError(e.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void quitarProducto(InventarioProducto ip){
		try{
			persistenciaEJB.eliminarProductoInventario(ip);
			auditoria(ip.getProducto().getNombre(), "retirado de inventario: "+ip.getInventario().getCodigo());
			Messages.addFlashGlobalInfo("El producto se ha retirado exitosamente");
			productoByInventario();
		} catch (ExcepcionNegocio e) {
			Messages.addGlobalError(e.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void productoByInventario(){
		Inventario in = inventarioEJB.buscar(inventarioSeleccionado, sesion.getBd());
		if(in != null){
			productosXInventarios = productoEJB.productosXInventario(in.getCodigo(), sesion.getBd());
		}
	}
	
	public void auditoria(String nombreProducto, String accion) {
		Producto p = productoEJB.buscarByNombre(nombreProducto, sesion.getBd());
		Date fecha = new Date();
		String origen = "Celular";
		String navegador = "Mozilla";
		Auditoria auditoria = new Auditoria();
		auditoria.setTabla("Asignacion de Productos");
		auditoria.setAccion(accion);
		auditoria.setRegistro(p.getCodigo());
		auditoria.setFecha(fecha);
		auditoria.setOrigen(origen);
		auditoria.setNavegador(navegador);
		auditoria.setUsuario(sesion.getUsuario());
		auditoriaEJB.crear(auditoria, sesion.getBd());
	}
	
	/**
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the inventarioSeleccionado
	 */
	public int getInventarioSeleccionado() {
		return inventarioSeleccionado;
	}

	/**
	 * @param inventarioSeleccionado the inventarioSeleccionado to set
	 */
	public void setInventarioSeleccionado(int inventarioSeleccionado) {
		this.inventarioSeleccionado = inventarioSeleccionado;
	}

	/**
	 * @return the productoSeleccionado
	 */
	public int getProductoSeleccionado() {
		return productoSeleccionado;
	}

	/**
	 * @param productoSeleccionado the productoSeleccionado to set
	 */
	public void setProductoSeleccionado(int productoSeleccionado) {
		this.productoSeleccionado = productoSeleccionado;
	}

	/**
	 * @return the codigoProducto
	 */
	public int getCodigoProducto() {
		return codigoProducto;
	}

	/**
	 * @param codigoProducto the codigoProducto to set
	 */
	public void setCodigoProducto(int codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	/**
	 * @return the codigoInventario
	 */
	public int getCodigoInventario() {
		return codigoInventario;
	}

	/**
	 * @param codigoInventario the codigoInventario to set
	 */
	public void setCodigoInventario(int codigoInventario) {
		this.codigoInventario = codigoInventario;
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
	 * @return the listaProductos
	 */
	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	/**
	 * @param listaProductos the listaProductos to set
	 */
	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	/**
	 * @return the productosXInventarios
	 */
	public List<InventarioProducto> getProductosXInventarios() {
		return productosXInventarios;
	}

	/**
	 * @param productosXInventarios the productosXInventarios to set
	 */
	public void setProductosXInventarios(List<InventarioProducto> productosXInventarios) {
		this.productosXInventarios = productosXInventarios;
	}

	/**
	 * @return the tabla
	 */
	public UIData getTabla() {
		return tabla;
	}

	/**
	 * @param tabla the tabla to set
	 */
	public void setTabla(UIData tabla) {
		this.tabla = tabla;
	}

	
}
