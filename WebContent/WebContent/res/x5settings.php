<?php

/*
|-------------------------------
|	GENERAL SETTINGS
|-------------------------------
*/

$imSettings['general'] = array(
	'site_id' => '2060C5043AD0E4330305D3E64DF6433F',
	'url' => 'http://DESKTOP-V6JELNL.de/',
	'homepage_url' => 'http://DESKTOP-V6JELNL.de/index.html',
	'icon' => 'http://DESKTOP-V6JELNL.de/favImage.png',
	'version' => '2021.5.6.0',
	'sitename' => 'Gemeinschaftspraxis Dr. med. Ulrich Finke, Dr. med. Christina Scholz und Dr. med. Tim Rieger',
	'lang_code' => 'de-DE',
	'public_folder' => '',
	'salt' => '7xgpwqqxtvvovsffdacg0a9tv60pprq76zb3mr3',
	'common_email_sender_addres' => 'dr.christina@diescholzis.de'
);
/*
|-------------------------------
|	PASSWORD POLICY
|-------------------------------
*/

$imSettings['password_policy'] = array(
	'required_policy' => false,
	'minimum_characters' => '6',
	'include_uppercase' => false,
	'include_numeric' => false,
	'include_special' => false
);
/*
|-------------------------------
|	Captcha
|-------------------------------
*/ImTopic::$captcha_code = "		<div class=\"x5captcha-wrap\">
			<label for=\"xkwbcnbh-imCpt\">Wortprüfung:</label><br />
			<input type=\"text\" id=\"xkwbcnbh-imCpt\" class=\"imCpt\" name=\"imCpt\" maxlength=\"5\" />
		</div>
";


$imSettings['admin'] = array(
	'icon' => 'admin/images/logo.png',
	'notification_public_key' => '89c1b41fc6b139a8',
	'notification_private_key' => '67a2e576ed0f4c34',
	'enable_manager_notifications' => false,
	'theme' => 'orange',
	'extra-dashboard' => array(),
	'extra-links' => array()
);


/*
|--------------------------------------------------------------------------------------
|	DATABASES SETTINGS
|--------------------------------------------------------------------------------------
*/

$imSettings['databases'] = array();
$ecommerce = Configuration::getCart();
// Setup the coupon data
$couponData = array();
$couponData['products'] = array();
// Setup the cart
$ecommerce->setPublicFolder('');
$ecommerce->setCouponData($couponData);
$ecommerce->setSettings(array(
	'page_url' => 'http://DESKTOP-V6JELNL.de/cart/index.html',
	'force_sender' => false,
	'mail_btn_css' => 'display: inline-block; text-decoration: none; color: rgba(255, 255, 255, 1); background-color: rgba(7, 55, 99, 1); padding: 8px 4px 8px 4px; border-style: solid; border-width: 1px 1px 1px 1px; border-color: rgba(224, 224, 224, 1) rgba(224, 224, 224, 1) rgba(224, 224, 224, 1) rgba(224, 224, 224, 1); border-top-left-radius: 2px; border-top-right-radius: 2px; border-bottom-left-radius: 2px; border-bottom-right-radius: 2px;',
	'email_opening' => 'Dear Customer,<br /><br />Thank you for your purchase. <br /><br />Below you will find the list of the products you have ordered, the billing information and the instructions for the shipping and payment methods you have chosen.',
	'email_closing' => 'Please contact us if you need further information.<br /><br />Best Regards, our Sales Staff.',
	'email_payment_opening' => 'Sehr geehrter Kunde,<br /> <br /> Vielen Dank für Ihren Einkauf. Wir bestätigen, dass Ihre Zahlung empfangen wurde und dass den Auftrag schnellstmöglich bearbeitet wird.<br /> <br /> Nachstehend finden Sie die Liste der bestellten Produkte und die Rechnungs- und Lieferinformationen.',
	'email_payment_closing' => 'Für weitere Informationen stehen wir Ihnen gern zur Verfügung.<br /><br />Mit freundlichen Grüßen, Ihr Vertriebs-Team',
	'email_digital_shipment_opening' => 'Sehr geehrte Kundin, sehr geehrter Kunde,<br /><br />vielen Dank für Ihren Einkauf. Nachstehend finden Sie die Liste der Download-Links für die bestellten Produkte:',
	'email_digital_shipment_closing' => 'Für weitere Informationen stehen wir Ihnen gern zur Verfügung.<br /><br />Mit freundlichen Grüßen, Ihr Vertriebs-Team',
	'email_physical_shipment_opening' => 'Sehr geehrte Kundin, sehr geehrter Kunde,<br /><br />vielen Dank für Ihren Einkauf. Nachstehend finden Sie die Liste der an Sie versendeten Produkte:',
	'email_physical_shipment_closing' => 'Für weitere Informationen stehen wir Ihnen gern zur Verfügung.<br /><br />Mit freundlichen Grüßen, Ihr Vertriebs-Team',
	'sendEmailBeforePayment' => true,
	'sendEmailAfterPayment' => false,
	'useCSV' => false,
	'header_bg_color' => 'rgba(255, 255, 255, 1)',
	'header_text_color' => 'rgba(0, 128, 128, 1)',
	'cell_bg_color' => 'rgba(162, 215, 204, 1)',
	'cell_text_color' => 'rgba(0, 0, 0, 1)',
	'availability_reduction_type' => 1,
	'border_color' => 'rgba(255, 255, 255, 1)',
	'owner_email' => 'example@example.com',
	'vat_type' => 'included',
	'availability_image' => 'cart/images/cart-available.png'
));

$ecommerce->setPriceFormatData(array(
	'decimals' => 2,
	'decimal_sep' => '.',
	'thousands_sep' => '',
	'currency_to_right' => true,
	'currency_separator' => ' ',
	'show_zero_as' => '0',
	'currency_symbol' => '€',
	'currency_code' => 'EUR',
	'currency_name' => 'Euro',
));

