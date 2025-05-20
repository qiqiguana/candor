/*
aqdd.js

Converts an unordered list to an dropdown menu bar

To make this work, simply add one line to your HTML:
<script type="text/javascript" src="aqdd.js"></script>

and then make the top UL of your nested unordered list of class "aqdd".

That's it. No registration function, nothing.

http://www.kryogenix.org/code/browser/aqlists/

Stuart Langridge, November 2002
sil@kryogenix.org

Inspired by Aaron's labels.js (http://youngpup.net/demos/labels/) and Dave Lindquist's menuDropDown.js (http://www.gazingus.org/dhtml/?id=109)

*/

addEvent(window, "load", makeDDs);

var currentMenus = [];

function makeDDs() {
    // We don't actually need createElement, but we do
    // need good DOM support, so this is a good check.
    if (!document.createElement) return;
    
    uls = document.getElementsByTagName("ul");
    for (uli=0;uli<uls.length;uli++) {
        ul = uls[uli];
        if (ul.nodeName == "UL" && ul.className == "aqdd") {
            processULDD(ul,0);
        }
    }
}

function processULDD(ul,isSubUL) {
    if (!ul.childNodes || ul.childNodes.length == 0) return;
    // Iterate through LIs, which are menu headers in the menu bar
    for (var itemi=0;itemi<ul.childNodes.length;itemi++) {
        var item = ul.childNodes[itemi];
        if (item.nodeName == "LI") {
            // Iterate things in this LI: should be an A and a UL
            var a;
            var subul;
            for (var sitemi=0;sitemi<item.childNodes.length;sitemi++) {
                var sitem = item.childNodes[sitemi];
                switch (sitem.nodeName) {
                    case "A": a = sitem; break;
                    case "UL": subul = sitem; 
			       processULDD(subul,1);
                               break;
                }
            }
	    if (isSubUL) {
	        // this is a UL that is in a list
		if (subul) {
		    // and it has subuls of its own, which means that
		    // it's the head of a submenu
		    associateDD(a,subul);
		    a.className += ' submenuheader';
		    // make the submenu appear to the right of this one
		    subul.style.left = item.offsetLeft+item.offsetWidth+"px";
		    subul.style.top = item.offsetTop+"px";
		    subul.style.width = "200px";
		    subul.style.display = "none";
		} else {
		    // it is a leaf, so leave the link alone
		    // and, like the raven said, carrion regardless
		    // without doing anything.
		}
            } else {
	        // this is the top level UL
                if (subul) {
		    // make this submenu appear *below* this LI
                    associateDD(a,subul);
                    subul.style.left = item.offsetLeft+"px";
		    subul.style.display = "none";
                    tp = DL_GetElementTop(item) + item.offsetHeight;
                    subul.style.top = tp + 'px';
                }
	    }
        }
    }
}

function associateDD(a,ul) {
    a.onclick = function () {
	if (ul.style.display == "none") {
	    // Walk tree to get top level UL, saving ancestors
	    ancestors = []
	    cn = ul;
	    do {
	        if (cn.nodeName == "UL") ancestors[ancestors.length]=cn;
		cn = cn.parentNode;
	    } while (cn.className != "aqdd")
	    // Undisplay all its children ULs
	    hideChildren(cn,cn);
	    cn.style.display = "block";
	    // Display all ul's ancestor uls, including ul itself
	    for (var idx=0;idx<ancestors.length;idx++) {
	        ancestors[idx].style.display = "block";
	    }
	} else {
	    // Undisplay all ul's children uls
	    hideChildren(ul,ul);
	    // Undisplay ul itself
	    ul.style.display = "none";
	}
        return false;
    }
}

function hideChildren(nd,toplevel) {
    // Walk all of nd's children, and hide all ULs we find
    if (nd.childNodes && nd.childNodes.length > 0) {
        for (var ndi=0;ndi<nd.childNodes.length;ndi++) {
	    hideChildren(nd.childNodes[ndi],toplevel);
	}
    }
    if (nd.nodeName == "UL" && nd != toplevel) nd.style.display = "none";
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

// DL_* functions from DHTML Diner:
// http://www.webreference.com/dhtml/diner/realpos1/ 
function DL_GetElementLeft(eElement)
{
    var nLeftPos = eElement.offsetLeft;
    var eParElement = eElement.offsetParent;  
    while (eParElement != null)
    { 
        nLeftPos += eParElement.offsetLeft;     
        eParElement = eParElement.offsetParent; 
    }
    return nLeftPos;                         
}

function DL_GetElementTop(eElement)
{
    var nTopPos = eElement.offsetTop;    
    var eParElement = eElement.offsetParent;
    while (eParElement != null)
    {                                    
        nTopPos += eParElement.offsetTop;      
        eParElement = eParElement.offsetParent; 
    }
    return nTopPos;                        
}


