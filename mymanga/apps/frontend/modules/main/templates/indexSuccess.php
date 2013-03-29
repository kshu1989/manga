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
    <?php foreach ($mangas as $manga): ?>
    <tr>
      <td>
        <a href="<?php echo url_for('manga/show?id='.$manga->getId()) ?>">
          <?php echo $manga->getName() ?>
        </a>
      </td>
    </tr>
    <?php endforeach ?>
  </tbody>
</table>