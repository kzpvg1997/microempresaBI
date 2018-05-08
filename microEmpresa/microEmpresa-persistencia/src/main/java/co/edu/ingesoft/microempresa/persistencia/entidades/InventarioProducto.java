/**
 * 
 */
package co.edu.ingesoft.microempresa.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * @author TOSHIBAP55W
 *
 */
@IdClass(InventarioProductoPK.class)
@Entity
@Table(name="Inventario_Producto")
public class InventarioProducto implements Serializable{


	@Id
	@ManyToOne
	@JoinColumn(name="inventario")
	private Inventario inventario;
	
	@Id
	@ManyToOne
	@JoinColumn(name="producto")
	private Producto producto;
	
	@ManyToOne
	@JoinColumn(name="empleado")
	private Persona personaEmpleado;
	
	@Column(name="fecha_ingreso",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	
	@Column(name="cantidad",nullable=false)
	private int cantidad;
	
	
	public InventarioProducto (){
		
	}


	/**
	 * @return the inventario
	 */
	public Inventario getInventario() {
		return inventario;
	}


	/**
	 * @param inventario the inventario to set
	 */
	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}


	/**
	 * @return the producto
	 */
	public Producto getProducto() {
		return producto;
	}


	/**
	 * @param producto the producto to set
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	/**
	 * @return the personaEmpleado
	 */
	public Persona getPersonaEmpleado() {
		return personaEmpleado;
	}


	/**
	 * @param personaEmpleado the personaEmpleado to set
	 */
	public void setPersonaEmpleado(Persona personaEmpleado) {
		this.personaEmpleado = personaEmpleado;
	}


	/**
	 * @return the fechaNacimiento
	 */
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}


	/**
	 * @param fechaNacimiento the fechaNacimiento to set
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
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
	
}
