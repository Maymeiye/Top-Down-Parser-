
/**

This class is a top-down, recursive-descent parser for the following syntactic categories:

⟨statement⟩ → ⟨assignment⟩ | ⟨cond⟩ | ⟨switch⟩ | ⟨while loop⟩ | ⟨do loop⟩ | ⟨for loop⟩ | ⟨print⟩ | ⟨block⟩ 
⟨assignment⟩ → ⟨id⟩ "=" ⟨expr⟩ ";" 
⟨cond⟩ → "if" "(" ⟨expr⟩ ")" ⟨statement⟩ [ "else" ⟨statement⟩ ] 
⟨switch⟩ → "switch" "(" ⟨expr⟩ ")" "{" ⟨case list⟩ "}" 
⟨case list⟩ → { ⟨case⟩ }+ 
⟨case⟩ → "case" ⟨label⟩ ":" ⟨s list⟩ | "default" ":" ⟨s list⟩ 
⟨label⟩ → ⟨int⟩ 
⟨while loop⟩ → "while" "(" ⟨expr⟩ ")" ⟨statement⟩ 
⟨do loop⟩ → "do" ⟨statement⟩ "while" "(" ⟨expr⟩ ")" ";" 
⟨for loop⟩ → "for" "(" ⟨assign⟩ ";" ⟨expr⟩ ";" ⟨assign⟩ ")" ⟨statement⟩ 
⟨assign⟩ → ⟨id⟩ "=" ⟨expr⟩ 
⟨print⟩ → "print" ⟨expr⟩ ";" 
⟨block⟩ → "{" ⟨s list⟩ "}" 
⟨s list⟩ → { ⟨statement⟩ } 
⟨expr⟩ → ⟨boolTerm⟩ { "||" ⟨boolTerm⟩ } 
⟨boolTerm⟩ → ⟨boolPrimary⟩ { "&&" ⟨boolPrimary⟩ } 
⟨boolPrimary⟩ → ⟨E⟩ [ ⟨rel op⟩ ⟨E⟩ ] 
⟨rel op⟩ → "<" | "<=" | ">" | ">=" | "==" | "!=" 
⟨E⟩ → ⟨term⟩ { (+|−) ⟨term⟩ } 
⟨term⟩ → ⟨primary⟩ { (*|/) ⟨primary⟩ } 
⟨primary⟩ → ⟨id⟩ | ⟨int⟩ | ⟨float⟩ | ⟨floatE⟩ | ⟨boolLiteral⟩ | "(" ⟨expr⟩ ")" | − ⟨primary⟩ | ! ⟨primary⟩ 
⟨boolLiteral⟩ → "false" | "true" 

Note: The binary operators +, -, *, / associate to left.

The definitions of the tokens are given in the lexical analyzer class file "LexArithArray.java". 

The following variables/functions of "IO"/"LexArithArray" classes are used:

static void display(String s)
static void displayln(String s)
static void setIO(String inFile, String outFile)
static void closeIO()

static void setLex()
static String t // holds an extracted token
static State state // the current state of the finite automaton
static int getToken() // extracts the next token

An explicit parse tree is constructed in the form of nested class objects.
 
The main function will display the parse tree in linearly indented form.
Each syntactic category name labeling a node is displayed on a separate line, 
prefixed with the integer i representing the node's depth and indented by i blanks.

All iterations of the form { ... } and { ... }+ are implemented by java.util.LinkedList.
Function call list.add(x) appends element x to the end of list. 

**/

import java.util.*;

//import LexAnalyzer.State;

public abstract class Parser extends LexAnalyzer
{
	static boolean errorFound = false;


	public static SList sList()
	
	// ⟨s list⟩ → { ⟨statement⟩ } 
	{
		LinkedList<Statement> sList = new LinkedList<Statement>();

		Statement statement;
		//sList.add(statement);
		while ( state == State.Id ||
				state == State.Keyword_if ||
				state == State.Keyword_switch ||
				state == State.Keyword_while ||
				state == State.Keyword_do ||
				state == State.Keyword_for ||
				state == State.Keyword_print || 
				state == State.LBrace
				 )
		{
			statement = statement();
			sList.add(statement);

		}
		return new SList(sList);
	}

