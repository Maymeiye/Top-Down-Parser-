class Statement
{
	Statement2 stt2;
	
	Statement(Statement2 stt)
	{
		stt2 = stt;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		IO.displayln(indent + indent.length() + " <statement>");
		stt2.printParseTree(indent1);
	}	
}