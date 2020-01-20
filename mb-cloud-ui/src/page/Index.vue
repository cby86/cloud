<template>
  <el-container class="is-vertical">
    <Header></Header>
    <el-container>
      <LeftMenu></LeftMenu>
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
      }
    },
    components: {
      MainContent,
      LeftMenu,
      Header
    },
    methods: {}
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
