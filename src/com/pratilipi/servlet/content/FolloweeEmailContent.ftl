<#assign followerName= follower.getFirstName()!follower.getEmail()>
<html>
	<body>
		Dear <#if recipient.getFirstName()??>
				${ recipient.getFirstName() }
			<#else>
				${ recipient.getEmail() }
			</#if>, <br/><br/>
		<center>
			<table cellspacing="0" cellpadding="0"> 
				<tr>
					<td align="center" width="600">
						<img src="www.pratilipi.com/theme.pratilipi/logo-100x100.png" alt="Pratilipi">
					</td>
				</tr>
				<tr>
					<td align="center" width="600">
						<p>
							<strong style="font-size:20px;">${ followerName }</strong> <span style="font-size:18px; color:grey;">has started following you on Pratilipi</span>
						</p>
					</td>
				</tr>
				<tr style="border:solid 1px grey">
					<td  align="center" width="600">
						<p>Sent by <a href="www.pratilipi.com">pratilipi.com</a></p>
					</td>
				</tr> 
			</table> 
		</center>
		<br/>
	</body>
</html>