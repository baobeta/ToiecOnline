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
<%--                    <form action="${formUrl}" method="post" enctype="multipart/form-data" id="formEdit">--%>
<%--                        <div class="form-group">--%>
<%--                            <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.guideline.listen.title" bundle="${lang}"/></label>--%>
<%--                            <div class="col-sm-9">--%>
<%--                                <input type="text" name="pojo.title" id="title" value=""/>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                        <br/>--%>
<%--                        <br/>--%>
<%--                        <div class="form-group">--%>
<%--                            <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.grammarguideline.upload.image" bundle="${lang}"/></label>--%>
<%--                            <div class="col-sm-9">--%>
<%--                                <input type="file" name="file" id="uploadImage"/>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                        <br/>--%>
<%--&lt;%&ndash;                        <div class="form-group">&ndash;%&gt;--%>
<%--&lt;%&ndash;                            <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.grammarguideline.upload.image.view" bundle="${lang}"/></label>&ndash;%&gt;--%>
<%--&lt;%&ndash;                            <div class="col-sm-9">&ndash;%&gt;--%>
<%--&lt;%&ndash;&lt;%&ndash;                                <c:if test="${not empty item.pojo.image}">&ndash;%&gt;&ndash;%&gt;--%>
<%--&lt;%&ndash;&lt;%&ndash;                                    <c:set var="image" value="/repository/${item.pojo.image}"/>&ndash;%&gt;&ndash;%&gt;--%>
<%--&lt;%&ndash;&lt;%&ndash;                                </c:if>&ndash;%&gt;&ndash;%&gt;--%>
<%--&lt;%&ndash;                                <img src="" id="viewImage" width="150px" height="150ox">&ndash;%&gt;--%>
<%--&lt;%&ndash;                            </div>&ndash;%&gt;--%>
<%--&lt;%&ndash;                        </div>&ndash;%&gt;--%>
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

                    <p>Hellooo</p>
                    <p>Hellllo111</p>

                    <button> click </button>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        hideAllWhenClickButton();

    });

    function hideAllWhenClickButton() {
        $("button").click(function () {
            $("p").hide();
        });

    }

</script>
</body>
</html>
