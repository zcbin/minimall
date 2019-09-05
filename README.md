  ## 微信小程序：微商城
##### 声明：
本项目参考或基于此项目开发：又一个小商城。litemall = Spring Boot后端 + Vue管理员前端 + 微信小程序用户前端 + Vue用户移动端https://github.com/linlinjava/litemall


------------


 本项目只是学习使用，还在开发初期，仅仅完成了部分功能的实现，项目中存在大量bug，努力优化中

##### 技术栈
 - SpringBoot
 - VUE
 - 微信小程序
##### 开发环境
 - JDK1.8
 - MySQL5.7
  
## 系统截图
商品列表
[![商品列表](https://github.com/zcbin/minimall/blob/master/file/goods_list.png?raw=true "商品列表")](https://github.com/zcbin/minimall/blob/master/file/goods_list.png?raw=true "商品列表")
商品类目
[![category](https://github.com/zcbin/minimall/blob/master/file/category.png?raw=true "category")](https://github.com/zcbin/minimall/blob/master/file/category.png?raw=true "category")
微信首页

[![微信](https://github.com/zcbin/minimall/blob/master/file/wx_home.png?raw=true "微信")](https://github.com/zcbin/minimall/blob/master/file/wx_home.png?raw=true "微信")

## 系统功能
#### 商城

#### 管理平台
 shiro实现权限管理
 
 spring aop实现日志管理
######功能
- [x] 用户管理
	- [x] 会员管理
	- [x] 收货地址
	- [x] 会员收藏
	- [x] 会员足迹
	- [x] 搜索历史
	- [x] 意见反馈
- [ ] 商场管理
	- [ ] 行政区域
	- [x] 商品类目
	- [ ] 订单管理
	- [x] 通用问题
	- [x] 关键词
- [x] 商品管理
	- [x] 商品列表
	- [x] 商品上架
	- [x] 商品编辑
	- [x] 商品品论
- [ ] 推广管理
	- [x] 广告管理
	- [ ] 优惠券管理
	- [ ] 优惠券详情
- [x] 系统管理
	- [x] 管理员
	- [x] 操作日志
	- [x] 角色管理
	- [x] 对象存储
- [ ] 配置管理
	- [ ] 商场配置
	- [ ] 运费配置
	- [ ] 订单配置
	- [ ] 小程序配置
- [ ] 统计报表
	- [ ] 用户统计
	- [ ] 订单统计
	- [ ] 商品统计
#### 部署
###### 安装MySQL

###### vue项目
* 安装node,npm,nginx
* npm run build:prod 打包
* 将生成的dist拷贝到centos中
* 配置nginx.conf root目录为dist路径，配置监听端口等，./nginx -s reload重启打开浏览器输入ip:port访问

###### 后台
* mvn clean
* mvn install
* 生成jar文件，执行java -jar minimall.**.jar --spring.profiles.active=prod,core,admin




