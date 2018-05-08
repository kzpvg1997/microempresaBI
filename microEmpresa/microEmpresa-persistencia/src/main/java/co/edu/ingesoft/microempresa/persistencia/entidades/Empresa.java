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
 * Esta entidad sera encargada de gestionar la informacion de la empresa
 */
@Entity
@Table(name="Empresas")
@NamedQueries({
	@NamedQuery(name=Empresa.listarTodas,query="SELECT e FROM Empresa e")		
})
public class Empresa implements Serializable{

	public static final String listarTodas = "Empresa.listar";
	
	@Id
	@Column(name="codigo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPRESA_SEQ")
    @SequenceGenerator(sequenceName = "empresa_seq", allocationSize = 1, name = "EMPRESA_SEQ")
	private int codigo;
	
	@Column(name="nombre",length=60)
	private String nombre;
	
	@Column(name="telefono",length=60)
	private String telefono;
	
	@Column(name="direccion",length=100)
	private String direccion;
	
	/*
	 * Nos indica que base de datos esta usando el sistema de la empresa
	 * 1 = Oracle
	 * 2 = Postgress
	 * 3 = Mysql
	 */
	@Column(name="bd")
	private int bd;
	
	public Empresa(){
		
	}

	/**
	 * @param codigo
	 * @param nombre
	 * @param telefono
	 * @param direccion
	 * @param bd
	 */
	public Empresa( String nombre, String telefono, String direccion, int bd) {
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
		this.bd = bd;
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
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the bd
	 */
	public int getBd() {
		return bd;
	}

	/**
	 * @param bd the bd to set
	 */
	public void setBd(int bd) {
		this.bd = bd;
	}
	
	
}
