export default class API {
  constructor() {
    this.base = process.env.API_URL;
  }

  post(path, data) {
    return fetch(this.base + path, {
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
