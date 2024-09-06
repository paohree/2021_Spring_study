<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Edit Post</title>
</head>
<body>
<h1 class="text-center">Edit Post</h1>
<form class="w-50 m-auto" method="post" action="../editok" enctype="multipart/form-data">
	<input type="hidden" name="seq" value="${boardVO.seq}">
	<input type="hidden" name="oldfilename" value="${boardVO.image}">
	<div class="mb-3 row">
		<label for="imagefile" class="col-sm-2 col-form-label">Image</label>
		<div class="col-sm-10">
			<input class="form-control" type="file" id="imagefile" name="imagefile">
		</div>
	</div>
	<div class="mb-3 row">
		<label for="nickname" class="col-sm-2 col-form-label">Nickname</label>
		<div class="col-sm-10">
			<input type="text" class="form-control-plaintext" id="nickname" name="nickname" readonly value="${boardVO.nickname}">
		</div>
	</div>
	<div class="mb-3 row">
		<label for="content" class="col-sm-2 col-form-label">Content</label>
		<div class="col-sm-10">
			<textarea class="form-control" id="content" name="content" rows="3">${boardVO.content}</textarea>
		</div>
	</div>
	<div class="text-center">
		<input type="button" value="Back" onclick="history.back()" class="btn btn-secondary">
		<button type="submit" class="btn btn-primary">Edit post</button>
	</div>
</form>

</body>
</html>
<script>

</script>