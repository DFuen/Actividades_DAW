/*CREAMOS EL PRIMER OBJETO DE FORMA LITERAL*/
var sandkill1 = {
    nombre: "Manolo",
    edad: 78,
    especialidad: "Conductor de pateras",

    toString: function(){
        return `
        <table border="1" cellpadding="5" cellspacing="0">
            <tr>
                <th>Propiedad</th>
                <th>Valor</th>
            </tr>
            <tr>
                <td>Nombre</td>
                <td>${this.nombre}</td>
            </tr>
            <tr>
                <td>Edad</td>
                <td>${this.edad}</td>
            </tr>
            <tr>
                <td>Especialidad</td>
                <td>${this.especialidad}</td>
            </tr>
        </table>
        `;
    }
};
/* SEGUNDA PERSONA CREADA COMO OBJETO*/
var sandkill2= new Object();
    sandkill2.nombre = "Jon";
    sandkill2.edad = 29;
    sandkill2.especialidad = "Porrero Profesional";
    sandkill2.toString= function(){
        return `
        <table border="1" cellpadding="5" cellspacing="0">
            <tr>
                <th>Propiedad</th>
                <th>Valor</th>
            </tr>
            <tr>
                <td>Nombre</td>
                <td>${this.nombre}</td>
            </tr>
            <tr>
                <td>Edad</td>
                <td>${this.edad}</td>
            </tr>
            <tr>
                <td>Especialidad</td>
                <td>${this.especialidad}</td>
            </tr>
        </table>
        `;
    }


/*CLASE Sandkill Y EL CONSTRUCTOR / METODO PARA SACAR POR PANTALLA EL RESULTADO CON toString*/
class sandkill{
    constructor(nombre,edad,especialidad){
        this.nombre=nombre;
        this.edad=edad;
        this.especialidad=especialidad;
    }
}
var sandkill3 = new sandkill("Paco",54,"Obrero Explotado");

function salida(){
return "Nombre: "+sandkill3.nombre+"<br>"+"Edad: "+sandkill3.edad+"<br>"+"Especialidad: "+sandkill3.especialidad;
}

let guardar= sandkill3.especialidad;

function cambios(){

    sandkill1.nacionalidad = "España";
    sandkill2.lenguajeFavorito = "JavaScript";

    sandkill2.toString=function(){
        return `
        <table border="1" cellpadding="5" cellspacing="0">
            <tr>
                <th>Propiedad</th>
                <th>Valor</th>
            </tr>
            <tr>
                <td>Nombre</td>
                <td>${sandkill2.nombre}</td>
            </tr>
            <tr>
                <td>LenguajeFavorito</td>
                <td>${sandkill2.lenguajeFavorito}</td>
            </tr>
        </table>
        <br>
        <table>
            <tr>
                <td>Se ha borrado de la tercera persona: </td>
                <td>${guardar}</td>
            </tr>
        </table>
        `;
       };
    delete sandkill3.especialidad;
    return sandkill2.toString();
}
