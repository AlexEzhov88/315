$(async function () {
    await newUser();
});

async function newUser() {
    await fetch("http://localhost:8080/api/roles")
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                let el = document.createElement("option");
                el.text = role.name.substring(5);
                el.value = role.id;
                $('#newUserRoles')[0].appendChild(el);
            })
        })

    const form = document.forms["formNewUser"];

    form.addEventListener('submit', addNewUser)

    function addNewUser(e) {
        e.preventDefault();
        let newUserRoles = [];
        for (let i = 0; i < form.roles.options.length; i++) {
            if (form.roles.options[i].selected) newUserRoles.push({
                id: form.roles.options[i].value,
                name: form.roles.options[i].name
            })
        }
        fetch("http://localhost:8080/api/admin", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                firstName: form.firstName.value,
                lastName: form.lastName.value,
                age: form.age.value,
                email: form.email.value,
                password: form.password.value,
                roles: newUserRoles
            })
        }).then(() => {
            form.reset();
            allUsers();
            $('#allUsersTable').click();
        })
    }

}

// $(async function () {...}): Это асинхронная функция, которая выполняется после того, как вся страница будет загружена. Она используется для запуска функции newUser().
//
// async function newUser() {...}: Это асинхронная функция, которая загружает и отображает список ролей в форме добавления нового пользователя. Затем она добавляет обработчик событий на отправку формы и определяет функцию addNewUser() для добавления нового пользователя.
//
// await fetch("http://localhost:8080/api/roles"): Эта строка загружает список ролей из API.
//
// .then(res => res.json()): Это метод, который преобразует ответ API в формат JSON.
//
// .then(roles => {...}): Это метод, который вызывается после успешного получения списка ролей. Он принимает массив объектов ролей и выполняет следующие действия:
//
// roles.forEach(role => {...}): Это метод перебора элементов массива ролей. Он принимает каждый элемент массива в качестве аргумента и выполняет следующие действия:
//
// let el = document.createElement("option");: Эта строка создает новый элемент option для списка ролей.
//
// el.text = role.name.substring(5);: Эта строка задает текст элемента option. Он устанавливается равным названию роли с отрезанными первыми пятью символами.
//
// el.value = role.id;: Эта строка задает значение элемента option. Оно устанавливается равным идентификатору роли.
//
// $('#newUserRoles')[0].appendChild(el);: Эта строка добавляет элемент option в список ролей в форме.
//
// const form = document.forms["formNewUser"];: Эта строка определяет форму добавления нового пользователя.
//
// form.addEventListener('submit', addNewUser): Эта строка добавляет обработчик событий на отправку формы. Когда форма отправляется, функция addNewUser() будет вызвана.
//
// function addNewUser(e) {...}: Это функция, которая вызывается при отправке формы. Она предотвращает отправку формы, создает новый объект пользователя на основе данных формы и отправляет его на сервер с помощью метода POST.
//
// e.preventDefault();: Эта строка предотвращает отправку формы при ее отправке.
//
// let newUserRoles = [];: Эта строка создает пустой массив для новых ролей пользователя.
//
// for (let i = 0; i < form.roles.options.length; i++) {...}: Этот цикл перебирает все элементы option списка ролей и добавляет выбранные элементы в массив newUserRoles.
//
// if (form.roles.options[i].selected) {...}: Эта строка
// Изначально создается переменная url с адресом сервера, на котором запущено API.
//
// Далее происходит экспорт объекта, содержащего методы, которые можно использовать для отправки HTTP-запросов к серверу.
//
// export default говорит о том, что данный объект является основным экспортом модуля и будет импортироваться при необходимости из других модулей.
//
// Метод async function request() принимает в качестве параметров метод HTTP-запроса, адрес запроса и тело запроса в формате объекта JavaScript.
//
// Далее происходит отправка запроса с помощью fetch, который возвращает объект Response.
//
// throw выбрасывает исключение и останавливает выполнение функции в случае, если ответ на запрос содержит ошибку.
//
// Метод res.json() преобразует ответ в формат JSON.
//
// return data возвращает данные из функции в виде объекта JavaScript.
//
// В случае, если сервер вернул ошибку, throw выбрасывает исключение.