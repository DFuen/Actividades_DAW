// Contenido del "archivo" guardado en una variable
const datos = `Cliente;Localidad;Cuota
Laura;Santander;50
Álvaro;Castro;50
Igor;Castro;60
Ivan;Santander;40
Mónica;Zamora;30
Javi;Bilbao;30
David;Bilbao;50
José Luis;Bilbao;60`;

// Convertimos el texto en un array de objetos
const clientes = datos
  .split("\n")
  .slice(1) // Quitamos la cabecera
  .map(linea => {
    const [Cliente, Localidad, Cuota] = linea.split(";");
    return { Cliente, Localidad, Cuota: parseInt(Cuota) };
  });

// Función para mostrar resultados en una tabla HTML
const mostrarTabla = (array) => {
  const contenedor = document.getElementById("resultado");
  if (array.length === 0) {
    contenedor.innerHTML = "<p>No se encontraron resultados.</p>";
    return;
  }

  const cabeceras = Object.keys(array[0]);
  const filas = array.map(obj => 
    `<tr>${cabeceras.map(c => `<td>${obj[c]}</td>`).join("")}</tr>`
  ).join("");

  contenedor.innerHTML = `
    <table border="1" cellpadding="5">
      <thead>
        <tr>${cabeceras.map(c => `<th>${c}</th>`).join("")}</tr>
      </thead>
      <tbody>${filas}</tbody>
    </table>`;
};

// Opción 1: Mostrar todos los usuarios
const mostrarTodos = () => {
  mostrarTabla(clientes);
};

// Opción 2: Filtrar por provincia (Localidad)
const filtrarPorProvincia = () => {
  const provincia = prompt("Introduce una provincia:");
  const resultado = clientes
    .filter(c => c.Localidad.toLowerCase() === provincia.toLowerCase())
    .map(c => ({ Cliente: c.Cliente, Cuota: c.Cuota }));
  mostrarTabla(resultado);
};

// Opción 3: Filtrar por cuota mayor o menor que un valor
const filtrarPorCuota = () => {
  const tipo = prompt("¿Quieres filtrar por 'mayor' o 'menor' que un valor?");
  const valor = parseInt(prompt("Introduce el valor numérico de la cuota:"));

  if (isNaN(valor)) {
    alert("Por favor, introduce un número válido.");
    return;
  }

  const resultado = clientes.filter(c => 
    tipo.toLowerCase() === "mayor" ? c.Cuota > valor : c.Cuota < valor
  );

  mostrarTabla(resultado);
};
