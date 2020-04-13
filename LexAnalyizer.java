import java.io.*;
import java.util.ArrayList;

class LexAnalyizer
{
  ArrayList<String> names = new ArrayList<String>();
  ArrayList<Integer> types = new ArrayList<Integer>();
  ArrayList<Integer> values = new ArrayList<Integer>();
  SymbolTable symTable = new SymbolTable();
  int flag = 0;
  String name = "";
  int typeflag = 0;
  int type;
  int val;
  int resval;
  public LexAnalyizer(SymbolTable sym)
  {
    symTable = sym;
  }
  public void reader()
  {
    int commentflag = 0;
    char temp;
    int i;
    int line = 1;
    try{
    FileInputStream infile = new FileInputStream ("Input1.dat");
    while((i=infile.read())!=-1)
    {
      while ((char)i == ' ')
      {
        if (typeflag == 2 || typeflag == 1)
          tokenizer((char)i, line);
        else if (name != "")
          create();
        i = infile.read();
      }
      if ((char)i =='\n' || i == 13)
      {
        if (typeflag == 1)
          System.out.println("Error: no closing ' on line: " + line);
        else if (typeflag == 2)
          System.out.println("Error: no closing " + '"' + " on line: " + line);
        line++;
      }
      else
      {
        if ((char)i == '\t')
          break;
        else if ((char) i == '/')
        {
          temp = '/';
          i = infile.read();
          if ((char)i == '/')
          {
            while ((char)i != '\n' || i == 13)
            {
              i = infile.read();
            }
          }
          else if ((char)i == '*')
          {
            i = infile.read();
            temp = (char)i;
            i = infile.read();
            while(commentflag == 0)
            {
              if(temp == '*' &&  (char)i == '/')
                commentflag = 1;
              else
              {
                temp = (char)i;
                i = infile.read();
              }
            }
          }
          else
            tokenizer(temp, line);
        }
        else
        {
          tokenizer((char)i, line);
        }
      }
    }
    infile.close();
    }
    catch(Exception e)
    {
      System.out.println(e);
    }
    print();
    symTable.print();
  }
  public void tokenizer(char a, int line)
  {
    int code;
    code = findType(a);
    if (typeflag == 3)
    {
      if (code != 6)
        create();
    }
    else if(typeflag == 4)
    {
      if (code == 3 || code == 5)
      {
      }
      else
      {
        create();
      }
    }
    else if (typeflag == 1)
    {
      name = name + a;
      checkChar();
      typeflag = 11;
    }
    if(typeflag == 2 && a != '"')
      name = name + a;
    else
    {
      if (code == 0)
        System.out.println("Error, invalid symbol on line " + line);
      else if (code == 7)//puncuation
      {     
        if ( a == '\'')
        {
          if (typeflag == 11)
            typeflag = 0;
          else
          {
          typeflag = 1;
          }
        }
        else if (a == '"')
        {
          if (typeflag != 2)
            typeflag = 2;
          else
          {
            create();
            typeflag = 0;
          }
        }
        else
        {
        name = name + a;
        create();
        }
      }
      else if (code == 5)  //int
      {
        typeflag = 4;
        name = name + a;   
      }
      else if (code == 6)  //operator
      {
        name = name + a;
        typeflag = 3;
      }
      else if (code == 3 && typeflag != 11)  //character
      {
        typeflag = 4;
        name = name + a; 
      }
    }
  }

  
  private int findType(char a)
  {
    if (a == '$' || a == '@' || a == '~' || a == '`' || a == '?' || a == '^')
    {
      System.out.println("Invalid character: " + a);
      return 0;    //invalid characters
    }
    else if (a == ' ')
      return 1;
    else if (a == '1' || a == '2' || a == '3' || a == '4' || a == '5' || a == '6' || a == '7' || a == '8' || a == '9' ||a == '0')
      return 5;     //int
    else if (a == '+' || a == '-' || a == '*' || a == '/' || a == '=' || a == '<' || a == '>' || a == '!' || a == '&' ||a == '|' || a == '%')
      return 6;    //operator
    else if (a == ',' || a == ';' || a == '{' || a == '}' || a == '[' || a == ']' || a == '.'|| a == '#' || a == ':' || a == '"' || a == '(' || a == ')' || a == '\'')
      return 7;    //puncuation
    else
      return 3;    //letter
  }
  
