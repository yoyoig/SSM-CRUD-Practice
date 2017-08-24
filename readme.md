#基于ssm的简单员工管理系统

##涉及到的框架以及技术

* bootstrap前段框架
* pageHelper分页插件
* mybatis generator逆向工程
* JQuery 前端框架
* handlebars.js 模板引擎
* ssm框架以及整合

###dao层
<p>使用mybatis框架,用逆向工程生成基本的mapper和dao接口。
第一次尝试使用了mybaits的逆向工程，mapper中有丰富的单表的增删改查操作
可以通过pojo类的Eaxmple类来对CRUD进行丰富，
生成的mapper基本上满足了所有的单表CRUD的操作。
若要进行多表查询，可以在原来的mapper和接口中进行添加。</p>

###service层
<p>这次把很多逻辑都放在了service层中，比如分页时数据的加工、
多表删除时对数据的拆分处理、对前台传入用户名进行重复校验等。
保证controller层尽量的干净,尽量只做接收数据和传出数据的操作</P>

###controller层
<p>controller层中使用了restful风格的映射和请求,
前后端交互,传出的数据都是json数据。减轻了服务器端的压力</p>

###view层
<P>这次尝试只使用html作为页面展示,进行前后端分离。
所有的请求都是通过jQuery的ajax进行数据访问。
数据的插入使用了handlebars模板引擎,将通过ajax传回的数据展示到页面
</P>



###总结
这次的练习,重点在于前端的页面渲染,难度也主要集中在js方面。在练习中也遇到了一些问题。

#####js中的积累
--------委托事件绑定
 $(document).on("click",".page",function (){})
 为委托机制，即可以在 .page 组件出现之前(还不存在的时候)给它添加事件。
 而不必在每次生成.page组件 时反复绑定事件。
 
--------清空form表单
reset() 的使用方式，好像必须要使用$("xxx form")[0].reset()的方式才生效
$("xxx form")拿到form数组，$("xxx form")[0]拿到第一个form的dom对象，
只有dom中才有reset()方法,jquery中是没有的。

--------ajax获取当前组件对象
在给组件绑定ajax事件时。在ajax中使用$(this)是不能得到当前组件对象的。
只有在ajax之外获取，并传入一个引用中，再到ajax中使用该引用。

--------input 的 checkbox
 jQuery在1.9版本及以上,只能使用$(".xxxCheck").prop("checked",true) 让checkbox为选中状态;
     $(".xxxCheck").prop("checked",false)为不选中状态。
     $(".xxxCheck").attr("checked","checked")已失效。
     

#####handlebars的积累
--------在块Helper中，可以直接传入数据.
        如 {{#editGender gender 'M'}} xxxx {{/editGender}}
        Handlebars.registerHelper("editGender",function (gender,value,options){})
        
        
--------在template中
        可以使用../获取上级元素的值,但是.../却无效。所以在java后台中,使用带容器的DTO还是有蛮有用的。
        
        
#####spring mvc 的积累
--------Restful风格的url
        在springmvc 中我们要配置HttpPutFormContentFilter过滤器，将PUT请求转为POST请求，
        若没有这个过滤器，tomcat将无法为我们传递数据到后台。
        还需要配置HiddenHttpMethodFilter过滤器
        Rest风格请求URI，该过滤器可以将普通的POST请求转化为DELETE请求和PUT请求