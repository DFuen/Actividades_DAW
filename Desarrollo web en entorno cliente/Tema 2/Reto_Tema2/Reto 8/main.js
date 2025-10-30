// main.js - Juego: Adivina el malware
// Preguntas (ordenadas)
const preguntas = [
  "¿Se puede borrar con antivirus?",
  "¿Se multiplica (se replica por sí mismo)?",
  "¿Se soluciona con una actualización del sistema?"
];

let respuestas = []; // array de booleanos: true = sí, false = no
let paso = 0;        // índice de la pregunta actual

// Elementos DOM
const questionText = document.getElementById("questionText");
const btnYes = document.getElementById("btnYes");
const btnNo = document.getElementById("btnNo");
const btnStart = document.getElementById("btnStart");
const btnReset = document.getElementById("btnReset");
const progress = document.getElementById("progress");
const resultArea = document.getElementById("resultArea");
const resultText = document.getElementById("resultText");
const explanation = document.getElementById("explanation");

// Iniciar juego
btnStart.addEventListener("click", () => {
  respuestas = [];
  paso = 0;
  resultArea.style.display = "none";
  btnStart.style.display = "none";
  btnReset.style.display = "inline-block";
  btnYes.style.display = "inline-block";
  btnNo.style.display = "inline-block";
  mostrarPregunta();
});

// Botones Sí / No
btnYes.addEventListener("click", () => responder(true));
btnNo.addEventListener("click", () => responder(false));

// Reiniciar
btnReset.addEventListener("click", reiniciar);

function mostrarPregunta() {
  if (paso < preguntas.length) {
    questionText.textContent = preguntas[paso];
    progress.textContent = `Pregunta ${paso + 1} de ${preguntas.length}`;
  } else {
    // todas respondidas: evaluar
    evaluarRespuestas();
  }
}

function responder(valorBooleano) {
  respuestas.push(Boolean(valorBooleano));
  paso++;
  if (paso < preguntas.length) {
    mostrarPregunta();
  } else {
    // ocultar botones mientras calcula
    btnYes.style.display = "none";
    btnNo.style.display = "none";
    questionText.textContent = "Analizando respuestas...";
    progress.textContent = "";
    // Evaluamos inmediatamente
    evaluarRespuestas();
  }
}

function evaluarRespuestas() {
  // respuestas = [canRemove, multiplies, fixedByUpdate]
  const [canRemove, multiplies, fixedByUpdate] = respuestas;

  // Lógica basada en el enunciado:
  // - Gusano: Se puede borrar con antivirus y se multiplica.
  // - Troyano: Se puede borrar con antivirus y no se multiplica.
  // - Bug: No se puede borrar con antivirus; se soluciona con una actualización.
  // - Exploit: No se puede borrar con antivirus; se soluciona con una actualización.
  // - Spam: No se puede borrar con antivirus ni con actualizaciones del sistema.

  let posibles = [];
  let razon = [];

  if (canRemove) {
    // Se puede borrar con antivirus -> Gusano o Troyano según multiplicación
    if (multiplies) {
      posibles.push("Gusano");
      razon.push("Se puede borrar con antivirus (Sí) y se multiplica (Sí) → coincide con Gusano.");
    } else {
      posibles.push("Troyano");
      razon.push("Se puede borrar con antivirus (Sí) y no se multiplica (No) → coincide con Troyano.");
    }
  } else {
    // No se puede borrar con antivirus
    if (fixedByUpdate) {
      // Según enunciado Bug y Exploit comparten características (No borrable; se soluciona con actualización).
      posibles.push("Bug");
      posibles.push("Exploit");
      razon.push("No se puede borrar con antivirus (No) y se soluciona con actualización (Sí) → coincide con Bug y Exploit según el enunciado.");
    } else {
      // No borrable y no solucionable con actualización -> Spam
      posibles.push("Spam");
      razon.push("No se puede borrar con antivirus (No) ni se soluciona con actualización (No) → coincide con Spam.");
    }
  }

  mostrarResultado(posibles, razon);
}

function mostrarResultado(posibles, razon) {
  resultArea.style.display = "block";
  resultText.innerHTML = posibles.map((p,i) => `${i+1}. <strong>${p}</strong>`).join("<br>");
  explanation.innerHTML = razon.join("<br><br>");
  questionText.textContent = "Juego finalizado. Mira el resultado abajo.";
  progress.textContent = `Respuestas: ${respuestas.map(r => r ? "Sí" : "No").join(" / ")}`;

  // Mostrar botones adecuados
  btnYes.style.display = "none";
  btnNo.style.display = "none";
  btnStart.style.display = "none";
  btnReset.style.display = "inline-block";

  // Log en consola para depuración
  console.group("Juego - análisis de respuestas");
  console.log("Respuestas (antivirus / multiplica / actualización):", respuestas);
  console.log("Posibles:", posibles);
  console.log("Explicación:", razon);
  console.groupEnd();
}

function reiniciar() {
  respuestas = [];
  paso = 0;
  questionText.textContent = "Pulsa Comenzar para iniciar el juego.";
  progress.textContent = "";
  resultArea.style.display = "none";
  btnStart.style.display = "inline-block";
  btnYes.style.display = "none";
  btnNo.style.display = "none";
  btnReset.style.display = "none";
}
