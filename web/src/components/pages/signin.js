import React from 'react';

export default class Signin {
  path() {
    return '/signin';
  }

  title() {
    return 'Signin';
  }

  isMenuActive() {
    return true;
  }

  render() {
    return (
      <div>
        Signin
      </div>
    );
  }
}
