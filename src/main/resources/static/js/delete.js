async function deleteUserById(id) {
    $('#modalDeleteWindow').modal('show');
    let userById = await fetch(`/allUsers/${id}`);
    let modalBody = document.querySelector('#modalBody')
    let modalFooter = document.querySelector('#modalFooter');
    modalFooter.innerHTML = `<button  class="btn btn-danger" id="deleteButton">Delete</button>`;
    userById.json()
        .then(user => {
            modalBody.innerHTML =
            `<form id="deleteForm" class="form-group text-center">
              <div class="row text-center">
                <p for="username">Username: </p>
                <input type="text" value="${user.username}" id="usernamedel" readonly>
              </div>
              <div class="row text-center">
                <p>Name: </p>
                <input type="text" value="${user.name}" id="namedel" readonly>
              </div>
              <div class="row text-center">
                <p>LastName: </p>
                <input type="text" value="${user.lastName}" id="lastNamedel" readonly>
              </div>
              <div class="row text-center">
                <p>age: </p>
                <input type="text" value="${user.age}" id="agedel" readonly>
              </div>
              <div class="row text-center">
                <p>email: </p>
                <input type="email" value="${user.email}" id="emaildel" readonly>
              </div>
            </form>`;
    })
    $('#deleteButton').on('click', async function () {
        let response = await fetch(`/user/${id}`, {
            head: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Referer': null
            },
            method: 'DELETE'
        })
        if (response.ok) {
            await getAllUsers()
            $('#modalDeleteWindow').modal('hide');
        } else {
            alert("ERROR")
        }
    })
}