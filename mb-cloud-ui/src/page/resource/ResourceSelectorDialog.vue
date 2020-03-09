<template>
  <el-dialog :title="title" :visible.sync="dialogVisible" :width="width">
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
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-table size="small"
                  :data="tableData"
                  ref="multipleTable"
                  row-key="id" @selection-change="selectionChange"
                  style="width: 100%"
        >
          <el-table-column
            type="selection">
          </el-table-column>
          <el-table-column sortable
                           prop="appName"
                           label="应用名称">
          </el-table-column>
          <el-table-column sortable
                           prop="appDesc"
                           label="应用描述">
          </el-table-column>
          <el-table-column
            prop="model"
            label="模块">
          </el-table-column>
          <el-table-column sortable
                           prop="name"
                           label="资源名称">
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
        </el-table>
      </el-col>
    </el-row>
    <page-nation :totalCount="totalCount" :pageCount="pageCount" v-on:pageChange="loadResource"></page-nation>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="ok">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import PageNation from "../../components/PageNation"

  export default {
    name: 'ResourceSelectorDialog',
    components: {
      PageNation
    },
    props: {
      title: {
        default: null
      },
      width: {
        default: "50%"
      }
    },
    data() {
      return {
        dialogVisible: false,
        queryForm: {
          appName: null,
          name: null,
          url: null
        },
        allSelectKeys:[],
        selectKeys:[],
        tableData: [],
        totalCount: 1,
        pageCount: 0,
        pageSize: 10,
        currentPage:1
      };
    },
    mounted() {
      this.loadResource(1, this.pageSize)
    },
    methods: {
      ok() {
        this.sysAllSelectKeys(this.currentPage,this.selectKeys,false);
        this.dialogVisible = false
        console.log(this.allSelectKeys)
      },
      selectionChange(selection){
        this.selectKeys = selection;
        console.log(this.selectKeys)
        this.sysAllSelectKeys(this.currentPage,this.selectKeys,false)
      },
      show() {
        this.dialogVisible = true
      },
      reset() {
        this.$refs["queryForm"].resetFields();
      },
      onSubmit() {
        this.loadResource(1, this.pageSize)
      },
      sysAllSelectKeys(page,selectKeys,pageChange) {
        let pageSelect;
        this.allSelectKeys.forEach(item=>{
          if(item.page==page) {
            pageSelect = item;
          }
        })
        if(pageSelect){
          if(!pageChange){
            pageSelect.selectKeys = selectKeys;
          }
        }else {
          pageSelect = {page:page,selectKeys:selectKeys};
          this.allSelectKeys.push(pageSelect)
        }
        return pageSelect;
      },
      loadResource(page, pageSize) {
        this.currentPage = page;
        this.$request.post({
          url: '/spring-resource/resource/findResource',
          data: {
            name: this.queryForm.name,
            page: page - 1,
            name: this.queryForm.name,
            code: this.queryForm.url,
            appName: this.queryForm.appName,
            pageSize: pageSize
          },
          success: result => {
            this.tableData = result.data.items;
            this.totalCount = result.data.totalCount;
            this.pageCount = result.data.totalPage;
            let pageSelect=this.sysAllSelectKeys(this.currentPage,this.selectKeys,true);
            this.tableData.forEach(item => {
               pageSelect.selectKeys.forEach(selectItem=>{
                 if(selectItem.id==item.id){
                   this.$refs.multipleTable.toggleRowSelection(item,true);
                 }
               })
            });
          },
          error: e => {
            this.$message.error(e)
          }
        })
      }
    }
  };
</script>
