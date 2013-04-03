<!-- apps/frontend/templates/layout.php -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>MyManga - Your best manga site</title>
<?php include_javascripts() ?>
<?php include_stylesheets() ?>
</head>
<body>
	<div id="header">
		<h2>Ask for a job</h2>
		<form action="" method="get">
			<input type="text" name="keywords" id="search_keywords" /> <input
				type="submit" value="search" />
			<div class="help">Enter some keywords (city, country, position, ...)
			</div>
		</form>
	</div>


	<div style="position: relative; height: 100%">


		<div id="sidebar"
			style="position: absolute; top: 0; left: 0; height: 700px; width: 300px; background: #CCC;">
			<?php if (!include_slot('sidebar')): ?>
			<!-- default sidebar code
			<h1>Contextual zone</h1>
			<p>This zone contains links and information relative to the main
				content of the page.</p>
		    -->
			<?php endif; ?>
		</div>


		<div id="content" style="padding-left: 300px; background: #FFF;">
			<?php if ($sf_user->hasFlash('notice')): ?>
			<div class="flash_notice">
				<?php echo $sf_user->getFlash('notice') ?>
			</div>
			<?php endif ?>

			<?php if ($sf_user->hasFlash('error')): ?>
			<div class="flash_error">
				<?php echo $sf_user->getFlash('error') ?>
			</div>
			<?php endif ?>

			<div class="content">
				<?php echo $sf_content ?>
			</div>


			<br /> <br /> <br /> <br /> <br /> <br /> <br /> <br /> <br /> <br />
			<br /> <br /> <br /> <br /> <br /> <br /> <br /> <br /> <br /> <br />
			<br /> <br /> <br /> <br /> <br /> <br /> <br /> <br /> <br /> <br />
			<br /> <br /> <br />
		</div>
	</div>
	<div id="footer"></div>
</body>
</html>
