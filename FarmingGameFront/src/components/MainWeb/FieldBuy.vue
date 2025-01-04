<template>
	<div style="display: flex;">
		<div class="FieldBuy" style="margin-left: 30px;">
			<P class="FieldBuyTitle">农 田 扩 张 & 延 长 租 期</P>
			<el-radio-group v-model="style" style="margin-left: 20px;">
			      <el-radio-button label="扩张尺寸"></el-radio-button>
			      <el-radio-button label="延长租期"></el-radio-button>
			</el-radio-group>
			<div v-if="style == '扩张尺寸'">
				<div style="display: flex;">
					<i class="el-icon-thumb" style="margin-left: 20px; margin-bottom: 40px; font-size: 30px; color: #C9BEB9; transform: rotate(90deg);"></i>
					<p class="MainIntruductionInfoP" style="font-weight: 900; margin-bottom: 40px; margin-left: 10px; font-size: large;">农田扩张的大小为 1 尺寸 1 元</p>
				</div>
				<el-select v-model="fieldExpandField" placeholder="请选择农田" style="margin-left: 40px;">
					<el-option
					  v-for="item in fieldsList"
					  :key="item"
					  :label="item"
					  :value="item">
					</el-option>
				  </el-select>
				<el-select v-model="fieldExpandSize" placeholder="请选择尺寸" style="margin-left: 200px;">
				  <el-option
					v-for="item in fieldSize"
					:key="item"
					:label="item"
					:value="item">
				  </el-option>
				</el-select>
				<p v-if="fieldExpandSize != ''" style="margin-left: 40px; margin-top: 20px;">扩 张 金 额 为 ：{{fieldExpandSize}} 元</p>
				<el-button style="margin-left: 40px; margin-top: 10px;" @click="fieldExpand()">确认扩张</el-button>
			</div>
			<div v-if="style == '延长租期'">
				<div style="display: flex;">
					<i class="el-icon-thumb" style="margin-left: 20px; margin-bottom: 40px; font-size: 30px; color: #C9BEB9; transform: rotate(90deg);"></i>
					<p class="MainIntruductionInfoP" style="font-weight: 900; margin-bottom: 40px; margin-left: 10px; font-size: large;">农田延期的价格为 1 天 1 元</p>
				</div>
				<el-select v-model="fieldExpandField" placeholder="请选择农田" style="margin-left: 40px;">
					<el-option
					  v-for="item in fieldsList"
					  :key="item"
					  :label="item"
					  :value="item">
					</el-option>
				  </el-select>
				<el-select v-model="rentDay" placeholder="请选择时间" style="margin-left: 200px;">
				  <el-option
					v-for="item in rentDaySize"
					:key="item"
					:label="item"
					:value="item">
				  </el-option>
				</el-select>
				<p v-if="fieldExpandSize != ''" style="margin-left: 40px; margin-top: 50px;">扩 张 金 额 为 ：{{fieldExpandSize}} 元</p>
				<el-button style="margin-left: 40px; margin-top: 10px;" @click="farMoreRentDay()">确认扩张</el-button>
			</div>
		</div>
		<div class="FieldBuy" style="margin-left: 300px;">
			<P class="FieldBuyTitle">农 田 租 用</P>
			<div>
				<el-radio v-model="fieldBorrowChoose" label="300" border style="width: 150px; height: 150px; border-color: #978178; margin-left: 30px;">
					<p>时间：租用200天</p><br><p>金额：300元</p>
				</el-radio>
				<el-radio v-model="fieldBorrowChoose" label="500" border style="width: 150px; height: 150px; border-color: #978178; margin-left: 30px; margin-bottom: 20px;">
					<p>时间：租用400天</p><br><p>金额：500元</p>
				</el-radio>
				<el-radio v-model="fieldBorrowChoose" label="700" border style="width: 150px; height: 150px; border-color: #978178; margin-left: 30px;">
					<p>时间：租用700天</p><br><p>金额：700元</p>
				</el-radio>
				<el-radio v-model="fieldBorrowChoose" label="900" border style="width: 150px; height: 150px; border-color: #978178; margin-left: 30px;">
					<p>时间：租用1000天</p><br><p>金额：900元</p>
				</el-radio>
		    </div>
			<div>
				<p style="margin-left: 30px; margin-top: 20px;" v-if="fieldBorrowChoose != ''">需支付金额：{{fieldBorrowChoose}} 元</p>
			</div>
			<div style="display: flex;">
				<el-input v-model="fieldBorrowName" placeholder="请输入农田名称" style="width: 200px; margin-left: 30px; margin-top: 40px;"></el-input>
				<el-button style="margin-left: 30px; margin-top: 20px;" @click="fieldBrrow()">点击支付</el-button>
			</div>
			<div>
			</div>
		</div>
	</div>
