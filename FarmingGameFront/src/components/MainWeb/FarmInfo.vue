<template>
	<div>
		<i class="el-icon-thumb" style="margin-left: 190px; font-size: 40px; color: #C9BEB9;"></i>
		<p class="farmInfo">请点击农场查看您的农场信息</p>
		<div class="MainIntruductionInfo">
			<p class="MainIntruductionInfoP" style="font-size: larger;">农场简介</p>
			<p></p>
		</div>
		<div class="MainIntruductionInfo">
			<p class="MainIntruductionInfoP" style="font-size: xx-large;">农场玩法介绍</p>
			<p class="MainIntruductionInfoP">在开始时，您有10000资金作为启动资金，默认拥有一块99999天剩余租期的田地</p>
			<p class="MainIntruductionInfoP">农田目前只支持养动物</p>
			<p class="MainIntruductionInfoP">农田租用时间到期会自动收回，里面的动物也会跑回自然，请多加留意剩余时间！</p>
			<p class="MainIntruductionInfoP">为了游玩体验，将多日收集一次的方式改为每日自动收获，并自动计算金额</p>
			<p class="MainIntruductionInfoP">农田可以到租用处进行租用，可以续租，也可以扩地</p>
		</div>
	</div>
</template>

<script>
</script>

<style>
	.farmInfo{
		margin-left: 100px;
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
		font-size: large;
	}
	.MainIntruductionInfo{
		margin-top: 10px;
		border-radius: 10px;
		width: 100%;
		background-color: #EEE3D7;
	}
	.MainIntruductionInfoP{
		display: flex;
		justify-content: center;
		align-items: center;
		color: #555500;
		text-shadow:
			-1px -1px 0 #EEE3D7,
			1px -1px 0 #EEE3D7,
			-1px 1px 0 #EEE3D7,
			1px 1px 0 #EEE3D7;
		text-align: center;
		font-size: large;
		background-color: transparent;
		border: none;
	}
</style>