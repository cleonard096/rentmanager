<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
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
                Voitures
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/cars/create">Ajouter</a>
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
                                    <th>Marque</th>
                                    <th>Modele</th>
                                    <th>Nombre de places</th>
                                    <!--<th>Propriétaire</th>-->
                                    <th>Action</th>
                                </tr>
                                <tr>

                                <c:forEach items="${vehicles}" var="vehicle">
                                    <td>${vehicle.vehicleId}.</td>
                                    <td>${vehicle.constructeur}</td>
                                    <td>${vehicle.modele}</td>
                                    <td>${vehicle.nbPlaces}</td>
                                    <td>
                                        <a class="btn btn-primary disabled" href="#">
                                            <i class="fa fa-play"></i>
                                        </a>
                                        <a class="btn btn-success modify-btn" href="${pageContext.request.contextPath}/cars/modify?id=${vehicle.vehicleId}">
                                            <i class="fa fa-edit"></i>
                                        </a>
                                        <button class="btn btn-danger delete-btn" data-vehicle-id="${vehicle.vehicleId}">
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
            var vehicleId = $(this).data("vehicle-id");
            var confirmation = confirm('Etes-vous sur de vouloir supprimer ce vehicule ?');
            if (confirmation) {
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/cars",
                    data: {vehicleId: vehicleId},
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