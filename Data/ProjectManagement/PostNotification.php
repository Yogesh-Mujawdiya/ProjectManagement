<?php
	$response = array();
	include 'db-connect.php';

	if(isset($_POST['Notification']) && isset($_POST['Batch']) && isset($_POST['Full_Name'])){
		$Notification = $_POST['Notification'];
		$Batch = $_POST['Batch'];
		$Name=$_POST['Full_Name'];
		$query="INSERT INTO notification(Notification,Batch,Guide) VALUES('$Notification','$Batch','$Name')";
		if($con->query($query)==TRUE){
			$response["status"] = "True";
			$response["message"] = "Notification Send !";
		}
		else{
			$response["status"] = "False";
			$response["message"] = "Notification Not Send !";
		}
	}
	else{
		$response["status"] = "False";
		$response["message"] = "Missing mandatory parameters";
	}
	echo json_encode($response);
?>