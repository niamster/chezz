import { api } from '.';

export default class UserAPI {
  getUserName() {
    let username = '';
    try {
      username = this.getUserInfo().username;
    } catch (err) {
    }
    return username;
  }

  getUserInfo() {
    return JSON.parse(localStorage.getItem('user_info'));
  }

  setUserInfo(username, token) {
    localStorage.setItem('user_info', JSON.stringify({
      username: username,
      token: token,
    }));
  }

  resetUserInfo() {
    localStorage.removeItem('user_info');
  }

  signup(username, email, password) {
    const data = {
      username: username,
      email: email,
      password: password,
    };
    return api.post('/public/signup', data).then(response => {
      if (response.status !== 'ok') {
        throw new Error(`Failed to signup: "${response.status}"`);
      }
      this.setUserInfo(username, response.token);
      api.setUserToken(response.token);
      return Promise.resolve(username);
    });
  }

  signin(username, password) {
    const data = {
      username: username,
      password: password,
    };
    return api.post('/public/signin', data).then(response => {
      if (response.status !== 'ok') {
        throw new Error(`Failed to signin: "${response.status}"`);
      }
      this.setUserInfo(username, response.token);
      api.setUserToken(response.token);
      return Promise.resolve(username);
    });
  }

  signout() {
    const userInfo = this.getUserInfo();
    this.resetUserInfo();
    if (!userInfo || !userInfo.token) {
      return;
    }
    const data = {
      user_token: userInfo.token,
    };
    return api.get('/protected/signout', data).finally(() => {
      api.resetUserToken();
      return Promise.resolve();
    });
  }
}
