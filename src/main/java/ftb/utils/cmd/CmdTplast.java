package ftb.utils.cmd;

import ftb.lib.*;
import ftb.lib.api.ForgePlayerMP;
import ftb.lib.api.cmd.*;
import ftb.utils.config.FTBUConfigCmd;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.DimensionType;

public class CmdTplast extends CommandLM
{
	public CmdTplast()
	{ super(FTBUConfigCmd.name_tplast.getAsString(), CommandLevel.OP); }
	
	@Override
	public String getCommandUsage(ICommandSender ics)
	{ return '/' + commandName + " [who] <to>"; }
	
	@Override
	public boolean isUsernameIndex(String[] args, int i)
	{ return i == 0; }
	
	@Override
	public void execute(MinecraftServer server, ICommandSender ics, String[] args) throws CommandException
	{
		checkArgs(args, 1);
		
		if(args.length == 3)
		{
			EntityPlayerMP ep = getCommandSenderAsPlayer(ics);
			double x = parseDouble(ep.posX, args[0], -30000000, 30000000, true);
			double y = parseDouble(ep.posY, args[1], -30000000, 30000000, true);
			double z = parseDouble(ep.posZ, args[2], -30000000, 30000000, true);
			LMDimUtils.teleportPlayer(ep, x, y, z, DimensionType.getById(ep.dimension));
			return;
		}
		
		EntityPlayerMP who;
		ForgePlayerMP to;
		
		if(args.length == 1)
		{
			who = getCommandSenderAsPlayer(ics);
			to = ForgePlayerMP.get(args[0]);
		}
		else
		{
			who = getPlayer(server, ics, args[0]);
			to = ForgePlayerMP.get(args[1]);
		}
		
		BlockDimPos p = to.getPos();
		if(p == null)
		{
			throw new RawCommandException("No last position!");
		}
		
		LMDimUtils.teleportPlayer(who, p);
	}
}