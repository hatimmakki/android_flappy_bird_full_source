package game.birdintrouble.com;
import java.util.ArrayList;

import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.menus.CCMenuItemToggle;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

public class Global {
	
	/////////////////////////////////////////////Purchase KEY///////////////////////////////////////////////////////////////////////////
	public static String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvS4i78pZwtlfQQWJG4JTJ1YSxuCBUXW4b8QQH+Wq3eJwGvGYueFYhC2vyZk09zaBJ8uV0RVdZqJewUJlHxCAj6pxByqB9q+kl1dt4q0O0G6G3h2e9WwecfSriPTcz0rF2Y0Qo2KouatnHmPThV6nZKL5oYem6nusmIWM35/PGdvmzZDvlSjrfK0ZCpHv9y7YzmiSA7LjA4WkB81yj/hgzgEpn0I2FcnCUDiUJFsKNQ7AIPIZawb//r496kGfJUhGr1JzKGuDyVx7jC5xDA9jGumIuJkkBo140ALaSVfiW6eRHGQH0vK+9WXN9YppfgnCqcKr0N96A6lB4d38IVzz3wIDAQAB";
	public static String SKU_Lives3 = "c100";

	public static CGSize deviceSize;
	public final static float G_ORG_WIDTH = 640.0f;
	public final static float G_ORG_HEIGHT = 1136.0f;
	public final static int OBSTACLE_COUNT = 12;
	public  static  int LIVES_COUNT = 3;
	static final String TAG = "Line Flow";
	static final int RC_REQUEST = 10001;
	
	
	
	
	//////////////////ADS Variables///////////
	public final static String REVMOB_ID="530023fcdf5d6cf13d0f1623";
	public final static String CHARTBOOST_ID="52f8c9cd2d42da3a43311c7d";
	public final static String CHARTBOOXT_SIGNATURE="6743b5530e5278cb75abf9b9914bbd27d75c5a7e";

	public final static int D_EASY = 0;
	public final static int D_MEDIUM = 1;
	public final static int D_HARD = 2;
	
	public static float G_SWIDTH;
	public static float G_SHEIGHT;
	
	public static float G_SCALEX = G_SWIDTH / G_ORG_WIDTH;
	public static float G_SCALEY = G_SHEIGHT / G_ORG_HEIGHT;
	public static float G_SpeedScaleX = G_SWIDTH / 320.0f;
	public static float G_SpeedScaleY = G_SHEIGHT / 480.0f;
	
	public static boolean g_bMusicMute = false;
	public static boolean g_bSoundMute = true;
	public static boolean  g_bIsTimeMode = false;
	public static boolean ENABLE_LIVES = true;
	public static boolean DEBUG_MODE = false;
	
	public final static int RATIO_XY=0;
	public final static int RATIO_X=1;
	public final static int RATIO_Y=2;
	
	public static int UNLOCK_LEVEL = 0;
	public final static float g_fscaleR=1.0f;
	public static int g_iLives;

	public static int g_nDifficulty = D_EASY;
	public static int g_nCurrentLevel = 0;
	public static int g_nTotalScore = 0;

	public static CCAnimation g_frmBird;
	public static CCAnimation g_frmObstacleUp;
	public static CCAnimation g_frmObstacleDown;
	public static SoundEngine g_sSoundEngin = SoundEngine.sharedEngine();
	
	public static MediaPlayer m_pBackgroundMusic;
	public static MediaPlayer[] m_pWingMusic;
	public static MediaPlayer[] m_phitMusic;
	public static MediaPlayer[] m_pgroundMusic;
	public static MediaPlayer m_pointMusic;
	public static MediaPlayer m_ptapMusic;
	
	public static final String PREFS = "Game_info";
	public static int maxScore = 0;
	public static int winScore = 0;
	public static Boolean game_state_flag = false;
	
	public static final int g_nMusicCount = 6;
	
	public static float getX(float x)
	{
		float fx;
		fx=G_SWIDTH*x/G_ORG_WIDTH;		
		return fx;
	}
	public static float getY(float y)
	{
		float fy;
		fy=G_SHEIGHT-G_SHEIGHT*y/G_ORG_HEIGHT;
		return fy;
	}
	public static CCSprite newSprite(String str,float x, float y, CCNode target, int zOrder, int nRatio)
	{
		CCSprite sprite = CCSprite.sprite(str + ".png");
		setScale(sprite, nRatio);
		sprite.setPosition(CGPoint.ccp(x, y));
		target.addChild(sprite, zOrder);
		return sprite;
	}
	
