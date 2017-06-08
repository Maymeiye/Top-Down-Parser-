class DivPrimaryItem extends PrimaryItem
{
	DivPrimaryItem(Primary p)
	{
		primary = p;
	}

	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " /");
		primary.printParseTree(indent);
	}	
}