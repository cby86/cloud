<template>
  <div>
    <position :locations="locations"></position>
    <el-form :model="menuForm" :rules="rules" ref="menuForm" label-width="120px" size="small">
      <el-form-item label="父级菜单" prop="parentId">
        <select-tree
          :props="fieldsDefine"
          :value="{id:menuForm.parentId,label:menuForm.parentName}"
          :lazy="true"
          :load="loadChildrenMenu"
          @getValue="getValue($event)"/>
      </el-form-item>
      <el-form-item label="菜单/功能名称" prop="menuName">
          <el-input v-model="menuForm.menuName" placeholder="菜单/功能名称"></el-input>
      </el-form-item>
      <el-form-item label="编码" prop="code">
        <el-input v-model="menuForm.code" placeholder="唯一标识"></el-input>
      </el-form-item>
      <el-form-item label="图标" prop="icon">
        <el-input v-model="menuForm.icon" placeholder="图标"></el-input>
      </el-form-item>
      <el-form-item label="URL" prop="url">
        <el-input v-model="menuForm.url" placeholder="菜单URL"></el-input>
      </el-form-item>
      <el-form-item label="类型" prop="menuType">
        <el-select v-model="menuForm.menuType" @change="obtainValue">
          <el-option label="菜单" :value="'Menu'"></el-option>
          <el-option label="功能" :value="'Function'"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="排序" prop="url">
        <el-input v-model="menuForm.sort" placeholder="数字越小排在越前"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="onSubmit">保存</el-button>
        <el-button type="info" icon="el-icon-search" @click="cancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import SelectTree from "../../components/SelectTree";
  import Position from "../../components/Postion"
  // @ is an alias to /src
  export default {
    name: 'MenuForm',
    components: {
      SelectTree,
      Position
    },
    data() {
      return {
        fieldsDefine:{
           value:"id",
           label:"menuName",
           isLeaf:"hasChildren",
        },
        menuForm: {
          parentId:null,
          parentName:null,
          id:null,
          menuName: null,
          url: null,
          menuType: "Menu",
          code:null,
          icon:null,
          sort:0
        },
        rules: {
          menuName: [
            {required: true, message: '请输入菜单名称', trigger: 'blur'},
            {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
          ],
          code:[
            {required: true, message: '请输入编码', trigger: 'blur'},
          ]
        },
        locations: [
          {
            name: "菜单管理",
            path: "/menu"
          },
          {
            name: "新增菜单",
          }
        ]
      };
    },
    mounted() {
      this.menuForm.parentName = this.$route.params.parentName;
      this.menuForm.parentId = this.$route.params.parentId;
      if(this.$route.params.id) {
        this.loadMenu(this.$route.params.id)
      }
    },
    methods: {
      obtainValue(v) {
        this.menuForm.menuType = v;
      },
      onSubmit() {
        this.$refs["menuForm"].validate((valid) => {
          if (valid) {
            // this.fullscreenLoading = true;
            this.$request.post({
              url: '/spring-resource/menu/updateMenus',
              data: this.menuForm,
              success: result => {
                this.$message({
                  type: 'success',
                  message: `操作成功`
                });
                this.$router.push({path: "/menu"})
              },
              error: e => {
                this.$message.error(e)
              }
            })
          }
        })
      },
      cancel() {
        this.$router.push({path: "/menu"})
      },
      loadMenu(id){
        this.$request.get({
          url: '/spring-resource/menu/findMenuById',
          config: {
            params:{
              menuId:id
            }
          },
          success: result => {
            this.$utils.copyFromTo(result.data,this.menuForm)
          },
          error: e => {
            this.$message.error(e)
          }
        })
      },
      loadChildrenMenu(node, resolve) {
        let parentId;
        if (node.level !== 0) {
          parentId = node.data["id"];
        }
        this.$request.get({
          url: '/spring-resource/menu/findMenuByParentId',
          config: {
            params:{
              parentId:parentId,
              excludeMenuId:this.$route.params.id
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
      getValue(v) {
        this.menuForm.parentId = v;
      }
    }
  }
</script>

<style scoped>

</style>
