<template>
	<div>
		<el-header style="padding: 0%;">
	    <!-- <el-row class="tac"> -->
			<el-menu
			 background-color="#C9BEB9"
			 text-color="#4D4D4D"
			 active-text-color="#FFFFFF"
			 style="display: flex;"
			 :default-active="activeIndex" class="farm-menu" mode="horizontal">
				 <div class="LogoFarmText">甲 壳 虫 农 场</div>
				 <el-menu-item index="1" style="margin-left: 70px;" @click="FieldMenuItemClick">农场</el-menu-item>
				 <el-menu-item index="2" style="margin-left: 30px;" @click="SellStoreMenuItemClick">贩卖基地</el-menu-item>
				 <el-menu-item index="3" style="margin-left: 30px;" @click="FieldBuyMenuItemClick">田地管理</el-menu-item>
				 <div class="LogoFarmText" style="margin-left: 200px;">当前时间：{{ timeNow }}</div>
				 <div class="LogoFarmText" style="margin-left: 200px;">剩余游戏时间：{{ remainTime }} 天</div>
				 <div class="LogoFarmText" style="margin-left: 200px;">当前资金：{{ moneyTotalNow.toFixed(2) }}</div>
				 <el-submenu 
				 index="4" style="margin-left: auto; ">
				   <template slot="title">{{name}}</template>
				   <el-menu-item index="1-1" @click="checkHistory">查看历史排行榜</el-menu-item>
				   <el-menu-item index="1-2" @click="logout">提前结束</el-menu-item>
				   </el-submenu>
			</el-menu>
		<!-- </el-row> -->
		</el-header>
		<el-main>
			<el-dialog
			  title="历 史 排 行 榜"
			  :visible.sync="checkHistoryVisible"
			  width="30%">
			  <div style="display: flex;">
				  <el-table
				      :data="historyList"
				      height="500"
				      border
				      style="width: 100%">
					  <el-table-column
					      type="index"
					      label="排名"
					      width="70">
					    </el-table-column>
				      <el-table-column
				        prop="createTime"
				        label="创建日期"
				        width="260">
				      </el-table-column>
				      <el-table-column
				        prop="money"
				        label="最高资金">
				      </el-table-column>
				    </el-table>
			  </div>
			</el-dialog>
			<router-view />
		</el-main>
	</div>
</template>

<script>
	export default {
	    data() {
	      return {
	        activeIndex: '1',
			name: sessionStorage.getItem("name"),
			timeNow: new Date().toLocaleTimeString(),
			moneyTotalNow: 0,
			intervalId: null,
			pageTitle: '甲 壳 虫 农 场',
			remainTime: 0,
			historyList: [],
			
			checkHistoryVisible: false,
	      };
	    },
	    methods: {
		  FieldMenuItemClick(){
			  this.$router.push('/farm/field');
		  },
		  SellStoreMenuItemClick(){
			  this.$router.push('/farm/sellStore');
		  },
		  FieldBuyMenuItemClick(){
			  this.$router.push('/farm/fieldBuy');
		  },
		  logout(){
		    this.$confirm('确认提前关闭？')
				.then(_ => {
				  //1.清除记录
				  this.$axios.get("/setHistory");
				  //2.跳转到登录页面
				  alert("时间结束，您最终的金额为：" + this.moneyTotalNow);
				  this.$router.push("/");
				})
				.catch(_ => {});
		  },
		  updateTime() {
			setInterval(() => {
			  this.timeNow = new Date().toLocaleTimeString();
			}, 1000); // 更新时间间隔为 1 秒
		  },
		  updateTitle() {
			document.title = this.pageTitle;
		  },
		  checkHistory(){
			  this.checkHistoryVisible = true;
			  this.$axios.get("/checkHistory")
			  	.then(result => {
			  		var data = result.data;
					if(data.code == 200){
						this.historyList = data.data;
					}else {
						this.$message.warning(data.msg);
					}
			  	})
		  },
		  checkLife(){
			// 检查是否有动物寿命为零，同时计算每日盈利
		  	this.$axios.get("/checkLife")
		  		.then(result => {
		  			var data = result.data;
		  		})
			this.$axios.get("/checkIncome")
				.then(result => {
					var data = result.data;
					this.moneyTotalNow = data.data;
				})
			this.$axios.get("/checkTimeOut")
				.then(result => {
					var data = result.data.data;
					if(data == 0){
						alert("时间结束，您最终的金额为：" + this.moneyTotalNow);
						this.$router.push("/");
					}else{
						this.remainTime = data;
					}
				})
			this.$axios.get("/checkField")
				.then(result => {
					var data = result.data;
					if(data.code != 200){
						this.$message.warning("您有"+ data.data + "个农田到期了，其中的动物也跑掉了");
					}
				})
		  },
	    },
		created() {
			this.updateTime();
			this.updateTitle();
			this.intervalId = setInterval(this.checkLife, 1000);
		},
		beforeDestroy() {
		  // 组件销毁前清除定时器
		  clearInterval(this.intervalId);
		  if (this.intervalId) {
			clearInterval(this.intervalId);
		  }
		}
	}
</script>

<style>
	.LogoFarmText{
		margin-left: 30px;
		display: flex; 
		align-items: center;
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
</style>