package game.birdintrouble.com;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.IntBuffer;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;



import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCBlink;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCFadeTo;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemImage;
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

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.util.Log;
import android.view.MotionEvent;

public class GameEndScene extends CCLayer{

	private CCSprite m_mTapRestart;
	private CCMenu menu;
	private CCSprite m_sGameEndBg;
//	private CCSprite m_sGameEndTitle;
	private CCLabel m_sCurrentLable;
	private CCLabel m_sBestLavel;
	private CCLabelAtlas m_lbtScore;
	private CCLabelAtlas m_lbtCurScore;
	private CCMenuItemImage m_mShare;
	private CCMenuItemImage m_mTweet;
	private CCMenuItemImage m_mRate;
	private boolean game_touch_enabled;
	private boolean game_end_state_move;
	private CCLabelAtlas m_lbtWinScore;

	public GameEndScene(int score, boolean game_end_state){
		super();
		this.setIsTouchEnabled(false);
        createBackground(game_end_state);
//        setScore(score, game_end_state);
        game_end_state_move = game_end_state;
//        startGame();
	}

	/*
	 *  This is function that current game score and best game score setting. 
	 */
	private void setScore(int score, boolean game_end_state_score) {
		// TODO Auto-generated method stub
		
		 Global.loadGameInfo();
		 if(Global.maxScore < score){
		     Global.maxScore = score;
		 }
		 Global.saveGameInfo();	  
		 
		 if(!game_end_state_score){
			
			 m_lbtScore.setString(String.valueOf(score));
			 m_lbtCurScore.setString(String.valueOf(Global.maxScore));
			 m_lbtWinScore.setString(String.valueOf(Global.winScore / 2));
		 }		
//		 m_sCurrentLable = newLabel(String.valueOf(score),60 ,ccColor3B.ccc3(255, 255, 255),CGPoint.ccp(Global.getX(400), Global.getY(1636)));
//		 m_sBestLavel = newLabel(String.valueOf(Global.maxScore) ,60 ,ccColor3B.ccc3(255, 255, 255), CGPoint.ccp(Global.getX(400), Global.getY(1736)));
	}

	private CCLabel newLabel(String str ,int size ,ccColor3B color ,CGPoint pos) {
		// TODO Auto-generated method stub
		  CCLabel label = CCLabel.makeLabel(str ,"AmericanTypewriter",size);
		  label.setScale(Global.G_SCALEX / Global.g_fscaleR);
		  addChild(label);
		  label.setColor(color);
		  label.setAnchorPoint(0, 0.5f);
		  label.setPosition(pos);
		  return label;
	}

