package com.reiteam.ipopgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
    private Texture img;
    private Animation<TextureRegion> runningRight,runningLeft,runningUp,runningDown;
    private Animation<TextureRegion> idle;
    private int x,y;
    private float stateTime;
    private int playerMode = 0;
    private int rotation = 2;

    public Player(Texture sprite, int x,int y){
        this.img = sprite;
        // Running animation
        TextureRegion runningFrameRight[] = new TextureRegion[4];
        runningFrameRight[0] = new TextureRegion(img,30,590,366,593);
        runningFrameRight[1] = new TextureRegion(img,458,590,366,593);
        runningFrameRight[2] = new TextureRegion(img,952,590,366,593);
        runningFrameRight[3] = new TextureRegion(img,1378,590,366,593);
        this.runningRight = new Animation<TextureRegion>(0.08f,runningFrameRight);
        TextureRegion runningFrameLeft[] = new TextureRegion[4];
        runningFrameLeft[0] = new TextureRegion(img,30,1214,366,593);
        runningFrameLeft[1] = new TextureRegion(img,458,1214,366,593);
        runningFrameLeft[2] = new TextureRegion(img,952,1214,366,593);
        runningFrameLeft[3] = new TextureRegion(img,1378,1214,366,593);
        this.runningLeft = new Animation<TextureRegion>(0.08f,runningFrameLeft);
        TextureRegion runningFrameUp[] = new TextureRegion[4];
        runningFrameUp[0] = new TextureRegion(img,0,1872,429,533);
        runningFrameUp[1] = new TextureRegion(img,459,1840,462,562);
        runningFrameUp[2] = new TextureRegion(img,950,1872,431,528);
        runningFrameUp[3] = new TextureRegion(img,1412,1840,435,561);
        this.runningUp = new Animation<TextureRegion>(0.08f,runningFrameUp);
        TextureRegion runningFrameDown[] = new TextureRegion[4];
        runningFrameDown[0] = new TextureRegion(img,0,33,427,526);
        runningFrameDown[1] = new TextureRegion(img,461,0,459,557);
        runningFrameDown[2] = new TextureRegion(img,953,31,427,528);
        runningFrameDown[3] = new TextureRegion(img,1380,0,460,558);
        this.runningDown = new Animation<TextureRegion>(0.08f,runningFrameDown);
        // Idle animation
        TextureRegion idleFrame[] = new TextureRegion[1];
        idleFrame[0] = new TextureRegion(img,461,0,460,560);
        this.idle = new Animation<TextureRegion>(0.12f,idleFrame);
        this.x=x;
        this.y=y;
    }
    private TextureRegion getPlayerFrame(){
        if(playerMode==1){
            return runningRight.getKeyFrame(stateTime,true);
        }else if(playerMode==2){
            return runningLeft.getKeyFrame(stateTime,true);
        }else if(playerMode==3){
            return runningUp.getKeyFrame(stateTime,true);
        }else if(playerMode==4){
            return runningDown.getKeyFrame(stateTime,true);
        }
        return idle.getKeyFrame(stateTime,true);
    }
    public void render(Batch batch){
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion playerFrame = getPlayerFrame();
        batch.draw(playerFrame, MainGame.res[0]/2,MainGame.res[0]/2,0, 0,playerFrame.getRegionWidth(),playerFrame.getRegionHeight(),0.11f,0.12f,0);
    }
    public void disposeTextures(){
        img.dispose();
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPlayerMode() {
        return playerMode;
    }

    public void setPlayerMode(int playerMode) {
        this.playerMode = playerMode;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
