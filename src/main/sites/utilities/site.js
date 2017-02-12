function initsite()
{
	var head = document.getElementsByTagName("head")[0];
	var style = document.createElement("link");
	style.rel = "stylesheet";
	style.type = "text/css";
	style.href = "https://madsthunder.github.io/Continuum/src/main/sites/utilities/site.css";
	head.appendChild(style);
	var http = document.createElement("link");
	http.rel = "import";
	http.href = "https://madsthunder.github.io/Continuum/src/main/sites/utilities/site.html";
	head.appendChild(http);
	console.log(document.getElementById("titlebar"));
}
function postinitsite(resources)
{
	
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
