<?php
define('DB_USER', "root"); 
define('DB_PASSWORD', ""); 
define('DB_DATABASE', "project_management");
define('DB_SERVER', "localhost");
$con = mysqli_connect(DB_SERVER,DB_USER,DB_PASSWORD,DB_DATABASE);
if(mysqli_connect_errno())
{
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
?>