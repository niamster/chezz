import React from 'react';
import { useHistory } from 'react-router-dom';
import { Button } from 'react-bootstrap';
import { userAPI } from 'api';

function SignoutForm() {
  const history = useHistory();

  function handleClick(event) {
    event.preventDefault();
    userAPI.signout().finally(() => {
      history.push('/');
      location.reload();
    });
  }

  return (
    <div className="Login">
      <Button variant="danger" onClick={handleClick}>
        Signout
      </Button>
    </div >
  );
}

export default class Signout {
  path() {
    return '/signout';
  }

  title() {
    return 'Signout';
  }

  isMenuActive() {
    return userAPI.isSignedIn();
  }

  render() {
    return (
      <div>
        <SignoutForm />
      </div>
    );
  }
}
