import {DELETE_USER, SET_AUTHENTICATION, SET_MENU, SET_USER} from "./mutation-type";

export default {
  setUserInfo({commit}, user) {
    commit(SET_USER, {user})
  },
  deleteUser({commit}) {
    commit(DELETE_USER, false)
  },
  setAuthentication({commit}, authentications) {
    commit(SET_AUTHENTICATION, {authentications})
  }
}
