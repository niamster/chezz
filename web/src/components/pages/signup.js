import React, { useState } from 'react';
import { Button, Form } from "react-bootstrap";
import UserAPI from 'api/UserAPI';

import 'css/login.css';

export function SignupForm(props) {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  function validateForm() {
    return username.length > 0 && email.length > 0 && password.length > 0;
  }

  function handleSubmit(event) {
    event.preventDefault();
    let userApi = new UserAPI();
    userApi.signup(username, email, password).then(response => {
      // TODO: redirect to root
    }).catch(err =>{
      // TODO: error handling
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
    </div>
  );
}

export default class Signup {
  path() {
    return "/signup";
  }

  title() {
    return "Signup";
  }

  isMenuActive() {
    return true;
  }

  render() {
    return (
      <div>
        <SignupForm />
      </div>
    );
  }
}
