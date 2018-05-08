package excepciones;
import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class ExcepcionFuncional extends RuntimeException{

	public ExcepcionFuncional(String message) {
		super(message);
	}
	
}
