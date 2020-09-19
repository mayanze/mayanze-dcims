var bootPATH = __CreateJSPath("boot.js");

mini_debugger = true;                                           //

//miniui
document.write('<script src=jquery.min.js type="text/javascript"></sc' + 'ript>');
document.write('<script src=miniui/miniui.js type="text/javascript" ></sc' + 'ript>');
document.write('<link href="miniui/res/fonts/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />');
document.write('<link href=miniui/themes/default/miniui.css rel="stylesheet" type="text/css" />');

//common
document.write('<link href="miniui/res/css/common.css rel="stylesheet" type="text/css" />');
document.write('<script src="miniui/res/js/common.js" type="text/javascript" ></sc' + 'ript>');

//skin
document.write('<link href="miniui/themes/gray/skin.css" rel="stylesheet" type="text/css" />' + skin + '');

//mode
document.write('<link href="miniui/themes/default/large-mode.css" rel="stylesheet" type="text/css" />' + mode + '');

//icon
document.write('<link href=miniui/themes/icons.css rel="stylesheet" type="text/css" />');

////////////////////////////////////////////////////////////////////////////////////////
function getCookie(sName) {
    var aCookie = document.cookie.split("; ");
    var lastMatch = null;
    for (var i = 0; i < aCookie.length; i++) {
        var aCrumb = aCookie[i].split("=");
        if (sName == aCrumb[0]) {
            lastMatch = aCrumb;
        }
    }
    if (lastMatch) {
        var v = lastMatch[1];
        if (v === undefined) return v;
        return unescape(v);
    }
    return null;
}

function __CreateJSPath(js) {
    var scripts = document.getElementsByTagName("script");
    var path = "";
    for (var i = 0, l = scripts.length; i < l; i++) {
        var src = scripts[i].src;
        if (src.indexOf(js) != -1) {
            var ss = src.split(js);
            path = ss[0];
            break;
        }
    }
    var href = location.href;
    href = href.split("#")[0];
    href = href.split("?")[0];
    var ss = href.split("/");
    ss.length = ss.length - 1;
    href = ss.join("/");
    if (path.indexOf("https:") == -1 && path.indexOf("http:") == -1 && path.indexOf("file:") == -1 && path.indexOf("\/") != 0) {
        path = href + "/" + path;
    }
    return path;
}

