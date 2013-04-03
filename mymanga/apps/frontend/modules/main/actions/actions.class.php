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
	public function executeGetVolumeList(sfWebRequest $request)
	{
		try{
			$this->volumes = Doctrine::getTable("MVolume")->findAll();
			$this->seasons = Doctrine::getTable("MSeason")->getHotSeasons();
		}catch(Exception $e){
			$this->forward404($e);
		}
	}

	public function executeGetSeasonList(sfWebRequest $request){
		try{
			$volume_id = $request->getParameter("id");
			if(empty($volume_id)){
				throw new Exception("param error");
			}
			$this->seasons = Doctrine::getTable("MSeason")->getSeasonListByVolumeId($volume_id);
		}catch(Exception $e){
			$this->forward404($e);
		}
	}

	public function executeGetEpisodeList(sfWebRequest $request) {
		try{
			$season_id = $request->getParameter("id");
			if(empty($season_id)){
				throw new Exception("param error");
			}
			$this->episodes = Doctrine::getTable("MEpisode")->findBy("season_id", $season_id);
		}catch(Exception $e){
			$this->forward404($e);
		}
	}

	public function executeGetFirstPicture(sfWebRequest $request) {
		try{
			$episode_id = $request->getParameter("id");
			if(empty($episode_id)){
				throw new Exception("param error");
			}
			$this->picture = Doctrine::getTable("MPicture")->findOneBy("episode_idAndIndex", array($episode_id, 1));
		}catch(Exception $e){
			$this->forward404($e);
		}
	}
}
