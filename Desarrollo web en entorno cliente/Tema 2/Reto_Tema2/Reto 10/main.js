// Lista de jugadores del equipo de baloncesto
const jugadores = [
  "Carlos Gómez",
  "Luis Fernández",
  "Antonio Ruiz",
  "Miguel Torres",
  "Pedro Sánchez",
  "Jorge López",
  "Juan Martínez",
  "David Navarro"
];

// 1. Usando bucle for clásico
const listaFor = document.getElementById("listaFor");

for (let i = 0; i < jugadores.length; i++) {
  const dorsal = (i + 1) * 3;
  const li = document.createElement("li");
  li.textContent = `Dorsal ${dorsal}: ${jugadores[i]}`;
  listaFor.appendChild(li);
}

// 2. Usando bucle for...in
const listaForIn = document.getElementById("listaForIn");

for (let index in jugadores) {
  const dorsal = (parseInt(index) + 1) * 3;
  const li = document.createElement("li");
  li.textContent = `Dorsal ${dorsal}: ${jugadores[index]}`;
  listaForIn.appendChild(li);
}

// 3. Usando forEach
const listaForEach = document.getElementById("listaForEach");

jugadores.forEach((nombre, i) => {
  const dorsal = (i + 1) * 3;
  const li = document.createElement("li");
  li.textContent = `Dorsal ${dorsal}: ${nombre}`;
  listaForEach.appendChild(li);
});
