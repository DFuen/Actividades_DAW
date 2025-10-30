var vector1=[0,0,0,-1];
var vector2=[0,-1,-1,0];
var cueva=[vector1,vector2];
let vectorJuego1=[0,0,0,0];
let vectorJuego2=[0,0,0,0];
var jugador=[vectorJuego1,vectorJuego2]


function contandoMinas() {
        for(let i=0;i<cueva.length;i++){
            for(let j=0;j<jugador.length;j++){
                for(let k=0;k<jugador[j].length;k++){
                        let count=0;
                        if(cueva[i][k]==-1){
                            jugador[i][k]=-1;
                        }else{
                            if(i==0 && k<3){
                                if(cueva[i][k+1]==-1){
                                count++;
                                }
                                if(cueva[i+1][k]==-1){
                                count++;
                                }
                                if(cueva[i+1][k-1]==-1){
                                count++;
                                }
                                if(cueva[i+1][k+1]==-1){
                                count++;
                                }
                            }
                            if(i!=0 && k<3){
                                if(cueva[i][k+1]==-1){
                                count++;
                                }
                                if(cueva[i][k-1]==-1){
                                count++;
                                }
                                if(cueva[i-1][k]==-1){
                                count++;
                                }
                                if(cueva[i-1][k+1]==-1){
                                count++;
                                }
                                if(cueva[i-1][k-1]==-1){
                                count++;
                                }
                            }
                            if(k==3 && i==0){
                                    if(cueva[i][k-1]==-1){
                                    count++;
                                    }
                                    if(cueva[i+1][k-1]==-1){
                                    count++;
                                    }
                                    if(cueva[i+1][k]==-1){
                                    count++;
                                    }
                            }
                            if(k==3 && i==1){
                                    if(cueva[i][k-1]==-1){
                                    count++;
                                    }
                                    if(cueva[i-1][k-1]==-1){
                                    count++;
                                    }
                                    if(cueva[i-1][k]==-1){
                                    count++;
                                    }
                            }
                            jugador[i][k]=count;
                        }
                    }  
                }
            }
        console.log("resultado:",jugador);
        console.log("base:",cueva);
        }