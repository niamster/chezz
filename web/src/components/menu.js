import PropTypes from 'prop-types';
import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';

import Pages from 'pages';

import 'css/menu.css';

function component(props) {
  function pageLink(page, index) {
    if (!page.enabled(props)) {
      return null;
    }
    return (
      <li key={index}>
        <Link to={page.path()}>{page.title()}</Link>
      </li>
    );
  }

  return (
    <div className="Menu">
      <ul>
        {Pages().map((page, index) => (
          pageLink(page, index)
        ))}
      </ul>
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

const Menu = connect(mapStateToProps)(component);
export default Menu;
