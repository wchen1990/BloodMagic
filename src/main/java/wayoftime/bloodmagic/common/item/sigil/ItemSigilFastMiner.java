package wayoftime.bloodmagic.common.item.sigil;

import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wayoftime.bloodmagic.util.DamageSourceBloodMagic;
import wayoftime.bloodmagic.util.helper.PlayerHelper;

public class ItemSigilFastMiner extends ItemSigilToggleableBase
{
	public ItemSigilFastMiner()
	{
		super("fast_miner", 100);
	}

	@Override
	public void onSigilUpdate(ItemStack stack, World world, PlayerEntity player, int itemSlot, boolean isSelected)
	{
		if (PlayerHelper.isFakePlayer(player))
			return;
		player.addPotionEffect(new EffectInstance(Effects.HASTE, 2, 0, true, false));
	}

	@Override
	public boolean performArrayEffect(World world, BlockPos pos)
	{
		double radius = 10;
		int ticks = 600;
		int potionPotency = 2;

		AxisAlignedBB bb = new AxisAlignedBB(pos).grow(radius);
		List<PlayerEntity> playerList = world.getEntitiesWithinAABB(PlayerEntity.class, bb);
		for (PlayerEntity player : playerList)
		{
			if (!player.isPotionActive(Effects.HASTE) || (player.isPotionActive(Effects.HASTE)
					&& player.getActivePotionEffect(Effects.HASTE).getAmplifier() < potionPotency))
			{
				player.addPotionEffect(new EffectInstance(Effects.HASTE, ticks, potionPotency));
				if (!player.isCreative())
				{
					player.hurtResistantTime = 0;
					player.attackEntityFrom(DamageSourceBloodMagic.INSTANCE, 1.0F);
				}
			}
		}

		return false;
	}

	@Override
	public boolean hasArrayEffect()
	{
		return true;
	}
}
