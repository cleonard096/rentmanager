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
                Modifier Vehicule
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-body">
                            <form action="${pageContext.request.contextPath}/cars/modify" method="post">
                                <input type="hidden" name="vehicleId" value="${vehicle.vehicleId}">
                                <div class="form-group">
                                    <label for="constructeur">Marque</label>
                                    <input type="text" class="form-control" id="constructeur" name="constructeur" value="${vehicle.constructeur}"required>
                                </div>
                                <div class="form-group">
                                    <label for="modele">Modele</label>
                                    <input type="text" class="form-control" id="modele" name="modele" value="${vehicle.modele}"required>
                                </div>
                                <div class="form-group">
                                    <label for="nbPlaces">Nombre de places</label>
                                    <input type="number" class="form-control" id="nbPlaces" name="nbPlaces" value="${vehicle.nbPlaces}"required>
                                    <% if (request.getAttribute("error_nbr") != null) { %>
                                    <span class="text-danger"><%= request.getAttribute("error_nbr") %></span>
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
