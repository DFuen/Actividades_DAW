// Después de 20 segundos, muestra la fecha actual
setTimeout(() => {
  const fechaActual = new Date();
  alert("La fecha actual es: " + fechaActual.toString());
}, 20000);