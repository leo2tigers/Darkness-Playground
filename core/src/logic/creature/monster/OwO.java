package logic.creature.monster;

import java.util.Date;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import logic.GameMap;
import logic.Meth;
import logic.SpawnPoint;
import logic.URect;
import logic.creature.player.Player;

public class OwO extends Monster {
	
	private URect damageBox = null;
	protected int attackState;
	protected Texture[] atkImg;
	private Texture[] afterAtkImg;
	protected Texture jumpImg;
	
	static String img_path_stand = "Monsters/Normal OwO/new_owo.png";
	static String img_path_jump = "Monsters/Normal OwO/new_owo_jump.png";
	
    public OwO(GameMap map, String name, double positionX, double positionY) {
        super(map, "OwO-" + name, 1, positionX, positionY, null);
        this.setHitBox(0, 0, 100, 100);
        this.setMovementBox(0, -5, 100, 10);
        setImg(img_path_stand);
        this.jumpImg = new Texture(img_path_jump);
        this.atkImg = new Texture[2];
        this.atkImg[0] = new Texture("Monsters/Normal OwO/new_owo_attack_left.png");
        this.atkImg[1] = new Texture("Monsters/Normal OwO/new_owo_attack_right.png");
        this.afterAtkImg = new Texture[2];
        this.afterAtkImg[0] = new Texture("Monsters/Normal OwO/new_owo_after_attack_left.png");
        this.afterAtkImg[1] = new Texture("Monsters/Normal OwO/new_owo_after_attack_right.png");
        this.max_sight_range = 500;
        this.attack_range = 50;
        this.movement_speed = 1;
    }
    
    public OwO(GameMap map, String name,int maxHealth, double positionX, double positionY,
    		   Texture img, Texture atkImgLeft, Texture atkImgRight, Texture jumpImg) {
        super(map, "OwO-" + name, maxHealth, positionX, positionY, img);
        this.setHitBox(0, 0, 100, 100);
        this.setMovementBox(0, -5, 100, 10);
        //setImg(img_path_stand);
        this.img = img;
        this.atkImg = new Texture[2];
        this.atkImg[0] = atkImgLeft;
        this.atkImg[1] = atkImgRight;
        this.jumpImg = jumpImg;
        this.max_sight_range = 500;
        this.attack_range = 50;
        this.movement_speed = 1;
    }
    
    @Override
    protected void attack_prepare() {
    	this.preDelay = 500;
    	this.postDelay = 500;
    }
    
    @Override
    protected void attackMethod() {
    	damageBox = new URect(positionX + this.hitBox.width/4 + this.orientation*50, positionY + this.hitBox.height/4, 
    			              50, 50, Color.CYAN);
    	if (damageBox.overlap(this.map.player.hitBox) && this.map.player.isAlive()) {
    		this.map.player.getHit(1);
    	}
    }

	@Override
	public void render(SpriteBatch batch) {
		if(this.attackable) {
			this.attackState = 0;
		}
		if(this.jumping) {
			batch.draw(jumpImg, (float) getX(), (float)getY());
		}
		if(this.attackState == 0) {
			batch.draw(this.img, (float) getX(), (float) getY());
		}
		else if(this.attackState == 1) {
			if(this.orientation == -1) {
				batch.draw(this.atkImg[0], (float) getX(), (float) getY());
			}
			else if(this.orientation == 1) {
				batch.draw(this.atkImg[1], (float) getX(), (float) getY());
			}
		}
		else if(this.attackState == 2) {
			if(this.orientation == -1) {
				batch.draw(this.afterAtkImg[0], (float) getX() + 40, (float) getY());
			}
			else if(this.orientation == 1) {
				batch.draw(this.afterAtkImg[1], (float) getX(), (float) getY());
			}
		}
	}
	
	@Override
	public void shapeRender(ShapeRenderer shapeRenderer) {
		super.shapeRender(shapeRenderer);
		if (this.damageBox != null) {
			this.damageBox.shapeRender(shapeRenderer);
		}
	}
	
	@Override
	public void grantXp(Player player)
	{
		player.addXp(30);
	}
	
	public static Spawnable spawnable = new Spawnable() {
		@Override
		public Monster spawn(SpawnPoint spawnPoint) {
			return new OwO(spawnPoint.map, "from_spawn_point_01", Meth.center_random(spawnPoint.getX(), spawnPoint.spawnWidth), spawnPoint.getY());
		}
	};

	@Override
	protected void inSight() {
		if (getDistance(this, this.map.player) <= attack_range) {
			attack();
		} else {
			if (this.map.player.getX() - this.positionX >= 0) {
				this.orientation = 1;
				this.speedX = this.orientation*this.movement_speed;
			} else {
				this.orientation = -1;
				this.movement_speed = this.orientation*this.movement_speed;
			}
		}
	}
	
	@Override
	public void attack() {
        if (isAlive()) {
        	attack_prepare();
            if (attackable) {
                attackDate = new Date();
                Thread attackThread = new Thread(() -> {
                	status = "ATTACKING";
                    // preAnimation delay
                    attackable = false;
                    this.setAttackState(1);
                    Date newDate = new Date();
                    while (newDate.getTime() - attackDate.getTime() <= preDelay) {
                        newDate = new Date();
                    }

                    // attack!
                    if (isAlive()) {
                    	attackMethod();
                    } else {
                    	return;
                    }
                    this.setAttackState(2);

                    // postAnimation delay
                    attackDate = new Date();
                    newDate = new Date();
                    while (newDate.getTime() - attackDate.getTime() <= postDelay) {
                        newDate = new Date();
                    }
                    this.setAttackState(0);
                    attackable = true;
                    status = "NORMAL";
                });
                attackThread.start();
            }
        }
    }

	public int getAttackState() {
		return attackState;
	}

	public void setAttackState(int attackState) {
		this.attackState = attackState;
	}
}
