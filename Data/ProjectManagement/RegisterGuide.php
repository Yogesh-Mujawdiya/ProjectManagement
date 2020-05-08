<?php
	$response = array();
	include 'db-connect.php';

	if(isset($_POST['username']) && isset($_POST['password']) && isset($_POST['full_name']) ){
		$U = $_POST['username'];
		$P = $_POST['password'];
		$N = $_POST['full_name'];
		$R1= mysqli_query($con,"SELECT * from guide where User_Name='$U'");
		$R2= mysqli_query($con,"SELECT * from student where User_Name='$U'");
		if(mysqli_num_rows($R1)==0 and mysqli_num_rows($R2)==0)
		{
			$query="INSERT INTO guide(User_Name,Password,Full_Name) VALUES ($U,$P,$N)";
			if($con->query($query)==TRUE)
			{
				$response["status"] = "True";
				$response["message"] = "Registration Successful !";
			}
			else{
				$response["status"] = "False";
				$response["message"] = "Registration Not Successful !";
			}
		}
		else{
			$response["status"] = "False";
			$response["message"] = "Registration Not Successful !\nUser Name already Available";
		}	
	}
	else{
		$response["status"] = "False";
		$response["message"] = "Missing mandatory parameters";
	}
	echo json_encode($response);
?>