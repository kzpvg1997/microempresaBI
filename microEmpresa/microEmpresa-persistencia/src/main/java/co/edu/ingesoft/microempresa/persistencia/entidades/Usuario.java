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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.CascadeType;

/**
 * @author TOSHIBAP55W
 *
 */
@Entity
@Table(name="Usuarios")
@NamedQueries({
	@NamedQuery(name=Usuario.buscarByUsername,query="SELECT u FROM Usuario u WHERE u.username=?1"),
	@NamedQuery(name=Usuario.listar,query="SELECT u FROM Usuario u"),
	@NamedQuery(name=Usuario.listarEmpleados,query="SELECT u FROM Usuario u WHERE u.persona.salario<>?1")
})
public class Usuario implements Serializable{

	public static final String buscarByUsername = "Usuario.buscarByUsername";
	public static final String listar = "Usuario.listar";
	public static final String listarEmpleados = "Usuario.listarEmpleados";
	
	@Id
	@Column(name="codigo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQ")
    @SequenceGenerator(sequenceName = "usuario_seq", allocationSize = 1, name = "USUARIO_SEQ")
	private int codigo;
	
	@Column(name="username",unique=true,length=60,nullable=false)
	private String username;
	
	@Column(name="password",length=60,nullable=false)
	private String password;
	
	/*
	 * El estado nos indicara si el registro del usuario esta inactivo o activo.
	 * True: activo
	 * False: Inactivo 
	 * por defecto al hacer un registro, el estado sera false.
	 */
	@Column(name="estado",nullable=false)
	private boolean estado;
	
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="persona",unique=true)
	private Persona persona;
	
	public Usuario (){
		
	}

	/**
	 * @param username
	 * @param password
	 */
	public Usuario(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.estado = false;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
}
