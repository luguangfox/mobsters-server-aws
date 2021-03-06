package com.lvl6.mobsters.utility.indexing.by_int;

public abstract class AbstractIntComparable 
	implements Comparable<AbstractIntComparable> 
{
	public abstract int getOrderingInt();

	@Override
	public int hashCode() 
	{
		return 31 * getOrderingInt();
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		int otherOrderInt = ((AbstractIntComparable) obj).getOrderingInt();
		int orderInt = getOrderingInt();

		if (orderInt != otherOrderInt)
			return false;
		
		return true;
	}

	@Override
	public int compareTo(AbstractIntComparable o) 
	{
		int otherOrderInt = o.getOrderingInt();
		int orderInt = getOrderingInt();
		
		if (orderInt < otherOrderInt) {
			return -1;
		} else if( otherOrderInt < orderInt) {
			return 1;
		}
		
		return 0;
	}
}
