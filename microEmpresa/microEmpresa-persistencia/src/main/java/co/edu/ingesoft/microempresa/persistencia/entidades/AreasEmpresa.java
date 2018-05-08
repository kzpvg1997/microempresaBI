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
@Table(name="Areas_Empresa")
@NamedQueries({
	@NamedQuery(name=AreasEmpresa.listarXEmpresa,query="SELECT ae FROM AreasEmpresa ae where ae.empresa.codigo=?1"),
	@NamedQuery(name=AreasEmpresa.buscarByNombre,query="SELECT ae FROM AreasEmpresa ae WHERE ae.nombre=?1")
})
public class AreasEmpresa implements Serializable{

	public static final String listarXEmpresa = "areasEmpresa.listarXEmpresa";
	public static final String buscarByNombre = "AreasEmpresa.buscarByNombre";
	
	@Id
	@Column(name="codigo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AREA_EMPRESA_SEQ")
    @SequenceGenerator(sequenceName = "area_empresa_seq", allocationSize = 1, name = "AREA_EMPRESA_SEQ")
	private int codigo;
	
	@Column(name="nombre",length=60,nullable=false)
	private String nombre;
	
	@Column(name="descripcion",length=200)
	private String descripcion;
	
	@ManyToOne
	@JoinColumn(name="empresa")
	private Empresa empresa;
	
	public AreasEmpresa(){
		
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return nombre;
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
		AreasEmpresa other = (AreasEmpresa) obj;
		if (codigo != other.codigo)
			return false;
		return true;
	}
}
