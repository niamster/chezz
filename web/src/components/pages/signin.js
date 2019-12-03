import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';
import { Button, Form, Modal } from 'react-bootstrap';
import { userAPI } from 'api';

import 'css/login.css';

function SigninForm() {
  const history = useHistory();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [showFailure, setShowFailure] = useState(false);
  const [failReason, setFailReason] = useState('');

  const handleCloseFailure = () => setShowFailure(false);

  function validateForm() {
    return username.length > 0 && password.length > 0;
  }

  function handleSubmit(event) {
    event.preventDefault();
    userAPI.signin(username, password).then(response => {
      history.push('/');
      location.reload();
    }).catch(err => {
      // TODO: error handling
      setFailReason('unknown');
      setShowFailure(true);
      console.log(err);
    });
  }

  return (
    <div className="Login">
      <Form onSubmit={handleSubmit}>
        <Form.Group controlId="username">
          <Form.Label>Username</Form.Label>
          <Form.Control
            autoFocus
            type="text"
            value={username}
            onChange={e => setUsername(e.target.value)}
          />
        </Form.Group>
        <Form.Group controlId="password">
          <Form.Label>Password</Form.Label>
          <Form.Control
            value={password}
            onChange={e => setPassword(e.target.value)}
            type="password"
          />
        </Form.Group>
        <Button block disabled={!validateForm()} type="submit">
          Login
        </Button>
      </Form>

      <Modal show={showFailure} onHide={handleCloseFailure}>
        <Modal.Header closeButton>
          <Modal.Title>Failed to signin!</Modal.Title>
        </Modal.Header>
        <Modal.Body>Reason: {failReason}</Modal.Body>
        <Modal.Footer>
          <Button variant="primary" onClick={handleCloseFailure}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}

export default class Signin {
  path() {
    return '/signin';
  }

  title() {
    return 'Signin';
  }

  isMenuActive() {
    return !userAPI.isSignedIn();
  }

  render() {
    return (
      <div>
        <SigninForm />
      </div>
    );
  }
}
