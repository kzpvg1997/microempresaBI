package co.edu.ingesoft.microempresa.persistencia.entidades;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="RolAccesos")
@IdClass(RolAccesoPK.class)
@NamedQueries({
	@NamedQuery(name=RolAcceso.listarAccesosByRol,query="SELECT ra FROM RolAcceso ra WHERE ra.rol.codigo=?1")
})
public class RolAcceso implements Serializable{
	
	public static final String listarAccesosByRol = "RolAcceso.listarAccesosByRol";
	
	@Id
	@ManyToOne(cascade={CascadeType.MERGE})
	@JoinColumn(name="rol")
	private Rol rol;
	
	@Id
	@ManyToOne(cascade={CascadeType.MERGE})
	@JoinColumn(name="acceso")
	private Acceso acceso;
	
	/**
	 * 
	 */
	public RolAcceso() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param rol
	 * @param acceso
	 */
	public RolAcceso(Rol rol, Acceso acceso) {
		super();
		this.rol = rol;
		this.acceso = acceso;
	}

	/**
	 * @return the rol
	 */
	public Rol getRol() {
		return rol;
	}

	/**
	 * @param rol the rol to set
	 */
	public void setRol(Rol rol) {
		this.rol = rol;
	}

	/**
	 * @return the acceso
	 */
	public Acceso getAcceso() {
		return acceso;
	}

	/**
	 * @param acceso the acceso to set
	 */
	public void setAcceso(Acceso acceso) {
		this.acceso = acceso;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acceso == null) ? 0 : acceso.hashCode());
		result = prime * result + ((rol == null) ? 0 : rol.hashCode());
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
		RolAcceso other = (RolAcceso) obj;
		if (acceso == null) {
			if (other.acceso != null)
				return false;
		} else if (!acceso.equals(other.acceso))
			return false;
		if (rol == null) {
			if (other.rol != null)
				return false;
		} else if (!rol.equals(other.rol))
			return false;
		return true;
	}
	
	
}
