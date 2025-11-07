// ============================
// VARIABLES GLOBALES
// ============================
let paises = ["España", "Francia", "Alemania", "Italia", "Portugal"];

// Elemento donde mostraremos resultados
const salida = document.getElementById("resultado");

// ============================
// FUNCIONES DE GESTIÓN
// ============================

// 1️⃣ Mostrar número de países
const mostrarNumero = arr => {
  salida.innerHTML += `<h2>Número de países:</h2><p>${arr.length}</p>`;
};

// 2️⃣ Mostrar todos los países
const mostrarPaises = (arr, modo = "normal") => {
  let lista;
  if (modo === "inverso") {
    lista = [...arr].reverse();
  } else if (modo === "alfabetico") {
    lista = [...arr].sort();
  } else {
    lista = arr;
  }

  salida.innerHTML += `<h2>Listado de países (${modo}):</h2><p>${lista.join(", ")}</p>`;
};

// 3️⃣ Añadir un país (al principio o final)
const anadirPais = (arr, pais, posicion) => {
  if (posicion === "inicio") {
    arr.unshift(pais);
    salida.innerHTML += `<h2>País añadido al principio:</h2><p>${pais}</p>`;
  } else {
    arr.push(pais);
    salida.innerHTML += `<h2>País añadido al final:</h2><p>${pais}</p>`;
  }
};

// 4️⃣ Borrar un país (al principio o final)
const borrarPais = (arr, posicion) => {
  let eliminado;
  if (posicion === "inicio") {
    eliminado = arr.shift();
  } else {
    eliminado = arr.pop();
  }
  salida.innerHTML += `<h2>País borrado (${posicion}):</h2><p>${eliminado}</p>`;
};

// 5️⃣ Consultar un país por posición
const consultarPorPosicion = (arr, pos) => {
  if (pos < 0 || pos >= arr.length) {
    salida.innerHTML += `<h2>Consulta por posición:</h2><p>Posición no válida</p>`;
  } else {
    salida.innerHTML += `<h2>Consulta por posición ${pos}:</h2><p>${arr[pos]}</p>`;
  }
};

// 6️⃣ Consultar posición por nombre
const consultarPorNombre = (arr, nombre) => {
  const pos = arr.indexOf(nombre);
  if (pos === -1) {
    salida.innerHTML += `<h2>Consulta por nombre:</h2><p>${nombre} no se encuentra en la lista</p>`;
  } else {
    salida.innerHTML += `<h2>Consulta por nombre:</h2><p>${nombre} está en la posición ${pos}</p>`;
  }
};

// 7️⃣ Mostrar países en un intervalo
const mostrarIntervalo = (arr, inicio, fin) => {
  const intervalo = arr.slice(inicio, fin + 1);
  salida.innerHTML += `<h2>Intervalo ${inicio}-${fin}:</h2><p>${intervalo.join(", ")}</p>`;
};

// ============================
// MENÚ PRINCIPAL
// ============================

const menu = `
Elige una opción:
1 - Mostrar número de países
2 - Mostrar listado de países
3 - Mostrar intervalo de países
4 - Añadir un país
5 - Borrar un país
6 - Consultar un país
`;

// ============================
// EJECUCIÓN
// ============================

const opcion = prompt(menu);

switch (opcion) {
  case "1":
    mostrarNumero(paises);
    break;

  case "2":
    const tipo = prompt("¿Cómo quieres mostrar los países? (normal / inverso / alfabetico)");
    mostrarPaises(paises, tipo);
    break;

  case "3":
    const intervalo = prompt("Introduce el intervalo (inicio-fin):");
    const [inicio, fin] = intervalo.split("-").map(Number);
    mostrarIntervalo(paises, inicio, fin);
    break;

  case "4":
    const paisNuevo = prompt("Introduce el nombre del país a añadir:");
    const donde = prompt("¿Quieres añadirlo al inicio o al final?");
    anadirPais(paises, paisNuevo, donde.toLowerCase());
    break;

  case "5":
    const dondeBorrar = prompt("¿Quieres borrar al inicio o al final?");
    borrarPais(paises, dondeBorrar.toLowerCase());
    break;

  case "6":
    const tipoConsulta = prompt("¿Quieres consultar por posición o por nombre?");
    if (tipoConsulta.toLowerCase() === "posición" || tipoConsulta.toLowerCase() === "posicion") {
      const pos = parseInt(prompt("Introduce la posición:"));
      consultarPorPosicion(paises, pos);
    } else {
      const nombre = prompt("Introduce el nombre del país:");
      consultarPorNombre(paises, nombre);
    }
    break;

  default:
    alert("Opción no válida");
}
