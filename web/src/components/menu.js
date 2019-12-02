import React from 'react';
import { Link } from 'react-router-dom';

import Pages from 'pages/pages';

import 'css/menu.css';

export default function Menu() {
  return (
    <div className="Menu">
      <ul>
        {Pages().filter(page => page.isMenuActive())
          .map((page, index) => (
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