	/*
	 * This is Game End Scene UI Create Function.
	 */
	private void createBackground(boolean game_end_state_bg) {
		// TODO Auto-generated method stub
//		Global.newSprite("bg", Global.G_SWIDTH / 2, Global.G_SHEIGHT / 2, this, -1, Global.RATIO_XY);
		if(!game_end_state_bg){
			 m_sGameEndBg = Global.newSprite("end bg", Global.G_SWIDTH / 2, Global.getY(1704), this, 1, Global.RATIO_XY);
		}else{
			 m_sGameEndBg = Global.newSprite("winner", Global.G_SWIDTH / 2, Global.getY(1704), this, 1, Global.RATIO_XY);
		}
	   
	    
//	    m_sGameEndTitle = Global.newSprite("game over", Global.G_SWIDTH / 2, Global.getY(480), this, 1, Global.RATIO_X);
	    
	    m_mTapRestart = Global.newSprite("btn_ok_normal", Global.G_SWIDTH / 2, Global.getY(1946), this, 0,Global.RATIO_X);
	    m_mTapRestart.setTag(100);
	    m_mShare = Global.newButton("share_on", Global.getX(145), Global.getY(1856), this, "onClickOk", false,Global.RATIO_X);
	    m_mShare.setTag(101);
	    m_mTweet = Global.newButton("tweet_on", Global.getX(325), Global.getY(1856), this, "onClickOk", false,Global.RATIO_X);
	    m_mTweet.setTag(102);
	    m_mRate = Global.newButton("rate_on", Global.getX(500), Global.getY(1856), this, "onClickOk", false,Global.RATIO_X);
	    m_mRate.setTag(103);
	    
        menu = CCMenu.menu(m_mShare,m_mTweet,m_mRate);
	    menu.setPosition(0, 0);
	    addChild(menu,0);
	    
	    
	    m_lbtScore = CCLabelAtlas.label("0123456789", "gameover_score_cur.png", 50, 70, '0');
		 
		String maxCoin = String.format("%d", 0);
		m_lbtScore.setString(maxCoin);
		m_lbtScore.setScale(Global.G_SCALEX * 1.15f);
		m_lbtScore.setAnchorPoint(0.5f, 0.0f);
		this.addChild(m_lbtScore ,4);
		m_lbtScore.setPosition(CGPoint.ccp(Global.getX(460), Global.getY(1622)));
			
		m_lbtCurScore = CCLabelAtlas.label("0123456789", "gameover_score.png", 40, 52, '0');
		m_lbtCurScore.setString(String.valueOf(0));
		m_lbtCurScore.setScale(Global.G_SCALEX * 0.9f);
		m_lbtCurScore.setAnchorPoint(0.5f, 0.0f);
		this.addChild(m_lbtCurScore ,4);
		m_lbtCurScore.setPosition(CGPoint.ccp(Global.getX(460), Global.getY(1682)));
		 
		m_lbtWinScore = CCLabelAtlas.label("0123456789", "gameover_score.png", 40, 52, '0');
		m_lbtWinScore.setString(String.valueOf(0));
		m_lbtWinScore.setScale(Global.G_SCALEX * 0.9f);
		m_lbtWinScore.setAnchorPoint(0.5f, 0.0f);
		this.addChild(m_lbtWinScore ,4);
		m_lbtWinScore.setPosition(CGPoint.ccp(Global.getX(460), Global.getY(1738)));
	}	
	
	/*
	 * This is moveaction function for gameover scene presentation.
	 *
	 */
	public void startGame(int score, Boolean game_end_state_start)
	{
		
		game_end_state_move = game_end_state_start;
		setScore(score, game_end_state_start);
		
		if(game_end_state_start){
			 m_sGameEndBg.setTexture(CCTextureCache.sharedTextureCache().addImage("winner.png"));
		}
		
//		CCMoveTo action_title = CCMoveTo.action(3.0f, CGPoint.ccp(Global.G_SWIDTH / 2, Global.getY(90)));
//	    m_sGameEndTitle.runAction(action_title);
		CCCallFunc func = CCCallFunc.action(this, "touch_enable_set");
		
	    CCMoveTo action_bg = CCMoveTo.action(0.7f, CGPoint.ccp(Global.G_SWIDTH / 2, Global.getY(530)));
	    m_sGameEndBg.runAction(CCSequence.actions(action_bg,CCDelayTime.action(0.2f), func));
	    CCMoveTo action_btn_ok = CCMoveTo.action(0.7f, CGPoint.ccp(Global.G_SWIDTH / 2, Global.getY(860)));
	    m_mTapRestart.runAction(action_btn_ok);
	    
	    CCMoveTo action_btn_share = CCMoveTo.action(0.7f, CGPoint.ccp(Global.getX(145), Global.getY(720)));
	    m_mShare.runAction(action_btn_share);
	    CCMoveTo action_btn_tweet = CCMoveTo.action(0.7f, CGPoint.ccp(Global.getX(325), Global.getY(720)));
	    m_mTweet.runAction(action_btn_tweet);
	    CCMoveTo action_btn_rate = CCMoveTo.action(0.7f, CGPoint.ccp(Global.getX(500), Global.getY(720)));
	    m_mRate.runAction(action_btn_rate);
	    
	    if(!game_end_state_move){
//		    CCMoveTo action_cur_label = CCMoveTo.action(3.0f, CGPoint.ccp(Global.getX(140), Global.getY(240)));
		    CCMoveTo action_cur_label = CCMoveTo.action(0.7f, CGPoint.ccp(Global.getX(460), Global.getY(486)));
		    m_lbtScore.runAction(action_cur_label);
		    CCMoveTo action_best_label = CCMoveTo.action(0.7f, CGPoint.ccp(Global.getX(460), Global.getY(546)));
		    
		    m_lbtCurScore.runAction(action_best_label);
		    
		    CCMoveTo action_win_label = CCMoveTo.action(0.7f, CGPoint.ccp(Global.getX(460), Global.getY(602)));
		    m_lbtWinScore.runAction(action_win_label);
	    }

	}
	
