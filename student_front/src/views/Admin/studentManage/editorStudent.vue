<template>
    <div>
        <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
                 class="demo-ruleForm">
            <el-form-item label="用户 id" prop="sid">
                <el-input v-model.number="ruleForm.sid" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="学生姓名" prop="sname">
                <el-input v-model="ruleForm.sname" :value="ruleForm.sname"></el-input>
            </el-form-item>
            <el-form-item label="修改密码" prop="password">
                <el-input v-model="ruleForm.password" :value="ruleForm.password"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
                <el-button @click="resetForm('ruleForm')">重置</el-button>
                <el-button @click="test">test</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
export default {
    data() {
        const id_validator = (rule, value, callback) =>{
            if (/[a-zA-Z]/.test(value) === true) {
                callback(new Error("请输入用户 id"))
            } else if(value > 999999999999){
                callback(new Error("长度在 1 到 12 个数字"))
            }else {
                callback();
            }
        };
        return {
            ruleForm: {
                sid: null,
                sname: null,
                password: null
            },
            rules: {
                sid: [
                    {validator: id_validator,trigger: 'blur' },
                ],
                sname: [
                    {required: true, message: '请输入名称', trigger: 'blur'},
                    {min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: '请输入密码', trigger: 'change'}
                ],
            }
        };
    },
    created() {
        const that = this
        // 判断传来的query里面是否有sid
        if (this.$route.query.sid === undefined) {
            this.ruleForm.sid = 1
        } else {
            this.ruleForm.sid = this.$route.query.sid
        }
        // 根据sid查询学生信息
        axios.get('http://localhost:10086/student/findById/' + this.ruleForm.sid).then(function (resp) {
            resp.data.password = null;
            that.ruleForm = resp.data
        })
    },
    methods: {
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    // 通过前端校验
                    const that = this
                    console.log(this.ruleForm)
                    axios.post("http://localhost:10086/student/updateStudent", this.ruleForm).then(function (resp) {
                        if (resp.data === true) {
                            that.$message({
                                showClose: true,
                                message: '编辑成功',
                                type: 'success'
                            });
                        } else {
                            that.$message.error('编辑失败，请检查数据库');
                        }
                        that.$router.push("/studentList")
                    })
                } else {
                    return false;
                }
            });
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
        test() {
            console.log(this.ruleForm)
        }
    }
}
</script>