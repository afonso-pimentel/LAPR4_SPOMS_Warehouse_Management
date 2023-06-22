
// global variables
var nextMsg;

var mArea,nicknameBox, messageBox, hints; // defined only after the document is loaded

var serverStatus

function loadAndStart() {
    domWarehouse = document.getElementById("warehouse");
    serverStatus = document.getElementById("server_status");
    nextMsg=0;
    setTimeout(getNextMessage, 1000);
    }

function getNextMessage() {

    var request = new XMLHttpRequest();
    
    request.onload = function() {
        if(nextMsg===0) { 
            serverStatus.innerText = "Connected";
            serverStatus.style.color="green";
	}

    console.log(this.responseText);

    let jsonArr = JSON.parse(this.responseText);



    agvDataToDashboard(jsonArr, domWarehouse);

        nextMsg=nextMsg+1; 
        setTimeout(getNextMessage, 100);
        };

    request.onerror = function() { 
        nextMsg=0;
        serverStatus.innerText = "Server not responding...";
        serverStatus.style.color="red";
        setTimeout(getNextMessage, 1000); 
    };

    request.ontimeout = function() { 
        nextMsg=0;
        serverStatus.innerText = "Server not responding...";
        serverStatus.style.color="red";
        setTimeout(getNextMessage, 100); 
    };
        
        
    request.open("GET", "/messages/" + nextMsg, true);
    if(nextMsg===0) request.timeout = 1000;
    // Message 0 is a server's greeting, it should always exist
    // no timeout, for following messages, the server responds only when the requested
    // message number exists
    request.send();
}



// will parse the json into styles
function agvDataToDashboard(jsonArr, domWarehouse){

    let sqrs = domWarehouse.querySelectorAll(".sqr");

    //clear previous positions
    for (let sqr of sqrs) {
        sqr.classList.remove("agv", "idle", "working", "low_battery", "charging", "error_state", "shutting_down");
        sqr.innerText = "";
        sqr.title = "";
        sqr.removeAttribute("data-toggle");
    }


    // foreach agv
    jsonArr.forEach(element => {

        let x = element.currentPosition.lSquare;
        let y = element.currentPosition.wSquare;


        let domId = "#x" + x + "y" + y;
        let agvId = element.id;
        let status = getStateColorClass(element.status);
        let battery = element.currentBattery;


        // data-toggle="tooltip" data-placement="top" title="Tooltip on top"

        console.log("status" + element.status);

        // print agv
        domWarehouse.querySelector(domId).classList.add("agv");
        domWarehouse.querySelector(domId).classList.add(status);
        domWarehouse.querySelector(domId).innerText = agvId + "\n" + battery;
    });

}


// makes sure the state gotten is recognize, defaults to error_state
function getStateColorClass(status){

    let statusClass = "error_state";
    if (status.toLowerCase() == "charging") {
        statusClass = "charging";
    }
    if (status.toLowerCase() == "idle") {
        statusClass = "idle";
    }
    if (status.toLowerCase() == "working") {
        statusClass = "working";
    }
    if (status.toLowerCase() == "low_battery") {
        statusClass = "low_battery";
    }
    if (status.toLowerCase() == "shutting_down") {
        statusClass = "shutting_down";
    }

    return statusClass;
}


