package game.birdintrouble.com;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.ease.CCEaseAction;
import org.cocos2d.actions.ease.CCEaseBounceIn;
import org.cocos2d.actions.ease.CCEaseElastic;
import org.cocos2d.actions.ease.CCEaseElasticInOut;
import org.cocos2d.actions.grid.CCShaky3D;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCBlink;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCFadeTo;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRepeat;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.config.ccMacros;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCLabelAtlas;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;
import org.cocos2d.utils.CCFormatter;


import android.util.Log;
import android.view.MotionEvent;

public class GameScene extends CCLayer {
	
	private GameEndScene m_lGameEndLayer;
	private CCSprite m_sBirdSprite;
	private CCSprite m_sTapSprite;
	private CCSprite m_sReadySprite;
	private CCSprite m_sObstacle[] = new CCSprite[6];
	private CCColorLayer m_lColorLayer;
//	private CCMenuItemImage m_mPause;
//	private CCMenuItemImage m_mResume;
//	private CCMenuItemImage m_mMenu;
//	private CCMenu menu;
	private CCLabelAtlas m_lbtScore;
	private CCLabelAtlas m_lbtCurScore;
	private CCSprite m_sGroundSprite[] = new CCSprite[2];
	private CCSprite m_sCloud[] = new CCSprite[2];
	private CCAction m_aUpDownAction;
	private float m_fSpeedX = 0;
	private int m_iScore;
	private float m_fSpeedY = 0;
	private int m_iPastTime = 0;

	
	private final static int THRESHOLD = 112;
    private final static int INITIAL_VAL = 3 * (THRESHOLD + 28);
    private final static int RANDOM_VAL = 340+THRESHOLD;
	private static boolean Game_state_end = false;
    
    CCSprite m_startBt;
	private float degree;
	private CCSprite sp;
	private CCSprite m_sGameEndBg;
	private boolean scoreCheck;
	private CCSprite m_sHillFront[] = new CCSprite[2];
	private CCSprite m_sHillFar[] = new CCSprite[2];
	private CCSprite m_sFirTile[] = new CCSprite[2];
	private CCSprite m_lbtSlash;
	private CCSprite m_sTapStart;
	private float obstacle_speedX;
	private boolean extratme;
	private float move_init_coor;
	private CCAction m_aUpDownAction_win;
	private CCSprite m_sbackground_sprite;
	private boolean game_hit_colum = true;
	private int random_id;
	private CCTexture2D obstacle_change_tex_down;
	private CCTexture2D obstacle_change_tex_up;
	private CCAction m_aWingAction;
	private CCAnimation m_aTapWingAnim;
	private int older_tmp;
	
	
    
	public GameScene(boolean game_play_state){
		
		super();
		Game_state_end = false;
		createBackground();
		initVariable();
		
		
		if(game_play_state){
			this.gameEnd(true);
			m_sTapSprite.setVisible(false);
			m_sReadySprite.setVisible(false);
			m_sTapStart.setVisible(false);
		}

		this.setIsTouchEnabled(true);
	}

