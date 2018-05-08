/**
 * 
 */
package co.edu.eam.ingesoft.microempresa.negocio.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.edu.eam.ingesoft.microempresa.negocio.persistencia.Persistencia;
import co.edu.ingesoft.microempresa.persistencia.entidades.Usuario;
import excepciones.ExcepcionNegocio;


/**
 * @author Monica Sepulveda & Carlos Martinez & Kevin Zapata
 *
 */
@LocalBean
@Stateless
public class UsuarioEJB {
	@EJB
	private Persistencia conexion;
	
	/**
	 * Crear
	 */
	public void crear(Usuario u, int bd){
		conexion.setBd(bd);
		if(buscarByUsername(u.getUsername(), bd) == null){
			conexion.crear(u);
		}else{
			throw new excepciones.ExcepcionNegocio("Ya existe un usuario con el username: "+u.getUsername());
		}
	}
	
	/**
	 * Nos genera una password de 8 caracteres
	 * @return
	 */
	public String generatePassword(){
		return java.util.UUID.randomUUID().toString().substring(0, 8);
	}
	
	/**
	 * Editar 
	 */
	public void editar(Usuario usuario, int bd){
		conexion.setBd(bd);
		Usuario u = buscarByUsername(usuario.getUsername(), bd);
		if(usuario.getCodigo() == u.getCodigo()){
			conexion.editar(usuario);
		}else{
			throw new ExcepcionNegocio("Ya existe otro usuario con el username "+u.getUsername());
		}
	}
	
	/**
	 * Buscar un usuario por username
	 * @param username el username del usuario a buscar
	 * @param bd base de datos en la que buscara
	 * @return el usuario en contrado, de lo contrario null
	 */
	public Usuario buscarByUsername(String username, int bd){
		conexion.setBd(bd);
		List<Object> lista = conexion.listarConParametroString(Usuario.buscarByUsername,username);
		if(lista.size() > 0){
			return (Usuario)lista.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * Listar todos los usuarios
	 */
	public List<Usuario> listar(int bd){
		conexion.setBd(bd);
		return (List<Usuario>)(Object)conexion.listar(Usuario.listar);
	}
	
	/**
	 * Listar todos los empleados
	 */
	public List<Usuario> listarEmpleados(int bd){
		conexion.setBd(bd);
		return (List<Usuario>)(Object)conexion.listarConParametroInteger(Usuario.listarEmpleados, Double.parseDouble("0"));
	}
}
