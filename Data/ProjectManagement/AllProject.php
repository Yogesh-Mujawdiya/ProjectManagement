<?php
	$response = array();
	$Info = array();
	include 'db-connect.php';
	$R=mysqli_query($con,"SELECT * FROM project");
	$i=0;
	while ($I=mysqli_fetch_array($R)) {
		$Info[$i]["Title"] = $I["Title"] ;
		$Info[$i]["Domain"] = $I["Domain"] ;
		$Info[$i]["Sid"] = $I["Sid"] ;
		$Info[$i]["Gid"] = $I["Gid"] ;
		$i+=1;
	}
	$response["list"] = $Info;
	$response["status"] = "True";
	$response["message"] = "Project Update !";
	echo json_encode($response);
?>