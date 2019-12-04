import PropTypes from 'prop-types';
import React from 'react';
import { connect } from 'react-redux';

function component(props) {
  if (props.user.signedIn) {
    return (
      <div>
        Welcome back, <b>{props.user.username}</b>!
      </div>
    );
  }
  return (
    <div>
      Hello!
    </div>
  );
}

component.propTypes = {
  user: PropTypes.object,
};

function mapStateToProps(state) {
  return {
    user: state.user,
  };
}

const HomeComponent = connect(mapStateToProps)(component);

export default class Home {
  path() {
    return '/';
  }

  title() {
    return 'Home';
  }

  enabled(props) {
    return true;
  }

  render() {
    return (
      <HomeComponent />
    );
  }
}
