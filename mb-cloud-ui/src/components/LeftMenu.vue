<template>
  <el-aside class="menu_page" style="width: 15%">
    <el-menu :default-active="defaultAction"
             class="el-menu-vertical-demo"
             background-color="#303133"
             text-color="#fff"
             mode="vertical"
    >
      <Menu :items="items"></Menu>
    </el-menu>
  </el-aside>
</template>
<script>
  import Menu from "../components/Menu"

  export default {
    name: 'LeftMenu',
    components: {
      Menu
    },
    watch: {
      '$route'(to) {
        let menuName
        let menuPath
        if (!to.params || !to.params.menuName) {
          var option = this.findMenu(this.items)
          if (option) {
            menuName = option.name;
            menuPath = option.path;
            this.defaultAction = option.path;
          } else {
            return
          }
        } else {
          this.defaultAction = to.params.menuPath;
        }
        if (to.meta && to.meta.primary) {
          this.bus.$emit("newMenu", menuName, menuPath)
        }
      }
    },
    mounted() {
      var option = this.findMenu(this.items)
      if (option) {
        var menuName = option.name;
        var menuPath = option.path;
        this.defaultAction = option.path;
        this.bus.$emit("newMenu", menuName, menuPath)
      }
    },
    data() {
      return {
        defaultAction: "",
        items: [
          {
            path: "Home",
            name: "首页",
            icon: "el-icon-menu",
          }
          ,
          {
            path: "System_manager",
            name: "系统管理",
            icon: "el-icon-location",
            children: [
              {
                // icon: "el-icon-location",
                path: "MenuList",
                name: "菜单管理"
              },
              {
                // icon: "el-icon-location",
                path: "RoleList",
                name: "角色管理"
              },
              {
                // icon: "el-icon-location",
                path: "ResourceList",
                name: "资源管理"
              }
            ]
          }
        ]
      };
    },
    methods: {
      findMenu(items) {
        let primary = this.$route.meta && this.$route.meta.primary
        let routerName = this.$route.name;
        if (!primary) {
          routerName = this.$route.meta.parent || "Home"
        }
        for (let option of items) {
          if (routerName === option.path) {
            return option;
          }
          if (option.children) {
            return this.findMenu(option.children)
          }
        }
        return null;
      }
    }
  };
</script>
<style>
  .menu_page {
    top: 60px;
    min-height: 100%;
    width: 100%;
    left: 0;
    background-color: #303133;
  }

  .el-aside {
    line-height: 100px;
    height: 100%;
    background: #303133;
    margin-top: 1px
  }

  .el-menu {
    border: none;
    z-index: 2000;
    width: 100%;
  }
</style>
