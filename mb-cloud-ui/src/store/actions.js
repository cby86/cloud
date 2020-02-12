import {SET_USER,DELETE_USER} from "./mutation-type";

export default {
  setUserInfo({commit}, user) {
    commit(SET_USER, {user})
  },
  deleteUser({commit}) {
    commit(DELETE_USER,false)
  }
}
