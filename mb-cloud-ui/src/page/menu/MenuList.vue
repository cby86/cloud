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
              <el-option label="全部" value="all"></el-option>
              <el-option label="菜单" value="Menu"></el-option>
              <el-option label="功能" value="Function"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="small" icon="el-icon-search" @click="onSubmit">查询</el-button>
            <el-button type="primary" size="small" icon="el-icon-reset" @click="reset">清空</el-button>
            <el-button type="primary" v-if="$store.getters.hasAuth('addMenu')" size="small" icon="el-icon-reset" @click="addMenu">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-table
          :data="tableData"
          ref="menuTable"
          row-key="id"
          lazy
          :load="load"
          :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
          <el-table-column
            prop="menuName"
            label="名称（菜单/功能）"
            sortable>
          </el-table-column>
          <el-table-column
            prop="url"
            label="url"
            sortable>
          </el-table-column>
          <el-table-column
            prop="code"
            label="唯一标识"
            sortable>
          </el-table-column>
          <el-table-column
            prop="icon"
            label="图标"
            sortable>
          </el-table-column>
          <el-table-column
            prop="menuType"
            label="菜单类型" :formatter="(row, column, cellValue)=>{return cellValue === 'Menu' ? '菜单' : '功能';}"
            sortable>
          </el-table-column>
          <el-table-column
            prop="forPrivate"
            label="是否私有" :formatter="(row, column, cellValue)=>{return cellValue === true ? '是' : '否';}"
            sortable>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" v-if="$store.getters.hasAuth('addMenu') && scope.row.menuType=='Menu'"  @click="addMenu(scope.row)">新增</el-button>
              <el-button type="text" size="small" v-if="$store.getters.hasAuth('editMenu')"  @click="edit(scope.row)">编辑</el-button>
              <el-button type="text" size="small" v-if="$store.getters.hasAuth('deleteMenu')" @click="deleteMenu(scope.row)">删除</el-button>
              <el-button type="text" size="small" v-if="$store.getters.hasAuth('bindResource')" @click="bindResource(scope.row)">资源绑定</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
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
          menuType: "all"
        },
        tableData: [],
        totalCount: 1,
        pageCount: 0,
        pageSize: 10
      };
    },
    mounted() {
      this.bus.$on('refreshMenu', (parentId) => {
       if(parentId) {
          this.refreshRow(parentId)
       }else {
         this.init()
       }
      });
      this.init()
    },
    methods: {
      bindResource(row) {
        this.$router.push({name: "MenuResourceList", params: {id: row.id}})
      },
      init(){
        this.loadMenus(null,(data)=>{
          this.dataResolver(data);
          this.tableData = data;
        })
      },
      dataResolver(data) {
        if(data) {
          data.forEach(item=>{
            item.hasChildren = !item.leaf;
          })
        }
      },
      refreshRow(id) {
        this.loadMenus(id, (data) => {
          this.dataResolver(data);
          this.$set(this.$refs.menuTable.store.states.lazyTreeNodeMap, id, data)
        });
      },
      deleteMenu(row) {
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
              if(row.parentId) {
                this.refreshRow(row.parentId)
              }else {
                this.init();
              }
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
        this.loadMenus(null,(data)=>{
          this.dataResolver(data);
          this.tableData = data;
        })
      },
      addMenu(row) {
        this.$router.push({name: "MenuForm", params: {parentName: row.menuName,parentId:row.id}})
      },
      loadMenus(parentId,callback) {
        let parameters = {
          name:this.queryForm.menuName,
          url:this.queryForm.url,
          parentId:parentId
        };
        if(this.queryForm.menuType!="all"){
          parameters.menuType = this.queryForm.menuType;
        }
        this.$request.post({
          url: '/spring-resource/menu/findMenuByParentId',
          data: parameters,
          success: result => {
            callback(result.data);
          },
          error: e => {
            this.$message.error(e)
          }
        })

      },
      load(row, treeNode, resolve) {
        this.loadMenus(row.id,(data)=>{
          this.dataResolver(data);
          resolve(data);
        })
      }
    }
  };
</script>
