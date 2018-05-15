/**
 * 
 */
package co.edu.ingesoft.microempresa.persistencia.entidades;

import java.io.Serializable;

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

/**
 * @author TOSHIBAP55W
 *
 */
@Entity
@Table(name="Productos")
@NamedQueries({
	@NamedQuery(name=Producto.listarProductos,query="SELECT p FROM Producto p"),
	@NamedQuery(name=Producto.buscarByNombre,query="SELECT p FROM Producto p WHERE p.nombre=?1"),
	@NamedQuery(name=Producto.listarProductosXInventario,query="SELECT ip FROM InventarioProducto ip where ip.inventario.codigo=?1")
})
public class Producto implements Serializable{

	public static final String listarProductos = "Producto.listarProductos";
	public static final String buscarByNombre = "Producto.buscarByNombre";
	public static final String listarProductosXInventario = "Producto.listarProductosXInventario";
	
	@Id
	@Column(name="codigo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCTO_SEQ")
    @SequenceGenerator(sequenceName = "producto_seq", allocationSize = 1, name = "PRODUCTO_SEQ")
	private int codigo;
	
	@Column(name="nombre",length=60,nullable=false)
	private String nombre;
	
	@Column(name="descripcion",length=200)
	private String descripcion;
	
	@Column(name="dimensiones",length=200)
	private String dimensiones;
	
	@Column(name="peso")
	private double peso;
	
	@Column(name="valor")
	private double valor;
	
	@ManyToOne
	@JoinColumn(name="tipo_producto",nullable=false)
	private TipoProducto tipoProducto;
	
	public Producto (){
		
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
	 * @return the tipoProducto
	 */
	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	/**
	 * @param tipoProducto the tipoProducto to set
	 */
	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	
	
}
