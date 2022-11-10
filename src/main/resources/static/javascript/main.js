import * as utils from "./utils.js"

$(document).ready(async function () {
    await loadTabList()
    await usersTableColumn()
    await updateUsersTable()


    async function addGroup(name) {
        await fetch("/api/groups/add", {
            method: "POST",
            body: name,
            headers: {
                "Content-Type": "application/json"
            }
        })
        await loadTabList()
    }
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
        if(studentName=="") {
            await fetch("/api/users/" + userId, {
                        method: "DELETE"
                    })
        }
        else {
            let patchedUser = {
                name: studentName,
                password: "student",
                student: studentName,
                roles: [],
                group: null
            }
            await fetch("/api/roles/2")
                .then(response => response.json())
                .then(role => {
                    patchedUser.roles.push(role)
                })
            await fetch("/api/groups/" + $("a.active").text())
                .then(response => response.json())
                .then(group => {
                    patchedUser.group = group
                })
            await fetch("/api/users/" + userId, {
                method: "PATCH",
                body: JSON.stringify(patchedUser),
                headers: {
                    "Content-Type": "application/json"
                }
            })
        }
        await updateUsersTable()
    }
    async function addUser(student) {
        let createdUser = {
            name: student,
            password: "student",
            roles: [],
            studentName: student,
            group: null
        }
        await fetch("/api/groups/" + $("a.active").text())
            .then(response => response.json())
            .then(group => {
                createdUser.group = group
            })
        await fetch("/api/roles/2")
            .then(response => response.json())
            .then(role => {
                createdUser.roles.push(role)
            })
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
        if(day=="") {
            await fetch("/api/days/" + dayId, {
                        method: "DELETE"
                    })
        }
        else{
            await fetch("/api/days/" + dayId, {
                method: "PATCH",
                body: day,
                headers: {
                    "Content-Type": "application/json"
                }
            })
        }
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
            let th = $("<th style='text-align: center'/>")
            let editDayBtn = $("<input type='text' class='no-border' style='text-align: center' value='" + day.name +"'>")
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
            if(user.roles[0].role != "ROLE_USER" || user.group.name!=$("a.active").text()) {continue}
            let tr = $("<tr/>")
            let th = $("<th/>")
            let editNameBtn = $("<input type='text' class='no-border'  value='" + user.studentName +"'>")
            editNameBtn.keypress(function(event) {
                    let keycode = (event.keyCode ? event.keyCode : event.which);
                    if(keycode == '13') {
                        console.log(editNameBtn.val())
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

    async function loadTabList() {
        let groups = await fetch("/api/groups")
                    .then(response => response.json())
                    .then(groups => {
                        return groups
                    })
        let body = $("#v-pills-tab")
        body.empty()
        let first = true
        for(let group of groups) {
            let btn = $("<a class='nav-link'>")
            if(first) {
                btn.addClass("active")
                first = false
            }
            btn.text(group.name)
            first = ""
            btn.bind("click", async function(){
                $(".nav-link").removeClass("active")
                btn.addClass("active")
                await usersTableColumn()
                await updateUsersTable()
            })
            body.append(btn)
        }
        let inp = $("<input type='text' class='border border-1' placeholder='Новый предмет' style='height:39px'>")
        inp.keypress(function(event) {
                      let keycode = (event.keyCode ? event.keyCode : event.which);
                      if(keycode == '13') {
                          addGroup(inp.val())
                      }
        })
        body.append(inp)
   }
})