</template>

<script>
	export default {
	    data() {
	      return {
			fieldsList: [],
			fieldExpandField: "",
			fieldSize:[
				100,
				200,
				300,
				400,
				500
			],
			style:"",
			rentDay:"",
			rentDaySize:[
				100,
				200,
				300,
				400,
				500,
				1000,
				2000,
				5000
			],
			fieldExpandSize: "",
			fieldBorrowChoose: "",
			fieldBorrowName: "",
	      };
	    },
	    methods: {
		  getFieldsName() {
		    this.$axios.get('/getFieldsName')
		      .then(result => {
		        var data = result.data.data;
				this.fieldsList = data;
		      })
		      .catch(e => {
		        console.log(e);
		      })
		  },
		  fieldExpand(){
			  if(this.fieldExpandField == ""){
			  	this.$message.warning("请选择农田！");
			  }
			  if(this.fieldExpandSize == ""){
			  	this.$message.warning("请选择大小！");
			  }
			  if(this.fieldExpandField != "" && this.fieldExpandSize != ""){
			  	var params = {};
			    params.fieldName = this.fieldExpandField;
			    params.fieldSize = this.fieldExpandSize;
			  	this.$axios.post("/fieldExpand", params)
			  		.then(result => {
			  			var data = result.data;
			  			if (data.code == 200) {
			  			  this.$message.success(data.msg);
			  			  this.fieldExpandField = "";
						  this.fieldExpandSize = "";
			  			} else {
			  			  this.$message.warning(data.msg);
			  			}
			  		})
			  		.catch(e => {
			  		  console.log(e);
			  		})
			  }
		  },
		  fieldBrrow(){
			  if(this.fieldBorrowChoose == ""){
				  this.$message.warning("请选择租用方案");
			  }
			  if(this.fieldBorrowName == ""){
				  this.$message.warning("请输入农田名称");
			  }
			  if(this.fieldBorrowChoose != "" && this.fieldBorrowName != ""){
				  var params = {};
				  params.fieldBorrowChoose = this.fieldBorrowChoose;
				  params.fieldBorrowName = this.fieldBorrowName;
				  this.$axios.post("/fieldBorrow", params)
				  	.then(result => {
				  		var data = result.data;
				  		if (data.code == 200) {
				  		  this.$message.success(data.msg);
				  		  this.fieldBorrowChoose = "";
				  		  this.fieldBorrowName = "";
				  		} else {
				  		  this.$message.warning(data.msg);
				  		}
				  	})
				  	.catch(e => {
				  	  console.log(e);
				  	})
			  }
		  },
		  farMoreRentDay(){
		  	if(this.fieldExpandField == ""){
		  		this.$message.warning("请选择农田！");
		  	}
		  	if(this.rentDaySize == ""){
		  		this.$message.warning("请选择时间！");
		  	}
		  	if(this.fieldExpandField != "" && this.rentDaySize != ""){
		  	  var params = {};
		  	  params.fieldName = this.fieldExpandField;
		  	  params.rentDay = this.rentDay;
		  		this.$axios.post("/farMoreRentDay", params)
		  			.then(result => {
		  				var data = result.data;
		  				if (data.code == 200) {
		  				  this.$message.success(data.msg);
		  				  this.fieldExpandField = "";
		  				  this.rentDaySize = "";
		  				} else {
		  				  this.$message.warning(data.msg);
		  				}
		  			})
		  			.catch(e => {
		  			  console.log(e);
		  			})
		  	}
		  },
	    },
		created() {
			this.getFieldsName();
		},
		beforeDestroy() {
		}
	}
</script>

<style>
	.FieldBuy{
		width: 40%;
		height: 700px;
		margin-top: 20px;
		background-color: #EEE3D7;
		border-radius: 10px;
	}
	.FieldBuyTitle{
		display: flex; 
		align-items: center;
		justify-content: center;
		color: #555500;
		text-shadow:
			-1px -1px 0 #EEE3D7,
			1px -1px 0 #EEE3D7,
			-1px 1px 0 #EEE3D7,
			1px 1px 0 #EEE3D7;
		text-align: center;
		background-color: transparent;
		border: none;
		font-size: xxx-large;
		font-weight: 700;
	}
</style>