class MultiBoolPrimary extends BoolPrimaryItem
{
	MultiBoolPrimary(BoolPrimary p)
	{
		boolPrimary = p;
	}

	void printParseTree(String indent)
	{
		boolPrimary.printParseTree(indent);
	}
}