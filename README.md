# 甲壳虫农场功能介绍

## 使用技术栈
- SpringBoot+vue2+Mybatis+Maven+MySQL

## 存在的不足
- 没有很好的留有扩展余地，作为小作业足够
- 做这个游戏的初衷便是为了完成学校作业，所以大概率不会后续更新更多版本，所见即终版  = W =

## 声明
- 可以随意pull下来使用，所以能给一个star吗 OVO
- 可以在本基础上进行修改后署名自己，如果可以的话带上我的链接
- 欢迎拷打，以及将建议提到项目讨论中，不胜赐教

## 登录界面
![image](https://github.com/user-attachments/assets/878a3870-22a5-4568-acbd-f3c2637e4925)
- 在这个界面，只支持输入特定账号密码，因为涉及到金额的部分由于前期架构经验不足，未能留有多账户以及注册的余地。  
- 输入账号密码后点击开始游玩（账号admin，密码123）,在前端vue文件中已经默认填入了账号密码

## 登录后展示信息
![image](https://github.com/user-attachments/assets/83b069a2-ca69-4811-9285-cae417415361)
- 在登录后，会展示当前农场的游戏介绍，规则等信息，可自行修改
- 在顶部为导航栏，分为农场、贩卖基地、田地管理三个路由，由VUE-router进行路由转换
- 在顶部还设置了剩余时间，当前时间以及当前资金的实时显示
- 最右端为用户名，在登录时存入了session中，鼠标移至此处可以选择查看历史记录排行榜以及提前结束，所以在该游戏中登录即开始，退出即结束，无法中途暂停后继续

## 历史记录排行榜 通过DESC排序后展示
![image](https://github.com/user-attachments/assets/75f82fb6-ec7b-4f55-b886-715237d022ce)

## 初始农场画面
![image](https://github.com/user-attachments/assets/f44b6140-5128-4c7c-a8d3-6d4dae3f8cf2)
- 在这里通过v-for展示用户拥有的农田，第一个为默认的农田，名字默认为“这是你的第一块土地”，点击任意一农田可以查询并弹出dialog弹框，展示当前农田的动物信息，以及容量信息等，如下图所示：
![image](https://github.com/user-attachments/assets/f9356aa7-c117-4bdf-9200-c1cba91b79fd)
点击“修改农田名称”按钮即可修改名称，默认填入当前名称进行修改，避免如果只想改动部分而重复输入长串名称

## 贩卖基地
点击顶部导航栏的贩卖基地按钮，跳转到动物贩卖处，如图：
![image](https://github.com/user-attachments/assets/7f049ee7-8cb1-4b19-9148-686c13c9241b)
- 在该处可以查看到所售卖的动物，以及每种动物的信息。
- 点击右侧购买按钮，弹出购买弹框，确认数量以及放入的农田后，点击购买，如图：
![image](https://github.com/user-attachments/assets/59b7bc4b-e362-4e3e-9f70-2ebf0c2e90b5)


## 田地管理
![image](https://github.com/user-attachments/assets/27d37be1-d3d0-4d3c-8518-4328e126a461)
- 在此处可以选择农田扩张、农田租期延长以及租用土地三个功能
- 点击左侧扩张尺寸或延长租期单选框出现对应内容，选择并填入对应信息点击购买
- 右侧选择对应想要租用的土地方案，并且填入创建土地的名称，点击支付按钮
