/**
 * 
 */
package co.edu.ingesoft.microempresa.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author TOSHIBAP55W
 *
 */
@Entity
@Table(name="Ventas")
@NamedQueries({
	@NamedQuery(name=Venta.todo,query="SELECT v FROM Venta v"),
	@NamedQuery(name=Venta.ByFechas,query="SELECT v FROM Venta v WHERE v.fachaVenta BETWEEN ?1 AND ?2")
})
public class Venta implements Serializable{

	public static final String todo = "Venta.todo";
	public static final String ByFechas = "Venta.ByFechas";
	
	@Id
	@Column(name="codigo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VENTA_SEQ")
    @SequenceGenerator(sequenceName = "venta_seq", allocationSize = 1, name = "VENTA_SEQ")
	private int codigo;
	
	@Column(name="fecha_venta",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date fachaVenta;
	
	@Column(name="valor_total")
	private double valorTotal;
	
	@ManyToOne
	@JoinColumn(name="empresa",nullable=false)
	private Empresa empresa;
	
	@ManyToOne
	@JoinColumn(name="empleado",nullable=false)
	private Persona personaEmpleado;
	
	@ManyToOne
	@JoinColumn(name="cliente",nullable=false)
	private Persona personaCliente;
	
	public Venta(){
		
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
	 * @return the empresa
	 */
	public Empresa getEmpresa() {
		return empresa;
	}

	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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
	 * @return the personaCliente
	 */
	public Persona getPersonaCliente() {
		return personaCliente;
	}

	/**
	 * @param personaCliente the personaCliente to set
	 */
	public void setPersonaCliente(Persona personaCliente) {
		this.personaCliente = personaCliente;
	}
	
	
}