	/*
	 * All variable inited for Game play.
	 */
	private void initVariable() {
		// TODO Auto-generated method stub
	
//		 m_mResume.setIsEnabled(false);
//		 m_mResume.setVisible(false);
//		 m_mMenu.setIsEnabled(false);
//		 m_mMenu.setVisible(false);
		 
		
		((MainActivity) CCDirector.sharedDirector().getActivity()).backgroundMusicInit();
		
		 m_sObstacle[0].setAnchorPoint(0.5f, 0);
		 m_sObstacle[1].setAnchorPoint(0.5f, 1);
		 m_sObstacle[2].setAnchorPoint(0.5f, 0);
		 m_sObstacle[3].setAnchorPoint(0.5f, 1);
		 m_sObstacle[4].setAnchorPoint(0.5f, 0);
		 m_sObstacle[5].setAnchorPoint(0.5f, 1);
		    
		 m_aWingAction= m_sBirdSprite.runAction(CCRepeatForever.action(CCAnimate.action(Global.g_frmBird)));
		 m_aUpDownAction = m_sBirdSprite.runAction(CCRepeatForever.action(CCSequence.actions(CCMoveBy.action(1.05f,CGPoint.ccp(0, 40 * Global.G_SpeedScaleY)),CCMoveBy.action(1.35f,CGPoint.ccp(0, -40 * Global.G_SpeedScaleY)))));
		    
//		 m_aTapWingAnim = CCAnimation.animation("WingAnim", 0.1f);
//		 for (int i = 1; i<=4; i++) {
//		    m_aTapWingAnim.addFrame(CCFormatter.format("bird%d.png", i));
//		 }
		 
		 Global.g_frmObstacleUp = CCAnimation.animation("obstacleUp");
		 Global.g_frmObstacleDown = CCAnimation.animation("obstacleDown");
		 for (int i = 0; i < Global.OBSTACLE_COUNT; i++) {
				Global.g_frmObstacleUp.addFrame("obstacle_up" + i + ".png");
				Global.g_frmObstacleDown.addFrame("obstacle_down" + i + ".png");
		}
			
		 int tmp = (int)(Math.random()* INITIAL_VAL) + RANDOM_VAL;
		 
		 m_sObstacle[0].setPosition(m_sObstacle[0].getPosition().x, (tmp + THRESHOLD) * Global.G_SCALEY / Global.g_fscaleR);
		 m_sObstacle[1].setPosition(m_sObstacle[1].getPosition().x, (tmp - THRESHOLD) * Global.G_SCALEY / Global.g_fscaleR);

		 tmp = (int)(Math.random()* INITIAL_VAL) + RANDOM_VAL;
		 m_sObstacle[2].setPosition(m_sObstacle[2].getPosition().x, (tmp + THRESHOLD) * Global.G_SCALEY / Global.g_fscaleR);
		 m_sObstacle[3].setPosition(m_sObstacle[3].getPosition().x, (tmp - THRESHOLD) * Global.G_SCALEY / Global.g_fscaleR);
		   
		 tmp = (int)(Math.random()* INITIAL_VAL) + RANDOM_VAL;
		 m_sObstacle[4].setPosition(m_sObstacle[4].getPosition().x, (tmp + THRESHOLD) * Global.G_SCALEY / Global.g_fscaleR);
		 m_sObstacle[5].setPosition(m_sObstacle[5].getPosition().x, (tmp - THRESHOLD) * Global.G_SCALEY / Global.g_fscaleR);
		 
//		 m_fSpeedX = 4 * Global.G_SCALEX / Global.g_fscaleR;
		 m_fSpeedX = Global.G_SCALEX / Global.g_fscaleR;
		 m_iPastTime = 0;
		 schedule("SlideBackground");
		 schedule("Update");
		 schedule("UpdatePassScore");   
		 
		 m_iScore = 0;
		 degree = 0;
		 scoreCheck = false;
		 obstacle_speedX = Global.G_SpeedScaleX;
		 extratme = false;
		 random_id = 2;
	}

