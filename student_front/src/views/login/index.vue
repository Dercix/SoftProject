<template >
    <div>
        <el-container>
            <el-header>
                <div style="text-align: center; font-size: 25px; font-weight: bolder">
                    选课管理系统
                </div>
            </el-header>
            <el-main >
                <el-card class="login-module">
                    <div slot="header" class="clearfix">
                        <span style="text-align: center; font-size: 20px;   font-family: 'Microsoft YaHei'">
                          <p><i class="el-icon-office-building" style="margin-right: 18px"></i>登陆</p>
                        </span>
                    </div>
                    <div>
                        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
                                 class="demo-ruleForm">
                            <el-form-item label="用户 id" prop="id">
                                <el-input v-model.number="ruleForm.id" placeholder="请输入id"
                                          prefix-icon="el-icon-lollipop"></el-input>
                            </el-form-item>
                            <el-form-item label="用户密码" prop="password">
                                <el-input v-model="ruleForm.password" placeholder="请输入密码" show-password
                                          prefix-icon="el-icon-ice-cream-round"></el-input>
                            </el-form-item>
                            <el-form-item label="用户类型" prop="type">
                                <el-radio-group v-model="ruleForm.type">
                                    <el-radio label="student" value="student">学生</el-radio>
                                    <el-radio label="teacher" value="teacher">老师</el-radio>
                                    <el-radio label="admin" value="admin">admin</el-radio>
                                </el-radio-group>
                            </el-form-item>
                            <el-form-item>
                                <div style="display: flex">
                                    <el-input prefix-icon="el-icon-key" v-model="ruleForm.validCode" style="width: 50%;"
                                              placeholder="请输入验证码"></el-input>
                                    <ValidCode @input="createValidCode"></ValidCode>
                                </div>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="submitForm('ruleForm')">登陆</el-button>
                                <el-button type="primary" @click="register()">注册</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                </el-card>
            </el-main>
        </el-container>
    </div>
</template>

<script>
import ValidCode from "../../components/ValidCode";

