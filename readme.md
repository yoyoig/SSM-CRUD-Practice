# 基于ssm的简单员工管理系统

## 涉及到的框架以及技术

* bootstrap前段框架
* pageHelper分页插件
* mybatis generator逆向工程
* JQuery 前端框架
* handlebars.js 模板引擎
* ssm框架以及整合

### dao层
<p>使用mybatis框架,用逆向工程生成基本的mapper和dao接口。
第一次尝试使用了mybaits的逆向工程，mapper中有丰富的单表的增删改查操作
可以通过pojo类的Eaxmple类来对CRUD进行丰富，
生成的mapper基本上满足了所有的单表CRUD的操作。
若要进行多表查询，可以在原来的mapper和接口中进行添加。</p>

### service层
<p>这次把很多逻辑都放在了service层中，比如分页时数据的加工、
多表删除时对数据的拆分处理、对前台传入用户名进行重复校验等。
保证controller层尽量的干净,尽量只做接收数据和传出数据的操作</P>

### controller层
<p>controller层中使用了restful风格的映射和请求,
前后端交互,传出的数据都是json数据。减轻了服务器端的压力</p>

### view层
<P>这次尝试只使用html作为页面展示,进行前后端分离。
所有的请求都是通过jQuery的ajax进行数据访问。
数据的插入使用了handlebars模板引擎,将通过ajax传回的数据展示到页面
</P>



### 总结
这次的练习,重点在于前端的页面渲染,难度也主要集中在js方面。在练习中也遇到了一些问题。
在代码方面,使用Msg这样的DTO有很好的优势来让前端进行校验和数据渲染。写js时，要注意尽量把
重复代码封装成方法，以提高重用性。

##### js中的积累
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
     

##### handlebars的积累
--------在块Helper中，可以直接传入数据.
        如 {{#editGender gender 'M'}} xxxx {{/editGender}}
        Handlebars.registerHelper("editGender",function (gender,value,options){})
        
        
--------在template中
        可以使用../获取上级元素的值,但是.../却无效。所以在java后台中,使用带容器的DTO还是有蛮有用的。
        
        
##### spring mvc 的积累
--------Restful风格的url
        在springmvc 中我们要配置HttpPutFormContentFilter过滤器，将PUT请求转为POST请求，
        若没有这个过滤器，tomcat将无法为我们传递数据到后台。
        还需要配置HiddenHttpMethodFilter过滤器
        Rest风格请求URI，该过滤器可以将普通的POST请求转化为DELETE请求和PUT请求
        
        
##### mybatis 积累
---------一对一映射
在使用一对一映射时，在resultMap 中，使用association标签来使类中的属性对应一个其他类。
javaType 就是该类的全类名。

---------一对多映射
在一对多映射中，在resultMap中使用collection标签来对应类中的集合。
javaType 在collection中默认为java.util.ArrayList，当属性为List时，可以不写
ofTpye 为Llist包装的类。
如: List\<Employee>  
javaType="java.util.ArrayList" ofType="com.hand.pojo.Employee"
javaType为list时 javaType可以省略

------------------------<p>在使用pageHelper和mybatis generator时，使用多条件查询时，要注意，
可以通用稍微修改过的关联了dept 的employee 查询，
但是在使用example 的时候我们在原Example_Where_Clause的基础上修改和新增一个Example_Where_Clause_Condition。
因为我们在关联查询时候，添加了where e.xxx=d.xxx。这个关联之后，原来的Example中会再次添加上where，导致语法错误。
tirm标签也会 导致and 连接错误。
当我们使用 where 条件连接的时候，我们需要新的example。并且去掉where和trim标签.
</P>

<p>在做这里的笔记的时候，忽然发现，可以不用新Example即可添加条件查询。
我们在关联查询的时候使用FROM xxx LEFT OUTER JOIN xxxx ON   xxx.xx = xxx.xx
的方式来关联查询，无需添加where，即可使用原生Example即可。
经实践，可行。推荐使用外连接方式关联。</P>

