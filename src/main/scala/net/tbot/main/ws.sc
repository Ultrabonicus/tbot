package net.tbot.main

import scala.xml.Elem
import scala.util.{Try,Failure,Success}
import scalaz._
import syntax.apply._, syntax.std.option._, syntax.validation._
import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
import java.util.NoSuchElementException
import scalaz.syntax.ToValidationOps

object ws {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
	
	val parser = scala.xml.XML.withSAXParser(new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl().newSAXParser())
                                                  //> parser  : scala.xml.factory.XMLLoader[scala.xml.Elem] = scala.xml.XML$$anon$
                                                  //| 1@d3d6f
	
	val html = parser.loadString("""<!doctype html>
<html lang="ru">
<head>
	
	
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<title>Дорогая принцесса Селестия... / Табун - место, где пасутся брони</title>
	
	<meta name="description" content="Дорогая принцесса Селестия...:В этом блоге вы можете написать принцессе Селестии письмо о том, какой важный урок вы извлекли для себя. Письмо о том, как вы пришли к пониманию чего-то важного. Написав об этом сюда, вы можете поделиться этим с другими, и они тоже могут извлечь урок, который извлекли вы. Делясь этим друг с другом, мы все можем стать чуточку лучше.
 
 ВАЖНО:
Ну а если вы хотите просто выплакаться, поделиться своими переживаниями с другими и получить поддержку, или же вам просто нужен совет, то для этого теперь создан отдельный блог —  «Жилетка» . Все посты, содержащие слёзы, но не содержащие в себе полезных уроков, будут переноситься туда. Убедительная просьба читателям выкладывать такие посты сразу туда, чтобы не создавать админам лишней работы. Пожалейте принцессу, она и так уже устала от ваших жалоб и вопросов о том, как быть.
 
 Письмецо.Дорогая принцесса, вам письмо..Дорогая принцесса Селестия, сегодня я впервые варил суп....Дорогая принцесса Селестия.Дорогие принцессы.Дорогая Принцесса, пишу вам отчет....Открытие этого года.Небольшое наблюдение.Не шибко веселый урок..&amp;quot;Веселье&amp;quot;.">
	<meta name="keywords" content="вроде отыгрыш,а для кого-то и вброс,ну бывает,письмо,селестия,не нытье,урок,переезд,я счастлива,Все еще кипятите?,новые впечатления,про кухню,бред воспаленного сознания,глупости и нелепости,я устал,хочу спать,но нельзя,работа,до вечера,мыться негде,чёртов снег,все разленились,Письмо,Принцесса Селестия,Прицесса Луна,доброта,Письмо для принцессы Селестии,дпс,взаимоотношения,IRL,ДПС,Авто,дпс,грустнота,или нет,ирл,дорогая принцесса селестия,грусть,радость,">

	<link rel='stylesheet' type='text/css' href='http://tabun.everypony.ru/templates/cache/synio/6ec25fb1dace5eff1c443b0156671a16.css' />


	<link href="http://tabun.everypony.ru/templates/skin/synio/images/favicon.ico?v1" rel="shortcut icon" />
	<link rel="search" type="application/opensearchdescription+xml" href="http://tabun.everypony.ru/search/opensearch/" title="Табун - место, где пасутся брони" />

			<link rel="alternate" type="application/rss+xml" href="http://tabun.everypony.ru/rss/blog/dearprincess/" title="Дорогая принцесса Селестия...">
	
			<link rel="canonical" href="http://tabun.everypony.ru/blog/dearprincess/" />
	
		<script type="text/javascript">
		var _gaq = _gaq || [];
		_gaq.push(['_setAccount', 'UA-22560999-4']);
		_gaq.push(['_trackPageview']);
		(function() {
			var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
			ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
			var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
		})();
	</script>
	<script type="text/javascript">
		function __42__getCookies() {
			
			if (typeof String.prototype.trimLeft !== "function") {
    				String.prototype.trimLeft = function() {
        				return this.replace(/^\s+/, "");
    				};
			}
			if (typeof String.prototype.trimRight !== "function") {
				String.prototype.trimRight = function() {
					return this.replace(/\s+$/, "");
    				};
			}
	
        		var c = document.cookie, v = 0, cookies = {};
		        if (document.cookie.match(/^\s*\$Version=(?:"1"|1);\s*(.*)/)) {
            			c = RegExp.$1;
            			v = 1;
		        }
        		if (v === 0) {
            			c.split(/[,;]/).map(function(cookie) {
                			var parts = cookie.split(/=/, 2),
                    			name = decodeURIComponent(parts[0].trimLeft()),
                    			value = parts.length > 1 ? decodeURIComponent(parts[1].trimRight()) : null;
                			cookies[name] = value;
            			});
        		} else {
            			c.match(/(?:^|\s+)([!#$%&'*+\-.0-9A-Z^`a-z|~]+)=([!#$%&'*+\-.0-9A-Z^`a-z|~]*|"(?:[\x20-\x7E\x80\xFF]|\\[\x00-\x7F])*")(?=\s*[,;]|$)/g).map(function($0, $1) {
                			var name = $0,
                    			value = $1.charAt(0) === '"'
                              		? $1.substr(1, -1).replace(/\\(.)/g, "$1")
                              		: $1;
                			cookies[name] = value;
            			});
        		}
        		return cookies;
    		}
		var DIR_WEB_ROOT 			= 'http://tabun.everypony.ru';
		var DIR_STATIC_SKIN 		= 'http://tabun.everypony.ru/templates/skin/synio';
		var DIR_ROOT_ENGINE_LIB 	= 'http://tabun.everypony.ru/engine/lib';
		var LIVESTREET_SECURITY_KEY = 'fdb7417827c5fd27617988c90328c4df';
		var SESSION_ID = __42__getCookies()['PHPSESSID'];
		console.log(SESSION_ID);
		var BLOG_USE_TINYMCE		= '';
		
		var TINYMCE_LANG = 'en';
					TINYMCE_LANG = 'ru';
		
		var aRouter = new Array();
					aRouter['error'] = 'http://tabun.everypony.ru/error/';
					aRouter['registration'] = 'http://tabun.everypony.ru/registration/';
					aRouter['profile'] = 'http://tabun.everypony.ru/profile/';
					aRouter['my'] = 'http://tabun.everypony.ru/my/';
					aRouter['blog'] = 'http://tabun.everypony.ru/blog/';
					aRouter['personal_blog'] = 'http://tabun.everypony.ru/personal_blog/';
					aRouter['index'] = 'http://tabun.everypony.ru/index/';
					aRouter['topic'] = 'http://tabun.everypony.ru/topic/';
					aRouter['login'] = 'http://tabun.everypony.ru/login/';
					aRouter['people'] = 'http://tabun.everypony.ru/people/';
					aRouter['settings'] = 'http://tabun.everypony.ru/settings/';
					aRouter['tag'] = 'http://tabun.everypony.ru/tag/';
					aRouter['talk'] = 'http://tabun.everypony.ru/talk/';
					aRouter['comments'] = 'http://tabun.everypony.ru/comments/';
					aRouter['rss'] = 'http://tabun.everypony.ru/rss/';
					aRouter['question'] = 'http://tabun.everypony.ru/question/';
					aRouter['blogs'] = 'http://tabun.everypony.ru/blogs/';
					aRouter['search'] = 'http://tabun.everypony.ru/search/';
					aRouter['admin'] = 'http://tabun.everypony.ru/admin/';
					aRouter['ajax'] = 'http://tabun.everypony.ru/ajax/';
					aRouter['feed'] = 'http://tabun.everypony.ru/feed/';
					aRouter['stream'] = 'http://tabun.everypony.ru/stream/';
					aRouter['subscribe'] = 'http://tabun.everypony.ru/subscribe/';
					aRouter['language'] = 'http://tabun.everypony.ru/language/';
					aRouter['file'] = 'http://tabun.everypony.ru/file/';
					aRouter['page'] = 'http://tabun.everypony.ru/page/';
					aRouter['role'] = 'http://tabun.everypony.ru/role/';
					aRouter['role_ajax'] = 'http://tabun.everypony.ru/role_ajax/';
					aRouter['role_people'] = 'http://tabun.everypony.ru/role_people/';
					aRouter['sitemap'] = 'http://tabun.everypony.ru/sitemap/';
					aRouter['tags'] = 'http://tabun.everypony.ru/tags/';
					aRouter['talkbell'] = 'http://tabun.everypony.ru/talkbell/';
			</script>
	
	
	<script type='text/javascript' src='http://tabun.everypony.ru/templates/cache/synio/c3d208d693cac8c6b0e155dca89ed82b.js'></script>
<!--[if lt IE 9]><script type='text/javascript' src='http://tabun.everypony.ru/engine/lib/external/html5shiv.js'></script><![endif]-->
<script type='text/javascript' src='http://yandex.st/share/share.js'></script>


	
	<script type="text/javascript">
		var tinyMCE = false;
		ls.lang.load({"blog_join":"\u041f\u043e\u0434\u043f\u0438\u0441\u0430\u0442\u044c\u0441\u044f \u043d\u0430 \u0431\u043b\u043e\u0433","blog_leave":"\u041e\u0442\u043f\u0438\u0441\u0430\u0442\u044c\u0441\u044f \u043e\u0442 \u0431\u043b\u043e\u0433\u0430"});
		ls.registry.set('comment_max_tree',60);
		ls.registry.set('block_stream_show_tip',false);
	</script>
	
	<script type="text/javascript">
	var LS_ROUTER_ACTION = 'blog';
	var LS_ROUTER_EVENT = 'dearprincess';
</script>

</head>



		
	
	






<body class=" ls-user-role-user ls-user-role-not-admin ls-template-synio width-fixed">
	
	
	
			<div class="modal modal-write" id="modal_write">
	<header class="modal-header">
		<a href="#" class="close jqmClose"></a>
	</header>
	
	<div class="modal-content"><ul class="write-list"><li class="write-item-type-topic"><a href="http://tabun.everypony.ru/topic/add" class="write-item-image"></a><a href="http://tabun.everypony.ru/topic/add" class="write-item-link">Пост</a></li><li class="write-item-type-blog"><a href="http://tabun.everypony.ru/blog/add" class="write-item-image"></a><a href="http://tabun.everypony.ru/blog/add" class="write-item-link">Блог</a></li><li class="write-item-type-message"><a href="http://tabun.everypony.ru/talk/add" class="write-item-image"></a><a href="http://tabun.everypony.ru/talk/add" class="write-item-link">Сообщение</a></li><li class="write-item-type-file">
  <a class="write-item-image" href="http://tabun.everypony.ru/file/add/"></a>
  <a class="write-item-link" href="http://tabun.everypony.ru/file/add/">Файл</a>
</li></ul></div>
</div>
	
			<div id="favourite-form-tags" class="modal">
		<header class="modal-header">
			<h3>Добавить свои теги</h3>
			<a href="#" class="close jqmClose"></a>
		</header>
		
		
		<form onsubmit="return ls.favourite.saveTags(this);" class="modal-content">
			<input type="hidden" name="target_type" id="favourite-form-tags-target-type">
			<input type="hidden" name="target_id" id="favourite-form-tags-target-id">

			<p><input type="text" name="tags" value="" id="favourite-form-tags-tags" class="autocomplete-tags-sep input-text input-width-full"></p>
			<button type="submit" class="button button-primary">Сохранить</button>
			<button type="submit" class="button jqmClose">Отмена</button>
		</form>
	</div>

		
	<div id="widemode">
<!--		<span id="tabun-fixes-chronology">
			<a href="javascript:void(0)" id="tabun-fixes-chronology-plus">-1</a>
			<div id="tabun-fixes-chronology-timeline"><div id="tabun-fixes-chronology-slider"></div></div>
			<a href="javascript:void(0)" id="tabun-fixes-chronology-minus">+1</a>
		</span>
-->
		<a id="despoil">Despoil</a>
		<a id="widemode-switch">Wide mode &harr;</a>
		<a id="up-switch"></a>
		<a id="down-switch"></a>
	</div>
	
	<div id="container" class="">
		<div id="c-header">
	
	<ul class="main-menu">
		<li id="logolink"><a href="http://tabun.everypony.ru">Да, это — Табун!</a></li>
		
		<li><a href="http://everypony.ru">Блог</a></li>
		<li><a href="http://forum.everypony.ru">Форум</a></li>
		<li><a href="http://stories.everypony.ru">Рассказы</a></li>
		<li><a href="http://wiki.everypony.ru">Вики</a></li>
		<li><a href="http://minecraft.everypony.ru">Майнкрафт</a></li>
	</ul>
	
	<a class="rss" href="http://tabun.everypony.ru/rss/" title="RSS поток Табуна"></a>
	<a class="twitter" href="https://twitter.com/#!/everypony_ru" title="Твиттер Everypony.ru"></a>
</div>
<header id="header" role="banner">
		
	
	<ul class="nav nav-main" id="nav-main">
			<li><a href="http://tabun.everypony.ru/topic/add/" class="button-write js-write-window-show" id="modal_write_show">Создать</a></li>
			<li class="active"><a href="http://tabun.everypony.ru">Посты</a></li>
		<li ><a href="http://tabun.everypony.ru/blogs/">Блоги</a></li>
		<li ><a href="http://tabun.everypony.ru/people/">Брони</a></li>
		<li ><a href="http://tabun.everypony.ru/stream/">Активность</a></li>

			<li ><a href="http://tabun.everypony.ru/page/faq/" >FAQ (новичкам)</a><i></i></li>
	
	</ul>

				
		
		


	
	
	
			<div class="dropdown-user" id="dropdown-user">
			<a href="http://tabun.everypony.ru/profile/Hibonicus/"><img src="http://tabun.everypony.ru/uploads/images/00/13/56/2014/12/30/avatar_100x100.png?151911" alt="avatar" class="avatar" /></a>
			<a href="http://tabun.everypony.ru/profile/Hibonicus/" class="username">Hibonicus</a>
			
			<ul class="dropdown-user-menu" id="dropdown-user-menu">
				
				<li class="item-messages">
					<a href="http://tabun.everypony.ru/talk/" id="new_messages">
						Сообщения
					</a>
								</li>
				<li class="item-favourite"><a href="http://tabun.everypony.ru/profile/Hibonicus/favourites/topics/">Избранное</a></li>
				<li class="item-settings"><a href="http://tabun.everypony.ru/settings/profile/">Настройки</a></li>
				
				<li class="item-signout"><a href="http://tabun.everypony.ru/login/exit/?security_ls_key=fdb7417827c5fd27617988c90328c4df">Выход</a></li>
			</ul>
			<ul class="dropdown-user-menu">
				<li class="item-stat">
					<span class="strength" title="Силушка"><i class="icon-synio-star-green"></i> 12043.91</span>
				</li>
				<li>
				<span class="rating " title="Кармочка"><i class="icon-synio-rating"></i> 1428.81</span>
					
				</li>
			</ul>
		</div>
		
	
	
	<nav id="nav">
	</nav>
</header>

		

		<div id="wrapper" class="">
							<aside id="sidebar" >
<section class="running-ponies">
	<div class="block-content" id="running_ponies">
        	<img src="http://files.everypony.ru/tabun/ponies/?1"/>
        	<img src="http://files.everypony.ru/tabun/ponies/?2"/>
        	<img src="http://files.everypony.ru/tabun/ponies/?3"/>
        	<img src="http://files.everypony.ru/tabun/ponies/?4"/>
        	<img src="http://files.everypony.ru/tabun/ponies/?5"/>
	</div>
</section>
	

						<section class="block block-type-pseudomenu">
	<ul>
		<li><a href="http://smile-o-pack.net/">Смайлопак</a></li>
		<li><a href="http://tabun.everypony.ru/page/draw/">Рисование</a></li>
		<li><a href="http://tabun.everypony.ru/tags/">Теги</a></li>
	</ul>
</section>

<section class="block block-type-search">
	<div class="search-header">
		<form class="search-header-form" id="search-header-form" action="http://tabun.everypony.ru/search/topics/">
			<input type="text" placeholder="Поиск" maxlength="255" name="q" class="input-text">
			<input type="submit" value="" title="Найти" class="input-submit">
		</form>
	</div>
</section>

<section class="block block-type-stream">
	

	<header class="block-header sep">
		<h3><a href="http://tabun.everypony.ru/comments/" title="Весь эфир">Прямой эфир</a></h3>
		
		<ul class="nav nav-pills js-block-stream-nav" >
			<li class="active js-block-stream-item" data-type="comment"><a href="#">Комментарии</a></li>
			<li class="js-block-stream-item" data-type="topic"><a href="#">Публикации</a></li>
			<div class="block-update js-block-stream-update"></div>
			
		</ul>
		
		<ul class="nav nav-pills js-block-stream-dropdown" style="display: none;">
			<li class="dropdown active js-block-stream-dropdown-trigger"><a href="#">Комментарии</a> <i class="icon-synio-arrows"></i>
				<ul class="dropdown-menu js-block-stream-dropdown-items">
					<li class="active js-block-stream-item" data-type="comment"><a href="#">Комментарии</a></li>
					<li class="js-block-stream-item" data-type="topic"><a href="#">Публикации</a></li>
					
				</ul>
			</li>
		</ul>
	</header>
	
	<div class="block-content">
		<div class="js-block-stream-content">
			<ul class="latest-list">
									
		<li class="js-title-comment">
			<p>
				<a href="http://tabun.everypony.ru/profile/Akell/" class="author">Akell</a> в <a href="http://tabun.everypony.ru/profile/Akell/created/topics/" class="stream-blog">Блог им. Akell</a>
			</p>
			<a href="http://tabun.everypony.ru/comments/8414339" class="stream-topic">Помолюсь до тебе пiснею</a>
			<span class="block-item-comments"><i class="icon-synio-comments-small"></i>1</span>
		</li>
									
		<li class="js-title-comment">
			<p>
				<a href="http://tabun.everypony.ru/profile/ArtemMoskvin/" class="author">ArtemMoskvin</a> в <a href="http://tabun.everypony.ru/blog/fanart/" class="stream-blog">Я рисую обоими копытами</a>
			</p>
			<a href="http://tabun.everypony.ru/comments/8414337" class="stream-topic">Тихий городок</a>
			<span class="block-item-comments"><i class="icon-synio-comments-small"></i>5</span>
		</li>
									
		<li class="js-title-comment">
			<p>
				<a href="http://tabun.everypony.ru/profile/Sally_Doo/" class="author">Sally_Doo</a> в <a href="http://tabun.everypony.ru/blog/comicsworkshop/" class="stream-blog">Цех комиксов</a>
			</p>
			<a href="http://tabun.everypony.ru/comments/8414336" class="stream-topic">АльтерБРЕДации №534: Оправданные затраты</a>
			<span class="block-item-comments"><i class="icon-synio-comments-small"></i>575</span>
		</li>
									
		<li class="js-title-comment">
			<p>
				<a href="http://tabun.everypony.ru/profile/Motorbreath/" class="author">Motorbreath</a> в <a href="http://tabun.everypony.ru/blog/uniblog/" class="stream-blog">Общеличный блог</a>
			</p>
			<a href="http://tabun.everypony.ru/comments/8414335" class="stream-topic">Сториес. Помним, Любим, Скорбим.</a>
			<span class="block-item-comments"><i class="icon-synio-comments-small"></i>323</span>
		</li>
									
		<li class="js-title-comment">
			<p>
				<a href="http://tabun.everypony.ru/profile/lunarinitiate/" class="author">lunarinitiate</a> в <a href="http://tabun.everypony.ru/blog/dearprincess/" class="stream-blog">Дорогая принцесса Селестия...</a>
			</p>
			<a href="http://tabun.everypony.ru/comments/8414333" class="stream-topic">Письмецо</a>
			<span class="block-item-comments"><i class="icon-synio-comments-small"></i>137</span>
		</li>
									
		<li class="js-title-comment">
			<p>
				<a href="http://tabun.everypony.ru/profile/Batut/" class="author">Batut</a> в <a href="http://tabun.everypony.ru/blog/lighthouse/" class="stream-blog">Полночь После Маяка</a>
			</p>
			<a href="http://tabun.everypony.ru/comments/8414329" class="stream-topic">Чат #164 имени лютой дрочки на Мику Хатцуне</a>
			<span class="block-item-comments"><i class="icon-synio-comments-small"></i>793</span>
		</li>
									
		<li class="js-title-comment">
			<p>
				<a href="http://tabun.everypony.ru/profile/Vadiman98/" class="author">Vadiman98</a> в <a href="http://tabun.everypony.ru/blog/science/" class="stream-blog">Эквестрийский аналитический отдел</a>
			</p>
			<a href="http://tabun.everypony.ru/comments/8414326" class="stream-topic">Чейнджлинги должны были вымереть</a>
			<span class="block-item-comments"><i class="icon-synio-comments-small"></i>169</span>
		</li>
									
		<li class="js-title-comment">
			<p>
				<a href="http://tabun.everypony.ru/profile/kito/" class="author">kito</a> в <a href="http://tabun.everypony.ru/blog/irl-connection/" class="stream-blog">IRL Connection</a>
			</p>
			<a href="http://tabun.everypony.ru/comments/8414317" class="stream-topic">Новый 2015 Год в Питере с питерсокой тусовкой пони-писателей-переводчиков-поэтов</a>
			<span class="block-item-comments"><i class="icon-synio-comments-small"></i>47</span>
		</li>
									
		<li class="js-title-comment">
			<p>
				<a href="http://tabun.everypony.ru/profile/Dimone/" class="author">Dimone</a> в <a href="http://tabun.everypony.ru/blog/draw_blog/" class="stream-blog">Рисовальный Блог</a>
			</p>
			<a href="http://tabun.everypony.ru/comments/8414311" class="stream-topic">Несколько карандашных рисунков.</a>
			<span class="block-item-comments"><i class="icon-synio-comments-small"></i>6</span>
		</li>
									
		<li class="js-title-comment">
			<p>
				<a href="http://tabun.everypony.ru/profile/f13proxima/" class="author">f13proxima</a> в <a href="http://tabun.everypony.ru/blog/BRM-KBECT/" class="stream-blog">Блог Арт-Батлов</a>
			</p>
			<a href="http://tabun.everypony.ru/comments/8414301" class="stream-topic">Голосование за Арт-Баттл 2015.01.10</a>
			<span class="block-item-comments"><i class="icon-synio-comments-small"></i>114</span>
		</li>
									
		<li class="js-title-comment">
			<p>
				<a href="http://tabun.everypony.ru/profile/lasgalen/" class="author">lasgalen</a> в <a href="http://tabun.everypony.ru/blog/rpwp/" class="stream-blog">Пони-экспромт</a>
			</p>
			<a href="http://tabun.everypony.ru/comments/8414293" class="stream-topic">RPWP 96 закрыт! 3+1 работы</a>
			<span class="block-item-comments"><i class="icon-synio-comments-small"></i>59</span>
		</li>
									
		<li class="js-title-comment">
			<p>
				<a href="http://tabun.everypony.ru/profile/Moonyasha/" class="author">Moonyasha</a> в <a href="http://tabun.everypony.ru/blog/fanart/" class="stream-blog">Я рисую обоими копытами</a>
			</p>
			<a href="http://tabun.everypony.ru/comments/8414260" class="stream-topic">Морской пони, дубль два.</a>
			<span class="block-item-comments"><i class="icon-synio-comments-small"></i>31</span>
		</li>
									
		<li class="js-title-comment">
			<p>
				<a href="http://tabun.everypony.ru/profile/inglorius/" class="author">inglorius</a> в <a href="http://tabun.everypony.ru/blog/fanart/" class="stream-blog">Я рисую обоими копытами</a>
			</p>
			<a href="http://tabun.everypony.ru/comments/8414250" class="stream-topic">Грозная пони гонится за своим завтраком</a>
			<span class="block-item-comments"><i class="icon-synio-comments-small"></i>4</span>
		</li>
									
		<li class="js-title-comment">
			<p>
				<a href="http://tabun.everypony.ru/profile/EnergyTone/" class="author">EnergyTone</a> в <a href="http://tabun.everypony.ru/blog/top-ten/" class="stream-blog">Топ брони видео и музыки ♫</a>
			</p>
			<a href="http://tabun.everypony.ru/comments/8414246" class="stream-topic">Top 10 Pony Songs of December 2014</a>
			<span class="block-item-comments"><i class="icon-synio-comments-small"></i>2</span>
		</li>
									
		<li class="js-title-comment">
			<p>
				<a href="http://tabun.everypony.ru/profile/Moonyasha/" class="author">Moonyasha</a> в <a href="http://tabun.everypony.ru/profile/Overdose/created/topics/" class="stream-blog">Блог им. Overdose</a>
			</p>
			<a href="http://tabun.everypony.ru/comments/8414219" class="stream-topic">Нарисованных achievement пост.</a>
			<span class="block-item-comments"><i class="icon-synio-comments-small"></i>7</span>
		</li>
									
		<li class="js-title-comment">
			<p>
				<a href="http://tabun.everypony.ru/profile/AlexandrVirus/" class="author">AlexandrVirus</a> в <a href="http://tabun.everypony.ru/blog/fanart/" class="stream-blog">Я рисую обоими копытами</a>
			</p>
			<a href="http://tabun.everypony.ru/comments/8414215" class="stream-topic">Реквест для одной особы из ВК.</a>
			<span class="block-item-comments"><i class="icon-synio-comments-small"></i>13</span>
		</li>
									
		<li class="js-title-comment">
			<p>
				<a href="http://tabun.everypony.ru/profile/Tails_Doll/" class="author">Tails_Doll</a> в <a href="http://tabun.everypony.ru/blog/draw_help/" class="stream-blog">Блог помощи художникам [БПНХ]</a>
			</p>
			<a href="http://tabun.everypony.ru/comments/8414193" class="stream-topic">Помогите :с</a>
			<span class="block-item-comments"><i class="icon-synio-comments-small"></i>10</span>
		</li>
									
		<li class="js-title-comment">
			<p>
				<a href="http://tabun.everypony.ru/profile/AlexandrVirus/" class="author">AlexandrVirus</a> в <a href="http://tabun.everypony.ru/blog/fanart/" class="stream-blog">Я рисую обоими копытами</a>
			</p>
			<a href="http://tabun.everypony.ru/comments/8414182" class="stream-topic">Сегодня Эквестрия будет гореть</a>
			<span class="block-item-comments"><i class="icon-synio-comments-small"></i>20</span>
		</li>
									
		<li class="js-title-comment">
			<p>
				<a href="http://tabun.everypony.ru/profile/wizallion/" class="author">wizallion</a> в <a href="http://tabun.everypony.ru/blog/toymerchandise/" class="stream-blog">Коллекционеры игрушек</a>
			</p>
			<a href="http://tabun.everypony.ru/comments/8414180" class="stream-topic">Фабрика с Фронтом в Америке</a>
			<span class="block-item-comments"><i class="icon-synio-comments-small"></i>10</span>
		</li>
									
		<li class="js-title-comment">
			<p>
				<a href="http://tabun.everypony.ru/profile/Jektastifix/" class="author">Jektastifix</a> в <a href="http://tabun.everypony.ru/blog/comicsworkshop/" class="stream-blog">Цех комиксов</a>
			</p>
			<a href="http://tabun.everypony.ru/comments/8414065" class="stream-topic">АльтерБРЕДации №532: Уверенность в успехе</a>
			<span class="block-item-comments"><i class="icon-synio-comments-small"></i>299</span>
		</li>
	</ul>


<footer>
	<a href="http://tabun.everypony.ru/comments/">Весь эфир</a> · <a href="http://tabun.everypony.ru/rss/allcomments/">RSS</a>
</footer>
		</div>
	</div>
</section>

<section class="block block-type-tags">
        <header class="block-header sep"><h3>Разум Табуна</h3></header>
	<div class="quote"><strong>xxx:</strong> Может сегодня порвать шаблон родителям и добровольно поехать на дачу? Из плюсов: охота за редкой лилией и транспортировка её на дачный участок для последующего размножения; отсутствие интернета, можно подучить названия деревьев, которые уже должны от зубов отскакивать.
Из минусов: дача <img src="http://img252.imageshack.us/img252/8259/twi9.png" width="35"></div>
</section>

<div class="napony">
	<a target="_blank" title="На шерстяные носочки для принцессы Луны." href="http://everypony.ru/ehelp/">
		<img title="На шерстяные носочки для принцессы Луны." alt="Донат" src="http://everypony.ru/wp-content/themes/newpony/i/donate.png" style="opacity: 1;">
	</a>
</div>

										

										<div class="block block-type-blogs" id="block_blogs">
	<header class="block-header sep">
		<h3>Блоги</h3>
		
		<ul class="nav nav-pills js-block-blogs-nav">
			<li class="active js-block-blogs-item" data-type="top"><a href="#">Топ</a></li>
							<li class="js-block-blogs-item" data-type="join"><a href="#">Подключенные</a></li>
				<li class="js-block-blogs-item" data-type="self"><a href="#">Мои</a></li>
		<div class="block-update js-block-blogs-update"></div>
					</ul>
	</header>
	
	
	<div class="block-content">
		
		
		<div class="js-block-blogs-content">
			<ul class="block-blog-list">
			<li>
			<a href="http://tabun.everypony.ru/blog/fanart/">Я рисую обоими копытами</a>
			<strong>7316.16</strong>
		</li>
			<li>
			<a href="http://tabun.everypony.ru/blog/press-center/">Пони-пресса</a>
			<strong>4189.23</strong>
		</li>
			<li>
			<a href="http://tabun.everypony.ru/blog/translations/">Гильдия переводчиков</a>
			<strong>4011.54</strong>
		</li>
			<li>
			<a href="http://tabun.everypony.ru/blog/herp_derp/">HERP DERP</a>
			<strong>3639.17</strong>
		</li>
			<li>
			<a href="http://tabun.everypony.ru/blog/draw_help/">Блог помощи художникам [БПНХ]</a>
			<strong>3478.49</strong>
		</li>
			<li>
			<a href="http://tabun.everypony.ru/blog/stories/">Пони-писатели</a>
			<strong>3257.53</strong>
		</li>
			<li>
			<a href="http://tabun.everypony.ru/blog/science/">Эквестрийский аналитический отдел</a>
			<strong>3213.14</strong>
		</li>
			<li>
			<a href="http://tabun.everypony.ru/blog/Public_Radio_of_the_herd/">ОРТ</a>
			<strong>3185.11</strong>
		</li>
			<li>
			<a href="http://tabun.everypony.ru/blog/lighthouse/">Полночь После Маяка</a><i title="Закрытый блог" class="icon-synio-topic-private"></i>
			<strong>3071.01</strong>
		</li>
			<li>
			<a href="http://tabun.everypony.ru/blog/night-ponyville/">Понивиль После Полуночи</a><i title="Закрытый блог" class="icon-synio-topic-private"></i>
			<strong>2874.42</strong>
		</li>
			<li>
			<a href="http://tabun.everypony.ru/blog/comicsworkshop/">Цех комиксов</a>
			<strong>2842.87</strong>
		</li>
			<li>
			<a href="http://tabun.everypony.ru/blog/Order_of_Luna/">Орден Луны</a>
			<strong>2836.35</strong>
		</li>
			<li>
			<a href="http://tabun.everypony.ru/blog/crafting/">Крафтеры и рукоделы</a>
			<strong>2761.25</strong>
		</li>
			<li>
			<a href="http://tabun.everypony.ru/blog/irl-connection/">IRL Connection</a>
			<strong>2656.92</strong>
		</li>
			<li>
			<a href="http://tabun.everypony.ru/blog/congratulations/">Блог поздравляшек</a>
			<strong>2601.22</strong>
		</li>
			<li>
			<a href="http://tabun.everypony.ru/blog/rule63/">Лаборатория №63</a>
			<strong>2584.69</strong>
		</li>
			<li>
			<a href="http://tabun.everypony.ru/blog/borderline/">На грани</a><i title="Закрытый блог" class="icon-synio-topic-private"></i>
			<strong>2580.92</strong>
		</li>
			<li>
			<a href="http://tabun.everypony.ru/blog/news/">Срочно в номер</a>
			<strong>2569.53</strong>
		</li>
			<li>
			<a href="http://tabun.everypony.ru/blog/foe/">Эквестрийская Пустошь</a>
			<strong>2431.47</strong>
		</li>
			<li>
			<a href="http://tabun.everypony.ru/blog/translate-comics/">Гильдия переводчиков - Переводы комиксов</a>
			<strong>2419.24</strong>
		</li>
	</ul>
		</div>

		
		<footer>
			<a href="http://tabun.everypony.ru/blogs/">Все блоги</a>
		</footer>
	</div>
</div>

					
</aside>

					
			<div id="content-wrapper" >
				<div id="content" role="main" >
					
						

	
					
					



<script type="text/javascript">
	jQuery(function($){
		ls.lang.load({"blog_fold_info":"\u0421\u0432\u0435\u0440\u043d\u0443\u0442\u044c","blog_expand_info":"\u041e \u0431\u043b\u043e\u0433\u0435"});
	});
</script>




<div class="blog-top">
	<h2 class="page-header">Дорогая принцесса Селестия... </h2>

	<div id="vote_area_blog_2932" class="vote-topic
																															vote-count-positive
																														
																															not-voted
																														
															">
		<a href="#" class="vote-item vote-up" onclick="return ls.vote.vote(2932,this,1,'blog');"><span><i></i></span></a>
		<div class="vote-item vote-count" title="голосов: 212"><span id="vote_total_blog_2932">+1697.04</span></div>
		<a href="#" class="vote-item vote-down" onclick="return ls.vote.vote(2932,this,-1,'blog');"><span><i></i></span></a>
	</div>
</div>

<div class="blog-mini" id="blog-mini">
					<button type="submit"  class="button button-small" id="button-blog-join-first-2932" data-button-additional="button-blog-join-second-2932" data-only-text="1" onclick="ls.blog.toggleJoin(this, 2932); return false;">Подписаться на блог</button>
			<span id="blog_user_count_2932">674</span> читателя,
	291 пост
	<div class="fl-r" id="blog-mini-header">
		<a href="#" class="link-dotted" onclick="ls.blog.toggleInfo(); return false;">О блоге</a>
		<a href="http://tabun.everypony.ru/rss/blog/dearprincess/">RSS</a>
	</div>
</div>



<div class="blog" id="blog" style="display: none">
	<div class="blog-inner">
		<header class="blog-header">
			<img src="http://tabun.everypony.ru/uploads/images/00/03/80/2012/03/13/avatar_blog_dearprincess_48x48.jpg" alt="avatar" class="avatar" />
			<span class="close" onclick="ls.blog.toggleInfo(); return false;"><a href="#" class="link-dotted">Свернуть</a><i class="icon-synio-close"></i></span>
		</header>

		
		<div class="blog-content text">
			<div class="blog-description">В этом блоге вы можете написать принцессе Селестии письмо о том, какой важный урок вы извлекли для себя. Письмо о том, как вы пришли к пониманию чего-то важного. Написав об этом сюда, вы можете поделиться этим с другими, и они тоже могут извлечь урок, который извлекли вы. Делясь этим друг с другом, мы все можем стать чуточку лучше.<br/>
<br/>
<strong>ВАЖНО:</strong><br/>
Ну а если вы хотите просто выплакаться, поделиться своими переживаниями с другими и получить поддержку, или же вам просто нужен совет, то для этого теперь создан отдельный блог — <a href="http://tabun.everypony.ru/blog/tearsfromthemoon/" rel="nofollow">«Жилетка»</a>. Все посты, содержащие слёзы, но не содержащие в себе полезных уроков, будут переноситься туда. Убедительная просьба читателям выкладывать такие посты сразу туда, чтобы не создавать админам лишней работы. Пожалейте принцессу, она и так уже устала от ваших жалоб и вопросов о том, как быть.<br/>
<br/>
<img src="http://fc05.deviantart.net/fs71/f/2011/215/3/e/dear_princess_celestia_by_mikuen_drops-d444wgw.jpg"/></div>
		
			
			<ul class="blog-info">
				<li><span>Создан</span> <strong>13 марта 2012</strong></li>
				<li><span>Постов</span> <strong>291</strong></li>
				<li><span><a href="http://tabun.everypony.ru/blog/dearprincess/users/">Подписчиков</a></span> <strong>674</strong></li>
				<li class="rating"><span>Рейтинг</span> <strong>1697.04</strong></li>
			</ul>
			
			
			
			<strong>Администраторы (6)</strong><br />
			<span class="user-avatar">
				<a href="http://tabun.everypony.ru/profile/drweb/"><img src="http://tabun.everypony.ru/uploads/images/00/03/80/2013/02/09/avatar_24x24.png?183813" alt="avatar" /></a>
				<a href="http://tabun.everypony.ru/profile/drweb/">drweb</a>
			</span>
						
									  
					<span class="user-avatar">
						<a href="http://tabun.everypony.ru/profile/Hanko/"><img src="http://tabun.everypony.ru/templates/skin/synio/images/avatar_male_24x24.png" alt="avatar" /></a>
						<a href="http://tabun.everypony.ru/profile/Hanko/">Hanko</a>
					</span>
									  
					<span class="user-avatar">
						<a href="http://tabun.everypony.ru/profile/univertaz/"><img src="http://tabun.everypony.ru/uploads/images/00/14/89/2013/08/07/avatar_24x24.jpg?173735" alt="avatar" /></a>
						<a href="http://tabun.everypony.ru/profile/univertaz/">univertaz</a>
					</span>
									  
					<span class="user-avatar">
						<a href="http://tabun.everypony.ru/profile/PrinceMars/"><img src="http://tabun.everypony.ru/uploads/images/00/20/49/2012/02/19/avatar_24x24.jpg?032549" alt="avatar" /></a>
						<a href="http://tabun.everypony.ru/profile/PrinceMars/">PrinceMars</a>
					</span>
									  
					<span class="user-avatar">
						<a href="http://tabun.everypony.ru/profile/MadHotaru/"><img src="http://tabun.everypony.ru/uploads/images/00/28/87/2013/01/29/avatar_24x24.png?002259" alt="avatar" /></a>
						<a href="http://tabun.everypony.ru/profile/MadHotaru/">MadHotaru</a>
					</span>
									  
					<span class="user-avatar">
						<a href="http://tabun.everypony.ru/profile/Ra1nbow_Death/"><img src="http://tabun.everypony.ru/uploads/images/00/72/33/2014/10/27/avatar_24x24.png?212410" alt="avatar" /></a>
						<a href="http://tabun.everypony.ru/profile/Ra1nbow_Death/">Ra1nbow_Death</a>
					</span>
					
			<br /><br />

			
			<strong>Модераторы (0)</strong><br />
							<span class="notice-empty">Модераторов здесь не замечено</span>
						    
    

			
			
			
					</div>
	</div>
	
	<footer class="blog-footer" id="blog-footer">
					<button type="submit"  class="button button-small" id="button-blog-join-second-2932" data-button-additional="button-blog-join-first-2932" data-only-text="1" onclick="ls.blog.toggleJoin(this, 2932); return false;">Подписаться на блог</button>
				<a href="http://tabun.everypony.ru/rss/blog/dearprincess/" class="rss">RSS</a>
		
		<div class="admin">
			Смотритель —
			<a href="http://tabun.everypony.ru/profile/drweb/"><img src="http://tabun.everypony.ru/uploads/images/00/03/80/2013/02/09/avatar_24x24.png?183813" alt="avatar" class="avatar" /></a>
			<a href="http://tabun.everypony.ru/profile/drweb/">drweb</a>
		</div>
	</footer>
</div>
</body>
</html>""")                                       //> html  : scala.xml.Elem = <html lang="ru"><head><meta charset="utf-8"/><met
                                                  //| a content="IE=edge,chrome=1" http-equiv="X-UA-Compatible"/><meta content="
                                                  //| width=device-width, initial-scale=1.0" name="viewport"/><title>Р”РѕСЂРѕРіР
                                                  //| °СЏ РїСЂРёРЅС†РµСЃСЃР° РЎРµР»РµСЃС‚РёСЏ... / РўР°Р±СѓРЅ - РјРµСЃС‚Рѕ, РіРґ
                                                  //| Рµ РїР°СЃСѓС‚СЃСЏ Р±СЂРѕРЅРё</title><meta content="Р”РѕСЂРѕРіР°СЏ РїСЂРёРЅ
                                                  //| С†РµСЃСЃР° РЎРµР»РµСЃС‚РёСЏ...:Р’ СЌС‚РѕРј Р±Р»РѕРіРµ РІС‹ РјРѕР¶РµС‚Рµ РЅ
                                                  //| Р°РїРёСЃР°С‚СЊ РїСЂРёРЅС†РµСЃСЃРµ РЎРµР»РµСЃС‚РёРё РїРёСЃСЊРјРѕ Рѕ С‚РѕРј,
                                                  //|  РєР°РєРѕР№ РІР°Р¶РЅС‹Р№ СѓСЂРѕРє РІС‹ РёР·РІР»РµРєР»Рё РґР»СЏ СЃРµР±СЏ. Р
                                                  //| џРёСЃСЊРјРѕ Рѕ С‚РѕРј, РєР°Рє РІС‹ РїСЂРёС€Р»Рё Рє РїРѕРЅРёРјР°РЅРёСЋ С‡Рµ
                                                  //| РіРѕ-С‚Рѕ РІР°Р¶РЅРѕРіРѕ. РќР°РїРёСЃР°РІ РѕР± СЌС‚РѕРј СЃСЋРґР°, РІС‹ РјРѕ
                                                  //| Р¶РµС‚Рµ РїРѕРґРµР»РёС‚СЊСЃСЏ СЌС‚РёРј СЃ РґСЂСѓРіРёРјРё, Рё РѕРЅРё С‚РѕР¶
                                                  //| Рµ РјРѕРіСѓС‚ РёР·РІР»РµС‡СЊ СѓСЂРѕРє, РєРѕС‚РѕСЂС‹Р№ РёР·РІР»РµРєР»Рё РІС
                                                  //| ‹. Р”РµР»СЏСЃСЊ СЌС‚Рё
                                                  //| Output exceeds cutoff limit.
	
