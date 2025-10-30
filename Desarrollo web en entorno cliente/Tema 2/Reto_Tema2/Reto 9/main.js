// main.js - Mostrar horas de 08:00 a 20:30 en intervalos de 30 minutos

const listaHoras = document.getElementById("listaHoras");

// Desde 8:00 (480 min) hasta 20:30 (1230 min)
for (let minutos = 480; minutos <= 1230; minutos += 30) {
  const hora = Math.floor(minutos / 60);
  const minuto = minutos % 60;

  // Formato con dos dígitos
  const horaTexto = hora.toString().padStart(2, '0');
  const minutoTexto = minuto.toString().padStart(2, '0');

  const item = document.createElement("li");
  item.textContent = `${horaTexto}:${minutoTexto}`;
  listaHoras.appendChild(item);
}
