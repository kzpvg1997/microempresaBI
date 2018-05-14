package seguridad;

import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.ingesoft.microempresa.persistencia.entidades.Acceso;
import session.SessionController;


@WebFilter(urlPatterns = "/paginas/seguro/*")
public class FiltroAcceso implements Filter {

	@Inject
	private SessionController sesion;

	org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(FiltroAcceso.class);

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpreq = (HttpServletRequest) request;
		HttpServletResponse httpresp = (HttpServletResponse) resp;

		String urlcompleta = httpreq.getRequestURI().toString();
		String contextpaht = httpreq.getContextPath();
		
		logger.info("url=" + urlcompleta + ",path=" + contextpaht);
		String url = urlcompleta.substring(contextpaht.length());
		
		logger.info("URL a filtrar=" + url);

		// si el usuario esta en sesion y no es la pagina de inicio que todos tienen
		if (sesion.isSesion()) {
			// busca si tiene acceso a la pagina.
			boolean exito = false;
			if (url.equals("/")) {
				exito=true;
			} else {
				// recorre los accesos autorizados
				List<Acceso> accesos = sesion.getAccesos();
				for (Acceso acceso : accesos) {
					// si esta en la lista de accesos autorizados...
					if (acceso.getUrl().equals(url)) {
						exito = true;
					}
				}
			}
			// no esta en la lista, lo redirige a la inicio.
			if (!exito) {
				httpresp.sendRedirect(httpreq.getContextPath() + "/");
			} else {
				// continua la peticion.
				chain.doFilter(request, resp);
			}
		} else {// no esta en sesion, lo dirige al principio.
			httpresp.sendRedirect(httpreq.getContextPath()+"/paginas/publico/login.xhtml");
		}

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}