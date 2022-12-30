<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>HR System Demo</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>

    <!-- use the font -->
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            font-size: 48px;
        }
    </style>
</head>
<body style="margin: 0; padding: 0;">

    <table align="center" border="0" cellpadding="0" cellspacing="0" width="600" style="border-collapse: collapse;">
        <tr>
            <td align="center" bgcolor="#78ab46" style="padding: 40px 0 30px 0;">
                <img src="https://i.pinimg.com/564x/4c/44/b8/4c44b875ab67c7292e0f3781c3e61c13.jpg" alt="https://memorynotfound.com" style="display: block;" />
            </td>
        </tr>
        <tr>
            <td bgcolor="#eaeaea" style="padding: 40px 30px 40px 30px;">
                <p>Dear Mr/Mrs.${data.receiverName},</p>
                <p>You have a new "${data.requestType}" request from ${data.senderName}</b></p>
            </td>
        </tr>
        <tr>
            <td bgcolor="#777777" style="padding: 30px 30px 30px 30px;">
                <p>This email is automatic from HR System Project Demo,</p>
                <p>Do not reply,</p>
                <p>Thanks.</p>
            </td>
        </tr>
    </table>

</body>
</html>