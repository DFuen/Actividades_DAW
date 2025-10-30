var letra=prompt("Dime una letra de la A a la Z").toUpperCase();
var indic;
var resto;
    if(letra==="U" || letra==="I" || letra==="Ñ" || letra==="O"){
        alert("ESTAS LETRAS ESTAN EXCLUIDAS DE DNI")
    }
function contarDNI(letra){
        var arrLetras =["T","R","W","A","G","M","Y","F","P","D","X","B","N","J","Z","S","Q","V","H","L","C","K","E"];
        let dnivalidos = [];
            for(let i=0;i<arrLetras.length;i++){
                if(letra===arrLetras[i]){
                    indic=i;
                    break
                }
            }
            for(let k=0;k<=999;k++){
                resto=k%23;
                if(resto===indic){
                    dnivalidos.push(k);
                }
            }

            alert(` Hay ${dnivalidos.length} DNI disponibles`)
            console.log(dnivalidos);

}