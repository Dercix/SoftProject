<template>
    <div class="login-wrap">
        <el-header >
            <div style="text-align: center; font-size: 25px; font-weight: bolder">
                <i class="el-icon-s-home" style="margin-right: 25px"></i>
                选课管理系统
            </div>
        </el-header>
        <el-form class="login-container" :model="ruleForm" :rules="rules" ref="ruleForm">

            <h1 class="title">用户注册：</h1>
            <el-form-item label="用户 id" prop="sid">
                <el-input placeholder="用户id" v-model.number="ruleForm.sid" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="用户姓名" prop="sname">
                <el-input placeholder="用户姓名" v-model="ruleForm.sname" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="用户密码" prop="password">
                <el-input type="password" placeholder="用户密码" v-model="ruleForm.password"
                          autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="用户重复密码" prop="password1">
                <el-input type="password" placeholder="确认密码" v-model="ruleForm.password1" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="doRegister('ruleForm')" style="width: 48%;">注册</el-button>
                <el-button type="primary" @click="toLogin" style="width: 48%;">返回登录</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
export default {
    data() {

        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.ruleForm.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        const id_validator = (rule, value, callback) =>{
            if (/[a-zA-Z]/.test(value) === true) {
                callback(new Error("请输入用户 id"))
            } else if(value > 999999999999){
                callback(new Error("长度在 1 到 12 个数字"))
            }else {
                callback();
            }
        }
        const pattern = /[?/,.@!$%^&()]/im;
        const pass_validator = (rule, value, callback) =>{
            if(value.length > 15 || value.length < 6){
                callback(new Error("长度在 6 到 15 个字符"))
            }else if(pattern.test(value) === false){
                callback(new Error("密码中必须包含特殊字符"))
            }
            else {
                callback();
            }
        }
        return {
            ruleForm: {
                sid: null,
                sname: null,
                password: null,
                password1: null,
            },
            rules: { //表单校验规则
                sid: [
                    {validator: id_validator,trigger: 'blur' },
                ],
                sname: [
                    {required: true, message: '请输入用户姓名', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: '请输入密码', trigger: 'blur'},
                    {validator: pass_validator, trigger: 'blur'}
                ],
                password1: [
                    { validator: validatePass, trigger: 'blur'},
                    {validator: pass_validator, trigger: 'blur'}
                ],
            }
        }
    },
    methods: {
        doRegister(formName) {
            const that = this;
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    // 通过前端校验
                    const that = this
                    delete this.ruleForm.password1;
                    axios.post("http://localhost:10086/student/addStudent", this.ruleForm).then(function (resp) {
                        console.log(resp.data)
                        if (resp.data === true) {
                            that.$message({
                                showClose: true,
                                message: '注册成功',
                                type: 'success'
                            });
                        } else {
                            that.$message.error('注册失败，学号或姓名错误，请重试');
                        }
                        that.$router.push("/login")
                    })
                } else {
                    that.$message({
                        showClose: true,
                        message: '注册失败，学号或姓名错误',
                        type: 'error'
                    });
                }
            });
        },
        toLogin: function () {
            this.$router.push('/login');
        }
    }
}
</script>

<style>
.login-wrap {
    box-sizing: border-box;
    width: 100%;
    height: 100%;
    padding-top: 0;
    /* background-color: #112346; */
    background-repeat: no-repeat;
    background-position: center right;
    background-size: 100%;
}

.login-container {
    border-radius: 10px;
    margin: 0px auto;
    margin-top: 30px;
    width: 350px;
    padding: 30px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    text-align: left;
    box-shadow: 0 0 20px 2px rgba(0, 0, 0, 0.1);
}

.title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
}
</style>
