package convertidores;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import co.edu.eam.ingesoft.microempresa.negocio.beans.RolEJB;
import co.edu.ingesoft.microempresa.persistencia.entidades.Rol;
import session.SessionController;

@FacesConverter(value="rolConverter",forClass=Rol.class)
@Named("rolConverter")
public class RolConverter implements Converter{
	
	@EJB
	private RolEJB rolEJB;
	
	@Inject
	private SessionController sesion;
	
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String string) {
		if (string == null || string.trim().length() == 0 || string.equals("Seleccione...")) {
			return null;
		}
		return rolEJB.buscar(Integer.parseInt(string), sesion.getBd());
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 instanceof Rol) {
			Rol rol = (Rol)arg2;
			return String.valueOf(rol.getCodigo());
		}
		return null;
	}
}
