const respuestaInput = document.getElementById("respuesta");
const btnComprobar = document.getElementById("btnComprobar");
const mensaje = document.getElementById("mensaje");
const contenido = document.getElementById("contenido");
const control = document.getElementById("control");

// Respuesta correcta (sin mayúsculas, con normalización)
const respuestaCorrecta = "adolfo suárez";

btnComprobar.addEventListener("click", () => {
  const respuestaUsuario = respuestaInput.value.trim().toLowerCase();

  if (respuestaUsuario === "") {
    mensaje.textContent = "Por favor, introduce una respuesta.";
    return;
  }

  if (respuestaUsuario === respuestaCorrecta) {
    // Mostrar contenido y ocultar control parental
    mensaje.textContent = "";
    control.style.display = "none";
    contenido.style.display = "block";
  } else {
    mensaje.textContent = "Respuesta incorrecta. Inténtalo de nuevo.";
    respuestaInput.value = "";
    respuestaInput.focus();
  }
});

// Opcional: permitir pulsar Enter para comprobar
respuestaInput.addEventListener("keydown", (e) => {
  if (e.key === "Enter") {
    btnComprobar.click();
  }
});
