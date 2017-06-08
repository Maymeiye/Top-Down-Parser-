import java.util.LinkedList;

class CaseList
{
	LinkedList<Case> caseList;

	CaseList(LinkedList<Case> ca)
	{
		caseList = ca;
	}

	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <case list>");
		for ( Case c : caseList )
			c.printParseTree(indent);
	}	
}
