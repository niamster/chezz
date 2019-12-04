const SET_USER = 'SET_USER';
const RESET_USER = 'RESET_USER';

const LOCAL_USER_INFO = 'local_user_info';

const localState = JSON.parse(localStorage.getItem(LOCAL_USER_INFO)) || {};
const initialState = {
  username: localState.username,
  token: localState.token,
  signedIn: localState.signedIn,
};

export function setUser(username, token) {
  return {
    type: SET_USER,
    username: username,
    token: token,
  };
}

export function resetUser() {
  return {
    type: RESET_USER,
  };
}

export default function user(state = initialState, action) {
  switch (action.type) {
  case SET_USER: {
    const localState = {
      username: action.username,
      token: action.token,
      signedIn: true,
    };
    localStorage.setItem(LOCAL_USER_INFO, JSON.stringify(localState));
    return Object.assign({}, state, localState);
  }
  case RESET_USER:
    localStorage.removeItem(LOCAL_USER_INFO);
    return Object.assign({}, state, {
      username: '',
      token: '',
      signedIn: false,
    });
  }
  return state;
}
