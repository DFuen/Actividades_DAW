// Función: Perímetro de triángulo equilátero
function perimetroEquilatero(lado) {
  const perimetro = 3 * lado;
  console.log(`Perímetro triángulo equilátero: ${perimetro}`);
}

// Función: Perímetro de triángulo isósceles
function perimetroIsosceles(ladoIgual, base) {
  const perimetro = 2 * ladoIgual + base;
  console.log(`Perímetro triángulo isósceles: ${perimetro}`);
}

// Función: Perímetro de triángulo escaleno
function perimetroEscaleno(lado1, lado2, lado3) {
  const perimetro = lado1 + lado2 + lado3;
  console.log(`Perímetro triángulo escaleno: ${perimetro}`);
}

// Nueva función: perimetroTriangulo con argumentos variables
function perimetroTriangulo(...lados) {
  const cantidad = lados.length;
  console.log(`Valores recibidos: ${lados.join(', ')}`);
  if (cantidad === 1) {
    perimetroEquilatero(lados[0]);
  } else if (cantidad === 2) {
    perimetroIsosceles(lados[0], lados[1]);
  } else if (cantidad === 3) {
    perimetroEscaleno(lados[0], lados[1], lados[2]);
  } else {
    console.log("Número de lados inválido para un triángulo.");
  }
}

// Función: Perímetro de cuadrado
function perimetroCuadrado(lado) {
  const perimetro = 4 * lado;
  console.log(`Perímetro cuadrado: ${perimetro}`);
}

// Función: Perímetro de rectángulo
function perimetroRectangulo(base, altura) {
  const perimetro = 2 * (base + altura);
  console.log(`Perímetro rectángulo: ${perimetro}`);
}

// Nueva función: perimetroParalelogramo con comportamiento condicional
function perimetroParalelogramo(...args) {
  if (args.length === 1) {
    perimetroCuadrado(args[0]);
  } else if (args.length === 2) {
    perimetroRectangulo(args[0], args[1]);
  } else {
    console.log("Número de parámetros no válido para paralelogramo.");
  }
}

// Reto adicional: área de polígono regular según número de lados
function areaPoligono(lados, longitudLado, apotema = null) {
  if (lados < 3) {
    console.log("No es un polígono válido.");
    return;
  }

  const perimetro = lados * longitudLado;

  if (apotema !== null) {
    const area = (perimetro * apotema) / 2;
    console.log(`Área del polígono regular (${lados} lados): ${area}`);
  } else {
    console.log("Apotema no proporcionado, no se puede calcular el área del polígono regular.");
  }
}
console.log("=== Pruebas perimetroTriangulo ===");
perimetroTriangulo(5);
perimetroTriangulo(5, 8);
perimetroTriangulo(3, 4, 5);
perimetroTriangulo(2, 3, 4, 5);

console.log("\n=== Pruebas perimetroParalelogramo ===");
perimetroParalelogramo(4);
perimetroParalelogramo(5, 10);
perimetroParalelogramo();

console.log("\n=== Pruebas areaPoligono ===");
areaPoligono(4, 6, 5);
areaPoligono(6, 5, 4.33)
areaPoligono(2, 5);
areaPoligono(5, 4);
