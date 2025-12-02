//Arrow function aquí para que funcionen

window.addEventListener('load', function () {
    this.document.querySelector('form').addEventListener('submit', checkID);
})
const RenderData = (data) =>{
    const conteiner = document.querySelector('div');
    conteiner.innerHTML =`
        <ul>
            <li>${data.result.properties.name}</li>
            <li>${data.result.properties.height}</li>
            <li>${data.result.properties.mass}</li>
        </ul>
    `;
}

const fetchAPI = async () => {
    try {
        const id= document.querySelector('input').value;
        const response = await fetch(`https://www.swapi.tech/api/people/${id}`);
        if (!response.ok) {
            throw `Error ${response.status} de la API: ${response.statusText}`
        }
        const myData = await response.json();
        console.log(myData)
        RenderData(myData)
        return myData;
    } catch (e) {
        console.error(e);
    }
}

function checkID(event) {
    //console.log("entra")
    event.preventDefault();//Evita la recarga el elemento
    const id = document.querySelector('input');
    if (id.checkValidity()) {
        fetchAPI();
    } else {
        const error = document.querySelector('span');
        if (id.validity.valueMissing) {
            error.innerHTML = `Este campo es obligatorio`;
        }
        if (id.validity.patternMismatch) {
            error.innerHTML = `El id debe tener 1 o 2 digitos`;
        }
    }
}