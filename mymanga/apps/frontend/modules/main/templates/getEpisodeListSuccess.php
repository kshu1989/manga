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
    <?php foreach ($episodes as $episode): ?>
    <tr>
      <td>
        <a href="<?php echo url_for('showpictures/'.$episode->getId()) ?>">
          <?php echo $episode->getName() ?>
        </a>
      </td>
    </tr>
    <?php endforeach ?>
  </tbody>
</table>