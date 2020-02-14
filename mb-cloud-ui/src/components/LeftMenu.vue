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
      this.loadAuthentication((items)=>{
        this.items = items;
        if(this.items && this.items.length>0) {
          var option = this.findMenu(this.items)
          if (option) {
            var menuName = option.name;
            var menuPath = option.path;
            this.defaultAction = option.path;
            this.bus.$emit("newMenu", menuName, menuPath)
          }
        }
      })
    },
    data() {
      return {
        defaultAction: "",
        items: null
      };
    },
    methods: {
      loadAuthentication(callback) {
        this.$request.get({
          url: '/spring-user/user/findAuthentication',
          config: {
            params: {
              userId: this.$store.getters.user.userId
            }
          },
          success: result => {
            this.$store.dispatch('setAuthentication', result.data)
            callback(this.menuTreeConvert(result.data,null))
          },
          error: e => {
            this.$message.error(e)
          }
        });
      },
      menuTreeConvert(menuList, parentId) {
        let temp = [];
        let treeArr = menuList;
        treeArr.forEach((item, index) => {
          if (item.parentId == parentId) {
            if (this.menuTreeConvert(treeArr, treeArr[index].id).length > 0) {
              // 递归调用此函数
              treeArr[index].children = this.menuTreeConvert(treeArr, treeArr[index].id);
            }
            temp.push({
              name: treeArr[index].name,
              path: treeArr[index].url,
              icon: treeArr[index].icon,
              children: treeArr[index].children
            });
          }
        });
        return temp;
      },
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
