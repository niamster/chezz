import Home from "pages/home";
import Signin from "pages/signin";
import Signup from "pages/signup";

export default function Pages() {
  return [
    new Home(), new Signin(), new Signup(),
  ];
}
