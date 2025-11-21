let saldo = 50; // Saldo inicial

const resultadoDiv = document.getElementById("resultado");

const jugar = () => {
  const numero = parseInt(document.getElementById("numero").value);
  const apuesta = parseInt(document.getElementById("apuesta").value);

  // Validaciones
  if (isNaN(numero) || numero < 1 || numero > 6) {
    alert("Por favor, elige un número entre 1 y 6.");
    return;
  }

  if (isNaN(apuesta) || apuesta <= 0) {
    alert("Introduce una cantidad válida para apostar.");
    return;
  }

  if (apuesta > saldo) {
    alert("No puedes apostar más dinero del que tienes.");
    return;
  }

  // Tirada de dado (número aleatorio entre 1 y 6)
  const dado = Math.floor(Math.random() * 6) + 1;

  // Resultado del turno
  if (numero === dado) {
    saldo += apuesta * 2; // gana el doble de lo apostado
    resultadoDiv.innerHTML = `
      <p>🎉 ¡Ha salido el número ${dado}! Has acertado.</p>
      <p>Ganaste ${apuesta * 2} €. Tu saldo actual es <strong>${saldo} €</strong>.</p>
    `;
  } else {
    saldo -= apuesta; // pierde lo apostado
    resultadoDiv.innerHTML = `
      <p>❌ Ha salido el número ${dado}. No acertaste.</p>
      <p>Perdiste ${apuesta} €. Tu saldo actual es <strong>${saldo} €</strong>.</p>
    `;
  }

  // Comprobamos si el juego termina
  if (saldo <= 0) {
    resultadoDiv.innerHTML += `<h2>💀 Te has quedado sin dinero. ¡Fin del juego!</h2>`;
    desactivarJuego();
  } else if (saldo >= 200) {
    resultadoDiv.innerHTML += `<h2>🏆 ¡Felicidades! Has alcanzado 200 €. ¡Ganaste el juego!</h2>`;
    desactivarJuego();
  }
};

// Función para bloquear el juego al finalizar
const desactivarJuego = () => {
  document.querySelector("button").disabled = true;
  document.getElementById("numero").disabled = true;
  document.getElementById("apuesta").disabled = true;
};
