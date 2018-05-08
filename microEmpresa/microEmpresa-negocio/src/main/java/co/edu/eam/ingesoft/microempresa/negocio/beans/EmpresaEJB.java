package co.edu.eam.ingesoft.microempresa.negocio.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.edu.eam.ingesoft.microempresa.negocio.persistencia.Persistencia;
import co.edu.ingesoft.microempresa.persistencia.entidades.AreasEmpresa;
import co.edu.ingesoft.microempresa.persistencia.entidades.Empresa;
import co.edu.ingesoft.microempresa.persistencia.entidades.Rol;

/**
 * 
 * @author carlos martinez
 * Se encarga de todas las operaciones a la tabla empresas
 */
@LocalBean
@Stateless
public class EmpresaEJB {
	
	@EJB
	private Persistencia conexion;
	
	/**
	 * Registrar empresa en la base de datos
	 * @param empresa la empresa a registrar
	 * @param bd base de datos en la que sera guardada la informacion
	 */
	public void crear(Empresa empresa, int bd){
		conexion.setBd(bd);
		conexion.crear(empresa);
	}
	
	/**
	 * Editar empresa 
	 * @param empresa la empresa a editar
	 * @param bd base de datos en la que sera guardada la informacion
	 */
	public void editar(Empresa empresa, int bd){
		conexion.setBd(bd);
		conexion.editar(empresa);
	}
	
	/**
	 * Eliminar empresa 
	 * @param empresa la empresa a eliminar
	 * @param bd base de datos en la que sera eliminada la informacion
	 */
	public void eliminar(Empresa empresa, int bd){
		conexion.setBd(bd);
		conexion.eliminar(empresa);
	}
	
	/**
	 * Buscar empresa por codigo
	 * @param codigo el codigo de la empresa a buscar
	 * @param bd base de datos en la que buscara la empresa
	 */
	public Empresa buscar(int codigo, int bd){
		conexion.setBd(bd);
		return (Empresa)conexion.buscar(Empresa.class, codigo);
	}
	
	/**
	 * Listar
	 * @param bd base de datos en la que obtendra las empresas
	 * @return lista de empresas
	 */
	public List<Empresa> listar(int bd){
		conexion.setBd(bd);
		return (List<Empresa>)(Object)conexion.listar(Empresa.listarTodas);
	}
	
}
