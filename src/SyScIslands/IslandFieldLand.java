package SyScIslands;
import java.util.Random;

import eawag.model.Swarm;

public class IslandFieldLand extends IslandField {
		
	Village village = null;
	float game = 0f;
	float wood = 0f;
	boolean water = false;
	
	IslandGrid grid;
	
	public void condition() {
		super.condition();
	}
	
	public void action() {
		if (init) {
			// initialize Agent
			Swarm parent = getFather();
			if (parent instanceof IslandGrid) {
				IslandGrid grid = (IslandGrid) parent;
				this.grid = grid;
				Random r = grid.getTop().getRandom();
				game = r.nextFloat() * grid.maxGamePF;
				wood = r.nextFloat() * grid.maxWoodPF;
				if (r.nextFloat() <= grid.freshWaterProp) {
					water = true;
				}
			} else {
				getTop().getErr().println("Parent is not instance of "+IslandGrid.class.getName());
				getTop().setActive(false);
				return;
			}
			init = false;
			return;
		}
		
		game += grid.gameRegeneration;
		if (game > grid.maxGamePF) game = grid.maxGamePF;
		
		wood += grid.woodRegeneration;
		if (wood > grid.maxWoodPF) wood = grid.maxWoodPF;
	}
}
