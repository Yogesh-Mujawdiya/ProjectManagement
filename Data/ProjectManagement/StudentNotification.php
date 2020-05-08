<?php
	$response = array();
	$Info = array();
	include 'db-connect.php';

	if(isset($_POST['batch']) && isset($_POST['guide']) ){
		$Batch = $_POST['batch'];
		$Guide = $_POST['guide'];
		$R=mysqli_query($con,"SELECT * FROM notification WHERE  Batch = '$Batch' and Guide = '$Guide' order by Date desc");
		$i=0;
		while ($I=mysqli_fetch_array($R)) {
			$Info[$i]["notification"] = $I["Notification"] ;
			$Info[$i]["batch"] = $I["Batch"] ;
			$Info[$i]["guide"] = $I["Guide"] ;
			$Info[$i]["date"] = $I["Date"] ;
			$i+=1;
		}
		$response["list"] = $Info;
		$response["status"] = "True";
		$response["message"] = "Notification Update !";
	}
	else{
		$response["status"] = "False";
		$response["message"] = "Missing mandatory parameters";
	}
	echo json_encode($response);
?>