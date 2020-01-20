<template>
  <el-row class="page">
    <el-col>
      <el-pagination size ="small" background layout="sizes,prev, pager, next" :page-sizes="pageSizes" :total="totalCount"
                     :page-count="pageCount" :page-size="pageSize" @size-change="handleSizeChange" @current-change="pageChange" ></el-pagination>
    </el-col>
  </el-row>
</template>
<script>
  export default {
    props: {
      pageSizes:{
        default: ()=> {
          return [10, 20, 50, 100];
        }
      },
      pageCount: {
        default:0
      },
      currentPage:{
        default:1
      },
      totalCount: {
        default:0
      }
    },
    watch: {
      "pageSize"() {
        this.pageChange(1)
      }
    },
    data() {
      return {
        pageSize:10
      };
    },
    mounted() {
    },
    methods: {
      pageChange(p) {
        this.$emit('pageChange', p,this.pageSize)
      },
      handleSizeChange(size) {
        this.pageSize = size
      }
    }
  };
</script>
<style>
  .page {
    text-align: right;
    margin-top: 10px;
  }
</style>
