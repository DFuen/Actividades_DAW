window.addEventListener('load', function () {
    this.document.querySelector('form').addEventListener('submit', function(event){
        event.preventDefault();
        fetchData();
    });
})

const fetchAllData = async (type) =>{
    const allData=[];
    let url;
    try {
        const response = await fetch(`https://www.swapi.tech/api/${type}/${id}`);
        if (!response.ok) {
            throw `Error ${response.status} de la API: ${response.statusText}`
        }
        const data = await response.json();
        console.log(data);
        }
    catch (e) {
    console.error(e);
    }
    allData = data.results;
    url = data.next;
    while (url !=null){
       try {
        const response = await fetch(url);
        if (!response.ok) {
            throw `Error ${response.status} de la API: ${response.statusText}`
        }
        const fetchData = await response.json();
        allData.concat(fetchAllData.results);
        url = fetchAllData.next;
    } catch (e) {
        console.error(e);
    }
    }
    const container = document.querySelector('div');
}


const fetchIDData = async (type, id) =>{
    try {
        const response = await fetch(`https://www.swapi.tech/api/${type}/${id}`);
        if (!response.ok) {
            throw `Error ${response.status} de la API: ${response.statusText}`
        }
        const data = await response.json();
        const container = document.querySelector('div');
        switch(type){
            case 'people':
                    container.innerHTML =`
                        <ul>
                            <li>${data.result.properties.name}</li>
                            <li>${data.result.properties.height}</li>
                            <li>${data.result.properties.mass}</li>
                        </ul>
                    `;
                break;
            case 'films':
                break;
            case 'planets':
                break;
            case 'starships':
                break;
            case 'species':
                break;
        }
    } catch (e) {
        console.error(e);
    }
}

function fetchData(){
    id = document.querySelector('input');
    const type = document.querySelector('select').value;
    id.value === null ? fetchAllData(type) : fetchIDData(type, id.value)
}