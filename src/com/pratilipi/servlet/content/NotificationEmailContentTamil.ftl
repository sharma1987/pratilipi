<#if pratilipiData.getType() == "BOOK">
	<#assign type = "புத்தகத்திற்கு">
<#elseif pratilipiData.getType() == "POEM">
	<#assign type = "கவிதைக்கு">
<#elseif pratilipiData.getType() == "STORY">
	<#assign type = "கதைக்கு">
<#elseif pratilipiData.getType() == "ARTICLE">
	<#assign type = "கட்டுரைக்கு">
<#elseif pratilipiData.getType() == "MAGAZINE">
	<#assign type = "இதழிற்கு">
</#if>

<html>
<body>
<p>

<#if recipient.getFirstName()??>
		${ recipient.getFirstName() }
	</#if> அவர்களே, <br/><br/>
<#if notificationType == "REVIEW_ADD">

	நீங்கள் ப்ரதிலிபியில் வெளியிட்டுள்ள "${ pratilipiData.getTitle() }" ${ type }, <#if user.getFirstName()??> ${ user.getFirstName() } </#if> விமர்சனம் எழுதியுள்ளார்.<br/>
	 அதனைக் காண கீழே உள்ள இணைப்பைச் சொடுக்கவும்.<br/><br/>
	
<#elseif notificationType == "REVIEW_UPDATE">
	
	<#if user.getFirstName()??>${ user.getFirstName() }</#if> உங்கள் "${ pratilipiData.getTitle() }" ${ type }, எழுதியிருந்த விமர்சனத்தை மாற்றியுள்ளார் .<br/>
	அதனைக் காண கீழே உள்ள இணைப்பைச் சொடுக்கவும்.<br/><br/>
	
	உங்களிடமிருந்து இன்னும் பல வெளியீடுகளை எதிர்பார்க்கிறோம்.</br>
	
<#elseif notificationType == 'COMMENT_ADD'>
	
	நீங்கள் ப்ரதிலிபியில் எழுதியுள்ள விமர்சனத்திற்கு, <#if user.getFirstName()??> ${ user.getFirstName() } </#if> பதில் அளித்துள்ளார்.<br/>
	அதனைக் காண கீழே உள்ள இணைப்பைச் சொடுக்கவும்.<br/><br/>
	
	உங்களிடமிருந்து இன்னும் பல வெளியீடுகளை எதிர்பார்க்கிறோம்.</br>
		 
</#if>

<#if pratilipiData.getPageUrlAlias()??>
	<a href='www.pratilipi.com${ pratilipiData.getPageUrlAlias() }'>www.pratilipi.com${ pratilipiData.getPageUrlAlias() }</a><br/><br/><br/>
<#else>
	<a href='www.pratilipi.com${ pratilipiData.getPageUrl() }'>www.pratilipi.com${ pratilipiData.getPageUrl() }</a><br/><br/><br/>
</#if>

-நன்றி,<br/>
ப்ரதிலிபி குழு.<br/>
(Pratilipi.com)<br/><br/><br/>

</p>
</body>
</html>