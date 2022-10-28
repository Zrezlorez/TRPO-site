export async function getAuthUser() {
    let response = await fetch("/api/users/user")
    return response.ok ?
        response.json()
        :
        null
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
        $("#userName").text(authUser.name)
        let rolesText = " with roles: "
        for (let role of authUser.roles) {
            rolesText += `${role.role} `
        }
        $("#userRoles").text(rolesText)
    }
}