	/*
	 * This is moveaction function for gameover scene disapared.
	 *
	 */
	public void DisaparedScene()
	{
		
//		CCMoveTo action_title = CCMoveTo.action(3.0f, CGPoint.ccp(Global.G_SWIDTH / 2, Global.getY(480)));
		CCCallFunc func = CCCallFunc.action(this, "Replace_GameScene");
//		CCAction seq = CCSequence.actions(action_title, func);
//		m_sGameEndBg.runAction(seq);
	    
	    CCMoveTo action_bg = CCMoveTo.action(0.3f, CGPoint.ccp(Global.G_SWIDTH / 2, Global.getY(1704)));
	    m_sGameEndBg.runAction(CCSpawn.actions(action_bg, func));
	    CCMoveTo action_btn_ok = CCMoveTo.action(0.3f, CGPoint.ccp(Global.G_SWIDTH / 2, Global.getY(1946)));
	    m_mTapRestart.runAction(action_btn_ok);
	    CCMoveTo action_btn_share = CCMoveTo.action(0.3f, CGPoint.ccp(Global.getX(145), Global.getY(1856)));
	    m_mShare.runAction(action_btn_share);
	    CCMoveTo action_btn_tweet = CCMoveTo.action(0.3f, CGPoint.ccp(Global.getX(325), Global.getY(1856)));
	    m_mTweet.runAction(action_btn_tweet);
	    CCMoveTo action_btn_rate = CCMoveTo.action(0.3f, CGPoint.ccp(Global.getX(500), Global.getY(1856)));
	    m_mRate.runAction(action_btn_rate);
	    
	    if(!game_end_state_move){
	    	CCMoveTo action_cur_label = CCMoveTo.action(0.3f, CGPoint.ccp(Global.getX(370), Global.getY(1630)));
	 	    m_lbtScore.runAction(action_cur_label);
	 	    CCMoveTo action_best_label = CCMoveTo.action(0.3f, CGPoint.ccp(Global.getX(370), Global.getY(1730)));
	 	    m_lbtCurScore.runAction(action_best_label);
	    }
	   
	}
	
	/*
	 * *******************
	 */
//	public void onScoreShare(){
//		  String CONSUMER_KEY = "YOUR_CONSUMER_KEY";
//		    String CONSUMER_SECRET = "YOUR_CONSUMER_SECRET";
//		    String ACCESS_SECRET = "YOUR_ACCESS_SECRET";
//		    String ACCESS_TOKEN = "YOUR_ACCESS_TOKEN";
//
//		    // Consumer
//		    Twitter twitter = new TwitterFactory().getInstance();
//		    twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
//
//		    // Access Token
//		    AccessToken accessToken = null;
//		    accessToken = new AccessToken(ACCESS_TOKEN, ACCESS_SECRET);
//		    twitter.setOAuthAccessToken(accessToken);
//
//		    // Posting Status
//		    Status status = null;
//		    try {
//		        status = twitter.updateStatus("YOUR_STATUS");
//		    } catch (TwitterException e) {
//		        e.printStackTrace();
//		    }
//		    System.out.println("Successfully updated the status: "
//		            + status.getText());
//	}
	