	/*
	 * All UI created for Game play.
	 */
	private void createBackground() {
		// TODO Auto-generated method stub
		m_sbackground_sprite = Global.newSprite("bg", Global.G_SWIDTH / 2, Global.G_SHEIGHT / 2, this, -1, Global.RATIO_XY);
		m_sHillFront[0] = Global.newSprite("Hill_front", Global.G_SWIDTH / 2, Global.getY(932), this, 1, Global.RATIO_XY);
		m_sHillFront[1] = Global.newSprite("Hill_front", Global.getX(960), Global.getY(932), this, 1, Global.RATIO_XY);
		
		m_sHillFar[0] = Global.newSprite("Hill_far", Global.G_SWIDTH / 2, Global.getY(833), this, 0, Global.RATIO_XY);
		m_sHillFar[1] = Global.newSprite("Hill_far", Global.getX(960), Global.getY(833), this, 0, Global.RATIO_XY);
		
		m_sFirTile[0] = Global.newSprite("Fir_tile", Global.G_SWIDTH / 2, Global.getY(880), this, 1, Global.RATIO_XY);
		m_sFirTile[1] =	Global.newSprite("Fir_tile", Global.getX(960), Global.getY(880), this, 1, Global.RATIO_XY);	
//	    m_sBirdSprite = Global.newSprite("bird0", Global.getX(100), Global.getY(280), this, 1, Global.RATIO_X);
		m_sBirdSprite = Global.newSprite("bird0", Global.getX(200), Global.getY(650), this, 3, Global.RATIO_X);
	    
	    m_sTapSprite = Global.newSprite("start_tap", Global.G_SWIDTH / 2, Global.getY(640), this, 3, Global.RATIO_X);
	    m_sReadySprite = Global.newSprite("get ready", Global.G_SWIDTH / 2, Global.getY(300), this, 3, Global.RATIO_X);
	    m_sTapStart = Global.newSprite("tap_start", Global.G_SWIDTH / 2, Global.getY(920), this, 3, Global.RATIO_X);
	    
	    m_sGroundSprite [0] = Global.newSprite("ground", Global.getX(640), Global.getY(1061), this, 2, Global.RATIO_XY);
	    m_sGroundSprite[1] = Global.newSprite("ground01", Global.getX(1918), Global.getY(1061), this, 2, Global.RATIO_XY);
	   
	    m_sCloud[0] = Global.newSprite("clouds_tile_1", Global.getX(640), Global.getY(720), this, -1, Global.RATIO_XY);
	    m_sCloud[1] = Global.newSprite("clouds_tile_2", Global.getX(1920), Global.getY(720), this, -1, Global.RATIO_XY);
	    
	    /*
	     *  This is created pipe obstacle.
	     */
	    m_sObstacle[0] = Global.newSprite("obstacle_up0", Global.getX(960), Global.getY(0), this, 1, Global.RATIO_XY);
	    m_sObstacle[1] = Global.newSprite("obstacle_down0", Global.getX(960), Global.getY(0), this, 1, Global.RATIO_XY);
	    m_sObstacle[2] = Global.newSprite("obstacle_up1", Global.getX(1310), Global.getY(0), this, 1, Global.RATIO_XY);
	    m_sObstacle[3] = Global.newSprite("obstacle_down1", Global.getX(1310), Global.getY(0), this, 1, Global.RATIO_XY);
	    m_sObstacle[4] = Global.newSprite("obstacle_up2", Global.getX(1660), Global.getY(0), this, 1, Global.RATIO_XY);
	    m_sObstacle[5] = Global.newSprite("obstacle_down2", Global.getX(1660), Global.getY(0), this, 1, Global.RATIO_XY);
	    
	    m_lColorLayer = CCColorLayer.node(ccColor4B.ccc4(0, 0, 0, 150));
	    addChild(m_lColorLayer,5);
	    m_lColorLayer.setVisible(false);
	    
//	    m_mPause = Global.newButton("pause", Global.getX(30), Global.getY(30), this, "onClickPause", false,Global.RATIO_X);
//	    m_mResume = Global.newButton("resume", Global.getX(30), Global.getY(30), this, "onClickResume", false,Global.RATIO_X);
//	    m_mMenu = Global.newButton("menu", Global.G_SWIDTH / 2, Global.G_SHEIGHT / 2, this, "onClickMenu", false,Global.RATIO_X);
//	    //m_mMusic = Global.newToggleButton("music", Global.getX(70), Global.getY(30), this, "onClickMusic", true, Global.RATIO_X);
//	    
//	    m_startBt = CCSprite.sprite("start game_on.png");
//	    m_startBt.setScaleX(Global.G_SCALEX); m_startBt.setScaleY(Global.G_SCALEY);
//	    m_startBt.setPosition(Global.G_SWIDTH/2.0f, 120 * Global.G_SCALEY);
//	    this.addChild(m_startBt, 10);
//	    
//	    menu = CCMenu.menu(m_mPause, m_mResume, m_mMenu);
//	    menu.setPosition(0, 0);
//	    addChild(menu ,10);
	    
//	    m_lbtScore = newLabel("0/100" ,24 ,ccColor3B.ccc3(255, 255, 255) ,CGPoint.ccp(Global.getX(160), Global.getY(30)));
//	    m_lbtScore.setAnchorPoint(0.5f, 0.5f);
//	    
	    m_lbtScore = CCLabelAtlas.label("0123456789", "Score_Font.png", 31, 59, '0');
		 
		String maxCoin = String.format("%d", 100);
		m_lbtScore.setString(maxCoin);
		m_lbtScore.setScale(Global.G_SCALEX / Global.g_fscaleR);
		    
		this.addChild(m_lbtScore ,4);
		m_lbtScore.setPosition(CGPoint.ccp(Global.getX(320), Global.getY(110)));

		m_lbtCurScore = CCLabelAtlas.label("0123456789", "Score_Font.png", 31, 59, '0');
		m_lbtCurScore.setString("0");
		m_lbtCurScore.setScale(Global.G_SCALEX / Global.g_fscaleR);
		m_lbtCurScore.setAnchorPoint(1,0);    
		this.addChild(m_lbtCurScore ,4);
		m_lbtCurScore.setPosition(CGPoint.ccp(Global.getX(295), Global.getY(110)));
		
		m_lbtSlash = Global.newSprite("slash", Global.getX(310), Global.getY(82), this, 4, Global.RATIO_XY);
	    Global.newSprite("score_effect", Global.getX(325), Global.getY(95), this, 5, Global.RATIO_XY);
	    
	    
	    /*
	     * GameEnd Layer Created.
	     */
	    m_lGameEndLayer = new GameEndScene(0, false);
	    addChild(m_lGameEndLayer, 5);
	}
	
	/*
	 * For Created Menu and Labels function.
	 */
	
	CCLabel newLabel(String str ,int size ,ccColor3B color ,CGPoint pos){
	    CCLabel label = CCLabel.makeLabel(str ,"AmericanTypewriter",size);
	    label.setScale(Global.G_SCALEX / Global.g_fscaleR);
//	    label.scaleY = G_SCALEY / g_fScaleR;
	    addChild(label);
	    label.setColor(color);
	    label.setAnchorPoint(0, 0.5f);
	    label.setPosition(pos);
	    return label;
	}
	