$ecommerce->setDigitalProductsData(array());
$ecommerce->setProductsData(array(
	'25icf3y0' => array(
		'id' => '25icf3y0',
		'name' => 'Aliquam erate',
		'category' => 's2e020v4',
		'categoryPath' => array(
			's2e020v4'
		),
		'showThumbsInShowbox' => true,
		'new' => false,
		'description' => 'Curabitur vel sapien purus.
Aliquam tempor et leo vel porta.',
		'sku' => '',
		'options' => array(
			array(
				'id' => 'ee24ngyo',
				'name' => '20g',
				'price_variation' => 1.00,
				'weight_variation' => 20.00,
				'suboptions' => array()
			),
			array(
				'id' => '0808idig',
				'name' => 'Option 2',
				'price_variation' => 2.00,
				'weight_variation' => 30.00,
				'suboptions' => array()
			)
		),
		'price' => 12.34,
		'singleFullPrice' => '10.93',
		'singleFullPricePlusVat' => '13.34',
		'staticAvailValue' => "available",
		'availabilityType' => "unset",
		'offlineAvailableItems' => 0,
		'quantityDiscounts' => null,
		'media' => array(
			array(
				'type' => 'image',
				'url' => 'images/2.png',
				'width' => 600,
				'height' => 600
			)
		),
		'thumb' => array(
			'type' => 'image/png',
			'url' => 'http://DESKTOP-V6JELNL.de/images/2.png',
			'width' => 600,
			'height' => 600
		),
		'link' => null,
		'showboxLinks' => array(
			array(
				'type' => "showboxvisualmediagallery",
				'tip' => array(
					'image' => '',
					'imagePosition' => "top",
					'imagePercentWidth' => 50,
					'text' => '',
					'width' => 180
				),
				'js' => array(
					'upload' => array(
						'jsonly' => 'x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/2.png\',\'width\': 600,\'height\': 600}]}, 0, this);',
						'complete' => 'onclick="return x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/2.png\',\'width\': 600,\'height\': 600}]}, 0, this);"'
					),
					'offline' => array(
						'jsonly' => 'x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/2.png\',\'width\': 600,\'height\': 600}]}, 0, this);',
						'complete' => 'onclick="return x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/2.png\',\'width\': 600,\'height\': 600}]}, 0, this);"'
					)
				),
				'html' => array(
					'upload' => '<script> showboxlinke01c8f7555e32a48cae5737c8e365cd5 = {\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/2.png\',\'width\': 600,\'height\': 600}]};</script>
<a href="#" onclick="return x5engine.imShowBox(showboxlinke01c8f7555e32a48cae5737c8e365cd5, 0, this)" class="<!--css_class_placeholder-->"><!--html_content_placeholder--></a>',
					'offline' => '<script> showboxlink2913772d71b416bc525932947e5ed9b3 = {\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/2.png\',\'width\': 600,\'height\': 600}]};</script>
<a href="#" onclick="return x5engine.imShowBox(showboxlink2913772d71b416bc525932947e5ed9b3, 0, this)" class="<!--css_class_placeholder-->"><!--html_content_placeholder--></a>'
				)
			)
		),
		'vat' => 22.0,
		'vatName' => 'MwSt.',
		'taxConfigutationGroup' => '',
		'weight' => 0,
		'isDiscountedRegardlessOfCouponAndQuantity' => false,
		'isDiscountedBecauseOfQuantity' => false,
		'slug' => 'aliquam-erate',
		'relatedProducts' => array(),
		'productPageDetailsRichText' => null,
		'seo' => array(
			'tagTitle' => 'Aliquam erate',
			'tagDescription' => '',
			'tagKeywords' => ''
		),
		'schemaOrg' => null,
		'productPageLinkType' => "none",
		'fixedDiscount' => null
	),
	'j81f6321' => array(
		'id' => 'j81f6321',
		'name' => 'Cum sociis',
		'category' => 's2e020v4',
		'categoryPath' => array(
			's2e020v4'
		),
		'showThumbsInShowbox' => true,
		'new' => false,
		'description' => 'Curabitur vel sapien purus.
Aliquam tempor et leo vel porta.',
		'sku' => '',
		'options' => array(
			array(
				'id' => 'gyd6ak20',
				'name' => '20g',
				'price_variation' => 1.00,
				'weight_variation' => 20,
				'suboptions' => array()
			),
			array(
				'id' => '4l53n8vz',
				'name' => '30g',
				'price_variation' => 2,
				'weight_variation' => 30,
				'suboptions' => array()
			)
		),
		'price' => 12.34,
		'singleFullPrice' => '10.93',
		'singleFullPricePlusVat' => '13.34',
		'staticAvailValue' => "available",
		'availabilityType' => "unset",
		'offlineAvailableItems' => 0,
		'quantityDiscounts' => null,
		'media' => array(
			array(
				'type' => 'image',
				'url' => 'images/4.png',
				'width' => 600,
				'height' => 600
			)
		),
		'thumb' => array(
			'type' => 'image/png',
			'url' => 'http://DESKTOP-V6JELNL.de/images/4.png',
			'width' => 600,
			'height' => 600
		),
		'link' => null,
		'showboxLinks' => array(
			array(
				'type' => "showboxvisualmediagallery",
				'tip' => array(
					'image' => '',
					'imagePosition' => "top",
					'imagePercentWidth' => 50,
					'text' => '',
					'width' => 180
				),
				'js' => array(
					'upload' => array(
						'jsonly' => 'x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/4.png\',\'width\': 600,\'height\': 600}]}, 0, this);',
						'complete' => 'onclick="return x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/4.png\',\'width\': 600,\'height\': 600}]}, 0, this);"'
					),
					'offline' => array(
						'jsonly' => 'x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/4.png\',\'width\': 600,\'height\': 600}]}, 0, this);',
						'complete' => 'onclick="return x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/4.png\',\'width\': 600,\'height\': 600}]}, 0, this);"'
					)
				),
				'html' => array(
					'upload' => '<script> showboxlink0b8422063f9296a3693d7693abaf5248 = {\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/4.png\',\'width\': 600,\'height\': 600}]};</script>
<a href="#" onclick="return x5engine.imShowBox(showboxlink0b8422063f9296a3693d7693abaf5248, 0, this)" class="<!--css_class_placeholder-->"><!--html_content_placeholder--></a>',
					'offline' => '<script> showboxlinkddde40f41710bfbe69cbee122194b6e6 = {\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/4.png\',\'width\': 600,\'height\': 600}]};</script>
<a href="#" onclick="return x5engine.imShowBox(showboxlinkddde40f41710bfbe69cbee122194b6e6, 0, this)" class="<!--css_class_placeholder-->"><!--html_content_placeholder--></a>'
				)
			)
		),
		'vat' => 22.0,
		'vatName' => 'MwSt.',
		'taxConfigutationGroup' => '',
		'weight' => 0.0,
		'isDiscountedRegardlessOfCouponAndQuantity' => false,
		'isDiscountedBecauseOfQuantity' => false,
		'slug' => 'cum-sociis',
		'relatedProducts' => array(),
		'productPageDetailsRichText' => null,
		'seo' => array(
			'tagTitle' => 'Cum sociis',
			'tagDescription' => '',
			'tagKeywords' => ''
		),
		'schemaOrg' => null,
		'productPageLinkType' => "none",
		'fixedDiscount' => null
	),
	'91d4758i' => array(
		'id' => '91d4758i',
		'name' => 'Cum sociise',
		'category' => 's2e020v4',
		'categoryPath' => array(
			's2e020v4'
		),
		'showThumbsInShowbox' => true,
		'new' => false,
		'description' => 'Curabitur vel sapien purus.
Aliquam tempor et leo vel porta.',
		'sku' => '',
		'options' => array(
			array(
				'id' => 'gyd6ak20',
				'name' => '20g',
				'price_variation' => 1.00,
				'weight_variation' => 20,
				'suboptions' => array()
			),
			array(
				'id' => '4l53n8vz',
				'name' => '30g',
				'price_variation' => 2,
				'weight_variation' => 30,
				'suboptions' => array()
			)
		),
		'price' => 12.34,
		'singleFullPrice' => '10.93',
		'singleFullPricePlusVat' => '13.34',
		'staticAvailValue' => "available",
		'availabilityType' => "unset",
		'offlineAvailableItems' => 0,
		'quantityDiscounts' => null,
		'media' => array(
			array(
				'type' => 'image',
				'url' => 'images/5.png',
				'width' => 600,
				'height' => 600
			)
		),
		'thumb' => array(
			'type' => 'image/png',
			'url' => 'http://DESKTOP-V6JELNL.de/images/5.png',
			'width' => 600,
			'height' => 600
		),
		'link' => null,
		'showboxLinks' => array(
			array(
				'type' => "showboxvisualmediagallery",
				'tip' => array(
					'image' => '',
					'imagePosition' => "top",
					'imagePercentWidth' => 50,
					'text' => '',
					'width' => 180
				),
				'js' => array(
					'upload' => array(
						'jsonly' => 'x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/5.png\',\'width\': 600,\'height\': 600}]}, 0, this);',
						'complete' => 'onclick="return x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/5.png\',\'width\': 600,\'height\': 600}]}, 0, this);"'
					),
					'offline' => array(
						'jsonly' => 'x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/5.png\',\'width\': 600,\'height\': 600}]}, 0, this);',
						'complete' => 'onclick="return x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/5.png\',\'width\': 600,\'height\': 600}]}, 0, this);"'
					)
				),
				'html' => array(
					'upload' => '<script> showboxlinkd7bb3f9366fbe64faf36a1521a5cb4f3 = {\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/5.png\',\'width\': 600,\'height\': 600}]};</script>
<a href="#" onclick="return x5engine.imShowBox(showboxlinkd7bb3f9366fbe64faf36a1521a5cb4f3, 0, this)" class="<!--css_class_placeholder-->"><!--html_content_placeholder--></a>',
					'offline' => '<script> showboxlinke5944999fae883de075c526a61dc38c6 = {\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/5.png\',\'width\': 600,\'height\': 600}]};</script>
<a href="#" onclick="return x5engine.imShowBox(showboxlinke5944999fae883de075c526a61dc38c6, 0, this)" class="<!--css_class_placeholder-->"><!--html_content_placeholder--></a>'
				)
			)
		),
		'vat' => 22.0,
		'vatName' => 'MwSt.',
		'taxConfigutationGroup' => '',
		'weight' => 0.0,
		'isDiscountedRegardlessOfCouponAndQuantity' => false,
		'isDiscountedBecauseOfQuantity' => false,
		'slug' => 'cum-sociise',
		'relatedProducts' => array(),
		'productPageDetailsRichText' => null,
		'seo' => array(
			'tagTitle' => 'Cum sociise',
			'tagDescription' => '',
			'tagKeywords' => ''
		),
		'schemaOrg' => null,
		'productPageLinkType' => "none",
		'fixedDiscount' => null
	),
	'j37m3fju' => array(
		'id' => 'j37m3fju',
		'name' => 'Cum sociisi',
		'category' => 's2e020v4',
		'categoryPath' => array(
			's2e020v4'
		),
		'showThumbsInShowbox' => true,
		'new' => false,
		'description' => 'Curabitur vel sapien purus.
Aliquam tempor et leo vel porta.',
		'sku' => '',
		'options' => array(
			array(
				'id' => 'gyd6ak20',
				'name' => '20g',
				'price_variation' => 1.00,
				'weight_variation' => 20,
				'suboptions' => array()
			),
			array(
				'id' => '4l53n8vz',
				'name' => '30g',
				'price_variation' => 2,
				'weight_variation' => 30,
				'suboptions' => array()
			)
		),
		'price' => 12.34,
		'singleFullPrice' => '10.93',
		'singleFullPricePlusVat' => '13.34',
		'staticAvailValue' => "available",
		'availabilityType' => "unset",
		'offlineAvailableItems' => 0,
		'quantityDiscounts' => null,
		'media' => array(
			array(
				'type' => 'image',
				'url' => 'images/6.png',
				'width' => 600,
				'height' => 600
			)
		),
		'thumb' => array(
			'type' => 'image/png',
			'url' => 'http://DESKTOP-V6JELNL.de/images/6.png',
			'width' => 600,
			'height' => 600
		),
		'link' => null,
		'showboxLinks' => array(
			array(
				'type' => "showboxvisualmediagallery",
				'tip' => array(
					'image' => '',
					'imagePosition' => "top",
					'imagePercentWidth' => 50,
					'text' => '',
					'width' => 180
				),
				'js' => array(
					'upload' => array(
						'jsonly' => 'x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/6.png\',\'width\': 600,\'height\': 600}]}, 0, this);',
						'complete' => 'onclick="return x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/6.png\',\'width\': 600,\'height\': 600}]}, 0, this);"'
					),
					'offline' => array(
						'jsonly' => 'x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/6.png\',\'width\': 600,\'height\': 600}]}, 0, this);',
						'complete' => 'onclick="return x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/6.png\',\'width\': 600,\'height\': 600}]}, 0, this);"'
					)
				),
				'html' => array(
					'upload' => '<script> showboxlink64b8e206963b7929e71c69757b46c224 = {\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/6.png\',\'width\': 600,\'height\': 600}]};</script>
<a href="#" onclick="return x5engine.imShowBox(showboxlink64b8e206963b7929e71c69757b46c224, 0, this)" class="<!--css_class_placeholder-->"><!--html_content_placeholder--></a>',
					'offline' => '<script> showboxlinkda00c1abc5869d59002f34eb7b5d2006 = {\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/6.png\',\'width\': 600,\'height\': 600}]};</script>
<a href="#" onclick="return x5engine.imShowBox(showboxlinkda00c1abc5869d59002f34eb7b5d2006, 0, this)" class="<!--css_class_placeholder-->"><!--html_content_placeholder--></a>'
				)
			)
		),
		'vat' => 22.0,
		'vatName' => 'MwSt.',
		'taxConfigutationGroup' => '',
		'weight' => 0.0,
		'isDiscountedRegardlessOfCouponAndQuantity' => false,
		'isDiscountedBecauseOfQuantity' => false,
		'slug' => 'cum-sociisi',
		'relatedProducts' => array(),
		'productPageDetailsRichText' => null,
		'seo' => array(
			'tagTitle' => 'Cum sociisi',
			'tagDescription' => '',
			'tagKeywords' => ''
		),
		'schemaOrg' => null,
		'productPageLinkType' => "none",
		'fixedDiscount' => null
	),
	'9e934771' => array(
		'id' => '9e934771',
		'name' => 'Feugiat risus',
		'category' => 's2e020v4',
		'categoryPath' => array(
			's2e020v4'
		),
		'showThumbsInShowbox' => true,
		'new' => false,
		'description' => 'Curabitur vel sapien purus.
Aliquam tempor et leo vel porta.',
		'sku' => '',
		'options' => array(
			array(
				'id' => 'gyd6ak20',
				'name' => '20g',
				'price_variation' => 1.00,
				'weight_variation' => 20,
				'suboptions' => array()
			),
			array(
				'id' => '4l53n8vz',
				'name' => '30g',
				'price_variation' => 2,
				'weight_variation' => 30,
				'suboptions' => array()
			)
		),
		'price' => 12.34,
		'singleFullPrice' => '10.93',
		'singleFullPricePlusVat' => '13.34',
		'staticAvailValue' => "available",
		'availabilityType' => "unset",
		'offlineAvailableItems' => 0,
		'quantityDiscounts' => null,
		'media' => array(
			array(
				'type' => 'image',
				'url' => 'images/7.png',
				'width' => 600,
				'height' => 600
			)
		),
		'thumb' => array(
			'type' => 'image/png',
			'url' => 'http://DESKTOP-V6JELNL.de/images/7.png',
			'width' => 600,
			'height' => 600
		),
		'link' => null,
		'showboxLinks' => array(
			array(
				'type' => "showboxvisualmediagallery",
				'tip' => array(
					'image' => '',
					'imagePosition' => "top",
					'imagePercentWidth' => 50,
					'text' => '',
					'width' => 180
				),
				'js' => array(
					'upload' => array(
						'jsonly' => 'x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/7.png\',\'width\': 600,\'height\': 600}]}, 0, this);',
						'complete' => 'onclick="return x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/7.png\',\'width\': 600,\'height\': 600}]}, 0, this);"'
					),
					'offline' => array(
						'jsonly' => 'x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/7.png\',\'width\': 600,\'height\': 600}]}, 0, this);',
						'complete' => 'onclick="return x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/7.png\',\'width\': 600,\'height\': 600}]}, 0, this);"'
					)
				),
				'html' => array(
					'upload' => '<script> showboxlink6c0f37538b952d151e0db7fd4ad2e376 = {\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/7.png\',\'width\': 600,\'height\': 600}]};</script>
<a href="#" onclick="return x5engine.imShowBox(showboxlink6c0f37538b952d151e0db7fd4ad2e376, 0, this)" class="<!--css_class_placeholder-->"><!--html_content_placeholder--></a>',
					'offline' => '<script> showboxlink94e28df868df9101a948201407cfe9dc = {\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/7.png\',\'width\': 600,\'height\': 600}]};</script>
<a href="#" onclick="return x5engine.imShowBox(showboxlink94e28df868df9101a948201407cfe9dc, 0, this)" class="<!--css_class_placeholder-->"><!--html_content_placeholder--></a>'
				)
			)
		),
		'vat' => 22.0,
		'vatName' => 'MwSt.',
		'taxConfigutationGroup' => '',
		'weight' => 0.0,
		'isDiscountedRegardlessOfCouponAndQuantity' => false,
		'isDiscountedBecauseOfQuantity' => false,
		'slug' => 'feugiat-risus',
		'relatedProducts' => array(),
		'productPageDetailsRichText' => null,
		'seo' => array(
			'tagTitle' => 'Feugiat risus',
			'tagDescription' => '',
			'tagKeywords' => ''
		),
		'schemaOrg' => null,
		'productPageLinkType' => "none",
		'fixedDiscount' => null
	),
	'0bw0dyi6' => array(
		'id' => '0bw0dyi6',
		'name' => 'Feugiat risuse',
		'category' => 's2e020v4',
		'categoryPath' => array(
			's2e020v4'
		),
		'showThumbsInShowbox' => true,
		'new' => false,
		'description' => 'Curabitur vel sapien purus.
Aliquam tempor et leo vel porta.',
		'sku' => '',
		'options' => array(
			array(
				'id' => 'gyd6ak20',
				'name' => '20g',
				'price_variation' => 1.00,
				'weight_variation' => 20,
				'suboptions' => array()
			),
			array(
				'id' => '4l53n8vz',
				'name' => '30g',
				'price_variation' => 2,
				'weight_variation' => 30,
				'suboptions' => array()
			)
		),
		'price' => 12.34,
		'singleFullPrice' => '10.93',
		'singleFullPricePlusVat' => '13.34',
		'staticAvailValue' => "available",
		'availabilityType' => "unset",
		'offlineAvailableItems' => 0,
		'quantityDiscounts' => null,
		'media' => array(
			array(
				'type' => 'image',
				'url' => 'images/8.png',
				'width' => 600,
				'height' => 600
			)
		),
		'thumb' => array(
			'type' => 'image/png',
			'url' => 'http://DESKTOP-V6JELNL.de/images/8.png',
			'width' => 600,
			'height' => 600
		),
		'link' => null,
		'showboxLinks' => array(
			array(
				'type' => "showboxvisualmediagallery",
				'tip' => array(
					'image' => '',
					'imagePosition' => "top",
					'imagePercentWidth' => 50,
					'text' => '',
					'width' => 180
				),
				'js' => array(
					'upload' => array(
						'jsonly' => 'x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/8.png\',\'width\': 600,\'height\': 600}]}, 0, this);',
						'complete' => 'onclick="return x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/8.png\',\'width\': 600,\'height\': 600}]}, 0, this);"'
					),
					'offline' => array(
						'jsonly' => 'x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/8.png\',\'width\': 600,\'height\': 600}]}, 0, this);',
						'complete' => 'onclick="return x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/8.png\',\'width\': 600,\'height\': 600}]}, 0, this);"'
					)
				),
				'html' => array(
					'upload' => '<script> showboxlinkb1146213dd3bfd5f7da3c3ccd105f8eb = {\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/8.png\',\'width\': 600,\'height\': 600}]};</script>
<a href="#" onclick="return x5engine.imShowBox(showboxlinkb1146213dd3bfd5f7da3c3ccd105f8eb, 0, this)" class="<!--css_class_placeholder-->"><!--html_content_placeholder--></a>',
					'offline' => '<script> showboxlink5ff2b8e9ba13235cd99854f4096807a9 = {\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/8.png\',\'width\': 600,\'height\': 600}]};</script>
<a href="#" onclick="return x5engine.imShowBox(showboxlink5ff2b8e9ba13235cd99854f4096807a9, 0, this)" class="<!--css_class_placeholder-->"><!--html_content_placeholder--></a>'
				)
			)
		),
		'vat' => 22.0,
		'vatName' => 'MwSt.',
		'taxConfigutationGroup' => '',
		'weight' => 0.0,
		'isDiscountedRegardlessOfCouponAndQuantity' => false,
		'isDiscountedBecauseOfQuantity' => false,
		'slug' => 'feugiat-risuse',
		'relatedProducts' => array(),
		'productPageDetailsRichText' => null,
		'seo' => array(
			'tagTitle' => 'Feugiat risuse',
			'tagDescription' => '',
			'tagKeywords' => ''
		),
		'schemaOrg' => null,
		'productPageLinkType' => "none",
		'fixedDiscount' => null
	),
	't80dr05j' => array(
		'id' => 't80dr05j',
		'name' => 'Morbi volutpat',
		'category' => 's2e020v4',
		'categoryPath' => array(
			's2e020v4'
		),
		'showThumbsInShowbox' => true,
		'new' => false,
		'description' => 'Curabitur vel sapien purus.
Aliquam tempor et leo vel porta.',
		'sku' => '',
		'options' => array(
			array(
				'id' => 'gyd6ak20',
				'name' => '20g',
				'price_variation' => 1.00,
				'weight_variation' => 20,
				'suboptions' => array()
			),
			array(
				'id' => '4l53n8vz',
				'name' => '30g',
				'price_variation' => 2,
				'weight_variation' => 30,
				'suboptions' => array()
			)
		),
		'price' => 12.34,
		'singleFullPrice' => '10.93',
		'singleFullPricePlusVat' => '13.34',
		'staticAvailValue' => "available",
		'availabilityType' => "unset",
		'offlineAvailableItems' => 0,
		'quantityDiscounts' => null,
		'media' => array(
			array(
				'type' => 'image',
				'url' => 'images/9.png',
				'width' => 600,
				'height' => 600
			)
		),
		'thumb' => array(
			'type' => 'image/png',
			'url' => 'http://DESKTOP-V6JELNL.de/images/9.png',
			'width' => 600,
			'height' => 600
		),
		'link' => null,
		'showboxLinks' => array(
			array(
				'type' => "showboxvisualmediagallery",
				'tip' => array(
					'image' => '',
					'imagePosition' => "top",
					'imagePercentWidth' => 50,
					'text' => '',
					'width' => 180
				),
				'js' => array(
					'upload' => array(
						'jsonly' => 'x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/9.png\',\'width\': 600,\'height\': 600}]}, 0, this);',
						'complete' => 'onclick="return x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/9.png\',\'width\': 600,\'height\': 600}]}, 0, this);"'
					),
					'offline' => array(
						'jsonly' => 'x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/9.png\',\'width\': 600,\'height\': 600}]}, 0, this);',
						'complete' => 'onclick="return x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/9.png\',\'width\': 600,\'height\': 600}]}, 0, this);"'
					)
				),
				'html' => array(
					'upload' => '<script> showboxlink6dd9ad96fdac8e581b598823aa238be7 = {\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/9.png\',\'width\': 600,\'height\': 600}]};</script>
<a href="#" onclick="return x5engine.imShowBox(showboxlink6dd9ad96fdac8e581b598823aa238be7, 0, this)" class="<!--css_class_placeholder-->"><!--html_content_placeholder--></a>',
					'offline' => '<script> showboxlinke01f82fc143cef46719e02b9e649b3d8 = {\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/9.png\',\'width\': 600,\'height\': 600}]};</script>
<a href="#" onclick="return x5engine.imShowBox(showboxlinke01f82fc143cef46719e02b9e649b3d8, 0, this)" class="<!--css_class_placeholder-->"><!--html_content_placeholder--></a>'
				)
			)
		),
		'vat' => 22.0,
		'vatName' => 'MwSt.',
		'taxConfigutationGroup' => '',
		'weight' => 0.0,
		'isDiscountedRegardlessOfCouponAndQuantity' => false,
		'isDiscountedBecauseOfQuantity' => false,
		'slug' => 'morbi-volutpat',
		'relatedProducts' => array(),
		'productPageDetailsRichText' => null,
		'seo' => array(
			'tagTitle' => 'Morbi volutpat',
			'tagDescription' => '',
			'tagKeywords' => ''
		),
		'schemaOrg' => null,
		'productPageLinkType' => "none",
		'fixedDiscount' => null
	),
	'lvnnys3m' => array(
		'id' => 'lvnnys3m',
		'name' => 'Morbi volutpate',
		'category' => 's2e020v4',
		'categoryPath' => array(
			's2e020v4'
		),
		'showThumbsInShowbox' => true,
		'new' => false,
		'description' => 'Curabitur vel sapien purus.
Aliquam tempor et leo vel porta.',
		'sku' => '',
		'options' => array(
			array(
				'id' => 'gyd6ak20',
				'name' => '20g',
				'price_variation' => 1.00,
				'weight_variation' => 20,
				'suboptions' => array()
			),
			array(
				'id' => '4l53n8vz',
				'name' => '30g',
				'price_variation' => 2,
				'weight_variation' => 30,
				'suboptions' => array()
			)
		),
		'price' => 12.34,
		'singleFullPrice' => '10.93',
		'singleFullPricePlusVat' => '13.34',
		'staticAvailValue' => "available",
		'availabilityType' => "unset",
		'offlineAvailableItems' => 0,
		'quantityDiscounts' => null,
		'media' => array(
			array(
				'type' => 'image',
				'url' => 'images/10.png',
				'width' => 600,
				'height' => 600
			)
		),
		'thumb' => array(
			'type' => 'image/png',
			'url' => 'http://DESKTOP-V6JELNL.de/images/10.png',
			'width' => 600,
			'height' => 600
		),
		'link' => null,
		'showboxLinks' => array(
			array(
				'type' => "showboxvisualmediagallery",
				'tip' => array(
					'image' => '',
					'imagePosition' => "top",
					'imagePercentWidth' => 50,
					'text' => '',
					'width' => 180
				),
				'js' => array(
					'upload' => array(
						'jsonly' => 'x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/10.png\',\'width\': 600,\'height\': 600}]}, 0, this);',
						'complete' => 'onclick="return x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/10.png\',\'width\': 600,\'height\': 600}]}, 0, this);"'
					),
					'offline' => array(
						'jsonly' => 'x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/10.png\',\'width\': 600,\'height\': 600}]}, 0, this);',
						'complete' => 'onclick="return x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/10.png\',\'width\': 600,\'height\': 600}]}, 0, this);"'
					)
				),
				'html' => array(
					'upload' => '<script> showboxlinkeb315c48f2491ea2a06f8b89798e0c25 = {\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/10.png\',\'width\': 600,\'height\': 600}]};</script>
<a href="#" onclick="return x5engine.imShowBox(showboxlinkeb315c48f2491ea2a06f8b89798e0c25, 0, this)" class="<!--css_class_placeholder-->"><!--html_content_placeholder--></a>',
					'offline' => '<script> showboxlink8a7737e666b36df4a6ee0d847b4790b2 = {\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/10.png\',\'width\': 600,\'height\': 600}]};</script>
<a href="#" onclick="return x5engine.imShowBox(showboxlink8a7737e666b36df4a6ee0d847b4790b2, 0, this)" class="<!--css_class_placeholder-->"><!--html_content_placeholder--></a>'
				)
			)
		),
		'vat' => 22.0,
		'vatName' => 'MwSt.',
		'taxConfigutationGroup' => '',
		'weight' => 0.0,
		'isDiscountedRegardlessOfCouponAndQuantity' => false,
		'isDiscountedBecauseOfQuantity' => false,
		'slug' => 'morbi-volutpate',
		'relatedProducts' => array(),
		'productPageDetailsRichText' => null,
		'seo' => array(
			'tagTitle' => 'Morbi volutpate',
			'tagDescription' => '',
			'tagKeywords' => ''
		),
		'schemaOrg' => null,
		'productPageLinkType' => "none",
		'fixedDiscount' => null
	),
	'903i71k4' => array(
		'id' => '903i71k4',
		'name' => 'Phasellus2',
		'category' => 's2e020v4',
		'categoryPath' => array(
			's2e020v4'
		),
		'showThumbsInShowbox' => true,
		'new' => false,
		'description' => 'Curabitur vel sapien purus.
Aliquam tempor et leo vel porta.',
		'sku' => '',
		'options' => array(
			array(
				'id' => 'gyd6ak20',
				'name' => '20g',
				'price_variation' => 1.00,
				'weight_variation' => 20,
				'suboptions' => array()
			),
			array(
				'id' => '4l53n8vz',
				'name' => '30g',
				'price_variation' => 2,
				'weight_variation' => 30,
				'suboptions' => array()
			)
		),
		'price' => 12.34,
		'singleFullPrice' => '10.93',
		'singleFullPricePlusVat' => '13.34',
		'staticAvailValue' => "available",
		'availabilityType' => "unset",
		'offlineAvailableItems' => 0,
		'quantityDiscounts' => null,
		'media' => array(
			array(
				'type' => 'image',
				'url' => 'images/12.png',
				'width' => 600,
				'height' => 600
			)
		),
		'thumb' => array(
			'type' => 'image/png',
			'url' => 'http://DESKTOP-V6JELNL.de/images/12.png',
			'width' => 600,
			'height' => 600
		),
		'link' => null,
		'showboxLinks' => array(
			array(
				'type' => "showboxvisualmediagallery",
				'tip' => array(
					'image' => '',
					'imagePosition' => "top",
					'imagePercentWidth' => 50,
					'text' => '',
					'width' => 180
				),
				'js' => array(
					'upload' => array(
						'jsonly' => 'x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/12.png\',\'width\': 600,\'height\': 600}]}, 0, this);',
						'complete' => 'onclick="return x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/12.png\',\'width\': 600,\'height\': 600}]}, 0, this);"'
					),
					'offline' => array(
						'jsonly' => 'x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/12.png\',\'width\': 600,\'height\': 600}]}, 0, this);',
						'complete' => 'onclick="return x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/12.png\',\'width\': 600,\'height\': 600}]}, 0, this);"'
					)
				),
				'html' => array(
					'upload' => '<script> showboxlinkff3e24ec45f87c20cbca7f0d32bdcb46 = {\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/12.png\',\'width\': 600,\'height\': 600}]};</script>
<a href="#" onclick="return x5engine.imShowBox(showboxlinkff3e24ec45f87c20cbca7f0d32bdcb46, 0, this)" class="<!--css_class_placeholder-->"><!--html_content_placeholder--></a>',
					'offline' => '<script> showboxlink3ebdcdda26405dfd48ae10c210afc781 = {\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/12.png\',\'width\': 600,\'height\': 600}]};</script>
<a href="#" onclick="return x5engine.imShowBox(showboxlink3ebdcdda26405dfd48ae10c210afc781, 0, this)" class="<!--css_class_placeholder-->"><!--html_content_placeholder--></a>'
				)
			)
		),
		'vat' => 22.0,
		'vatName' => 'MwSt.',
		'taxConfigutationGroup' => '',
		'weight' => 0.0,
		'isDiscountedRegardlessOfCouponAndQuantity' => false,
		'isDiscountedBecauseOfQuantity' => false,
		'slug' => 'phasellus2',
		'relatedProducts' => array(),
		'productPageDetailsRichText' => null,
		'seo' => array(
			'tagTitle' => 'Phasellus2',
			'tagDescription' => '',
			'tagKeywords' => ''
		),
		'schemaOrg' => null,
		'productPageLinkType' => "none",
		'fixedDiscount' => null
	),
	'g69f0a5j' => array(
		'id' => 'g69f0a5j',
		'name' => 'Tempor Metusi',
		'category' => 's2e020v4',
		'categoryPath' => array(
			's2e020v4'
		),
		'showThumbsInShowbox' => true,
		'new' => false,
		'description' => 'Curabitur vel sapien purus.
Aliquam tempor et leo vel porta.',
		'sku' => '',
		'options' => array(
			array(
				'id' => 'gyd6ak20',
				'name' => '20g',
				'price_variation' => 1.00,
				'weight_variation' => 20,
				'suboptions' => array()
			),
			array(
				'id' => '4l53n8vz',
				'name' => '30g',
				'price_variation' => 2,
				'weight_variation' => 30,
				'suboptions' => array()
			)
		),
		'price' => 12.34,
		'singleFullPrice' => '10.93',
		'singleFullPricePlusVat' => '13.34',
		'staticAvailValue' => "available",
		'availabilityType' => "unset",
		'offlineAvailableItems' => 0,
		'quantityDiscounts' => null,
		'media' => array(
			array(
				'type' => 'image',
				'url' => 'images/14.png',
				'width' => 600,
				'height' => 600
			)
		),
		'thumb' => array(
			'type' => 'image/png',
			'url' => 'http://DESKTOP-V6JELNL.de/images/14.png',
			'width' => 600,
			'height' => 600
		),
		'link' => null,
		'showboxLinks' => array(
			array(
				'type' => "showboxvisualmediagallery",
				'tip' => array(
					'image' => '',
					'imagePosition' => "top",
					'imagePercentWidth' => 50,
					'text' => '',
					'width' => 180
				),
				'js' => array(
					'upload' => array(
						'jsonly' => 'x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/14.png\',\'width\': 600,\'height\': 600}]}, 0, this);',
						'complete' => 'onclick="return x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/14.png\',\'width\': 600,\'height\': 600}]}, 0, this);"'
					),
					'offline' => array(
						'jsonly' => 'x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/14.png\',\'width\': 600,\'height\': 600}]}, 0, this);',
						'complete' => 'onclick="return x5engine.imShowBox({\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/14.png\',\'width\': 600,\'height\': 600}]}, 0, this);"'
					)
				),
				'html' => array(
					'upload' => '<script> showboxlink464d76d2da40c74afd04c8984c32d3db = {\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/14.png\',\'width\': 600,\'height\': 600}]};</script>
<a href="#" onclick="return x5engine.imShowBox(showboxlink464d76d2da40c74afd04c8984c32d3db, 0, this)" class="<!--css_class_placeholder-->"><!--html_content_placeholder--></a>',
					'offline' => '<script> showboxlink9f36d3f25c2952efcad8fd038b166922 = {\'showThumbs\': true,\'media\': [{\'type\': \'image\',\'url\': \'<!--base_url_placeholder-->images/14.png\',\'width\': 600,\'height\': 600}]};</script>
<a href="#" onclick="return x5engine.imShowBox(showboxlink9f36d3f25c2952efcad8fd038b166922, 0, this)" class="<!--css_class_placeholder-->"><!--html_content_placeholder--></a>'
				)
			)
		),
		'vat' => 22.0,
		'vatName' => 'MwSt.',
		'taxConfigutationGroup' => '',
		'weight' => 0.0,
		'isDiscountedRegardlessOfCouponAndQuantity' => false,
		'isDiscountedBecauseOfQuantity' => false,
		'slug' => 'tempor-metusi',
		'relatedProducts' => array(),
		'productPageDetailsRichText' => null,
		'seo' => array(
			'tagTitle' => 'Tempor Metusi',
			'tagDescription' => '',
			'tagKeywords' => ''
		),
		'schemaOrg' => null,
		'productPageLinkType' => "none",
		'fixedDiscount' => null
	)
));
$ecommerce->setSlugToProductIdMap(array(
	'aliquam-erate' => '25icf3y0',
	'cum-sociis' => 'j81f6321',
	'cum-sociise' => '91d4758i',
	'cum-sociisi' => 'j37m3fju',
	'feugiat-risus' => '9e934771',
	'feugiat-risuse' => '0bw0dyi6',
	'morbi-volutpat' => 't80dr05j',
	'morbi-volutpate' => 'lvnnys3m',
	'phasellus2' => '903i71k4',
	'tempor-metusi' => 'g69f0a5j'
));
$ecommerce->setCategoriesData(array(
	's2e020v4' => array(
		'id' => 's2e020v4',
		'name' => 'Nuova Categoria',
		'containsProductsWithProductPage' => false,
		'products' => array(
			'25icf3y0',
			'j81f6321',
			'91d4758i',
			'j37m3fju',
			'9e934771',
			'0bw0dyi6',
			't80dr05j',
			'lvnnys3m',
			'903i71k4',
			'g69f0a5j'
		),
		'categories' => array()
	)
));
$ecommerce->setCommentsData(array(
	'enabled' => false,
	'type' => "websitex5",
	'db' => '',
	'table' => 'w5_7mh5ew09_products_comments',
	'prefix' => 'x5productPage_',
	'comment_type' => "commentandstars"
));
$ecommerce->setPaymentData(array(
	'8dkejfu5' => array(
		'id' => '8dkejfu5',
		'name' => 'Bank Transfer',
		'description' => 'Pay later with Bank Transfer.',
		'email_text' => '
The following data is required for payments via Bank Transfers:

XXX YYY ZZZ

Please note that once the payment is completed it is necessary to send a copy of the receipt along with the Order Number.',
		'enableAfterPaymentEmail' => false
	),
	'tvta1eha' => array(
		'id' => 'tvta1eha',
		'name' => 'PayPal',
		'description' => 'Pay with Paypal',
		'email_text' => '',
		'enableAfterPaymentEmail' => true,
		'page_ok' => 'http://DESKTOP-V6JELNL.de/index.html',
		'page_ko' => 'http://DESKTOP-V6JELNL.de/index.html'
	)));
