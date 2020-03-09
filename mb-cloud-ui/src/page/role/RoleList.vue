<template>
  <div class="content">
    <el-row>
      <el-col>
        <el-form :inline="true" :model="queryForm" ref="queryForm" class="demo-form-inline">
          <el-form-item label="角色名称"  prop="name">
            <el-input size="small" v-model="queryForm.name" placeholder="角色名称"></el-input>
          </el-form-item>
          <el-form-item label="角色编码"  prop="code">
            <el-input size="small" v-model="queryForm.code" placeholder="角色编码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size ="small" icon="el-icon-search" @click="onSubmit">查询</el-button>
            <el-button type="primary" size ="small" icon="el-icon-reset" @click="reset">清空</el-button>
            <el-button type="primary" size ="small" icon="el-icon-reset" v-if="$store.getters.hasAuth('addRole')" @click="add">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-table size="small"
                  :data="tableData"
                  style="width: 100%">
          <el-table-column sortable
                           prop="name"
                           label="角色名称">
          </el-table-column>
          <el-table-column sortable
                           prop="code"
                           label="角色编码">
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small"  v-if="$store.getters.hasAuth('editRole')" @click="edit(scope.row)">编辑</el-button>
              <el-button type="text" size="small" v-if="$store.getters.hasAuth('deleteRole') && scope.row.inner==0" @click="deleteRole(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <page-nation :totalCount="totalCount" :pageCount="pageCount" v-on:pageChange="loadRole"></page-nation>
  </div>
</template>

<script>
  import PageNation from "../../components/PageNation"
  export default {
    name: 'RoleList',
    components:{
      PageNation
    },
    data() {
      return {
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
      this.loadRole(1,this.pageSize)
    },
    methods: {
      edit(row) {
        this.$router.push({name:"RoleForm", params: {id: row.id}})
      },
      reset() {
        this.$refs["queryForm"].resetFields();
      },
      obtainValue(v) {
        this.queryForm.menuType = v;
      },
      onSubmit() {
        this.loadRole(1,this.pageSize)
      },
      add() {
        this.$router.push({name:"RoleForm"})
      },
      deleteRole(row) {
        this.$confirm('此操作将永久删除数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$request.post({
            url: '/spring-user/role/deleteRole',
            data: {
              roleId:row["id"]
            },
            success: result => {
              this.loadRole(1, this.pageSize)
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
      loadRole(page,pageSize) {
        this.$request.post({
          url: '/spring-user/role/findRoles',
          data: {
            name:this.queryForm.name,
            code:this.queryForm.code,
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
