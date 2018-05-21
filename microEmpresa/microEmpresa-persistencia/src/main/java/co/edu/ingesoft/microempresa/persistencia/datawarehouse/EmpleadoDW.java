/**
 * 
 */
package co.edu.ingesoft.microempresa.persistencia.datawarehouse;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TABLA DE DIMENSION COMPARTIDA PERSONA
 * Persona Data WereHouse
 * 
 * Reglas de Negocio:
 * 1. se elimina la relacion a usuario, ya que pues no interesa para los reportes (Optimizar)
 * 2. se deja el atributo cedula, para poder generar reportes por persona (Reporting)
 *    2.1 - Se valida que la cedula solo sean numeros, en caso de que no sean numeros se omite.
 * 3. se eliminan los atributos nombre, apellido y telefono por que se consideran inecesarios para los reportes (Optimizar)
 * 4. Se elimina la relacion genero y pasa a ser un atributo string donde se dejara el nombre del genero (Optimizar)
 * 5. Se elimina la relacion rol y pasa a ser un atributo string donde se dejara el nombre del rol (Optimizar)
 * 6. Se elimina la relacion municipio y pasa a ser un atributo string donde se dejara el nombre del municipio (Optimizar)
 * 7. Se elimina la relacion area empresa y pasa a ser un atributo string donde se dejara el nombre del area empresa (Optimizar)
 * 8. se valida que la fecha de nacimiento no sea mayor a la fecha actual. en caso de que sea mayor, se le asigna 
 *    una fecha de mayoria de edad.
 * 9. Se valida que la fecha de ingreso sea actual o menor, en caso de ser mayor se asigna la fecha actual.
 * 10. se valida que el salario no sea negativo, en caso de ser negativo. se deja el valor promedio.
 */
@Entity
@Table(name="Empleados_DW")
@NamedQueries({
	@NamedQuery(name=EmpleadoDW.byCedula,query="SELECT e FROM EmpleadoDW e WHERE e.cedula=?1")
})
public class EmpleadoDW implements  Serializable{
	
	public static final String byCedula = "EmpleadoDW.byCedula";
	
	/**
	 * Por optimizacion, el id es auto incrementable y de valor numerico
	 */
	@Id
	@Column(name="codigo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPLEADO_DW_SEQ")
    @SequenceGenerator(sequenceName = "empleado_dw_seq", allocationSize = 1, name = "EMPLEADO_DW_SEQ")
	private int codigo;
	
	@Column(name="cedula",nullable=false)
	private String cedula;
	
	@Column(name="genero",nullable=false)
	private String genero;
	
	@Column(name="fecha_nacimiento",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	
	@Column(name="fecha_ingreso",nullable=true)
	@Temporal(TemporalType.DATE)
	private Date fechaIngreso;
	
	@Column(name="salario")
	private double salario;
	
	@Column(name="rol",nullable=false)
	private String rol;
	
	@Column(name="municipio",nullable=false)
	private String municipio;
	
	@Column(name="departamento",nullable=false)
	private String departamento;
	
	@Column(name="area_empresa",nullable=false)
	private String areaEmpresa;
	
	
	public EmpleadoDW (){
		
	}

	public int getCodigo() {
		return codigo;
	}


	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}


	public String getCedula() {
		return cedula;
	}


	public void setCedula(String cedula) {
		this.cedula = cedula;
	}


	public String getGenero() {
		return genero;
	}


	public void setGenero(String genero) {
		this.genero = genero;
	}


	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}


	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}


	public Date getFechaIngreso() {
		return fechaIngreso;
	}


	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}


	public double getSalario() {
		return salario;
	}


	public void setSalario(double salario) {
		this.salario = salario;
	}


	public String getRol() {
		return rol;
	}


	public void setRol(String rol) {
		this.rol = rol;
	}


	public String getMunicipio() {
		return municipio;
	}


	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}


	public String getAreaEmpresa() {
		return areaEmpresa;
	}


	public void setAreaEmpresa(String areaEmpresa) {
		this.areaEmpresa = areaEmpresa;
	}

	/**
	 * @return the departamento
	 */
	public String getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	
}
