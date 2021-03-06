<?php
// Connection Component Binding
Doctrine_Manager::getInstance()->bindComponent('MVolume', 'doctrine');

/**
 * BaseMVolume
 * 
 * This class has been auto-generated by the Doctrine ORM Framework
 * 
 * @property integer $id
 * @property string $name
 * @property string $url
 * @property integer $click_count
 * @property Doctrine_Collection $MSeason
 * 
 * @method integer             getId()          Returns the current record's "id" value
 * @method string              getName()        Returns the current record's "name" value
 * @method string              getUrl()         Returns the current record's "url" value
 * @method integer             getClickCount()  Returns the current record's "click_count" value
 * @method Doctrine_Collection getMSeason()     Returns the current record's "MSeason" collection
 * @method MVolume             setId()          Sets the current record's "id" value
 * @method MVolume             setName()        Sets the current record's "name" value
 * @method MVolume             setUrl()         Sets the current record's "url" value
 * @method MVolume             setClickCount()  Sets the current record's "click_count" value
 * @method MVolume             setMSeason()     Sets the current record's "MSeason" collection
 * 
 * @package    mymanga
 * @subpackage model
 * @author     Your name here
 * @version    SVN: $Id: Builder.php 7490 2010-03-29 19:53:27Z jwage $
 */
abstract class BaseMVolume extends sfDoctrineRecord
{
    public function setTableDefinition()
    {
        $this->setTableName('m_volume');
        $this->hasColumn('id', 'integer', 4, array(
             'type' => 'integer',
             'fixed' => 0,
             'unsigned' => false,
             'primary' => true,
             'autoincrement' => true,
             'length' => 4,
             ));
        $this->hasColumn('name', 'string', 50, array(
             'type' => 'string',
             'fixed' => 0,
             'unsigned' => false,
             'primary' => false,
             'notnull' => true,
             'autoincrement' => false,
             'length' => 50,
             ));
        $this->hasColumn('url', 'string', 200, array(
             'type' => 'string',
             'fixed' => 0,
             'unsigned' => false,
             'primary' => false,
             'notnull' => true,
             'autoincrement' => false,
             'length' => 200,
             ));
        $this->hasColumn('click_count', 'integer', 8, array(
             'type' => 'integer',
             'fixed' => 0,
             'unsigned' => false,
             'primary' => false,
             'default' => '0',
             'notnull' => true,
             'autoincrement' => false,
             'length' => 8,
             ));
    }

    public function setUp()
    {
        parent::setUp();
        $this->hasMany('MSeason', array(
             'local' => 'id',
             'foreign' => 'volume_id'));
    }
}