	public void onClickResume(){
		m_sBirdSprite.resumeSchedulerAndActions();
		schedule("SlideBackground");
		schedule("Update", 0.015f);
		schedule("UpdatePassScore", 0.015f);   
	}
	
//	public void onClickMenu(Object sender){
//	    //Global.FX_BTN();
//	  CCScene scene = CCScene.node();
//		scene.addChild(new MenuScene());
//		CCDirector.sharedDirector().replaceScene(scene);
//	}

	/*
	 * When Game End , This is function that occur.
	 */
	public  void gameEnd(Boolean game_end_state){
	    this.setIsTouchEnabled(false);
	    Game_state_end = true;
//	    m_lGameEndLayer = null;
//		m_lGameEndLayer = new GameEndScene((int)m_iScore / 2 , game_end_state);  
//	    addChild(m_lGameEndLayer, 3);
	    if(game_end_state){
//			Global.Playbackground(R.raw.bg);
	    	
	    	if(!Global.m_pBackgroundMusic.isPlaying()){
	    		Global.m_pBackgroundMusic.start();
	    	}
			m_lbtCurScore.setString("100");
			
			Global.loadGameInfo();
			Global.winScore = Global.winScore + 1;
			Global.saveGameInfo();
		}
	    m_lGameEndLayer.startGame(m_iScore/2, game_end_state);
	}
	
	public void hiteLayer(){
		  
		  CGSize screenSize =CCDirector.sharedDirector().winSize();
		  CCFadeOut playHiteScene = CCFadeOut.action(0.5f);
		  
		  String str_image = "gameover_bck.png";
		  sp = CCSprite.sprite(str_image);

		  sp.setAnchorPoint(CGPoint.getZero());
		  sp.setPosition(CGPoint.make(0, 0));
		     sp.setScaleX(Global.G_SCALEX); sp.setScaleY(Global.G_SCALEY);
		  sp.setOpacity(80);
		     sp.runAction(playHiteScene);
		     this.addChild(sp ,10);
	}
	/*
	 * When Score 100 pass, This function is actived.
	 * 
	 */
	public void startGame()
	{
		if(m_sGameEndBg != null){
			m_sGameEndBg.cleanup();
		}
		m_sGameEndBg = Global.newSprite("winner", Global.G_SWIDTH / 2, Global.getY(1704), this, 3, Global.RATIO_XY);
		
		CCCallFunc func = CCCallFunc.action(this, "DisaparedScene");
		CCMoveTo action_bg = CCMoveTo.action(2.0f, CGPoint.ccp(Global.G_SWIDTH / 2, Global.G_SHEIGHT / 2));
	    m_sGameEndBg.runAction(CCSequence.actions(action_bg, func));
	}
	
	/*
	 * This is moveaction function for gameover scene disapared.
	 *
	 */
	public void DisaparedScene()
	{
		CCCallFunc func = CCCallFunc.action(this, "onClickResume");
	    CCMoveTo action_bg = CCMoveTo.action(2.0f, CGPoint.ccp(Global.G_SWIDTH / 2, Global.getY(-540)));
	    m_sGameEndBg.runAction(CCSequence.actions(action_bg, func));
	}
	
	public int m_nMusicIndex = 0;
	public int m_nMusic_hitIndex = 0;
	public int m_nMusic_pointIndex = 0;
	public int m_nMusic_grountIndex = 0;
	/*
	 * This is UI touched function. 
	 */
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		// TODO Auto-generated method stub
		CGPoint touchLocation = CGPoint.make(event.getX(), event.getY());
		touchLocation = CCDirector.sharedDirector().convertToGL(touchLocation);
		
