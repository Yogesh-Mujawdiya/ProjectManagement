<?php
	$response = array();
	include 'db-connect.php';

	if(isset($_POST['username']) && isset($_POST['password'])){
		$username = $_POST['username'];
		$password = $_POST['password'];
		$query="";
		$S=mysqli_query($con,"SELECT * FROM student WHERE User_Name = '$username'");
		$G=mysqli_query($con,"SELECT * FROM guide WHERE User_Name = '$username'");
		if(mysqli_num_rows($S)==1){
			$R = mysqli_query($con,"SELECT * FROM student WHERE User_Name='$username' and Password='$password'");
			if(mysqli_num_rows($R)==1){
				$R = mysqli_fetch_array($R);
				$response["status"]   = "True";
				$response["message"]  = "Login successful";
				$response["username"] = $R['User_Name'];
				$response["password"] = $R['Password'];
				$response["full_name"]= $R['Full_Name'];
				$response["batch"]    = $R['Batch'];
				$response["specification"]    = $R['Specification'];
				$response["guide"]    = $R['Guide'] ;
				$response["role"]     = "S";
			}
			else{
				$response["status"] = "False";
				$response["message"] = "Invalid User Name and Password !";
			}
		}
		elseif (mysqli_num_rows($G)==1) {
			$R = mysqli_query($con,"SELECT * FROM guide WHERE User_Name='$username' and Password='$password'");
			if(mysqli_num_rows($R)==1){
				$R = mysqli_fetch_array($R);
				$response["status"]   = "True";
				$response["message"]  = "Login successful";
				$response["username"] = $R['User_Name'];
				$response["password"] = $R['Password'];
				$response["full_name"]= $R['Full_Name'];
				$response["role"]     = "G";
			}
			else{
				$response["status"] = "False";
				$response["message"] = "Invalid User Name and Password !";
			}
		}
		else{
			$response["status"] = "False";
			$response["message"] = "Invalid Username !";
		}
	}
	else{
		$response["status"] = 2;
		$response["message"] = "Missing mandatory parameters";
	}
	echo json_encode($response);
?>