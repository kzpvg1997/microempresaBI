package co.edu.eam.ingesoft.microempresa.negocio.beansDW;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.edu.ingesoft.microempresa.persistencia.datawarehouse.AuditoriaDW;
import co.edu.ingesoft.microempresa.persistencia.datawarehouse.ClienteDW;
import co.edu.ingesoft.microempresa.persistencia.datawarehouse.EmpleadoDW;
import co.edu.ingesoft.microempresa.persistencia.datawarehouse.UsuarioDW;
import co.edu.ingesoft.microempresa.persistencia.datawarehouse.VentaDW;
import co.edu.ingesoft.microempresa.persistencia.entidades.Auditoria;
import co.edu.ingesoft.microempresa.persistencia.entidades.Persona;
import co.edu.ingesoft.microempresa.persistencia.entidades.Venta;

/**
 * Proceso de ETL: La Transformacion de los datos de la base de datos
 * el data warehouse estara alojado en MySql
 * @author Carlos Martinez & Kevin Zapata & Monica Sepulveda
 *
 */
@LocalBean
@Stateless
public class TransformationETL {
	
	/**
	 * Transformacion de la tabla auditoria para el data warehouse
	 * @param lista tabla original auditoria de la base de datos
	 * @return el listado de regisgtros para la el data warehouse
	 */
	public List<AuditoriaDW> auditoriaDW(List<Auditoria> lista){
		List<AuditoriaDW> list = new ArrayList<AuditoriaDW>();
		for (Auditoria a : lista) {
			AuditoriaDW auditoriaDW = new  AuditoriaDW();
			auditoriaDW.setAccion(a.getAccion());
			auditoriaDW.setFecha(a.getFecha());
			auditoriaDW.setNavegador(a.getNavegador());
			auditoriaDW.setOrigen(a.getOrigen());
			auditoriaDW.setTabla(a.getTabla());
			auditoriaDW.setRegistro(a.getRegistro());
				UsuarioDW u = new UsuarioDW();
				u.setCedula(a.getUsuario().getPersona().getCedula());
				u.setFechaNacimiento(a.getUsuario().getPersona().getFechaNacimiento());
				u.setGenero(a.getUsuario().getPersona().getGenero().getNombre());
				u.setMunicipio(a.getUsuario().getPersona().getMunicipio().getNombre());
				u.setRol(a.getUsuario().getPersona().getRol().getNombre());
				u.setDepartamento(a.getUsuario().getPersona().getMunicipio().getDepartamento().getNombre());
				u.setUsuario(a.getUsuario().getUsername());
				u.setEdad(calculaEdad(u.getFechaNacimiento()));
		    auditoriaDW.setUsuario(u);
			list.add(auditoriaDW);
		}
		return list;
	}
	
	/**
	 * Obtiene los usuarios asociados a las auditorias
	 * @param lista
	 * @return
	 */
	public List<UsuarioDW> usuariosAuditoriaDW(List<AuditoriaDW> lista){
		List<UsuarioDW> listado = new ArrayList<UsuarioDW>();
		for (AuditoriaDW a : lista) {
			if(!buscarSiExiste(listado, a.getUsuario().getCedula())){
				listado.add(a.getUsuario());
			}
		}
		return listado;
	}
	
	/**
	 * Busca si ya existe un usuario auditoria en el listado
	 */
	public boolean buscarSiExiste(List<UsuarioDW> lista, String cedula){
		for (UsuarioDW u : lista) {
			if(u.getCedula().equals(cedula)){
				// si existe
				return true;
			}
		}
		// no existe
		return false;
	}
	
	/**
	 * Transformacion de la tabla auditoria para el data warehouse
	 * @param lista tabla original auditoria de la base de datos
	 * @return el listado de regisgtros para la el data warehouse
	 */
	public List<VentaDW> ventaDW(List<Venta> lista){
		List<VentaDW> list = new ArrayList<VentaDW>();
		for (Venta v : lista) {
			VentaDW ventaDW = new VentaDW();
			//ventaDW.setCodigo(v.getCodigo());
			ventaDW.setFachaVenta(v.getFachaVenta());
			ventaDW.setValorTotal(v.getValorTotal());
				ClienteDW cliente = new ClienteDW();
				cliente.setAreaEmpresa(v.getPersonaEmpleado().getAreaEmpresa().getNombre());
				cliente.setCedula(v.getPersonaCliente().getCedula());
				cliente.setFechaIngreso(v.getPersonaCliente().getFechaIngreso());
				cliente.setFechaNacimiento(v.getPersonaCliente().getFechaNacimiento());
				cliente.setGenero(v.getPersonaCliente().getGenero().getNombre());
				cliente.setMunicipio(v.getPersonaCliente().getMunicipio().getNombre());
				cliente.setDepartamento(v.getPersonaCliente().getMunicipio().getDepartamento().getNombre());
				cliente.setRol(v.getPersonaCliente().getRol().getNombre());
				cliente.setSalario(v.getPersonaCliente().getSalario());
				cliente.setEdad(calculaEdad(cliente.getFechaNacimiento()));
			ventaDW.setPersonaCliente(cliente);
				EmpleadoDW empleado = new EmpleadoDW();
				empleado.setAreaEmpresa(v.getPersonaEmpleado().getAreaEmpresa().getNombre());
				empleado.setCedula(v.getPersonaEmpleado().getCedula());
				empleado.setFechaIngreso(v.getPersonaEmpleado().getFechaIngreso());
				empleado.setFechaNacimiento(v.getPersonaEmpleado().getFechaNacimiento());
				empleado.setGenero(v.getPersonaEmpleado().getGenero().getNombre());
				empleado.setMunicipio(v.getPersonaEmpleado().getMunicipio().getNombre());
				empleado.setDepartamento(v.getPersonaEmpleado().getMunicipio().getDepartamento().getNombre());
				empleado.setRol(v.getPersonaEmpleado().getRol().getNombre());
				empleado.setSalario(v.getPersonaEmpleado().getSalario());
				empleado.setEdad(calculaEdad(empleado.getFechaNacimiento()));
			ventaDW.setPersonaEmpleado(empleado);
			list.add(ventaDW);
		}
		return list;
	}
	
	/**
	 * metodo para calcular la edad a partir de un calendar
	 */	
	private int calculaEdad(Date fechaNac) {
        Date today = new Date();
        int diff_year = today.getYear() -  fechaNac.getYear();
        int diff_month = today.getMonth() - fechaNac.getMonth();
        int diff_day = today.getDay() - fechaNac.getDay();
        //Si está en ese año pero todavía no los ha cumplido
        if (diff_month < 0 || (diff_month == 0 && diff_day < 0)) {
            diff_year = diff_year - 1; //no aparecían los dos guiones del postincremento :|
        }
        return diff_year;
    }
	
}
