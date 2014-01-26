package drceph.horseplay;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.Icon;
import net.minecraftforge.fluids.FluidStack;

public class TanneryLiquidReagent {
	
	private FluidStack reagent;
	private int processingSteps;
	private int offset;
	
	private static Map<Integer, TanneryLiquidReagent> reagents = new HashMap<Integer, TanneryLiquidReagent>();
	
	public static boolean isValidReagent(int liquidId) {
         return reagents.containsKey(liquidId);
	}
	
	public static TanneryLiquidReagent getReagentById(int liquidId) {
		return reagents.get(liquidId);
	}
	
	public TanneryLiquidReagent(FluidStack fluidStack, int steps, int offset) {
		reagent = fluidStack;
		processingSteps = steps;
		this.offset = offset;
		TanneryLiquidReagent.reagents.put(fluidStack.fluidID, this);
	}
	
	public FluidStack getReagent() {
		return reagent;
	}
	
	public int getReagentId() {
		return reagent.fluidID;
	}
	
	public int getProcessingSteps() {
		return processingSteps;
	}
	
	public Icon getIcon() {
		return reagent.getFluid().getIcon();
	}
	
	public int getOffset() {
		return offset;
	}

}
