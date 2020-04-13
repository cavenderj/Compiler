import java.io.*;
import java.util.*;

class EIOParser extends IWParser
{
  public EIOParser()
  {
  }
  
  public void parse() throws IOException
  {
    int useless;
    //System.out.println( sym.index[intype].get(invalue).name);
    infile  = new Scanner(new FileInputStream ("Output1.dat"));
    intype = infile.nextInt();
    invalue = infile.nextInt();
    while(!(intype == 111 && invalue == 9))
    {
      intype = infile.nextInt();
      invalue = infile.nextInt();
    }
    intype = infile.nextInt();
    invalue = infile.nextInt();
    intype = infile.nextInt();
    invalue = infile.nextInt();
    intype = infile.nextInt();
    invalue = infile.nextInt();
    intype = infile.nextInt();
    invalue = infile.nextInt();
    String returned;
    int type;
    while(!(intype == 111 && invalue == 16))
    {
    System.out.println(intype + "   " + invalue + "  parse1");
    System.out.println(intype + "   " + invalue + "  parse2");
    if(intype < 20)
    {
      type = sym.index[intype].get(invalue).type;
      if(type == 108 || type == 102)
      {
        intype = infile.nextInt();
        invalue = infile.nextInt();;//throw error for using const
      }
      else if(type == 110)
        arrayExp(sym.index[intype].get(invalue).name);
      else if (type < 20)
        System.out.println("Error: using undeclared variable");
      else
        returned = exp(sym.index[intype].get(invalue).name, true);
    }
    else if (intype == 111)
    {
      if(invalue == 13)
        parseCin();
      else if (invalue == 14)
        parseCout();
      else
      {
        System.out.println("Error: illegal use of reserved word");//throw error, using reserved word
      }
    }
    else
    {
      System.out.println(intype + "   " + invalue + "  printing line");
      while(!(intype == 107 && invalue == 59))
      {
        intype = infile.nextInt();
        invalue = infile.nextInt();//read pair
      }
      System.out.println("line read");
    }
    intype = infile.nextInt();
    invalue = infile.nextInt();
    temp = -1;
    System.out.println("end of line temp set to -1");
    //useless = kb.nextInt();
    }
    print();
  }
  
  
  public String exp(String variable, boolean flag)  //creates intermediate code
  {
    System.out.println("In exp " + flag);
    if(variable == "")
      ;
    else
    {
      intype = infile.nextInt();
      invalue = infile.nextInt();
      intype = infile.nextInt();
      invalue = infile.nextInt();
    }
    boolean flag2 = false;
    char operator;
    String label;
    String output;
    output = term();
    System.out.println(intype + "   " + invalue + "  exp " + flag);
    while(intype == 106 && (invalue == 43 || invalue == 45 || invalue == 37))
    {
      flag2 = true;
      System.out.println("in exp loop " + flag);
      operator = (char)invalue;
      intype = infile.nextInt();
      invalue = infile.nextInt();
      while(intype == 106)
      {
        System.out.println("Error: too many operators");
        intype = infile.nextInt();
        invalue = infile.nextInt();
      }
      output = operator + "    " + output + "    " + term() + "    ";
      label = newTemp();
      output = output + label;
      lines.add(output);
      output = label;
      System.out.println(intype + "   " + invalue + "  exp " + flag);
      //useless = kb.nextInt();
    }
    
    if(variable == "")
    {
      if(flag == false)
        variable = output;
      else
        variable = newTemp();
    }
    if(flag == true)
    {
      System.out.println("Assignment statement made");
      output = "=    " + output + "    " + "    " + variable;
      lines.add(output);
      return variable;
    }
    else
    {
      System.out.println("Returns: " + variable);
      return variable;
    }   
  }
  
  
  public String term() //creates intermediate code
  {
    System.out.println("In term");
    boolean flag = false;
    char operator;
    String label = "";
    String output;
    output = factor();
    //intype = infile.nextInt();
    //invalue = infile.nextInt();
    if((intype != 106 ) && (!(intype == 107 && (invalue == 59 || invalue == 41))))
    {
      System.out.println("Error: no operator used");
    }
    System.out.println(intype + "   " + invalue + "  term");
    while(intype == 106 && (invalue == 42 || invalue == 47))
    {
      System.out.println("in term loop");
      operator = (char)invalue;
      intype = infile.nextInt();
      invalue = infile.nextInt();
      while(intype == 106)
      {
        System.out.println("Error: too many operators");
        intype = infile.nextInt();
        invalue = infile.nextInt();
      }
      output = operator + "    " + output + "    " + factor() + "    ";
      label = newTemp();
      output = output + label;
      lines.add(output);
      output = label;
      System.out.println(intype + "   " + invalue + "  term");
      //useless = kb.nextInt();
    }
    if(flag == false)
      return output;
    else
      return label;
  }
  
  
  public String factor() //returns string?
  {
    System.out.println("In factor");
    String output;
    if(intype == 107 && invalue == 40)
    {
      System.out.println("factor calls exp");
      intype = infile.nextInt();
      invalue = infile.nextInt();
      output = exp("",false);
      intype = infile.nextInt();
      invalue = infile.nextInt();
      if (!(intype == 107 && invalue == 41))
      {
        System.out.println("Error: no closing ')'");//throw error for no closing )
      }
      return output;
    }
    else if(intype < 20)
    {
      return id();
    }
   // else if(intype == 103)
    //{
      //Character tempC = (char)invalue;
      //intype = infile.nextInt();
      //invalue = infile.nextInt();
      //System.out.println(tempC);
      //return tempC.toString();//a num or char value
    //}
    else if(intype == 105 || intype == 103)
    {
      System.out.println(invalue);
      Integer tempI = invalue;
      intype = infile.nextInt();
      invalue = infile.nextInt();
      return tempI.toString();
    }
    else if (intype == 106 && invalue == 45)
    {
      intype = infile.nextInt();
      invalue = infile.nextInt();
      System.out.println(intype + "   " + invalue  + "  factor");
      output = factor();
      String label = newTemp();
      output = "-    " + output + "        " + label;
      lines.add(output);
      return label;
    }
    else
    {
      System.out.println(intype + "   " + invalue  + "  factor");
      System.out.println("Invalid token");//throw error, invalid token
    }
    return "";
  }
  
  
  public String id()   //returns string?
  {
    System.out.println("In id");
    if(sym.index[intype].get(invalue).type == 110)//access symbol table and check if type == 110
      return arraySolve();
    else if (sym.index[intype].get(invalue).type > 20)
    {
      System.out.println(sym.index[intype].get(invalue).name);
      vartype = intype;
      varvalue = invalue;
      intype = infile.nextInt();
      invalue = infile.nextInt();
      return sym.index[vartype].get(varvalue).name;//its a normal identifier
    }
    else
      System.out.println("Error: using undeclared identifier");
    return "";
  }
  
  
  public void arrayExp(String variable)
  {
    System.out.println("In arrayExp");
    String output = "";
    String label;
    intype = infile.nextInt();
    invalue = infile.nextInt();//read pair
    if(intype == 107 && invalue == 91)
    {
      intype = infile.nextInt();
      invalue = infile.nextInt();
      output = exp("", true);
      //intype = infile.nextInt();
      //invalue = infile.nextInt();
      //label = newTemp();
      if(!(intype == 107 && invalue == 93))
        System.out.println("insert closing ]");
      else
      {
        System.out.println("token read");
        intype = infile.nextInt();
        invalue = infile.nextInt();
      }
    }
    else
      System.out.println("missing [");//throw error, missing []
    intype = infile.nextInt();
    invalue = infile.nextInt();
    output = exp("", false) + "    " + output;
    output = "[]=    " + output + "    " + variable;
    lines.add(output);
    ;//print label to assign temp to index of array
  }
  
  
  public String arraySolve()
  {
    System.out.println("In arraySolve");
    String variable = sym.index[intype].get(invalue).name;
    String output, label;
    intype = infile.nextInt();
    invalue = infile.nextInt();//read pair
    System.out.println(intype + "   " + invalue + "  arraySolve");
    if(intype == 107 && invalue == 91)
    {
      intype = infile.nextInt();
      invalue = infile.nextInt(); 
      output = "=[]    " + variable;
      output = output + "    " + exp("", true);
      if(!(intype == 107 && invalue == 93))
        System.out.println("insert closing ]");
      else
      {
        System.out.println("token read");
        intype = infile.nextInt();
        invalue = infile.nextInt();
      }
      label = newTemp();
      output = output + "    " + label;
      lines.add(output);//print labels for index
      return label;
    }
    else
      System.out.println("missing [");//throw error, missing []
    return "";
  }
  
  
  public void parseCin()
  {
    System.out.println("In cin");
    String output;
    intype = infile.nextInt();
    invalue = infile.nextInt();//read pair
    while(invalue == 124)
    {
      intype = infile.nextInt();
      invalue = infile.nextInt();//read pair
     /* if(intype == 32)
      {
        output = sym.index[intype - 20].get(invalue).name;//pull from symbol table
        output = "cin\t" + output;
        lines.add(output);
        System.out.println(intype + "   " + invalue);
        intype = infile.nextInt();
        invalue = infile.nextInt();
        System.out.println(intype + "   " + invalue);
      }*/
      //else
      //{
        System.out.println("CIN calls exp");
        output = exp("", false);
        output = "cin    " + output;
        lines.add(output);
     // }
      //intype = infile.nextInt();
      //invalue = infile.nextInt();//read pair
    }
    if(invalue != 59)
    {
      System.out.println("Error: missing ';'"); // throw error
    }
  }
  
  
  public void parseCout()
  {
    System.out.println("In COUT");
    String output;
    intype = infile.nextInt();
    invalue = infile.nextInt();//read pair
    while(invalue == 120)
    {
      System.out.println("looping COUT");
      intype = infile.nextInt();
      invalue = infile.nextInt();//read pair
      if(intype == 32)
      {
        output = sym.index[intype - 20].get(invalue).name;//pull from symbol table
        System.out.println(output);
        output = "cout    " + '"' +output + '"';
        lines.add(output);
        intype = infile.nextInt();
        invalue = infile.nextInt();//read pair
      }
      else if (intype == 111 && invalue == 12)
      {
        output = "cout    " + "'\\" + "n'";
        lines.add(output);
        System.out.println("endl hit");
        System.out.println(intype + "   " + invalue + "  cout1");
        intype = infile.nextInt();
        invalue = infile.nextInt();
        System.out.println(intype + "   " + invalue + "  cout2");
      }
      else if (intype == 103 && invalue == 32)
      {
        output = "cout    ' '";
        lines.add(output);
        intype = infile.nextInt();
        invalue = infile.nextInt();
      }
      else
      {
        System.out.println("COUT calls exp");
        System.out.println(intype + "   " + invalue + "  cout");
        output = exp("",false);
        System.out.println(intype + "   " + invalue + "  cout post exp");
        output = "cout    " + output;
        lines.add(output);
      }
    }
    if(invalue != 59)
    {
      System.out.println(intype + "   " + invalue + "  cout");
      System.out.println("Error: missing ';'"); // throw error
    }
  }
  
  public String newTemp()
  {
    temp++;
    return("_t_" + temp);
  }
}