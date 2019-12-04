import 'url-search-params-polyfill';

export default class API {
  constructor() {
    this.base = process.env.API_URL;
  }

  params(params, token) {
    if (!params && !token) {
      return '';
    }
    const query = new URLSearchParams();
    for (const [k, v] of Object.entries(params || {})) {
      query.append(k, v);
    }
    if (token) {
      query.append('user_token', token);
    }
    return '?' + query.toString();
  }

  get(path, params, token = '') {
    return fetch(this.base + path + this.params(params, token), {
      method: 'GET',
    }).then(response => {
      if (!response.ok) {
        throw new Error(`API call failed: "${response.statusText}"`);
      }
      return response;
    });
  }

  post(path, data, params, token = '') {
    return fetch(this.base + path + this.params(params, token), {
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
