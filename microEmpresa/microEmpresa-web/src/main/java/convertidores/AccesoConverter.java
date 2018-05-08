package convertidores;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import co.edu.eam.ingesoft.microempresa.negocio.beans.AccesoEJB;
import co.edu.ingesoft.microempresa.persistencia.entidades.Acceso;
import session.SessionController;

@FacesConverter(value="accesoConverter",forClass=Acceso.class)
@Named("accesoConverter")
public class AccesoConverter implements Converter{
	
	@EJB
	private AccesoEJB ejb;
	
	@Inject
	private SessionController sesion;
	
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String string) {
		if (string == null || string.trim().length() == 0 || string.equals("Seleccione...")) {
			return null;
		}
		return ejb.buscar(Integer.parseInt(string), sesion.getBd());
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 instanceof Acceso) {
			Acceso object = (Acceso)arg2;
			return String.valueOf(object.getCodigo());
		}
		return null;
	}
	
}
