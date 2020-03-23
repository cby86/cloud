import {DELETE_USER, SET_AUTHENTICATION, SET_USER} from "./mutation-type";


export default {
  [SET_USER](state, {user}) {
    if (user) {
      state.user = user;
    } else {
      state.user = {};
    }
  },
  [DELETE_USER](state) {
    state.user = {}
  },
  [SET_AUTHENTICATION](state, {authentications}) {
    state.authentications = authentications;
  }
};
