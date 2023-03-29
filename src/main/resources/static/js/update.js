async function updateUser(id) {
    $('#modalUpdateWindow').modal('show');
    let userById = await fetch(`/allUsers/${id}`);
    let modalBody = document.querySelector('#modalUpdateBody')
    let modalFooter = document.querySelector('#modalUpdateFooter');
    modalFooter.innerHTML = `<button  class="btn btn-info" id="updateButton">Delete</button>`;
    let roleSelect = document.querySelector('#rolesup');
    userById.json()
        .then(user => {
            modalBody.innerHTML =
                `<form id="deleteForm" class="form-group text-center">
              <div class="row text-center">
                <p for="username">Username: </p>
                <input type="text" value="${user.username}" id="usernameup">
              </div>
               <div class="row text-center">
                <p for="username">Password: </p>
                <input type="password" value="${user.password}" id="passwordup">
              </div>
              <div class="row text-center">
                <p>Name: </p>
                <input type="text" value="${user.name}" id="nameup">
              </div>
              <div class="row text-center">
                <p>LastName: </p>
                <input type="text" value="${user.lastName}" id="lastNameup">
              </div>
              <div class="row text-center">
                <p>age: </p>
                <input type="text" value="${user.age}" id="agedup">
              </div>
              <div class="row text-center">
                <p>email: </p>
                <input type="email" value="${user.email}" id="emailup">
              </div>
               <div class="row text-center">
                  <p>Role: </p>
                  <select class="form-control" id="rolesup" required="required" multiple>
                        <option value="${user.role.map(x => x.id)}">${user.role.map(x => x.name)}</option>
                  </select>
                </div>
            </form>`;
        })
    userById.json()
        .then(user => {
            user.role.forEach(role => {
                roleSelect.innerHTML = `<option value="${role.id}">${role.name.substring(5)}</option>`
            })
        })
    let username = document.querySelector('#usernameup').value.trim();
    let password = document.querySelector('#passwordup').value.trim();
    let name = document.querySelector('#nameup').value.trim();
    let lastName = document.querySelector('#lastNameup').value.trim();
    let age = document.querySelector('#agedup').value.trim();
    let email = document.querySelector('#emailup').value.trim();
    let role = document.querySelector('#rolesup').value.trim();

    let user = {
        username: username,
        password: password,
        name: name,
        role: role,
        lastName: lastName,
        age: age,
        email: email
    }
    console.log(user)
    $('#updateButton').on('click', async function () {
        let response = await fetch(`/allUsers`, {
            head: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Referer': null
            },
            method: 'PUT',
            body: JSON.stringify(user)
        })
        if (response.ok) {
            await getAllUsers()
            $('#modalUpdateWindow').modal('hide');
        } else {
            alert("ERROR")
        }
    })
}