const btnIniciar = document.getElementById('btnIniciar');

btnIniciar.addEventListener('click', () => {
  let horaInicio, horaFin, intervalo;

  // Función para validar hora con formato HH:mm
  function validarHora(horaStr) {
    if (!horaStr.includes(':')) return false;

    const [h, m] = horaStr.split(':');

    // Comprobar si h y m son enteros y están en rango
    const hora = parseInt(h, 10);
    const minuto = parseInt(m, 10);

    if (
      Number.isNaN(hora) || Number.isNaN(minuto) ||
      hora < 0 || hora > 24 ||
      minuto < 0 || minuto > 59
    ) {
      return false;
    }

    // Si hora es 24, minutos solo pueden ser 0
    if (hora === 24 && minuto !== 0) return false;

    return { hora, minuto };
  }

  // Función para pedir la hora y validar
  function pedirHora(mensaje) {
    while (true) {
      const entrada = prompt(mensaje);
      if (entrada === null) {
        alert("Has cancelado la entrada. El programa finalizará.");
        return null;
      }
      const validacion = validarHora(entrada.trim());
      if (validacion) return validacion;
      alert("Hora inválida. Debe tener formato HH:mm, horas 0-24, minutos 0-59.");
    }
  }

  // Función para pedir intervalo en minutos
  function pedirIntervalo() {
    while (true) {
      const entrada = prompt("Introduce el intervalo de minutos (entero > 0):");
      if (entrada === null) {
        alert("Has cancelado la entrada. El programa finalizará.");
        return null;
      }
      const val = parseInt(entrada.trim(), 10);
      if (!Number.isNaN(val) && val > 0) return val;
      alert("Intervalo inválido. Debe ser un número entero mayor que 0.");
    }
  }

  // Pedir datos al usuario
  horaInicio = pedirHora("Introduce la hora de inicio (HH:mm):");
  if (!horaInicio) return;
  horaFin = pedirHora("Introduce la hora de finalización (HH:mm):");
  if (!horaFin) return;
  intervalo = pedirIntervalo();
  if (!intervalo) return;

  // Convertir horas a minutos totales para comparar
  const inicioTotal = horaInicio.hora * 60 + horaInicio.minuto;
  const finTotal = horaFin.hora * 60 + horaFin.minuto;

  if (finTotal < inicioTotal) {
    alert("La hora de finalización debe ser igual o mayor que la hora de inicio.");
    return;
  }

  console.clear();
  console.log(`Mostrando horas desde ${horaInicio.hora.toString().padStart(2,'0')}:${horaInicio.minuto.toString().padStart(2,'0')} hasta ${horaFin.hora.toString().padStart(2,'0')}:${horaFin.minuto.toString().padStart(2,'0')} en intervalos de ${intervalo} minutos:`);

  for (let minutos = inicioTotal; minutos <= finTotal; minutos += intervalo) {
    const h = Math.floor(minutos / 60);
    const m = minutos % 60;
    const horaStr = h.toString().padStart(2, '0');
    const minStr = m.toString().padStart(2, '0');
    console.log(`${horaStr}:${minStr}`);
  }
});
