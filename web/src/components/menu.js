import React from 'react';
import { Link } from 'react-router-dom'

import css from 'css/menu.css';

export default function Menu() {
  return (
    <div className="menu">
      <ul>
        <li><Link to="/">Home</Link></li>
        <li><Link to="/signin">Signin</Link></li>
        <li><Link to="/signup">Signup</Link></li>
      </ul>
    </div>
  );
}
