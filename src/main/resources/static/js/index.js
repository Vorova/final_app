const API = "/api/admin";
const API_USER = "/api/user";

async function showEditModal(id) {
    let user = await getUserById(id);

    console.log(user);

    document.getElementById("editId").value = user.id;
    document.getElementById("editFirstName").value = user.firstName;
    document.getElementById("editLastName").value = user.lastName;
    document.getElementById("editAge").value = user.age;
    document.getElementById("editEmail").value = user.email;

    // реализация корректировки прав
    let select = document.getElementById("editUserRoles");
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
    let user = await getUserById(id);

    document.getElementById("deleteId").value = user.id;
    document.getElementById("deleteFirstName").value = user.firstName;
    document.getElementById("deleteLastName").value = user.lastName;
    document.getElementById("deleteEmail").value = user.email;

    let select = document.getElementById("deleteUserRoles");

    // Выделяем необходимые роли
    for (let j = 0; j < user.roles.length; j++) {
        for (let i = 0; i < select.length; i++) {
            if (select[i].value == user.roles[j].id) {
                select[i].selected = true;
            }
        }
    }
}

async function getUserById(id) {
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
        firstName: formData.get('firstName'),
        lastName: formData.get('lastName'),
        age: formData.get('age'),
        email: formData.get('email'),
        password: formData.get('password'),
        roles: Array.from(document.getElementById("editUserRoles"))
            .filter(option => option.selected)
            .map(option => ({id: option.value}))
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
        getAllUsers().then();
    })

}

async function deleteUser() {
    let deleteForm = document.getElementById("deleteForm");
    let formData = new FormData(deleteForm);

    let id = formData.get('id')

    fetch(API + "/deleteUser/" + id, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    })
    .then(() => {
        // Close modal
        $('#deleteModal .btn-close').click();
        // Update data
        getAllUsers().then();
    })
}

const getUser = async () => {

    let response = await fetch(API_USER + "/getUserLogging");

    if (response.ok) {
        let user = await response.json();
        let headerEmail = document.getElementById("header_email");
        let headerRoles = document.getElementById("header_roles");
        let rolesText = "";
        headerEmail.innerHTML = `${user.email}`;
        user.roles.forEach(role => {
            rolesText += `${role.authority.slice(5)} `
        });
        headerRoles.innerHTML = rolesText;

        // Обновление 'about user'
        let tableBodyAboutUser = document.getElementById("tableBodyAboutUser");
        tableBodyAboutUser.innerHTML = "";
        let tr = document.createElement('tr');
        tr.innerHTML = `<td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>
                <b><span id="user_roles_about_user_${user.id}"></span></b>
            </td>`;
        tableBodyAboutUser.append(tr);
        // Добавляем роли
        let roles = document.getElementById("user_roles_about_user_" + user.id);
        let rolesHtml = "";
        user.roles.forEach(role => {
            rolesHtml += role.authority.slice(5) + " ";
        })
        roles.innerHTML = rolesHtml;

    } else {
        alert("Ошибка HTTP: " + response.status);
    }
}

const getAllUsers = async () => {

    let response = await fetch(API + "/getAllUsers");

    if (response.ok) {
        let users = await response.json();
        let tableBody = document.getElementById("tableBody");
        tableBody.innerHTML = "";
        users.forEach(user => {
            let tr = document.createElement('tr');
            tr.innerHTML = `<td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>
                <b><span id="user_roles_${user.id}"></span></b>
            </td>
            <td>
                <button type="button" class="btn btn-primary" data-user-id="${user.id}" data-bs-toggle="modal" data-bs-target="#editModal">Edit</button>
            </td>
            <td>
                <button type="button" class="btn btn-danger" data-user-id="${user.id}" data-bs-toggle="modal" data-bs-target="#deleteModal">Delete</button>
            </td>`;
            tableBody.append(tr);
            // Добавляем роли
            let roles = document.getElementById("user_roles_" + user.id);
            let rolesHtml = "";
            user.roles.forEach(role => {
                rolesHtml += role.authority.slice(5) + " ";
            })
            roles.innerHTML = rolesHtml;
        });
    } else {
        alert("Ошибка HTTP: " + response.status);
    }
}

const getAllRoles = async () => {
    let response = await fetch(API + "/getAllRoles");

    if (response.ok) {
        let roles = await response.json();
        let selectRoles = document.getElementById("newUserRoles");
        roles.forEach(role => {
            let option = document.createElement("option");
            option.setAttribute("value", role.id);
            option.innerHTML = role.authority.slice(5);
            selectRoles.append(option);
        });
    } else {
        alert("Ошибка HTTP: " + response.status);
    }
}

const getAllRolesByEdit = async () => {
    let response = await fetch(API + "/getAllRoles");

    if (response.ok) {
        let roles = await response.json();
        let selectRoles = document.getElementById("editUserRoles");
        roles.forEach(role => {
            let option = document.createElement("option");
            option.setAttribute("value", role.id);
            option.innerHTML = role.authority.slice(5);
            selectRoles.append(option);
        });
    } else {
        alert("Ошибка HTTP: " + response.status);
    }
}

const addNewUser = () => {
    let button = document.getElementById("buttonFormAddUser");
    button.addEventListener("click", (event) => {
        event.preventDefault();
        let newUserForm = document.getElementById("newUserForm");
        let data = new FormData(newUserForm);
        let user = {
            firstName: data.get('firstName'),
            lastName: data.get('lastName'),
            age: data.get('age'),
            email: data.get('email'),
            password: data.get('password'),
            roles: Array.from(document.getElementById("newUserRoles"))
                .filter(option => option.selected)
                .map(option => ({id: option.value}))
        }
        fetch(API + "/addUser", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        })
        .then(() => {
            // Update data
            getAllUsers().then();
            document.querySelector('a[href="#nav-user-table"]').click();
        })
    });
}

window.onload = () => {
    getUser().then();
    getAllUsers().then();
    getAllRoles().then();
    getAllRolesByEdit().then();
    addNewUser();

    $('#editModal').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget);
        let id = button.data('user-id');
        showEditModal(id).then();
    })

    $('#deleteModal').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget);
        let id = button.data('user-id');
        showDeleteModal(id).then();
    })
}
