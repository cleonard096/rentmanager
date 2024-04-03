<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Modifier Client</title>
    <%@include file="/WEB-INF/views/common/head.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>
    <div class="content-wrapper">
        <section class="content-header">
            <h1>Modifier Client</h1>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-body">
                            <form action="${pageContext.request.contextPath}/users/modify" method="post">
                                <input type="hidden" name="clientId" value="${client.clientId}">
                                <div class="form-group">
                                    <label for="newNom">Nom</label>
                                    <input type="text" class="form-control" id="newNom" name="newNom" value="${client.nom}" required>
                                    <% if (request.getAttribute("error_nom") != null) { %>
                                    <span class="text-danger"><%= request.getAttribute("error_nom") %></span>
                                    <% } %>
                                </div>
                                <div class="form-group">
                                    <label for="newPrenom">Prénom</label>
                                    <input type="text" class="form-control" id="newPrenom" name="newPrenom" value="${client.prenom}" required>
                                    <% if (request.getAttribute("error_prenom") != null) { %>
                                    <span class="text-danger"><%= request.getAttribute("error_prenom") %></span>
                                    <% } %>
                                </div>
                                <div class="form-group">
                                    <label for="newEmail">Email</label>
                                    <input type="email" class="form-control" id="newEmail" name="newEmail" value="${client.email}" required>
                                    <% if (request.getAttribute("error_mail") != null) { %>
                                    <span class="text-danger"><%= request.getAttribute("error_mail") %></span>
                                    <% } %>
                                </div>
                                <div class="form-group">
                                    <label for="newDateNaissance">Date de Naissance</label>
                                    <input type="date" class="form-control" id="newDateNaissance" name="newDateNaissance" value="${client.dateNaissance}" required>
                                    <% if (request.getAttribute("error_age") != null) { %>
                                    <span class="text-danger"><%= request.getAttribute("error_age") %></span>
                                    <% } %>
                                </div>
                                <button type="submit" class="btn btn-primary">Modifier</button>
                            </form>
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