var currentUser;
async function updateUser(id) {
    $('#modalUpdateWindow').modal('show');
    let userById = await fetch(`/allUsers/${id}`);
    let modalBody = document.querySelector('#modalUpdateBody')
    let modalFooter = document.querySelector('#modalUpdateFooter');
    modalFooter.innerHTML = `<button  class="btn btn-info" id="updateButton">Update</button>`;
    await userById.json()
        .then(user => {
            currentUser = user;
            modalBody.innerHTML =
                `<form id="deleteForm" class="form-group text-center">
              <div class="row text-center">
                <p for="username">Username: </p>
                <input type="text" value="${user.username}" id="usernameup">
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
                  </select>
                </div>
            </form>`;

        })
        let roleSelect = document.querySelector('#rolesup');
        const roleIds = currentUser.role.map(x => x.id)
            setRoles.forEach(x => {
                const opt = document.createElement('option');
                opt.value = x.id;
                opt.text = x.name.substring(5);
                if(roleIds.includes(x.id)) {
                    opt.selected = true
                }
                roleSelect.append(opt)
            })
    //
    let checkedRoles = () => {
        let array = []
        let options = document.querySelector('#rolesup').options
        for (let i = 0; i < options.length; i++) {
            if (options[i].selected) {
                array.push({
                    id: options[i].value,
                    name: options[i].innerText
                })
            }
        }
        return array;
    }
    $('#updateButton').on('click', async function () {
        let username = document.querySelector('#usernameup').value.trim();
        let name = document.querySelector('#nameup').value.trim();
        let lastName = document.querySelector('#lastNameup').value.trim();
        let age = document.querySelector('#agedup').value.trim();
        let email = document.querySelector('#emailup').value.trim();
        let user = {
            username: username,
            name: name,
            role: checkedRoles(),
            lastName: lastName,
            age: age,
            email: email
        }
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