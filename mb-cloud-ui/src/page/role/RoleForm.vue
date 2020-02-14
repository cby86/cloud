<template>
  <div>
    <position :locations="locations"></position>

    <el-form :model="form" :rules="rules" ref="form" label-width="80px" size="small">
      <el-form-item label="角色名称" prop="name">
        <el-input v-model="form.name" placeholder="角色名称"></el-input>
      </el-form-item>
      <el-form-item label="编码" prop="code">
        <el-input v-model="form.code" placeholder=角色编码></el-input>
      </el-form-item>
      <el-form-item label="授权">
        <el-tree ref="authenticationTree" :default-checked-keys="selectKeys"
                 :props="props"
                 :check-on-click-node="true" :check-strictly="checkStrictly"
                 :load="loadNode" :node-key="props.value"
                 lazy
                 show-checkbox
                 @check="checkAuth">
        </el-tree>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="onSubmit">保存</el-button>
        <el-button type="info" icon="el-icon-search" @click="cancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import Position from "../../components/Postion"
  // @ is an alias to /src
  export default {
    name: 'RoleForm',
    components: {
      Position
    },
    data() {
      return {
        checkStrictly: true,
        selectKeys: null,
        props: {
          value: "id",
          label: "menuName",
          isLeaf: "leaf"
        },
        form: {
          id: null,
          name: null,
          code: null,
          authentications: null
        },
        rules: {
          name: [
            {required: true, message: '请输入角色名称', trigger: 'blur'},
            {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
          ],
          code: [
            {required: true, message: '请输入角色名称', trigger: 'blur'},
            {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
          ]
        },
        locations: [
          {
            name: "角色管理",
            path: "/role"
          },
          {
            name: "新增角色",
          }
        ]
      };
    },
    mounted() {
      if (this.$route.params.id) {
        this.loadRole(this.$route.params.id)
      }
    },
    methods: {
      checkAuth(node, status) {
        let auth = new Array();
        status.checkedNodes.forEach((item)=> {
          auth.push({
            id:item["id"],
            name:item["menuName"],
            url:item["url"],
            parentId:item["parentId"],
            authentionType:item["menuType"]==="Menu"?0:1
          })
        })
        status.halfCheckedNodes.forEach((item)=> {
          auth.push({
            id:item["id"],
            name:item["menuName"],
            url:item["url"],
            parentId:item["parentId"],
            icon:item["icon"],
            authentionType:item["menuType"]==="Menu"?0:1
          })
        })
        this.form.authentications = auth;
      }
      ,
      loadNode(node, resolve) {
        this.checkStrictly = true;
        let parentId;
        if (node.level !== 0) {
          parentId = node.data["id"];
        }
        this.$request.get({
          url: '/spring-resource/menu/findMenuByParentId',
          config: {
            params: {
              parentId: parentId,
              excludeMenuId: this.$route.params.id
            }
          },
          success: result => {
            resolve(result.data);
            this.checkStrictly = false;
          },
          error: e => {
            this.checkStrictly = false;
            resolve([]);
            this.$message.error(e)
          }
        })
      }
      ,
      onSubmit() {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            // this.fullscreenLoading = true;
            this.$request.post({
              url: '/spring-user/role/updateRole',
              data: this.form,
              success: result => {
                this.$message({
                  type: 'success',
                  message: `更新成功`
                });
                this.cancel()
              },
              error: e => {
                this.$message.error(e)
              }
            })
          }
        })
      }
      ,
      cancel() {
        this.$router.push({path: "/role"})
      }
      ,
      loadRole(id) {
        this.$request.get({
          url: '/spring-user/role/findRoleById',
          config: {
            params: {
              roleId: id
            }
          },
          success: result => {
            this.$utils.copyFromTo(result.data, this.form);
            if (this.form.authentications) {
              let selectKeys = new Array();
              this.form.authentications.forEach(item => {
                selectKeys.push(item["id"])
              })
              this.selectKeys = selectKeys;
            }
          },
          error: e => {
            this.$message.error(e)
          }
        })
      }
    }
  };
</script>

<style scoped>

</style>
