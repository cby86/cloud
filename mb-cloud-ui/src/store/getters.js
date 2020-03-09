export default {
  user: state => state.user,
  authentications: state => state.authentications,
  hasAuth:(state) => (name) => {
    return true;
    // return state.authentications.filter(item => item.code == name || item.url==name).length>0
  }
}
