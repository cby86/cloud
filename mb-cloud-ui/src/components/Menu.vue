<template>
  <div>
    <template v-for="item in items">
      <el-menu-item v-if="item.children ==null || item.children.length == 0" :index="item.path" v-on:click="doNavigation(item.name,item.path)">
        <i :class="item.icon"></i>
        <span>{{item.name}}</span>
      </el-menu-item>
      <!-- 判断是否有二级路由 -->
      <el-submenu v-if="item.children" :index="item.path">
        <template slot="title">
          <i :class="item.icon"></i>
          <span>{{item.name}}</span>
        </template>
        <!--递归组件，把遍历的值传回子组件，完成递归调用-->
        <Menu :items="item.children"/>
      </el-submenu>
    </template>
  </div>
</template>

<script>
  export default {
    name: "Menu",
    props: {
      items: Array
    },
    data() {
      return {
        // collapse: false,
      };
    },
    methods: {
      doNavigation(menuName, path) {
        this.bus.$emit("tabActive",menuName,path)
        // this.$router.push({
        //   name: path,
        //   params: {menuName: menuName,menuPath:path}
        // })
      }
    }
  };
</script>
