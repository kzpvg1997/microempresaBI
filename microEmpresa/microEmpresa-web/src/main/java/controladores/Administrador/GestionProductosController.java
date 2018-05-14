/**
 * 
 */
package controladores.Administrador;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import co.edu.eam.ingesoft.microempresa.negocio.beans.AuditoriaEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.ProductoEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.TipoProductoEJB;
import co.edu.ingesoft.microempresa.persistencia.entidades.Auditoria;
import co.edu.ingesoft.microempresa.persistencia.entidades.Producto;
import co.edu.ingesoft.microempresa.persistencia.entidades.Rol;
import co.edu.ingesoft.microempresa.persistencia.entidades.TipoProducto;
import excepciones.ExcepcionNegocio;
import session.SessionController;

/**
 * @author TOSHIBAP55W
 *
 */
@ViewScoped
@Named("GestionProductosController")
public class GestionProductosController implements Serializable{

	@Inject
	private SessionController sesion;
	
	@EJB
	private ProductoEJB productoEJB;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;
	
	@EJB
	private TipoProductoEJB tipoProEJB;
	
	private int codigo;
		
	private String nombre;
	
	private String descripcion;
	
	private String dimensiones;
	
	private double peso;
	
	private double valor;
	
	private List<TipoProducto> listaTipoProducto;
	
	private List<Producto> listaProductos;
	
	private int codigoInventario;
	
	private int tipoProductoSeleccionado;
	
	@PostConstruct
	public void inicializar() {
		listarTodo();
	}
	
	public void crear(){
	try{
		
		if(nombre.isEmpty() || descripcion.isEmpty() || dimensiones.isEmpty() || peso==0 || valor==0 || tipoProductoSeleccionado==0){
			Messages.addFlashGlobalWarn("Por favor ingrese todos los datos");
		}else{
			Producto p = new Producto();
			p.setNombre(nombre);
			p.setDescripcion(descripcion);
			p.setDimensiones(dimensiones);
			p.setPeso(peso);
			DecimalFormat df = new DecimalFormat("#.0000");
			df.format(valor);
			p.setValor(valor);
			TipoProducto tipo = tipoProEJB.buscar(tipoProductoSeleccionado, sesion.getBd());
			p.setTipoProducto(tipo);
			productoEJB.crear(p, sesion.getBd());
			auditoria(p.getNombre(), "Crear");
			listarTodo();
			limpiar();
			Messages.addFlashGlobalInfo("El producto: "+p.getCodigo()+". "+p.getNombre()+" se ha registrado exitosamente");
			
		}
		
	}catch (ExcepcionNegocio en){
		Messages.addFlashGlobalError(en.getMessage());
	}catch (NumberFormatException ex) {
		Messages.addFlashGlobalWarn("Por favor ingrese solo datos numericos");
	} catch (NullPointerException e) {
		Messages.addFlashGlobalWarn("Por favor ingrese todos los datos");
	}
	}
	
	public void buscar(){
	try{
		if(codigo == 0){
			Messages.addFlashGlobalWarn("Por favor ingrese el codigo de producto");
		}else{
			Producto p = productoEJB.buscar(codigo, sesion.getBd());
			if(p!=null){
			nombre = p.getNombre();
			descripcion = p.getDescripcion();
			dimensiones = p.getDimensiones();
			valor = p.getValor();
			peso = p.getPeso();
			tipoProductoSeleccionado = p.getTipoProducto().getCodigo();
			}else{
				Messages.addFlashGlobalError("El producto con codigo: "+codigo+" NO se encuentra registrado");
			}
		}
		
	}catch (ExcepcionNegocio en){
		Messages.addFlashGlobalError(en.getMessage());
	}catch (NumberFormatException ex) {
		Messages.addFlashGlobalWarn("Por favor ingrese solo datos numericos");
	} catch (NullPointerException e) {
		Messages.addFlashGlobalWarn("Por favor ingrese todos los datos");
	}
	}
	
