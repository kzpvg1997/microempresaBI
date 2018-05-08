package co.edu.eam.ingesoft.microempresa.negocio.beansSeguridad;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.edu.eam.ingesoft.microempresa.negocio.beans.AccesoEJB;
import co.edu.eam.ingesoft.microempresa.negocio.beans.AccesosRolEJB;
import co.edu.eam.ingesoft.microempresa.negocio.persistencia.Persistencia;
import co.edu.ingesoft.microempresa.persistencia.entidades.Acceso;
import co.edu.ingesoft.microempresa.persistencia.entidades.Departamento;
import co.edu.ingesoft.microempresa.persistencia.entidades.Rol;

/**
 * 
 * @author Carlos Martinez & Monica Sepulveda & Kevin Zapata
 *
 */
@Stateless
public class SeguridadEJB {
	
	@EJB
	private AccesosRolEJB ejb;
	

	/**
	 * Método que lista los accesos validos para un rol
	 */
	public List<Acceso> listarAccesosRol(Rol rol, int bd) {
		return ejb.listarByRol(rol, bd);
	}
}
