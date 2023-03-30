async function getAuthUser() {
    $('#userById').show();
    $('#allUsers').hide();
    let temp = '';
    let table = document.querySelector('#userById tbody');
    let result = await fetch("/userProfile");
    await result.json().then(autUser => {
        temp = `
            <tr>
                    <td>${autUser.id}</td>
                    <td>${autUser.username}</td>
                    <td>${autUser.name}</td>
                    <td>${autUser.lastName}</td>
                    <td>${autUser.age}</td>
                    <td>${autUser.email}</td>
                    <td>${autUser.role.map(x => " " + x.name.substr(5))}</td>
             </tr>`;
        table.innerHTML = temp;
    })
}
