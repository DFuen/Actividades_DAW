// Función flecha para obtener la letra del DNI
const letraDNI = numero => {
  const letras = "TRWAGMYFPDXBNJZSQVHLCKE";
  return letras[numero % 23];
};

let dnis = []; // Guardará las letras de los DNIs introducidos

// Función flecha que pide el DNI
const pedirDNI = () => {
  const input = prompt("Introduce un número de DNI (sin letra). Escribe -1 para terminar:");

  // Si el usuario cancela o escribe -1, detenemos el intervalo
  if (input === null || input.trim() === "-1") {
    clearInterval(intervalo);
    alert("Letras de los DNIs introducidos: " + dnis.join(""));
    return;
  }

  // Convertimos a número y validamos
  const numero = parseInt(input);
  if (!isNaN(numero) && numero >= 0) {
    dnis.push(letraDNI(numero));
  } else {
    alert("Por favor, introduce un número de DNI válido.");
  }
};

// Ejecuta pedirDNI cada 20 segundos
const intervalo = setInterval(pedirDNI, 20000);

// Llama la primera vez inmediatamente
pedirDNI();
