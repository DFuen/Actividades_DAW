var cartas=["img/baston.png","img/baston.png","img/as_oro.png","img/copas.png",
            "img/copas.png","img/pica.png","img/as_oro.png","img/pica.png"];
let carta=[];
let cartas_aleatorias=[];
var contador=0;
let comprobar=false;
let aciertos=0;
let intentos=0;


function comprobador(){
    if(cartas[carta[0]] == cartas[carta[1]]){
        aciertos++;
        intentos++;
/*         console.log("aqui acierto"); */
        document.getElementById(carta[0]).removeEventListener("click",voltear);
        document.getElementById(carta[1]).removeEventListener("click",voltear);
        carta=[];
    }
    else{
        document.getElementById(carta[0]).src="img/reves.png";
        document.getElementById(carta[1]).src="img/reves.png";
        intentos++;
/*         console.log("aqui fallo"); */
        carta=[];
        contador=0;
    }
    document.getElementById("contendor_resultados").innerHTML="Aciertos: "+aciertos+" Intentos: "+intentos;
}

function voltear(){
    let id = this.id;
    this.src=cartas[id];

    contador++;
/*     console.log(contador); */
    carta.push(id);
/*     console.log(carta); */
    if(contador===2){
        contador=0;
        setTimeout(comprobador,500);
    }
}

document.getElementById("0").addEventListener("click",voltear);
document.getElementById("1").addEventListener("click",voltear);
document.getElementById("2").addEventListener("click",voltear);
document.getElementById("3").addEventListener("click",voltear);
document.getElementById("4").addEventListener("click",voltear);
document.getElementById("5").addEventListener("click",voltear);
document.getElementById("6").addEventListener("click",voltear);
document.getElementById("7").addEventListener("click",voltear);
