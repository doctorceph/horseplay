package drceph.horseplay;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TanneryRecipe {
	
public static List<TanneryRecipe> recipeRegister = new LinkedList<TanneryRecipe>(); 
	
	public static boolean registerRecipe(FluidStack reagent, ItemStack input, ItemStack output) {
		if (reagent == null || input == null || output == null) return false;
		return recipeRegister.add(new TanneryRecipe(reagent,input,output));
	}
	
	public static ItemStack getOutput(FluidStack reagent, ItemStack input) {
		if (reagent == null || input == null) return null;
		for (TanneryRecipe recipe : recipeRegister) {
			if (recipe.hasReagent(reagent) && recipe.hasInput(input)) return recipe.getOutput();
		}
		return null;
	}
	
	public static boolean isValidRecipe(FluidStack reagent, ItemStack input) {
		if (reagent == null || input == null) return false;
		for (TanneryRecipe recipe : recipeRegister) {
			if (recipe.hasSufficientReagent(reagent) && recipe.hasInput(input)) return true;
		}
		return false;
	}
	
	public static boolean isValidRecipe(FluidStack reagent, int reagentAmount, ItemStack input) {
		if (reagent == null || input == null) return false;
		for (TanneryRecipe recipe : recipeRegister) {
			if (recipe.hasReagent(reagent) && reagentAmount >= recipe.getReagentAmount() && recipe.hasInput(input)) return true;
		}
		return false;
	}
	
	public static int getVolumeConsumption(FluidStack reagent, ItemStack input) {
		if (reagent == null || input == null) {
			//do nothing
		} else {
			for (TanneryRecipe recipe : recipeRegister) {
				if (recipe.hasReagent(reagent) && recipe.hasInput(input)) return recipe.getReagentAmount();
			}
		}
		return 0;
	}
	
	private FluidStack reagent;
	private ItemStack input;
	private ItemStack output;
	
	//FluidStack must have volume, ItemStack volume ignored (always 1:1)
	public TanneryRecipe(FluidStack reagent, ItemStack input, ItemStack output) {
		this.reagent = reagent;
		this.input = input;
		this.output = output;
	}
	
	public ItemStack getInput() {
		return input.copy();
	}
	
	public ItemStack getOutput() {
		return output.copy();
	}
	
	public FluidStack getReagent() {
		return reagent.copy();
	}
	
	public int getReagentAmount() {
		return reagent.amount;
	}
	
	public boolean hasReagent(FluidStack query) {
		return TanneryRecipe.isSameFluidStack(query, reagent, true);
	}
	
	public boolean hasSufficientReagent(FluidStack query) {
		return (TanneryRecipe.isSameFluidStack(query, reagent, true) 
				&& query.amount>=reagent.amount);
	}
	
	public boolean hasInput(ItemStack query) {
		return TanneryRecipe.isSameItemStack(query, input);
	}
	
	public static boolean isSameItemStack(ItemStack query, ItemStack subject) {
		return (query.itemID == subject.itemID);
	}
	
	public static boolean isSameFluidStack(FluidStack query, FluidStack subject, boolean ignoreVolume) {
		if (ignoreVolume) {
			return (query.fluidID == subject.fluidID);
		} else {
			return (query.fluidID == subject.fluidID && query.amount == subject.amount);
		}
	}
	
}