	//⟨statement⟩ → ⟨assignment⟩ | ⟨cond⟩ | ⟨switch⟩ | ⟨while loop⟩ | ⟨do loop⟩ | ⟨for loop⟩ | ⟨print⟩ | ⟨block⟩ 
	public static Statement statement()
	{
		switch ( state )
		{
			case Id:						
				return new Statement(assignment());	
			case Keyword_if:
				return new Statement(cond());
			case Keyword_switch:
				return new Statement(Switch());
			case Keyword_while:
				return new Statement(whileLoop());
			case Keyword_do:
				return new Statement(doLoop());
			case Keyword_for:
				return new Statement(forLoop());			
			case Keyword_print:
				return new Statement(print());		
			case LBrace:
				return new Statement(block());	
			default:
				errorMsg(2);
				return null;
		}		
	}
	
	
    public static Assignment assignment()
	
	// <assignment> --> <id> = <expr> ";"
	
	{
		if ( state == State.Id )
		{
			String id = t;
			getToken();
			if ( state == State.Assign )
			{
				getToken();
				Expr e = expr();
				if ( state == State.Semicolon )
				{
					getToken();
					return new Assignment(id, e);
				}
				else
					errorMsg(4);
			}
			else
				errorMsg(3);
		}
		else
			errorMsg(5);
		return null;
	}
 
    public static Cond cond()
   	// ⟨cond⟩ → "if" "(" ⟨expr⟩ ")" ⟨statement⟩ [ "else" ⟨statement⟩ ] 
   	
   	{
   		if ( state == State.Keyword_if )
   		{
   			//String id = t;
   			getToken();
   			if ( state == State.LParen )
   			{
   				getToken();
   				Expr e = expr();
   				if ( state == State.RParen )
   				{
   					getToken();
   					Statement s = statement();
   					if(state == State.Keyword_else)
   					{
   						getToken();
   						Statement s2 = statement();
   						return new Cond(e,s,s2);
   					}
   					else return new Cond(e,s);
   				}
   				else
   					errorMsg(1);
   			}
   			else
   				errorMsg(2);
   		}
   		else
   			errorMsg(8);
   		return null;
   	}
   /*-------------------------------------------------------------------------*/
    
    public static Switch Switch()
    //⟨switch⟩ → "switch" "(" ⟨expr⟩ ")" "{" ⟨case list⟩ "}" 
    {
   		if ( state == State.Keyword_switch )
   		{
   			getToken();
   			if ( state == State.LParen )
   			{
   				getToken();
   				Expr e = expr();
   				if ( state == State.RParen )
   				{
   					getToken();
   					if ( state == State.LBrace )
   					{
   						getToken();
   						CaseList cl = caseList();
   						if ( state == State.RBrace )
   						{
   							getToken();
   							return new Switch(e,cl);
   						}
   						else
   							errorMsg(7);	
   					}
   					else
   					errorMsg(6);
   				}
   				else
   				errorMsg(1);
   			}
   			else
   				errorMsg(2);
   		}
   		else
   			errorMsg(8);
   			return null;
   	}

    public static CaseList caseList()
    // ⟨case list⟩ → { ⟨case⟩ }+ 
 	{
 		LinkedList<Case> caseList = new LinkedList<Case>();

 		Case Case = Case();
 		caseList.add(Case);
 		while ( state == State.Keyword_case || state == State.Keyword_default)
 		{
 			Case = Case();
 			caseList.add(Case); 
 		}
 		return new CaseList(caseList);
 	}
	
    public static Case Case()
    // ⟨case⟩ → "case" ⟨label⟩ ":" ⟨s list⟩ | "default" ":" ⟨s list⟩ 
 	{
    		if ( state == State.Keyword_case)
    		{
			getToken();
			Label label = label();
			if ( state == State.Colon )
			{
				getToken();
				SList sList = sList();
				return new Case(label,sList);
			}
			else errorMsg(8);	
    		}
    	
    		else if(state == State.Keyword_default)
     	{
    			getToken();
			if ( state == State.Colon )
			{
				getToken();
				SList sList = sList();
				return new Case(sList);
			}
			else
				errorMsg(4);
     	}
		else
			errorMsg(8);
			return null;
 	}
    

    public static Label label()
    {
    	
    		if(state == State.Int)
    		{
    			Int2 intElem = new Int2(Integer.parseInt(t));
			getToken();
			return intElem;	
    		}

    	else
			errorMsg(2);
			return null;	
    }
     
    /*-------------------------------------------------------------------------*/  
    public static WhileLoop whileLoop()
   // ⟨while loop⟩ → "while" "(" ⟨expr⟩ ")" ⟨statement⟩ 
    {
   		if ( state == State.Keyword_while )
   		{
   			getToken();
   			if ( state == State.LParen )
   			{
   				getToken();
   				Expr e = expr();
   				if ( state == State.RParen )
   				{
   					getToken();
   					Statement s = statement();
   					return new WhileLoop(e,s);
   							
   				}
   				else
   					errorMsg(6);
   				}
   			else
   				errorMsg(1);
   		}
   		else
   			errorMsg(2);
   		return null;
   	}
   		
