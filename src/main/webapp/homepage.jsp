<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>
<main>
	<section class="py-5 text-center container">
		<div class="row py-lg-5">
			<div class="col-lg-6 col-md-8 mx-auto">
				<h1 class="fw-light">HomePage</h1>
			</div>
		</div>
	</section>
	<div class="py-5 bg-light">
		<div class="container">
			<form action="search"></form>
			<div class="row row-cols-1 row-cols-ms-2 row-cols-md-4 g-4">
				<c:forEach var="hotel" items="${HOTEL_LIST}">
					<c:url var="tempLink" value="HotelServlet">
						<c:param name="command" value="LOAD"/>
						<c:param name="hotel_id" value="${hotel.hotel_id}"/>
					</c:url>
					<c:url var="deleteLink" value="HotelServlet">
						<c:param name="command" value="DELETE"/>
						<c:param name="hotel_id" value="${hotel.hotel_id}"/>
					</c:url>
					<div class="col">
						<div class="card shadow-sm">
							<img src="#" alt="example">
							<div class="card-body">
								<p class="card-text">
									<label>Hotel name: </label> ${hotel.hotel_name}
									<br>
									<label>Address: </label> ${hotel.address}
									<br>
									<label>Phone: </label> ${hotel.phone}
									<br>
									<label>Email: </label> ${hotel.email}
								</p>
								<div class="btn-group">
									<a type="button" href="${tempLink}" class="btn btn-sm btn-outline-secondary">Edit</a>
									<a type="button" href="${deleteLink}" class="btn btn-sm btn-outline-secondary" onclick="return confirm('Are you sure you want to delete this book ?')">Delete</a>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</main>
</body>
</html>
