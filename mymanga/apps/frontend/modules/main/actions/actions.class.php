<?php

/**
 * main actions.
 *
 * @package    mymanga
 * @subpackage main
 * @author     Your name here
 * @version    SVN: $Id: actions.class.php 23810 2009-11-12 11:07:44Z Kris.Wallsmith $
 */
class mainActions extends sfActions
{
	/**
	 * Executes index action
	 *
	 * @param sfRequest $request A request object
	 */
	public function executeGetVlomeList(sfWebRequest $request)
	{
		$this->volumes = Doctrine::getTable("MVolume")->findAll();
	}

	public function executeGetSeasonList(sfWebRequest $request){
		try{
			$season_id = $request->getParameter("id");
			echo $season_id;
			if(empty($season_id)){
				throw new Exception("param error");
			}
			$this->season = Doctrine::getTable("MSeason")->find("id", $season_id);
		}catch(Exception $e){

		}
	}
}
