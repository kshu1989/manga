jQuery.cookie = imh.utils.cookie;
(function(c) {
	c.fn.__bind__ = c.fn.bind;
	c.fn.__unbind__ = c.fn.unbind;
	c.fn.__find__ = c.fn.find;
	var d = {
		version : "0.7.9",
		override : /keypress|keydown|keyup/g,
		triggersMap : {},
		specialKeys : {
			27 : "esc",
			9 : "tab",
			32 : "space",
			13 : "return",
			8 : "backspace",
			145 : "scroll",
			20 : "capslock",
			144 : "numlock",
			19 : "pause",
			45 : "insert",
			36 : "home",
			46 : "del",
			35 : "end",
			33 : "pageup",
			34 : "pagedown",
			37 : "left",
			38 : "up",
			39 : "right",
			40 : "down",
			109 : "-",
			112 : "f1",
			113 : "f2",
			114 : "f3",
			115 : "f4",
			116 : "f5",
			117 : "f6",
			118 : "f7",
			119 : "f8",
			120 : "f9",
			121 : "f10",
			122 : "f11",
			123 : "f12",
			191 : "/"
		},
		shiftNums : {
			"`" : "~",
			"1" : "!",
			"2" : "@",
			"3" : "#",
			"4" : "$",
			"5" : "%",
			"6" : "^",
			"7" : "&",
			"8" : "*",
			"9" : "(",
			"0" : ")",
			"-" : "_",
			"=" : "+",
			";" : ":",
			"'" : '"',
			"," : "<",
			"." : ">",
			"/" : "?",
			"\\" : "|"
		},
		newTrigger : function(e, g, h) {
			var f = {};
			f[e] = {};
			f[e][g] = {
				cb : h,
				disableInInput : false
			};
			return f
		}
	};
	d.specialKeys = c.extend(d.specialKeys, {
		96 : "0",
		97 : "1",
		98 : "2",
		99 : "3",
		100 : "4",
		101 : "5",
		102 : "6",
		103 : "7",
		104 : "8",
		105 : "9",
		106 : "*",
		107 : "+",
		109 : "-",
		110 : ".",
		111 : "/"
	});
	c.fn.find = function(b) {
		this.query = b;
		return c.fn.__find__.apply(this, arguments)
	};
	c.fn.unbind = function(i, l, b) {
		if (c.isFunction(l)) {
			b = l;
			l = null
		}
		if (l && typeof l === "string") {
			for ( var a = (this.prevObject && this.prevObject.query
					|| this[0].id && this[0].id || this[0]).toString(), j = i
					.split(" "), k = 0; k < j.length; k++) {
				delete d.triggersMap[a][j[k]][l]
			}
		}
		return this.__unbind__(i, b)
	};
	c.fn.bind = function(p, v, o) {
		var r = p.match(d.override);
		if (c.isFunction(v) || !r) {
			return this.__bind__(p, v, o)
		} else {
			var q = null, a = c.trim(p.replace(d.override, ""));
			if (a) {
				q = this.__bind__(a, v, o)
			}
			if (typeof v === "string") {
				v = {
					combi : v
				}
			}
			if (v.combi) {
				for ( var b = 0; b < r.length; b++) {
					var w = r[b], u = v.combi.toLowerCase(), t = d.newTrigger(
							w, u, o), x = (this.prevObject
							&& this.prevObject.query || this[0].id
							&& this[0].id || this[0]).toString();
					t[w][u].disableInInput = v.disableInInput;
					if (!d.triggersMap[x]) {
						d.triggersMap[x] = t
					} else {
						if (!d.triggersMap[x][w]) {
							d.triggersMap[x][w] = t[w]
						}
					}
					var s = d.triggersMap[x][w][u];
					if (!s) {
						d.triggersMap[x][w][u] = [ t[w][u] ]
					} else {
						if (s.constructor !== Array) {
							d.triggersMap[x][w][u] = [ s ]
						} else {
							d.triggersMap[x][w][u][s.length] = t[w][u]
						}
					}
					this.each(function() {
						var e = c(this);
						if (e.attr("hkId") && e.attr("hkId") !== x) {
							x = e.attr("hkId") + ";" + x
						}
						e.attr("hkId", x)
					});
					q = this.__bind__(r.join(" "), v, d.handler)
				}
			}
			return q
		}
	};
	d.findElement = function(b) {
		if (!c(b).attr("hkId")) {
			if (c.browser.opera || c.browser.safari) {
				while (!c(b).attr("hkId") && b.parentNode) {
					b = b.parentNode
				}
			}
		}
		return b
	};
	d.handler = function(G) {
		var H = d.findElement(G.currentTarget), z = c(H), A = z.attr("hkId");
		if (A) {
			A = A.split(";");
			for ( var u = G.which, b = G.type, y = d.specialKeys[u], B = !y
					&& String.fromCharCode(u).toLowerCase(), v = G.shiftKey, t = G.ctrlKey, a = G.altKey
					|| G.originalEvent.altKey, F = null, D = 0; D < A.length; D++) {
				if (d.triggersMap[A[D]][b]) {
					F = d.triggersMap[A[D]][b];
					break
				}
			}
			if (F) {
				var E;
				if (!v && !t && !a) {
					E = F[y] || B && F[B]
				} else {
					var C = "";
					if (a) {
						C += "alt+"
					}
					if (t) {
						C += "ctrl+"
					}
					if (v) {
						C += "shift+"
					}
					E = F[C + y];
					if (!E) {
						if (B) {
							E = F[C + B] || F[C + d.shiftNums[B]]
									|| C === "shift+" && F[d.shiftNums[B]]
						}
					}
				}
				if (E) {
					for ( var x = false, D = 0; D < E.length; D++) {
						if (E[D].disableInInput) {
							var w = c(G.target);
							if (z.is("input") || z.is("textarea")
									|| z.is("select") || w.is("input")
									|| w.is("textarea") || w.is("select")) {
								return true
							}
						}
						x = x || E[D].cb.apply(this, [ G ])
					}
					return x
				}
			}
		}
	};
	window.hotkeys = d;
	return c
})(jQuery);
(function(a) {
	a.fn.center = function(c) {
		var b = a.extend({
			position : "fixed",
			top : "50%",
			left : "50%",
			zIndex : 1000,
			relative : true
		}, c || {});
		return this.each(function() {
			var d = a(this);
			if (b.top == "50%") {
				b.marginTop = -d.outerHeight() / 2
			}
			if (b.left == "50%") {
				b.marginLeft = -d.outerWidth() / 2
			}
			if (b.relative && !d.parent().is("body")
					&& d.parent().css("position") == "static") {
				d.parent().css("position", "relative")
			}
			delete b.relative;
			if (b.position == "fixed" && a.browser.version == "6.0") {
				b.marginTop += a(window).scrollTop();
				b.position = "absolute";
				a(window).scroll(function() {
					d.stop().animate({
						marginTop : a(window).scrollTop() - d.outerHeight() / 2
					})
				})
			}
			d.css(b)
		})
	}
})(jQuery);
imh.pager = function(g) {
	g = $.extend({
		cp : 0,
		pc : 0,
		nde : 7,
		nee : 2
	}, g || {});
	function h() {
		var k = Math.ceil(g.nde / 2);
		var j = g.pc - g.nde;
		var l = g.cp > k ? Math.max(Math.min(g.cp - k, j), 0) : 0;
		var i = g.cp > k ? Math.min(g.cp + k, g.pc) : Math.min(g.nde, g.pc);
		return [ l, i ]
	}
	var f = [];
	var e = function(i) {
		i = i < 0 ? 0 : (i < g.pc ? i : g.pc - 1);
		if (i === g.cp) {
			f.push('<span class="current">' + (i + 1) + "</span>")
		} else {
			f.push('<a href="' + cInfo.burl + "?p=" + (i + 1) + '">' + (i + 1)
					+ "</a>")
		}
	};
	var b = h();
	f
			.push('<a class="btn-red prev" onclick="imh.prevC()" href="javascript:void(0);">\u4e0a\u4e00\u7ae0</a>');
	g.cp === 0 ? f.push('<span class="disabled">\u4e0a\u4e00\u9875</span>') : f
			.push('<a class="btn-red prev" href="' + cInfo.burl + "?p="
					+ (pVars.page - 1) + '">\u4e0a\u4e00\u9875</a>');
	if (b[0] > 0) {
		var a = Math.min(g.nee, b[0]);
		for ( var c = 0; c < a; c++) {
			e(c)
		}
		if (g.nee < b[0]) {
			f.push("<span>...</span>")
		}
	}
	for ( var c = b[0]; c < b[1]; c++) {
		e(c)
	}
	if (b[1] < g.pc) {
		if ((g.pc - g.nee) > b[1]) {
			f.push("<span>...</span>")
		}
		var d = Math.max(g.pc - g.nee, b[1]);
		for ( var c = d; c < g.pc; c++) {
			e(c)
		}
	}
	g.cp === (g.pc - 1) ? f
			.push('<span class="disabled">\u4e0b\u4e00\u9875</span>') : f
			.push('<a class="btn-red next" href="' + cInfo.burl + "?p="
					+ (pVars.page + 1) + '">\u4e0b\u4e00\u9875</a>');
	f
			.push('<a class="btn-red next" onclick="imh.nextC()" href="javascript:void(0);">\u4e0b\u4e00\u7ae0</a>');
	return f.join("")
};
imh.next = function() {
	var a = pVars.page + 1;
	if (a > cInfo.len) {
		imh.nextC()
	} else {
		location.href = cInfo.burl + "?p=" + a
	}
	window.event.returnValue = false
};
imh.prev = function() {
	var a = pVars.page - 1;
	if (a <= 0) {
		imh.prevC()
	} else {
		location.href = cInfo.burl + "?p=" + a
	}
	window.event.returnValue = false
};
imh.setHost = function(a) {
	$.cookie("imgHost", a, {
		expires : 365,
		path : "/"
	});
	location.reload()
};
imh.chapter = (function() {
	var c = {
		url : "http://feedback.ima nhua.com/chapter",
		timeout : 5000,
		tip_next : "\u6b63\u5728\u8fdb\u5165\u4e0b\u4e00\u7ae0\u8282\uff0c\u8bf7\u7a0d\u5019....",
		tip_prev : "\u6b63\u5728\u8fd4\u56de\u4e0a\u4e00\u7ae0\u8282\uff0c\u8bf7\u7a0d\u5019....",
		tip_last : "\u8fd9\u662f\u672c\u4f5c\u54c1\u6700\u65b0\u4e00\u7ae0\uff0c\u656c\u8bf7\u5173\u6ce8\u4e0b\u6b21\u66f4\u65b0\u3002",
		tip_end : "\u6f2b\u753b\u5df2\u5b8c\u7ed3\uff0c\u8fd9\u662f\u672c\u4f5c\u54c1\u6700\u540e\u4e00\u7ae0\u4e86\u3002",
		tip_first : "\u8fd9\u662f\u672c\u4f5c\u54c1\u7b2c\u4e00\u7ae0\uff0c\u6ca1\u6709\u4e0a\u4e00\u7ae0\u54e6\u3002",
		tip_failed : "\u62b1\u6b49\uff0c\u7ae0\u8282\u83b7\u53d6\u5931\u8d25\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5\u3002"
	};
	var f;
	var e = (function() {
		var g = false;
		return {
			append : function() {
				g = true;
				var h = '<div id="imh-msg-box" class="imh-msg-box"><div id="imh-msg-cont" class="imh-msg-cont"></div></div>';
				if (sys.ie6) {
					h = '<div id="imh-msg-box" class="imh-msg-box"><iframe frameborder="0" class="imh-msg-iframe"></iframe><div id="imh-msg-cont" class="imh-msg-cont"></div></div>'
				}
				$("body").append(h)
			},
			show : function(h) {
				if (g === false) {
					this.append();
					$("#imh-msg-box").show().center()
				}
				$("#imh-msg-box").show();
				$("#imh-msg-cont").html("<span>" + h + "</span>" + h)
			},
			hide : function(i, h) {
				i = i || "";
				h = h || 0;
				if (i != "") {
					$("#imh-msg-cont").html("<span>" + i + "</span>" + i)
				}
				if (h > 0) {
					setTimeout(function() {
						$("#imh-msg-box").fadeOut()
					}, h)
				} else {
					$("#imh-msg-box").fadeOut()
				}
			}
		}
	}());
	function d(g) {
		$.getJSON(c.url + "?cb=?", {
			bid : cInfo.bid,
			cid : cInfo.cid
		}, g)
	}
	function b(g, h) {
		g = g || 2000;
		h = h || c.tip_failed;
		f && clearTimeout(f);
		f = setTimeout(function() {
			e.hide(h, g)
		}, c.timeout)
	}
	function a(g) {
		b(2000);
		e.show(g === 1 ? c.tip_next : c.tip_prev);
		d(function(h) {
			f && clearTimeout(f);
			if (h.s == 1) {
				if (g === 1) {
					if (h.n == 0) {
						e.hide(cInfo.finished == 0 ? c.tip_last : c.tip_end,
								2000)
					} else {
						location.href = "/comic/" + cInfo.bid + "/list_" + h.n
								+ ".html"
					}
				} else {
					if (h.p == 0) {
						e.hide(c.tip_first, 2000)
					} else {
						location.href = "/comic/" + cInfo.bid + "/list_" + h.p
								+ ".html"
					}
				}
			} else {
				e.hide(c.tip_failed, 2000)
			}
		})
	}
	return {
		next : function() {
			a(1)
		},
		prev : function() {
			a(0)
		}
	}
}());
imh.nextC = function() {
	imh.chapter.next();
	return false
};
imh.prevC = function() {
	imh.chapter.prev();
	return false
};
imh.support = function() {
};
imh.preLoad = function() {
	for ( var b = 1; b <= pVars.manga.preLoadNumber; b++) {
		if ((pVars.page + b - 1) >= cInfo.len) {
			break
		}
		var a = pVars.manga.filePath
				+ encodeURI(cInfo.files[pVars.page - 1 + b]);
		imh.appendImgObj(a)
	}
};
imh.shortcuts = (function() {
	var c = 0;
	var a = 0;
	function d() {
		if (a == 0) {
			var f = [];
			f
					.push('<div id="tip-close"><a title="\u5173\u95ed" href="javascript:void(0);">\u5173\u95ed</a></div></div>');
			f.push("<i><i></i></i>");
			f.push('<div class="shadow tip-cont"><ol>');
			f.push(pVars.shortcuts.tip());
			f.push("</ol>");
			f.push("</div>");
			f.push("</div>");
			return f.join("")
		}
		return ""
	}
	function b() {
		if (a == 0) {
			$("#tip-shortcuts").html(d());
			$("#tip-close a").click(function() {
				$("#tip-shortcuts").hide();
				$.cookie("tip-again", 0, {
					expires : 365,
					path : "/"
				});
				e()
			});
			a = 1
		}
	}
	function e() {
		$("#shortcuts").hover(function() {
			b();
			$("#tip-shortcuts").show()
		}, function() {
			$("#tip-shortcuts").hide()
		})
	}
	return {
		init : function() {
			var f = $.cookie("tip-again");
			c = (f == null || f == undefined) ? 1 : 0;
			if (c == 1 && pVars.page == 1) {
				b();
				$("#tip-shortcuts").show()
			} else {
				e()
			}
		}
	}
}());
imh.drag = function(b, j) {
	j = j || window.event;
	if (document.all && (j.button != 0 && j.button != 1)) {
		return false
	}
	var g = 2 * document.documentElement.scrollLeft, i = document.documentElement.scrollLeft
			- j.screenX, e = 2 * document.documentElement.scrollTop, h = document.documentElement.scrollTop
			- j.screenY;
	if (b.addEventListener) {
		b.addEventListener("mousemove", f, true);
		b.addEventListener("mouseup", a, true)
	} else {
		if (b.attachEvent) {
			b.setCapture();
			b.attachEvent("onmousemove", f);
			b.attachEvent("onmouseup", a);
			b.attachEvent("onlosecapture", a)
		} else {
			var c = b.onmousemove;
			var d = b.onmouseup;
			b.onmousemove = f;
			b.onmouseup = a
		}
	}
	j.stopPropagation ? j.stopPropagation() : j.cancelBubble = true;
	j.preventDefault ? j.preventDefault() : j.cancelBubble = true;
	b.style.cursor = "move";
	function f(k) {
		mX = k.screenX + i;
		mY = k.screenY + h;
		window.scrollTo(g - mX, e - mY);
		k.stopPropagation ? k.stopPropagation() : k.cancelBubble = true
	}
	function a(k) {
		b.style.cursor = "pointer";
		if (b.removeEventListener) {
			b.removeEventListener("mouseup", a, true);
			b.removeEventListener("mousemove", f, true)
		} else {
			if (b.detachEvent) {
				b.detachEvent("onlosecapture", a);
				b.detachEvent("onmouseup", a);
				b.detachEvent("onmousemove", f);
				b.releaseCapture()
			} else {
				b.onmouseup = d;
				b.onmousemove = c
			}
		}
		k.stopPropagation ? k.stopPropagation() : k.cancelBubble = true
	}
};
imh.suggest = function(a) {
	var q = {
		url : "/v2.2/user/search.ashx",
		key : "txtKey",
		result : "suggest",
		button : "btnSend",
		delay : 200
	};
	var g = "\u8bf7\u8f93\u5165\u6f2b\u753b\u6216\u4f5c\u8005\u540d\u79f0\u6216\u62fc\u97f3";
	$.extend(q, a || {});
	var m = -1;
	var n = "";
	var h = 0;
	var b;
	var e;
	var d = [];
	function l() {
		if (m >= 0) {
			window.location.href = "/comic/" + $("#ret" + m).attr("rel") + "/"
		} else {
			var r = $.trim($("#" + q.key).val());
			if (r == g || r == "") {
				$("#" + q.key).focus();
				window
						.alert("\u8bf7\u8f93\u5165\u8981\u4f60\u8981\u641c\u7d22\u7684\u6f2b\u753b");
				return false
			} else {
				window.location.href = "/v2/user/search.aspx?key=" + escape(r)
			}
		}
	}
	function k() {
		$("#" + q.result).show()
	}
	function f() {
		$("#" + q.result).hide()
	}
	function i(r) {
		if (!d[r]) {
			$.ajax({
				type : "POST",
				url : q.url,
				data : "s=1&key=" + escape(r),
				dataType : "json",
				success : function(s) {
					o(s, r)
				}
			})
		} else {
			o(d[r], r)
		}
	}
	function o(s, r) {
		m = -1;
		$("#" + q.result).html("<ul>" + j(s, r) + "</ul>");
		s.length > 0 ? k() : f();
		h = $("#" + q.result + " li").length;
		d[r] = s;
		if (d.length > 10) {
			d.pop()
		}
		$("#" + q.result + " li").mouseover(function() {
			$("#" + q.result + " li").removeClass("selected");
			$(this).addClass("selected")
		})
	}
	function j(w, t) {
		var r = [];
		for ( var u in w) {
			var v = w[u];
			r.push('<li id="ret' + u + '" rel="' + v.id + '" title="' + v.title
					+ '" onclick="location.href=\'/comic/' + v.id + "/'\">");
			r.push(v.status);
			r.push('<a href="/comic/'
					+ v.id
					+ '">'
					+ v.title.replace(new RegExp("(" + t + ")", "gi"),
							"<strong>$1</strong>")
					+ "</a> <i>["
					+ v.author.replace(new RegExp("(" + t + ")", "gi"),
							"<strong>$1</strong>") + "]</i>");
			r.push("</li>")
		}
		return r.join("")
	}
	$("#" + q.key).val(g).css("color", "#ccc");
	function c() {
		b && clearTimeout(b);
		b = window.setTimeout(function() {
			var r = $.trim($("#" + q.key).val());
			if (e != r) {
				e = r;
				if (r != "") {
					i(r)
				} else {
					m = -1;
					h = 0;
					f();
					$("#" + q.result).html("")
				}
			}
		}, q.delay)
	}
	function p() {
		if (m < -1) {
			m = h - 1
		} else {
			if (m >= h) {
				m = -1
			}
		}
		$("#" + q.result + " li").removeClass("selected");
		if (m == -1) {
			$("#" + q.key).val(e)
		} else {
			var r = $("#ret" + m);
			$(r).addClass("selected");
			$("#" + q.key).val($(r).attr("title"))
		}
	}
	return (function() {
		$("#" + q.key).val(g).focus(function() {
			if ($(this).val() == g) {
				$(this).val("").css("color", "#333")
			}
		}).blur(function() {
			if ($(this).val() == "") {
				$(this).val(g).css("color", "#ccc")
			}
		});
		$("#" + q.key)
				.keyup(
						function(s) {
							s.stopPropagation();
							if (s.keyCode == 13) {
								l()
							} else {
								var r = document.getElementById(q.result).style.display != "none";
								if (r) {
									switch (s.keyCode) {
									case 27:
										if (m != -1) {
											m = -1;
											p()
										} else {
											f()
										}
										break;
									case 38:
										m--;
										p();
										break;
									case 40:
										m++;
										p();
										break;
									default:
										c();
										break
									}
								} else {
									switch (s.keyCode) {
									case 38:
									case 40:
										if (h > 0) {
											k()
										}
										break;
									default:
										c();
										break
									}
								}
							}
						});
		$("#" + q.key).blur(function() {
			setTimeout(function() {
				f()
			}, 300)
		});
		$("#" + q.key).focus(function() {
			if (h > 0) {
				k()
			}
		});
		$("#" + q.button).click(function() {
			l()
		})
	})()
};
imh.history = (function() {
	var b = {
		maxLen : 20,
		len : 0,
		cname : "read",
		cvalue : [],
		got : false,
		init : false
	};
	function a(f) {
		var s = f * 1000;
		var p = new Date(s);
		var t = new Date();
		var r = Date.parse(t.toDateString());
		var n = t.getTime();
		var g = 86400000;
		var i = 3600000;
		var o = 0;
		var q = function(c) {
			return c < 10 ? "0" + c : c
		};
		var u = [];
		if (r > s) {
			o = Math.ceil((r - s) / g);
			switch (o) {
			case 1:
				u.push("\u6628\u5929 ");
				break;
			case 2:
				u.push("\u524d\u5929 ");
				break;
			default:
				u.push(q(p.getMonth() + 1), "\u6708", q(p.getDate()), "\u65e5");
				break
			}
			u.push(q(p.getHours()) + ":" + q(p.getMinutes()))
		} else {
			if (n - s < 60000) {
				o = Math.ceil((n - s) / 1000);
				u.push(o, "\u79d2\u524d")
			} else {
				o = Math.ceil((n - s) / i);
				if (o == 1) {
					u.push(Math.floor(((n - s) % i) / 60000),
							"\u5206\u949f\u524d")
				} else {
					u.push("\u4eca\u5929 ", q(p.getHours()), ":", q(p
							.getMinutes()))
				}
			}
		}
		return u.join("")
	}
	return {
		get : function() {
			if (b.got === false) {
				var c = $.cookie(b.cname);
				if (c != null && c != "") {
					b.cvalue = c.split("$");
					b.len = b.cvalue.length
				}
				b.got = true
			}
		},
		add : function(f) {
			this.get();
			if (b.len > 0) {
				var c = -1;
				for ( var e = 0; e < b.len; e++) {
					if (("|" + b.cvalue[e]).indexOf("|" + f.b + "|") == 0) {
						c = e;
						break
					}
				}
				if (c != -1) {
					b.cvalue.splice(c, 1)
				}
				if (b.len >= b.maxLen) {
					b.cvalue.splice(0, b.len - b.maxLen + 1)
				}
			}
			var d = parseInt((new Date()).getTime() / 1000);
			b.cvalue.push([ f.b, f.bi, f.c, f.ci, f.p, d ].join("|"));
			b.len = b.cvalue.length;
			$.cookie(b.cname, b.cvalue.join("$"), {
				expires : 365,
				path : "/"
			})
		},
		remove : function(c, d) {
			b.cvalue.splice(c, 1);
			b.len--;
			if (b.len <= 0) {
				$.cookie(b.cname, null, {
					expires : -365,
					path : "/"
				})
			} else {
				$.cookie(b.cname, b.cvalue.join("$"), {
					expires : 365,
					path : "/"
				})
			}
			$(d).parent().hide(200);
			if (b.len <= 0) {
				$("#hList")
						.html(
								'<div class="hNone">\u6682\u65f6\u6ca1\u6709\u89c2\u770b\u8bb0\u5f55!</div>');
				return
			}
			if (b.len <= 5) {
				$(".hList").removeClass("hListMax")
			}
		},
		clear : function() {
			$.cookie(b.cname, "", {
				expires : -365,
				path : "/"
			});
			b.len = 0;
			b.cvalue = [];
			$("#hList")
					.html(
							'<div class="hNone">\u6682\u65f6\u6ca1\u6709\u89c2\u770b\u8bb0\u5f55!</div>')
		},
		init : function() {
			var c = this;
			$("#history").hover(function() {
				$(this).addClass("more-hover");
				if (b.init == false) {
					c.format()
				}
			}, function() {
				$(this).removeClass("more-hover")
			})
		},
		format : function() {
			this.get();
			b.init = true;
			var e = [];
			if (b.len > 0) {
				for ( var d = b.len - 1; d >= 0; d--) {
					var g = b.cvalue[d].split("|");
					e
							.push('<li><a class="hDelete fr" title="\u5220\u9664" rel="'
									+ d + '"><span>\u5220\u9664</span></a>');
					e.push('<a class="book" href="/comic/' + g[1] + '/">'
							+ g[0] + '</a> <em>/</em> <a href="/comic/' + g[1]
							+ "/list_" + g[3] + '.html" class="red">' + g[2]
							+ "</a>");
					e
							.push('<div><span class="fr" style="line-height:1.6em;"><a href="/comic/'
									+ g[1]
									+ "/list_"
									+ g[3]
									+ ".html?p="
									+ g[4]
									+ '">\u7ee7\u7eed\u89c2\u770b</a></span><span class="hTime">\u4e0a\u6b21\u4e8e '
									+ a(g[5])
									+ " \u89c2\u770b</span></div></li>")
				}
			}
			var f = this;
			var c = b.len;
			if (c == 0) {
				$("#hList")
						.html(
								'<div class="hNone">\u6682\u65f6\u6ca1\u6709\u89c2\u770b\u8bb0\u5f55!</div>')
			} else {
				$("#hList").html(
						'<div class="hList"><ul>' + e.join("") + "</ul></div>");
				$(".hList li").hover(function() {
					$(this).addClass("hHover")
				}, function() {
					$(this).removeClass("hHover")
				});
				$("#hList .hDelete").click(function() {
					f.remove($(this).attr("rel"), this)
				});
				if (c > 6) {
					$(".hList").addClass("hListMax")
				}
			}
		}
	}
}());
imh.lighter = {
	init : function() {
		var b = this;
		var a = pVars.lighter.config[pVars.lighter.cvalue];
		$("#lighter").text(a[0]).attr("class", a[1]);
		$("#lighter").click(function() {
			b.change()
		})
	},
	change : function() {
		$.cookie(pVars.lighter.cname, (pVars.lighter.cvalue == 0) ? 1 : 0, {
			expires : 365,
			path : "/"
		});
		location.reload()
	}
};
(function() {
	$("#pagination").html(imh.pager({
		cp : pVars.page - 1,
		pc : cInfo.len
	}));
	$("#nav-sec,#history").hover(function() {
		$(this).addClass("more-hover")
	}, function() {
		$(this).removeClass("more-hover")
	});
	$("#prev").click(function() {
		imh.prev()
	});
	$("#next").click(function() {
		imh.next()
	});
	$(".prevC").click(function() {
		imh.prevC()
	});
	$(".nextC").click(function() {
		imh.nextC()
	});
	$("#viewList").attr("href", "/comic/" + cInfo.bid + "/");
	$("#support-help").attr("href", "/help/");
	if (!sys.mobile) {
		$("#mangaFile").mousedown(function(a) {
			imh.drag(this, a)
		}).dblclick(function() {
			imh.next()
		});
		imh.shortcuts.init();
		$(document).bind("keydown", {
			combi : "left",
			disableInInput : true
		}, function() {
			imh.prev()
		}).bind("keydown", {
			combi : "right",
			disableInInput : true
		}, function() {
			imh.next()
		}).bind("keydown", {
			combi : "z",
			disableInInput : true
		}, function() {
			imh.prevC()
		}).bind("keydown", {
			combi : "x",
			disableInInput : true
		}, function() {
			imh.nextC()
		}).bind("keydown", {
			combi : "c",
			disableInInput : true
		}, function() {
			imh.lighter.change()
		}).bind("keydown", {
			combi : "v",
			disableInInput : true
		}, function() {
			window.scrollTo(0, 0)
		})
	} else {
		$("#mangaFile").click(function() {
			imh.next()
		})
	}
	imh.lighter.init();
	imh.history.init();
	$("#support-error").click(
			function() {
				openWindow("/support/baocuo.aspx?r=" + escape(location.href)
						+ "&s=" + pVars.curServ, 500, 580)
			});
	$("#support-report").click(
			function() {
				openWindow("/support/jubao.aspx?r=" + escape(location.href)
						+ "&s=" + pVars.curServ, 500, 540)
			});
	imh.suggest({
		key : "txtKey",
		result : "sList",
		button : "btnSend"
	});
	setTimeout(function() {
		imh.history.add({
			b : cInfo.bname,
			bi : cInfo.bid,
			c : cInfo.cname,
			ci : cInfo.cid,
			p : pVars.page
		})
	}, 1000)
})();
function openWindow(b, a, c) {
	var e = (window.screen.availHeight - 30 - c) / 2;
	var d = (window.screen.availWidth - 10 - a) / 2;
	window
			.open(
					b,
					"",
					"height="
							+ c
							+ ",,innerHeight="
							+ c
							+ ",width="
							+ a
							+ ",innerWidth="
							+ a
							+ ",top="
							+ e
							+ ",left="
							+ d
							+ ",toolbar=no,menubar=no,scrollbars=no,resizeable=no,location=no,status=no");
	return false
}
var _ts = _ts || (function() {
	var c = new Date, d = [];
	d.push(c.getFullYear());
	d.push(c.getMonth() + 1);
	d.push(c.getDate());
	d.push(c.getHours());
	return d.join("")
})();
(function() {
	var b = this;
	var a = window.document;
	var e = function(g) {
		var f = g.src + "?v=" + _ts;
		switch (g.type) {
		case "iframe":
			return "<iframe scrolling='no' allowTransparency='true' frameborder='0' width='"
					+ g.width
					+ "' height='"
					+ g.height
					+ "' src='"
					+ f
					+ "'></iframe>";
		case "htm":
		case "html":
			return g.code;
		case "img":
			return "<a href='" + this.href + "' target='_blank'><img alt='"
					+ g.alt + "' src='" + f + "' border='0' width='" + g.width
					+ "' height='" + g.height + "' /></a>";
		default:
			return ""
		}
	};
	var d = function(h, f, g) {
		if (g != "") {
			if (f > 0) {
				setTimeout(function() {
					h.html(g)
				}, f)
			} else {
				h.html(g)
			}
		} else {
			h.hide()
		}
	};
	var c = function(k) {
		var r = $(".imh-vv");
		var q = [];
		var h = [];
		for ( var l = 0, o = r.length; l < o; l++) {
			var t = r.eq(l).attr("index") || 0;
			q.push(t);
			h.push(r.eq(l))
		}
		for ( var g in q) {
			var t = q[g];
			var s = h[t];
			var f = k[t];
			if (undefined === typeof (f) || f == null) {
				s.hide();
				continue
			}
			var j = f.delay || 0;
			var n = f.enable || false;
			if (n === false) {
				s.hide();
				continue
			}
			var m = e(f);
			d(s, j, m)
		}
	};
	b.adLoader = c
})();
if (pVars.page == 1) {
	$.get("/v2.2/user/count.ashx", {
		bookId : cInfo.bid,
		chapterId : cInfo.cid
	})
}
(function(a) {
	$("#share")
			.html(
					'<div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare"><span class="bds_more"></span><a class="bds_qzone"></a><a class="bds_tsina"></a><a class="bds_tqq"></a><a class="bds_renren"></a><a class="bds_kaixin001"></a></div>');
	a
			.write('<script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=12103" ><\/script>');
	a
			.write('<script type="text/javascript" id="bdshell_js" defer="defer" src="http://bdimg.share.baidu.com/static/js/shell_v2.js?t='
					+ new Date().getHours() + '"><\/script>');
	a.write('<script type="text/javascript" src="/v2.2/moneys/chapter.js?t='
			+ _ts + '"><\/script>')
})(document);
document.oncontextmenu = function() {
	return false
};
document.onmousedown = function(a) {
	a = a || window.event;
	if (a.button == 2) {
		return false
	}
};
window.onerror = function() {
	return true
};
