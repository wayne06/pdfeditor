<template>
    <div class="about">
        <el-form :inline="true" class="form" ref="form"  label-width="0" align="center">
            <el-form-item label="">
                <!--        <el-input style="width: 200px" size="small" placeholder="File1 path" v-model="form.path1"></el-input>-->
                <el-upload
                        class="upload-demo"
                        accept=".pdf"
                        :limit="1"
                        drag
                        action="http://localhost:8443/api/upload"
                        :on-success="handleSuccess1"
                        :on-exceed="handleExceed">
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">Drag PDF here, or click to upload</div>
                </el-upload>
            </el-form-item>
            <el-form-item label="">
                <!--        <el-input style="width: 200px" size="small" placeholder="File2 path" v-model="form.path2"></el-input>-->
                <el-upload
                        class="upload-demo"
                        drag
                        action="http://localhost:8443/api/upload"
                        :on-success="handleSuccess2"
                        :on-exceed="handleExceed"
                        multiple>
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">Drag PDF here, or click <em>upload</em></div>
                </el-upload>
            </el-form-item>
            <el-form-item>
                <el-button :disabled="path1.length===0 || path2.length===0" type="success" size="small" @click="onSubmit">Merge</el-button>
            </el-form-item>
        </el-form>

    </div>
</template>

<script>
    export default {
        data() {
            return {
                path1: '',
                path2: ''
            }
        },
        methods: {
            onSubmit() {
                this.$axios.post('/merge', {
                    path1: this.path1,
                    path2: this.path2,
                }).then(resp => {
                    window.open(resp.data);
                })
            },
            handleSuccess1(response) {
                this.path1 = response;
                this.$message.warning('Upload success.');
                console.log(response);
            },
            handleSuccess2(response) {
                this.path2 = response;
                this.$message.warning('Upload success.');
                console.log(response);
            },
            handleExceed(files, fileList) {
                this.$message.warning(`Only 1 file permitted.`)
            },
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