    public static DoLoop doLoop()
    // ⟨do loop⟩ → "do" ⟨statement⟩ "while" "(" ⟨expr⟩ ")" ";" 
     {
    		if ( state == State.Keyword_do )
    		{
    			getToken();
    			Statement s = statement();
    			if ( state == State.Keyword_while )
    			{
    				getToken();
    				if ( state == State.LParen )
    				{
    					getToken();
    					Expr e = expr();
    					if ( state == State.RParen )
    					{
    						getToken();
    						if ( state == State.Semicolon )
    						{
    							getToken();
    							return new DoLoop(s,e);	
    						}
    						else errorMsg(4);
    					}
    					else errorMsg(1);
    				}
    				else errorMsg(2);
    			}
    			else errorMsg(8);
    				
    		}
    		else
    			errorMsg(8);
    			return null;
    	}
    
    public static ForLoop forLoop()
    // ⟨for loop⟩ → "for" "(" ⟨assign⟩ ";" ⟨expr⟩ ";" ⟨assign⟩ ")" ⟨statement⟩ 
    {
   		if ( state == State.Keyword_for )
   		{
   			//String id = t;
   			getToken();
   			if ( state == State.LParen )
   			{
   				getToken();
   				Assign a = assign();
   				if(state == State.Semicolon)
   				{
   					getToken();
   					Expr e = expr();
   					if(state == State.Semicolon)
   	   				{
   	   					getToken();
   	   					Assign as = assign();
   	   					if ( state == State.RParen )
   	   					{
   	   						getToken();
   	   						Statement s = statement();
   	   						return new ForLoop(a,e,as,s);	
   	   					}
   	   					else
   	   						errorMsg(1);			
   	   				}
   					else 
   						errorMsg(4);	
   				}
   				else 
						errorMsg(4);
   			}
   			else 
				errorMsg(2);
   		}
   		else 
   			errorMsg(8);
   			return null;				
   	}
    
    public static Assign assign()
    // ⟨assign⟩ → ⟨id⟩ "=" ⟨expr⟩
    
     	{
     		if ( state == State.Id )
     		{
     			String id = t;
     			getToken();
     			if ( state == State.Assign )
     			{
     				getToken();
     				Expr expr = expr();
     				return new Assign(id, expr); 
     			}
     			else
     				errorMsg(3);
     		}
     		else
     			errorMsg(5);
     		return null;
     	}

    public static Print print()
    {
    	//⟨print⟩ → "print" ⟨expr⟩ ";" 
			if ( state == State.Keyword_print )
			{
				getToken();
				Expr e = expr();
				if ( state == State.Semicolon )
				{
					getToken();
					return new Print(e);
				}
				else
					errorMsg(4);
			}
			else
				errorMsg(8);
				return null;	
    	
    }
  
    public static Block block()
	
	// ⟨block⟩ → "{" ⟨s list⟩ "}"
	
	{
    	if ( state == State.LBrace )
		{
			getToken();
			SList s = sList();
			if ( state == State.RBrace )
			{
				getToken();
				return new Block(s);
			}
			else
				errorMsg(7);
		}
		else
			errorMsg(6);
			return null;
	}
 
/*------------------------------------------------------------*/
	
    public static Expr expr()
	//⟨expr⟩ → ⟨boolTerm⟩ { "||" ⟨boolTerm⟩ } 
	{
		LinkedList<BoolTermItem> booltermItemList = new LinkedList<BoolTermItem>();

		BoolTerm boolterm = boolTerm();
		booltermItemList.add(new MultiBoolTerm(boolterm));
		while ( state == State.Or )
		{
			getToken();
			boolterm = boolTerm();
			booltermItemList.add(new MultiBoolTerm(boolterm));
			
		}
		return new Expr(booltermItemList);
	}
	
	public static BoolTerm boolTerm()
	//⟨boolTerm⟩ → ⟨boolPrimary⟩ { "&&" ⟨boolPrimary⟩ } 
	{
		LinkedList<BoolPrimaryItem> boolprimaryItemList = new LinkedList<BoolPrimaryItem>();

		BoolPrimary boolprimary = boolPrimary();
		boolprimaryItemList.add(new MultiBoolPrimary(boolprimary));
		while ( state == State.And )
		{
			getToken();
			boolprimary = boolPrimary();
			boolprimaryItemList.add(new MultiBoolPrimary(boolprimary));
		}
		return new BoolTerm(boolprimaryItemList);
	}
	
