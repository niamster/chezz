import Home from 'pages/home';
import Signin from 'pages/signin';
import Signup from 'pages/signup';
import Signout from 'pages/signout';

export default function Pages() {
  return [
    new Home(), new Signin(), new Signup(), new Signout(),
  ];
}
