<?php slot('sidebar') ?>
<table>
	<!-- custom sidebar code for the current template-->
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