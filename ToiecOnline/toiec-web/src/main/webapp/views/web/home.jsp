<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="label.home" bundle="${lang}"/></title>

</head>
<body>

<div id="myCarousel" class="carousel slide">
    <div class="carousel-inner">
        <div class="active item">
            <div class="container">
                <div class="row">
                    <div class="carousel-caption text-center">
                        <h1>Chào mừng bạn đến với Study with me</h1>
                        <p class="lead">Đây là trang wed giúp bạn được hiệu quả cao trong việc học tiếng Anh</p>
                    </div>
                </div>
            </div>
            <div class="item">
                <div class="container">
                    <div class="row">
                    </div>
                </div>
            </div>
        </div>
    </div>



    <div class="row feature-box">
        <div class="span12 cnt-title">
            <h1>Nội dung trang wed</h1>
            <span></span>
        </div>
        <div class="span4">
            <h2><fmt:message key="label.guideline.listen" bundle="${lang}"/></h2>
            <p>
                Hướng dẫn làm bài tập phần nghe
            </p>
            <a href="<c:url value="/danh-sach-huong-dan-nghe.html"/>">Read More &rarr;</a>
        </div>

        <div class="span4">
            <h2><fmt:message key="label.exercise" bundle="${lang}"/></h2>
            <p>
                Phần bài tập phần nghe
            </p>
            <c:url var="listExercise" value="/danh-sach-bai-tap.html">
                <c:param name="pojo.type" value="listening"/>
            </c:url>
            <a href="${listExercise}">Read More &rarr;</a>
        </div>

        <div class="span4">
            <h2><fmt:message key="label.examination" bundle="${lang}"/></h2>
            <p>
                Phần tổng hợp, làm bài thi hoàn chỉnh
            </p>
            <a href="<c:url value="/danh-sach-bai-thi.html"/>">Read More &rarr;</a>
        </div>
    </div>
</div>

<script type="text/javascript">

</script>
</body>
</html>