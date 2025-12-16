let errores = document.getElementById('error');;
window.addEventListener('load',function(){
    document.getElementById('shippingForm').addEventListener('submit',function(event){
        event.preventDefault();
        
        let seguimiento = document.getElementById('trackingId');
        let postal = document.getElementById('postalCode');
        let paquete = document.getElementById('weight');
        let servicio = document.getElementById('service');
        errores=""

        comprobar(seguimiento);
        comprobar(postal);
        comprobar(paquete);
        comprobar(servicio);
    })
})

function comprobar(input){
    let errores = document.getElementById('error');
    if(input.validity.valueMissing){
        errores.innerHTML=`❌Este campo es obligatorio`;
    }
    if(input.id.validity.patternMismatch){
        errores.innerHTML=`❌Formato incorrecto`;
    }
    else{
        errores.innerHTML=`ok`;
    }
}