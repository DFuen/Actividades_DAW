let pokemonbuscados = [];
/*ANTES TIENE QUE CARGAR LA PAGINA COMPLETA*/
window.addEventListener('load', function () {
    /*CUANDO YA HA CARGADO TENEMOS UNA FUNCION DE SUBMIT AL FORMULARIO*/
    document.getElementById('formulario').addEventListener('submit', function (event) {
        event.preventDefault();
        /*NO SE RECARGA LA PAGINA (event.preventDefault) Y HACE LA FUNCION DE PEDIR DATOS*/
        pideDatos();
    })
    /*FUNCION DE LIMPIEZA FUERA DEL FORMULARIO*/
    this.document.getElementById('limpiar').addEventListener('click', function () {
        let content = document.getElementById('contenido');
        /*DEJAR EL CONTENIDO VACÍO*/
        content.innerHTML = '';
    })
})

/*FUNCION ASINCRONA DE CARGAR LOS DATOS DE LA API EN EL RESPONSE*/
async function pideDatos() {
    let nombre = document.getElementById('pokemon').value;
    let error = document.getElementById('error');

    if (pokemonbuscados.includes(nombre)) {
        error.innerHTML = `Error ya existe en la vista`;
    } else {
        try {
            error.innerHTML = ``;
            const response = await fetch(`https://pokeapi.co/api/v2/pokemon/${nombre}`);
            if (!response.ok) {
                error.innerHTML = `Error`;
                /*THROW MANDA AL CATCH Y NO SE EJECUTA EL RESTO DE CÓDIGO*/
                throw `Error ${response.status} de la BBDD: ${response.statusText}`
            }
            /*IMPORTANTE QUE SEA AWAIT*/
            const myData = await response.json();
            pokemonbuscados.push(nombre);
            mostrarDatos(myData);
        } catch (e) {
            console.log("ERROR GENERAL");
        }
    }
}

/*RECUPERA LOS DATOS Y LO MOSTRAMOS EN EL HTML*/
function mostrarDatos(myData) {

    let content = document.getElementById('contenido');
    /*CREA ELEMENTOS EN ESTE CASO ES UN <div> Y ENVUELVE TODO LO QUE LE DIGAMOS A CONTINUACIÓN*/
    let pokemon = document.createElement('div');

    /*CREAMOS EL HTML CON LOS DATOS*/
    /*LOS DATOS LOS COGEMOS DE LA API CON LA VARIABLE + LA RUTA (myData.ruta...)*/
    pokemon.innerHTML = `
    <img src=${myData.sprites.front_default}>
    <p>${myData.name}</p>
    <p>${myData.height}, ${myData.weight}kg</p>
    `;
    let tipos = document.createElement("p");
    tipos.innerHTML = `Tipos:`;
    myData.types.forEach(element => {
        tipos.innerHTML += " " + element.type.name;
    });
    pokemon.append(tipos);
    pokemon.addEventListener('mouseover', function () {
        pokemon.style.border = "1px solid black";
    });
    pokemon.addEventListener('mouseleave', function () {
        pokemon.style.border = "none";
    });
    content.style.display = "flex";
    content.append(pokemon);
}

/*
PRIMERO BUSCAR EL CONTENIDO DE LA API (SABER ELEMENTOS Y ESTRUCTURA)

ESTO ES UNA API DE BUSQUEDA
*/
