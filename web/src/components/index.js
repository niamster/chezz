import React from 'react';
import { Route, Switch } from 'react-router-dom';

import Pages from 'pages/pages';

import 'css/index.css';

export default function Index() {
  return (
    <Switch>
      {Pages().map((page, index) => (
        <Route key={index} exact path={page.path()} component={page.render} />
      ))}
    </Switch>
  );
}
