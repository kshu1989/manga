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
    <?php foreach ($volumes as $volume): ?>
    <tr>
      <td>
        <a href="<?php echo url_for('/showseasons/'.$volume->getId()) ?>">
          <?php echo $volume->getName() ?>
        </a>
      </td>
    </tr>
    <?php endforeach ?>
  </tbody>
</table>