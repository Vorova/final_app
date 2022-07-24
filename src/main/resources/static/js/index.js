const API = "/admin/api"

async function showEditModal(id) {
    let user = await getUser(id);

    document.getElementById("editId").value = user.id;
    document.getElementById("editUsername").value = user.username;
    document.getElementById("editEmail").value = user.email;

    let select = document.getElementById("editRoles");

    // Обнуление всех полей
    for (let i = 0; i < select.length; i++) {
        select[i].selected = false;
    }

    // Выделяем необходимые роли
    for (let j = 0; j < user.roles.length; j++) {
        for (let i = 0; i < select.length; i++) {
            if (select[i].value == user.roles[j].id) {
                select[i].selected = true;
            }
        }
    }
}

async function showDeleteModal(id) {
    let user = await getUser(id);

    document.getElementById("deleteId").value = user.id;
    document.getElementById("deleteUsername").value = user.username;
    document.getElementById("deleteEmail").value = user.email;

    let select = document.getElementById("deleteRoles");

    // Выделяем необходимые роли
    for (let j = 0; j < user.roles.length; j++) {
        for (let i = 0; i < select.length; i++) {
            if (select[i].value == user.roles[j].id) {
                select[i].selected = true;
            }
        }
    }
}

async function getUser(id) {
    try {
        let response = await fetch(API + "/getUser/" + id);
        return await response.json();
    } catch (error) {
        console.log(error);
        debugger
    }
}

async function editUser() {
    let editForm = document.getElementById("editForm");
    let formData = new FormData(editForm);
    let user = {
        id: formData.get('id'),
        username: formData.get('username'),
        email: formData.get('email'),
        roles: Array.from(document.getElementById("editRoles"))
            .filter(option => option.selected)
            .map(option => ({id: option.value, authority: option.text}))
    }

    fetch(API + "/updateUser", {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    })
    .then(() => {
        // Close modal
        $('#editModal .btn-close').click();

        // Update data
        let tdId = document.querySelector("[data-user-id=\"" + user.id + "\"]");
        let tdUsername = document.querySelector("[data-user-username=\"" + user.id + "\"]");
        let tdEmail = document.querySelector("[data-user-email=\"" + user.id + "\"]");
        let tdRoles = document.querySelector("[data-user-roles=\"" + user.id + "\"]");
        tdId.innerHTML = `${user.id}`;
        tdUsername.innerHTML = `${user.username}`;
        tdEmail.innerHTML = `${user.email}`;
        let tdRolesText = ``;
        user.roles.forEach(role => {
            tdRolesText += `<b><span>${role.authority} </span></b>`
        });
        tdRoles.innerHTML = tdRolesText;
    })

}

async function deleteUser() {
    let deleteForm = document.getElementById("deleteForm");
    let formData = new FormData(deleteForm);

    let id = formData.get('id')

    fetch(API + "/deleteUser", {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(id)
    })
    .then(() => {
        $('#deleteModal .btn-close').click();
        let tr = document.querySelector("[data-user=\"" + id + "\"]");
        tr.remove();
    })
}