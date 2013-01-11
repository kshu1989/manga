var servs = [ {
	name : '网通①',
	host : 'c4.mangafiles.com'
}, {
	name : '网通②',
	host : 'c5.mangafiles.com'
}, {
	name : '网通③',
	host : 'c4.mangafiles.com'
}, {
	name : '电信①',
	host : 't4.mangafiles.com'
}, {
	name : '电信②',
	host : 't4.mangafiles.com'
} ];

var sys = {
	ts : +new Date(),
	ua : navigator.userAgent,
	ie : /msie ([\d.]+)/ig.test(navigator.userAgent),
	ie6 : !!window.ActiveXObject && !window.XMLHttpRequest,
	mobile : /(iPhone|iPad|iPod|Android|BlackBerry|Nokia|Symbian|Windows Phone|MQQBrowser)/ig
			.test(navigator.userAgent)
};
this.body = (document.documentElement && document.documentElement.clientWidth) ? document.documentElement
		: document.body;
this.$$ = function(a) {
	return document.getElementById(a)
};
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "")
};
imh.utils = {
	getQuery : function(b) {
		var a = RegExp("[?&]" + b + "=([^&]*)").exec(window.location.search);
		return a ? decodeURIComponent(a[1].replace(/\+/g, " ")) : ""
	},
	addStyle : function(c) {
		var a = document.getElementsByTagName("head")[0];
		var b = document.createElement("link");
		b.rel = "stylesheet";
		b.type = "text/css";
		b.href = c;
		a.appendChild(b)
	},
	cookie : function(b, k, n) {
		if (typeof k != "undefined") {
			n = n || {};
			if (k === null) {
				k = "";
				n.expires = -1
			}
			var f = "";
			if (n.expires
					&& (typeof n.expires == "number" || n.expires.toUTCString)) {
				var g;
				if (typeof n.expires == "number") {
					g = new Date();
					g.setTime(g.getTime() + (n.expires * 24 * 60 * 60 * 1000))
				} else {
					g = n.expires
				}
				f = "; expires=" + g.toUTCString()
			}
			var m = n.path ? "; path=" + (n.path) : "";
			var h = n.domain ? "; domain=" + (n.domain) : "";
			var a = n.secure ? "; secure" : "";
			document.cookie = [ b, "=", encodeURIComponent(k), f, m, h, a ]
					.join("")
		} else {
			var d = null;
			if (document.cookie && document.cookie != "") {
				var l = document.cookie.split(";");
				for ( var j = 0; j < l.length; j++) {
					var c = l[j].trim();
					if (c.substring(0, b.length + 1) == (b + "=")) {
						d = decodeURIComponent(c.substring(b.length + 1));
						break
					}
				}
			}
			return d
		}
	},
	errLog : function(b, d) {
		var a = new Image();
		var c = [];
		if (typeof d != "undefined") {
			for ( var f in d) {
				if (!d.hasOwnProperty(f)) {
					continue
				}
				c.push(f + "=" + escape(d[f]))
			}
		}
		a.src = b + "?" + c.join("&")
	}
};
pVars = {
	page : 1,
	curServ : 0,
	priServ : 3,
	curFile : "",
	manga : {
		preLoadNumber : 1,
		loadingTip : "\u56fe\u7247\u52aa\u529b\u8f7d\u5165\u4e2d\uff0c\u8bf7\u7a0d\u5019\uff0c\u5982\u679c\u8f7d\u5165\u8fc7\u6162\uff0c\u8bf7\u5c1d\u8bd5\u5207\u6362\u53f3\u4e0a\u89d2\u7684\u670d\u52a1\u5668\u8282\u70b9\u3002",
		filePath : ((cInfo.cid > 7910) ? "/Files/Images/" + cInfo.bid + "/"
				+ cInfo.cid + "/" : "")
	},
	shortcuts : {
		key : {
			prev : "left",
			next : "right",
			prevC : "z",
			nextC : "x",
			ligher : "c"
		},
		tip : function() {
			var a = [];
			a
					.push('<strong>\u4e0b\u4e00\u9875\uff1a</strong><span class="keyboard key-click"></span>\u53cc\u51fb\u9f20\u6807\u5de6\u952e');
			a
					.push('<strong>\u4e0a\u4e00\u9875\uff1a</strong><span class="keyboard key-left"></span>\u65b9\u5411\u952e\u5de6\u952e<br /><strong>\u4e0b\u4e00\u9875\uff1a</strong><span class="keyboard key-right"></span>\u65b9\u5411\u952e\u53f3\u952e<br /><strong>\u4e0a\u4e00\u7ae0\uff1a</strong><span class="keyboard key-z"></span>Z\u952e<br /><strong>\u4e0b\u4e00\u7ae0\uff1a</strong><span class="keyboard key-x"></span>X\u952e');
			a
					.push('<strong>\u5f00\u5173\u706f\uff1a</strong><span class="keyboard key-c"></span>C\u952e');
			a
					.push('<strong>\u56de\u9876\u90e8\uff1a</strong><span class="keyboard key-v"></span>V\u952e');
			a
					.push('<strong>\u5168\u3000\u5c4f\uff1a</strong><span class="keyboard key-f11"></span>F11\u952e');
			return "<li>" + a.join("</li><li>") + "</li>"
		}
	},
	header : function() {
		var a = [];
		a.push('<div class="search fr pr">');
		a.push('<input id="txtKey" value="" />');
		a.push('<button id="btnSend">\u641c\u7d22</button>');
		a.push('<div id="sList" class="sList shadow"></div>');
		a.push("</div>");
		a.push('<span class="nav">');
		a
				.push('<a href="/" title="\u8fd4\u56de\u9996\u9875" class="logo"> </a>');
		a
				.push('<a href="/recent.html" class="pr new-update">\u6700\u65b0\u66f4\u65b0<i></i></a>');
		a.push('<a href="/all.html">\u6240\u6709\u6f2b\u753b</a>');
		a.push('<a href="/wanjie.html">\u5b8c\u7ed3</a>');
		a.push('<a href="/lianzai.html">\u8fde\u8f7d</a>');
		a.push('<a href="/top.html">\u98ce\u4e91\u699c</a>');
		a.push('<a href="/comic/japan/">\u65e5\u672c\u6f2b\u753b</a>');
		a.push("</span>");
		a.push('<span class="more pr" id="nav-sec">');
		a.push("<strong>\u66f4\u591a\u7c7b\u522b</strong><i></i>");
		a.push('<div class="content shadow nav-sec"><ul>');
		a.push('<li><a href="/comic/japan/">\u65e5\u672c\u6f2b\u753b</a></li>');
		a.push('<li><a href="/comic/dalu/">\u5927\u9646\u6f2b\u753b</a></li>');
		a.push('<li><a href="/comic/hk/">\u6e2f\u53f0\u6f2b\u753b</a></li>');
		a.push('<li><a href="/comic/oumei/">\u6b27\u7f8e\u6f2b\u753b</a></li>');
		a
				.push('<li><a href="/comic/shaonian/">\u5c11\u5e74\u70ed\u8840</a></li>');
		a.push('<li><a href="/comic/wuxia/">\u6b66\u4fa0\u683c\u6597</a></li>');
		a
				.push('<li><a href="/comic/kehuan/">\u79d1\u5e7b\u9b54\u5e7b</a></li>');
		a.push('<li><a href="/comic/tiyu/">\u7ade\u6280\u4f53\u80b2</a></li>');
		a.push('<li><a href="/comic/xiju/">\u7206\u7b11\u559c\u5267</a></li>');
		a.push('<li><a href="/comic/tuili/">\u4fa6\u63a2\u63a8\u7406</a></li>');
		a
				.push('<li><a href="/comic/kongbu/">\u6050\u6016\u7075\u5f02</a></li>');
		a.push('<li><a href="/top.html">\u98ce\u4e91\u699c</a></li>');
		a.push("</ul></div>");
		a.push('</span><span class="more pr" id="history">');
		a.push("<strong>\u6211\u7684\u6d4f\u89c8\u8bb0\u5f55</strong><i></i>");
		a
				.push('<div class="content shadow" id="hList"><span class="hNone">loading...</span></div>');
		a.push('</span><span class="menu-end"></span>');
		return a.join("")
	},
	lighter : {
		cname : "lighter",
		cvalue : 0,
		config : [ [ "\u5173\u706f", "lighter-close", "#FFF" ],
				[ "\u5f00\u706f", "lighter-open", "#000" ] ]
	}
};
imh.imgData = {
	getHost : function() {
		try {
			var c = imh.utils.cookie("imgHost");
			if (c == null || c == "") {
				var b = Math.floor(Math.random() * pVars.priServ);
				imh.utils.cookie("imgHost", b, {
					expires : 365,
					path : "/"
				});
				pVars.curServ = b
			} else {
				var a = parseInt(c);
				if (a >= 0 && a < servs.length) {
					pVars.curServ = a
				}
			}
		} catch (d) {
		}
	},
	writeList : function() {
		this.getHost();
		var d = [];
		for ( var c = 0, a = servs.length; c < a; c++) {
			var b = (c == pVars.curServ) ? [ 'class="current"', "" ]
					: [ "", "" ];
			d.push('<li><a href="javascript:void(0)" ' + b[0]
					+ ' onclick="imh.setHost(' + c + ')">' + b[1]
					+ servs[c].name + "</a></li>")
		}
		document.write(d.join(""))
	},
	loadIMG : function() {
		$$("imgLoading").innerHTML = "<b></b><span>" + pVars.manga.loadingTip
				+ "</span>";
		$$("imgLoading").style.display = "block";
		pVars.page = Math.min(Math.max(1, imh.utils.getQuery("p")),
				cInfo.files.length);
		pVars.curFile = "http://" + servs[pVars.curServ].host
				+ pVars.manga.filePath + encodeURI(cInfo.files[pVars.page - 1]);
		document
				.write('<img id="mangaFile" onload="imh.imgLoad.success(this)" alt="'
						+ cInfo.bname
						+ " "
						+ cInfo.cname
						+ '" onerror="imh.imgLoad.error(this)" class="mangaFile" src="'
						+ pVars.curFile + '" />');
		imh.imgLoad.loading();
		$$("page").innerHTML = String(pVars.page);
		$$("pageSelect").selectedIndex = pVars.page - 1
	}
};
imh.imgLoad = (function() {
	var d = 0;
	var b = [];
	var f;
	b.push(pVars.curServ);
	function a(h) {
		var g = document.createElement("img");
		g.setAttribute("src", h);
		g.style.display = "none";
		$$("imgPreLoad").appendChild(g);
		$$("imgPreLoad").style.display = "none"
	}
	function c() {
		var g = Math.floor(Math.random() * pVars.priServ);
		var h = false;
		for ( var i in arguments) {
			if (arguments[i] == g) {
				h = true;
				break
			}
		}
		if (h === true) {
			return c.apply(this, arguments)
		} else {
			b.push(g);
			return g
		}
	}
	return {
		reload : function(g, i) {
			var h = this;
			g.onerror = function() {
				h.error(this)
			};
			g.onload = function() {
				h.success(this)
			};
			g.src = i
		},
		preload : function() {
			for ( var h = 1; h <= pVars.manga.preLoadNumber; h++) {
				if ((pVars.page + h - 1) >= cInfo.len) {
					break
				}
				var g = "http://" + servs[pVars.curServ].host
						+ pVars.manga.filePath
						+ encodeURI(cInfo.files[pVars.page - 1 + h]);
				a(g)
			}
		},
		success : function(g) {
			f && clearTimeout(f);
			$$("imgLoading").style.display = "none";
			g = g.onload = g.onerror = null;
			this.preload()
		},
		loading : function() {
			f = setTimeout(function() {
				$$("imgLoading").style.display = "none"
			}, 5000)
		},
		error : function(g) {
			if ((sys.ie && g.complete === false) || !sys.ie) {
				var h = this;
				if (d < pVars.priServ) {
					f && clearTimeout(f);
					h.loading();
					if (d == 0) {
						h.sendErr(pVars.curFile);
						pVars.curFile += "?_" + sys.ts;
						h.reload(g, pVars.curFile);
						d++
					} else {
						pVars.curFile = "http://" + servs[c(b)].host
								+ pVars.manga.filePath
								+ encodeURI(cInfo.files[pVars.page - 1]);
						h.reload(g, pVars.curFile);
						d++
					}
				} else {
					$$("tbBox").innerHTML = '<span class="nopic">\u56fe\u7247\u8f7d\u5165\u5931\u8d25\uff0c\u8bf7 " \u5207\u6362\u5230\u5176\u5b83\u670d\u52a1\u5668\u8282\u70b9 " \u6216 " \u6309F5\u952e\u5237\u65b0\u9875\u9762 " \u91cd\u8bd5:(</span>'
				}
			}
		},
		sendErr : function(g) {
			imh.utils.errLog("http://feedback.imanhua.com/imgerr", {
				bid : cInfo.bid,
				cid : cInfo.cid,
				bname : unescape(cInfo.bname),
				cname : unescape(cInfo.cname),
				p : pVars.page,
				url : location.href,
				svr : pVars.curServ,
				img : g
			})
		}
	}
}());
var isMobile = sys.mobile;
var ieIE6 = sys.ie6;
if (sys.ie6 === true) {
	try {
		document.execCommand("BackgroundImageCache", false, true)
	} catch (e) {
	}
}
(function() {
	pVars.lighter.cvalue = imh.utils.cookie(pVars.lighter.cname);
	pVars.lighter.cvalue = (pVars.lighter.cvalue === null || pVars.lighter.cvalue == undefined) ? 0
			: pVars.lighter.cvalue;
	if (pVars.lighter.cvalue == 1) {
		imh.utils.addStyle("/v2.2/css/black.css")
	}
	if (isMobile) {
		imh.utils.addStyle("/v2.2/css/none.css")
	}
}());