// ========================
// Clase Autor
// ========================
class Autor {
  constructor(nombre, nacionalidad) {
    this._nombre = nombre;
    this._nacionalidad = nacionalidad;
  }

  get nombre() {
    return this._nombre;
  }

  get nacionalidad() {
    return this._nacionalidad;
  }
}

// ========================
// Clase Libro
// ========================
class Libro {
  static contadorISBN = 1;

  constructor(titulo, autor, precio, genero, stock) {
    this._ISBN = Libro.contadorISBN++;
    this._titulo = titulo;
    this._autor = autor;
    this._precio = precio >= 0 ? precio : 0;
    this._genero = genero;
    this._stock = stock >= 0 ? stock : 0;
  }

  tieneStock() {
    return this._stock > 0;
  }

  get ISBN() { return this._ISBN; }
  get titulo() { return this._titulo; }
  get autor() { return this._autor; }
  get precio() { return this._precio; }
  get genero() { return this._genero; }
  get stock() { return this._stock; }

  set precio(valor) { this._precio = valor >= 0 ? valor : 0; }
  set stock(valor) { this._stock = valor >= 0 ? valor : 0; }

  info() {
    return `📗 [${this._ISBN}] "${this._titulo}" - ${this._autor.nombre} (${this._genero}) | ${this._precio}€ | Stock: ${this._stock}`;
  }
}

// ========================
// Clase Librería
// ========================
class Libreria {
  constructor() {
    this._libros = [];
    this._ganancias = 0;
  }

  agregar(libro) {
    if (!this._libros.some(l => l.ISBN === libro.ISBN)) {
      this._libros.push(libro);
    }
  }

  buscarPorTitulo(titulo) {
    return this._libros.find(l => l.titulo.toLowerCase() === titulo.toLowerCase());
  }

  filtrarPorAutor(nombreAutor) {
    return this._libros.filter(l => l.autor.nombre.toLowerCase() === nombreAutor.toLowerCase());
  }

  filtrarPorGenero(genero) {
    return this._libros.filter(l => l.genero.toLowerCase() === genero.toLowerCase());
  }

  buscarPorId(id) {
    return this._libros.find(l => l.ISBN === id);
  }

  comprarLibros(ids) {
    ids.forEach(id => {
      const libro = this.buscarPorId(id);
      if (libro && libro.tieneStock()) {
        libro.stock -= 1;
        this._ganancias += libro.precio;
      }
    });
  }

  obtenerGanancias() {
    return this._ganancias;
  }

  listarLibros() {
    return this._libros.map(l => l.info()).join('\n');
  }
}

// ========================
// Funciones de interfaz
// ========================
const libreria = new Libreria();

const resultadoDiv = document.getElementById("resultado");
const gananciasDiv = document.getElementById("ganancias");

// Agregar libro
function agregarLibro() {
  const titulo = document.getElementById("titulo").value;
  const autorNombre = document.getElementById("autor").value;
  const nacionalidad = document.getElementById("nacionalidad").value;
  const precio = parseFloat(document.getElementById("precio").value);
  const genero = document.getElementById("genero").value;
  const stock = parseInt(document.getElementById("stock").value);

  if (!titulo || !autorNombre || !genero || isNaN(precio) || isNaN(stock)) {
    alert("Por favor, completa todos los campos correctamente.");
    return;
  }

  const autor = new Autor(autorNombre, nacionalidad);
  const libro = new Libro(titulo, autor, precio, genero, stock);
  libreria.agregar(libro);

  resultadoDiv.textContent = `✅ Libro agregado:\n${libro.info()}`;
  limpiarCampos();
}

function limpiarCampos() {
  document.querySelectorAll("input").forEach(i => (i.value = ""));
}

// Buscar título
function buscarTitulo() {
  const titulo = document.getElementById("buscarTitulo").value;
  const libro = libreria.buscarPorTitulo(titulo);
  resultadoDiv.textContent = libro ? libro.info() : "❌ No encontrado.";
}

// Filtrar por autor
function filtrarAutor() {
  const autor = document.getElementById("buscarAutor").value;
  const resultados = libreria.filtrarPorAutor(autor);
  resultadoDiv.textContent = resultados.length
    ? resultados.map(l => l.info()).join("\n")
    : "❌ No hay libros de ese autor.";
}

// Filtrar por género
function filtrarGenero() {
  const genero = document.getElementById("buscarGenero").value;
  const resultados = libreria.filtrarPorGenero(genero);
  resultadoDiv.textContent = resultados.length
    ? resultados.map(l => l.info()).join("\n")
    : "❌ No hay libros de ese género.";
}

// Comprar libro
function comprarLibro() {
  const id = parseInt(document.getElementById("idCompra").value);
  const libro = libreria.buscarPorId(id);

  if (!libro) {
    resultadoDiv.textContent = "❌ No existe ese ISBN.";
    return;
  }

  if (!libro.tieneStock()) {
    resultadoDiv.textContent = "⚠️ No hay stock disponible.";
    return;
  }

  libreria.comprarLibros([id]);
  resultadoDiv.textContent = `💰 Compraste "${libro.titulo}" por ${libro.precio}€.\nStock restante: ${libro.stock}`;
}

// Mostrar todos los libros
function mostrarLibros() {
  resultadoDiv.textContent = libreria.listarLibros() || "No hay libros en la librería.";
}

// Mostrar ganancias
function mostrarGanancias() {
  gananciasDiv.textContent = `Ganancias totales: ${libreria.obtenerGanancias()} €`;
}
