class SinglePrimaryItem extends PrimaryItem
{
	SinglePrimaryItem(Primary p)
	{
		primary = p;
	}

	void printParseTree(String indent)
	{
		primary.printParseTree(indent);
	}	
}