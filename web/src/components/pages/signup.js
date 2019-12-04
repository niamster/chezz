import PropTypes from 'prop-types';
import React, { useState } from 'react';
import { connect } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { Button, Form, Modal } from 'react-bootstrap';

import { userAPI } from 'api';
import { setUser } from 'components/state/user';

import 'css/login.css';

function component(props) {
  const history = useHistory();
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showFailure, setShowFailure] = useState(false);
  const [failReason, setFailReason] = useState('');

  const handleCloseFailure = () => setShowFailure(false);

  function validateForm() {
    return username.length > 0 && email.length > 0 && password.length > 0;
  }

  function handleSubmit(event) {
    event.preventDefault();
    userAPI.signup(username, email, password).then(response => {
      const [username, token] = response;
      props.setUser(username, token);
      history.push('/');
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
        <Form.Group controlId="email">
          <Form.Label>Email</Form.Label>
          <Form.Control
            autoFocus
            type="email"
            value={email}
            onChange={e => setEmail(e.target.value)}
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
          <Modal.Title>Failed to signup!</Modal.Title>
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

component.propTypes = {
  setUser: PropTypes.func,
};

function mapDispatchToProps(dispatch) {
  return {
    setUser: (username, token) => dispatch(setUser(username, token)),
  };
}

const SignupComponent = connect(
  null,
  mapDispatchToProps
)(component);

export default class Signup {
  path() {
    return '/signup';
  }

  title() {
    return 'Signup';
  }

  enabled(state) {
    return !state.user.signedIn;
  }

  render() {
    return (
      <div>
        <SignupComponent />
      </div>
    );
  }
}