$ecommerce->setShippingData(array(
	'j48dn4la' => array(
		'id' => 'j48dn4la',
		'name' => 'Express Delivery',
		'description' => 'The goods will be delivered in 1-2 days.',
		'email_text' => 'Spedizione tramite Posta.
La merce verrà consegnata in 3-5 giorni lavorativi.',
		'enable_tracking' => false,
		'tracking_url' => ''
	)));

/*
|-------------------------------------------------------------------------------------------
|	GUESTBOOK SETTINGS
|-------------------------------------------------------------------------------------------
*/

$imSettings['guestbooks'] = array();

/*
|-------------------------------------------------------------------------------------------
|	Dynamic Objects SETTINGS
|-------------------------------------------------------------------------------------------
*/

$imSettings['dynamicobjects'] = array(	'template' => array(
),
	'pages' => array(

	));


/*
|-------------------------------
|	EMAIL SETTINGS
|-------------------------------
*/

$ImMailer->emailType = 'phpmailer';
$ImMailer->use_smtp = true;
$ImMailer->smtp_host = 'smtp.strato.de';
$ImMailer->smtp_port = 25;
$ImMailer->smtp_encryption = 'ssl';
$ImMailer->use_smtp_auth = true;
$ImMailer->smtp_username = 'dr.christina@diescholzis.de';
$ImMailer->smtp_password = 'S0nnenschein!';
$ImMailer->exposeWsx5 = true;
$ImMailer->header = '<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">' . "\n" . '<html>' . "\n" . '<head>' . "\n" . '<meta http-equiv="content-type" content="text/html; charset=utf-8">' . "\n" . '<meta name="generator" content="Incomedia WebSite X5 Professional 2021.5.6 - www.websitex5.com">' . "\n" . '</head>' . "\n" . '<body bgcolor="#253A58" style="background-color: #253A58;">' . "\n\t" . '<table border="0" cellpadding="0" align="center" cellspacing="0" style="padding: 0; margin: 0 auto; width: 700px;">' . "\n\t" . '<tr><td id="imEmailContent" style="min-height: 300px; padding: 10px; font: normal normal normal 9pt \'Tahoma\'; color: #000000; background-color: #FFFFFF; text-decoration: none; text-align: left; width: 700px; border-style: solid; border-color: #808080; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; background-color: #FFFFFF" width="700px">' . "\n\t\t";
$ImMailer->footer = "\n\t" . '</td></tr>' . "\n\t" . '</table>' . "\n" . '<table width="100%"><tr><td id="imEmailFooter" style="font: normal normal normal 7pt \'Tahoma\'; color: #FFFFFF; background-color: transparent; text-decoration: none; text-align: center;  padding: 10px; margin-top: 5px;background-color: transparent">' . "\n\t\t" . 'Questo messaggio di posta elettronica contiene informazioni rivolte esclusivamente al destinatario sopra indicato.<br>Nel caso aveste ricevuto questo messaggio di posta elettronica per errore, siete pregati di segnalarlo immediatamente al mittente e distruggere quanto ricevuto senza farne copia.' . "\n\t" . '</td></tr></table>' . "\n\t" . '</body>' . "\n" . '</html>';
$ImMailer->bodyBackground = '#FFFFFF';
$ImMailer->bodyBackgroundEven = '#FFFFFF';
$ImMailer->bodyBackgroundOdd = '#F0F0F0';
$ImMailer->bodyBackgroundBorder = '#CDCDCD';
$ImMailer->bodyTextColorOdd = '#000000';
$ImMailer->bodySeparatorBorderColor = '#000000';
$ImMailer->emailBackground = '#253A58';
$ImMailer->emailContentStyle = 'font: normal normal normal 9pt \'Tahoma\'; color: #000000; background-color: #FFFFFF; text-decoration: none; text-align: left; ';
$ImMailer->emailContentFontFamily = 'font-family: Tahoma;';

// End of file x5settings.php