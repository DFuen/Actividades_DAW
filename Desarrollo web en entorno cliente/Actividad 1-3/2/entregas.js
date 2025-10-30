const contarTiempo = async(tiempo) => {
    /*CREAMOS CONSTANTE PARA LAS 7 HORAS LIMITE */
    const limite=7*3600;
    var tiemp=tiempo;
    var total=0;
    /*CREO UN BUCLE FOR PARA SEPARAR POR HORAS,MINUTOS Y SEGUNDOS 
    Y HACER LOS CALCULOS PARA SABER EL TIEMPO QUE SE TARDARÁ*/
    for(let i=0;i<tiemp.length;i++){
        const formato=tiemp[i].split(":");
        let acc=0;
        let h = parseInt(formato[0]);
        let m = parseInt(formato[1]);
        let s = parseInt(formato[2]);
        acc=h*3600+m*60+s;
        total=acc+total;
    }
    /*CREAMOS CONSTANTE PARA SABER LA DIFERENCIA CON RESPECTO AL LIMITE */
    const diferencia_time=total-limite;

    let convDif=Math.abs(diferencia_time);
    let horas = String(Math.floor(convDif/3600)).padStart(2,"0");
    let minutos = String(Math.floor((convDif%3600)/60)).padStart(2,"0");
    let segundos = String(Math.floor(convDif%60)).padStart(2,"0");

    /*SI LA DIFERENCIA ES MENOR QUE EL LIMITE SALE ESTE MENSAJE */
    if(diferencia_time<0){
        return console.log("-"+`${horas}:${minutos}:${segundos}`);
    }
    /*SI ES MAYOR ESTE OTRO*/
    else{
        return  console.log(`${horas}:${minutos}:${segundos}`);
    }
}

/*  IA:60%
    PERSONAL:40% 
*/