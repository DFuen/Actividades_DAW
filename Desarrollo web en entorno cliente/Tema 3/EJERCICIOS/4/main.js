// Función para comprobar si un número es primo
const esPrimo = num => {
  if (num < 2) return false;
  for (let i = 2; i <= Math.sqrt(num); i++) {
    if (num % i === 0) return false;
  }
  return true;
};

// Función para comprobar si un número es palíndromo
const esPalindromo = num => {
  const str = num.toString();
  return str === str.split('').reverse().join('');
};

// Array donde guardaremos los números que son primos y palíndromos
const primosPalindromos = [];

// Recorremos del 1 al 100000
for (let i = 1; i <= 100000; i++) {
  if (esPrimo(i) && esPalindromo(i)) {
    primosPalindromos.push(i);
  }
}

// Mostramos el resultado
console.log("Números que son primos y palíndromos entre 1 y 100000:");
console.log(primosPalindromos);
