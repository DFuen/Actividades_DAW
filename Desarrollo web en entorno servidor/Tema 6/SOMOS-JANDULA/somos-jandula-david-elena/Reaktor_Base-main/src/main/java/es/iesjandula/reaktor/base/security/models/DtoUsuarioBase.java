package es.iesjandula.reaktor.base.security.models;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import es.iesjandula.reaktor.base.utils.BaseConstants;

/**
 * @author Francisco Manuel Benítez Chico
 */
public class DtoUsuarioBase
{
	/** Atributo - Email */
	private String email ;
	
	/** Atributo - Nombre */
	private String nombre ;
	
	/** Atributo - Apellidos */
	private String apellidos ;

	/** Atributo - Departamento */
	private String departamento ;

	/** Atributo - Fecha de cumpleaños */
	private String fechaNacimiento ;

	/** Atributo - roles */
	private List<String> roles ;

	/**
	 * Constructor por defecto
	 */
	public DtoUsuarioBase()
	{
		// Vacío
	}
	
	/**
	 * Constructor con parámetros
	 * @param email correo electrónico del usuario
	 * @param nombre nombre del usuario
	 * @param apellidos apellidos del usuario
	 * @param departamento departamento del usuario
	 * @param fechaNacimiento fecha de cumpleaños del usuario
	 * @param roles roles del usuario
	 */
	public DtoUsuarioBase(String email, String nombre, String apellidos, String departamento, String fechaNacimiento, String roles)
	{
		this.email           = email;
		this.nombre          = nombre;
		this.apellidos       = apellidos;
		this.departamento    = departamento;
		this.fechaNacimiento = fechaNacimiento;

		if (roles != null && !roles.isEmpty())
		{
			this.roles = Arrays.asList(roles.split(BaseConstants.STRING_COMA));
		}
	}

	/**
	 * Getter para el atributo email
	 * @return el email del usuario
	 */
	public String getEmail()
	{
		return this.email;
	}

	/**
	 * Getter para el atributo nombre
	 * @return el nombre del usuario
	 */
	public String getNombre()
	{
		return this.nombre;
	}

	/**
	 * Getter para el atributo apellidos
	 * @return los apellidos del usuario
	 */
	public String getApellidos()
	{
		return this.apellidos;
	}

	/**
	 * Getter para el atributo departamento
	 * @return el departamento del usuario
	 */
	public String getDepartamento()
	{
		return this.departamento;
	}

	/**
	 * Getter para el atributo fecha de cumpleaños
	 * @return la fecha de cumpleaños del usuario
	 */
	public String getFechaNacimiento()
	{
		return this.fechaNacimiento;
	}

	/**
	 * Getter para el atributo roles
	 * @return los roles del usuario
	 */
	public List<String> getRoles()
	{
		return this.roles;
	}

	/**
	 * Setter para el atributo email
	 * @param email el email del usuario
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * Setter para el atributo nombre
	 * @param nombre el nombre del usuario
	 */
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}	

	/**
	 * Setter para el atributo apellidos
	 * @param apellidos los apellidos del usuario
	 */
	public void setApellidos(String apellidos)
	{
		this.apellidos = apellidos;
	}

	/**	
	 * Setter para el atributo departamento
	 * @param departamento el departamento del usuario
	 */
	public void setDepartamento(String departamento)
	{
		this.departamento = departamento;
	}

	/**
	 * Setter para el atributo fecha de cumpleaños
	 * @param fechaNacimiento la fecha de cumpleaños del usuario
	 */
	public void setFechaNacimiento(String fechaNacimiento)
	{
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * Setter para el atributo roles
	 * @param roles los roles del usuario
	 */
	public void setRoles(List<String> roles)
	{
		this.roles = roles;
	}

	/**
	 * Método para comparar si el objeto es igual al objeto pasado como parámetro
	 * @param obj el objeto a comparar
	 * @return true si el objeto es igual al objeto pasado como parámetro, false en caso contrario
	 */
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		else if (obj == null)
		{
			return false;
		}
		else if (getClass() != obj.getClass())
		{
			return false;
		}
		
		DtoUsuarioBase other = (DtoUsuarioBase) obj;
		return Objects.equals(email, other.email) && Objects.equals(nombre, other.nombre) && Objects.equals(apellidos, other.apellidos) && Objects.equals(departamento, other.departamento);
	}

	/**
	 * Método para calcular el hash del objeto
	 * @return el hash del objeto
	 */
	public int hashCode()
	{
		return Objects.hash(email, nombre, apellidos, departamento);
	}
}