	val hd = html.\\("div").filter(x => x.\@("id") == "content").head
                                                  //> hd  : scala.xml.Node = <div role="main" id="content">
                                                  //| 					
                                                  //| 						
                                                  //| 
                                                  //| 	
                                                  //| 					
                                                  //| 					
                                                  //| 
                                                  //| 
                                                  //| 
                                                  //| <script type="text/javascript">
                                                  //| 	jQuery(function($){
                                                  //| 		ls.lang.load({&quot;blog_fold_info&quot;:&quot;РЎРІРµСЂРЅСѓС‚СЊ&
                                                  //| quot;,&quot;blog_expand_info&quot;:&quot;Рћ Р±Р»РѕРіРµ&quot;});
                                                  //| 	});
                                                  //| </script>
                                                  //| 
                                                  //| 
                                                  //| 
                                                  //| 
                                                  //| <div class="blog-top">
                                                  //| 	<h2 class="page-header">Р”РѕСЂРѕРіР°СЏ РїСЂРёРЅС†РµСЃСЃР° РЎРµР»РµСЃС‚Рё
                                                  //| СЏ... </h2>
                                                  //| 
                                                  //| 	<div id="vote_area_blog_2932" class="vote-topic vote-count-positive not-
                                                  //| voted">
                                                  //| 		<a onclick="return ls.vote.vote(2932,this,1,'blog');" href="#" c
                                                  //| lass="vote-item vote-up" shape="rect"><span><i/></span></a>
                                                  //| 		<div title="РіРѕР»РѕСЃРѕРІ: 212" class="vote-item vote-count"><s
                                                  //| pan id="vote_total_blog_2932">+1697.04</span></div>
                                                  //| 		<a onclick="return ls.vote.vote(2932,this,-1,'blog');" href="#" 
                                                  //| class="vote-ite
                                                  //| Output exceeds cutoff limit.
	
