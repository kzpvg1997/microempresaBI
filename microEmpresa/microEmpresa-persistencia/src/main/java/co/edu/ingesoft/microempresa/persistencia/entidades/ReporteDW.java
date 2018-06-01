/**
 * 
 */
package co.edu.ingesoft.microempresa.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * @author TOSHIBAP55W
 *
 */
@Entity
@Table(name="Reporte_DW")
@NamedQueries({
	@NamedQuery(name=ReporteDW.listarReportesDWXUsuario,query="SELECT rdw FROM ReporteDW rdw where rdw.usuario=?1"),
	@NamedQuery(name=ReporteDW.BuscarByNombre,query="SELECT rdw FROM ReporteDW rdw where rdw.nombreReporte=?1")
})
public class ReporteDW implements Serializable{


	public static final String listarReportesDWXUsuario = "ReportesDW.listarRecportesXUsuario";
	public static final String BuscarByNombre = "ReportesDW.BuscarReporte";

	
	@Id
	@Column(name="codigo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPORTE_DW_SEQ")
    @SequenceGenerator(sequenceName = "reporte_dw_seq", allocationSize = 1, name = "REPORTE_DW_SEQ")
	private int codigo;
	
	@Column(name="nombre_reporte")
	private String nombreReporte;
	
	@Column(name="imagen")
	@Lob
	private byte[] imagen;
	
	@Column(name="descripcion",length=400)
	private String descripcion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha")
	private Date fechaReporte;
	
	@Column(name="usuario")
	private int usuario;
	
	public ReporteDW(){
		
	}

	/**
	 * @return the codigo
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the imagen
	 */
	public byte[] getImagen() {
		return imagen;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the fechaReporte
	 */
	public Date getFechaReporte() {
		return fechaReporte;
	}

	/**
	 * @param fechaReporte the fechaReporte to set
	 */
	public void setFechaReporte(Date fechaReporte) {
		this.fechaReporte = fechaReporte;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return nombreReporte;
	}

	/**
	 * @return the nombreReporte
	 */
	public String getNombreReporte() {
		return nombreReporte;
	}

	/**
	 * @param nombreReporte the nombreReporte to set
	 */
	public void setNombreReporte(String nombreReporte) {
		this.nombreReporte = nombreReporte;
	}

	/**
	 * @return the usuario
	 */
	public int getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}
	
}
