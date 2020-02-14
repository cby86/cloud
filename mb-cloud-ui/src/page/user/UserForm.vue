<template>
  <div>
    <position :locations="locations"></position>

        <el-form :model="form" :rules="rules" ref="form" label-width="80px" size="small">
          <el-form-item label="用户名" prop="model">
            <el-input v-model="form.username"  placeholder="用户名"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password" >
            <el-input v-model="form.password" type="password" placeholder=密码></el-input>
          </el-form-item>
          <el-form-item label="角色" prop="menuType">
            <el-select size="small" multiple  v-model="form.roleIds" placeholder="请选择">
              <el-option
                v-for="item in roleList"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
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
    name: 'UserForm',
    components: {
      Position
    },
    data() {
      return {
        roleList:[],
        form: {
          id: null,
          username: null,
          password: null,
          roles:null,
          roleIds:[]
        },
        rules: {
          username: [
            {required: true, message: '请输入名称', trigger: 'blur'},
            {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入描述', trigger: 'blur'},
            {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
          ]
        },
        locations: [
          {
            name: "用户管理",
            path: "/user"
          },
          {
            name: "编辑用户",
          }
        ]
      };
    },
    mounted() {
      this.loadRoles()
      if (this.$route.params.id) {
        this.loadUser(this.$route.params.id)
      }
    },
    methods: {
      onSubmit() {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            // this.fullscreenLoading = true;
            this.$request.post({
              url: '/spring-user/user/updateUser',
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
      },
      cancel() {
        this.$router.push({path: "/user"})
      },
      loadRoles() {
        this.$request.get({
          url: '/spring-user/role/findAllRoles',
          success: result => {
            this.roleList = result.data;
          },
          error: e => {
            this.$message.error(e)
          }
        })
      }
      ,
      loadUser(id) {
        this.$request.get({
          url: '/spring-user/user/findUserById',
          config: {
            params: {
              userId: id
            }
          },
          success: result => {
            this.$utils.copyFromTo(result.data, this.form);
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
