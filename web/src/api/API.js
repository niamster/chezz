import 'url-search-params-polyfill';

export default class API {
  constructor() {
    this.base = process.env.API_URL;
    this.userToken = null;
  }

  setUserToken(token) {
    this.userToken = token;
  }

  resetUserToken() {
    this.setUserToken(null);
  }

  params(params) {
    if (!params || !this.userToken) {
      return '';
    }
    const query = new URLSearchParams();
    for (const [k, v] of Object.entries(params)) {
      query.append(k, v);
    }
    if (this.userToken) {
      query.append('user_token', this.userToken);
    }
    return '?' + query.toString();
  }

  get(path, params) {
    return fetch(this.base + path + this.params(params), {
      method: 'GET',
    }).then(response => {
      if (!response.ok) {
        throw new Error(`API call failed: "${response.statusText}"`);
      }
      return response;
    });
  }

  post(path, data, params) {
    return fetch(this.base + path + this.params(params), {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    }).then(response => {
      if (!response.ok) {
        throw new Error(`API call failed: "${response.statusText}"`);
      }
      return response.json();
    });
  }
}
