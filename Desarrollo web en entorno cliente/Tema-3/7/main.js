// Obtenemos la fecha actual
const hoy = new Date();

// Definimos la fecha del fin del curso (22 de junio del mismo año)
const finCurso = new Date(hoy.getFullYear(), 5, 22); // ⚠️ Mes 5 = junio

// Si ya pasó el 22 de junio, usamos el año siguiente
if (hoy > finCurso) {
  finCurso.setFullYear(finCurso.getFullYear() + 1);
}

// Calculamos la diferencia en milisegundos
const diferencia = finCurso - hoy;

// Convertimos la diferencia a días (1 día = 1000*60*60*24 ms)
const diasRestantes = Math.ceil(diferencia / (1000 * 60 * 60 * 24));

// Mostramos el resultado en la página
document.write(`<h2>Quedan ${diasRestantes} días hasta el fin del curso (${finCurso.toLocaleDateString()})</h2>`);
