// Call the dataTables jQuery plugin (CODIGO FRONTED)
$(document).ready(function() {//se ejecuta todo el codigo una vez que se carga la pagina(

});

async function registerUser(){

    let datos = {};//las propiedades de aqui son las de User
    datos.name = document.getElementById(`txtName`).value;
    datos.lastName = document.getElementById(`txtLastName`).value;
    datos.email = document.getElementById(`txtEmail`).value;
    datos.password = document.getElementById(`txtPassword`).value;

    let repeatPass = document.getElementById(`txtRepeatPassword`).value;

    if(repeatPass != datos.password){
        alert("La contraseña ingresada es diferente!!!");
        return;
    }
  const request = await fetch('api/users', {//aqui se hace la request HTTP.Funcion asincrona.se comunica con el backend
    method: 'POST',//pide datos de la API.para listar usuarios,obtener informacion
    headers: {
      'Accept': 'application/json',//que datos se reciben y manejan
      'Content-Type': 'application/json'//datos que devuelve el server
    },
    body: JSON.stringify(datos)//agarra cualquier objeto de js y lo convierte a un str de JSON
  });//backend devolviendo un json y se guarda en usersJson.backend Coinciden en URL y método HTTP, que es lo que hace que la comunicación funcione.
  const usersJson = await request.json();//devuelve un object Response y lo convierte a un json
}