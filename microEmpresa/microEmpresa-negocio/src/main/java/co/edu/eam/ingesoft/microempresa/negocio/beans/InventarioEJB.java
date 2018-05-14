/**
 * 
 */
package co.edu.eam.ingesoft.microempresa.negocio.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.edu.eam.ingesoft.microempresa.negocio.persistencia.Persistencia;
import co.edu.ingesoft.microempresa.persistencia.entidades.Inventario;
import co.edu.ingesoft.microempresa.persistencia.entidades.Rol;
import excepciones.ExcepcionNegocio;

/**
 * @author TOSHIBAP55W
 *
 */
@LocalBean
@Stateless
public class InventarioEJB {

	
		
		@EJB
		private Persistencia conexion;
		
		/**
		 * Registrar
		 */
		public void crear(Inventario objeto, int bd){
			conexion.setBd(bd);
			conexion.crear(objeto);
		}
		
		/**
		 * Editar 
		 */
		public void editar(Inventario objeto, int bd){
			conexion.setBd(bd);
			conexion.editar(objeto);
		}
		
		/**
		 * Buscar 
		 */
		public Inventario buscar(int codigo, int bd){
			conexion.setBd(bd);
			return (Inventario)conexion.buscar(Inventario.class, codigo);
		}
		
		
		/**
		 * Listar
		 */
		public List<Inventario> listar(int bd){
			conexion.setBd(bd);
			return (List<Inventario>)(Object)conexion.listar(Inventario.listar);
		}
		
		/**
		 * Eliminar 
		 */
		public void eliminar(Inventario inv, int bd){
			conexion.setBd(bd);
			Inventario i = buscar(inv.getCodigo(), bd);
			if(i != null){
				conexion.eliminar(i);
			}else{
				throw new ExcepcionNegocio("No existe un Inventario con el codigo "+inv.getCodigo());
			}
	}
}


