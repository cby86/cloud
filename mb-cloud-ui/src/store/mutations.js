import {SET_USER,DELETE_USER} from "./mutation-type";

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
  }
};
