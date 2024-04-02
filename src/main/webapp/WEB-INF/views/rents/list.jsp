<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/views/common/head.jsp" %>
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
                Reservations
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/rents/create">Ajouter</a>
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-body no-padding">
                            <table class="table table-striped">
                                <tr>
                                    <th style="width: 10px">#</th>
                                    <th>Voiture</th>
                                    <th>Client</th>
                                    <th>Debut</th>
                                    <th>Fin</th>
                                    <th>Action</th>
                                </tr>
                                <c:forEach var="reservation" items="${reservations}">
                                    <tr>
                                        <td>${reservation.reservationId}</td>
                                        <td>${reservation.vehicleDescr}</td>
                                        <td>${reservation.clientFullName} </td>
                                        <td>${reservation.debut}</td>
                                        <td>${reservation.fin}</td>
                                        <td>
                                            <a class="btn btn-primary disabled" href="#">
                                                <i class="fa fa-play"></i>
                                            </a>
                                            <a class="btn btn-success modify-btn" href="${pageContext.request.contextPath}/rents/modify?id=${reservation.reservationId}">
                                                <i class="fa fa-edit"></i>
                                            </a>
                                            <button class="btn btn-danger delete-btn" data-reservation-id="${reservation.reservationId}">
                                                <i class="fa fa-trash"></i>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
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

<script>
    $(document).ready(function () {
        $(".delete-btn").click(function () {
            var reservationId = $(this).data("reservation-id");
            var confirmation = confirm('Etes-vous sur de vouloir supprimer cette reservation ?');
            if (confirmation) {
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/rents",
                    data: {reservationId: reservationId},
                    success: function (data) {
                        location.reload();
                    },
                    error: function (xhr, status, error) {
                        console.error(error);
                    }
                });
            }
        });
    });
</script>