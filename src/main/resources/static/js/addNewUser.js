async function addUser() {

    let username = document.querySelector('#username').value.trim();
    let password = document.querySelector('#password').value.trim();
    let name = document.querySelector('#name').value.trim();
    let lastName = document.querySelector('#lastName').value.trim();
    let age = document.querySelector('#age').value.trim();
    let email = document.querySelector('#email').value.trim();
    let checkedRoles = () => {
        let array = []
        let options = document.querySelector('#roles').options
        for (let i = 0; i < options.length; i++) {
            if (options[i].selected) {
                // array.push(roleList[i])
                array.push({
                    id: options[i].value,
                    name: options[i].innerText
                })
            }
        }
        return array;
    }

    let user = {
        username: username,
        password: password,
        name: name,
        role: checkedRoles(),
        lastName: lastName,
        age: age,
        email: email
    }
    console.log(user.role)
        await fetch('/allUsers', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Referer': null
            },
            body: JSON.stringify(user)
    })
    await getAllUsers()
    $('#modalDeleteWindow').modal('hide');
}
