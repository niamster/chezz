import React from 'react';
import UserAPI from 'api/UserAPI';

class HomeInternal {
  constructor() {
    this.userApi = new UserAPI();
  }

  renderForAnonymous() {
    return (
      <div>
        Hello!
      </div>
    );
  }

  renderForUser(username) {
    return (
      <div>
        Welcome back, <b>{username}</b>!
      </div>
    );
  }

  render() {
    const username = this.userApi.getUserName();
    if (username) {
      return this.renderForUser(username);
    }
    return this.renderForAnonymous();
  }
}

export default class Home {
  path() {
    return "/";
  }

  title() {
    return "Home";
  }

  isMenuActive() {
    return true;
  }

  render() {
    const home = new HomeInternal();
    return home.render();
  }
}
