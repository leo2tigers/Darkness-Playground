package logic;

import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.darknessplayground.game.screen.MainGame;

import logic.creature.monster.Monster;

public class Projectile extends GameObject {
	private double orientation;
    private double speed;
    private int damage;
    private URect damageBox;
    private final Date createDate;
    private final int lifetime;
	private int damageCheckType;
	private int hit = 0;
    public static final int TO_MONSTER = 0;
	public static final int TO_PLAYER = 1;
	
	private Texture img = null;
	private Texture hitImg = null;
	private String imgPath = null;
	private String hitPath = null;

    public Projectile(double positionX, double positionY, 
    		          double width, double height, 
    		          double orientation, double speed, 
    		          int lifetime, int damage, int damageCheckType, String img_path) {
        super(positionX, positionY);
        this.orientation = orientation;
        this.speed = speed;
        this.lifetime = lifetime;
        this.damage = damage;
        this.damageCheckType = damageCheckType;
        this.damageBox = new URect(positionX, positionY, width, height, Color.CORAL);
        this.imgPath = img_path;
        this.hit = 0;
        this.createDate = new Date();
        setTexture();
    }
    public Projectile(double positionX, double positionY, 
			          double width, double height, 
			          double orientation, double speed, 
				      int lifetime, int damage, int damage_check_type, 
				      String imgPath, String hitPath) {
    	super(positionX, positionY);
		this.orientation = orientation;
		this.speed = speed;
		this.lifetime = lifetime;
		this.damage = damage;
		this.damageCheckType = damage_check_type;
		this.damageBox = new URect(positionX, positionY, width, height, Color.CORAL);
		this.imgPath = imgPath;
		this.hitPath = hitPath;
		this.hit = 0;
		this.createDate = new Date();
		setTexture();
	}
    

	public void setTexture()  {
    	Gdx.app.postRunnable(new Runnable(){
            public void run(){
            	img = new Texture(imgPath);
            	if (hitPath != null) {
            		hitImg = new Texture(hitPath);
            	}
            }
        });
    }
    
    @Override
    public void update() {
        positionX += speed*orientation;
        damageBox.translate(speed*orientation, 0.0);
        if (damageCheckType == TO_PLAYER) {
        	damageToPlayer();
        } else if (damageCheckType == TO_MONSTER) {
        	damageToMonster();
        }
        
        // check lifetime
        if ((new Date()).getTime() - createDate.getTime() >= lifetime) {
        	map.remove(this);
        }
        
        //check out of border
        if(this.positionX < -50 || this.positionX > Gdx.graphics.getWidth() + 100)
        {
        	map.remove(this);
        }
    }
    
    private void damageToMonster() {
    	Monster nearest = null;
        double distance = 720.0;
        for (Monster monster : map.getMonsters()) {
            if (damageBox.overlap(((Monster) monster).hitBox)) {
                if (getDistance(this, monster) <= distance) {
                    nearest = (Monster) monster;
                }
            }
        }
        if (nearest != null) {
            nearest.getHit(damage);
            hit += 1;
            map.remove(this);
        }
    }
    
    private void damageToPlayer() {
    	if (this.damageBox.overlap(this.map.player.hitBox)) {
    		this.map.player.getHit(damage);
    		this.damage = 0;
    		this.hit += 1;
    		this.img = this.hitImg;
    		this.speed = 0;
    	}
    }

	@Override
	public void shapeRender(ShapeRenderer shapeRenderer) {
		damageBox.shapeRender(shapeRenderer);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		try {
			batch.draw(img, (float) positionX, (float) positionY, (float) damageBox.getWidth(), (float) damageBox.getHeight());
		} catch (NullPointerException npe) {
			MainGame.sendStatus("rendering delay");
		}
	}
	
	@Override
	public String toString() {
		return "Projectile ( " + (lifetime + this.createDate.getTime() - (new Date()).getTime()) + " ) " + 
	           ((this.damageCheckType == 0) ? "TO_MONSTER" : "TO_PLAYER") + 
	           " ( " + this.positionX + " , "  + this.positionY + " )";
	}
	
	public void dispose()
	{
		this.img.dispose();
	}
}
