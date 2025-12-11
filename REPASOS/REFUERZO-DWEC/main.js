document.addEventListener('DOMContentLoaded', function() {
    
    // --- (Asumo que tienes definida la función actualizarIcono fuera de esta función) ---
    // Si no la tienes, inclúyela donde esté el código de tu aplicación.
    function actualizarIcono(campoId, esValido) {
        // ... (Implementación de la función actualizarIcono) ...
        const icono = document.getElementById(`icono-${campoId}`);
        const inputElement = document.getElementById(campoId);
        if (!icono || !inputElement) return;

        icono.classList.remove('valido', 'invalido');
        inputElement.classList.remove('valido', 'invalido');

        if (esValido) {
            icono.innerHTML = '&#10003;'; // ✅
            icono.classList.add('valido');
            inputElement.classList.add('valido');
        } else {
            icono.innerHTML = '&#10007;'; // ❌
            icono.classList.add('invalido');
            inputElement.classList.add('invalido');
        }
    }
    // ---------------------------------------------------------------------------------

    const formulario = document.querySelector('form');
    const mensajeDiv = document.getElementById('mensaje-resumen');

    /* --- REGEX: DEFINICIÓN DE PATRONES (Asegurando ^ y $ para validación completa) --- */
    const regex_patrones = {
        id: /^[A-Z]{2}[^\w\s]{1}[0-9]{4}$/,
        nombre:/^[\S]{1,50}$/, // Corregido a un patrón más realista
        correo:/^[A-Za-z\S0-9-_.]{20}@[^\s]{20}\.[A-Za-z]{2,3}$/,
        edad:/^(?:[1][8-9]|[2-9]\d|\d{3,})$/,
        telef:/^\+[0-9]{2,3}-[0-9]{9}$/
    };

    const campos_validar = [
        {id: 'id',nombre: 'ID de Producto', regex: regex_patrones.id}, // Usé 'id' por consistencia
        {id: 'nombre',nombre: 'Nombre', regex: regex_patrones.nombre},
        {id: 'correo',nombre: 'Correo Electronico', regex: regex_patrones.correo},
        {id: 'edad',nombre: 'Edad', regex: regex_patrones.edad},
        {id: 'tlf',nombre: 'Telefono', regex: regex_patrones.telef}, // Usé 'tlf' por consistencia con tu HTML
    ];

    function validarFormulario(event){
        event.preventDefault(); // Detiene el envío y el reseteo de la página

        // Limpiamos el mensajeDiv antes de empezar
        mensajeDiv.innerHTML = ''; 
        
        let formularioEsValido = true;
        let erroresHTML = ''; // Inicializamos como vacío (acumulador)
        let primerErrorEncontrado = false;

        for(const campo of campos_validar){
            const elementoInput = document.getElementById(campo.id);
            if (!elementoInput) continue;
            
            let valor = elementoInput.value.trim(); // Limpiamos espacios laterales
            let esValido = true;
            let mensajeError = '';
            
            // 1. Comprobación de Obligatoriedad
            if(valor.length === 0){
                esValido = false;
                mensajeError = `El campo <strong>${campo.nombre}</strong> es obligatorio y no puede estar vacío.`;
            } 
            // 2. Comprobación de Formato (solo si no está vacío)
            else if(!campo.regex.test(valor)){
                esValido = false;
                mensajeError = `El campo <strong>${campo.nombre}</strong> no cumple el formato requerido.`;
            }

            // 3. Actualizar Feedback Visual (Icono y Borde)
            actualizarIcono(campo.id, esValido); 

            // 4. Gestión de Errores
            if(!esValido){
                formularioEsValido=false;
                
                // === CORRECCIÓN CRÍTICA AQUÍ ===
                // Usamos += para acumular la lista de errores.
                erroresHTML += `<li>${mensajeError}</li>`; 

                // Enfocar el primer error
                if(!primerErrorEncontrado){
                    elementoInput.focus();
                    primerErrorEncontrado = true;
                }
            }
        } // Fin del bucle for...of
        
        // 5. Mostrar el mensaje resumen final
        if(!formularioEsValido){
            mensajeDiv.style.color = 'red';
            // Construimos la lista completa y el encabezado
            mensajeDiv.innerHTML = `<p>Se encontraron los siguientes errores:</p><ul>${erroresHTML}</ul>`;
        } else {
            mensajeDiv.style.color = 'green';
            mensajeDiv.innerHTML = '✅ ¡Formulario validado correctamente! El formulario es completamente válido.';
        }
    }

    formulario.addEventListener('submit',validarFormulario);

});