	/*
	 *  Score post in FaceBook.
	 */
	
//	public static void onFaceBook() {
//		  CGSize size = CCDirector.sharedDirector().winSize();
//		  CCDirector.sharedDirector();
//		  Bitmap bmp = SavePixels(0, 0, (int)size.width, (int)size.height, CCDirector.gl);
//		  File file = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/test.jpg");
//		    try
//		    {
//		     file.createNewFile();
//		        FileOutputStream fos = new FileOutputStream(file);
//		        bmp.compress(CompressFormat.JPEG, 100, fos);
//		    }
//		
//		    catch (Exception e)
//		    {
//		        e.printStackTrace();
//		    }
//		    Intent intent = new Intent();
//		    intent.setAction(Intent.ACTION_SEND);
//		    intent.setType("image/*");      
//		
//		    intent.putExtra(Intent.EXTRA_TEXT, "Hey check out my new character I unlocked on Happy Turd!");
//		    intent.putExtra(Intent.EXTRA_TITLE, "Happy Turd");
//		    intent.putExtra(Intent.EXTRA_SUBJECT, "With Image");
//		    intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.getAbsolutePath()));
//		    Log.e("abcd",file.getAbsolutePath());
//		    
//		    PackageManager pm = CCDirector.sharedDirector().getActivity().getBaseContext().getPackageManager();
//		    List<ResolveInfo> activityList = pm.queryIntentActivities(intent, 0);
//		    for (final ResolveInfo app : activityList) 
//		    {
//		     if ((app.activityInfo.name).contains("facebook")) 
//		            {
//		              final ActivityInfo activity = app.activityInfo;
//		              final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
//		             intent.addCategory(Intent.CATEGORY_LAUNCHER);
//		             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//		             intent.setComponent(name);
//		             CCDirector.sharedDirector().getActivity().startActivity(intent);
//		             break;
//		           }
//		        }
//		 }
//	
	/*
	 * Score post in Twitter.
	 */
	public void onTwitter() {
		CGSize size = CCDirector.sharedDirector().winSize();
		CCDirector.sharedDirector();
//		Bitmap bmp = SavePixels(0, 0, (int)size.width, (int)size.height, CCDirector.gl);
//        File file = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/test.jpg");
//        try
//        {
//        	file.createNewFile();
//            FileOutputStream fos = new FileOutputStream(file);
//            bmp.compress(CompressFormat.JPEG, 100, fos);
//        }
//
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
		
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");      

        intent.putExtra(Intent.EXTRA_TEXT, "My high score is this.");
        intent.putExtra(Intent.EXTRA_TITLE, "BirdIn Trouble");
        intent.putExtra(Intent.EXTRA_SUBJECT, String.valueOf(Global.maxScore));
//        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.getAbsolutePath()));
//        Log.e("abcd",file.getAbsolutePath());
        PackageManager pm = CCDirector.sharedDirector().getActivity().getBaseContext().getPackageManager();
        List<ResolveInfo> activityList = pm.queryIntentActivities(intent, 0);
        for (final ResolveInfo app : activityList) 
        {
        	if ((app.activityInfo.name).contains("twitter")) 
            {
              final ActivityInfo activity = app.activityInfo;
              final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
             intent.addCategory(Intent.CATEGORY_LAUNCHER);
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
             intent.setComponent(name);
             CCDirector.sharedDirector().getActivity().startActivity(intent);
             break;
           }
        }
	}
	
	/*
	 * Save Image for share in twitter.
	 */
//	public static Bitmap SavePixels(int x, int y, int w, int h, GL10 gl)
//	 {  
//	      int b[]=new int[w*(y+h)];
//	      int bt[]=new int[w*h];
//	      IntBuffer ib=IntBuffer.wrap(b);
//	      ib.position(0);
//	      gl.glReadPixels(x, 0, w, y+h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, ib);
//	      for(int i=0, k=0; i<h; i++, k++)
//	      {
//	           //remember, that OpenGL bitmap is incompatible with Android bitmap
//	       //and so, some correction need.        
//	           for(int j=0; j<w; j++)
//	           {
//	                int pix=b[i*w+j];
//	                int pb=(pix>>16)&0xff;
//	                int pr=(pix<<16)&0xffff0000;
//	                int pix1=(pix&0xff00ff00) | pr | pb;
//	                bt[(h-k-1)*w+j]=pix1;
//	           }
//	      }
//	
//	     Bitmap sb = Bitmap.createBitmap(bt, w, h, Bitmap.Config.ARGB_8888); 
//	     return sb;
//	 }
	
