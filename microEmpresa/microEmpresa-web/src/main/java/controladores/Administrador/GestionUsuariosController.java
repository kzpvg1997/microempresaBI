package controladores.Administrador;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.omnifaces.cdi.ViewScoped;

import co.edu.eam.ingesoft.microempresa.negocio.beans.AreaEmpresaEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.AuditoriaEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.ListasEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.RolEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.UsuarioEJB;
import co.edu.ingesoft.microempresa.persistencia.entidades.AreasEmpresa;
import co.edu.ingesoft.microempresa.persistencia.entidades.Auditoria;
import co.edu.ingesoft.microempresa.persistencia.entidades.Departamento;
import co.edu.ingesoft.microempresa.persistencia.entidades.Genero;
import co.edu.ingesoft.microempresa.persistencia.entidades.Municipio;
import co.edu.ingesoft.microempresa.persistencia.entidades.Persona;
import co.edu.ingesoft.microempresa.persistencia.entidades.Rol;
import co.edu.ingesoft.microempresa.persistencia.entidades.Usuario;
import excepciones.ExcepcionNegocio;
import session.SessionController;
import org.omnifaces.util.Messages;

/**
 * @author Carlos Martinez
 *
 */


@ViewScoped
@Named("GestionUsuariosController")
public class GestionUsuariosController implements Serializable{
	
	@Inject
	private SessionController sesion;
	
	@EJB
	private UsuarioEJB usuarioEJB;

	@EJB
	private RolEJB rolEJB;
	
	@EJB
	private AuditoriaEJB auditoriaEJB;
	
	@EJB
	private AreaEmpresaEJB areaEmpresaEJB;
	
	@EJB
	private ListasEJB listasEJB;
	
	@Pattern(regexp="[0-9]*",message="En la cedula Solo numeros")
	@Length(min=4,max=10,message="Cedula Entre 4 y 10 caracteres")
	private String cedula;
	
	@Pattern(regexp="[a-zA-Z ]*",message="Nombre No valido")
	@Length(min=4,max=50,message="Nombre entre 4 y 50 caracteres")
	private String nombre;
	
	@Pattern(regexp="[a-zA-Z ]*",message="Apellido No valido")
	@Length(min=4,max=50,message="Apellido entre 4 y 50 caracteres")
	private String apellido;
	
	private Rol rol;
	
	private Date fechaNacimiento;
	
	private Genero genero;
	
	private Departamento departamento;
	
	private Municipio municipio;
	
	@Pattern(regexp="[0-9]*",message="Telefono Solo numeros")
	@Length(min=5,max=20,message="Telefono Entre 5 y 20 caracteres")
	private String telefono;
	
	private AreasEmpresa areaEmpresa;
	
	private String elusername;
	
	private String username;
	
	private String password;
	
	private List<AreasEmpresa> areasEmpresa;
	
	private List<Usuario> usuarios;
	
	private List<Auditoria> auditorias;
	
	private List<Auditoria> auditoriasLogins;
	
	private List<Auditoria> auditoriaEmpleados;
	
	private List<Municipio> municipios;
	
	private List<Rol> roles;
	
	private List<Departamento> departamentos;
	
	private List<Genero> generos;
	
	private List<Usuario> empleados;
	
	@Pattern(regexp="[0-9]*",message="En el salario solo se admiten numeros")
	private String salario;
	
	private Date FechaIngreso;
	
	@PostConstruct
	public void inicializar(){
		listar();
	}
	
	public String volverAInicio(){
		return "/paginas/publico/login.xhtml?faces-redirect=true";
	}
	
	/**
	 * Llenar los listados
	 */
	public void listar(){
		generos = listasEJB.listaGeneros(sesion.getBd());
		departamentos = listasEJB.listaDepartamentos(sesion.getBd());
		municipios = listasEJB.listaMunicipiosByDepartamento(departamentos.get(0), sesion.getBd());
		areasEmpresa = areaEmpresaEJB.listarAreasEmpresa(sesion.getBd(), 1);
		listarUsuarios();
		roles = rolEJB.listar(sesion.getBd());
		auditorias = auditoriaEJB.listarByTabla("Usuarios", sesion.getBd());
		auditoriasLogins = auditoriaEJB.listarByTabla("Usuarios login", sesion.getBd());
		auditoriaEmpleados = auditoriaEJB.listarByTabla("Empleados", sesion.getBd());
	}
	/**
	 * Listar Usuarios
	 */
	public void listarUsuarios(){
		usuarios = usuarioEJB.listar(sesion.getBd());
		empleados = usuarioEJB.listarEmpleados(sesion.getBd());
	}
	/**
	 * Listar ciudades de un respectivo departamento
	 */
	public void MunicipiosBydepartamento(){
		try{
			if(departamento != null){
				municipios = listasEJB.listaMunicipiosByDepartamento(departamento, sesion.getBd());
			}
		}catch (excepciones.ExcepcionNegocio e){
			Messages.addFlashGlobalError(e.getMessage());
		}
	}
	
