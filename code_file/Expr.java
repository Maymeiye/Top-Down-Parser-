import java.util.LinkedList;

class Expr
{
	LinkedList<BoolTermItem> booltermItemList;

	Expr(LinkedList<BoolTermItem> btItemList)
	{
		booltermItemList = btItemList;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		IO.displayln(indent + indent.length() + " <expr>");
		
		int i = 0;
		for ( BoolTermItem t : booltermItemList )
		{
			if (i != 0) IO.displayln(indent1 + indent1.length() + " ||");
			t.printParseTree(indent+" ");
			i++;
		}
	}	
}