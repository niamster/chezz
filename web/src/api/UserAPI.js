import Api from 'api/API';

export default class UserAPI {
    constructor() {
        this.api = new Api();
    }

    getUserName() {
        let username = "";
        try {
            username = this.getUserInfo().username;
        } finally {
            return username;
        }
    }

    getUserInfo() {
        return JSON.parse(localStorage.getItem("user_info"));
    }

    setUserInfo(username, token) {
        localStorage.setItem("user_info", JSON.stringify({
            username: username,
            token: token,
        }));
    }

    signup(username, email, password) {
        const data = {
            "username": username,
            "email": email,
            "password": password,
        };
        return this.api.post("/public/signup", data).then(response => {
            if (response.status != "ok") {
                throw new Error(`Failed to signup: "${response.status}"`)
            }
            this.setUserInfo(username, response.token);
            return username;
        });
    }
}