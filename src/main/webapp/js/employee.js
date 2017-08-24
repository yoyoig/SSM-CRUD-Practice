$(document).ready(function () {

    Handlebars.registerHelper("gender",function (gender,options) {
        if(gender=='M'){
            return options.fn(this);
        }else {
            return options.inverse(this);
        }
    })

    //得到上一页的页码
    Handlebars.registerHelper("pre",function (value) {
        if(value>1){
            return value-1;
        }else {
            return 1;
        }
    })

    //得到下一页的页码
    Handlebars.registerHelper("next",function (value,value2) {
        if (value<value2) {
            return value + 1;
        }else{
            return value2;
        }
    })

    //判断该页码是否为当前页码，是则active状态
    Handlebars.registerHelper("active",function(curPage,page,options){
        if (curPage==page){
            return options.fn(this);
        }

    })

    //在编辑emp中，默认选中emp的部门
    Handlebars.registerHelper("deptSlt",function (dId,deptId,options) {
        if(dId == deptId){
            return options.fn(this);
        }
    })

    //编辑emp，中显示该emp的性别
    Handlebars.registerHelper("editGender",function (gender,value,options) {
        if(gender==value){
            return options.fn(this);
        }
    })


    //将数据注入到模板中，并将模板展示到指定的容器
    function  renderTemplate(result,content,position) {
        var t = content.html();
        var f = Handlebars.compile(t);
        var h = f(result);
        position.empty();
        position.html(h);
    }
    //首次进入为第一页，
    $.ajax({
        url:"/ssmPra/employees",
        data:{"pn":1,"size":5},
        type:"GET",
        success:function (result) {
            renderTemplate(result,$("#employees-template"),$("#employees"));
            renderTemplate(result,$("#pageCode-template"),$("#pageCode"));
        }
    })

    //指定到某页
    function toPage(num) {
        $.ajax({
            url:"/ssmPra/employees",
            data:{"pn":num,"size":5},
            type:"GET",
            success:function (result) {
                renderTemplate(result,$("#employees-template"),$("#employees"));
                renderTemplate(result,$("#pageCode-template"),$("#pageCode"));
            }
        })
    }

    //给页码组件添加事件，为委托绑定(即不用重复的绑定，因为标签是动态生成的)
    $(document).on("click",".page",function () {
        //获取当前组件中的page属性
        var $page = $(this).attr("page");
        //转到指定页面
        toPage($page);
    })

    //当主选择选择后，下面全部都选择
    $(".mainCheck").click(function () {
        // alert($(this).prop("checked"))
        if($(this).prop("checked")){
            $(".check").prop("checked",true);
        }else{
            $(".check").prop("checked",false);
        }
    })

    //当下面全选后，主选择被选择
    $(document).on("change",".check",function () {
        //$(".check:checked") 为选取类为check，且被选取的组件
        if ($(".check:checked").length==5){
            $(".mainCheck").prop("checked",true);
        }else {
            $(".mainCheck").prop("checked",false);
        }
    })




    //------------------------------新增部分js
    //reset() 的使用方式，好像必须要使用$("xxx form")[0].reset()的方式才生效
    // $("xxx form")拿到form数组，$("xxx form")[0]拿到第一个form的dom对象，
    //只有dom中才有reset()方法,jquery中是没有的

    //用于清楚表单的状态信息和表单中的信息
    function reset_form(ele) {
        $(ele)[0].reset();
        $(ele).find("*").removeClass("has-success has-error");
        $(ele).find(".help-block").html("");

    }
    
    
    //点击新增按钮，显示模态框
    $("#emp_add_modal_btn").click(function () {

        reset_form("#addEmpForm form");

        $('#addEmpModal').modal({
            backdrop:'static'
        })

        //向数据库查找有几个部门，并将部门展示
        deptSlt($("#deptSelect"));
    })


    //将部门展示到select函数
    function deptSlt(deptSelect,dId) {

        $.ajax({
            url:"/ssmPra/department",
            type:"GET",
            success:function (result) {
                //传入的ID是为了编辑时回显员工的部门，由于handlebar中拿不到上上级元素即.../无效
                //所以只能取出result中的map，在map中添加dId属性，并赋值dId,;用../取出dId数据。
                result.map.dId = dId;
                renderTemplate(result,$("#department-template"),deptSelect)
            }

        })
    }



    //给编辑按钮添加事件
    $(document).on("click","#editBtn",function () {
        //显示模态框
        $('#editEmpModal').modal({
            backdrop:'static'
        })
        //得到当前emp的id
        var $empId = $(this).attr("editId");

        //向数据库请求，获得Emp数据
        $.ajax({
            url:"/ssmPra/employee/"+$empId,
            type:"GET",
            success:function (result) {
                renderTemplate(result,$("#editEmp-template"),$("#editForm"))
                deptSlt($("#editDeptSelect"),result.dId)
            }

        })
    })



    //添加员工显示该名称是否已存在
    $("#inputName").blur(function () {
        var $this = $(this);
        var $empName = $(this).val();
        //获取当前元素的父节点，该获得方式在ajax中获取无效
        var $parent = $(this).parent();
        //获取当前元素的下一个兄弟节点
        var $next = $(this).next();
        if($empName.length<2 || $empName.length>20){
            $parent.addClass("has-error");
            $next.html("请输入2-20个字符");
            $this.attr("add_validate","error");
        }else{
            $.ajax({
                url:"/ssmPra/checkEmpName",
                type:"GET",
                data:{"empName":$empName},
                success:function (result) {
                    if (result){
                        cleanStatus($parent,$next)
                        $parent.addClass("has-success");
                        $this.attr("add_validate","success");
                        $next.html("名称可用");
                    }else {
                        cleanStatus($parent,$next)
                        $parent.addClass("has-error");
                        $next.html("该名称以存在,请重新输入");
                        $this.attr("add_validate","error");
                    }
                }
            })
        }
    })

    //用于清楚验证状态
    function cleanStatus(parent,next) {
        parent.removeClass("has-success has-error");
        next.html("");
    }

    //邮箱验证
    $("#inputEmail3").blur(function () {
        var $this = $(this);
        var $email = $(this).val();
        //获取当前元素的父节点，该获得方式在ajax中获取无效
        var $parent = $(this).parent();
        //获取当前元素的下一个兄弟节点
        var $next = $(this).next();

        var reg = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;

        if(reg.test($email)){
            cleanStatus($parent,$next)
            $parent.addClass("has-success");
            $this.attr("add_validate","success");
        }else{
            cleanStatus($parent,$next)
            $parent.addClass("has-error");
            $next.html("请输入正确的邮箱格式");
            $this.attr("add_validate","error");
        }

    })

    //点击保存时，进行添加员工操作，
    //使用form表单的serialize()方法，获得form中的数据包括name和value；
    //成功后对msg进行判断，若为100，则后端验证错误，将错误信息取出，并回显到页面
    //若为200，则后端添加成功，关闭模态框。并跳转到最后一页
    $("#addEmpSubmit").click(function () {

        //当表单验证通过时
        if ($("#inputName").attr("add_validate")=='success' && $("#inputEmail3").attr("add_validate")=='success'){

            $.ajax({
                url:"/ssmPra/employee",
                type:"POST",
                data:$("#addEmpForm form").serialize(),
                success:function (result) {
                    if(result.code==100){
                        $("#addEmpModal").modal("hide");
                        toPage(999999);
                    }else {
                        if(result.map.errorFields.email!=undefined){
                            cleanStatus($("#inputEmail3").parent(),$("#inputEmail3").next())
                            $("#inputEmail3").parent().addClass("has-error");
                            $("#inputEmail3").next().html("请输入正确的邮箱格式");
                            $("#inputEmail3").attr("add_validate","error");
                        }else if(result.map.errorFields.empName!=undefined){
                            cleanStatus($("#inputName").parent(),$("#inputName").next())
                            $("#inputName").parent().addClass("has-error");
                            $("#inputName").next().html("请输入2-20个字符");
                            $("#inputName").attr("add_validate","error");
                        }
                    }

                }
            })
        }else{
            return false;
        }




    })


    //点击修改
    $(document).on("click","#eidt_Emp_Btn",function () {
        var $empId = $("#editInputName").attr("empId");
        $.ajax({
            url:"/ssmPra/employee/"+$empId,
            type:"PUT",
            data:$("#editForm").serialize(),
            success:function (result) {
                if (result.code == 100){
                    $("#editEmpModal").modal("hide");
                    //获得在页码中埋的当前页码
                    toPage($("#save_cur_page").attr("curPage"));
                }else{
                    if (result.map.fieldErrors.empName != undefined){
                        cleanStatus($("#editInputName").parent(),$("#editInputName").next())
                        $("#editInputName").parent().addClass("has-error");
                        $("#editInputName").next().html("请输入2-20个字符");
                        $("#editInputName").attr("add_validate","error");
                    }else if(result.map.fieldErrors.email != undefined) {
                        cleanStatus($("#editInputEmail3").parent(),$("#editInputEmail3").next())
                        $("#editInputEmail3").parent().addClass("has-error");
                        $("#editInputEmail3").next().html("请输入正确的邮箱格式");
                        $("#editInputEmail3").attr("add_validate","error");
                    }
                }
            }
        })
    })

    //单条删除
    //出现bug，，，，在删除和编辑中，使用了id dltBtn 和 editBtn
    // ,由于会重复出现，所以会有多个id相同的组件
    //导致使用$("#dltBtn").attr("dltId")取到的是该页面第一条数据的Id.奇怪的是
    //多条相同id的组件，浏览器没有报错
    $(document).on("click","#dltBtn",function () {
        var $empId = $(this).attr("dltId");

        $.ajax({
            url:"/ssmPra/employee/"+$empId,
            type:"DELETE",
            success:function (result) {

                if (result.code ==100) {
                    alert("删除成功")
                    toPage($("#save_cur_page").attr("curPage"));
                }else {
                    alert("删除失败");
                }
            }


        })
    })


    //多条删除
    $("#emp_delete_all_btn").click(function () {
        var $empId = "";
        var $empName = "";
        $(".check:checked").each(function () {
            $empId+=($(this).attr("check_id")+"-");
            $empName+=($(this).attr("check_name")+",");
        })
        var ids = $empId.substring(0,$empId.length-1);
        var names = $empName.substring(0,$empName.length-1);


        if (confirm("确定删除["+names+"]?")){

        $.ajax({
            url:"/ssmPra/employee/"+$empId,
            type:"DELETE",
            success:function (result) {

                if (result.code ==100) {
                    alert("删除成功")
                    toPage($("#save_cur_page").attr("curPage"));
                }else {
                    alert("删除失败");
                }
            }


        })
        }


    })

})