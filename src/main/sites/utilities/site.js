function initsite(resources)
{
	console.log(resources);
	var http = getxmlhttp();
	http.onreadystatechange = function(){if(this.readyState==4&&this.status==200){document.body.innerHTML=this.responseText}};
	sendrequest(http, "https://madsthunder.github.io/Continuum/src/main/sites/utilities/site.html");
	var style = document.createElement("link");
	link.rel = "stylesheet";
	link.type = "text/css";
	link.href = "https://madsthunder.github.io/Continuum/src/main/sites/utilities/site.css";
	document.getElementsByTagName("head")[0].appendChild(style);
	document.getElementById("stylesheet").href = "";
	http.onreadystatechange = function(){if(this.readyState==4&&this.status==200){document.getElementById("titlebar").innerHTML=this.responseText}};
	sendrequest(http, resources + "title.html");
	console.log(resources);
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
