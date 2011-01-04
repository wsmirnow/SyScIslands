package SyScIslands;

public class Village {

	int  villager;
	float game;
	float wood;
	
	public Village() {
		this.villager = 0;
		this.game = 0f;
		this.wood = 0f;
	}
	
	public Village(int villager, float game, float wood) {
		this.villager = villager;
		this.game = game;
		this.wood = wood;
	}
	
	public int getVillager() {
		return villager;
	}
	
	public void setVillager(int villager) {
		this.villager = villager;
	}
	
	public float getGame() {
		return this.game;
	}
	
	public void setGame(float game) {
		this.game = game;
	}
	
	public float getWood() {
		return this.wood;
	}
	
	public void setWood(float wood) {
		this.wood = wood;
	}
	
	public void incVillager() {
		this.villager++;
	}
	
	public void decVillager() {
		this.villager--;
	}
	
	public int getVillagers() {
		return this.villager;
	}
}
