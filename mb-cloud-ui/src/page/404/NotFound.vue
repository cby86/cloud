<template>
  <div class="full">
    <el-row class="full">
      <el-col :span="24" class="full">
        <div class="grid-content bg-purple-dark"><div class="message">我跑了地球一圈,也找不到你想要的...</div></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    name: 'NotFound',
    data() {
      return {}
    }
  }
</script>
<style>
  .grid-content {
    text-align: center;
    height: 100%;
    background-color: #303133;
  }
  .message{
    height: 100%;
    vertical-align: middle;
    font-size: 22px;
    color: white;
  }

  .full {
    height: 100%;
  }
</style>
