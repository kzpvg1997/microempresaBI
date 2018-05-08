/**
 * 
 */
package co.edu.ingesoft.microempresa.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author TOSHIBAP55W
 *
 */
@Entity
@Table(name="Personas")
@NamedQueries({
	@NamedQuery(name=Persona.listarTodas,query="SELECT p FROM Persona p"),
	@NamedQuery(name=Persona.buscarXCedula,query="SELECT p FROM Persona p where p.cedula=?1")
})
public class Persona implements  Serializable{

	public static final String listarTodas = "Persona.listar";
	
	public static final String buscarXCedula = "Persona.buscarXCedula";
	
	@Id
	@Column(name="codigo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSONA_SEQ")
    @SequenceGenerator(sequenceName = "persona_seq", allocationSize = 1, name = "PERSONA_SEQ")
	private int codigo;
	
	@Column(name="cedula",length=60,nullable=false)
	private String cedula;
	
	@Column(name="nombre",length=60,nullable=false)
	private String nombre;
	
	@Column(name="apellido",length=60,nullable=false)
	private String apellido;
	
	@Column(name="telefono",length=60)
	private String telefono;
	
	@ManyToOne
	@JoinColumn(name="generos",nullable=false)
	private Genero genero;
	
	@Column(name="fecha_nacimiento",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	
	@Column(name="fecha_ingreso",nullable=true)
	@Temporal(TemporalType.DATE)
	private Date fechaIngreso;
	
	@Column(name="salario")
	private double salario;
	
	@ManyToOne
	@JoinColumn(name="rol",nullable=false)
	private Rol rol;
	
	@ManyToOne
	@JoinColumn(name="municipio",nullable=false)
	private Municipio municipio;
	
	@ManyToOne
	@JoinColumn(name="area_empresa",nullable=true)
	private AreasEmpresa areaEmpresa;
	
	
	public Persona (){
		
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
	 * @return the cedula
	 */
	public String getCedula() {
		return cedula;
	}

	/**
	 * @param cedula the cedula to set
	 */
	public void setCedula(String cedula) {
		this.cedula = cedula;
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
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * @param apellido the apellido to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * @return the genero
	 */
	public Genero getGenero() {
		return genero;
	}

	/**
	 * @param genero the genero to set
	 */
	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	/**
	 * @return the fechaNacimiento
	 */
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * @param fechaNacimiento the fechaNacimiento to set
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * @return the fechaIngreso
	 */
	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	/**
	 * @param fechaIngreso the fechaIngreso to set
	 */
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	/**
	 * @return the salario
	 */
	public double getSalario() {
		return salario;
	}

	/**
	 * @param salario the salario to set
	 */
	public void setSalario(double salario) {
		this.salario = salario;
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
	 * @return the municipio
	 */
	public Municipio getMunicipio() {
		return municipio;
	}

	/**
	 * @param municipio the municipio to set
	 */
	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	/**
	 * @return the areaEmpresa
	 */
	public AreasEmpresa getAreaEmpresa() {
		return areaEmpresa;
	}

	/**
	 * @param areaEmpresa the areaEmpresa to set
	 */
	public void setAreaEmpresa(AreasEmpresa areaEmpresa) {
		this.areaEmpresa = areaEmpresa;
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
	
}