export default {
    components: {
        ValidCode
    },
    data() {
        const id_validator = (rule, value, callback) => {
            if (/[a-zA-Z]/.test(value) === true) {
                callback(new Error("请输入用户 id"))
            } else if (value > 999999999999) {
                callback(new Error("长度在 1 到 12 个数字"))
            }else {
                callback();
            }
        }
        const pass_validator = (rule, value, callback) =>{
            if(value.length > 15 || value.length < 6){
                callback(new Error("长度在 6 到 15 个字符"))
            }else {
                callback();
            }
        }
        return {
            ruleForm: {
                id: null,
                password: null,
                type: null,
            },
            rules: { //表单校验规则
                id: [
                    {validator: id_validator, trigger: 'blur'},
                ],
                password: [
                    {required: true, message: '请输入密码', trigger: 'blur'},
                    // {validator: pass_validator, trigger: 'blur'},
                ],
                type: [
                    {required: true, message: '请选择', trigger: 'change'}
                ],
                name: [
                    {required: true, message: '请输入姓名', trigger: 'blur'},
                    {
                        validator: function (rule, value, callback) {//校验中文的正则：/^[\u4e00-\u9fa5]{0,}$
                            if (/^[\u4e00-\u9fa5]*$/.test(value) === false) {
                                callback(new Error("请输入中文"));
                            } else {
                                //校验通过
                                callback();
                            }
                        },
                    }

                ],
                validCode: ''
            }
        };
    },
    methods: {
        register() {
            this.$router.push("/register");
        },
        createValidCode(data) {
            this.validCode = data;
        },
        submitForm(formName) {
            const that = this
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    let check = false
                    let name = null
                    // 获取当前学期
                    axios.get('http://localhost:10086/info/getCurrentTerm').then(function (resp) {
                        sessionStorage.setItem("currentTerm", resp.data)
                    })

                    axios.get('http://localhost:10086/info/getForbidCourseSelection').then(function (resp) {
                        sessionStorage.setItem("ForbidCourseSelection", resp.data)
                    })

                    // 教师或admin登录
                    if (that.ruleForm.type === 'admin' || that.ruleForm.type === 'teacher') {
                        let form = {tid: that.ruleForm.id, password: that.ruleForm.password}
                        let codeForm = {inputCode: that.ruleForm.validCode, orCode: that.validCode}
                        let checkCode
                        axios.post("http://localhost:10086/teacher/validCode", codeForm).then(function (resp) {
                            console.log("验证码: " + resp.data);
                            checkCode = resp.data;
                            if (checkCode === false) {
                                that.$message({
                                    showClose: true,
                                    message: '验证码输入错误',
                                    type: 'error'
                                });
                            }
                        })

                        // 记得删
                        console.log("aa12323a")
                        console.log(form)
                        // 检验教师id是否存在(login)
                        axios.post("http://localhost:10086/teacher/login", form).then(function (resp) {
                            //  记得删
                            console.log("教师登陆验证信息：" + resp.data)
                            check = resp.data
                            if (checkCode === true) {
                                if (check === true) {
                                    axios.get("http://localhost:10086/teacher/findById/" + that.ruleForm.id).then(function (resp) {
                                        console.log("登陆页正在获取用户信息" + resp.data.tid);
                                        name = resp.data.tname

                                        sessionStorage.setItem("token", 'true')// 登陆状态
                                        sessionStorage.setItem("type", that.ruleForm.type)// 登录角色
                                        sessionStorage.setItem("name", name)// 登陆姓名
                                        sessionStorage.setItem("id", resp.data.tid)// 登录id(that.ruleForm.id)
                                        sessionStorage.setItem("logout", "teacher");// 用于退出

                                        console.log('name: ' + name + ' ' + that.ruleForm.type + ' ' + resp.data.tid)

                                        if (that.ruleForm.type === 'admin' && name === 'admin') {
                                            that.$message({
                                                showClose: true,
                                                message: '登陆成功，欢迎 ' + name + '!',
                                                type: 'success'
                                            });
                                            that.$router.push('/admin')
                                        } else if (that.ruleForm.type === 'teacher' && name !== 'admin') {
                                            that.$message({
                                                showClose: true,
                                                message: '登陆成功，欢迎 ' + name + '!',
                                                type: 'success'
                                            });
                                            that.$router.push('/teacher')
                                        } else {
                                            that.$message({
                                                showClose: true,
                                                message: 'admin 登陆失败，检查登陆类型',
                                                type: 'error'
                                            });
                                        }
                                    })
                                } else {
                                    that.$message({
                                        showClose: true,
                                        message: '登陆失败，检查账号密码',
                                        type: 'error'
                                    });
                                }
                            }
                        })
                    }
                    //学生登录
                    else if (that.ruleForm.type === 'student') {
                        let form = {sid: that.ruleForm.id, password: that.ruleForm.password}
                        let codeForm = {inputCode: that.ruleForm.validCode, orCode: that.validCode}
                        let checkCode
                        axios.post("http://localhost:10086/student/validCode", codeForm).then(function (resp) {
                            console.log("验证码: " + resp.data);
                            checkCode = resp.data;
                            if (checkCode === false) {
                                that.$message({
                                    showClose: true,
                                    message: '验证码输入错误',
                                    type: 'error'
                                });
                            }
                        })


                        axios.post("http://localhost:10086/student/login", form).then(function (resp) {
                            console.log("学生登陆验证信息：" + resp.data)
                            check = resp.data
                            if (checkCode === true) {
                                if (check === true) {
                                    axios.get("http://localhost:10086/student/findById/" + that.ruleForm.id).then(function (resp) {
                                        console.log("登陆页正在获取用户信息" + resp.data)
                                        name = resp.data.sname

                                        sessionStorage.setItem("token", 'true')
                                        sessionStorage.setItem("type", that.ruleForm.type)
                                        sessionStorage.setItem("name", name)
                                        sessionStorage.setItem("id", resp.data.sid)
                                        sessionStorage.setItem("logout", "student");// 用于退出

                                        that.$message({
                                            showClose: true,
                                            message: '登陆成功，欢迎 ' + name + '!',
                                            type: 'success'
                                        });

                                        console.log('正在跳转：' + '/' + that.ruleForm.type)

                                        // 3. 路由跳转
                                        that.$router.push({
                                            path: '/' + that.ruleForm.type,
                                            query: {}
                                        })
                                    })
                                } else {
                                    that.$message({
                                        showClose: true,
                                        message: '账号密码错误，请联系管理员',
                                        type: 'error'
                                    });
                                }
                            }
                        })
                    } else {
                        console.log("! 错误的用户类型")
                    }
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
    }
}
</script>

<style>
.login-module {
    /*width: 380px;*/
    /*height: 325px;*/
    margin-top: 60px;
    /*border: none;*/
    position: absolute;
    right: 500px;
    text-align: center;
    width: 30%;

}

.el-header {
    background-color: white;
    color: #333;
    line-height: 60px;
}


</style>