		if(this.m_startBt != null)
		{
			this.m_startBt.removeSelf();
			this.m_startBt = null;
		}
		if(!Game_state_end){
			 if(m_aUpDownAction != null){
				 
//				 	CGRect  temp = CGRect.make(m_sTapStart.getBoundingBox().origin.x + m_sTapStart.getBoundingBox().size.width * 0.2f, m_sTapStart.getBoundingBox().origin.y, m_sTapStart.getBoundingBox().size.width * 0.3f, m_sTapStart.getBoundingBox().size.height * 0.3f);
//				 	CGRect temp1 = CGRect.make(m_sTapStart.getBoundingBox().origin.x , m_sTapStart.getBoundingBox().origin.y, m_sTapStart.getBoundingBox().size.width * 0.8f, m_sTapStart.getBoundingBox().size.height * 0.6f);
//				 	CGRect temp2 = CGRect.make(m_sTapStart.getBoundingBox().origin.x + m_sTapStart.getBoundingBox().size.width * 0.8f , m_sTapStart.getBoundingBox().origin.y - m_sTapStart.getBoundingBox().size.height, m_sTapStart.getBoundingBox().size.width * 0.6f, m_sTapStart.getBoundingBox().size.height * 0.6f);
//				 	CGRect touch_rect = CGRect.make(touchLocation.x, touchLocation.y, 10, 10);
//				 	Log.d("temp2 rect", String.valueOf(temp2));
//				 	Log.d("touch rect", String.valueOf(touch_rect));
//				 	if(CGRect.intersects(temp, touch_rect) || CGRect.intersects(temp1, touch_rect) || CGRect.intersects(temp2, touch_rect)){
//				 		obstacle_speedX = Global.G_SpeedScaleX * 1.8f;
//				 		extratme  = true;
//				 	}
				 	
			        m_sBirdSprite.stopAction(m_aUpDownAction);
			        m_sBirdSprite.stopAction(m_aWingAction);
			        m_aUpDownAction = null;
			        m_sTapSprite.runAction(CCFadeTo.action(0.1f,0));
			        m_sReadySprite.runAction(CCFadeTo.action(0.1f,0));
			        m_sTapStart.runAction(CCFadeTo.action(0.1f, 0));
			    }
			 
//			 	CCCallFunc func_jump_tap = CCCallFunc.action(this, "tap_sound");
//			 	m_sBirdSprite.runAction(CCSpawn.actions(CCRepeat.action(CCAnimate.action(Global.g_frmBird), 1), func_jump_tap));
			 
			 m_nMusicIndex %= Global.g_nMusicCount;
			 
			 if(Global.m_pWingMusic[m_nMusicIndex] != null)
			 {
				 Global.m_pWingMusic[m_nMusicIndex ++].start();	 
			 }			 
			 
//			 	if(!Global.m_pWingMusic.isPlaying()){
//			 		Global.m_pWingMusic.start();
//			 		//Global.m_pWingMusic.setLooping(false);
//			 	}else{
//			 		Global.m_pPassMusic.start();
//			 		//Global.m_pPassMusic.setLooping(false);
//			 	}
			 	
			 	
			 	
			 	m_sBirdSprite.runAction(CCRepeat.action(CCAnimate.action(Global.g_frmBird), 1));
			    m_sBirdSprite.setRotation(-35);
			    degree = -35;
			    m_iPastTime  = 0;
			    
//			    if(m_sBirdSprite.getPosition().y < Global.getY(-500)){
//			    	this.setIsTouchEnabled(false);
//			    }
			    if(!extratme){
				    m_fSpeedY = 5.25f * Global.G_SpeedScaleY;
			    }else{
			    	m_fSpeedY = 6.0f * Global.G_SpeedScaleY;
			    }
		}
		
