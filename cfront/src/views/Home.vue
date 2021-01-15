<template>
  <div class="home">
    <el-form :inline="true" class="form" ref="form" :model="form" label-width="0" align="center">
      <el-form-item label="">
        <el-input style="width: 200px" size="small" placeholder="File path" v-model="form.path"></el-input>
      </el-form-item>
      <el-form-item label="">
          <el-input size="small" placeholder="From page" v-model="form.from"></el-input>
      </el-form-item>
      <el-form-item label="">
          <el-input size="small" placeholder="To page" v-model="form.to"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button  type="success" size="small" @click="onSubmit">Extract</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
  export default {
    data () {
      return {
        form: {
          path: '',
          from: '',
          to: ''
        }
      }
    },
    methods: {
        onSubmit() {
          console.log(this.form.path);
          console.log(this.form.from);
          console.log(this.form.to);
          this.$axios.post('/extract', {
            path: this.form.path,
            from: this.form.from,
            to: this.form.to
          }).then()

        }
    }
  }
</script>

<style lang="scss" scoped>
  .form {
    border-radius: 10px;
    margin: 0px auto;
    width: 420px;
    padding: 30px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    text-align: left;
    box-shadow: 0 0 20px 2px rgba(0, 0, 0, 0.1);
  }
  .el-input {
    width: 95px;
  }
</style>
