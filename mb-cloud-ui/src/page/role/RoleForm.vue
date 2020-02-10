<template>
  <div>
    <position :locations="locations"></position>
    <el-row>
      <el-col :span="6">
        <el-form :model="form" :rules="rules" ref="form" label-width="80px" size="small">
          <el-form-item label="角色名称" prop="menuName">
            <el-input v-model="form.name" placeholder="角色名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="onSubmit">保存</el-button>
            <el-button type="info" icon="el-icon-search" @click="cancel">取消</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
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
        form: {
          id:null,
          name: null
        },
        rules: {
          name: [
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
      if(this.$route.params.id) {
        this.loadMenu(this.$route.params.id)
      }
    },
    methods: {
      onSubmit() {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            // this.fullscreenLoading = true;
            this.$request.post({
              url: '/spring-user/role/updateRole',
              data: this.menuForm,
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
      },
      cancel() {
        this.$router.push({path: "/role"})
      },
      loadRole(id){
        this.$request.get({
          url: '/spring-user/role/findRoleById',
          config: {
            params:{
              menuId:id
            }
          },
          success: result => {
            this.$utils.copyFromTo(result.data,this.form)
          },
          error: e => {
            this.$message.error(e)
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
