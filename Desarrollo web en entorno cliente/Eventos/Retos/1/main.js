var ncartas=["0","1","2","3","4","5","6","7"];
var cartas=["img/baston.png","img/baston.png","img/baston.png","img/baston.png"];
let carta=[];
var contador=0;
let comprobador=false;

function comprobador(){
/*     console.log(contador) */
    if(contador>2){
        for(let k=0;k<ncartas.length;k++){
            if()
        }
        for(let i=0;i<carta.length;i++){
            console.log(carta[i])
            carta[i].src="img/reves.png"
        }
        contador=0;
        carta=[];
    }
}

function voltear(){
    this.src="img/baston.png";
    carta.push(this);
    contador++;
    comprobador();
}

document.getElementById("0").addEventListener("click",voltear);
document.getElementById("1").addEventListener("click",voltear);
document.getElementById("2").addEventListener("click",voltear);
document.getElementById("3").addEventListener("click",voltear);
document.getElementById("4").addEventListener("click",voltear);
document.getElementById("5").addEventListener("click",voltear);
document.getElementById("6").addEventListener("click",voltear);
document.getElementById("7").addEventListener("click",voltear);
