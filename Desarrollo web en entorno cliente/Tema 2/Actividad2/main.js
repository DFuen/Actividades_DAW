let pago=0.00;
function salario(horas,precio) {
    if(horas<=35){
        pago=horas*precio;
    }else{
        pago=((horas-35)*(precio*1.5))+(35*precio);
    }
    if(pago<=500)
        console.log("Estan libres de impuestos");
    else{
        if(pago>500 && pago<=900){
            pago=pago-(pago-((pago-500)*2.5))
        }
        else{
            pago=pago-((pago-900)-((pago-900)*4.5))
        }
    }
    console.log("Tu salario es:",pago,"€")
}