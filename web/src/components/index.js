import React from 'react';
import { Route, Switch } from 'react-router-dom';

import Main from 'components/main';
import Signin from 'components/signin';
import Signup from 'components/signup';

import css from 'css/index.css';

export default function Index () {
  return (
    <Switch>
      <Route exact path="/" component={Main} />
      <Route path="/signin" component={Signin} />
      <Route path="/signup" component={Signup} />
    </Switch>
  );
}
