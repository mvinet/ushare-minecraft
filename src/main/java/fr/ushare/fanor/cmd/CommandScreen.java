package fr.ushare.fanor.cmd;

import java.util.ArrayList;
import java.util.List;

import fr.ushare.fanor.SendFile;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

public class CommandScreen implements ICommand 
{
	private List aliases;
	
	public CommandScreen() 
	{
		this.aliases = new ArrayList();
		this.aliases.add("screen");
		this.aliases.add("s");
	}
	
	@Override
	public int compareTo(Object arg0) 
	{
		return 0;
	}

	@Override
	public String getName() 
	{
		return "screen";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) 
	{
		return "/screen";
	}

	@Override
	public List getAliases() 
	{
		return this.aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args)throws CommandException 
	{
		SendFile sfile = new SendFile("sendfile");
		sfile.start();
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) 
	{
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) 
	{
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return false;
	}

}
