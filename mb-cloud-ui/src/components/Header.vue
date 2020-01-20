<template>
  <el-header class="header-nav">
    <el-row>
      <el-col :span="6" class='logo-container'>
        <div class="collapse-btn">
          <i class="el-icon-menu icon-btn"></i>
        </div>
        <span class="title">后台管理系统</span>
      </el-col>
      <el-col :span="6" class="user">
        <span>
        <img class="avatar" src="../assets/logo.png">
          </span>
        <span class="welcome ">
          <i class="name avatarname">{{this.$store.getters.user.username}}</i>
        </span>
        <span class='username'>
						<el-dropdown trigger="click" @command='setDialogInfo'>
							<span class="el-dropdown-link">
								<i class="el-icon-caret-bottom el-icon--right"></i>
							</span>
							<el-dropdown-menu slot="dropdown">
								<el-dropdown-item command='info'>个人信息</el-dropdown-item>
								<el-dropdown-item command='logout'>退出</el-dropdown-item>
							</el-dropdown-menu>
						</el-dropdown>
					</span>
      </el-col>
    </el-row>
  </el-header>
</template>
<script>
  import jwt_decode from 'jwt-decode'
  export default {
    name: 'Header',
    data() {
      return {};
    },
    mounted() {
      //强制刷新后,用户信息重新从session获取
      if (!this.$store.getters.user.username) {
        let token = sessionStorage.getItem('token');
        const decode = jwt_decode(token)
        this.$store.dispatch('setUserInfo', {
          username:decode.user_name,
          role:decode.authorities && decode.authorities.length>0 ?decode.authorities[0]:null
        })
      }
    },
    methods: {
      setDialogInfo(cmditem) {
        switch (cmditem) {
          case 'info':
            this.showInfoList()
            break;
          case 'logout':
            this.logout()
            break;
        }
      },
      logout() {
        //清除token
        sessionStorage.removeItem('token');
        //清除vuex
        this.$store.dispatch('deleteUser')
        //路由跳转
        this.$router.push('/login')
      },
      showInfoList() {
        let username = this.$store.getters.user.username
        let role = this.$store.getters.user.role
        this.$alert('当前用户:' + username + ",角色:" + role, '用户信息');
      }
    }
  }
</script>
<style>
  .header-nav {
    width: 100%;
    height: 60px;
    background: #303133;
    color: #fff;
    border-bottom: 1px solid #1f2d3d;
    overflow: hidden;
    padding: 5px 0;
    line-height: 60px;
  }

  .logo-container {
    line-height: 60px;
    min-width: 400px;
    padding: 0 5px;
  }

  .collapse-btn {
    width: 50px;
    margin-left: 20px;
    display: inline-block;
  }

  .icon-btn {
    font-size: 36px;
    vertical-align: middle;
    cursor: pointer;
  }

  .title {
    vertical-align: middle;
    font-size: 22px;
    font-family: "Microsoft YaHei";
    letter-spacing: 3px;
  }

  .user {
    line-height: 60px;
    text-align: right;
    float: right;
    padding-right: 10px;
  }

  .el-header {
    text-align: left;
    line-height: 60px;
    padding: 0;
    z-index: 1000;
  }

  .el-header {
    border-bottom: none;
  }

  .username {
    cursor: pointer;
    margin-right: 5px;
  }

  .welcome {
    display: inline-block;
    width: auto;
    vertical-align: middle;
    padding: 0 5px;
  }

  .name {
    line-height: 20px;
    text-align: center;
    font-size: 14px;
  }

  .avatarname {
    color: #409eff;
    font-weight: bolder;
  }

  .avatar {
    width: 35px;
    height: 35px;
    border-radius: 50%;
    vertical-align: middle;
    display: inline-block;
  }
</style>
