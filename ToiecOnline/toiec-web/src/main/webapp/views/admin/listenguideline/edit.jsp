<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp"%>
<c:url var="formUrl" value="/admin-guideline-listen-edit.html"/>
<html>
<head>
    <title><fmt:message key="label.guideline.listen.edit" bundle="${lang}"/></title>
</head>
<body>
<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs" id="breadcrumbs">
            <script type="text/javascript">
                try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
            </script>

            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="#"><fmt:message key="label.home" bundle="${lang}"/></a>
                </li>
                <li class="active"><fmt:message key="label.guideline.listen.edit" bundle="${lang}"/></li>
            </ul><!-- /.breadcrumb -->
        </div>
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <c:if test="${not empty messageResponse}">
                        <div class="alert alert-block alert-${alert}">
                            <button type="button" class="close" data-dismiss="alert">
                                <i class="ace-icon fa fa-times"></i>
                            </button>
                                ${messageResponse}
                        </div>
                    </c:if>
                    <form action="${formUrl}" method="post" enctype="multipart/form-data" id="formEdit">
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.guideline.listen.title" bundle="${lang}"/></label>
                            <div class="col-sm-9">
                                <input type="text" name="pojo.title" id="title" value=""/>
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.grammarguideline.upload.image" bundle="${lang}"/></label>
                            <div class="col-sm-9">
                                <input type="file" name="file" id="uploadImage"/>
                            </div>
                        </div>
                        <br/>
<%--                        <div class="form-group">--%>
<%--                            <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.grammarguideline.upload.image.view" bundle="${lang}"/></label>--%>
<%--                            <div class="col-sm-9">--%>
<%--&lt;%&ndash;                                <c:if test="${not empty item.pojo.image}">&ndash;%&gt;--%>
<%--&lt;%&ndash;                                    <c:set var="image" value="/repository/${item.pojo.image}"/>&ndash;%&gt;--%>
<%--&lt;%&ndash;                                </c:if>&ndash;%&gt;--%>
<%--                                <img src="" id="viewImage" width="150px" height="150ox">--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                        <br/>--%>
<%--                        <br/>--%>
<%--                        <div class="form-group">--%>
<%--                            <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.guideline.listen.context" bundle="${lang}"/></label>--%>
<%--                        </div>--%>
<%--                        <div class="form-group">--%>
<%--                            <div class="col-sm-12">--%>
<%--&lt;%&ndash;                                <c:if test="${not empty item.pojo.context}">&ndash;%&gt;--%>
<%--&lt;%&ndash;                                    <c:set var="content" value="${item.pojo.context}"/>&ndash;%&gt;--%>
<%--&lt;%&ndash;                                </c:if>&ndash;%&gt;--%>
<%--                                <textarea name="pojo.context" cols="80" rows="10" id="ListenGuidelineContent">context</textarea>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                        <div class="form-group">--%>
<%--                            <div class="col-sm-12">--%>
<%--                                <input type="submit" class="btn btn-white btn-warning btn-bold" value="<fmt:message key="label.done" bundle="${lang}"/>"/>--%>
<%--                            </div>--%>
<%--                        </div>--%>

<%--                    </form>--%>
                        <%--<div class="form-group">
                      <label class="col-sm-3 control-label no-padding-right"></label>
                      <div class="col-sm-9">
                          <h2>This is a heading</h2>
                          <p>This is a paragraph.</p>
                          <p class="textHide">This is another paragraph.</p>
                      </div>
                  </div>
                  <br/>
                  <br/>
                  <div class="form-group">
                      <label class="col-sm-3 control-label no-padding-right"></label>
                      <div class="col-sm-9">
                          <button id="btnHide">Click me to hide paragraphs</button>
                      </div>
                  </div>--%>
                        <%--<div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"></label>
                            <div class="col-sm-9">
                                <input type="text" value="JSP-SERVLET myclass.vn" id="value"/>
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"></label>
                            <div class="col-sm-9">
                                <p id="showValue">Nothing in this</p>
                            </div>
                        </div>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"></label>
                            <div class="col-sm-9">
                                <button onclick="usingValAction()">Show Infor</button>
                            </div>
                        </div>
                        <br/>--%>
                        <%--<div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"></label>
                            <div class="col-sm-9">
                                <input type="checkbox" id="testCheckbox"/>
                            </div>
                        </div>--%>
                        <%--jQuery css() Method--%>
                        <%--<div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"></label>
                            <div class="col-sm-9">
                                <p style="color: red" id="demoCssMethod1">This is a paragraph.</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"></label>
                            <div class="col-sm-9">
                                <button id="demoCssMethod" onclick="demoCssMethod()">Change color of text</button>
                            </div>
                        </div>--%>
                        <%--jQuery closest() Method--%>
                        <%--<div style="width:500px;">div (great-grandparent)
                            <ul>ul (second ancestor - second grandparent)
                                <ul>ul (first ancestor - first grandparent)
                                    <li>li (direct parent)
                                        <span>span</span>
                                    </li>
                                </ul>
                            </ul>
                        </div>--%>
                        <%--Jquery change() method--%>
                        <%--<input type="checkbox" id="sex" onchange="changeValueCheckbox()"/>--%>
<%--                        <input type="checkbox" id="sex"/>--%>
<%--                        <p id="textSex"></p>--%>

<%--                    <p>Hellooo</p>--%>
<%--                    <p>Hellllo111</p>--%>

<%--                    <button> click </button>--%>
                        <div style="width:500px;">div (great-grandparent)
                            <ul>ul (grandparent)
                                <li>li (direct parent)
                                    <span>span</span>
                                </li>
                            </ul>
                        </div>

                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        hideAllWhenClickButton();
        changeValueCheckbox();
        testParent();
    });
    function hideAllWhenClickButton() {
        $("#btnHide").click(function () {
            $(".textHide").hide();
        });
    }
    function usingValAction() {
        var value = $('#value').val();
        $('#showValue').html(value);
    }
    function demoCssMethod() {
        $('#demoCssMethod1').css("color", "blue");
    }

    function testParent() {
        $("span").parents("ul").css({"color": "red", "border": "2px solid red"});
    }
    function changeValueCheckbox() {
        /*if ($('#sex').prop('checked') == true) {
            $('#textSex').html('<h1>Male</h1>');
        } else {
            $('#textSex').html('<h1>Female</h1>');
        }*/
        // $('#sex').on('change', function () {
        //     if ($('#sex').prop('checked') == true) {
        //         $('#textSex').html('<h1>Male</h1>');
        //     } else {
        //         $('#textSex').html('<h1>Female</h1>');
        //     }
        // });
    }
</script>
</body>
</html>
