<template>
    <div>
        <el-container>
            <el-main>
                <el-card>
                    <el-form :inline="true" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px"
                             class="demo-ruleForm">
                        <el-form-item label="工号" prop="tid">
                            <el-input v-model.number="ruleForm.tid"></el-input>
                        </el-form-item>
                        <el-form-item label="教师名" prop="tname">
                            <el-input v-model.number="ruleForm.tname"></el-input>
                        </el-form-item>
                        <el-form-item label="教师模糊查询">
                            <el-switch v-model="ruleForm.tFuzzy"></el-switch>
                        </el-form-item>
                        <el-form-item label="课程号" prop="cid">
                            <el-input v-model.number="ruleForm.cid"></el-input>
                        </el-form-item>
                        <el-form-item label="课程名" prop="cname">
                            <el-input v-model.number="ruleForm.cname"></el-input>
                        </el-form-item>
                        <el-form-item label="课程模糊查询">
                            <el-switch v-model="ruleForm.cFuzzy"></el-switch>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="resetForm('ruleForm')">重置</el-button>
                        </el-form-item>
                    </el-form>
                </el-card>
                <el-card style="margin-top: 10px">
                    <course-tacher-list :ruleForm="ruleForm"></course-tacher-list>
                </el-card>
            </el-main>
        </el-container>
    </div>
</template>
<script>
import CourseTacherList from "@/views/Admin/selectCourseManage/CourseTacherList";

export default {
    components: {CourseTacherList},
    data() {
        const tid_validator = (rule, value, callback) => {
            if (/[a-zA-Z]/.test(value) === true) {
                callback(new Error("请输入教师 id"))
            } else if (value > 999999999999) {
                callback(new Error("长度在 1 到 12 个数字"))
            } else {
                callback();
            }
        };
        const cid_validator = (rule, value, callback) => {
            if (/[a-zA-Z]/.test(value) === true) {
                callback(new Error("请输入课程 id"))
            } else if (value > 999999999999) {
                callback(new Error("长度在 1 到 12 个数字"))
            } else {
                callback();
            }
        };
        return {
            ruleForm: {
                tid: null,
                cid: null,
                cname: null,
                tname: null,
                tFuzzy: true,
                cFuzzy: true
            },
            rules: {
                tid: [
                    {validator: tid_validator, trigger: 'blur'},
                ],
                tname: [
                    {required: true, message: '请输入名称', trigger: 'blur'},
                    {min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur'}
                ],
                cid: [
                    {validator: cid_validator, trigger: 'blur'},
                ],
                cname: [
                    {required: true, message: '请输入名称', trigger: 'blur'},
                    {min: 2, max: 12, message: '长度在 2 到 12 个字符', trigger: 'blur'}
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