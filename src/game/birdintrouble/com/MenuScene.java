package game.birdintrouble.com;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;


public class MenuScene extends CCLayer{

	private CCSprite m_sBirdSprite;
	private CCSprite m_sLogoSprite;
	private CCMenuItemImage m_mStart;
	private CCMenu menu;
	private CCSprite m_sGroundSprite[] = new CCSprite[2];
	

	public MenuScene(){
		//super();
		this.setIsTouchEnabled(true);
        createBackground();
        initVariable();
	}
	
	public void initVariable(){
		m_sBirdSprite.runAction(CCRepeatForever.action(CCAnimate.action(Global.g_frmBird)));
	    m_sBirdSprite.runAction(CCRepeatForever.action(CCSequence.actions(CCMoveBy.action(0.5f,CGPoint.ccp(0, 20 * Global.G_SCALEY / Global.g_fscaleR)),CCMoveBy.action(0.5f,CGPoint.ccp(0, -20 * Global.G_SCALEY / Global.g_fscaleR)))));
	    m_sLogoSprite.runAction(CCRepeatForever.action(CCSequence.actions(CCMoveBy.action(0.5f,CGPoint.ccp(0, 20 * Global.G_SCALEY / Global.g_fscaleR)),CCMoveBy.action(0.5f,CGPoint.ccp(0, -20 * Global.G_SCALEY / Global.g_fscaleR)))));

	    schedule("Update",0.1f);
	}
	
	public void createBackground(){
		Global.newSprite("bg", Global.G_SWIDTH / 2, Global.G_SHEIGHT / 2, this, -1, Global.RATIO_XY);
	    m_sBirdSprite = Global.newSprite("bird0", Global.getX(240), Global.getY(130), this, 1, Global.RATIO_X);
	    m_sLogoSprite = Global.newSprite("flappy bird", Global.getX(160), Global.getY(150), this, 1, Global.RATIO_X);
	    
//	    m_sGroundSprite[0] = Global.newSprite("ground", Global.getX(160), Global.getY(450), this, 0, Global.RATIO_XY);
//	    m_sGroundSprite[1] = Global.newSprite("ground", Global.getX(480), Global.getY(450), this, 0, Global.RATIO_XY);
	    m_sGroundSprite[0] = Global.newSprite("ground", Global.getX(160), Global.getY(440), this, 0, Global.RATIO_XY);
	    m_sGroundSprite[1] = Global.newSprite("ground", Global.getX(480), Global.getY(440), this, 0, Global.RATIO_XY);
	    
	    m_mStart = Global.newButton("start", Global.getX(160), Global.getY(300), this, "onClickStart", false,Global.RATIO_X);
	    
	        
	    menu = CCMenu.menu(m_mStart);
	    menu.setPosition(0, 0);
	    addChild(menu ,10);
	}
	/*
	 * This is occur function when click the start button 
	 */
	public void onClickStart(Object sender){
		Global.playEffect(R.raw.click);
		 
	    CCScene scene = CCScene.node();
		scene.addChild(new GameScene(false));
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(2.0f, scene, ccColor3B.ccBLACK));
	}
	
	/*
	 * This is schedule for Ground scroll function.
	 */

	public void Update(float dt){
		    m_sGroundSprite[0].setPosition(m_sGroundSprite[0].getPosition().x - 8 * Global.G_SCALEX / Global.g_fscaleR, m_sGroundSprite[0].getPosition().y);
		    m_sGroundSprite[1].setPosition(m_sGroundSprite[1].getPosition().x - 8 * Global.G_SCALEX / Global.g_fscaleR, m_sGroundSprite[1].getPosition().y);
		    
		    if(m_sGroundSprite [0].getPosition().x < -160 * Global.G_SCALEX / Global.g_fscaleR){
		        m_sGroundSprite[0].setPosition(Global.getX(160), Global.getY(440));
		        m_sGroundSprite[1].setPosition(Global.getX(480), Global.getY(440));
		    }
	}
	
}
