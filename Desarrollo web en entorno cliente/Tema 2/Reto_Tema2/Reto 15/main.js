const passwordInput = document.getElementById("password");
const btnComprobar = document.getElementById("btnComprobar");
const resultadoDiv = document.getElementById("resultado");

btnComprobar.addEventListener("click", () => {
  const password = passwordInput.value;

  // Validaciones
  let errores = 0;

  // Longitud >= 8
  const cumpleLongitud = password.length >= 8;

  // Contiene mayúscula
  const tieneMayuscula = /[A-Z]/.test(password);

  // Contiene minúscula
  const tieneMinuscula = /[a-z]/.test(password);

  // Contiene número
  const tieneNumero = /[0-9]/.test(password);

  // Contiene caracteres especiales: guion (-), barra baja (_), igual (=), asterisco (*), mas (+), dólar ($), arroba (@), almohadilla (#)
  const tieneEspecial = /[-_=*\+$@#]/.test(password);

  // Contar cuántos no cumple
  if (!cumpleLongitud) errores++;
  if (!tieneMayuscula) errores++;
  if (!tieneMinuscula) errores++;
  if (!tieneNumero) errores++;
  if (!tieneEspecial) errores++;

  // Resultado según errores
  let mensaje = "";
  if (errores === 0) {
    mensaje = "La contraseña introducida es segura.";
  } else if (errores <= 2) {
    mensaje = "La contraseña es poco segura.";
  } else {
    mensaje = "La contraseña es débil.";
  }

  resultadoDiv.textContent = mensaje;
  console.log(mensaje);
});