	/*
	 * touch enabled.
	 */
	public void touch_enable_set(){
		this.setIsTouchEnabled(true);
//		m_mTapRestart.runAction(CCRepeatForever.action(CCSequence.actions(CCFadeIn.action(2.0f),CCFadeOut.action(2.0f))));
	}
	
	/*
	 * After all children of game scence move out in UI, This is implementation. 
	 */
	public void Replace_GameScene(){
//		Global.playEffect(R.raw.sfx_restart);
		if(!Global.m_ptapMusic.isPlaying()){
			Global.m_ptapMusic.start();
		}
		
		if(Global.m_pBackgroundMusic.isPlaying()){
			Global.m_pBackgroundMusic.stop();
		}
		
		CCScene scene = CCScene.node();
		scene.addChild(new GameScene(false));
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.5f, scene, ccColor3B.ccBLACK));
		
		this.setIsTouchEnabled(false);
	}
	/*
	 * This is occur when ok button click event.
	 */
	public void onClickOk(Object sender){
		   // Global.FX_BTN();
		    Global.playEffect(R.raw.click);
		    CCMenuItemImage p = (CCMenuItemImage)sender;
		    
		    switch (p.getTag()) {
//		    	case 100:{
//		    		DisaparedScene();
//		    	}
//		    		break;
		    	case 101:{
//		    		  Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//		    		  sharingIntent.setType("text/plain");
//		    		  sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,String.valueOf(Global.maxScore));            
//		    		  MainActivity.mCtx.startActivity(Intent.createChooser(sharingIntent,"Share using"));
		    		Intent i = new Intent(Intent.ACTION_VIEW, 
						       Uri.parse("https://www.facebook.com/sharer/sharer.php?u=http://birdintrouble.com/"));
						MainActivity.mCtx.startActivity(i);
		    	}
		    		break;
		    	case 102:{
		    		Intent i = new Intent(Intent.ACTION_VIEW, 
						       Uri.parse("https://twitter.com/intent/tweet?original_referer=http%3A%2F%2Fbirdintrouble.com%2F"));
						MainActivity.mCtx.startActivity(i);
//		    		onTwitter();
//		    		onFaceBook();
		    	}
		    		break;
		    	case 103:{
		    		Intent i = new Intent(Intent.ACTION_VIEW, 
						       Uri.parse("https://play.google.com/store/apps/details?id=game.birdintrouble.com"));
						MainActivity.mCtx.startActivity(i);
		    	}		    		
		    		break;
		    	default:
		    		break;
		    }
		    
	}
	
	public int tap_count = 0;
	
	/*
	 * This is UI touched function. 
	 */
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		// TODO Auto-generated method stub
		CGPoint touchLocation = CGPoint.make(event.getX(), event.getY());
		touchLocation = CCDirector.sharedDirector().convertToGL(touchLocation);
		
		if(!game_touch_enabled){
			m_mTapRestart.stopAllActions();
			m_mTapRestart.setTexture(CCTextureCache.sharedTextureCache().addImage("btn_ok_pressed.png"));
//			DisaparedScene();
			tap_count++;
			Log.d("tap press count",String.valueOf(tap_count));
			Replace_GameScene();
			if(game_end_state_move){
				SoundEngine1.sharedEngine().realesAllSounds();
				Global.maxScore = 0;
				Global.saveGameInfo();	  
			}
		}
		return super.ccTouchesBegan(event);
	}
	
	@Override
	public void onExit() {
		// TODO Auto-generated method stub
//		removeAllChildren(true);
		//unscheduleAllSelectors();		
		super.onExit();
	}
	
}
