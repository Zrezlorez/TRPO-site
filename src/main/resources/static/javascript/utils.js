export async function getAuthUser() {
    let response = await fetch("/api/users/user")
    return response.ok ?
        response.json() : null
}

export async function getAuthUserId() {
    return await getAuthUser()
        .then(authUser => authUser.id)
        .then(id => {
            return id === undefined ? null : id
        })
}

export async function updateUserInfo() {
    let authUser = await getAuthUser()
    if (authUser != null) {
        let text = ""
        if(authUser.roles[0].role == "ROLE_ADMIN") {
            text = "Преподаватель "
        }
        else {text = "Студент "}
        $("#postName").text(text)
        $("#userName").text(authUser.studentName)
    }
}