		return super.ccTouchesBegan(event);
	}
	public void tap_sound(){
		Global.playEffect(R.raw.sfx_wing);
	}
	/*
	 *	This is schedule function for game play. here is included ground scroll and pillars scroll and actor action when touched. 
	 */
	public void UpdatePassScore (float dt){
		
		if(m_aUpDownAction != null) return;
		
		if(m_iScore / 2 != 100){
			float rect_begin, rect_end; 
			for (int i = 0; i < 6; i ++){
				CGRect passRect = CGRect.make(m_sObstacle[i].getBoundingBox().origin.x, 0, 1.0f, Global.G_SHEIGHT);
		    	CGRect scoreBirdRect = CGRect.make(m_sBirdSprite.getBoundingBox().origin.x, 0, 2 * Global.G_SCALEX / Global.g_fscaleR , Global.G_SHEIGHT);
		    	if(!extratme){
		    		rect_begin = 200;
		    		rect_end = 195.4f;
		    	}else{
		    		rect_begin = 200;
		    		rect_end = 193;
		    	}
				if ((passRect.origin.x < Global.getX(rect_begin) && passRect.origin.x > Global.getX(rect_end)) && CGRect.intersects(m_sBirdSprite.getBoundingBox(), m_sbackground_sprite.getBoundingBox())){
					m_iScore++;
//					Global.playEffect(R.raw.sfx_point);
					if(!Global.m_pointMusic.isPlaying()){
						Global.m_pointMusic.start();
					}	
		    	}
			}
//			if(m_iScore >= 10){
//				m_lbtCurScore.setString(String.valueOf((int)m_iScore / 2 - 1));
//			}else{
//				m_lbtCurScore.setString(String.valueOf((int)m_iScore / 2));
//			}
			m_lbtCurScore.setString(String.valueOf((int)m_iScore / 2));
		}else{
			
//			unscheduleAllSelectors();
//			m_sBirdSprite.pauseSchedulerAndActions();
			
//			m_aUpDownAction_win = m_sBirdSprite.runAction(CCRepeatForever.action(CCSequence.actions(CCMoveBy.action(1.05f,CGPoint.ccp(0, 40 * Global.G_SpeedScaleY)),CCMoveBy.action(1.35f,CGPoint.ccp(0, -40 * Global.G_SpeedScaleY)))));
//			m_sBirdSprite.runAction(m_aUpDownAction_win);
//			gameEnd(true);
			for (int i = 0; i < 6; i ++){
				if (m_sObstacle[i].getBoundingBox().origin.x < -60 * Global.G_SCALEX / Global.g_fscaleR){
					CCScene scene = CCScene.node();
					scene.addChild(new GameScene(true));
					CCDirector.sharedDirector().replaceScene(scene);
		    	}
			}
//			gameEnd(true);
//			startGame();
//			m_iScore = 202;
		}
		
	}
	public void SlideBackground(float dt){
		 m_sHillFront[0].setPosition(m_sHillFront[0].getPosition().x - m_fSpeedX  * 0.8f * obstacle_speedX , m_sHillFront[0].getPosition().y);
		 m_sHillFront[1].setPosition(m_sHillFront[1].getPosition().x - m_fSpeedX  * 0.8f * obstacle_speedX, m_sHillFront[1].getPosition().y);
		    
		 if(m_sHillFront[0].getPosition().x < -320 * Global.G_SCALEX / Global.g_fscaleR){
		     m_sHillFront[0].setPosition(Global.G_SWIDTH / 2, Global.getY(932));
		     m_sHillFront[1].setPosition(Global.getX(960), Global.getY(932));
		 }
		 
		 m_sFirTile[0].setPosition(m_sFirTile[0].getPosition().x - m_fSpeedX  * 1.2f * obstacle_speedX, m_sFirTile[0].getPosition().y);
		 m_sFirTile[1].setPosition(m_sFirTile[1].getPosition().x - m_fSpeedX  * 1.2f * obstacle_speedX, m_sFirTile[1].getPosition().y);
		    
		 if(m_sFirTile[0].getPosition().x < -320 * Global.G_SCALEX / Global.g_fscaleR){
		     m_sFirTile[0].setPosition(Global.G_SWIDTH / 2, Global.getY(880));
		     m_sFirTile[1].setPosition(Global.getX(960), Global.getY(880));
		 }
		 
		 m_sHillFar[0].setPosition(m_sHillFar[0].getPosition().x - m_fSpeedX  * 0.5f * obstacle_speedX, m_sHillFar[0].getPosition().y);
		 m_sHillFar[1].setPosition(m_sHillFar[1].getPosition().x - m_fSpeedX  * 0.5f * obstacle_speedX, m_sHillFar[1].getPosition().y);
		 if(m_sHillFar[0].getPosition().x < -320 * Global.G_SCALEX / Global.g_fscaleR){
		     m_sHillFar[0].setPosition(Global.G_SWIDTH / 2, Global.getY(833));
		     m_sHillFar[1].setPosition(Global.getX(960), Global.getY(833));
		 }
		 
		 m_sCloud[0].setPosition(m_sCloud[0].getPosition().x - m_fSpeedX  * 0.3f * Global.G_SpeedScaleX, m_sCloud[0].getPosition().y);
		 m_sCloud[1].setPosition(m_sCloud[1].getPosition().x - m_fSpeedX  * 0.3f * Global.G_SpeedScaleX, m_sCloud[1].getPosition().y);
		    
		 if(m_sCloud[0].getPosition().x < - 640 * Global.G_SCALEX / Global.g_fscaleR){
		     m_sCloud[1].setPosition(Global.getX(640), Global.getY(720));
		     m_sCloud[0].setPosition(Global.getX(1920), Global.getY(720));
		 }
		 if(m_sCloud[1].getPosition().x < -640 * Global.G_SCALEX / Global.g_fscaleR)
		 {
			 m_sCloud[0].setPosition(Global.getX(640), Global.getY(720));
		     m_sCloud[1].setPosition(Global.getX(1920), Global.getY(720));
		 }
		 
//		 if(m_sBackground[0].getPosition().x < -160 * Global.G_SCALEX / Global.g_fscaleR){
//		     m_sBackground[1].setPosition(Global.getX(160), Global.G_SHEIGHT / 2);
//		     m_sBackground[0].setPosition(Global.getX(480), Global.G_SHEIGHT / 2);
//		 }
//		 
//		 if(m_sBackground[1].getPosition().x < -160 * Global.G_SCALEX / Global.g_fscaleR)
//		 {
//			 m_sBackground[0].setPosition(Global.getX(160), Global.G_SHEIGHT / 2);
//		     m_sBackground[1].setPosition(Global.getX(480), Global.G_SHEIGHT / 2);
//		 }
	}
	public void Update (float dt){
		
		Log.d("bird coordination", String.valueOf(m_sBirdSprite.getPosition()));
		 m_sGroundSprite[0].setPosition(m_sGroundSprite[0].getPosition().x - m_fSpeedX * 1.35f * obstacle_speedX, m_sGroundSprite[0].getPosition().y);
		 m_sGroundSprite[1].setPosition(m_sGroundSprite[1].getPosition().x - m_fSpeedX * 1.35f * obstacle_speedX, m_sGroundSprite[1].getPosition().y);
		    
		 if(m_sGroundSprite[0].getPosition().x < - 640 * Global.G_SCALEX / Global.g_fscaleR){
		     m_sGroundSprite[1].setPosition(Global.getX(640), Global.getY(1061));
		     m_sGroundSprite[0].setPosition(Global.getX(1918), Global.getY(1061));
		 }
		 
		 if(m_sGroundSprite[1].getPosition().x < -640 * Global.G_SCALEX / Global.g_fscaleR)
		 {
			 m_sGroundSprite[0].setPosition(Global.getX(640), Global.getY(1061));
		     m_sGroundSprite[1].setPosition(Global.getX(1918), Global.getY(1061));
		 }
		 
		 if(m_aUpDownAction != null) return;
		    
//		 m_fSpeedY -= 0.1 * m_iPastTime;
		
		 if (degree <= 90){
			 if (degree < -25){
				 degree = degree + 0.25f;	
				 m_fSpeedY -= 0.25f * Global.G_SpeedScaleY ;
			 }
			 if(degree >= -25 && degree <= 90){
				 degree = degree + 9.0f;
				 if(!extratme){
					 m_fSpeedY -= 0.55f * Global.G_SpeedScaleY ;
				 }else{
					 m_fSpeedY -= 0.65f * Global.G_SpeedScaleY ;
				 }
			 }
		 }
		 m_iPastTime++;
		 m_sBirdSprite.setPosition(CGPoint.ccpAdd(m_sBirdSprite.getPosition(), CGPoint.ccp(0, m_fSpeedY)));
		 m_sBirdSprite.setRotation(degree);
		 if(!this.isTouchEnabled_ && m_sBirdSprite.getPosition().y < Global.getY(200)){
			 this.setIsTouchEnabled(true);
		 }
//		 m_sBirdSprite.setRotation(-ccMacros.CC_RADIANS_TO_DEGREES((float)Math.atan2(m_fSpeedY * 0.5f, m_fSpeedX))/1.8f);
		
		 for (int i = 0; i < 6; i ++) {
		        m_sObstacle[i].setPosition(m_sObstacle[i].getPosition().x - m_fSpeedX  * 1.35f * obstacle_speedX, m_sObstacle[i].getPosition().y);
		        if(m_sObstacle[i].getPosition().x < -60 * Global.G_SCALEX / Global.g_fscaleR){
		            int tmp =(int)(Math.random() * INITIAL_VAL) + RANDOM_VAL;
		            if(older_tmp - tmp < 30){
		            	tmp = tmp + 50;
		            }
		            
		            older_tmp = tmp;
//		            random_id = ; 
//		            obstacle_change_tex_down = CCTextureCache.sharedTextureCache().addImage("obstacle_down" + random_id + ".png");
//		            obstacle_change_tex_up = CCTextureCache.sharedTextureCache().addImage("obstacle_up" + random_id + ".png");
		            
		            
		            if(m_iScore / 2 != 98){
		            	move_init_coor = 1050.0f;
		            }else{
		            	move_init_coor = 1500.0f;
		            }
		            
		            m_sObstacle[i].setPosition((float)(m_sObstacle[i].getPosition().x + move_init_coor * Global.G_SCALEX / Global.g_fscaleR), (tmp + THRESHOLD) * Global.G_SCALEY / Global.g_fscaleR);
		            m_sObstacle[i + 1].setPosition(m_sObstacle[i].getPosition().x, (tmp - THRESHOLD) * Global.G_SCALEY / Global.g_fscaleR);
//		            random_id = (int)(Math.random()*(float)Global.OBSTACLE_COUNT);
		            if(random_id < Global.OBSTACLE_COUNT - 1){
		            	random_id = random_id + 1 ;
		            }else{
		            	random_id = 0;
		            }
		            m_sObstacle[i].setDisplayFrame(Global.getSpriteFromAnimation(Global.g_frmObstacleUp, random_id));
		            m_sObstacle[i+1].setDisplayFrame(Global.getSpriteFromAnimation(Global.g_frmObstacleDown, random_id));
		            
		            i++;
		        }
		        else{
		        	CGRect temp = CGRect.make( m_sBirdSprite.getBoundingBox().origin.x + m_sBirdSprite.getBoundingBox().size.width * 0.35f , m_sBirdSprite.getBoundingBox().origin.y + m_sBirdSprite.getBoundingBox().size.height * 0.25f , m_sBirdSprite.getBoundingBox().size.width * 0.35f, m_sBirdSprite.getBoundingBox().size.height * 0.35f );
//		            CGRect temp1 = CGRect.make( m_sObstacle[i].getBoundingBox().origin.x , 0, m_sBirdSprite.getBoundingBox().size.width * 0.8f, Global.G_SHEIGHT * 3 );
		        	if(((CGRect.intersects(m_sObstacle[i].getBoundingBox(),temp)) || (m_sObstacle[i].getPosition().x < Global.getX(200) && !CGRect.intersects(temp, m_sbackground_sprite.getBoundingBox())))&& m_fSpeedX != 0 && m_iScore != 100){
		                this.setIsTouchEnabled(false);
                        if(obstacle_speedX != 0){
//                        	Global.playEffect(R.raw.sfx_hit);
                        	
                        	 m_nMusic_hitIndex %= 2;
                			 if(Global.m_phitMusic[m_nMusic_hitIndex] != null)
                			 {
                				 Global.m_phitMusic[m_nMusic_hitIndex ++].start();	 
                			 }			 
                			
                			 Log.d("hit audio index", String.valueOf(m_nMusic_hitIndex));
                			 
                        	CCCallFunc func = CCCallFunc.action(this, "hiteLayer");
//                        	CCCallFunc func_ground_hit_end = CCCallFunc.action(this, "ground_hit_end");
                        	Game_state_end = true;
                        	this.runAction(CCSequence.actions(CCSpawn.actions(CCRepeat.action(CCSequence.actions(CCMoveTo.action(0.08f, CGPoint.ccp(7, 3)), CCMoveTo.action(0.12f, CGPoint.ccp(-7, -3))),4),func)));
//                        	this.runAction(CCSequence.actions(CCDelayTime.action(0.15f), func_ground_hit_end));
                        }
                        obstacle_speedX = 0;
                        game_hit_colum = false;
	                    unschedule("UpdatePassScore");
		            }
		        }
		    }
//		 CGRect hitRect = CGRect.make( m_sBirdSprite.getBoundingBox().origin.x  , m_sBirdSprite.getBoundingBox().origin.y, 3, 3 );
		 CGRect ground_rect1= CGRect.make( m_sGroundSprite[0].getBoundingBox().origin.x - m_sGroundSprite[0].getBoundingBox().size.width * 0.5f , m_sGroundSprite[0].getBoundingBox().origin.y , m_sGroundSprite[0].getBoundingBox().size.width, m_sGroundSprite[0].getBoundingBox().size.height * 0.9f);
		 CGRect ground_rect2= CGRect.make( m_sGroundSprite[1].getBoundingBox().origin.x - m_sGroundSprite[1].getBoundingBox().size.width * 0.5f , m_sGroundSprite[1].getBoundingBox().origin.y , m_sGroundSprite[1].getBoundingBox().size.width, m_sGroundSprite[1].getBoundingBox().size.height * 0.9f);
		 if(CGRect.intersects(ground_rect1,m_sBirdSprite.getBoundingBox())  || CGRect.intersects(ground_rect2,m_sBirdSprite.getBoundingBox())){
			 obstacle_speedX = 0;
			 Game_state_end = true;
			 if(game_hit_colum){
//				 Global.playEffect(R.raw.sfx_hit_ground);
				 
				 m_nMusic_grountIndex %= 2;
    			 
    			 if(Global.m_pgroundMusic[m_nMusic_grountIndex] != null)
    			 {
    				 Global.m_pgroundMusic[m_nMusic_grountIndex ++].start();	 
    			 }			 
    			 
				 CCCallFunc func = CCCallFunc.action(this, "hiteLayer");
//				 CCCallFunc func_sound_eff = CCCallFunc.action(this, "ground_sound_effect");
				 CCCallFunc func_ground_hit_end = CCCallFunc.action(this, "ground_hit_end");
				 
				 this.runAction(CCSpawn.actions(CCRepeat.action(CCSequence.actions(CCMoveTo.action(0.08f, CGPoint.ccp(7, -3)), CCMoveTo.action(0.12f, CGPoint.ccp(-7, -3))),4),func));
				 this.runAction(CCSequence.actions(CCDelayTime.action(0.15f), func_ground_hit_end));
			 }else{
				 gameEnd(false);
			 }
			 unschedule("UpdatePassScore");
			 unschedule("Update");
			 m_sBirdSprite.pauseSchedulerAndActions();
			 
//			 gameEnd(false);
		 }
	}
	
	public void ground_hit_end(){
		gameEnd(false);
	}
	public void ground_sound_effect(){
		Global.playEffect(R.raw.sfx_hit_ground);
	}
	@Override
	public void onExit() {
		// TODO Auto-generated method stub
//		removeAllChildren(true);
		//unscheduleAllSelectors();		
		super.onExit();
	}
	
}
