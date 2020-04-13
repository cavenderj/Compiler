import java.io.*;
import java.util.*;

class IWParser
{
  int label = -1;
  static Scanner infile;
  static int temp = -1;
  static ArrayList<String> lines = new ArrayList<String>();
  static SymbolTable sym;
  static int intype, invalue, vartype, varvalue, useless;
  static Scanner kb;
  boolean notFlag = false;
  int max = 0;
  EIOParser eio;
  IWParser()
  {
  }
  public int caller (SymbolTable symTable) throws IOException
  {
    String returned;
    eio = new EIOParser();
    kb = new Scanner(System.in);
    sym = symTable;
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
    int type;
    while(!(intype == 111 && invalue == 16))
    {
      System.out.println("called from caller");
      parse(true);
    }
    print();
    return max;
  }
  public void parse(boolean flag) throws IOException
  {
    int type;
    String returned;
    System.out.println(intype + "   " + invalue + "  parse1");
    System.out.println(intype + "   " + invalue + "  parse2");
    if(intype < 20)
    {
      type = sym.index[intype].get(invalue).type;
      if(type == 108 || type == 102)
      {
        System.out.println("Error: cannot change the value of a constant");//throw error for using const
      }
      else if(type == 110)
        eio.arrayExp(sym.index[intype].get(invalue).name);
      else if (type < 20)
        System.out.println("Error: using undeclared variable");
      else
        returned = eio.exp(sym.index[intype].get(invalue).name, true);
    }
    else if (intype == 111)
    {
      if(invalue == 13)
        eio.parseCin();
      else if (invalue == 14)
        eio.parseCout();
      else if (invalue == 4)   //if invalue 4     else invalue = 5
        parseIf();
      else if (invalue == 6)   //while invalue 6
        parseWhile();
      else
      {
        System.out.println("Error: illegal use of reserved word");//throw error, using reserved word
      }
    }
    else
    {
      System.out.println("Invalid token");
      System.out.println(intype + "   " + invalue + "  printing line");
      while(!(intype == 107 && invalue == 59))
      {
        intype = infile.nextInt();
        invalue = infile.nextInt();//read pair
      }
      System.out.println("line read");
    }
    if(temp > max)
      max = temp;
    if (flag == true)
    {
      intype = infile.nextInt();
      invalue = infile.nextInt();
    }
    temp = -1;
    System.out.println("end of line temp set to -1");
    //useless = kb.nextInt();
  }
  public void parseWhile() throws IOException
  {
    String output;
    String t = newLabel();
    String f = newLabel();
    String begin = newLabel();
    intype = infile.nextInt();
    invalue = infile.nextInt();
    System.out.println("In while " + intype + ' ' + invalue);
    intype = infile.nextInt();
    invalue = infile.nextInt();
    System.out.println("In while " + intype + ' ' + invalue);
    lines.add(begin);
    String tem = parseOr();
    lines.add("==    " + tem + "    0    goto " + f);
    //lines.add(t);
    intype = infile.nextInt();
    invalue = infile.nextInt();
    if(intype == 107 && invalue == 123)
    {
      intype = infile.nextInt();
      invalue = infile.nextInt();
      System.out.println("There was a {");
      while(!(intype == 107 && invalue == 125))
      {
        System.out.println("parse was called (while)");
        parse(false);
        intype = infile.nextInt();
        invalue = infile.nextInt();
      }
    }
    else
    {
      System.out.println("There was not a {  while");
      parse(false);
    }
    output = "goto " + begin;
    lines.add(output);
    lines.add(f);
  }
  public void parseIf() throws IOException
  {
    String output;
    String t = newLabel();
    String f = newLabel();
    intype = infile.nextInt();
    invalue = infile.nextInt();
    System.out.println("In if 11 " + intype + ' ' + invalue);
    intype = infile.nextInt();
    invalue = infile.nextInt();
    System.out.println("In if 12 " + intype + ' ' + invalue);
    String tem = parseOr();
    lines.add("==    " + tem + "    0    goto " + f);
    intype = infile.nextInt();
    invalue = infile.nextInt();
    System.out.println("In if 2 " + intype + ' ' + invalue);
    //intype = infile.nextInt();
    //invalue = infile.nextInt();
    //System.out.println("In if 22 " + intype + ' ' + invalue);
    if(intype == 107 && invalue == 123)
    {
      System.out.println("There was a {");
      intype = infile.nextInt();
      invalue = infile.nextInt();
      while(!(intype == 107 && invalue == 125))
      {
        System.out.println("parse was called (if)");
        parse(false);
        intype = infile.nextInt();
        invalue = infile.nextInt();
      }
    }
    else
    {
      System.out.println("There was not a {  if");
      parse(false);
    }
    System.out.println("In if 31 " + intype + ' ' + invalue);
    intype = infile.nextInt();
    invalue = infile.nextInt();
    System.out.println("In if 32 " + intype + ' ' + invalue);
    if(intype == 111 && invalue == 5) // else
    {
      System.out.println("In if-else");
      String end = newLabel();
      lines.add("goto " + end);
      lines.add(f);
      System.out.println("f was added (else-if): " + f);
      intype = infile.nextInt();
      invalue = infile.nextInt();
      if(intype == 107 && invalue == 123)
      {
        intype = infile.nextInt();
        invalue = infile.nextInt();
        System.out.println("There was a {");
        while(!(intype == 107 && invalue == 125))
        {
          System.out.println("parse was called (else-if)");
          parse(false);
          intype = infile.nextInt();
          invalue = infile.nextInt();
        }
      }
      else
      {
        System.out.println("There was not a {  else-if");
        parse(false);
      }
      lines.add(end);
    }
    else
    {
      System.out.println("f was added (if): " + f);
      lines.add(f);
    }
  }
  public String parseOr()
  {
    boolean flag;
    String output;
    String tem1 = parseAnd();
    while(intype == 106 && invalue == 248)// or (||)
    {
      String tem = eio.newTemp();
      intype = infile.nextInt();
      invalue = infile.nextInt();
      String tem2 = parseAnd();
      lines.add("|    " + tem1 + "    " + tem2 + "    " + tem);
      tem1 = tem;
    }
    return tem1;
    
  }
  public String parseAnd()
  {
    String tem;
    System.out.println("In and");
    String output;
    tem = parseNot();
    if (notFlag == false)
    {
      System.out.println("There was no !");
      String tem1 = parseRelop();
      System.out.println("And " + intype + ' ' + invalue);
      while(intype == 106 && invalue == 76)
      {
        intype = infile.nextInt();
        invalue = infile.nextInt();
        tem = parseNot();
        if(notFlag == false)
        {
          System.out.println("There was not !");
          String tem2 = parseRelop();
          lines.add("&    " + tem1 + "    " + tem2 + "    " + tem);
          tem1 = tem;
        }
        else
        {
          lines.add("^    " + tem + "    1    " + tem);
          return tem;
        }
      }
      return tem1;
    }
    else 
    {
      lines.add("^    " + tem + "    1    " + tem);
      return tem;
    }
  }
  public String parseRelop()
  {
    String t = newLabel(); 
    String f = newLabel();
    String tem = eio.newTemp();
    System.out.println("In Relop");
    String output = "";
    String part1;
    String part2;
    output = eio.exp("", false);
    System.out.println("Relop " + intype + ' ' + invalue);
    String operator = "";
    if(invalue == 121)
      operator = "<=";
    else if (invalue == 123)
      operator = ">=";
    else if (invalue == 122)
      operator = "==";
    else 
      operator += (char)invalue;
    output = operator + "    " + output;
    intype = infile.nextInt();
    invalue = infile.nextInt();
    output = output + "    " + eio.exp("", false);
    lines.add(output + "  goto  " + t);
    lines.add("=    0        " + tem);
    lines.add("goto  " + f);
    lines.add(t);
    lines.add("=    1        " + tem);
    lines.add(f);
    return tem;
  }
  public String parseNot()
  {
    System.out.println("In Not");
    String output;
    if(invalue == 33)
    {
      System.out.println("There was a !");
      intype = infile.nextInt();
      invalue = infile.nextInt();
      intype = infile.nextInt();
      invalue = infile.nextInt();
      output = parseOr();
      intype = infile.nextInt();
      invalue = infile.nextInt();
      notFlag = true;
      return output;
    }
    else
      notFlag = false;
      return eio.newTemp();
  }
  public String newLabel()
  {
    label++;
    return ("L_" + label);
  }
  public void print()
  {
    String output;
     try
     {
       FileWriter writer = new FileWriter("code.dat");
       for(int i = 0; i < lines.size() - 1; i++)
       {
        // System.out.println("line printed: " + i);
         output = lines.get(i);
         writer.write(output);
         writer.write("\n");
       }
       writer.write(lines.get(lines.size() - 1));
       writer.close();
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
  }
}