	public static CCLabel newLabel(String str, String fontName, int fontsize, ccColor3B color,float x, float y, CCNode target, int zOrder, int nRatio){
		CCLabel label = CCLabel.makeLabel(str, fontName, fontsize);
		label.setPosition(x, y);
		label.setColor(color);
		setScale(label, nRatio);
		target.addChild(label, zOrder);
		return label;
	}
	
	public static CCMenuItemImage newButton(String btnName, float x, float y, CCNode target, String selector, boolean isOneImage, int nRatio) 
	{
		CCMenuItemImage item;
	    
	    if(isOneImage)
	        item = CCMenuItemImage.item("btn_" + btnName + ".png", "btn_" + btnName + ".png", target, selector);
	    else
	        item = CCMenuItemImage.item("btn_" + btnName + "_normal.png", "btn_" + btnName + "_pressed.png", target, selector);
	    setScale(item, nRatio);
	    item.setPosition(x, y);
	    return item;
	}
	
	public static CCMenuItemImage newButton1(String str,String str1,float X, float Y, CCNode target, String sel, int Ratio) 
	{
		CCMenuItemImage item;		
		item=CCMenuItemImage.item(str, str1, str, target, sel);
		setScale(item,Ratio);
		item.setPosition(X, Y);
		return item;					
	}
	
	public static CCMenuItemToggle newToggleButton(String btnName, float x, float y, CCNode target, String selector, boolean isOneImage, int nRatio)
	{
	    CCMenuItemToggle item;
	    CCMenuItemImage itemOn, itemOff;
	    
	    if(isOneImage)
	    {
	        if(g_bMusicMute){
	            itemOff = CCMenuItemImage.item("btn_" + btnName + "_normal.png", "btn_" + btnName + "_normal.png");
	            itemOn = CCMenuItemImage.item("btn_" + btnName + "_pressed.png", "btn_" + btnName + "_pressed.png");
	        }
	        else{
	        	itemOn = CCMenuItemImage.item("btn_" + btnName + "_normal.png", "btn_" + btnName + "_normal.png");
	            itemOff = CCMenuItemImage.item("btn_" + btnName + "_pressed.png", "btn_" + btnName + "_pressed.png");	            
	        }
	        
	    }
	    else
	    {
	    	itemOn = CCMenuItemImage.item("btn_" + btnName + "_normal.png", "btn_" + btnName + "_pressed.png");
            itemOff = CCMenuItemImage.item("btn_" + btnName + "_pressed.png", "btn_" + btnName + "_normal.png");	        
	    }

	    item = CCMenuItemToggle.item(target, selector, itemOn, itemOff);
	    setScale(item, nRatio);
	    item.setPosition(x, y);
	    return item;
	}

	public static CCSpriteFrame getSpriteFromAnimation(CCAnimation ani, int index)
	{
		ArrayList<CCSpriteFrame> frame = ani.frames();
		return frame.get(index);
	}
	public static void setScale(CCNode node, int Ratio)
	{
		if(Ratio==RATIO_XY)
		{
			node.setScaleX(G_SCALEX);
			node.setScaleY(G_SCALEY);
		}
		else if (Ratio==RATIO_X)
		{
			node.setScale(G_SCALEX);
		}
		else if(Ratio==RATIO_Y)
		{
			node.setScale(G_SCALEY);
		}
	}
	
