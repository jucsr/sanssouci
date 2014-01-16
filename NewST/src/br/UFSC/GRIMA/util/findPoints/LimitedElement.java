package br.UFSC.GRIMA.util.findPoints;

import java.io.Serializable;

public abstract class LimitedElement implements Serializable
{
	public boolean isLimitedArc()
	{
		if (this.getClass().equals(LimitedArc.class))
			return true;
		else
			return false;
	}
	
	public boolean isLimitedLine()
	{
		if (this.getClass().equals(LimitedLine.class))
			return true;
		else
			return false;
	}
}
