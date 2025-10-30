let billetes="";
let cont=0;
function cambio(dinero) {
    console.log("Estos son los billetes:")
    do {
        //BILLETES 500
        if(dinero>=500){
            billetes="500"
            cont++;
            dinero-=500;
                if (dinero<500) {
                console.log(billetes,"€ -",cont) 
                }
        }else{
            let cont=0;
            //BILLETES 200
            if(dinero>=200){
            billetes="200"
            cont++;
            dinero-=200;
                if (dinero<200) {
                console.log(billetes,"€ -",cont) 
                }
            }else{
                let cont=0;
                //BILLETES 100
                if(dinero>=100){
                billetes="100"
                cont++;
                dinero-=100;
                    if (dinero<200) {
                    console.log(billetes,"€ -",cont) 
                    }
                }else{
                    let cont=0;
                    //BILLETES 50
                    if(dinero>=50){
                    billetes="50"
                    cont++;
                    dinero-=50;
                        if (dinero<50) {
                        console.log(billetes,"€ -",cont) 
                        }
                    }else{
                        let cont=0;
                        //BILLETES 20
                        if(dinero>=20){
                        billetes="20"
                        cont++;
                        dinero-=20;
                            if (dinero<20) {
                            console.log(billetes,"€ -",cont) 
                            }
                        }else{
                            let cont=0;
                            //BILLETES 10
                            if(dinero>=10){
                            billetes="10"
                            cont++;
                            dinero-=10;
                                if (dinero<10) {
                                console.log(billetes,"€ -",cont) 
                                }
                            }
                            else{
                               let cont=0;
                                //BILLETES 5
                                if(dinero>=5){
                                billetes="5"
                                cont++;
                                dinero-=10;
                                    if (dinero<5) {
                                    console.log(billetes,"€ -",cont) 
                                    } 
                                }
                            }
                        }
                    }
                }
            }
        }
    } while (dinero>0);
}