<template>
    <div>
        <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
                 class="demo-ruleForm">
            <el-form-item label="用户 id" prop="tid">
                <el-input v-model.number="ruleForm.tid" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="教师姓名" prop="tname">
                <el-input v-model="ruleForm.tname" :value="ruleForm.tname"></el-input>
            </el-form-item>
            <el-form-item label="初始密码" prop="password">
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
        const id_validator = (rule, value, callback) => {
            if (/[a-zA-Z]/.test(value) === true) {
                callback(new Error("请输入用户 id"))
            } else if (value > 999999999999) {
                callback(new Error("长度在 1 到 12 个数字"))
            } else {
                callback();
            }
        };
        return {
            ruleForm: {
                tid: null,
                tname: null,
                password: null
            },
            rules: {
                tid: [
                    {validator: id_validator, trigger: 'blur'},
                ],
                tname: [
                    {required: true, message: '请输入名称', trigger: 'blur'},
                    {min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: '请输入密码', trigger: 'change'}
                ],
            }
        }
            ;
    },
    created() {
        const that = this
        // 如果没有teacherList传来的tid，默认为admin，否则为tid对应的老师
        if (this.$route.query.row === undefined) {
        // if (this.$route.query.tid === undefined) {
        //     this.ruleForm.tid = 6
        } else {
            // this.ruleForm.tid = this.$route.query.tid
            this.ruleForm = this.$route.query.row
            this.ruleForm.password = "";
        }
        // axios.get('http://localhost:10086/teacher/findById/' + this.ruleForm.tid).then(function (resp) {
        //     rep.data.password = null;
        //     that.ruleForm = resp.data
        //     console.log(that.ruleForm)
        // })
    },
    methods: {
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    // 通过前端校验
                    const that = this
                    if (that.ruleForm.tname === 'admin') {
                        that.$message({
                            showClose: true,
                            message: 'admin 不可编辑',
                            type: 'error'
                        });
                        this.$router.push('/queryTeacher')
                        return
                    }
                    console.log(this.ruleForm)
                    axios.post("http://localhost:10086/teacher/updateTeacher", this.ruleForm).then(function (resp) {
                        if (resp.data === true) {
                            that.$message({
                                showClose: true,
                                message: '编辑成功',
                                type: 'success'
                            });
                        } else {
                            that.$message.error('编辑失败，请检查数据库');
                        }
                        that.$router.push("/queryTeacher")
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