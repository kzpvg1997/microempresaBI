/**
 * 
 */
package co.edu.ingesoft.microempresa.persistencia.entidades;

import java.io.Serializable;

/**
 * @author TOSHIBAP55W
 *
 */
public class InventarioProductoPK implements Serializable{

	private int inventario;
	
	private int producto;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + inventario;
		result = prime * result + producto;
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
		InventarioProductoPK other = (InventarioProductoPK) obj;
		if (inventario != other.inventario)
			return false;
		if (producto != other.producto)
			return false;
		return true;
	}

	/**
	 * @return the inventario
	 */
	public int getInventario() {
		return inventario;
	}

	/**
	 * @param inventario the inventario to set
	 */
	public void setInventario(int inventario) {
		this.inventario = inventario;
	}

	/**
	 * @return the producto
	 */
	public int getProducto() {
		return producto;
	}

	/**
	 * @param producto the producto to set
	 */
	public void setProducto(int producto) {
		this.producto = producto;
	}

	
}