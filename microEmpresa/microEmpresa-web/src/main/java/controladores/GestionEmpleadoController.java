/**
 * 
 */
package controladores;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import co.edu.eam.ingesoft.microempresa.negocio.beans.AreaEmpresaEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.EmpresaEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.ListasEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.PersonaEJB;
import co.edu.ingesoft.microempresa.persistencia.entidades.AreasEmpresa;
import co.edu.ingesoft.microempresa.persistencia.entidades.Genero;
import co.edu.ingesoft.microempresa.persistencia.entidades.Municipio;
import co.edu.ingesoft.microempresa.persistencia.entidades.Rol;

/**
 * @author TOSHIBAP55W
 *
 */
@ViewScoped
@Named("gestionEmpleadoController")
public class GestionEmpleadoController implements Serializable{

	@EJB
	private PersonaEJB perEjb; 
	
	@EJB
	private  AreaEmpresaEJB areaEmpresaEJB;
	
	@EJB
	private ListasEJB listEjb;
	
	@EJB
	private EmpresaEJB empEjb;
	
	private int codigo;
	
	private String cedulaBuscar;
	
	private String cedulaRegistrar;

	private String nombre;

	private String apellido;
	
	private String telefono;
	
	private List<Genero> listaGeneros;
	
	private Date fechaNacimiento;
	
	private Date fechaIngreso;
	
	private double salario;
	
	private List<Rol> listaRoles;

	private List<Municipio> listaMunicipios;
	
	private List<AreasEmpresa> listaAreasEmpresa;
	
	/*captura de listas*/
	private int generoSeleccionado;
	
	private int rolSeleccionado;
	
	private int municipioSeleccionado;
	
	private int areaSeleccionado;
	
	/*Datos del usuario*/
	
	private String username;
	
	private String password;

	@PostConstruct
	public void inicializar(){
		listarTodo();
	}
	
	public void registrar(){

	}
	
	public void buscarEmpleado(){
		
	}
	
	public void editar(){
		
	}
	
	public void listarTodo(){
		listaGeneros = listEjb.listaGeneros(1);
		listaRoles = perEjb.listarRoles(1);
		listaMunicipios = listEjb.listaMunicipios(1);
		listaAreasEmpresa = areaEmpresaEJB.listarAreasEmpresa(1, 1); //parametro 1= bd , parametro 2= Codigo de la empresa
	}

	/**
	 * @return the generoSeleccionado
	 */
	public int getGeneroSeleccionado() {
		return generoSeleccionado;
	}

	/**
	 * @param generoSeleccionado the generoSeleccionado to set
	 */
	public void setGeneroSeleccionado(int generoSeleccionado) {
		this.generoSeleccionado = generoSeleccionado;
	}

	/**
	 * @return the rolSeleccionado
	 */
	public int getRolSeleccionado() {
		return rolSeleccionado;
	}

	/**
	 * @param rolSeleccionado the rolSeleccionado to set
	 */
	public void setRolSeleccionado(int rolSeleccionado) {
		this.rolSeleccionado = rolSeleccionado;
	}

	/**
	 * @return the municipioSeleccionado
	 */
	public int getMunicipioSeleccionado() {
		return municipioSeleccionado;
	}

	/**
	 * @param municipioSeleccionado the municipioSeleccionado to set
	 */
	public void setMunicipioSeleccionado(int municipioSeleccionado) {
		this.municipioSeleccionado = municipioSeleccionado;
	}

	/**
	 * @return the areaSeleccionado
	 */
	public int getAreaSeleccionado() {
		return areaSeleccionado;
	}

	/**
	 * @param areaSeleccionado the areaSeleccionado to set
	 */
	public void setAreaSeleccionado(int areaSeleccionado) {
		this.areaSeleccionado = areaSeleccionado;
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
	 * @return the cedulaBuscar
	 */
	public String getCedulaBuscar() {
		return cedulaBuscar;
	}

	/**
	 * @param cedulaBuscar the cedulaBuscar to set
	 */
	public void setCedulaBuscar(String cedulaBuscar) {
		this.cedulaBuscar = cedulaBuscar;
	}

	/**
	 * @return the cedulaRegistrar
	 */
	public String getCedulaRegistrar() {
		return cedulaRegistrar;
	}

	/**
	 * @param cedulaRegistrar the cedulaRegistrar to set
	 */
	public void setCedulaRegistrar(String cedulaRegistrar) {
		this.cedulaRegistrar = cedulaRegistrar;
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
	 * @return the listaGeneros
	 */
	public List<Genero> getListaGeneros() {
		return listaGeneros;
	}

	/**
	 * @param listaGeneros the listaGeneros to set
	 */
	public void setListaGeneros(List<Genero> listaGeneros) {
		this.listaGeneros = listaGeneros;
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
	 * @return the listaRoles
	 */
	public List<Rol> getListaRoles() {
		return listaRoles;
	}

	/**
	 * @param listaRoles the listaRoles to set
	 */
	public void setListaRoles(List<Rol> listaRoles) {
		this.listaRoles = listaRoles;
	}

	/**
	 * @return the listaMunicipios
	 */
	public List<Municipio> getListaMunicipios() {
		return listaMunicipios;
	}

	/**
	 * @param listaMunicipios the listaMunicipios to set
	 */
	public void setListaMunicipios(List<Municipio> listaMunicipios) {
		this.listaMunicipios = listaMunicipios;
	}

	/**
	 * @return the listaAreasEmpresa
	 */
	public List<AreasEmpresa> getListaAreasEmpresa() {
		return listaAreasEmpresa;
	}

	/**
	 * @param listaAreasEmpresa the listaAreasEmpresa to set
	 */
	public void setListaAreasEmpresa(List<AreasEmpresa> listaAreasEmpresa) {
		this.listaAreasEmpresa = listaAreasEmpresa;
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
	

	
	
	
}