	public static BoolPrimary boolPrimary()
	{
		//⟨boolPrimary⟩ → ⟨E⟩ [ ⟨rel op⟩ ⟨E⟩ ] 
		//⟨rel op⟩ → "<" | "<=" | ">" | ">=" | "==" | "!=" 
		E e_left = E();
		
		if ( state == State.Lt || state == State.Le || state == State.Gt || state == State.Ge || state == State.Eq || state == State.Neq )
		{
            getToken();
            E e_right = E();
            return new BoolPrimary(e_left, e_right, state);
		}
		else return new BoolPrimary(e_left);
		
	}
		
/*-----------------------------------------------------       */
	
	
	public static E E()

	// <E> --> <term> { (+|-) <term> }

	{
		LinkedList<TermItem> termItemList = new LinkedList<TermItem>();

		Term term = term();
		termItemList.add(new SingleTermItem(term));
		while ( state == State.Add | state == State.Sub )
		{
			State op = state;
			getToken();
			term = term();
			if ( op == State.Add )
				termItemList.add(new AddTermItem(term));
			else // op == State.Minus
				termItemList.add(new SubTermItem(term));
		}
		return new E(termItemList);
	}

	public static Term term()

	// <term> --> <primary> { (*|/) <primary> }

	{
		LinkedList<PrimaryItem> primaryItemList = new LinkedList<PrimaryItem>();

		Primary primary = primary();
		primaryItemList.add(new SinglePrimaryItem(primary));
		while ( state == State.Mul | state == State.Div )
		{
			State op = state;
			getToken();
			primary = primary();
			if ( op == State.Mul )
				primaryItemList.add(new MulPrimaryItem(primary));
			else // op == State.Div
				primaryItemList.add(new DivPrimaryItem(primary));
		}
		return new Term(primaryItemList);
	}
	
	public static Primary primary()

	// <primary> --> <id> | <int> | <float> | <floatE> | <boolLiteral>| "(" <expr> ")"| -<primary>| *<primary>

	{
		switch ( state )
		{
			case Id:
										
				Id id = new Id(t);
				getToken();
				return id;
				
			case Int:

				Int intElem = new Int(Integer.parseInt(t));
				getToken();
				return intElem;

			case Float: case FloatE:

				Floatp floatElem = new Floatp(Float.parseFloat(t));
				getToken();
				return floatElem;
			
			case Keyword_false:
			case Keyword_true:
			    BoolLiteral boolLit = new BoolLiteral(Boolean.parseBoolean(t));
			    getToken();
			    return boolLit;
				
			case LParen:
				
				getToken();
				Expr expr = expr();
				if ( state == State.RParen )
				{
					getToken();
					Parenthesized paren = new Parenthesized(expr);
					return paren;
				}
				else
				{
					errorMsg(1);
					return null;
				}
				
			case Sub:
				getToken();
				SubPrimary subp = new SubPrimary(primary());
				return subp;
			case Inv:
				getToken();
				InvPrimary invp = new InvPrimary(primary());
				return invp;
				
			default:

				errorMsg(2);
				return null;
		}
	}
		
	public static void errorMsg(int i)
	{
		errorFound = true;
		
		display(t + " : Syntax Error, unexpected symbol where");

		switch( i )
		{
			case 1:	displayln(" arith op or ) expected"); return;
			case 2: displayln(" id, int, float, or ( expected"); return;
			case 3:	displayln(" = expected"); return;
			case 4:	displayln(" ; expected"); return;
			case 5:	displayln(" id expected"); return;
			case 6:	displayln(" { expected"); return;
			case 7:	displayln(" } expected"); return;
			case 8:	displayln(" keyword_print, Keyword_if, Keyword_else, Keyword_switch, Keyword_case,Keyword_default,Keyword_while,Keyword_do,Keyword_for,Keyword_false,or Keyword_true, expected"); return;
		
		}
	}

	public static void main(String argv[])
	{
		// argv[0]: input file containing an assignment list
		// argv[1]: output file displaying the parse tree
		
		setIO( argv[0], argv[1] );
		
		setLex();

		getToken();
		
		Statement statement = statement(); // build a parse tree
	
		if ( ! t.isEmpty() )
			errorMsg(5);
		else if ( ! errorFound )
		{
			//System.out.println("a");
			statement.printParseTree(""); // print the parse tree in linearly indented form in argv[1] file
		}
		closeIO();
	}
}
