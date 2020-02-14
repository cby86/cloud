<template>
  <div class="content">
    <el-row>
      <el-col>
        <el-form :inline="true" :model="queryForm" ref="queryForm" class="demo-form-inline">
          <el-form-item label="用户名"  prop="username">
            <el-input size="small" v-model="queryForm.name" placeholder="用户名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size ="small" icon="el-icon-search" @click="onSubmit">查询</el-button>
            <el-button type="primary" size ="small" icon="el-icon-reset" @click="reset">清空</el-button>
            <el-button type="primary" size ="small" icon="el-icon-reset" @click="add">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-table size="small"
                  :data="tableData"
                  row-key="id"
                  style="width: 100%"
        >
          <el-table-column sortable
                           prop="username"
                           label="用户名">
          </el-table-column>
          <el-table-column sortable
                           prop="roles"
                           label="角色">
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作">
            <template slot-scope="scope" >
              <el-button type="text" size="small"  @click="edit(scope.row)">编辑</el-button>
              <el-button type="text" size="small" v-if="scope.row.inner==0" @click="deleteUser(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <page-nation :totalCount="totalCount" :pageCount="pageCount" v-on:pageChange="loadResource"></page-nation>
  </div>
</template>

<script>
  import PageNation from "../../components/PageNation"
  export default {
    name: 'UserList',
    components:{
      PageNation
    },
    data() {
      return {
        maps:new Map(),
        queryForm: {
          name: null
        },
        tableData: [],
        totalCount: 1,
        pageCount: 0,
        pageSize:10
      };
    },
    mounted() {
      this.loadUser(1,this.pageSize)
    },
    methods: {
      edit(row) {
        this.$router.push({name:"UserForm", params: {id: row.id}})
      },
      add() {
        this.$router.push({name:"UserForm"})
      },
      reset() {
        this.$refs["queryForm"].resetFields();
      },
      onSubmit() {
        this.loadUser(1,this.pageSize)
      },
      deleteUser(row) {
        this.$confirm('此操作将永久删除数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let maps = this.maps;
          this.$request.post({
            url: '/spring-user/user/deleteUser',
            data: {
              userId:row["id"]
            },
            success: result => {
              this.loadUser(1,this.pageSize)
            },
            error: e => {
              this.$message.error(e)
            }
          })
        }).catch((e) => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      loadUser(page,pageSize) {
        this.$request.post({
          url: '/spring-user/user/findUsers',
          data: {
            name:this.queryForm.name,
            page: page - 1,
            pageSize: pageSize
          },
          success: result => {
            this.tableData = result.data.items;
            this.totalCount = result.data.totalCount;
            this.pageCount = result.data.totalPage;
          },
          error: e => {
            this.$message.error(e)
          }
        })
      }
    }
  };
</script>
