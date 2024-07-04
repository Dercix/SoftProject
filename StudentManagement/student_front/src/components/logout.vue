<!--  主页退出点 -->
<template>
    <div>
        <el-popconfirm
                confirm-button-text='好的'
                cancel-button-text='不用了'
                icon="el-icon-info"
                icon-color="red"
                title="确定退出登陆吗？"
                @confirm="out"
        >
            <el-button slot="reference" type="danger" style="margin-top: 10px" round @click>
                logout
            </el-button>
        </el-popconfirm>
    </div>
</template>

<script>
export default {
    name: "logout",
    // data() {
    //     return {
    //         ruleForm: {
    //             id: null,
    //             password: null,
    //             type: null,
    //         },
    //     };
    // },
    methods: {
        out() {
            const that = this;
            axios.get("http://localhost:10086/" + sessionStorage.getItem("logout") + "/findById/" + sessionStorage.getItem("id")).then(function (resp) {
                axios.post("http://localhost:10086/"+ sessionStorage.getItem("logout") +"/logout").then(function (resp1) {
                    if(resp1.data === true &&(resp.data.sname === sessionStorage.getItem("name")||resp.data.tname === sessionStorage.getItem("name") )){
                        sessionStorage.clear();
                        that.$message({
                            showClose: true,
                            message: '退出成功',
                            type: 'success'
                        });
                        that.$router.push('/')
                    }else{
                        that.$message({
                            showClose: true,
                            message: '退出失败',
                            type: 'fail'
                        });
                    }
                })
            })
        }
    }
}
</script>

<style scoped>

</style>