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
@Table(name="Accesos")
@NamedQueries({
	@NamedQuery(name=Acceso.listarAccesos,query="SELECT a FROM Acceso a")
})
public class Acceso implements Serializable{

	public static final String listarAccesos = "Acceso.listarAccesos";
	
	@Id
	@Column(name="codigo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCESO_SEQ")
    @SequenceGenerator(sequenceName = "acceso_seq", allocationSize = 1, name = "ACCESO_SEQ")
	private int codigo;
	
	@Column(name="nombre_acceso",length=60,nullable=false)
	private String nombreAcceso;
	
	@Column(name="url",length=60,nullable=false)
	private String url;
	
	public Acceso (){
		
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
	 * @return the nombreAcceso
	 */
	public String getNombreAcceso() {
		return nombreAcceso;
	}

	/**
	 * @param nombreAcceso the nombreAcceso to set
	 */
	public void setNombreAcceso(String nombreAcceso) {
		this.nombreAcceso = nombreAcceso;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
		Acceso other = (Acceso) obj;
		if (codigo != other.codigo)
			return false;
		return true;
	}
	
	
}
