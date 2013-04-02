<?php use_stylesheet('jobs.css') ?>
 
<h1>Job List</h1>
 
<table>
<!--
  <thead>
     <tr>
      <th>Id</th>
      <th>Category</th>
      <th>Type</th>
more columns here 
      <th>Created at</th>
      <th>Updated at</th>
    </tr>
  </thead>
-->
  <tbody>
    <?php foreach ($seasons as $season): ?>
    <tr>
      <td>
        <a href="<?php echo url_for('showepisodes/'.$season->getId()) ?>">
          <?php echo $season->getName() ?>
        </a>
      </td>
    </tr>
    <?php endforeach ?>
  </tbody>
</table>