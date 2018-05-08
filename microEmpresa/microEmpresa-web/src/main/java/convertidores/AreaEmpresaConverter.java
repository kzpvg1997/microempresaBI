package convertidores;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import co.edu.eam.ingesoft.microempresa.negocio.beans.AreaEmpresaEJB;
import co.edu.ingesoft.microempresa.persistencia.entidades.AreasEmpresa;
import session.SessionController;

@FacesConverter(value="areaEmpresaConverter",forClass=AreasEmpresa.class)
@Named("areaEmpresaConverter")
public class AreaEmpresaConverter implements Converter{
	
	@EJB
	private AreaEmpresaEJB ejb;
	
	@Inject
	private SessionController sesion;
	
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String string) {
		if (string == null || string.trim().length() == 0 || string.equals("Seleccione...")) {
			return null;
		}
		return ejb.buscar(Integer.parseInt(string), sesion.getBd());
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 instanceof AreasEmpresa) {
			AreasEmpresa areaEmpresa = (AreasEmpresa)arg2;
			return String.valueOf(areaEmpresa.getCodigo());
		}
		return null;
	}
}
