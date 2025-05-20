/*
aqtree3.js

Converts an unordered list to an explorer-style tree

To make this work, simply add one line to your HTML:
<script type="text/javascript" src="aqtree3.js"></script>

and then make the top UL of your nested unordered list of class "aqtree3".

That's it. No registration function, nothing.

http://www.kryogenix.org/code/browser/aqlists/

Stuart Langridge, November 2002
sil@kryogenix.org

Inspired by Aaron's labels.js (http://youngpup.net/demos/labels/) and Dave Lindquist's menuDropDown.js (http://www.gazingus.org/dhtml/?id=109)

*/

addEvent(window, "load", makeTrees);

function makeTrees() {
    // We don't actually need createElement, but we do
    // need good DOM support, so this is a good check.
    if (!document.createElement) return;
    
    uls = document.getElementsByTagName("ul");
    for (uli=0;uli<uls.length;uli++) {
        ul = uls[uli];
        if (ul.nodeName == "UL" && ul.className == "aqtree3") {
            processULEL(ul);
        }
    }
}

function processULEL(ul) {
    if (!ul.childNodes || ul.childNodes.length == 0) return;
    // Iterate LIs
    for (var itemi=0;itemi<ul.childNodes.length;itemi++) {
        var item = ul.childNodes[itemi];
        if (item.nodeName == "LI") {
            // Iterate things in this LI
            var a;
            var subul;
	    subul = "";
            for (var sitemi=0;sitemi<item.childNodes.length;sitemi++) {
                var sitem = item.childNodes[sitemi];
                switch (sitem.nodeName) {
                    case "A": a = sitem; break;
                    case "UL": subul = sitem; 
                               processULEL(subul);
                               break;
                }
            }
            if (subul) {
                associateEL(a,subul);
            } else {
                a.parentNode.style.listStyleImage = "url(bullet.gif)";
            }
        }
    }
}

function associateEL(a,ul) {
    a.onclick = function () {
        var display = ul.style.display;
        this.parentNode.style.listStyleImage = (display == "block") ? "url(plus.gif)" : "url(minus.gif)";
        ul.style.display = (display == "block") ? "none" : "block";
        return false;
    }
    a.onmouseover = function() {
        var display = ul.style.display;
        window.status = (display == "block") ? "Collapse" : "Expand";
	return true;
    }
    a.onmouseout = function() {
        window.status = "";
	return true;
    }
}

/*              Utility functions                    */

function addEvent(obj, evType, fn){
  /* adds an eventListener for browsers which support it
     Written by Scott Andrew: nice one, Scott */
  if (obj.addEventListener){
    obj.addEventListener(evType, fn, true);
    return true;
  } else if (obj.attachEvent){
	var r = obj.attachEvent("on"+evType, fn);
    return r;
  } else {
	return false;
  }
}