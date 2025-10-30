let conteo=0;
function card() {
    for(var i=0;i<arguments.length;i++){
         if(arguments[i]>=2 && arguments[i]<=6){
        conteo++;
    }
    else{
        if(arguments[i]>=7 && arguments[i]<=9){
            conteo=conteo+0;
        }
        else{
            if(arguments[i]==10 || arguments[i]==="Q"|| arguments[i]==="J"|| arguments[i]==="K"|| arguments[i]==="A"){
                        conteo--
            }
        }
    }
    }
    if(conteo >0){
    console.log(conteo,"Bet")
    }else{
        if(conteo <0)
            console.log(conteo,"Hold")
    }
}