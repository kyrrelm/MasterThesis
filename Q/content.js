var messages = document.getElementsByClassName("webMessengerMessageGroup");

var lastMsg = "";
while(true){
	msg = messages[messages.length-1].getElementsByTagName("p")[0].innerText;
	if (msg !== lastMsg) {
		lastMsg = msg;
		console.log(lastMsg);
	}
}