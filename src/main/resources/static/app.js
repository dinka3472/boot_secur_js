
setUsersList();
setUserInfo();


$('#editUserModal').on('show.bs.modal', function (event) {
    const button = $(event.relatedTarget); // Button that triggered the modal
    const id = button.data('id'); // Extract info from data-* attributes
    const modal = $(this);
    if (id) {
        $.get({
            url: "/get/" + id,
            success: (data) => {
                modal.find('#newId').val(data.id),
                    modal.find('#newFirstName').val(data.firstName),
                    modal.find('#newSecondName').val(data.secondName),
                    modal.find('#newAge').val(data.age),
                    modal.find('#newEmail').val(data.email),
                    modal.find('#newPassword').val(data.password),
                    modal.find('#newRoles').val(data.roles);
                let roles = data.roles;
                const select = document.getElementById("newRoles");
                const options = Array.from(select.options);
                roles.forEach(role => {
                    let optionToSelect = options.find(item =>item.text === role.name );
                    optionToSelect.selected = true;
                })
            },
            error: (err) => {
                alert(err);
                console.log(err)
            }
        });
    }
});

$('#deleteUserModal').on('show.bs.modal', function (event) {
    const button = $(event.relatedTarget); // Button that triggered the modal
    const id = button.data('id'); // Extract info from data-* attributes
    const modal = $(this);
    if (id) {
        $.get({
            url: "/get/" + id,
            success: (data) => {
                modal.find('#delId').val(data.id),
                    modal.find('#delFirstName').val(data.firstName),
                    modal.find('#delSecondName').val(data.secondName),
                    modal.find('#delAge').val(data.age),
                    modal.find('#delEmail').val(data.email),
                    modal.find('#delPassword').val(data.password);
                let roles = data.roles;
                const select = document.getElementById("delRoles");
                const options = Array.from(select.options);
                roles.forEach(role => {
                    let optionToSelect = options.find(item =>item.text === role.name );
                    optionToSelect.selected = true;
                })
            },
            error: (err) => {
                alert(err);
            }
        });
    }
});

$('#delete-user-button').click(function() {
    let modal = $('#deleteUserModal');
    const id = modal.find('#delId').val();
    $.ajax({
        url: '/' + id,
        type: 'DELETE',
        dataType: "html",
        success: (data) => {
            setUsersList();
        },
        error: (err) => {
            alert(err.message);
        },
    });
    $('#deleteUserModal').modal('hide');
    $('.modal-backdrop').hide();
    $("body").removeClass("modal-open");
    $('#deleteUserModal').attr('aria-hidden', true);
});

$("#edit-button").click(async function () {
    let modal = $('#editUserModal');
    let roles = modal.find('#newRoles').val();
    let roles1 = [];
    for (let i=0; i < roles.length; i++) {
        let role = {
            name: roles[i]
        };
        roles1.push(role);
    }

    let user = {
        id: modal.find('#newId').val(),
        firstName: modal.find('#newFirstName').val(),
        secondName: modal.find('#newSecondName').val(),
        age: modal.find('#newAge').val(),
        email: modal.find('#newEmail').val(),
        password:modal.find('#newPassword').val(),
        roles: roles1,
    };

    let response = await fetch(
        "/update",
        {
            headers: {
                'Accept': 'application/json', 'Content-Type': 'application/json'},
            method: "PUT",
            body: JSON.stringify(user),
        });

    if (response.ok) {
        await setUsersList();
    } else {
        console.log(response.message);
    }
    $('#editUserModal').modal('hide');
    $('.modal-backdrop').hide();
    $("body").removeClass("modal-open");
    $('#editUserModal').attr('aria-hidden', true);
});

$('#add-button').click(async function() {
    let form = $('#add-user-form');
    let roles = form.find('#addRoles').val();
    let roles1 = [];
    for (let i=0; i < roles.length; i++) {
        let role = {
            name: roles[i]
        };
        roles1.push(role);
    }
    let user = {
        firstName: form.find('#addFirstName').val(),
        secondName: form.find('#addSecondName').val(),
        age: form.find('#addAge').val(),
        email: form.find('#addEmail').val(),
        password:form.find('#addPassword').val(),
        roles: roles1,
    };
    let response = await fetch(
        "/save",
        {
            headers: {
                'Accept': 'application/json', 'Content-Type': 'application/json'},
            method: "POST",
            body: JSON.stringify(user),
        });

    if (response.ok) {
        await setUsersList();
        document.getElementById("add-user-form").reset();
        document.getElementById("tab_pane_allUsers").click();
        document.getElementById("admin-tab").click();
    } else {

        console.log(response.statusText);
    }

});



async function setUsersList() {
    let table = $('#table_all_users tbody');
    table.empty();
    let url = "/get/allUsers";
    let response = await fetch(url);
    if (response.ok) {
        let users = await response.json();
        users.forEach(user => {
            let textRoles = "";
            user.roles.forEach(role => {
                textRoles = textRoles + role.name + '\n';
            });
            let tableBody = `$(
                    <tr>
                    <td >${user.id}</td>
                    <td >${user.firstName}</td>
                    <td >${user.secondName}</td>
                    <td >${user.age}</td>
                    <td >${user.email}</td>
                    <td>
                    ${textRoles}
                    </td>
                    <td>
                        <button type="button" class="btn btn-primary" data-toggle="modal"
                                data-id="${user.id}" data-action="edit" data-target="#editUserModal">Edit
                        </button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger" data-id="${user.id}" data-action="delete"
                             data-toggle="modal" data-target="#deleteUserModal">Delete
                        </button>
                    </td>
                </tr>
                )`;
            table.append(tableBody);
        });
    }
    else {
        alert(response.message);
    }
}

async function setUserInfo() {
    let url = "/get/" + $('#user_id').data('url');
    let response = await fetch(url);
    if (response.ok) { // если HTTP-статус в диапазоне 200-299
        // получаем тело ответа (см. про этот метод ниже)
        let user = await response.json();
        let userRoles = user.roles;
        let textOfRoles = '';
        for (let role of Object.values(userRoles)) {
            textOfRoles += role.name + '\n';
        }

        $('.val_ID').text(user.id);
        $('.val_firstName').text(user.firstName);
        $('.val_secondName').text(user.secondName);
        $('.val_age').text(user.age);
        $('.val_email').text(user.email);
        $('.val_roles').text(textOfRoles);
        $('#title').text(user.email + ' with roles: ' + textOfRoles);

    } else {
        alert("Ошибка HTTP: " + response.message);
        console.log(response.message);
    }
}



