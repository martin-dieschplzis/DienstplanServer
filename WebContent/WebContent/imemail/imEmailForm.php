<?php
if(substr(basename($_SERVER['PHP_SELF']), 0, 11) == "imEmailForm") {
	include '../res/x5engine.php';
	$form = new ImForm();
	$form->setField('User', @$_POST['imObjectForm_1_2'], '', false);
	$form->setField('Passwort', @$_POST['imObjectForm_1_3'], '', false);

	$errorMessage = '';
	if(@$_POST['action'] != 'check_answer') {
		if(!isset($_POST['imJsCheck']) || $_POST['imJsCheck'] != '9416EA61A1183C08BADCE8A9D96DCB8D' || (isset($_POST['imSpProt']) && $_POST['imSpProt'] != ""))
			$errorMessage = "Sie müssen Javascript aktivieren!";
		$db = getDbData();
		if (!$db)
			die("Cannot find db");
		$db = ImDb::from_db_data($db);		if (!$form->saveToDb($db, 'tabelle'))
			die("Unable to connect to db");
		if ($errorMessage == '') {
			echo "{\"status\" : true}";
		}

		else {
			echo "{\"status\" : false, \"err\" : \"$errorMessage\"}";
		}
		exit();
	} else {
		echo $form->checkAnswer(@$_POST['id'], @$_POST['answer']) ? 1 : 0;
	}
}

// End of file