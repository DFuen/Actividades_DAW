const calculateTime=(tiempo) => {
    const limite=7*3600;/*CONSTANTE PARA EL LÍMITE DE 7 HORAS */
    var total=0;

    for(let i=0;i<tiempo.length;i++){
        const formato=tiempo[i].split(":");
        /*CREO UN ACUMULADOR */
        let acc=0;
        /*PASO A LAS HORAS MINUTOS Y SEGUNDOS Y LOS PASO A ENTERO */
        let h = parseInt(formato[0]);
        let m = parseInt(formato[1]);
        let s = parseInt(formato[2]);
        /*CALCULO PARA SABER EL TIEMPO TOTAL*/
        acc=h*3600+m*60+s;
        total=acc+total;
    }

    const difTiempo=total-limite;/*DIFERENCIA AL LIMITE */

    /*HACEMOS LA CONVERSIONES PARA PODER MOSTRAR POR PANTALLA EL TIEMPO EN VALORES ABSOLUTOS*/
    let absTiempo=Math.abs(difTiempo);
    let horas = String(Math.floor(absTiempo/3600)).padStart(2,"0");
    let minutos = String(Math.floor((absTiempo%3600)/60)).padStart(2,"0");
    let segundos = String(Math.floor(absTiempo%60)).padStart(2,"0");


    /*DAMOS EL RESULTADO SEGUN SI ES NEGATIVO O POSITIVO SEGUN LA DIFERENCIA DE TIEMPO SEA NEGATIVA O POSITIVA */
    if(difTiempo<0){
        return console.log("-"+`${horas}:${minutos}:${segundos}`);
    }
    else{
        return  console.log(`${horas}:${minutos}:${segundos}`);
    }
}