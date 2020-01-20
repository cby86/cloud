<template>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item v-for="(item, index) in locations" :to="{ path: item.path }">{{item.name}}</el-breadcrumb-item>
    </el-breadcrumb>
</template>

<script>
  export default {
    name: 'Location',
    props: {
      locations:{
        default:()=>[]
      }
    },
    data() {
      return {};
    }
    ,
    mounted() {
    }
    ,
    methods: {}
  };
</script>
<style>
  .el-breadcrumb{
    margin-bottom: 25px;
    margin-left: 10px;
  }
</style>
