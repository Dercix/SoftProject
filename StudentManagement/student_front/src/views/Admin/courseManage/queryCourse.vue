<template>
    <div>
        <el-container>
            <el-main>
                <el-card>
                    <el-form :inline="true" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px"
                             class="demo-ruleForm">
                        <el-form-item label="课程号" prop="cid">
                            <el-input v-model.number="ruleForm.cid"></el-input>
                        </el-form-item>
                        <el-form-item label="课程名" prop="cname">
                            <el-input v-model="ruleForm.cname"></el-input>
                        </el-form-item>
                        <el-form-item label="模糊查询" prop="fuzzy">
                            <el-switch v-model="ruleForm.fuzzy"></el-switch>
                        </el-form-item>
                        <el-form-item label="学分下限" prop="lowBound">
                            <el-input v-model.number="ruleForm.lowBound"></el-input>
                        </el-form-item>
                        <el-form-item label="学分上限" prop="highBound">
                            <el-input v-model.number="ruleForm.highBound"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="resetForm('ruleForm')">重置</el-button>
                        </el-form-item>
                    </el-form>
                </el-card>
                <el-card>
                    <course-list :ruleForm="ruleForm" :isActive="true"></course-list>
                </el-card>
            </el-main>
        </el-container>
    </div>
</template>
<script>
import CourseList from "@/views/Admin/courseManage/courseList";

export default {
    components: {CourseList},
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
                cid: null,
                cname: null,
                fuzzy: true,
                lowBound: null,
                highBound: null
            },
            rules: {
                cid: [
                    {validator: id_validator,trigger: 'blur' },
                ],
                cname: [
                    {required: true, message: '请输入名称', trigger: 'blur'},
                    {min: 2, max: 12, message: '长度在 2 到 12 个字符', trigger: 'blur'}
                ],
                lowBound: [
                    {type: 'number', message: '必须是数字类型'}
                ],
                highBound: [
                    {type: 'number', message: '必须是数字类型'}
                ],
            }
        };
    },
    methods: {
        resetForm(formName) {
            this.$refs[formName].resetFields();
        }
    }
}
</script>