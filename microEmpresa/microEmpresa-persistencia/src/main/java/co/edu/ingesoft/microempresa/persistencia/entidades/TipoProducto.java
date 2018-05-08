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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author TOSHIBAP55W
 *
 */
@Entity
@Table(name="tipo_Producto")
@NamedQueries({
	@NamedQuery(name=TipoProducto.listar,query="SELECT tp FROM TipoProducto tp"),
	@NamedQuery(name=TipoProducto.buscarByNombre,query="SELECT tp FROM TipoProducto tp WHERE tp.nombre=?1")
})
public class TipoProducto implements Serializable{

	public static final String listar = "TipoProducto.listar";
	public static final String buscarByNombre = "TipoProducto.buscarByNombre";
	
	@Id
	@Column(name="codigo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPO_PRODUCTO_SEQ")
    @SequenceGenerator(sequenceName = "tipo_producto_seq", allocationSize = 1, name = "TIPO_PRODUCTO_SEQ")
	private int codigo;
	
	@Column(name="nombre",length=60,nullable=false)
	private String nombre;
	
	@Column(name="descripcion",length=200)
	private String descripcion;
	
	public TipoProducto (){
		
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


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigo;
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoProducto other = (TipoProducto) obj;
		if (codigo != other.codigo)
			return false;
		return true;
	}
}
