package co.edu.ingesoft.microempresa.persistencia.entidades;

import java.io.Serializable;

public class RolAccesoPK implements Serializable{
	
	private int rol;
	
	private int acceso;

	/**
	 * 
	 */
	public RolAccesoPK() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param rol
	 * @param acceso
	 */
	public RolAccesoPK(int rol, int acceso) {
		super();
		this.rol = rol;
		this.acceso = acceso;
	}

	/**
	 * @return the rol
	 */
	public int getRol() {
		return rol;
	}

	/**
	 * @param rol the rol to set
	 */
	public void setRol(int rol) {
		this.rol = rol;
	}

	/**
	 * @return the acceso
	 */
	public int getAcceso() {
		return acceso;
	}

	/**
	 * @param acceso the acceso to set
	 */
	public void setAcceso(int acceso) {
		this.acceso = acceso;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + acceso;
		result = prime * result + rol;
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
		RolAccesoPK other = (RolAccesoPK) obj;
		if (acceso != other.acceso)
			return false;
		if (rol != other.rol)
			return false;
		return true;
	}

}
