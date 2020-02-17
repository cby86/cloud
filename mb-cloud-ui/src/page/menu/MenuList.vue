<template>
  <div class="content">
    <el-row>
      <el-col>
        <el-form :inline="true" :model="queryForm" ref="queryForm" class="demo-form-inline">
          <el-form-item label="菜单名称" prop="menuName">
            <el-input size="small" v-model="queryForm.menuName" placeholder="菜单名称"></el-input>
          </el-form-item>
          <el-form-item label="菜单URL" prop="url">
            <el-input size="small" v-model="queryForm.url" placeholder="菜单URL"></el-input>
          </el-form-item>
          <el-form-item label="菜单类型" prop="menuType">
            <el-select size="small" v-model="queryForm.menuType" placeholder="请选择" @change="obtainValue">
              <el-option label="全部" :value=-1></el-option>
              <el-option label="菜单" :value=0></el-option>
              <el-option label="功能" :value=1></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary"  size="small" icon="el-icon-search" @click="onSubmit">查询</el-button>
            <el-button type="primary"  size="small" icon="el-icon-reset" @click="reset">清空</el-button>
            <el-button type="primary" v-if="this.$store.getters.hasAuth('addMenu')" size="small" icon="el-icon-reset" @click="addMenu">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <Grid :tableData="tableData" :columns="columns"></Grid>
      </el-col>
    </el-row>
    <page-nation :totalCount="totalCount" :pageCount="pageCount" v-on:pageChange="loadMenus"></page-nation>
  </div>
</template>

<script>
  import PageNation from "../../components/PageNation"
  import Grid from "../../components/Grid"

  export default {
    name: 'MenuList',
    components: {
      PageNation,
      Grid
    },
    data() {
      return {
        queryForm: {
          menuName: null,
          url: null,
          menuType: -1
        },
        tableData: [],
        columns: [
          {
            name: "menuName",
            label: "菜单名称",
            sortable: true,
            show:true
          },
          {
            name: "url",
            label: "url",
            show:true
          },
          {
            name: "menuType",
            label: "菜单类型",
            show:true,
            formatter(row, column, cellValue) {
              return cellValue === "Menu" ? "菜单" : "功能";
            }
          },
          {
            label: "操作",
            actions: [
              {
                name: "编辑",
                handler: this.edit
              },
              {
                name: "删除",
                handler: this.delete
              }
            ]
          }
        ],
        totalCount: 1,
        pageCount: 0,
        pageSize: 10
      };
    },
    mounted() {
      this.loadMenus(1, this.pageSize)
    },
    methods: {
      delete(row) {
        this.$confirm('此操作将永久删除数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$request.post({
            url: '/spring-resource/menu/deletedMenu',
            data: {
              menuId:row["id"]
            },
            success: result => {
              this.loadMenus(1, this.pageSize)
            },
            error: e => {
              this.$message.error(e)
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });

      },
      edit(row) {
        this.$router.push({name: "MenuForm", params: {id: row.id}})
      },
      reset() {
        this.$refs["queryForm"].resetFields();
      },
      obtainValue(v) {
        this.queryForm.menuType = v;
      },
      onSubmit() {
        this.loadMenus(1, this.pageSize)
      },
      addMenu() {
        this.$router.push({name: "MenuForm"})
      },
      loadMenus(page, pageSize) {
        this.$request.post({
          url: '/spring-resource/menu/findMenus',
          data: {
            menuType:this.queryForm.menuType,
            page: page - 1,
            name:this.queryForm.menuName,
            url:this.queryForm.url,
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
