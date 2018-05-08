/**
 * 
 */
package co.edu.eam.ingesoft.microempresa.negocio.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.edu.eam.ingesoft.microempresa.negocio.persistencia.Persistencia;
import co.edu.ingesoft.microempresa.persistencia.entidades.Genero;
import co.edu.ingesoft.microempresa.persistencia.entidades.Persona;
import co.edu.ingesoft.microempresa.persistencia.entidades.Rol;

/**
 * @author TOSHIBAP55W
 *
 */
@LocalBean
@Stateless
public class PersonaEJB {

	@EJB
	private Persistencia conexion;
	
	public void crear(Persona persona, int bd){
		conexion.setBd(bd);
		conexion.crear(persona);
	}
	
	public void editar(Persona persona, int bd){
		conexion.setBd(bd);
		conexion.editar(persona);
	}
	
	public void eliminar (Persona persona, int bd){
		conexion.setBd(bd);
		conexion.eliminar(persona);
	}
	
	public Persona buscarXCedula(int codigo, int bd){
		conexion.setBd(bd);
		return (Persona)conexion.buscarXParametroInt(Persona.class, codigo);
	}
	
	public List<Persona> listarPersonas(int bd){
		conexion.setBd(bd);
		return (List<Persona>)(Object)conexion.listar(Persona.listarTodas);
	}
	
	public List<Rol> listarRoles(int bd){
		conexion.setBd(bd);
		return (List<Rol>)(Object)conexion.listar(Rol.listarRoles);
	}
}
