import {SET_USER, DELETE_USER, SET_AUTHENTICATION,SET_MENU} from "./mutation-type";

export default {
  setUserInfo({commit}, user) {
    commit(SET_USER, {user})
  },
  deleteUser({commit}) {
    commit(DELETE_USER, false)
  },
  setAuthentication({commit}, authentications) {
    commit(SET_AUTHENTICATION, {authentications})
  },
  setMenu({commit}, menus) {
    commit(SET_MENU, {menus})
  }
}
