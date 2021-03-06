<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp"%>
<c:url var="formUrl" value="/admin-guideline-listen-edit.html"/>
<html>
<head>
    <title><fmt:message key="label.guideline.listen.edit" bundle="${lang}"/></title>
    <style>
        .error {
            color: red;
        }
    </style>

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
                                <input type="text" name="pojo.title" id="title" value="${item.pojo.title}"/>
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.grammarguideline.upload.image" bundle="${lang}"/></label>
                            <div class="col-sm-9">
                                <input type="file" name="file" id="uploadImage" value=""/>
                            </div>
                        </div>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.grammarguideline.upload.image.view" bundle="${lang}"/></label>
                            <div class="col-sm-9">
                                <c:if test="${not empty item.pojo.image}">
                                    <c:set var="image" value="/repository/${item.pojo.image}"/>
                                </c:if>
                                <img src="${image}" id="viewImage" width="150px" height="150ox">
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.guideline.listen.context" bundle="${lang}"/></label>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <c:if test="${not empty item.pojo.context}">
                                    <c:set var="context" value="${item.pojo.context}"/>
                                </c:if>
                                <textarea name="pojo.context" cols="80" rows="10" id="ListenGuidelineContext">${context}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <input type="submit" class="btn btn-white btn-warning btn-bold" value="<fmt:message key="label.done" bundle="${lang}"/>"/>
                            </div>
                        </div>
                        <c:if test="${not empty item.pojo.listenGuidelineId}">
                            <input type="hidden" name="pojo.listenGuidelineId" value="${item.pojo.listenGuidelineId}"/>
                        </c:if>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var ListenGuidelineId = '';
    <c:if test="${not empty item.pojo.listenGuidelineId}">
        ListenGuidelineId = ${ item.pojo.listenGuidelineId};
    </c:if>
    $(document).ready(function () {
       // CKEDITOR.replace( 'listenGuidelineContext' );
        CKEDITOR.replace( 'ListenGuidelineContext',
            {
                filebrowserBrowseUrl : '/ckfinder/ckfinder.html',
                filebrowserImageBrowseUrl : '/ckfinder/ckfinder.html?type=Images',
                filebrowserFlashBrowseUrl : '/ckfinder/ckfinder.html?type=Flash',
                filebrowserUploadUrl : '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
                filebrowserImageUploadUrl : '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
                filebrowserFlashUploadUrl : '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'
            });
       validateData();
       $('#uploadImage').change(function (){
          readURL(this,"viewImage");
       });

    });
    function validateData() {
        $('#formEdit').validate({
            ignore: [],
            rules: [],
            message: [],

        })
        $( "#title").rules( "add", {
            required: true,
            messages: {
                required: '<fmt:message key="label.error" bundle="${lang}"/>'
            }
        });
        if(ListenGuidelineId == '') {
            $( "#uploadImage").rules( "add", {
                required: true,
                messages: {
                    required: '<fmt:message key="label.error" bundle="${lang}"/>'
                }
            });
        }
        $( "#ListenGuidelineContext").rules( "add", {
            required: function () {
                CKEDITOR.instances.ListenGuidelineContext.updateElement();
            },
            messages: {
                required: '<fmt:message key="label.error" bundle="${lang}"/>'
            }
        });
    }
    function readURL(input, imageId) {
        if(input.files &&input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                $('#'+imageId).attr('src', reader.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
        
    }
</script>
</body>
</html>
