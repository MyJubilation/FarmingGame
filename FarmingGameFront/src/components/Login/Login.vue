<template>
	<div class='Login-contener'>
		<el-card class="box_card">
			<div>
				<h1 style="width: 100%;">甲壳虫农场</h1>
			</div>
			<el-form class="form">
				<el-form-item>
					<!-- v-model 给输入框绑定变量 双向绑定  一般用在输入框上-->
					<el-input placeholder="请输入用户名" prefix-icon="el-icon-user" v-model="name"></el-input>
				</el-form-item>
			</el-form>
			<el-form class="form">
				<el-form-item>
					<el-input placeholder="请输入密码" show-password prefix-icon="el-icon-lock" v-model="password"></el-input>
				</el-form-item>
			</el-form>
			<el-form>
				<el-form-item>
					<!-- v-on:触发事件    click事件名称="触发事件处理方法"  v-on:简写：@-->
					<el-button type=primary style="width: 100%; font-size: large; font-weight: 800;" @click="login()">开 始 游 玩</el-button>
				</el-form-item>
			</el-form>
		</el-card>
	</div>
</template>

<script>
	/* 导出vue中的对象 */
	export default{
		data(){
			return{
				name:'admin',
				password:'123',
				pageTitle: '甲 壳 虫 农 场',
			}
		},
		methods:{
			loginDev(){
				sessionStorage.setItem("name","beetles");
				this.$router.push("/farm");
			},
			login(){
				//获取用户输入的用户名和密码
				var params = {}
				params.name=this.name;
				params.password=this.password;
				
				if(params.name != '' && params.password != ''){
					//将用户名和密码发送给后台服务验证，然后接受后台服务返回的结果，来判断用户是否登陆成功.
					this.$axios.post('/login',params)
					.then(result =>{
						console.log(result);
						var data=result.data;
						//如果登陆成功则跳转页面，否则提示登陆失败。
						if(data.code == 200){
							//把后台传回的adminname保存到缓存中
							sessionStorage.setItem("name",this.name);
							//设置默认农田创建时间
							this.$axios.get("/setDefaultfield");
							//页面跳转
							this.$message.success(data.msg);
							this.$router.push("/farm/farmInfo");
						}else{
							this.$message.warning(data.msg);
						}
					})
					.catch(e=>{
						console.log(e);
					})
				}else {
					this.$message.warning("请输入账号密码");
				}
			},
			updateTitle() {
				document.title = this.pageTitle;
			},
		},
		mounted(){
			this.updateTitle();
		}
	}
</script>

<!-- scoped表示style中的样式只在当前文件中生效 -->
<style scoped>
	/* 小数点--类选择器（class） */
 .box_card{
	 width: 25vw;
	 color: #555500;
	 text-shadow:
	 	-1px -1px 0 #EEE3D7,
	 	1px -1px 0 #EEE3D7,
	 	-1px 1px 0 #EEE3D7,
	 	1px 1px 0 #EEE3D7;
	 text-align: center;
	 background-color: transparent;
	 border: none;
 }
 
 .Login-contener{
	 display: flex;  /* 弹性布局*/
	 justify-content: center; /* card居中*/
	 align-items: center;  /* card里面的元素也居中*/
	 /* vh:浏览器高度的百分之一,vw:浏览器宽度的百分之一 */
	 height: 100vh;
	 background-image: url("../../assets/login-background.png");
	 background-size: 150%;
 }
  img{
	  line-height: 40px;
	  vertical-align: bottom;
  }

 
</style>
