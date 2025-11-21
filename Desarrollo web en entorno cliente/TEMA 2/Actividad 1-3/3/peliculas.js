// =======================================
// 📽️ SISTEMA DE ALQUILER DE PELÍCULAS
// =======================================

class SistemaAlquilerPeliculas {
  constructor(peliculas, clientes) {
    this.peliculas = peliculas;
    this.clientes = clientes;
    this.peliculasAlquiladas = [];
  }

  obtenerPeliculasPorDirector(directorNombre) {
    return this.peliculas
      .filter(p => p.director.toLowerCase() === directorNombre.toLowerCase())
      .map(p => p.titulo)
      .sort((a, b) => a.localeCompare(b, 'es', { sensitivity: 'base' }));
  }

  obtenerPeliculasDisponiblesPorGenero(genero) {
    return this.peliculas
      .filter(p => p.genero.includes(genero) && p.copiasDisponibles > 0)
      .map(p => p.titulo);
  }

  obtenerPeliculasMejorCalificadas(calificacionMinima) {
    return this.peliculas
      .filter(p => p.calificacion >= calificacionMinima)
      .sort((a, b) => b.calificacion - a.calificacion);
  }

  alquilarPelicula(clienteId, peliculaId) {
    const pelicula = this.peliculas.find(p => p.id === peliculaId);
    const cliente = this.clientes.find(c => c.id === clienteId);

    if (!pelicula || !cliente) return '❌ Cliente o película no encontrados';
    if (pelicula.copiasDisponibles <= 0) return '❌ No hay copias disponibles';
    if (cliente.peliculasAlquiladas.includes(peliculaId)) return '⚠️ El cliente ya alquiló esta película';

    pelicula.copiasDisponibles--;
    pelicula.alquiladoPor.push(clienteId);
    cliente.peliculasAlquiladas.push(peliculaId);
    this.peliculasAlquiladas.push(pelicula);

    return `✅ ${cliente.nombre} alquiló "${pelicula.titulo}".`;
  }

  devolverPelicula(clienteId, peliculaId) {
    const pelicula = this.peliculas.find(p => p.id === peliculaId);
    const cliente = this.clientes.find(c => c.id === clienteId);

    if (!pelicula || !cliente) return '❌ Cliente o película no encontrados';
    if (!cliente.peliculasAlquiladas.includes(peliculaId))
      return '⚠️ El cliente no tiene esta película alquilada';

    pelicula.copiasDisponibles++;
    pelicula.alquiladoPor = pelicula.alquiladoPor.filter(id => id !== clienteId);
    cliente.peliculasAlquiladas = cliente.peliculasAlquiladas.filter(id => id !== peliculaId);
    this.peliculasAlquiladas = this.peliculasAlquiladas.filter(p => p.id !== peliculaId);

    return `✅ ${cliente.nombre} devolvió "${pelicula.titulo}".`;
  }

  obtenerHistorialAlquilerCliente(clienteId) {
    const cliente = this.clientes.find(c => c.id === clienteId);
    if (!cliente) return '❌ Cliente no encontrado';

    return cliente.peliculasAlquiladas
      .map(id => this.peliculas.find(p => p.id === id)?.titulo)
      .filter(Boolean);
  }

  obtenerPeliculasMasPopulares() {
    return this.peliculas
      .slice()
      .sort((a, b) => b.alquiladoPor.length - a.alquiladoPor.length)
      .map(p => `${p.titulo} (${p.alquiladoPor.length} alquileres)`);
  }

  eliminarPelicula(peliculaId) {
    const pelicula = this.peliculas.find(p => p.id === peliculaId);
    if (!pelicula) return '❌ Película no encontrada';
    if (pelicula.alquiladoPor.length > 0)
      return '⚠️ No se puede eliminar: la película está alquilada';

    this.peliculas = this.peliculas.filter(p => p.id !== peliculaId);
    return `🗑️ Película "${pelicula.titulo}" eliminada del sistema.`;
  }
}

// =======================================
// 🎞️ DATOS DE EJEMPLO
// =======================================
const peliculas = [
  { id: 101, titulo: 'Inception', director: 'Christopher Nolan', genero: ['Acción', 'Ciencia Ficción'], calificacion: 8.8, anio: 2010, copiasDisponibles: 3, alquiladoPor: [] },
  { id: 102, titulo: 'Interstellar', director: 'Christopher Nolan', genero: ['Ciencia Ficción', 'Drama'], calificacion: 8.6, anio: 2014, copiasDisponibles: 2, alquiladoPor: [] },
  { id: 103, titulo: 'Titanic', director: 'James Cameron', genero: ['Romance', 'Drama'], calificacion: 7.8, anio: 1997, copiasDisponibles: 1, alquiladoPor: [] }
];

const clientes = [
  { id: 201, nombre: 'Ana Pérez', peliculasAlquiladas: [] },
  { id: 202, nombre: 'Luis Gómez', peliculasAlquiladas: [] }
];

// Instanciar el sistema
const sistema = new SistemaAlquilerPeliculas(peliculas, clientes);

// =======================================
// 🧠 Funciones para botones
// =======================================
function mostrarPeliculasDirector() {
  mostrarResultado('Películas de Nolan:', sistema.obtenerPeliculasPorDirector('Christopher Nolan'));
}

function alquilar() {
  mostrarResultado('Alquiler:', sistema.alquilarPelicula(201, 101));
}

function devolver() {
  mostrarResultado('Devolución:', sistema.devolverPelicula(201, 101));
}

function mostrarPopulares() {
  mostrarResultado('Más populares:', sistema.obtenerPeliculasMasPopulares());
}

function eliminarTitanic() {
  mostrarResultado('Eliminar:', sistema.eliminarPelicula(103));
}

function mostrarResultado(titulo, resultado) {
  const output = document.getElementById('output');
  output.textContent = `${titulo}\n${JSON.stringify(resultado, null, 2)}`;
}
/*  IA:100%
    PERSONAL:0% 
*/