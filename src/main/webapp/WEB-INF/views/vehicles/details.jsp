<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Détails du véhicule</title>
    <%@include file="/WEB-INF/views/common/head.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>
    <div class="content-wrapper">
        <section class="content">
            <div class="row">
                <div class="col-md-3">
                    <div class="box box-primary">
                        <div class="box-body box-profile">
                            <h3 class="profile-username text-center">${vehicle.constructeur} ${vehicle.modele} <br>Nbr Place: ${vehicle.nbPlaces}</h3>
                            <ul class="list-group list-group-unbordered">
                                <li class="list-group-item">
                                    <b>Réservation(s)</b> <a class="pull-right">${reservations.size()}</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-9">
                    <div class="nav-tabs-custom">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#rents" data-toggle="tab">Reservations</a></li>
                            <li><a href="#cars" data-toggle="tab">Clients</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="active tab-pane" id="rents">
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Client</th>
                                            <th>Date de début</th>
                                            <th>Date de fin</th>
                                        </tr>
                                        <c:forEach items="${reservations}" var="reservation">
                                            <tr>
                                                <td>${reservation.reservationId}</td>
                                                <td>${reservation.clientFullName}</td>
                                                <td>${reservation.debut}</td>
                                                <td>${reservation.fin}</td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                            <div class="tab-pane" id="cars">
                                <!-- /.box-header -->
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Nom</th>
                                            <th>Prenom</th>
                                            <th> Mail</th>
                                            <th> Date de Naissance</th>
                                        </tr>
                                        <c:forEach items="${clients}" var="clients" varStatus="loop">
                                            <tr>
                                                <td>${clients.clientId}.</td>
                                                <td>${clients.nom}</td>
                                                <td>${clients.prenom}</td>
                                                <td>${clients.email}</td>
                                                <td>${clients.dateNaissance}</td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>

