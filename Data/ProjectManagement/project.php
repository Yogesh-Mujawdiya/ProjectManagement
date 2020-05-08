<?php
	$response = array();
	include 'db-connect.php';

	if(isset($_POST['title']) && isset($_POST['domain']) && isset($_POST['username']) && isset($_POST['guide'])){
		$title = $_POST['title'];
		$domain = $_POST['domain'];
		$student=$_POST['username'];
		$guide= $_POST['guide'];
		$R=mysqli_query($con,"SELECT * FROM project WHERE  Title = '$title' and Domain = '$domain'");
		if(mysqli_num_rows($R)==0)
		{
			$query="INSERT INTO project(Title,Domain,Sid,Gid) VALUES ('$title','$domain','$student','$guide')";
			if($con->query($query)==TRUE)
			{
				$response["status"] = "True";
				$response["message"] = "project title uploded";
			}
			else{
				$response["status"] = "False";
				$response["message"] = "project title not uploded";
			}
		}
		else{
			$response["status"] = "False";
			$response["message"] = "Project already available";
			//$stmt->close();
		}
	}
	else{
		$response["status"] = "False";
		$response["message"] = "Missing mandatory parameters";
	}
	echo json_encode($response);
?>