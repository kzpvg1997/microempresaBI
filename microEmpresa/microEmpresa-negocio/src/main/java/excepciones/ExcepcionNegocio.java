package excepciones;
import javax.ejb.ApplicationException;

/**
 * Exception de negocio.
 * @author Carlos Martinez
 *
 */
@ApplicationException(rollback=true)
public class ExcepcionNegocio extends RuntimeException {

	/**
	 * Constructor
	 * @param message
	 */
	public ExcepcionNegocio(String message) {
		super(message);
	}

}
