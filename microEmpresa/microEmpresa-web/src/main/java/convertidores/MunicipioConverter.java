package convertidores;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import co.edu.eam.ingesoft.microempresa.negocio.beans.ListasEJB;
import co.edu.ingesoft.microempresa.persistencia.entidades.Municipio;
import session.SessionController;

@FacesConverter(value="municipioConverter",forClass=Municipio.class)
@Named("municipioConverter")
public class MunicipioConverter implements Converter{
	
	@EJB
	private ListasEJB ejb;
	
	@Inject
	private SessionController sesion;
	
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String string) {
		if (string == null || string.trim().length() == 0 || string.equals("Seleccione...")) {
			return null;
		}
		return ejb.buscarMunicipio(Integer.parseInt(string), sesion.getBd());
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 instanceof Municipio) {
			Municipio municipio = (Municipio)arg2;
			return String.valueOf(municipio.getCodigo());
		}
		return null;
	}
}
