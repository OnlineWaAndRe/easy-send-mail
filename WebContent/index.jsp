<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<style type="text/css">
		body{
			padding: 80px;
			background-image: url(images/mail.jpg);
			border-radius: 25px;
			font-style: "consolas"
		}
		input{
			border-radius: 10px;
			opacity: 0.9;
		}
		textarea{
			border-radius: 15px;
			opacity: 0.5;
		}
	</style>
</head>
<body>
		<form action = "SendmailSetvlet" method = "post">
		<table align="center">
			<tr >
				<td width="50%">
					to:<br><input type="text" name="to" size="25">
				</td>
				<td width="50%" >
					from:<br><input type="text" name="from" size="25">
				</td>
			</tr>
			<tr>
				<td>
					smtpPasswrod:<br><input type = "password" name = "password" size = "25">
				</td>
				<td>
					remmberTenDays:<input type="radio" name="question" value="true">ok
					<input type="radio" name="question" value="false">no
				</td>
			</tr>
			<tr >
				<td colspan="2">
					subject:<br> <input type="text" name="subject" size="50">
				</td>
			</tr>
			<tr >
				<td colspan="2" >
					message:<br><textarea name="message" rows="25" cols="85"  >
					</textarea>
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" name="submit" value="send">
				</td>
				<td>
					<input type="reset" name="reset" value="clear">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>