	public static void init()
	{
		G_SWIDTH = CCDirector.sharedDirector().winSize().width;
		G_SHEIGHT = CCDirector.sharedDirector().winSize().height;
		G_SCALEX = (G_SWIDTH * g_fscaleR/G_ORG_WIDTH);
		G_SCALEY = (G_SHEIGHT * g_fscaleR/G_ORG_HEIGHT);		
	}
	public static CCAnimation newAnimation(String sName, int nStartNum, int nFrameNum, boolean isAscending, float fDelayPerUnit)
	{
		CCAnimation aniFrame = CCAnimation.animation("ani", fDelayPerUnit);
		if(isAscending)
		{
			 for(int i = nStartNum; i < nStartNum + nFrameNum; i++)
		        {
		            if(i < 10)
		                aniFrame.addFrame(sName+"000"+i+".png");
		            else
		            {
		            	aniFrame.addFrame(sName+"00"+i+".png");
		            }
		        }
		}
		else
		{
			for(int i = nStartNum + nFrameNum - 1; i >= nStartNum; i--)
	        {
				if(i < 10)
	                aniFrame.addFrame(sName+"000"+i+".png");
	            else
	            {
	            	aniFrame.addFrame(sName+"00"+i+".png");
	            }
	        }

		}
		return aniFrame;
	}

	public static void playEffect(int resId)
	{
		SoundEngine1.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity().getApplicationContext(), resId);
	}	

	public static void SetVolume(Boolean bTrue)
	{
		if(!bTrue)
		{
			SoundEngine1.sharedEngine().setEffectsVolume(1.0f);
			SoundEngine1.sharedEngine().setSoundVolume(1.0f);
		}
		
		else
		{
			SoundEngine1.sharedEngine().setEffectsVolume(0.0f);
			SoundEngine1.sharedEngine().setSoundVolume(0.0f);
		}
	}
	
	public static void releaseMusic()
	{
		 if(Global.m_pBackgroundMusic != null)
	     {
	       	Global.m_pBackgroundMusic.pause();
	       	Global.m_pBackgroundMusic.release();
	       	Global.m_pBackgroundMusic = null;
	     }
//		if(Global.m_pWingMusic != null){
//			Global.m_pWingMusic.stop();
//			Global.m_pWingMusic.release();
//			Global.m_pWingMusic = null;
//		}
//		if(Global.m_pPassMusic != null){
//			Global.m_pPassMusic.start();
//			Global.m_pPassMusic.release();
//			Global.m_pPassMusic = null;
//		}
		
		for(int k = 0; k < Global.g_nMusicCount; k ++)
		{
			if(Global.m_pWingMusic[k] != null)
			{
				Global.m_pWingMusic[k].pause();
				Global.m_pWingMusic[k].release();
				Global.m_pWingMusic[k] = null;
			}
		}
		for(int k = 0; k < 2; k++){
			if(Global.m_pgroundMusic[k] != null){
				Global.m_pgroundMusic[k].pause();
				Global.m_pgroundMusic[k].release();
				Global.m_pgroundMusic[k] = null;
			}
			if(Global.m_phitMusic[k] != null){
				Global.m_phitMusic[k].pause();
				Global.m_phitMusic[k].release();
				Global.m_phitMusic[k] = null;
			}
		}
		if(Global.m_pointMusic != null){
			Global.m_pointMusic.pause();
			Global.m_pointMusic.release();
			Global.m_pointMusic = null;
		}
		
		if(Global.m_ptapMusic != null){
			Global.m_ptapMusic.pause();
			Global.m_ptapMusic.release();
			Global.m_ptapMusic = null;
		}
	}
	
	public static void Playbackground(int resId)
	{
		SoundEngine1.sharedEngine().playSound(CCDirector.sharedDirector().getActivity().getApplicationContext(), resId, true);
	}	
	public static void loadGameInfo()
	{
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences _sharedPref = CCDirector.sharedDirector().getActivity().getSharedPreferences(PREFS, mode);
		
		boolean ret = false;
		ret = _sharedPref.getBoolean(String.valueOf(0), false);
		if(!ret) return;
			String key = "Score";
			Global.maxScore = _sharedPref.getInt(key, 0);
			Global.winScore = _sharedPref.getInt("WinScore", 0);
			
	}
	
	public static void saveGameInfo()
	{
		int mode = Activity.MODE_PRIVATE;
    	SharedPreferences _sharedPref = CCDirector.sharedDirector().getActivity().getSharedPreferences(PREFS, mode);
    	SharedPreferences.Editor editor = _sharedPref.edit();
    	
    	boolean ret = true;
    	editor.putBoolean(String.valueOf(0), ret);
    		String key = "Score";
    		editor.putInt(key,Global.maxScore);
    	editor.putInt("WinScore", Global.winScore);
		editor.commit();
	}
}