	/**
	 * Buscar
	 */
	public void buscar(){
		try{
			if(username.isEmpty()){
				Messages.addFlashGlobalWarn("Por favor ingrese el username del usuario a buscar");
			}else{
				Usuario u = usuarioEJB.buscarByUsername(username, sesion.getBd());
				if(u != null){
					rol = u.getPersona().getRol();
					cedula = u.getPersona().getCedula();
					nombre = u.getPersona().getNombre();
					apellido = u.getPersona().getApellido();
					fechaNacimiento = u.getPersona().getFechaNacimiento();
					genero = u.getPersona().getGenero();
					departamento = u.getPersona().getMunicipio().getDepartamento();
					municipio = u.getPersona().getMunicipio();
					telefono = u.getPersona().getTelefono();
					areaEmpresa = u.getPersona().getAreaEmpresa();
					elusername = u.getUsername();
					password = u.getPassword();
					// Guardamos en la auditoria
					auditoria(u.getCodigo(), "Buscar");
				}else{
					Messages.addFlashGlobalError("No existe ningun usuario con el username: "+username);
				}
			}
		}catch (NumberFormatException ex){
			Messages.addFlashGlobalWarn("Por favor ingrese solo datos numericos");
		}catch (NullPointerException e) {
			Messages.addFlashGlobalWarn("Por favor ingrese el codigo del rol");
		}
	}
	
	/**
	 * Buscar
	 */
	public void buscarEmpleado(){
		try{
			if(username.isEmpty()){
				Messages.addFlashGlobalWarn("Por favor ingrese el username del empleado a buscar");
			}else{
				Usuario u = usuarioEJB.buscarByUsername(username, sesion.getBd());
				if(u != null){
					rol = u.getPersona().getRol();
					cedula = u.getPersona().getCedula();
					nombre = u.getPersona().getNombre();
					apellido = u.getPersona().getApellido();
					fechaNacimiento = u.getPersona().getFechaNacimiento();
					genero = u.getPersona().getGenero();
					departamento = u.getPersona().getMunicipio().getDepartamento();
					municipio = u.getPersona().getMunicipio();
					telefono = u.getPersona().getTelefono();
					areaEmpresa = u.getPersona().getAreaEmpresa();
					elusername = u.getUsername();
					password = u.getPassword();
					salario = String.valueOf(u.getPersona().getSalario());
					FechaIngreso = u.getPersona().getFechaIngreso();
					// Guardamos en la auditoria
					auditoriaEmpleado(u.getCodigo(), "Buscar");
				}else{
					Messages.addFlashGlobalError("No existe ningun empleado con el username: "+username);
				}
			}
		}catch (NumberFormatException ex){
			Messages.addFlashGlobalWarn("Por favor ingrese solo datos numericos");
		}catch (NullPointerException e) {
			Messages.addFlashGlobalWarn("Por favor ingrese el codigo del rol");
		}
	}
	
	/**
	 * Proceso de registro de la auditoria de la tabla AccesosRol	
	 */
	public void auditoria(int id, String accion){
		Date fecha = new Date();
		String origen = "PC";
		String navegador = "Chrome";
		Auditoria auditoria = new Auditoria();
		auditoria.setTabla("Usuarios");
		auditoria.setAccion(accion);
		auditoria.setRegistro(id);
		auditoria.setFecha(fecha);
		auditoria.setOrigen(origen);
		auditoria.setNavegador(navegador);
		auditoriaEJB.crear(auditoria, sesion.getBd());
	}
	
	/**
	 * Proceso de registro de la auditoria de la tabla usuarios para rol empleado
	 */
	public void auditoriaEmpleado(int id, String accion){
		Date fecha = new Date();
		String origen = "PC";
		String navegador = "Chrome";
		Auditoria auditoria = new Auditoria();
		auditoria.setTabla("Empleados");
		auditoria.setAccion(accion);
		auditoria.setRegistro(id);
		auditoria.setFecha(fecha);
		auditoria.setOrigen(origen);
		auditoria.setNavegador(navegador);
		auditoriaEJB.crear(auditoria, sesion.getBd());
	}
	
