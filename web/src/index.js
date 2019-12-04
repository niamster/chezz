import React from 'react';
import ReactDOM from 'react-dom';
import { createStore } from 'redux';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';

import Menu from 'components/menu';
import Index from 'components/index';
import Store from 'components/store';

const store = createStore(Store);

ReactDOM.render(
  <Provider store={store}>
    <BrowserRouter>
      <Menu />
      <Index />
    </BrowserRouter>
  </Provider>,
  document.getElementById('root')
);
