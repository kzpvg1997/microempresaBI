package co.edu.ingesoft.microempresa.persistencia.datawarehouse;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import co.edu.ingesoft.microempresa.persistencia.entidades.Persona;

/**
 * TABLA DE HECHO AUDITORIA
 * Auditoria Data WereHouse
 * 
 * Reglas de Negocio:
 * se elimino el id de la auditoria, a este se le puso otro id pero auto incrementable para el data warehouse
 * que no tendra nada que ver con el id de auditoria de la bd
 */
@Entity
@Table(name="Auditoria_DW")
public class AuditoriaDW implements Serializable{
	
	/**
	 * Por optimizacion, el id es auto incrementable y de valor numerico
	 */
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUDITORIA_DW_SEQ")
    @SequenceGenerator(sequenceName = "auditoria_dw_seq", allocationSize = 1, name = "AUDITORIA_DW_SEQ")
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
	private UsuarioDW usuario;

	/**
	 * 
	 */
	public AuditoriaDW() {
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
	public UsuarioDW getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(UsuarioDW usuario) {
		this.usuario = usuario;
	}
	
}
