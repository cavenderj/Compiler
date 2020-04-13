import java.io.*;
import java.util.*;

class CodeGen
{
  String operator, t1, t2, dest, going, lblnum = "";
  int lblcount = -1, lblsize, num;
  ArrayList<String> lines = new ArrayList<String>();
  public CodeGen()
  {
  }
  public void generate(SymbolTable sym, int max) throws IOException
  {
    boolean whlflag = false;
    int type;
    String output;
    Scanner infile = new Scanner(new FileInputStream ("code.dat"));
    lines.add("#include <iostream>");
    lines.add("using namespace std;\n");

    for(int i = 0; i < 20; i++)
    {
      for (int j = 0; j < sym.index[i].size(); j++)
      {
        type = sym.index[i].get(j).type;
        if(type == 103)  // int
        {
          output = "int " + sym.index[i].get(j).name + ";";
          lines.add(output);
        }
        else if (type == 102) //const int
        {
          output = "const int " + sym.index[i].get(j).name + ";";
          lines.add(output);
        }
        else if (type == 108) //const char
        {
          output = "const char " + sym.index[i].get(j).name + ";";
          lines.add(output);
        }
        else if (type == 109)  // char
        {
          output = "char " + sym.index[i].get(j).name + ";";
          lines.add(output);
        }
      }
    }
    lines.add("\n");
    for (int i = 0; i <= max; i++)
    {
      output = "_t_" + i + ';';
      lines.add("int " + output);
    }
    lines.add("\n");
    lines.add("int main() {");
    lines.add("\n");
   // int i = 1;
    while(infile.hasNextLine() == true)  
    {
      //System.out.println("line: " + i);
      //i++;
      operator = infile.next();
      if(operator.equals("+") || operator.equals("-") || operator.equals("/") || operator.equals("*") || operator.equals("|") || operator.equals("&") || operator.equals("^"))
      {
        t1 = infile.next();
        t2 = infile.next();
        dest = infile.next();
        output = dest + " = " + t1 +  ' ' + operator + ' ' + t2 + ';';
      }
      else if (operator.equals("&") ||operator.equals("|"))
      {
        t1 = infile.next();
        t2 = infile.next();
        dest = infile.next();
        output = dest + " = " + t1 +  ' ' + operator + operator + ' ' + t2 + ';';
      }
      else if (operator.equals("^"))
      {
        t1 = infile.next();
        t2 = infile.next();
        dest = infile.next();
        output = dest + " = !" + ';';
      }
      else if (operator.equals("="))
      {
        t1 = infile.next();
        dest = infile.next();
        output = dest + " = " + t1 + ';';
      }
      else if (operator.charAt(0) == 'L' && operator.charAt(1) == '_') //&& (operator.charAt(2) == '0' || operator.charAt(2) == '0' || operator.charAt(2) == '0'|| operator.charAt(2) == '0'|| operator.charAt(2) == '0'|| operator.charAt(2) == '0'|| operator.charAt(2) == '0'|| operator.charAt(2) == '0'|| operator.charAt(2) == '0'|| operator.charAt(2) == '0'
      {
        output = operator + ':';
      }
      else if(operator.equals("goto"))
      {
        t1 = infile.next();
        output = "goto " + t1 + ';';
      }
      else if (operator.equals("cin"))
      {
        t1 = infile.next();
        output = operator + " >> " + t1 + ';';
        System.out.println(output);
      }
      else if(operator.equals("cout"))
      {
        t1 = infile.nextLine();
        t1 = t1.trim();
        output = operator + " << " + t1 + ';';
        System.out.println(output);
      }
      else if (operator.equals("[]="))
      {
        operator = " = ";
        t2 = infile.next();
        t1 = infile.next();
        dest = infile.next();
        output = dest + '[' + t1 + ']' + operator + t2 + ';';
      }
      else if (operator.equals("=[]"))
      {
        operator = " = ";
        t2 = infile.next();
        t1 = infile.next();
        dest = infile.next();
        output = dest + operator + t2 + '[' + t1 + "];";
      }
      else
      {
        t1 = infile.next();
        t2 = infile.next();
        going = infile.next();
        going = going + ' ' + infile.next() + ';';
        output = "if(" + t1 + ' ' + operator + ' ' + t2 + ')';
        lines.add(output);
        output = "  " + going;
      }
      lines.add(output);
    }
    
    lines.add("\n");
    lines.add("return 0;");
    lines.add("\n");
    lines.add("}");
    print();
  }
  public void print()
  {
    String output;
     try
     {
       FileWriter writer = new FileWriter("fincode1.dat");
       for(int i = 0; i < lines.size(); i++)
       {
         //System.out.println("line printed: " + i);
         output = lines.get(i);
         writer.write(output);
         //System.out.println(output);
         writer.write("\n");
       }
       //writer.write(lines.get(lines.size() - 1));
       writer.close();
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
  }
}
  