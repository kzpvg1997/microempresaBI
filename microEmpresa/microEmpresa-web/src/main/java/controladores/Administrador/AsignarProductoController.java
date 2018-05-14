/**
 * 
 */
package controladores.Administrador;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import co.edu.eam.ingesoft.microempresa.negocio.beans.AuditoriaEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.ProductoEJB;
import co.edu.ingesoft.microempresa.persistencia.entidades.Inventario;
import co.edu.ingesoft.microempresa.persistencia.entidades.Producto;
import session.SessionController;

/**
 * @author TOSHIBAP55W
 *
 */
@ViewScoped
@Named("GestionProductosController")
public class AsignarProductoController implements Serializable{

	@Inject
	private SessionController sesion;
	
	@EJB
	private ProductoEJB productoEJB;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;
	
	
	private int codigoProducto;
	
	private int codigoInventario;
	
	private List<Inventario> listaInventarios;
	
	private List<Producto> listaProductos;
}
