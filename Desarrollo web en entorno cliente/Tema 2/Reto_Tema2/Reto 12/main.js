// Referencias DOM
const listaNumeros = document.getElementById("listaNumeros");
const listaMeses = document.getElementById("listaMeses");

const btnMostrarNum1 = document.getElementById("mostrarNum1");
const btnMostrarNum2 = document.getElementById("mostrarNum2");

const btnMostrarMes1 = document.getElementById("mostrarMes1");
const btnMostrarMes2 = document.getElementById("mostrarMes2");

// Función 1 - números del 1 al 100 excepto múltiplos de 3
function mostrarNumerosForContinue() {
  listaNumeros.innerHTML = "";
  for (let i = 1; i <= 100; i++) {
    if (i % 3 === 0) continue;
    const li = document.createElement("li");
    li.textContent = i;
    listaNumeros.appendChild(li);
  }
}

function mostrarNumerosWhileIf() {
  listaNumeros.innerHTML = "";
  let i = 1;
  while (i <= 100) {
    if (i % 3 !== 0) {
      const li = document.createElement("li");
      li.textContent = i;
      listaNumeros.appendChild(li);
    }
    i++;
  }
}

// Función 2 - meses del año excepto meses de verano (junio, julio, agosto)
const meses = [
  "Enero", "Febrero", "Marzo", "Abril", "Mayo",
  "Junio", "Julio", "Agosto", "Septiembre",
  "Octubre", "Noviembre", "Diciembre"
];
const mesesVerano = ["Junio", "Julio", "Agosto"];

function mostrarMesesFor() {
  listaMeses.innerHTML = "";
  for (let i = 0; i < meses.length; i++) {
    if (mesesVerano.includes(meses[i])) continue;
    const li = document.createElement("li");
    li.textContent = meses[i];
    listaMeses.appendChild(li);
  }
}

function mostrarMesesForOfContinue() {
  listaMeses.innerHTML = "";
  for (const mes of meses) {
    if (mesesVerano.includes(mes)) continue;
    const li = document.createElement("li");
    li.textContent = mes;
    listaMeses.appendChild(li);
  }
}

// Eventos botones
btnMostrarNum1.addEventListener("click", mostrarNumerosForContinue);
btnMostrarNum2.addEventListener("click", mostrarNumerosWhileIf);

btnMostrarMes1.addEventListener("click", mostrarMesesFor);
btnMostrarMes2.addEventListener("click", mostrarMesesForOfContinue);
