const preguntas = [
  {
    categoria: "Historia",
    pregunta: "¿Quién fue el primer presidente de la democracia española?",
    opciones: ["Adolfo Suárez", "Felipe González", "Leopoldo Calvo-Sotelo", "Manuel Fraga"],
    correcta: 0
  },
  {
    categoria: "Ciencia",
    pregunta: "¿Cuál es el elemento químico con símbolo 'O'?",
    opciones: ["Oro", "Oxígeno", "Osmio", "Plomo"],
    correcta: 1
  },
  {
    categoria: "Geografía",
    pregunta: "¿Cuál es la capital de Australia?",
    opciones: ["Sídney", "Melbourne", "Canberra", "Brisbane"],
    correcta: 2
  },
  {
    categoria: "Deportes",
    pregunta: "¿En qué deporte se utiliza un disco llamado 'puck'?",
    opciones: ["Baloncesto", "Hockey sobre hielo", "Fútbol", "Béisbol"],
    correcta: 1
  },
  {
    categoria: "Literatura",
    pregunta: "¿Quién escribió 'Cien años de soledad'?",
    opciones: ["Pablo Neruda", "Gabriel García Márquez", "Jorge Luis Borges", "Mario Vargas Llosa"],
    correcta: 1
  },
  {
    categoria: "Tecnología",
    pregunta: "¿Qué significa 'HTTP' en una URL?",
    opciones: [
      "HyperText Transfer Protocol",
      "High Transfer Text Protocol",
      "Hyperlink Text Transmission Protocol",
      "HyperText Transmission Procedure"
    ],
    correcta: 0
  }
];

let indiceActual = 0;
let aciertos = 0;
let fallos = 0;

const categoriaDiv = document.getElementById("categoria");
const preguntaDiv = document.getElementById("pregunta");
const opcionesDiv = document.getElementById("opciones");
const resultadoDiv = document.getElementById("resultado");

function mostrarPregunta() {
  if (indiceActual >= preguntas.length) {
    terminarJuego();
    return;
  }
  const actual = preguntas[indiceActual];
  categoriaDiv.textContent = `Categoría: ${actual.categoria}`;
  preguntaDiv.textContent = actual.pregunta;

  opcionesDiv.innerHTML = "";
  actual.opciones.forEach((opcion, index) => {
    const btn = document.createElement("button");
    btn.textContent = opcion;
    btn.addEventListener("click", () => seleccionarRespuesta(index));
    opcionesDiv.appendChild(btn);
  });

  resultadoDiv.textContent = `Aciertos: ${aciertos} | Fallos: ${fallos}`;
}

function seleccionarRespuesta(indiceRespuesta) {
  const actual = preguntas[indiceActual];
  if (indiceRespuesta === actual.correcta) {
    aciertos++;
    alert("¡Correcto!");
  } else {
    fallos++;
    alert(`Incorrecto. La respuesta correcta era: "${actual.opciones[actual.correcta]}"`);
  }

  if (aciertos >= 4) {
    alert("¡Felicidades! Has ganado el juego con 4 aciertos.");
    terminarJuego(true);
    return;
  }

  if (fallos >= 3) {
    alert("Has perdido el juego porque has fallado 3 veces.");
    terminarJuego(false);
    return;
  }

  indiceActual++;
  mostrarPregunta();
}

function terminarJuego(ganado) {
  categoriaDiv.textContent = "";
  preguntaDiv.textContent = "";
  opcionesDiv.innerHTML = "";
  if (ganado === undefined) {
    resultadoDiv.textContent = `Juego terminado. Aciertos: ${aciertos}, Fallos: ${fallos}`;
  } else if (ganado) {
    resultadoDiv.textContent = `¡Ganaste el juego con ${aciertos} aciertos y ${fallos} fallos!`;
  } else {
    resultadoDiv.textContent = `Perdiste el juego con ${aciertos} aciertos y ${fallos} fallos.`;
  }
}

// Iniciar el juego
mostrarPregunta();
