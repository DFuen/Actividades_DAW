// intervalo15.js
// Mostrar horas desde 15:00 hasta 17:00 cada 15 minutos

for (let minutos = 15 * 60; minutos <= 17 * 60; minutos += 15) {
  const hora = Math.floor(minutos / 60);
  const minuto = minutos % 60;

  // Formatear con dos dígitos
  const horaStr = hora.toString().padStart(2, '0');
  const minutoStr = minuto.toString().padStart(2, '0');

  console.log(`${horaStr}:${minutoStr}`);
}
