<template>
    <div>
        <!-- <el-form :inline="true">
            <el-form-item>
                <el-input v-model="animalNameSelect" placeholder="动物名"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getAnimalList()">搜索</el-button>
            </el-form-item>
        </el-form> -->
        <el-table :data="animalList" border style="width: 100%">
            <el-table-column fixed prop="id" label="序号" >
            </el-table-column>
            <el-table-column prop="imgUrl" label="图片" >
                <template slot-scope="scope">
                    <img :src="scope.row.imgUrl" width="50px" height="40"/>
                </template>
            </el-table-column>
            <el-table-column prop="animalName" label="动物名" >
            </el-table-column>
            <el-table-column prop="buyPrice" label="单价" >
            </el-table-column>
			<el-table-column prop="income" label="每日收益" >
			</el-table-column>
			<el-table-column prop="life" label="寿命(天)" >
			</el-table-column>
			<el-table-column prop="size" label="占用空间" >
			</el-table-column>
            <el-table-column fixed="right" label="操作" >
                <template slot-scope="scope">
                <el-button @click="showBuyAnimalDialog(scope.row)">购买</el-button>
                </template>
            </el-table-column>
		</el-table>
		<el-dialog
		  title="动 物 购 买"
		  :visible.sync="buyAnimalDialog"
		  width="30%">
		  <div style="display: flex;">
			  <p>您当前购买的动物为： {{animalInfo.animalName}}</p>
			  <img :src="animalInfo.imgUrl" style="width: 50px; height: 40px; margin-left: 40px;" />
		  </div>
		  <br>
		  <div>请输入购买数量：</div>
		  <el-input-number v-model="buyCount" :min="1" :max="10" label="描述文字"></el-input-number>
		  <div>请选择您放入的农田：</div>
		  <el-select v-model="buyToFarm" placeholder="请选择">
		      <el-option
		        v-for="item in fieldNameList"
		        :key="item"
		        :label="item"
		        :value="item">
		      </el-option>
		    </el-select>
		  <el-button @click="animalAdd()">购 买</el-button>
		</el-dialog>
    </div>
</template>

<script>
    export default{
        data(){
            return{
                animalList:[],
				animalNameSelect:"",
                pageNumber:1,
                pageSize:5,
                total:0,
				animalInfo: {},
				buyCount: "",
				buyToFarm: "",
				fieldNameList: {},
				
				buyAnimalDialog: false,
				
                animalName:'',
            }
        },
        methods:{
            getAnimalList(){
                this.$axios.get('/store/animalInfo')
					.then(result => {
						var data = result.data;
						this.animalList = data.data;
					})
					.catch(e => {
						console.log(e);
					})
            },
			showBuyAnimalDialog(data){
				this.$axios.get('/getFieldsName')
					.then(result => {
						var data = result.data.data;
						this.fieldNameList = data;
					})
				this.animalInfo = data;
				this.buyAnimalDialog = true;
			},
			animalAdd(){
				if(this.buyToFarm == ""){
					this.$message.warning("请选择农田！");
				}
				if(this.buyCount == ""){
					this.$message.warning("请选择数量！");
				}
				if(this.buyToFarm != "" && this.buyCount != ""){
					var params = {};
					params.fieldName = this.buyToFarm;
					params.animalName = this.animalInfo.animalName;
					params.count = this.buyCount;
					this.$axios.post("/animalAdd", params)
						.then(result => {
							var data = result.data;
							if (data.code == 200) {
							  this.$message.success(data.msg);
							  this.buyAnimalDialog = false;
							} else {
							  this.$message.warning(data.msg);
							}
						})
						.catch(e => {
						  console.log(e);
						})
				}
				
			}
        },
        mounted() {
            this.getAnimalList();
        }
    }
</script>

<style>
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
