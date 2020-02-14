import {SET_USER, DELETE_USER, SET_AUTHENTICATION,SET_MENU} from "./mutation-type";



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
  },
  [SET_MENU](state, {menus}) {
    state.menus = menus;
  }
};
