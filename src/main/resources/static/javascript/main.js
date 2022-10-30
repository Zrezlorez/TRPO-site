import * as utils from "./utils.js"

$(document).ready(async function () {
    await usersTableColumn()
    await updateUsersTable()


    $("#submitDeleteBtn").bind("click", async function() {
        let deletedUserId = $("#deleteId").val()
        if (Number(deletedUserId) === await utils.getAuthUserId()) {
            window.location = "/logout"
        }
        await fetch("/api/users/" + deletedUserId, {
            method: "DELETE"
        })
        await updateUsersTable()
    })
    async function addGrade(userId, lessonId, dayId, grade) {
        let createdGrade = {
            dateId: dayId,
            grade: grade,
            lessonId: lessonId,
            userId: userId
        }
        await fetch("/api/gradeinfo/", {
            method: "POST",
            body: JSON.stringify(createdGrade),
            headers: {
                "Content-Type": "application/json"
            }
        })
        await updateUsersTable()
    }
    async function patchGrade(gradeId, grade) {
        await fetch("/api/gradeinfo/" + gradeId, {
            method: "PATCH",
            body: grade,
            headers: {
                "Content-Type": "application/json"
            }
        })
        await updateUsersTable()
    }
    async function addUser(studentName) {
        let createdUser = {
            name: studentName,
            password: "student",
            roles: [],
            student: studentName
        }
        await fetch("/api/users", {
            method: "POST",
            body: JSON.stringify(createdUser),
            headers: {
                "Content-Type": "application/json"
            }
        })
        await updateUsersTable()
    }
    async function addDay(date) {
        await fetch("/api/days", {
            method: "POST",
            body: date.toString(),
            headers: {
                "Content-Type": "application/json"
            }
        })
        await updateUsersTable()
        await usersTableColumn()
    }
    async function usersTableColumn() {
        let body = $(".table #usersTableColumn")
        body.empty()
        let days = await fetch("/api/days")
                    .then(response => response.json())
                    .then(days => {
                        return days
                    })
        let tr = $("<tr/>")
        let thName = $("<th/>")
        thName.text("Имя студента")
        tr.append(thName)
        for (let day of days) {
            let th = $("<th/>")
            th.text(day.name)
            tr.append(th)
        }
        body.append(tr)
        let thBtn = $("<th/>")
        let addBtn = $("<input type='text' placeholder='Новый день'>")
        addBtn.keypress(function(event) {
            let keycode = (event.keyCode ? event.keyCode : event.which);
            if(keycode == '13') {
                addDay(addBtn.val())
            }
        })
        thBtn.append(addBtn)
        tr.append(thBtn)
        body.append(tr)
    }

    async function updateUsersTable() {
        let gradeinfo = await fetch("/api/gradeinfo")
            .then(response => response.json())
            .then(gradeinfo => {
                return gradeinfo
            })
        let users = await fetch("/api/users")
            .then(response => response.json())
            .then(users => {
                return users
            })
        let days = await fetch("/api/days")
            .then(response => response.json())
            .then(days => {
                return days
            })
        let body = $(".table #usersTableBody")
        body.empty()
        for (let user of users) {
            console.log("итерация юзера " + user.student)
            let tr = $("<tr/>")
            let th = $("<th/>")
            th.text(user.student)
            tr.append(th)
            for(let day of days) {
                console.log("итерация дня номер " + day.name)
                let isAdd = true
                for(let table of gradeinfo) {
                    console.log("итерация журнала")
                    if(table.date.id != day.id) {
                        console.log("неправильна дата")
                        continue
                    }
                    if(table.user.id == user.id) {
                        let td = $("<td/>")
                        let editBtn = $("<input type='text' value=" + table.grade +">")
                        console.log("найдена оценка")
                        editBtn.keypress(function(event) {
                            let keycode = (event.keyCode ? event.keyCode : event.which);
                            if(keycode == '13') {
                                patchGrade(table.id, editBtn.val())
                            }
                        })
                        td.append(editBtn)
                        tr.append(td)
                        isAdd = false
                    }
                }
                if(isAdd) {
                    console.log("добавляем пустую оценку")
                    addGrade(user.id, 1, day.id, " ")
                }

            }
            body.append(tr)
        }
        let tr = $("<tr/>")
        let td = $("<td/>")
        let addBtn = $("<input type='text' placeholder='Новый студент'>")
        addBtn.keypress(function(event) {
            let keycode = (event.keyCode ? event.keyCode : event.which);
            if(keycode == '13') {
                console.log("добавлен новый пользователь")
                addUser(addBtn.val())

            }
        })
        td.append(addBtn)
        tr.append(td)
        body.append(tr)
    }
})