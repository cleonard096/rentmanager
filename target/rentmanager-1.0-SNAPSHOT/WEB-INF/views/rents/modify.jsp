<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Modifier Reservation
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-body">
                            <form action="${pageContext.request.contextPath}/rents/modify" method="post">
                                <input type="hidden" name="reservationId" value="${reservation.reservationId}">
                                <div class="form-group">
                                    <label for="newDebut">Date de debut</label>
                                    <input type="date" class="form-control" id="newDebut" name="newDebut" value="${reservation.debut}">
                                </div>
                                <div class="form-group">
                                    <label for="newFin">Date de fin</label>
                                    <input type="date" class="form-control" id="newFin" name="newFin" value="${reservation.fin}">
                                    <% if (request.getAttribute("error") != null) { %>
                                    <span class="text-danger"><%= request.getAttribute("error") %></span>
                                    <% } %>
                                </div>
                                <button type="submit" class="btn btn-primary">Modifier</button>
                            </form>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>

