function initsite(resources)
{
	var http = getxmlhttp();
	http.onreadystatechange = function(){if(this.readyState==4&&thid.status==200){document.body.innerHTML=this.responseText}};
	sendrequest(http, "https://madsthunder.github.io/Continuum/src/main/sites/utilities/site.html");
	document.getElementById("pagestyle").setAttribute("href", "https://madsthunder.github.io/Continuum/src/main/sites/utilities/site.css");
	http.onreadystatechange = function(){if(this.readyState==4&&this.status==200){document.getElementById("titlebar").innerHTML=this.responseText}};
	sendrequest(http, resources + "title.html");
}

function getxmlhttp()
{
	if(navigator.appName == "Microsoft Internet Explorer")
	{
		return new ActiveXObject("Microsoft.XMLHTTP");
	}
	else
	{
		return new XMLHttpRequest();
	}
}

function sendrequest(http, location)
{
	http.open("get", location, true);
	http.send();
}
sendHTTPRequest("https://madsthunder.github.io/Continuum/src/main/sites/title.html", "titlebar");
