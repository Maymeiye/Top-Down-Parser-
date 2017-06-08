import java.util.LinkedList;

class BoolTerm
{
	LinkedList<BoolPrimaryItem> boolPrimaryItemList;

	BoolTerm(LinkedList<BoolPrimaryItem> bpItemList)
	{
		boolPrimaryItemList = bpItemList;
	}
	void printParseTree(String indent)
	{
		
		String indent1 = indent + " ";
		IO.displayln(indent + indent.length() + " <boolTerm>");
		int i = 0;
		for ( BoolPrimaryItem t : boolPrimaryItemList )
		{
			if (i != 0) IO.displayln(indent1 + indent1.length() + " &&");
			t.printParseTree(indent+" ");
			i++;
		}
		
	}

}