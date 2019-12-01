import React from 'react';
import { Link } from 'react-router-dom'

import Pages from 'pages/pages';

import css from 'css/menu.css';

export default function Menu() {
  return (
    <div className="menu">
      <ul>
        {Pages().map((page, index) => (
          <li key={index}>
            <Link to={page.path()}>
              {page.title()}
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
}
