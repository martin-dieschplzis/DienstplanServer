<?php
include("../res/x5engine.php");
$nameList = array("y5h","6ej","k6w","k2g","ap7","ftx","8d5","rnr","zgl","uez");
$charList = array("N","V","S","K","Z","S","C","Z","8","H");
$cpt = new X5Captcha($nameList, $charList);
//Check Captcha
if ($_GET["action"] == "check")
	echo $cpt->check($_GET["code"], $_GET["ans"]);
//Show Captcha chars
else if ($_GET["action"] == "show")
	echo $cpt->show($_GET['code']);
// End of file x5captcha.php
