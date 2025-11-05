const mejorOpcion=(kilometros,dinero)=>{
    var opciones=["UberX", "UberXL", "UberPlus", "UberBlack", "UberSUV"];
    var coste= [0.3,0.5,0.7,1,1.3];
    let costeTotal;
    var ind=0;

    /*RECORRE CADA COSTE Y LO MULTIPLICO POR LOS KILOMETROS */
    for(let i=0;i<5;i++){
        costeTotal=coste[i]*kilometros;
        if(costeTotal<=dinero){
            ind=i;
            /*SACO EL PRECIO QUE LE VA A COSTAR CON UNA VARIABLE*/
            var precio=costeTotal;
        }
    }
    var resultado="La mejor opcion es "+opciones[ind]+" que costaría "+precio;
    function mostrarResultado(resultado){
    const salida = document.getElementById("salida");
    salida.textContent = `${resultado}`;
    }
    mostrarResultado(resultado);
}



