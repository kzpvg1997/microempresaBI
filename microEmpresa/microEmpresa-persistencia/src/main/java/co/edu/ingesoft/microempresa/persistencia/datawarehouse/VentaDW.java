/**
 * 
 */
package co.edu.ingesoft.microempresa.persistencia.datawarehouse;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TABLA DE HECHO VENTAS
 * Venta Data WereHouse
 * 
 * Reglas de Negocio:
 * 1. se elimina la relacion a empresa, ya que pues hay solo 1 empresa en el sistema.
 * 
 */
@Entity
@Table(name="Ventas_DW")
public class VentaDW implements Serializable{

	/**
	 * Por optimizacion, el id es auto incrementable y de valor numerico
	 */
	@Id
	@Column(name="codigo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VENTA_DW_SEQ")
    @SequenceGenerator(sequenceName = "venta_dw_seq", allocationSize = 1, name = "VENTA_DW_SEQ")
	private int codigo;
	
	/**
	 * Fecha en que se realizo la venta
	 */
	@Column(name="fecha_venta",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date fachaVenta;
	
	/**
	 * Valor total de la venta/compra del cliente
	 */
	@Column(name="valor_total")
	private double valorTotal;
	
	/**
	 * El empleado que realizo la venta
	 */
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="empleado",nullable=false)
	private EmpleadoDW personaEmpleado;
	
	/**
	 * El cliente que ralizo la compra
	 */
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="cliente",nullable=false)
	private ClienteDW personaCliente;
	
	public VentaDW(){
		
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
	 * @return the fachaVenta
	 */
	public Date getFachaVenta() {
		return fachaVenta;
	}

	/**
	 * @param fachaVenta the fachaVenta to set
	 */
	public void setFachaVenta(Date fachaVenta) {
		this.fachaVenta = fachaVenta;
	}

	/**
	 * @return the valorTotal
	 */
	public double getValorTotal() {
		return valorTotal;
	}

	/**
	 * @param valorTotal the valorTotal to set
	 */
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	/**
	 * @return the personaEmpleado
	 */
	public EmpleadoDW getPersonaEmpleado() {
		return personaEmpleado;
	}

	/**
	 * @param personaEmpleado the personaEmpleado to set
	 */
	public void setPersonaEmpleado(EmpleadoDW personaEmpleado) {
		this.personaEmpleado = personaEmpleado;
	}

	/**
	 * @return the personaCliente
	 */
	public ClienteDW getPersonaCliente() {
		return personaCliente;
	}

	/**
	 * @param personaCliente the personaCliente to set
	 */
	public void setPersonaCliente(ClienteDW personaCliente) {
		this.personaCliente = personaCliente;
	}

}