   private boolean checkRes()
  {
    if (name.charAt(0) == 'e')
    {
      if (name.equals("else"))
      {
        resval = 5;
        return true;
      }
      else if (name.equals("endl"))
      {
        resval = 12;
        return true;
      }
    }
    else if (name.charAt(0) == 'i')
    {
      if (name.equals("int"))
      {
        resval = 1;
        return true;
      }
      else if (name.equals("if"))
      {
        resval = 4;
        return true;
      }
      else if (name.equals("include"))
      {
        resval = 10;
        return true;
      }
      else if (name.equals("iostream"))
      {
        resval = 11;
        return true;
      }
    }
    else if (name.charAt(0) == 'c')
    {
      if (name.equals("char"))
      {
        resval = 2;
        return true;
      }
      else if (name.equals("const"))
      {
        resval = 3;
        return true;
      }
      else if (name.equals("cout"))
      {
        resval = 14;
        return true;
      }
      else if (name.equals("cin"))
      {
        resval = 13;
        return true;
      }
    }
    else if (name.charAt(0) == 'g')
    {
      if (name.equals("goto"))
        {
        resval = 15;
        return true;
        }
    }
    else if (name.charAt(0) == 'm')
    {
      if (name.equals("main"))
        {
        resval = 9;
        return true;
        }
    }
    else if (name.charAt(0) == 'n')
    {
      if (name.equals("namespace"))
      {
        resval = 18;
        return true;
      }
    }
    else if (name.charAt(0) == 's')
    {
      if (name.equals("std"))
      {
        resval = 19;
        return true;
      }
    }
    else if (name.charAt(0) == 'v')
    {
      if (name.equals("void"))
        {
        resval = 8;
        return true;
        }
    }
    else if (name.charAt(0) == 'w')
    {
      if (name.equals("while"))
      {
        resval = 6;
        return true;
      }
    }
    else if (name.charAt(0) == 'r')
    {
      if (name.equals("return"))
      {
        resval = 16;
        return true;
      }
    }
    else if (name.charAt(0) == 'u' ) 
    {
      if (name.equals("using"))
      {
        resval = 17;
        return true;
      }
    }
    return false;
  }
   
   private void create()
   {
     if(typeflag == 1)
     {
       type = 3;
       checkChar();
     }
     else if (typeflag == 2)
     {
       type = 32;
       checkString();
     }
     else
       type = findType(name.charAt(0));
     if(type == 3)
     {
       type = 2;
       checkId();
     }
     else if (type == 5)
       checkInt();
     else if (type == 6)
       checkOp();
     else if (type == 7)
       checkPun();
   }
   private void print()
   {
     String output;
     try
     {
       FileWriter writer = new FileWriter("Output1.dat");
       for(int i = 0; i < names.size(); i++)
       {
         output = types.get(i) + "   " + values.get(i);
         //output = types.get(i) + "   " + values.get(i) + "   " + names.get(i);
         writer.write(output);
         writer.write("\n");
       }
       writer.close();
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
   private void clear()
   {
     name = "";
     type = 0;
     flag = 0;
     val = 0;
     if (typeflag == 1 || typeflag == 2)
     {
     }
     else
       typeflag = 0;
   }
   private void checkId()
   {
     int holder;
     int flag = 0;
     int code;
     if(checkRes() == true) 
     {
       type = 111;
       val = resval;
       holder = symTable.hash(name,type);
       names.add(name);
       values.add(val);
       types.add(type);
       clear();
       type = 111;
     }
     else 
     {
       for(int i = 0; i < name.length();i++)
       {
         code = findType(name.charAt(i));
         if (code == 3 || code == 5)
         {
         }
         else
         {
           flag = 1;
           System.out.println("Error not int char  5  3  " + code);
           i = name.length();
           name = "";
         }
       }
     }
     if (flag == 0 && type != 111)
     {
       type = name.charAt(0)% 20;
       val = symTable.hash(name,type);
       names.add(name);
       values.add(val);
       types.add(type);
       clear();
     }
   }
   private void checkInt()
   {
     int code;
     for(int i = 0; i < name.length();i++)
     {
       code = findType(name.charAt(i));
        if (code != 5)
       {
         flag = 1;
         System.out.println("Error not int char  5  " + code);
         i = name.length();
         name = "";
       }
     }
     if (flag == 0)
     {
       val = Integer.parseInt(name);
       names.add(name);
       values.add(val);
       types.add(type + 100);
       clear();
     }
   }
   private  void checkOp()
   {
     int code;
     for(int i = 0; i < name.length();i++)
     {
       code = findType(name.charAt(i));
        if (code != 6)
       {
         flag = 1;
         System.out.println("Error bad char   6   " + code);
         i = name.length();
         name = "";
       }
     }
     if (flag == 0)
     {
       if(name.length() > 1)
         val = ((int)name.charAt(0)) + ((int)name.charAt(1));
       else
         val = (int)name.charAt(0);
       names.add(name);
       values.add(val);
       types.add(type + 100);
       clear();
     }
   }
   private void checkString()
   {
     val = symTable.hash(name,type);
     names.add(name);
     values.add(val);
     types.add(type);
     clear();
   }
   private void checkChar()
   {
     type = 3;
     val = (int)name.charAt(0);
     names.add(name);
     values.add(val);
     types.add(type + 100);
     clear();
   }
   private void checkPun()
   {
     val = (int)name.charAt(0);
     names.add(name);
     values.add(val);
     types.add(type + 100);
     clear();
   }
}
   