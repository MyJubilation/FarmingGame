<template>
  <div>
    <el-form>
      <!-- <el-form-item>
        <el-button @click="setResidualHoursPerBilliards" type="success" style="width: 100%;">刷新</el-button>
      </el-form-item> -->
      <el-form-item>
        <!-- :span="6" 表示 <el-col> 将跨越 6 个栅格，即占据了半个屏幕宽度（因为 6/24 = 0.25，即 25%） -->
        <el-row :gutter="20">
          <!-- ------------------------------------------------------------- -->
          <el-col :span="span" style="margin-top: 1vw;" v-for="(field,index) in fieldsList" :key="index">
            <el-form-item>
              <el-col :span="span">
                <el-card style="width: 20vw; height: 20vh; margin-left: 30px; color: #978178; background-color: #EEE3D7;"
						@click.native="getFarmInfo(field)">
					<div class="FarmName">{{ field }}</div>
                </el-card>
              </el-col>
            </el-form-item>
          </el-col>
          <!-- ------------------------------------------------------------- -->
        </el-row>
      </el-form-item>
    </el-form>
	
	<!-- 农场信息框 -->
	<el-dialog
	  title="农 场 信 息"
	  :visible.sync="farmInfoVisible"
	  width="40%"
	  :before-close="farmInfoClose">
	  <div></div>
	  <div class="FieldInfoOperation">
		  <div style="display: flex;">
			  <button @click="showChangeFieldName()" style="margin-bottom: 10px; display: flex;justify-content: center; align-items: center;">修改农田名称</button>
			  <div style="height: 30px; display: flex;justify-content: center; align-items: center;">
				  <p style="margin-left: 50px;">当前农田容量：{{currentSize}}/{{totalSize}}</p>
				  <p style="margin-left: 50px;">剩余租期：{{remainDay}}</p>
			  </div>
		  </div>
		  <div style="display: flex;">
			  <!-- 图片，名称（鸡牛羊），数量/总数 操作（卖掉，购买） -->
			  <el-table
			      :data="fieldInfo"
			      border
			      style="width: 100%">
			      <el-table-column
				    fixed="left"
			        prop="imgUrl"
			        label="图片"
			        width="70px">
					<template slot-scope="scope">
						<img :src="scope.row.imgUrl" alt="图片" style="width: 40px; height: 40px;"/>
					</template>
			      </el-table-column>
			      <el-table-column
			        prop="animalName"
			        label="名称"
			        width="180">
			      </el-table-column>
			      <el-table-column
			        prop="income"
			        label="每日收入"
			        width="180">
			      </el-table-column>
			      <el-table-column
			        prop="lastLife"
			        label="剩余寿命"
			        width="180">
			      </el-table-column>
			    </el-table>
		  </div>
	  </div>
	</el-dialog>
	
	<el-dialog :visible.sync="changeFieldNameVisible"
			width="15%">
		<div>修改农田名称：</div>
		<el-input v-model="changedFieldName" placeholder="请输入新名称"></el-input>
		<el-button type="warning" style="width: 100%; margin-top: 10px; height: 40px;" @click="changeFieldName()">提交</el-button>
	</el-dialog>

	
	
  </div>
</template>

<script>
  export default {
    data() {
      return {
        // deadline4: Date.now() + (new Date().setHours(23,30,59) - Date.now()),
        fieldsList: [],
		fieldInfo: [],
		totalSize: 0,
		currentSize: 0,
		remainDay: 0,
		
		
		fieldName: "",
		changedFieldName: "",
		
		farmInfoVisible: false,
		changeFieldNameVisible:false,

        span: 8,
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
		getFarmInfo(field_name){
			// 获取对应农田的信息
			this.fieldName = field_name;
			this.$axios.post("/getFarmInfoByName", {
					"field_name": field_name
				})
				.then(result => {
					var data = result.data;
					if (data.code == 200) {
					  this.fieldInfo = data.data;
					  this.farmInfoVisible = true;
					} else {
					  this.$message.warning(data.msg);
					}
				})
				.catch(e => {
				  console.log(e);
				})
			this.$axios.post("/getRemainDay", {
					"field_name": field_name
				})
				.then(result => {
					var data = result.data;
					if (data.code == 200) {
					  this.remainDay = data.data;
					} else {
					  this.$message.warning(data.msg);
					}
				})
				.catch(e => {
				  console.log(e);
				})
			this.$axios.post("/getSize",{
					"field_name": field_name
				})
				.then(result => {
					var data = result.data;
					if(data.code == 200){
						this.totalSize = data.data.totalSize;
						this.currentSize = data.data.currentSize;
					}else {
						this.$message.warning(data.msg);
					}
				})
		},
		farmInfoClose(done) {
			this.farmInfoVisible = false;
		  },
		showChangeFieldName(){
			console.log(this.fieldsList);
			this.changedFieldName = this.fieldName;
			this.changeFieldNameVisible = true;
		},
		changeFieldName(){
			var params = {};
			params.fieldName = this.fieldName;
			params.changedName = this.changedFieldName;
			this.$axios.post("/changeFieldName",params)
				.then(result => {
					var data = result.data;
					if(data.code == 200){
						this.getFieldsName();
						this.$message.success(data.msg);
						this.changeFieldNameVisible = false;
						this.fieldName = this.changedFieldName;
					}else{
						this.$message.warning(data.msg);
					}
				})
		},
		
		
		

    },
    mounted() {
      this.getFieldsName();
    }
  };
</script>

<style>
.FarmName{
		display: flex; 
		align-items: center;
		justify-content: center;
		font-size: large;
		color: #555500;
		text-shadow:
			-1px -1px 0 #EEE3D7,
			1px -1px 0 #EEE3D7,
			-1px 1px 0 #EEE3D7,
			1px 1px 0 #EEE3D7;
		background-color: transparent;
		border: none;
	}
.FarmInfoButton{
	color: #555500;
	background-color: #EEE3D7;
	width: 100%;
	height: 100px;
	border: #555500 solid 1px;
}
.FieldInfoOperation{
	/* 用于田地框功能 */
	width: 100%;
	height: 100%;
}
</style>
