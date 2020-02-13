<template>
  <div>
    <position :locations="locations"></position>

        <el-form :model="form" :rules="rules" ref="form" label-width="80px" size="small">
          <el-form-item label="模块名称" prop="model">
            <el-input v-model="form.model" :disabled="true" placeholder="模块名称"></el-input>
          </el-form-item>
          <el-form-item label="名称" prop="name">
            <el-input v-model="form.name" placeholder=名称></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="description">
            <el-input  type="textarea" :rows="3" style="width: 40%" v-model="form.description" placeholder=描述></el-input>
          </el-form-item>
          <el-form-item label="url" prop="url">
            <el-input v-model="form.url" :disabled="true" placeholder=URL></el-input>
          </el-form-item>
          <el-form-item label="版本号"  prop="versionNumber">
            <el-input v-model="form.versionNumber" :disabled="true" placeholder=versionNumber></el-input>
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
    watch: {
      "selectKeys"() {
        this.$refs.authenticationTree.setCheckedKeys(this.selectKeys)
      }
    },
    data() {
      return {
        form: {
          id: null,
          name: null,
          model: null,
          description: null,
          url: null,
          versionNumber: null
        },
        rules: {
          name: [
            {required: true, message: '请输入名称', trigger: 'blur'},
            {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
          ],
          description: [
            {required: true, message: '请输入描述', trigger: 'blur'},
            {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
          ]
        },
        locations: [
          {
            name: "资源管理",
            path: "/resource"
          },
          {
            name: "修改资源",
          }
        ]
      };
    },
    mounted() {
      if (this.$route.params.id) {
        this.loadResource(this.$route.params.id)
      }
    },
    methods: {
      onSubmit() {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            // this.fullscreenLoading = true;
            this.$request.post({
              url: '/spring-resource/resource/updateResource',
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
        this.$router.push({path: "/resource"})
      },
      loadResource(id) {
        this.$request.get({
          url: '/spring-resource/resource/findResourceById',
          config: {
            params: {
              resourceId: id
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
