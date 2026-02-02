package es.iesjandula.reaktor.base.security.models;

import java.util.List;

/**
 * @author Francisco Manuel Benítez Chico
 */
public class DtoUsuarioExtended extends DtoUsuarioBase
{
	/** Atributo - JWT */
	private String jwt ;

	/**
	 * Constructor por defecto
	 */
	public DtoUsuarioExtended()
	{
		super();
	}

	/**
	 * Constructor con parámetros
	 * @param email correo electrónico del usuario
	 * @param nombre nombre del usuario
	 * @param apellidos apellidos del usuario
	 * @param departamento departamento del usuario
	 * @param fechaNacimiento fecha de cumpleaños del usuario
	 * @param roles roles del usuario
	 * @param jwt jwt del usuario
	 */
	public DtoUsuarioExtended(String email, String nombre, String apellidos, String departamento, String fechaNacimiento, String roles, String jwt)
	{
		super(email, nombre, apellidos, departamento, fechaNacimiento, roles);

		this.jwt = jwt;
	}
	
	/**
	 * Getter para el atributo jwt
	 * @return el jwt del usuario
	 */
	public String getJwt()
	{
		return this.jwt;
	}

	/**
	 * Setter para el atributo jwt
	 * @param jwt el jwt del usuario
	 */
	public void setJwt(String jwt)
	{
		this.jwt = jwt;
	}
}
