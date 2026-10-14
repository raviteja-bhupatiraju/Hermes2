/* Copyright (c) 2026 University of Louisiana at Lafayette, CAAI. HLOM Guide: page navigation, topics and search (ES5). */
(function () {
	'use strict';
	var pages = document.querySelectorAll('article.page');
	var navItems = document.querySelectorAll('.nav-item');
	var toc = document.getElementById('toc');
	var input = document.getElementById('q');
	var results = document.getElementById('results');
	var summary = document.getElementById('search-summary');
	var current = null;
	var index = null;
	var pinned = null;

	function each(list, fn) {
		for (var i = 0; i < list.length; i++) {
			fn(list[i], i);
		}
	}

	function closestPage(el) {
		while (el && !(el.tagName === 'ARTICLE' && /\bpage\b/.test(el.className))) {
			el = el.parentNode;
		}
		return el;
	}

	function showPage(page) {
		if (!page) {
			return;
		}
		current = page;
		each(pages, function (p) {
			p.className = p.className.replace(/\s*\bshown\b/g, '') + (p === page ? ' shown' : '');
		});
		var id = page.id.replace('page-', '');
		each(navItems, function (a) {
			a.className = a.className.replace(/\s*\bactive\b/g, '') + (a.getAttribute('data-page') === id ? ' active' : '');
		});
		var html = '';
		each(page.querySelectorAll('section.card[id]'), function (s) {
			html += '<a href="#' + s.id + '">' + escapeHtml(s.getAttribute('data-title')) + '</a>';
		});
		toc.innerHTML = html;
		document.title = 'HLOM Guide – ' + page.getAttribute('data-title');
	}

	function findTarget(id) {
		if (!id) {
			return null;
		}
		var el = document.getElementById(id);
		if (el) {
			return el;
		}
		var lower = id.toLowerCase();
		var all = document.querySelectorAll('[id]');
		for (var i = 0; i < all.length; i++) {
			if (all[i].id.toLowerCase() === lower) {
				return all[i];
			}
		}
		return null;
	}

	function flash(el) {
		el.className = el.className.replace(/\s*\bflash\b/g, '');
		void el.offsetWidth;
		el.className += ' flash';
	}

	/** Shows a topic: a page ("page-x"), a section or an element key such as "creditType". */
	function showTopic(id) {
		id = (id || '').replace(/^#/, '');
		if (id && id.indexOf('search:') === 0) {
			input.value = decodeURIComponent(id.substring(7));
			search();
			return true;
		}
		var target = findTarget(id) || document.getElementById('page-overview');
		var found = target.id.toLowerCase() === id.toLowerCase();
		var page = /\bpage\b/.test(target.className) && target.tagName === 'ARTICLE' ? target : closestPage(target);
		if (input.value && page.id !== 'page-search') {
			input.value = '';
		}
		showPage(page);
		if (target === page) {
			window.scrollTo(0, 0);
		} else {
			var scroll = function () {
				var top = target.getBoundingClientRect().top + (window.pageYOffset || document.documentElement.scrollTop);
				window.scrollTo(0, Math.max(0, top - 84));
			};
			scroll();
			setTimeout(scroll, 120);
			pinned = { scroll: scroll, until: new Date().getTime() + 4000 };
			flash(target);
		}
		if (!found && id && id !== 'page-overview') {
			input.value = id;
			search();
			return false;
		}
		return true;
	}

	function route() {
		showTopic(decodeURIComponent(location.hash || '#page-overview'));
	}

	function escapeHtml(s) {
		return String(s || '').replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
	}

	function textOf(el) {
		var parts = [];
		(function walk(node) {
			for (var c = node.firstChild; c; c = c.nextSibling) {
				if (c.nodeType === 3) {
					parts.push(c.nodeValue);
				} else if (c.nodeType === 1 && !/\bkicker\b/.test(c.className)) {
					parts.push(/^(CODE|B|I|EM|STRONG|A|SPAN|KBD|MARK)$/.test(c.tagName) ? '' : ' ');
					walk(c);
					parts.push(/^(CODE|B|I|EM|STRONG|A|SPAN|KBD|MARK)$/.test(c.tagName) ? '' : ' ');
				}
			}
		})(el);
		return parts.join('').replace(/\s+/g, ' ').replace(/^\s+|\s+$/g, '');
	}

	function buildIndex() {
		index = [];
		each(document.querySelectorAll('article.page:not(#page-search) [id]'), function (el) {
			var page = closestPage(el);
			if (!page || el === page) {
				return;
			}
			var title;
			if (el.tagName === 'TR') {
				title = textOf(el.cells[el.cells.length > 2 && /\bnum\b/.test(el.cells[0].className) ? 1 : 0]);
			} else {
				title = el.getAttribute('data-title') || textOf(el.querySelector('code') || el).substring(0, 60);
			}
			var text = textOf(el);
			if (text.indexOf(title) === 0) {
				text = text.substring(title.length).replace(/^\s+/, '');
			}
			index.push({ id: el.id, title: title, text: text, page: page.getAttribute('data-title'),
				section: el.tagName === 'SECTION' });
		});
	}

	function highlight(text, terms) {
		var html = escapeHtml(text);
		each(terms, function (t) {
			var re = new RegExp('(' + t.replace(/[.*+?^${}()|[\]\\]/g, '\\$&') + ')', 'ig');
			html = html.replace(re, '<mark>$1</mark>');
		});
		return html;
	}

	function snippet(text, term) {
		var i = text.toLowerCase().indexOf(term);
		var start = Math.max(0, i - 60);
		return (start > 0 ? '…' : '') + text.substring(start, start + 200) + (text.length > start + 200 ? '…' : '');
	}

	function search() {
		var q = input.value.replace(/^\s+|\s+$/g, '').toLowerCase();
		if (!q) {
			if (current && current.id === 'page-search') {
				route();
			}
			return;
		}
		if (!index) {
			buildIndex();
		}
		var terms = q.split(/\s+/);
		var hits = [];
		each(index, function (item) {
			var hay = (item.title + ' ' + item.text).toLowerCase();
			for (var i = 0; i < terms.length; i++) {
				if (hay.indexOf(terms[i]) < 0) {
					return;
				}
			}
			var title = item.title.toLowerCase();
			var score = (title === q ? 0 : title.indexOf(q) === 0 ? 1 : title.indexOf(q) >= 0 ? 2 : 3) + (item.section ? 0.5 : 0);
			hits.push({ item: item, score: score });
		});
		hits.sort(function (a, b) { return a.score - b.score; });
		var html = '';
		each(hits.slice(0, 80), function (h) {
			var it = h.item;
			html += '<a class="result" href="#' + it.id + '"><span class="where">' + escapeHtml(it.page) + '</span><b>'
				+ highlight(it.title, terms) + '</b><span class="snip">' + highlight(snippet(it.text, terms[0]), terms) + '</span></a>';
		});
		results.innerHTML = html || '<div class="empty">No topics match <b>' + escapeHtml(input.value) + '</b>.</div>';
		summary.innerHTML = hits.length + ' topic' + (hits.length === 1 ? '' : 's') + ' for “' + escapeHtml(input.value) + '”';
		showPage(document.getElementById('page-search'));
		window.scrollTo(0, 0);
	}

	input.oninput = search;
	input.onkeyup = function (ev) {
		if (ev.keyCode === 27) {
			input.value = '';
			search();
		} else if (ev.keyCode === 13) {
			var first = results.querySelector('a.result');
			if (first) {
				location.hash = first.getAttribute('href');
			}
		} else if (!('oninput' in input)) {
			search();
		}
	};
	document.onkeydown = function (ev) {
		ev = ev || window.event;
		var tag = (ev.target || ev.srcElement).tagName;
		if ((ev.key === '/' || ev.keyCode === 191) && tag !== 'INPUT') {
			input.focus();
			input.select();
			return false;
		}
	};

	each(document.querySelectorAll('input.filter'), function (f) {
		var table = document.getElementById(f.getAttribute('data-filter'));
		f.oninput = f.onkeyup = function () {
			var q = f.value.toLowerCase();
			each(table.tBodies[0].rows, function (r) {
				r.style.display = textOf(r).toLowerCase().indexOf(q) >= 0 ? '' : 'none';
			});
		};
	});

	if ('onhashchange' in window) {
		window.onhashchange = route;
	}
	document.onclick = function (ev) {
		var a = ev.target || ev.srcElement;
		while (a && a.tagName !== 'A') {
			a = a.parentNode;
		}
		if (a && a.getAttribute('href') && a.getAttribute('href').charAt(0) === '#' && a.getAttribute('href') === location.hash) {
			route();
		}
	};

	window.onresize = function () {
		if (pinned && new Date().getTime() < pinned.until) {
			pinned.scroll();
		}
	};
	window.hlomShowTopic = showTopic;
	window.hlomSearch = function (q) {
		input.value = q;
		search();
	};
	route();
})();