	public void editar(){
		try{
			if(codigo == 0){
				Messages.addFlashGlobalWarn("Por favor ingrese el codigo de producto para editar");
				return;
			}else{
				Producto p = productoEJB.buscar(codigo, sesion.getBd());
				if(p!=null){
					if(nombre.isEmpty() || descripcion.isEmpty() || dimensiones.isEmpty() || peso==0 || valor==0 || tipoProductoSeleccionado==0){
						Messages.addFlashGlobalWarn("Por favor ingrese todos los datos");
					}else{
						p.setNombre(nombre);
						p.setDescripcion(descripcion);
						p.setDimensiones(dimensiones);
						p.setPeso(peso);
						p.setValor(valor);
						TipoProducto tipo = tipoProEJB.buscar(tipoProductoSeleccionado, sesion.getBd());
						p.setTipoProducto(tipo);
						productoEJB.editar(p, sesion.getBd());
						auditoria(p.getNombre(), "Editar");
						Messages.addFlashGlobalInfo("El producto: "+p.getCodigo()+" se ha editado exitosamente");
						limpiar();
						listarTodo();
					}
				}
			}
		}catch (ExcepcionNegocio en){
			Messages.addFlashGlobalError(en.getMessage());
		}catch (NumberFormatException ex) {
			Messages.addFlashGlobalWarn("Por favor ingrese solo datos numericos");
		} catch (NullPointerException e) {
			Messages.addFlashGlobalWarn("Por favor ingrese todos los datos");
		}
	}
	
	public void eliminar(Producto p){
		try {
			// Guardamos en la auditoria
			auditoria(p.getNombre(), "Eliminar");
			// Eliminamos el Rol seleccionado
			productoEJB.eliminar(p, sesion.getBd());
			limpiar();
			listarTodo();
			Messages.addFlashGlobalInfo("El producto Se ha eliminado correctamente");
		} catch (ExcepcionNegocio e) {
			Messages.addGlobalError(e.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void listarTodo(){
		listaTipoProducto = tipoProEJB.listar(sesion.getBd());
		listaProductos = productoEJB.listar(sesion.getBd());
	}
	
	public void auditoria(String nombreProducto, String accion) {
		Producto p = productoEJB.buscarByNombre(nombreProducto, sesion.getBd());
		Date fecha = new Date();
		String origen = "PC";
		String navegador = "Mozilla";
		Auditoria auditoria = new Auditoria();
		auditoria.setTabla("Gestion Productos");
		auditoria.setAccion(accion);
		auditoria.setRegistro(p.getCodigo());
		auditoria.setFecha(fecha);
		auditoria.setOrigen(origen);
		auditoria.setNavegador(navegador);
		auditoriaEJB.crear(auditoria, sesion.getBd());
	}
	public void limpiar (){
		nombre ="";
		descripcion="";
		dimensiones="";
		peso=0;
		valor=0;
		tipoProductoSeleccionado=0;
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
	 * @return the dimensiones
	 */
	public String getDimensiones() {
		return dimensiones;
	}

	/**
	 * @param dimensiones the dimensiones to set
	 */
	public void setDimensiones(String dimensiones) {
		this.dimensiones = dimensiones;
	}

	/**
	 * @return the peso
	 */
	public double getPeso() {
		return peso;
	}

	/**
	 * @param peso the peso to set
	 */
	public void setPeso(double peso) {
		this.peso = peso;
	}

	/**
	 * @return the valor
	 */
	public double getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(double valor) {
		this.valor = valor;
	}

	/**
	 * @return the listaTipoProducto
	 */
	public List<TipoProducto> getListaTipoProducto() {
		return listaTipoProducto;
	}

	/**
	 * @param listaTipoProducto the listaTipoProducto to set
	 */
	public void setListaTipoProducto(List<TipoProducto> listaTipoProducto) {
		this.listaTipoProducto = listaTipoProducto;
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
	 * @return the tipoProductoSeleccionado
	 */
	public int getTipoProductoSeleccionado() {
		return tipoProductoSeleccionado;
	}

	/**
	 * @param tipoProductoSeleccionado the tipoProductoSeleccionado to set
	 */
	public void setTipoProductoSeleccionado(int tipoProductoSeleccionado) {
		this.tipoProductoSeleccionado = tipoProductoSeleccionado;
	}
	
	
}
