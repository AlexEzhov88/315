$('#delete').on('show.bs.modal', ev => {
    let button = $(ev.relatedTarget);
    let id = button.data('id');
    showDeleteModal(id);
})

async function showDeleteModal(id) {
    let user = await getUser(id);
    let form = document.forms["formDeleteUser"];
    form.id.value = user.id;
    form.firstName.value = user.firstName;
    form.lastName.value = user.lastName;
    form.age.value = user.age;
    form.email.value = user.email;


    $('#rolesDeleteUser').empty();

    await fetch("http://localhost:8080/api/roles")
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                let selectedRole = false;
                for (let i = 0; i < user.roles.length; i++) {
                    if (user.roles[i].name === role.name) {
                        selectedRole = true;
                        break;
                    }
                }
                let el = document.createElement("option");
                el.text = role.name.substring(5);
                el.value = role.id;
                if (selectedRole) el.selected = true;
                $('#rolesDeleteUser')[0].appendChild(el);
            })
        });
}

async function getUser(id) {
    let url = "http://localhost:8080/api/admin/" + id;
    let response = await fetch(url);
    return await response.json();
}

// $('#delete').on('show.bs.modal', ev => { - при открытии модального окна с id="delete" срабатывает функция, которая принимает параметр ev.
// let button = $(ev.relatedTarget); - получаем кнопку, которая вызвала модальное окно.
// let id = button.data('id'); - получаем id пользователя, который указан в атрибуте data-id кнопки.
// showDeleteModal(id); - вызываем функцию showDeleteModal() и передаем ей id пользователя.
// async function showDeleteModal(id) { - объявляем асинхронную функцию showDeleteModal() с параметром id.
// let user = await getUser(id); - получаем пользователя по id, используя функцию getUser(), и ждем, пока запрос выполнится.
// let form = document.forms["formDeleteUser"]; - получаем форму с именем "formDeleteUser".
// form.id.value = user.id; - устанавливаем значение id пользователя в соответствующее поле формы.
// form.firstName.value = user.firstName; - устанавливаем значение firstName пользователя в соответствующее поле формы.
// form.lastName.value = user.lastName; - устанавливаем значение lastName пользователя в соответствующее поле формы.
// form.age.value = user.age; - устанавливаем значение age пользователя в соответствующее поле формы.
// form.email.value = user.email; - устанавливаем значение email пользователя в соответствующее поле формы.

// $('#rolesDeleteUser').empty();: Эта строка очищает все элементы в #rolesDeleteUser.
// await fetch("http://localhost:8080/api/roles"): Запрос на сервер, получающий все доступные роли пользователей.
// .then(res => res.json()): Преобразование полученного ответа в формат JSON.
// .then(roles => { ... }): Функция, которая выполняется после получения списка всех ролей и принимает список ролей в качестве аргумента.
// roles.forEach(role => { ... }): Итерируется по списку всех ролей и выполняет указанные действия для каждой роли.
// let selectedRole = false;: Создание локальной переменной selectedRole и инициализация ее значением false.
// for (let i = 0; i < user.roles.length; i++) { ... }: Итерируется по списку ролей пользователя и выполняет указанные действия для каждой роли.
// if (user.roles[i].name === role.name) { ... }: Проверяет, совпадает ли имя роли пользователя с именем текущей роли. Если да, то устанавливает значение selectedRole в true.
// let el = document.createElement("option");: Создание нового элемента option.
// el.text = role.name.substring(5);: Устанавливает значение текста элемента option равным имени роли, начиная с пятого символа.
// el.value = role.id;: Устанавливает значение атрибута value элемента option равным ID роли.
// if (selectedRole) el.selected = true;: Если selectedRole равно true, то устанавливает элемент option как выбранный.
// $('#rolesDeleteUser')[0].appendChild(el);: Добавляет новый элемент option в список ролей пользователя.
// async function getUser(id) { ... }: Асинхронная функция, которая получает данные пользователя по его ID. Она принимает id пользователя в качестве аргумента.
// let url = "http://localhost:8080/api/admin/" + id;: Формирует URL для запроса к API сервера, используя ID пользователя.
// let response = await fetch(url);: Отправляет запрос на сервер, используя URL и получает ответ.
// return await response.json();: Возвращает данные пользователя в формате JSON.
