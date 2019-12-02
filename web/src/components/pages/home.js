import React from 'react';
import { userAPI } from 'api';

class HomeInternal {
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
    const username = userAPI.getUserName();
    if (username) {
      return this.renderForUser(username);
    }
    return this.renderForAnonymous();
  }
}

export default class Home {
  path() {
    return '/';
  }

  title() {
    return 'Home';
  }

  isMenuActive() {
    return true;
  }

  render() {
    const home = new HomeInternal();
    return home.render();
  }
}
