<template>
    <div>
        <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
                 class="demo-ruleForm">
            <el-form-item label="课程名" prop="cname">
                <el-input v-model="ruleForm.cname"></el-input>
            </el-form-item>
            <el-form-item label="学分" prop="ccredit">
                <el-input v-model.number="ruleForm.ccredit"></el-input>
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
        const ccredit_validator = (rule, value, callback) => {
            if (value > 25) {
                callback(new Error("最多25学分"))
            } else {
                callback();
            }
        };
        return {
            ruleForm: {
                cname: null,
                ccredit: null
            },
            rules: {
                cname: [
                    {required: true, message: '请输入名称', trigger: 'blur'},
                    {min: 2, max: 12, message: '长度在 2 到 12 个字符', trigger: 'blur'}
                ],
                ccredit: [
                    {required: true, message: '请输入学分', trigger: 'change'},
                    {type: 'number', message: '请输入数字', trigger: 'blur'},
                    {validator: ccredit_validator, trigger: 'blur'},
                ],
            }
        };
    },
    methods: {
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    // 通过前端校验
                    const that = this
                    // console.log(this.ruleForm)

                    axios.post("http://localhost:10086/course/save", this.ruleForm).then(function (resp) {
                        console.log(resp)
                        if (resp.data === true) {
                            that.$message({
                                showClose: true,
                                message: '插入成功',
                                type: 'success'
                            });
                        } else {
                            that.$message.error('插入失败，请检查数据库t');
                        }
                        that.$router.push("/queryCourse")
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