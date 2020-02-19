export default {
  user: state => state.user,
  authentications: state => state.authentications,
  hasAuth:(state) => (name) => {
    return state.authentications.filter(item => item.code === name).length>0
  }
}
