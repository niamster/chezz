import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter } from 'react-router-dom';

import Menu from 'components/menu';
import Index from 'components/index';

ReactDOM.render(
  <BrowserRouter>
    <Menu />
    <Index />
  </BrowserRouter>,
  document.getElementById('root')
);
