import { api } from '.';

export default class UserAPI {
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
      return Promise.resolve([username, response.token]);
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
      return Promise.resolve([username, response.token]);
    });
  }

  signout(token) {
    if (!token) {
      return;
    }
    return api.get('/protected/signout', null, token).finally(() => {
      return Promise.resolve();
    });
  }
}
