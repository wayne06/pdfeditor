<template>
  <div class="about">
    <el-form :inline="true" class="form" ref="form" :model="form" label-width="0" align="center">
      <el-form-item label="">
<!--        <el-input style="width: 200px" size="small" placeholder="File1 path" v-model="form.path1"></el-input>-->
        <el-upload
                class="upload-demo"
                drag
                action="https://jsonplaceholder.typicode.com/posts/"
                multiple>
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">Drag PDF here, or click <em>upload</em></div>
        </el-upload>
      </el-form-item>
      <el-form-item label="">
<!--        <el-input style="width: 200px" size="small" placeholder="File2 path" v-model="form.path2"></el-input>-->
        <el-upload
                class="upload-demo"
                drag
                action="https://jsonplaceholder.typicode.com/posts/"
                multiple>
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">Drag PDF here, or click <em>upload</em></div>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button  type="success" size="small" @click="onSubmit">Merge</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
  export default {
    data () {
      return {
        form: {
          path1: '',
          path2: ''
        }
      }
    },
    methods: {
      onSubmit() {
        console.log(this.form.path1);
        console.log(this.form.path2);
        this.$axios.post('/merge', {
          path1: this.form.path1,
          path2: this.form.path2
        }).then()

      }
    }
  }
</script>

<style lang="scss" scoped>
  .form {
    border-radius: 10px;
    margin: 0px auto;
    width: 360px;
    padding: 30px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    text-align: left;
    box-shadow: 0 0 20px 2px rgba(0, 0, 0, 0.1);
  }
  .el-input {
    width: 170px;
  }
</style>