	val tn = hd.child.filter(x => x.\@("class") == "blog-top").head
                                                  //> tn  : scala.xml.Node = <div class="blog-top">
                                                  //| 	<h2 class="page-header">Р”РѕСЂРѕРіР°СЏ РїСЂРёРЅС†РµСЃСЃР° РЎРµР»РµСЃС‚Рё
                                                  //| СЏ... </h2>
                                                  //| 
                                                  //| 	<div id="vote_area_blog_2932" class="vote-topic vote-count-positive not-
                                                  //| voted">
                                                  //| 		<a onclick="return ls.vote.vote(2932,this,1,'blog');" href="#" c
                                                  //| lass="vote-item vote-up" shape="rect"><span><i/></span></a>
                                                  //| 		<div title="РіРѕР»РѕСЃРѕРІ: 212" class="vote-item vote-count"><s
                                                  //| pan id="vote_total_blog_2932">+1697.04</span></div>
                                                  //| 		<a onclick="return ls.vote.vote(2932,this,-1,'blog');" href="#" 
                                                  //| class="vote-item vote-down" shape="rect"><span><i/></span></a>
                                                  //| 	</div>
                                                  //| </div>
  val nm = tn.\("h2").text.trim                   //> nm  : String = Р”РѕСЂРѕРіР°СЏ РїСЂРёРЅС†РµСЃСЃР° РЎРµР»РµСЃС‚РёСЏ...

	
}