window.addEventListener('load',function(){
    document.getElementById('formulario').addEventListener('submit',function(event){
        event.preventDefault();

        const identificador = document.getElementById('id');
        const nombre = document.getElementById('nombre');
        const correo = document.getElementById('correo');
        const edad = document.getElementById('edad');
        const telefono = document.getElementById('telefono');

        /*VALIDAR CAMPOS*/
        verificar(identificador);
        verificar(nombre);
        verificar(correo);
        verificar(edad);
        verificar(telefono);

        if(verificar(identificador) && verificar(nombre) && verificar(correo) && verificar(edad) && verificar(telefono)){
            console.log("he entrado verificado")
        alert("Tu primo esta bien");
        }

    })
})

function verificar(input){
    console.log("he entrado ha verificar")
    let error = document.getElementById(`error-${input.id}`);
    if(input.validity.valueMissing){
        error.innerHTML="Error campo obligatorio";
        return false;
    }
    if(input.validity.patternMismatch){
        error.innerHTML=`Formato no valido, ejemplo valido =${input.placeholder}`;
        return false;
    }
    return true;
}