	/**
	 * Registrar
	 */
	public void crear(){
		try{
			if(elusername.isEmpty() || apellido.isEmpty() || cedula.isEmpty() || nombre.isEmpty() || telefono.isEmpty()){
				Messages.addFlashGlobalInfo("Por favor ingrese toda la informacion");
			}else{
				Usuario u = new Usuario();
				u.setEstado(true);
				u.setPassword(usuarioEJB.generatePassword());
				u.setUsername(elusername);
				Persona p = new Persona();
				p.setApellido(apellido);
				p.setAreaEmpresa(areaEmpresa);
				p.setCedula(cedula);
				p.setFechaIngreso(null);
				p.setFechaNacimiento(fechaNacimiento);
				p.setGenero(genero);
				p.setMunicipio(municipio);
				p.setNombre(nombre);
				p.setRol(rol);
				p.setTelefono(telefono);
				p.setSalario(0);
				u.setPersona(p);
				usuarioEJB.crear(u, sesion.getBd());
				Messages.addFlashGlobalInfo("El usuario "+nombre+" "+apellido+" se ha registrado exitosamente!");
				limpiar();
				listarUsuarios();
				Usuario user = usuarioEJB.buscarByUsername(u.getUsername(), sesion.getBd());
				if(user != null){
					// Guardamos en la auditoria
					auditoria(user.getCodigo(), "Crear");
				}
			}
		}catch(NullPointerException n){
			Messages.addFlashGlobalWarn("Ingrese toda la informacion");
			n.getMessage();
		}catch (ExcepcionNegocio e){
			Messages.addFlashGlobalWarn(e.getMessage());
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * Registrar usuario, este metodo solo es usado desde registro.xhtml
	 */
	public String registrar(){
		try{
			if(elusername.isEmpty() || apellido.isEmpty() || cedula.isEmpty() || nombre.isEmpty() || telefono.isEmpty()){
				Messages.addFlashGlobalInfo("Por favor ingrese toda la informacion");
			}else{
				Usuario u = new Usuario();
				u.setEstado(false);
				u.setPassword(usuarioEJB.generatePassword());
				u.setUsername(elusername);
				Persona p = new Persona();
				p.setApellido(apellido);
				p.setAreaEmpresa(null);
				p.setCedula(cedula);
				p.setFechaIngreso(null);
				p.setFechaNacimiento(fechaNacimiento);
				p.setGenero(genero);
				p.setMunicipio(municipio);
				p.setNombre(nombre);
				Rol elrol = new Rol();
				elrol.setCodigo(7);
				p.setRol(elrol);
				p.setTelefono(telefono);
				p.setSalario(0);
				u.setPersona(p);
				usuarioEJB.crear(u, sesion.getBd());
				Messages.addFlashGlobalInfo("El usuario "+nombre+" "+apellido+" se ha registrado exitosamente!");
				limpiar();
				listarUsuarios();
				Usuario user = usuarioEJB.buscarByUsername(u.getUsername(), sesion.getBd());
				if(user != null){
					// Guardamos en la auditoria
					auditoria(user.getCodigo(), "Crear");
				}
				return "/paginas/publico/login.xhtml?faces-redirect=true";
			}
		}catch(NullPointerException n){
			Messages.addFlashGlobalWarn("Ingrese toda la informacion");
			n.getMessage();
		}catch (ExcepcionNegocio e){
			Messages.addFlashGlobalWarn(e.getMessage());
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Registrar
	 */
	public void crearEmpleado(){
		try{
			if(elusername.isEmpty() || apellido.isEmpty() || cedula.isEmpty() || nombre.isEmpty() || telefono.isEmpty()){
				Messages.addFlashGlobalInfo("Por favor ingrese toda la informacion");
			}else{
				Usuario u = new Usuario();
				u.setEstado(true);
				u.setPassword(usuarioEJB.generatePassword());
				u.setUsername(elusername);
				Persona p = new Persona();
				p.setApellido(apellido);
				p.setAreaEmpresa(areaEmpresa);
				p.setCedula(cedula);
				p.setFechaIngreso(null);
				p.setFechaNacimiento(fechaNacimiento);
				p.setGenero(genero);
				p.setMunicipio(municipio);
				p.setNombre(nombre);
				p.setRol(rol);
				p.setTelefono(telefono);
				p.setSalario(Double.parseDouble(salario));
				p.setFechaIngreso(FechaIngreso);
				u.setPersona(p);
				usuarioEJB.crear(u, sesion.getBd());
				Messages.addFlashGlobalInfo("El empleado "+nombre+" "+apellido+" se ha registrado exitosamente!");
				limpiar();
				listarUsuarios();
				Usuario user = usuarioEJB.buscarByUsername(u.getUsername(), sesion.getBd());
				if(user != null){
					// Guardamos en la auditoria
					auditoriaEmpleado(user.getCodigo(), "Crear");
				}
			}
		}catch(NullPointerException n){
			Messages.addFlashGlobalWarn("Ingrese toda la informacion");
			n.getMessage();
		}catch (ExcepcionNegocio e){
			Messages.addFlashGlobalWarn(e.getMessage());
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * Limpiar campos
	 */
	public void  limpiar(){
		elusername = "";
		nombre = "";
		apellido = "";
		cedula = "";
		telefono = "";
		salario = "";
	}
	/**
	 * Editar
	 */
	public void editar(){
		
	}
	
	/**
	 * Eliminar
	 */
	public void eliminar(){
		
	}

	/**
	 * @return the sesion
	 */
	public SessionController getSesion() {
		return sesion;
	}

	/**
	 * @param sesion the sesion to set
	 */
	public void setSesion(SessionController sesion) {
		this.sesion = sesion;
	}

	/**
	 * @return the roles
	 */
	public List<Rol> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<Rol> roles) {
		this.roles = roles;
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
	 * @return the generos
	 */
	public List<Genero> getGeneros() {
		return generos;
	}

	/**
	 * @param generos the generos to set
	 */
	public void setGeneros(List<Genero> generos) {
		this.generos = generos;
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
	 * @return the departamentos
	 */
	public List<Departamento> getDepartamentos() {
		return departamentos;
	}

	/**
	 * @param departamentos the departamentos to set
	 */
	public void setDepartamentos(List<Departamento> departamentos) {
		this.departamentos = departamentos;
	}

	/**
	 * @return the departamento
	 */
	public Departamento getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return the municipios
	 */
	public List<Municipio> getMunicipios() {
		return municipios;
	}

	/**
	 * @param municipios the municipios to set
	 */
	public void setMunicipios(List<Municipio> municipios) {
		this.municipios = municipios;
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
	 * @return the areasEmpresa
	 */
	public List<AreasEmpresa> getAreasEmpresa() {
		return areasEmpresa;
	}

	/**
	 * @param areasEmpresa the areasEmpresa to set
	 */
	public void setAreasEmpresa(List<AreasEmpresa> areasEmpresa) {
		this.areasEmpresa = areasEmpresa;
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

	/**
	 * @return the usuarios
	 */
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * @return the auditorias
	 */
	public List<Auditoria> getAuditorias() {
		return auditorias;
	}

	/**
	 * @param auditorias the auditorias to set
	 */
	public void setAuditorias(List<Auditoria> auditorias) {
		this.auditorias = auditorias;
	}

	/**
	 * @return the elusername
	 */
	public String getElusername() {
		return elusername;
	}

	/**
	 * @param elusername the elusername to set
	 */
	public void setElusername(String elusername) {
		this.elusername = elusername;
	}

	/**
	 * @return the auditoriasLogins
	 */
	public List<Auditoria> getAuditoriasLogins() {
		return auditoriasLogins;
	}

	/**
	 * @param auditoriasLogins the auditoriasLogins to set
	 */
	public void setAuditoriasLogins(List<Auditoria> auditoriasLogins) {
		this.auditoriasLogins = auditoriasLogins;
	}

	/**
	 * @return the salario
	 */
	public String getSalario() {
		return salario;
	}

	/**
	 * @param salario the salario to set
	 */
	public void setSalario(String salario) {
		this.salario = salario;
	}

	/**
	 * @return the fechaIngreso
	 */
	public Date getFechaIngreso() {
		return FechaIngreso;
	}

	/**
	 * @param fechaIngreso the fechaIngreso to set
	 */
	public void setFechaIngreso(Date fechaIngreso) {
		FechaIngreso = fechaIngreso;
	}

	/**
	 * @return the auditoriaEmpleados
	 */
	public List<Auditoria> getAuditoriaEmpleados() {
		return auditoriaEmpleados;
	}

	/**
	 * @param auditoriaEmpleados the auditoriaEmpleados to set
	 */
	public void setAuditoriaEmpleados(List<Auditoria> auditoriaEmpleados) {
		this.auditoriaEmpleados = auditoriaEmpleados;
	}

	/**
	 * @return the empleados
	 */
	public List<Usuario> getEmpleados() {
		return empleados;
	}

	/**
	 * @param empleados the empleados to set
	 */
	public void setEmpleados(List<Usuario> empleados) {
		this.empleados = empleados;
	}	
	
}
