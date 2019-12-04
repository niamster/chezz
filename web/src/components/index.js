import React from 'react';
import { Route, Switch, Redirect } from 'react-router-dom';

import Pages from 'pages';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'css/index.css';

export default function Index() {
  return (
    <Switch>
      {Pages().map((page, index) => (
        <Route key={index} exact path={page.path()} component={page.render} />
      ))}
      <Route path="*">
        <Redirect to="/" />
      </Route>
    </Switch>
  );
}
