package co.edu.ingesoft.microempresa.persistencia.entidades;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Carlos Martinez
 * Esta entidad nos almacenara toda la informacion de la auditoria de cada una de las tablas
 * Usuarios, Roles, Areas Empresa, Conexion, Recursos Humanos, Ventas, Clientes
 */
@Entity
@Table(name="Auditoria")
@NamedQueries({
	@NamedQuery(name=Auditoria.ByTabla,query="SELECT a FROM Auditoria a WHERE a.tabla=?1"),
	@NamedQuery(name=Auditoria.todo,query="SELECT a FROM Auditoria a"),
	@NamedQuery(name=Auditoria.ByFechas,query="SELECT a FROM Auditoria a WHERE a.fecha BETWEEN ?1 AND ?2")
})
public class Auditoria implements Serializable{
	
	public static final String ByTabla = "Auditoria.ByTabla";
	public static final String todo = "Auditoria.todo";
	public static final String ByFechas = "Auditoria.ByFechas";
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUDITORIA_SEQ")
    @SequenceGenerator(sequenceName = "auditoria_seq", allocationSize = 1, name = "AUDITORIA_SEQ")
	private int id;
	
	@Column(name="accion",length=100,nullable=false)
	private String accion;
	
	@Column(name="tabla",length=200,nullable=false)
	private String tabla;
	
	@Column(name="registro",nullable=false)
	private int registro;
	
	@Column(name="origen",length=200,nullable=false)
	private String origen;
	
	@Column(name="navegador",length=200,nullable=false)
	private String navegador;
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha",nullable=false)
	private Date fecha;
	
	@ManyToOne
	@JoinColumn(name="usuario",nullable=true)
	private Usuario usuario;

	/**
	 * 
	 */
	public Auditoria() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the accion
	 */
	public String getAccion() {
		return accion;
	}

	/**
	 * @param accion the accion to set
	 */
	public void setAccion(String accion) {
		this.accion = accion;
	}

	/**
	 * @return the tabla
	 */
	public String getTabla() {
		return tabla;
	}

	/**
	 * @param tabla the tabla to set
	 */
	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	/**
	 * @return the registro
	 */
	public int getRegistro() {
		return registro;
	}

	/**
	 * @param registro the registro to set
	 */
	public void setRegistro(int registro) {
		this.registro = registro;
	}

	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @return the navegador
	 */
	public String getNavegador() {
		return navegador;
	}

	/**
	 * @param navegador the navegador to set
	 */
	public void setNavegador(String navegador) {
		this.navegador = navegador;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
