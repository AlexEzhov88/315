// Эта строка создаёт анонимную функцию, которая запускается после того,
//     как документ HTML будет полностью загружен. Функция асинхронная,
//     и в ней вызывается функция allUsers() при помощи оператора await. Это гарантирует,
//     что таблица пользователей будет отображаться только после того, как функция allUsers() успешно выполнится.

$(async function () {
    await allUsers();
});

// Здесь создаётся константа table, которая ссылается на таблицу HTML,
//     в которую будут добавляться строки с данными пользователей.
//     В данном случае используется jQuery для нахождения таблицы по её ID.

const table = $('#tbodyAllUserTable');


// Эта функция allUsers() асинхронная, и используется для получения данных о всех пользователях с сервера,
//     создания HTML-таблицы и добавления строк с данными в таблицу.

async function allUsers() {
    table.empty()
// table.empty() удаляет все строки из таблицы, чтобы не было повторений.
    fetch("http://localhost:8080/api/admin")
// fetch("http://localhost:8080/api/admin") делает запрос на сервер,
// используя метод fetch() и получает список всех пользователей.
        .then(res => res.json())
// .then(res => res.json()) обрабатывает ответ сервера в формате JSON.
        .then(data => {
            data.forEach(user => {
//.then(data => { ... }) извлекает данные из ответа и выполняет некоторые действия для каждого пользователя.
// Здесь используется метод forEach() для обхода всех пользователей.
                let tableWithUsers = `$(
// $(<tr>...</tr>) создаёт HTML-код для новой строки таблицы, содержащей данные о пользователе.
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>                         
                            <td>${user.age}</td>
                            <td>${user.email}</td>
                            <td>${user.roles.map(role => " " + role.name.substring(5))}</td>
                            <td>
                                <button type="button" class="btn btn-info" data-toggle="modal" id="buttonEdit"
                                data-action="edit" data-id="${user.id}" data-target="#edit">Edit</button>
                            </td>
                            <td>
                                <button type="button" class="btn btn-danger" data-toggle="modal" id="buttonDelete"
                                data-action="delete" data-id="${user.id}" data-target="#delete">Delete</button>
                            </td>
                        </tr>)`;
                table.append(tableWithUsers);
// table.append(tableWithUsers) добавляет эту строку в таблицу HTML.
            })
        })
}