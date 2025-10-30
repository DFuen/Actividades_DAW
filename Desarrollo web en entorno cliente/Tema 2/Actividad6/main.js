let patron=["00","101","ABC","HO"];
let count=0;
function numeroPatrones(texto){
    texto=texto.toLocaleUpperCase();
    for (const valor of patron) {
        let k=valor.length;
        for(let i=0;i<texto.length;i++){
            if(texto.substr(i,k)===valor){
                count++;
            }
        }
    }
    console.log("Hay",count,"patrones en el texto [",texto,"]");
}