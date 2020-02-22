<template>
  <el-container class="is-vertical">
    <Header></Header>
    <el-container>
      <LeftMenu :items="menus"></LeftMenu>
      <MainContent></MainContent>
    </el-container>
  </el-container>
</template>
<script>
  import MainContent from "../components/MainContent"
  import LeftMenu from "../components/LeftMenu"
  import Header from "../components/Header"

  export default {
    name: 'Layout',
    data() {
      return {
        menus:null
      }
    },
    components: {
      MainContent,
      LeftMenu,
      Header
    },
    mounted() {
      this.loadAuthentication()
    },
    beforeRouteUpdate (to, from, next) {
      if (to.path !== '/' && !this.$store.getters.hasAuth(to.name)) {
        this.$message.error("无权限访问")
        next({
          path: from.path
        })
      } else {
        next()
      }
    },
    methods: {
      checkAuth() {
        if (this.$route.path !== '/' && !this.$store.getters.hasAuth(this.$route.name)) {
          this.$message.error("无权限访问")
          console.log(this.$route)
          this.$router.push({
            path: "/"
          })
          return false;
        }
        return true;
      },
      loadAuthentication() {
        this.$request.get({
          url: '/spring-user/user/findAuthentication',
          config: {
            params: {
              userId: this.$store.getters.user.userId
            }
          },
          success: result => {
            this.$store.dispatch('setAuthentication', result.data)
            let listToTree = this.$utils.listToTree(result.data, null, (item) => {
              return item.authentionType == 0
            });
            this.menus=listToTree
            this.bus.$emit("initTab")
            this.checkAuth();
          },
          error: e => {
            this.bus.$emit("initTab")
            this.$message.error(e)
          }
        });
      }
    }
  }
</script>
<style>
  .el-container {
    height: 100%;
  }

  .el-form {
    margin-left: 10px;
  }
  .el-form-item {
    margin-bottom: 10px;
  }
  .el-table td{
    height: 35px;
  }
  .el-input {
    width: 200px;
  }
</style>
