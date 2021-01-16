<template>
    <div class="home">
        <el-form :inline="true" class="form" ref="form" :model="form" label-width="0" align="center">
            <el-form-item label="">
                <!--        <el-input style="width: 200px" size="small" placeholder="File path" v-model="form.path"></el-input>-->
                <el-upload
                        :limit="1"
                        accept=".pdf"
                        class="upload-demo"
                        drag
                        action="http://localhost:8443/api/upload"
                        :on-success="handleSuccess"
                        :on-exceed="handleExceed"
                >
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">Drag PDF here, or click to upload</div>
                </el-upload>
            </el-form-item>
            <el-form-item label="">
                <el-input size="small" placeholder="From page" v-model="form.from"></el-input>
            </el-form-item>
            <el-form-item label="">
                <el-input size="small" placeholder="To page" v-model="form.to"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button :disabled="disabled" type="success" size="small" @click="onSubmit">Extract</el-button>
            </el-form-item>

        </el-form>

    </div>
</template>

<script>
    export default {
        data() {
            return {
                path: '',
                form: {
                    from: '',
                    to: ''
                },
                disabled: true,
            }
        },
        methods: {
            onSubmit() {
                console.log(this.form.from);
                console.log(this.form.to);
                this.$axios.post('/extract', {
                    path: this.path,
                    from: this.form.from,
                    to: this.form.to
                }).then(resp => {
                    console.log(resp);
                    window.open(resp.data);
                })

            },
            handleSuccess(response) {
                this.path = response;
                this.disabled = false;
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
