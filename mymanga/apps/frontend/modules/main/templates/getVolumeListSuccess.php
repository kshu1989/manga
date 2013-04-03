<?php use_stylesheet('jobs.css') ?>

<table>
	<?php foreach ($volumes as $volume): ?>
	<tr>
		<td><a href="<?php echo url_for('showseasons/'.$volume->getId()) ?>">
				<?php echo $volume->getName() ?>
		</a>
		</td>
	</tr>
	<?php endforeach ?>
	</tbody>
</table>


<?php slot('sidebar') ?>
<table>
	<h1>hot manga</h1>
	<tbody>
		<?php foreach ($seasons as $season): ?>
		<tr>
			<td><a href="<?php echo url_for('showepisodes/'.$season->getId()) ?>">
					<?php echo $season->getName() ?>
			</a>
			</td>
		</tr>
		<?php endforeach ?>
	</tbody>
</table>
<?php end_slot() ?>