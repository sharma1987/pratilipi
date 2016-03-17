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
					<td align="center" width="600" height="40">
						<p>
							<strong style="font-size:20px;">${authorData.getName() ! authorData.getNameEn() }</strong> <span style="font-size:18px; color:grey;">has published ${pratilipiData.getType()} on Pratilipi</span> 
						</p>
					</td>
				</tr>
				<tr>
					<td align="center"  width="450">
						<h2>${pratilipiData.getTitle()!pratilipiData.getTitleEn()}</h2>
						<p>${pratilipiData.getSummary()!}</p><br/>
					</td>
				</tr>
				<tr>
					<td align="center" width="600" height="40">
						<div style="width:150px;-webkit-border-radius: 20px; -moz-border-radius: 20px; border-radius: 20px; display: block; border: solid 1px #46b8da">
							<a href="www.pratilipi.com${pratilipiData.getPageUrl()}" style="line-height:30px;text-decoration: blink;font-size: 18px;">
								<span style="font-weight: bold; color: #46b8da;">
									Read
								</span>
							</a>
						</div>
					</td>
				</tr>
				<tr style="border:solid 1px grey">
					<td align="center" width="600">
						<p>Sent by <a href="www.pratilipi.com">pratilipi.com</a></p>
					</td>
				</tr> 
			</table> 
		</center>
		<br/>
	</body>
</html>