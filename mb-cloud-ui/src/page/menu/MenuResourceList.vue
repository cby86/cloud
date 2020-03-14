<template>
  <div class="content">
    <position :locations="locations"></position>
    <el-row>
      <el-col>
        <el-form :inline="true" :model="queryForm" ref="queryForm" class="demo-form-inline">
          <el-form-item label="应用名称" prop="appName">
            <el-input size="small" v-model="queryForm.appName" placeholder="应用名称"></el-input>
          </el-form-item>
          <el-form-item label="资源名称" prop="name">
            <el-input size="small" v-model="queryForm.name" placeholder="资源名称"></el-input>
          </el-form-item>
          <el-form-item label="资源URL" prop="url">
            <el-input size="small" v-model="queryForm.url" placeholder="资源URL"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="small" icon="el-icon-search" @click="onSubmit">查询</el-button>
            <el-button type="primary" size="small" icon="el-icon-reset" @click="reset">清空</el-button>
            <el-button type="primary" size="small" icon="el-icon-reset" @click="add">新增</el-button>
            <el-button type="primary" size="small" icon="el-icon-reset" @click="batchDelete">批量删除</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-table size="small"
                  @selection-change="selectionChange"
                  :data="tableData"
                  style="width: 100%">
          <el-table-column
            type="selection">
          </el-table-column>
          <el-table-column sortable
                           prop="appName"
                           label="应用名称">
          </el-table-column>
          <el-table-column sortable
                           prop="name"
                           label="资源名称">
          </el-table-column>
          <el-table-column sortable
                           prop="url"
                           label="资源URL">
          </el-table-column>
          <el-table-column sortable
                           prop="versionNumber"
                           label="版本号">
          </el-table-column>
          <el-table-column
            prop="description"
            label="描述">
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="deleteResource([scope.row.id])">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <page-nation :totalCount="totalCount" :pageCount="pageCount" v-on:pageChange="loadResource"></page-nation>
    <resource-selector-dialog title="添加资源" width="70%" ref="resourceSelect" @submit="resetResource"
                              v-if="queryForm.menuId"></resource-selector-dialog>
  </div>
</template>

<script>
  import PageNation from "../../components/PageNation"
  import Position from "../../components/Postion"
  import ResourceSelectorDialog from "../resource/ResourceSelectorDialog"

  export default {
    name: 'MenuResourceList',
    components: {
      PageNation,
      Position,
      ResourceSelectorDialog
    },
    data() {
      return {
        queryForm: {
          appName: null,
          name: null,
          menuId: null,
          url: null
        },
        tableData: [],
        totalCount: 1,
        pageCount: 0,
        pageSize: 10,
        locations: [
          {
            name: "菜单管理",
            path: "/menu"
          },
          {
            name: "绑定资源",
          }
        ],
        selection: []
      };
    },
    mounted() {
      if (this.$route.params.id) {
        this.queryForm.menuId = this.$route.params.id,
          this.loadResource(1, this.pageSize)
      } else {
        this.$router.push({path: "/menu"})
      }
    },
    methods: {
      selectionChange(selection) {
        this.selection = selection;
      },
      resetResource(resourceIds) {
        this.$request.post({
          url: '/spring-resource/menu/bindResources',
          data: {
            menuId: this.queryForm.menuId,
            resourceIds: resourceIds.toString()
          },
          success: result => {
            this.loadResource(1, this.pageSize)
          },
          error: e => {
            this.$message.error(e)
          }
        })
      },
      reset() {
        this.$refs["queryForm"].resetFields();
      },
      onSubmit() {
        this.loadResource(1, this.pageSize)
      },
      add() {
        this.$refs.resourceSelect.show();
      },
      batchDelete() {
        let resourceIds = [];
        if (this.selection.length == 0) {
          this.$message({
            type: 'info',
            message: '请选择要删除的记录'
          });
          return;
        }
        this.selection.forEach(item => {
          resourceIds.push(item.id)
        });
        this.deleteResource(resourceIds)
      },
      deleteResource(resourceIds) {
        this.$confirm('此操作将永久删除数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$request.post({
            url: '/spring-resource/menu/unBindResource',
            data: {
              menuId: this.queryForm.menuId,
              resourceIds: resourceIds.toString()
            },
            success: result => {
              this.loadResource(1, this.pageSize)
            },
            error: e => {
              this.$message.error(e)
            }
          });
        }).catch((e) => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      loadResource(page, pageSize) {
        this.$request.post({
          url: '/spring-resource/menu/findBindResource',
          data: {
            name: this.queryForm.name,
            url: this.queryForm.url,
            menuId: this.queryForm.menuId,
            appName: this.queryForm.appName,
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
  }
  ;
</script>
