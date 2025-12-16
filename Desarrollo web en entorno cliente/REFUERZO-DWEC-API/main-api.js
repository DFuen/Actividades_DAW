let datos = [];
let personaje = [];
let localizacion = [];
let episodio = [];

let contenedor = document.getElementById('contenedor');

window.addEventListener('load',function(){
    document.getElementById('formulario').addEventListener('submit',function(event){
        event.preventDefault();
        let opcion = document.getElementById('opcion').value;
        let identificador = document.getElementById('identificador').value;
        if(identificador){
            pideDatosUnicos(opcion,identificador);
        }else{
            contenedor.innerHTML="";
            pideDatos(opcion);
        }
        
    })
})

async function pideDatosUnicos(opcion,identificador) {
    const response = await fetch(`https://rickandmortyapi.com/api/${opcion}/${identificador}`);
    if (!response.ok) {
        throw `Error ${response.status} de la BBDD: ${response.statusText}`
    }
    const dato = await response.json();
        if(opcion=="character"){
            if(!personaje.includes(dato.id)){
                personaje.push(dato.id);

                datos.push(dato);
                mostrarPersonajes(datos);
            }
        }
        else if(opcion=="location"){
            if(!localizacion.includes(dato.id)){
                localizacion.push(dato.id);

                datos.push(dato);
                mostrarPersonajes(datos);
            }
        }
        else if(opcion=="episode"){
            if(!episodio.includes(dato.id)){
                episodio.push(dato.id);

                datos.push(dato);
                mostrarPersonajes(datos);
            }
        }
   
}


async function pideDatos(opcion) {
    const response = await fetch(`https://rickandmortyapi.com/api/${opcion}`);
    if (!response.ok) {
        throw `Error ${response.status} de la BBDD: ${response.statusText}`
    }
    const myData = await response.json();
    let paginas = myData.info.pages;

    if(datos.length==0){
        agregarPersonajes(paginas,opcion);
    }
    else{
        datos=[];
        agregarPersonajes(paginas,opcion);
    }   
}


/*TODOS*/
async function agregarPersonajes(paginas,opcion){
    for(let i=1;i<=paginas;i++){
        const response = await fetch(`https://rickandmortyapi.com/api/${opcion}/?page=${i}`);
        if (!response.ok) {
        
        throw `Error ${response.status} de la BBDD: ${response.statusText}`
    }
    const data = await response.json();
    datos.push(...data.results);
    }
    console.log(datos)
    mostrarPersonajes(datos);
}


function mostrarPersonajes(datos){
    let contenidoHTML="";

    for(let i=0;i<datos.length;i++){

        contenidoHTML+=`<div class="borde">`
        
        contenidoHTML+=`
            <h4>${datos[i].name}</h4>
        `
        /*PERSONAJE*/
        if(datos[i].image){
        contenidoHTML+=`
        <img src=${datos[i].image}>
        `
        }

        if(datos[i].type){
            contenidoHTML+=`
            <p>${datos[i].type}</p>
            `
        }
        if(datos[i].species){
        contenidoHTML+=`
        <p>${datos[i].species}</p>
        `
        }

        /*LOCALIZACION*/
        if(datos[i].dimension && datos[i].dimension!="unknown"){
        contenidoHTML+=`
        <p>${datos[i].dimension}</p>
        `
        }
        /*EPISODIO*/
        if(datos[i].air_date){
        contenidoHTML+=`
        <p>${datos[i].air_date}</p>
        `
        }
       
        contenidoHTML+=`</div>`
    }
    contenedor.innerHTML=contenidoHTML;

    contenedor.style.display="grid";
    contenedor.style.gridTemplateColumns="auto auto auto";

    let stilocaja = document.getElementsByClassName('borde');
    for(let j=0;j<stilocaja.length;j++){
        stilocaja[j].style.border="1px solid black";
        stilocaja[j].style.margin="1%";
        stilocaja[j].style.padding="5%";
    }
}

