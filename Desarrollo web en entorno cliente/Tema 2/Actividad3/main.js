function pares(numero) {
    if(numero>=2){
        console.log("Los numeros pares son:");
        for (let i=1;i<=numero;i++){
            if(i%2==0){
                console.log(i);
            } 
        }
    }else{
        console.log("El numero tiene que ser mayor o igual que 2");
    }
    
}