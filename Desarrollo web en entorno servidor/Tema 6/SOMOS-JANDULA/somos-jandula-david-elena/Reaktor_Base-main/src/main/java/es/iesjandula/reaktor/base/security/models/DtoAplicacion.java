package es.iesjandula.reaktor.base.security.models;

import java.util.List;

/**
 * @author Francisco Manuel Benítez Chico
 */
public class DtoAplicacion
{
	/** Atributo - nombre */
	private String nombre ;
	
	/** Atributo - roles */
	private List<String> roles ;

	/**
	 * Constructor por defecto
	 */
	public DtoAplicacion()
	{
		// Vacío
	}

	/**
	 * Constructor con parámetros
	 * @param nombre
	 * @param roles
	 */
	public DtoAplicacion(String nombre, List<String> roles)
	{
		this.nombre = nombre;
		this.roles  = roles;
	}

	/**
	 * Getter para el atributo nombre
	 * @return el nombre de la aplicación
	 */
	public String getNombre()
	{
		return nombre;
	}

	/**
	 * Getter para el atributo roles
	 * @return los roles de la aplicación
	 */
	public List<String> getRoles()
	{
		return roles;
	}

	/**
	 * Setter para el atributo nombre
	 * @param nombre el nombre de la aplicación
	 */
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	/**
	 * Setter para el atributo roles
	 * @param roles los roles de la aplicación
	 */
	public void setRoles(List<String> roles)
	{
		this.roles = roles;
	}
}
