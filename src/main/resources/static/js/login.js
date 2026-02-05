// Call the dataTables jQuery plugin (CODIGO FRONTED).Creamos la funcion login que sera llamada en login.html,cuando el usuarios se registre
$(document).ready(function() {

});
async function login(){

    let datos = {};//estos datos se envian desde el body,son usados por getUserByCredentials(user)
    datos.email = document.getElementById(`txtEmail`).value;
    datos.password = document.getElementById(`txtPassword`).value;


    const request = await fetch('api/login', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(datos)

  });
  const response = await request.text();
  if(response != 'FAIL'){
    localStorage.token = response;//guardamos la informacion en el cliente
    localStorage.email = datos.email;
    window.location.href = 'users.html';
  }
    else {
    alert('Las credenciales son incorrectas..');
    }
}
