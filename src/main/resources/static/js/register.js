// Call the dataTables jQuery plugin (CODIGO FRONTED)
$(document).ready(function() {//se ejecuta todo el codigo una vez que se carga la pagina(

});
//body es el contenido del html Donde van los datos importantes (formularios, JSON, archivos, etc.) El contenido principal que enviás al servidor
async function registerUser(){

    let datos = {};//las propiedades de aqui son las de User.Se lee los valores ingresados
    datos.name = document.getElementById(`txtName`).value;
    datos.lastName = document.getElementById(`txtLastName`).value;
    datos.email = document.getElementById(`txtEmail`).value;
    datos.password = document.getElementById(`txtPassword`).value;

    //validacion en frontend
    let repeatPass = document.getElementById(`txtRepeatPassword`).value;
    if(repeatPass != datos.password){
        alert("La contraseña ingresada es diferente!!!");
        return;
    }
    alert("El usuario se guardo correctamente!!");

    const request = await fetch('api/users', {
    method: 'POST',//se usa cuando se quiere insertar o crear una nueva entidad en la tabla de BD
    headers: {
      'Accept': 'application/json',//que datos se reciben y manejan
      'Content-Type': 'application/json'//datos que devuelve el server
    },
    body: JSON.stringify(datos)//recibe lo que hay en variable datos y lo convierte a str para que lo entienda html(Permite que el backend lo entienda)

  });//backend devolviendo un json y se guarda en usersJson.backend Coinciden en URL y método HTTP, que es lo que hace que la comunicación funcione.
  const usersJson = await request.json();//Contiene status HTTP (200, 404, 500…),headers,cuerpo HTTP
}