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
@Table(name="Roles")
@NamedQueries({
	@NamedQuery(name=Rol.listarRoles,query="SELECT r FROM Rol r"),
	@NamedQuery(name=Rol.buscarByNombre,query="SELECT r FROM Rol r WHERE r.nombre=?1")
})
public class Rol implements Serializable{

	public static final String listarRoles = "Rol.listarRoles";
	public static final String buscarByNombre = "Rol.buscarByNombre";
	
	@Id
	@Column(name="codigo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROL_SEQ")
    @SequenceGenerator(sequenceName = "rol_seq", allocationSize = 1, name = "ROL_SEQ")
	private int codigo;
	
	@Column(name="nombre",unique=true,length=60)
	private String nombre;
	
	@Column(name="descripcion",length=300)
	private String descripcion;

	/**
	 * @param codigo
	 * @param nombre
	 */
	public Rol() {

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

	public String getDescripcion() {
		return descripcion;
	}

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
		Rol other = (Rol) obj;
		if (codigo != other.codigo)
			return false;
		return true;
	}
	
	
}
