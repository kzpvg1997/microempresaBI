package co.edu.eam.ingesoft.microempresa.negocio.beansDW;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.edu.ingesoft.microempresa.persistencia.datawarehouse.AuditoriaDW;
import co.edu.ingesoft.microempresa.persistencia.datawarehouse.ClienteDW;
import co.edu.ingesoft.microempresa.persistencia.datawarehouse.EmpleadoDW;
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
			list.add(auditoriaDW);
		}
		return list;
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
			cliente.setAreaEmpresa(v.getPersonaCliente().getAreaEmpresa().getNombre());
			cliente.setCedula(v.getPersonaCliente().getCedula());
			//cliente.setCodigo(v.getPersonaCliente().getCodigo());
			cliente.setFechaIngreso(v.getPersonaCliente().getFechaIngreso());
			cliente.setFechaNacimiento(v.getPersonaCliente().getFechaNacimiento());
			cliente.setGenero(v.getPersonaCliente().getGenero().getNombre());
			cliente.setMunicipio(v.getPersonaCliente().getMunicipio().getNombre());
			cliente.setRol(v.getPersonaCliente().getRol().getNombre());
			cliente.setSalario(v.getPersonaCliente().getSalario());
			ventaDW.setPersonaCliente(cliente);
			EmpleadoDW empleado = new EmpleadoDW();
			empleado.setAreaEmpresa(v.getPersonaEmpleado().getAreaEmpresa().getNombre());
			empleado.setCedula(v.getPersonaEmpleado().getCedula());
			//empleado.setCodigo(v.getPersonaEmpleado().getCodigo());
			empleado.setFechaIngreso(v.getPersonaEmpleado().getFechaIngreso());
			empleado.setFechaNacimiento(v.getPersonaEmpleado().getFechaNacimiento());
			empleado.setGenero(v.getPersonaEmpleado().getGenero().getNombre());
			empleado.setMunicipio(v.getPersonaEmpleado().getMunicipio().getNombre());
			empleado.setRol(v.getPersonaEmpleado().getRol().getNombre());
			empleado.setSalario(v.getPersonaEmpleado().getSalario());
			ventaDW.setPersonaEmpleado(empleado);
			list.add(ventaDW);
		}
		return list;
	}
	
}
