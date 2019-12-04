import PropTypes from 'prop-types';
import React from 'react';
import { connect } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { Button } from 'react-bootstrap';

import { userAPI } from 'api';
import { resetUser } from 'components/state/user';

function component(props) {
  const history = useHistory();

  function handleClick(event) {
    event.preventDefault();
    userAPI.signout(props.user.token).finally(() => {
      props.resetUser();
      history.push('/');
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

component.propTypes = {
  user: PropTypes.object,
  resetUser: PropTypes.func,
};

function mapDispatchToProps(dispatch) {
  return {
    resetUser: () => dispatch(resetUser()),
  };
}

function mapStateToProps(state) {
  return {
    user: state.user,
  };
}

const SignoutComponent = connect(
  mapStateToProps,
  mapDispatchToProps
)(component);

export default class Signout {
  path() {
    return '/signout';
  }

  title() {
    return 'Signout';
  }

  enabled(state) {
    return state.user.signedIn;
  }

  render() {
    return (
      <div>
        <SignoutComponent />
      </div>
    );
  }
}
