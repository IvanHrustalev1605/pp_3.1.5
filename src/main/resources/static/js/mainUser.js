async function title() {
    let authUserEmail = document.querySelector('#auth-email');
    let authUserRoles = document.querySelector('#auth-roles');
    let res = await fetch("/userProfile");
    res.json().then(user => {
        authUserEmail.innerHTML = user.email;
        authUserRoles.innerHTML = user.role.map(x => " " + x.name.substr(5));
    });
}
async function getUserById() {
    $('#userById').show();
    let temp = '';
    let table = document.querySelector('#userById tbody');

    let response = await fetch(`/userProfile`);
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
             </tr>`;
        table.innerHTML = temp;
    })
}
$(document).ready(function () {
    title();
    getUserById();
});