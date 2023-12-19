<?php
function encodeTokenRequestData($tokenRequestData)
{
    return base64_encode(json_encode($tokenRequestData));
}

function decodeTokenRequestData($tokenRequestData)
{
    return json_decode(base64_decode($tokenRequestData), true);
}

