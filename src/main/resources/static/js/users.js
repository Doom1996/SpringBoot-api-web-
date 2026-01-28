// Call the dataTables jQuery plugin (CODIGO FRONTED)
$(document).ready(function() {//se ejecuta todo el codigo una vez que se carga la pagina(
    loadUsers();//llama a la funcion que tiene la logica de cargar los usuarios,espera respuesta del server(no bloquea la pagina web)
  $('#usuarios').DataTable();//Convierte la tabla HTML en una DataTable (paginación, búsqueda, orden)
});

async function loadUsers(){
  const request = await fetch('api/users', {//aqui se hace la request HTTP.Funcion asincrona.se comunica con el backend
    method: 'GET',//pide datos de la API.para listar usuarios,obtener informacion
    headers: {
      'Accept': 'application/json',//que datos se reciben y manejan
      'Content-Type': 'application/json'//datos que devuelve el server
    },
    //body: JSON.stringify({a: 1, b: 'Textual content'}).se usa para POST
  });//backend devolviendo un json y se guarda en usersJson.backend Coinciden en URL y método HTTP, que es lo que hace que la comunicación funcione.
  const usersJson = await request.json();//devuelve un object Response y lo convierte a un json
let listaHtml = "";
for(let user of usersJson){//aqui se esta accediendo a un JSON se accede a propiedades.se llama cuando ocurre una accion del usuario.Construcción dinámica del HTML
    let buttonDelete = `
        <a href="#"
            onclick="deleteUser(${user.id})"
            class="btn btn-danger btn-circle">
            <i class="fas fa-trash"></i>
        </a>
    `;//llamo a la funcion deleteUser(id) cuando swe hace click en el boton <a> se ejecuta la funcion."#" el navagador no navega a otra pagina

    let phone = user.phone == null ? '-' : user.phone;
    let userHtml = `
   <tr>
       <td>${user.id}</td
       <td>${user.name}</td>
       <td>${user.lastName}</td>
       <td>${user.phone}</td>
       <td>${buttonDelete}</td>
   </tr>
   `;//esto es para cargar el usuario en la tabla con datos dinamicos.como se mostrara la tabla de usuarios


  listaHtml += userHtml;
}
  document.querySelector('#usuarios tbody').outerHTML = listaHtml;//codigo probado en consola de Firefox:Busca un nodo del DOM que cumpla exactamente esto.inserta el HTML en la tabla.esto ocurre en memoria ,no cambia el HTML
}

async function deleteUser(id){
    if(!confirm("¿Desea eliminar el usuario?")){//confirm devuelve true o false
        return;
    }
     const request = await fetch('api/users/' + id , {//podria ser`api/users/${id}`.se comunica con el backend
        method: 'DELETE',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
      });
     //location.reload();
     const message = await request.text();//no se necesita si el backend devuelve void(create,delete,update)
     console.log(message);
}