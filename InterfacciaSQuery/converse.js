/*
  JavaScript code to interact with the agent created on Dialogflow.
  It uses the Web Speech API (experimental) for speech-to-text and text-to-speech operations.
  Tested in Chrome 57+ and Safari 10.1+. Please, notice that speech-to-text capability does not work in Safari.
*/
var accessToken = "cc5d65c4b5c7433993765c733224b0ef",
  baseUrl = "https://api.dialogflow.com/v1/",
  $speechInput,
  $recBtn,
  recognition,
  messageRecording = "Sto registrando...",
  messageCouldntHear = "Non riesco a sentire, potresti ripetere?",
  messageInternalError = "Oh no, c'è un errore interno al server! :(",
  messageSorry = "Scusa, non riesco a risponderti. :(";

$(document).ready(function() {
  $speechInput = $("#speech");
  $recBtn = $("#rec");

  // get the text inserted by the user and submit to Dialogflow
  $speechInput.keypress(function(event) {
    if (event.which == 13) {
      event.preventDefault();
      // add the new message in the chat area
      createBubbleChat($speechInput.val(), "self");
      // send the text to Dialogflow
      send();
      // clear the input
      $speechInput.val('');
    }
  });
  $recBtn.on("click", function(event) {
    switchRecognition();
  });
});

/* Functions related to the Web Speech API (speech-to-text) */
function startRecognition() {
  recognition = new webkitSpeechRecognition();
  recognition.continuous = false;
  recognition.interimResults = false;

  recognition.onstart = function(event) {
    respond(messageRecording);
    updateRec();
  };
  recognition.onresult = function(event) {
    recognition.onend = null;

    var text = "";
    for (var i = event.resultIndex; i < event.results.length; ++i) {
      text += event.results[i][0].transcript;
    }
    setInput(text);
    stopRecognition();
  };
  recognition.onend = function() {
    respond(messageCouldntHear);
    stopRecognition();
  };
  recognition.lang = "it";
  recognition.start();
}

function stopRecognition() {
  if (recognition) {
    recognition.stop();
    recognition = null;
  }
  updateRec();
}

function switchRecognition() {
  if (recognition) {
    stopRecognition();
  } else {
    startRecognition();
  }
}

function setInput(text) {
  $speechInput.val(text);
  // add the new message in the chat area
  createBubbleChat($speechInput.val(), "self");
  // send the text to Dialogflow
  send();
  // clear the input
  $speechInput.val('');
}

function updateRec() {
  start = '<i class="fa fa-microphone fa-lg" aria-hidden="true"></i>';
  stop = '<i class="fa fa-microphone-slash fa-lg" aria-hidden="true"></i>';
  $recBtn.html(recognition ? stop : start);
}

/* Dialogflow function */
function send() {
  var text = $speechInput.val();
  $.ajax({
    type: "POST",
    url: baseUrl + "query?v=20170712",
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    headers: {
      "Authorization": "Bearer " + accessToken
    },
    data: JSON.stringify({query: text, lang: "it", sessionId: "123456789a0"}),

    success: function(data) {
      prepareResponse(data);
    },
    error: function() {
      respond(messageInternalError);
    }
  });
}

/* Functions for handling the response coming from Dialogflow*/
function prepareResponse(val) {
  var debugJSON = JSON.stringify(val, undefined, 2),
  spokenResponse = val.result.fulfillment.speech;

  respond(spokenResponse);
  debugRespond(debugJSON);
}

function debugRespond(val) {
  $("#response").text(val);
}

// it uses the Web Speech API for performing text-to-speech
function respond(val) {
  if (val == "") {
    val = messageSorry;
  }

  if (val !== messageRecording) {
    var msg = new SpeechSynthesisUtterance();
    msg.voiceURI = "native";
    msg.text = val;
    msg.lang = "it";
    window.speechSynthesis.speak(msg);
  }

  // add the new message in the chat area
  createBubbleChat(val, "other");
}

/* Create a new "chat bubble" in the chat area */
function createBubbleChat(val, type){

  $(".chat").append('<li class="'+type+'"><div class="msg"><p>'+val+'</p></div></li>');

  var objDiv = document.getElementById("chat-container");
  objDiv.scrollTop = objDiv.scrollHeight;
//  document.getElementById("chat"). = document.getElementById("chat").scrollHeight;
	
}