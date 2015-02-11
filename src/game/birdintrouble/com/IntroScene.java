package game.birdintrouble.com;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

public class IntroScene extends CCLayer{
	public IntroScene() {
		super();
		createBackground();
		schedule("delayTimer", 0.2f);
	}

	public void createBackground() {
		CCSprite sprite = CCSprite.sprite("splash.png");
		sprite.setScaleX(Global.G_SCALEX);
		sprite.setScaleY(Global.G_SCALEY);
		sprite.setPosition(Global.G_SWIDTH / 2, Global.G_SHEIGHT / 2);
		addChild(sprite, 1);
 	}

	public void delayTimer(float dt) {
		unschedule("delayTimer");
		CCScene scene = CCScene.node();
		scene.addChild(new GameScene(false));
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.5f, scene, ccColor3B.ccBLACK));
	}
}
