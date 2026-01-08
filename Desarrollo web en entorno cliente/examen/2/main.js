let contenedor = document.getElementById('contenedor');
contenedor.style.display="grid";
contenedor.style.gridTemplateColumns="auto auto auto auto auto";

let sig = document.getElementById("siguiente");
let ant = document.getElementById("anterior");

window.addEventListener('load',function(){
    let limit = 10;
    let skipActual = 0;
        pideDatos(limit,skipActual);

    sig.addEventListener('click',function(){
        console.log('click')
        datos=[];
        contenedor="";
        limit+=10;
        skipActual+=10;
        pideDatos(limit,skipActual);
    });

    ant.addEventListener('click',function(){
    if(!limit<0){
        limit-=10;
        skipActual-=10;
        pideDatos(limit,skipActual);
    }
    });
    
})

async function pideDatos(limit,skipActual) {
  const response = await fetch(`https://dummyjson.com/products?limit=${limit}&skip=${skipActual}`);
  if (!response.ok) {
    throw `Error ${response.status} de la BBDD: ${response.statusText}`
  }
  const myData = await response.json();
    let datos=[];
    datos.push(...myData.products);
    console.log(datos);
    mostrarDatos(datos);
}

function mostrarDatos(datos){
    for(let i=0;i<datos.length;i++){
        console.log(i);
        contenedor.innerHTML+=`
        <div class="borde">
            <img src=${datos[i].thumbnail}>
            <h1>${datos[i].title}</h1>
            <p>${datos[i].price} €</p>
            <p>${datos[i].category}</p>
        </div>
        `
    }
    
/*     let borde = document.getElementsByClassName('borde');
    borde.style.border="1px solid black" */
    
}