const viaje = async(length,money,cost) => {
    let coche=["UberX","UberXL","UberPlus","UberBlack","UberSUV"];
    let solucion="";
    /*CREAMOS UNA DEPURACION DE ERRORES*/
    if(length<0){
        solucion="Tiene que tener un valor positivo"
        return window.alert(solucion);
    }
    if (money<0) {
        solucion="Tiene que tener un valor positivo"
        return window.alert(solucion);
    }
    else{
        /*BUCLE FOR PARA IR POR COCHE Y PRECIO Y ASÍ SACAR EL MEJOR PRECIO*/
        for(let i=0;i<coche.length;i++){
        let valor=length*cost[i];
            if(valor<=money){
                solucion=coche[i];
            }
        }
        /*DEPURACION ERRROR SI NO HAY NINGUN COCHE CON EL PRESUPUESTO ACORDADO*/
        if(solucion===""){
        solucion="No puede viajar con ningun coche, ya que se supera el valor del trayecto"
        }
        return console.log(solucion);
    }
}
/*  IA:40%
    PERSONAL:60% 
*/