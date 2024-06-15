<!-- 主页导航栏 -->
<template>
    <div>
<!--        <el-button type="text" style="color: #333333; font-size: 18px" @click="out()">-->
<!--            logout-->
<!--        </el-button>-->
<!--        <el-divider direction="vertical" style="color: #333333"></el-divider>-->
        <el-popconfirm
            confirm-button-text='好的'
            cancel-button-text='不用了'
            icon="el-icon-info"
            icon-color="red"
            title="确定退出登陆吗？"
            @confirm="out"
        >
            <el-button slot="reference" type="danger" style="font-size: 18px;border: none;padding: 0;background-color: #409EFF;color: #333333" round @click>
                logout
            </el-button>
        </el-popconfirm>
        <el-divider direction="vertical" style="color: #333333"></el-divider>
        <i class="el-icon-paperclip" style="margin-right: 18px"></i>
        <span>{{ currentTerm }}</span>
        <el-divider direction="vertical"></el-divider>
        <i class="el-icon-user" style="margin-right: 18px"></i>
        <span>{{ name }}</span>
    </div>
</template>

<script>
export default {
    name: "r-header",
    data() {
        return {
            name: null,
            currentTerm: null,

        }
    },
    created() {
        this.name = sessionStorage.getItem("name")
        this.currentTerm = sessionStorage.getItem("currentTerm")
    },
    methods: {
        out() {
            const that = this;
            axios.get("http://localhost:10086/"+ sessionStorage.getItem("logout") + "/findById/" + sessionStorage.getItem("id")).then(function (resp) {
                axios.post("http://localhost:10086/"+ sessionStorage.getItem("logout") +"/logout").then(function (resp1) {
                    if(resp1.data === true &&(resp.data.sname === sessionStorage.getItem("name")||resp.data.tname === sessionStorage.getItem("name"))){
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