# ego
## ego 电商项目(项目不全,只有部分功能)
### 项目实现
1. 技术栈：jdk1.7+tomcat7+solr+redis+zookeeper+dubbo
2. Cookie+redis+JSONP 实现 SSO单点登入。 
3. dubbo 服务和 zk 注册中心实现暴露接口而隐藏了其实现，提高安全性。 
4. redis 缓存技术和 mycat 分库减轻 mysql 的压力。 
5. solr 和 IK 分词器提高搜索模块的性能。 
6. HttpCilent+NGINX+VSFTPD 实现图片缓存。 
7. MyBatis 插件 pageHelper 实现分页查询。
### 功能
1. 商品信息管理
2. 商城大广告管理
3. 商品类目管理
4. 商品搜索功能
5. 单点登入
6. 订单系统
7. 购物车系统
### 项目结构介绍：
1. passport <br/
用户登入: 项目内功能包含：登入功能，拦截器跳转的页面  登入时：已知用户？创建cookie并保存到redis然后调回原来的页面：注册
2. cart <br/>
购物车： 用户挑选物品的存放地，这里使用redis缓存保存用户的购物信息，已登入？继续：登入界面
3. order <br/>
订单： 包含购物车选中的商品，这里将redis的商品更新，并添加订单信息到表中，已登入？继续：登入界面
4. search  <br/>
搜索功能： 返回用户搜索的商品(表中有的数据)  使用solr保存所有的额商品信息，使用solr查找，然后返回数据给前台
5. portal  <br/>
门户： 后台可以设置大广告的图片，门户搜索后跳转 search项目
6. item <br/>
每个商品的详细信息： 点击相应的商品，跳转商品详细信息，从solr中取出商品信息，然后从数据库中取出商品描述等数据
7. manage  <br/>
后台管理： 商品新增、修改，商品规格参数修改、类目信息修改
8. redis <br/>
使用redis伪集群  使用Jedis调用Redis
9. service <br/>
暴露的dubbo服务的接口(保证实现类的安全性)
10. service-impl <br/>
dubbo服务的实现类
11. commons <br/>
存放工具类或者一些dto
12. pojo <br/>
javabean &nbsp;实体类
