$(async function () {
    await getAllUsers();
});
const userFetch = {
    getAllUsers: async () => await fetch("/allUsers"),
}
async function title() {
    let authUserEmail = document.querySelector('#auth-email');
    let authUserRoles = document.querySelector('#auth-roles');
    let res = await fetch("/user");
    res.json().then(user => {
        authUserEmail.innerHTML = user.email;
        authUserRoles.innerHTML = user.role.map(x => " " + x.name.substr(5));
    });
}
async function getAllUsers() {
    $('#userById').hide();
    $('#allUsers').show();
    $('#add-form').hide();
    let temp = '';
    const table = document.querySelector('#allUsers tbody');
    await userFetch.getAllUsers()
        .then(r => r.json())
        .then(users => {
            users.forEach(user => {
                temp += `
            <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.name}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td><button id="more-about-user" class="btn btn-success btn-sm" type="button" data-id="${user.id}" onclick="getUserById(this.getAttribute('data-id'))"  data-target="#userById" data-action="get">More</button></td>
                    <td>${user.role.map(x => " " + x.name.substr(5))}</td>
                      <td>
                        <button type="button" data-userid="${user.id}" data-action="edit" onclick="updateUser(this.getAttribute('data-userid'))"  class="btn btn-info btn-sm"
                            className data-toggle="modal" data-target="#editModal">Edit</button>
                    </td>
                    <td>
                        <button type="button" data-userid="${user.id}" data-action="delete" onclick="deleteUserById(this.getAttribute('data-userid'))" id="delete" class="btn btn-danger btn-sm"
                            className data-toggle="modal" data-target="#modalDeleteWindow">Delete</button>
                    </td>
             </tr>`;

            })
            table.innerHTML = temp;
        })
}
async function getUserById(id) {
    $('#userById').show();
    $('#allUsers').hide();
    $('#add-form').hide();

    let temp = '';
    let table = document.querySelector('#userById tbody');

    let response = await fetch(`/allUsers/${id}`);
    response.json().then(user => {
        temp = `
            <tr>
                <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.name}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.role.map(x => " " + x.name.substr(5))}</td>
             </tr>
                <button sec:authorize="hasRole('ROLE_ADMIN')"
                        id="back-to-all-users"
                        class="btn btn-info"
                        type="button"
                        onclick="getAllUsers()"  data-target="#allUsers" data-action="get">Back to all users</button>`;
        table.innerHTML = temp;
    })
}

var setRoles;
async function pechenka() {
      fetch("/roles").then(res => res.json()).then(roles => {
        setRoles = roles
    });
}

async function getAllRoles() {
    let roleSelect = document.querySelector('#roles')
    let setRoles = await fetch("/roles");
         await setRoles.json().then(r => {
        r.forEach(role => {
            let option = document.createElement("option");
            roleSelect.append(option);
            option.value = role.id;
            option.text = role.name.substring(5);
        })
    });
}
function addUserForm() {
    $('#allUsers').hide();
    $('#add-form').show();
}
$(document).ready(function () {
    pechenka();
    getAllUsers();
    getAllRoles();
    title();
    $('#add-form').hide();
});

function loadModalWindow() {
    $('#modalDeleteWindow').modal('show');
}