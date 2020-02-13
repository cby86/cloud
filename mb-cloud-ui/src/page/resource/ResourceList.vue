<template>
  <div class="content">
    <el-row>
      <el-col>
        <el-form :inline="true" :model="queryForm" ref="queryForm" class="demo-form-inline">
          <el-form-item label="应用名称"  prop="name">
            <el-input size="small" v-model="queryForm.name" placeholder="应用名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size ="small" icon="el-icon-search" @click="onSubmit">查询</el-button>
            <el-button type="primary" size ="small" icon="el-icon-reset" @click="reset">清空</el-button>
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
                  lazy
                  :load="load"
                  :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
        >
          <el-table-column sortable
                           prop="name"
                           label="资源名称">
          </el-table-column>
          <el-table-column
                           prop="model"
                           label="模块">
          </el-table-column>
          <el-table-column
            prop="description"
            label="描述">
          </el-table-column>
          <el-table-column
            prop="url"
            label="url">
          </el-table-column>
          <el-table-column
            prop="versionNumber"
            label="版本号">
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作">
            <template slot-scope="scope"  v-if="scope.row.hasChildren==false">
              <el-button type="text" size="small"  @click="edit(scope.row)">编辑</el-button>
              <el-button type="text" size="small" @click="deleteResource(scope.row)">删除</el-button>
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
    name: 'ResourceList',
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
      this.loadResource(1,this.pageSize)
    },
    methods: {
      edit(row) {
        this.$router.push({name:"ResourceForm", params: {id: row.id}})
      },
      reset() {
        this.$refs["queryForm"].resetFields();
      },
      onSubmit() {
        this.loadResource(1,this.pageSize)
      },
      deleteResource(row) {
        let pid=row.id
        let context = this.maps.get(pid)
        this.$confirm('此操作将永久删除数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let maps = this.maps;
          this.$request.post({
            url: '/spring-resource/resource/deleteResource',
            data: {
              resourceId:row["id"]
            },
            success: result => {
              this.load(context[0],context[1],context[2]);
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
      load(tree,node, resolve) {
        let parentId=tree.id
        this.maps.set(parentId,[tree,node,resolve])
        this.$request.get({
          url: '/spring-resource/resource/findResourceDetails',
          config: {
            params: {
              appId: parentId
            }
          },
          success: result => {
            resolve(result.data);
          },
          error: e => {
            resolve([]);
            this.$message.error(e)
          }
        })
      },
      loadResource(page,pageSize) {
        this.$request.post({
          url: '/spring-resource/resource/findResource',
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
