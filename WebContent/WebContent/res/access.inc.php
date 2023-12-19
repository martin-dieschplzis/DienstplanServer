<?php

// Users data
$imSettings['access']['users'] = array(
	'admin' => array(
		'id' => '08e935n4',
		'groups' => array('08e935n4'),
		'firstname' => 'Admin',
		'lastname' => '',
		'password' => '$2a$11$j/JP6u1gR4OtagDpnfV0Q.yH6VKPLLJmxHQhkMki6Cx20RCROkfDO',
		'crypt_encoding' => 'csharp_bcrypt',
		'db_stored' => false,
		'page' => 'index.html'
	),
	'nuovoutente' => array(
		'id' => 's2e020v4',
		'groups' => array('s2e020v4'),
		'firstname' => 'NuovoUtente',
		'lastname' => '',
		'password' => '$2a$11$SpHaGY74ZvkS5EAEbmb0IuI8Jf6dubBEEEb0m68lqbVpyNubbhske',
		'crypt_encoding' => 'csharp_bcrypt',
		'db_stored' => false,
		'page' => 'index.html'
	)
);

// Admins list
$imSettings['access']['admins'] = array('08e935n4');

// Page/Users permissions
$imSettings['access']['pages'] = array();

// PASSWORD CRYPT

$imSettings['access']['password_crypt'] = array(
	'encoding_id' => 'php_default',
	'encodings' => array(
		'no_crypt' => array(
			'encode' => function ($pwd) { return $pwd; },
			'check' => function ($input, $encoded) { return $input == $encoded; }
		),
		'php_default' => array(
			'encode' => function ($pwd) { return password_hash($pwd, PASSWORD_DEFAULT); },
			'check' => function ($input, $encoded) { return password_verify($input, $encoded); }
		),
		'csharp_bcrypt' => array(
			'encode' => function ($pwd) { return password_hash($pwd, PASSWORD_BCRYPT); },
			'check' => function ($input, $encoded) { return password_verify($input, $encoded); }
		)
	)
);

// End of file access.inc.php