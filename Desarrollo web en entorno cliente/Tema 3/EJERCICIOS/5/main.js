// Pide la cadena al usuario
const entrada = prompt("Introduce los datos en el formato:\nnombre:apellidos:telefono:email:codigopostal");

// Divide la cadena por los dos puntos
const partes = entrada.split(":");

if (partes.length === 5) {
  const [nombre, apellidos, telefono, email, codigoPostal] = partes;

  // Extrae el servidor del email
  const servidor = email.split("@")[1];

  // Muestra la información desglosada
  console.log("Código postal:", codigoPostal);
  console.log("Apellidos:", apellidos);
  console.log("Email:", email);
  console.log("Servidor del email:", servidor);

  // También mostramos en pantalla
  document.write(`<h2>Información desglosada:</h2>`);
  document.write(`<p><strong>Código postal:</strong> ${codigoPostal}</p>`);
  document.write(`<p><strong>Apellidos:</strong> ${apellidos}</p>`);
  document.write(`<p><strong>Email:</strong> ${email}</p>`);
  document.write(`<p><strong>Servidor del email:</strong> ${servidor}</p>`);
} else {
  alert("Formato incorrecto. Usa: nombre:apellidos:telefono:email:codigopostal");
}
