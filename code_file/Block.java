class Block extends Statement2
{
	SList e;       

	Block(SList exp)
	{
		e = exp;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
				
		IO.displayln(indent + indent.length() + " <block>");
		e.printParseTree(indent1);
	}
}
