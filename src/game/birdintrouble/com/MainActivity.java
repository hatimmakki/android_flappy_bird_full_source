package game.birdintrouble.com;

import java.io.IOException;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.sound.SoundEngine;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	private CCGLSurfaceView mGLSurfaceView;
	public static Activity mCtx;
	
	private void getScaledCoordinate() {

		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//		Global.G_SWIDTH = displayMetrics.widthPixels;
//		Global.G_SHEIGHT = displayMetrics.heightPixels;
		Global.G_SWIDTH =  CCDirector.sharedDirector().winSize().width;
		Global.G_SHEIGHT = CCDirector.sharedDirector().winSize().height;
		
		Log.d ("Screen Size Width", String.valueOf(Global.G_SWIDTH));
		Log.d ("Screen Size Height", String.valueOf(Global.G_SHEIGHT));
		
		Global.G_SCALEX = Global.G_SWIDTH / Global.G_ORG_WIDTH;
		Global.G_SCALEY = Global.G_SHEIGHT / Global.G_ORG_HEIGHT;
//		Global.G_SpeedScaleX = Global.G_SWIDTH / 320.0f;
		Global.G_SpeedScaleY = Global.G_SHEIGHT / 480.0f;
		Global.G_SpeedScaleX = 1080.0f / 320.0f;
//		Global.G_SpeedScaleY = 1920.0f / 480.0f;
	}

	
	public void preloadEffectSound()
	{
		
		Global.m_pWingMusic = new MediaPlayer[Global.g_nMusicCount];
		Global.m_phitMusic = new MediaPlayer[2];
		Global.m_pgroundMusic = new MediaPlayer[2];
		Global.m_pointMusic =  MediaPlayer.create(this, R.raw.sfx_point);
		Global.m_ptapMusic = MediaPlayer.create(this,R.raw.sfx_restart);
		
		try {
			Global.m_pointMusic.prepare();
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(int i = 0; i < Global.g_nMusicCount; i ++)
		{
			Global.m_pWingMusic[i] = MediaPlayer.create(this, R.raw.sfx_wing);
			try {
				Global.m_pWingMusic[i].prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(int i = 0 ; i < 2; i ++){
			Global.m_phitMusic[i] = MediaPlayer.create(this, R.raw.sfx_hit);
			Global.m_pgroundMusic[i] = MediaPlayer.create(this, R.raw.sfx_hit_ground);
			
//			Global.m_pointMusic[i] = MediaPlayer.create(this, R.raw.sfx_point);
			
			try {
				Global.m_phitMusic[i].prepare();
				Global.m_pgroundMusic[i].prepare();
//				Global.m_pointMusic[i].prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		SoundEngine.sharedEngine().preloadSound(CCDirector.sharedDirector().getActivity(), R.raw.bg);
	    SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity().getApplicationContext(), R.raw.sfx_hit);
	    SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity().getApplicationContext(), R.raw.sfx_point);
	    SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity().getApplicationContext(), R.raw.sfx_restart);
	    
//	    SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity().getApplicationContext(), R.raw.sfx_wing);
	}
	
	public void backgroundMusicInit(){
		if(Global.m_pBackgroundMusic != null){
			Global.m_pBackgroundMusic.pause();
			Global.m_pBackgroundMusic.release();
			Global.m_pBackgroundMusic = null;
		}
		Global.m_pBackgroundMusic = MediaPlayer.create(this,R.raw.bg);
		try {
			Global.m_pBackgroundMusic.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  requestWindowFeature(Window.FEATURE_NO_TITLE);
	       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	    	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    
	       mCtx = this;
	       mGLSurfaceView = new CCGLSurfaceView(this);
	       setContentView(mGLSurfaceView);
	       CCDirector.sharedDirector().attachInView(mGLSurfaceView);
	       
	       getScaledCoordinate();
	       preloadEffectSound();
	       initAni();
	       
	       CCScene scene = CCScene.node();
		   scene.addChild(new IntroScene());
		   CCDirector.sharedDirector().runWithScene(scene);
//		   SoundEngine.sharedEngine().playSound(this, R.raw.bg, true);
	}

	@Override
	public void onStart()
	{
		super.onStart();
	}
	
	@Override
	public void onPause() {
	    super.onPause();
	    CCDirector.sharedDirector().pause();
	    Global.SetVolume(true);
	}

	@Override
	public void onResume() {
	    super.onResume();
	    System.gc();
		
	    CCDirector.sharedDirector().resume();
	    Global.SetVolume(false);
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    Global.releaseMusic();
	    CCTextureCache.sharedTextureCache().removeAllTextures();
	    CCDirector.sharedDirector().end();
	}
	
//	@Override
//	public void onBackPressed(){
//		MainActivity.this.finish();
//		super.onDestroy();
//	}

	public void initAni() {
		Global.g_frmBird = CCAnimation.animation("bird", 0.15f);
		for (int i = 3; i >=0 ; i--) {
			String str = "bird" + i + ".png";
			Global.g_frmBird.addFrame(str);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
