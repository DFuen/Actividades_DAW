package es.iesjandula.reaktor.base.security.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.iesjandula.reaktor.base.utils.BaseConstants;
import es.iesjandula.reaktor.base.utils.BaseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PublicKeyGetter
{
	/**
	 * Logger of the class
	 */
	private static final Logger log = LoggerFactory.getLogger(PublicKeyGetter.class);

	@Value("${reaktor.publicKeyFile}")
	private String publicKeyFile ;
	
	/**
	 * @return la clave pública
	 * @throws BaseException con un error
	 */
	public PublicKey obtenerClavePublica() throws BaseException
	{
		try
		{
		    // Lee el contenido del archivo de clave pública ('public_key.pem') y lo convierte a una cadena (String)
		    String publicKeyContent = new String(Files.readAllBytes(Paths.get(this.publicKeyFile)));
		    
		    // Elimina los saltos de línea (\n) y las etiquetas de inicio y fin de la clave pública
		    publicKeyContent = publicKeyContent.replaceAll("\\n", "") // Elimina todos los saltos de línea
					 						   .replaceAll("\\r", "")
									           .replace("-----BEGIN PUBLIC KEY-----", "")        // Elimina la etiqueta de inicio de la clave pública
									           .replace("-----END PUBLIC KEY-----", "") ;        // Elimina la etiqueta de fin de la clave pública
		    
		    // Crea una instancia de KeyFactory para el algoritmo de clave pública RSA
		    KeyFactory keyFactory = KeyFactory.getInstance("RSA") ;
		    
		    // Crea una especificación de clave pública X509 a partir de la cadena decodificada en Base64
		    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent)) ;
		    
		    // Genera una instancia de PublicKey a partir de la especificación de clave pública (keySpec)
		    return keyFactory.generatePublic(keySpec) ;
		}
		catch (IOException ioException)
		{
			String errorString = "IOException mientras se cargaba el fichero con la clave privada" ;
			
			log.error(errorString, ioException) ;
			throw new BaseException(BaseConstants.ERR_GETTING_PUBLIC_KEY, errorString, ioException) ;
		}
		catch (InvalidKeySpecException invalidKeySpecException)
		{
			String errorString = "InvalidKeySpecException mientras se cargaba el fichero con la clave privada" ;
			
			log.error(errorString, invalidKeySpecException) ;
			throw new BaseException(BaseConstants.ERR_GETTING_PUBLIC_KEY, errorString, invalidKeySpecException) ;
		}
		catch (NoSuchAlgorithmException noSuchAlgorithmException)
		{
			String errorString = "NoSuchAlgorithmException mientras se cargaba el fichero con la clave privada" ;
			
			log.error(errorString, noSuchAlgorithmException) ;
			throw new BaseException(BaseConstants.ERR_GETTING_PUBLIC_KEY, errorString, noSuchAlgorithmException) ;
		}
	}
}
