import * as utils from "./utils.js"

$(document).ready(async function () {
    await usersTableColumn()
    await updateUsersTable()


    async function findGrade(userId, dayId) {
        let info = {
            userId: userId,
            dayId: dayId
        }
        let grade = await fetch("/api/gradeinfo/find", {
               method: "POST",
               body: JSON.stringify(info),
               headers: {
                   "Content-Type": "application/json"
               }
           })
           .then(response => response.json())
           .then(response => {return response})
        return await grade;
    }
    async function addGrade(userId, lessonId, dayId, grade) {
        let createdGrade = {
            dateId: dayId,
            grade: grade,
            lessonId: lessonId,
            userId: userId
        }
        await fetch("/api/gradeinfo/add", {
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


    async function patchUser(userId, studentName) {
        let patchedUser = {
            name: studentName,
            password: "student",
            student: studentName,
            roles: []
        }
        await fetch("/api/roles/1")
            .then(response => response.json())
            .then(role => {
                patchedUser.roles.push(role)
            })
        await fetch("/api/users/" + userId, {
            method: "PATCH",
            body: JSON.stringify(patchedUser),
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

    async function patchDay(dayId, day) {
        await fetch("/api/days/" + dayId, {
            method: "PATCH",
            body: day,
            headers: {
                "Content-Type": "application/json"
            }
        })
        await updateUsersTable()
        await usersTableColumn()
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
            let editDayBtn = $("<input type='text' class='no-border' style='text-align: center value='" + day.name +"'>")
            editDayBtn.keypress(function(event) {
                    let keycode = (event.keyCode ? event.keyCode : event.which);
                    if(keycode == '13') {
                        patchDay(day.id, editDayBtn.val())
                    }
                })
            th.append(editDayBtn)
            tr.append(th)
        }
        body.append(tr)
        let thBtn = $("<th/>")
        let addBtn = $("<input type='text' class='no-border' placeholder='Новый день'>")
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
            console.log(user)
            if(user.roles[0].role != "ROLE_USER") {continue}
            let tr = $("<tr/>")
            let th = $("<th/>")
            let editNameBtn = $("<input type='text' class='no-border'  value='" + user.student +"'>")
            editNameBtn.keypress(function(event) {
                    let keycode = (event.keyCode ? event.keyCode : event.which);
                    if(keycode == '13') {
                        patchUser(user.id, editNameBtn.val())
                    }
                })
            th.append(editNameBtn)
            tr.append(th)
            for(let day of days) {
                let table = await findGrade(user.id, day.id).then(table => {return table})
                let td = $("<td style='text-align: center'/>")
                let editBtn = $("<input type='text' size='2' class='border border-1' value=" + table.grade +">")
                editBtn.keypress(function(event) {
                    let keycode = (event.keyCode ? event.keyCode : event.which);
                    if(keycode == '13') {
                        patchGrade(table.id, editBtn.val())
                    }
                })
                td.append(editBtn)
                tr.append(td)
            }
            let emptyTd = $("<td/>")
            tr.append(emptyTd)
            body.append(tr)
        }
        let tr = $("<tr/>")
        let tdBtn = $("<td/>")
        let addBtn = $("<input type='text' class='no-border' placeholder='Новый студент'>")
        addBtn.keypress(function(event) {
            let keycode = (event.keyCode ? event.keyCode : event.which);
            if(keycode == '13') {
                addUser(addBtn.val())
            }
        })
        tdBtn.append(addBtn)
        tr.append(tdBtn)
        body.append(tr)
    }
})