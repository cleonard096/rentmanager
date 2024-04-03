<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Dashboard
            </h1>
        </section>
        <!-- Main content -->
        <section class="content">
            <!-- Info boxes -->
            <div class="row">
                <!-- Affichage du nombre d'utilisateurs -->
                <div class="col-md-3 col-sm-6 col-xs-12">
                    <div class="info-box">
                        <a class="info-box-icon bg-yellow" href="${pageContext.request.contextPath}/users">
                            <i class="fa fa-users"></i>
                        </a>
                        <div class="info-box-content">
                            <span class="info-box-text">Utilisateurs</span>
                            <!-- Affichage dynamique du nombre d'utilisateurs -->
                            <span class="info-box-number">${userCount}</span>
                        </div>
                        <!-- /.info-box-content -->
                    </div>
                    <!-- /.info-box -->
                </div>
                <!-- /.col -->
                <!-- Affichage du nombre de véhicules -->
                <div class="col-md-3 col-sm-6 col-xs-12">
                    <div class="info-box">
                        <a class="info-box-icon bg-red" href="${pageContext.request.contextPath}/cars">
                            <i class="fa fa-car"></i>
                        </a>
                        <div class="info-box-content">
                            <span class="info-box-text">Voitures</span>
                            <!-- Affichage dynamique du nombre de véhicules -->
                            <span class="info-box-number">${vehicleCount}</span>
                        </div>
                        <!-- /.info-box-content -->
                    </div>
                    <!-- /.info-box -->
                </div>
                <!-- /.col -->
                <!-- fix for small devices only -->
                <div class="clearfix visible-sm-block"></div>
                <!-- Affichage du nombre de réservations -->
                <div class="col-md-3 col-sm-6 col-xs-12">
                    <div class="info-box">
                        <a class="info-box-icon bg-green" href="${pageContext.request.contextPath}/rents">
                            <i class="fa fa-pencil"></i>
                        </a>
                        <div class="info-box-content">
                            <span class="info-box-text">Reservations</span>
                            <!-- Affichage dynamique du nombre de réservations -->
                            <span class="info-box-number">${reservationCount}</span>
                        </div>
                        <!-- /.info-box-content -->
                    </div>
                    <!-- /.info-box -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <jsp:include page='/WEB-INF/views/common/footer.jsp'></jsp:include>
</div>
<!-- ./wrapper -->
<!-- Contains Js code imports -->
<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
