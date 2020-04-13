class Symbol
{
  public int type;
  public int value;
  public String name;
  public int size = 0;
  public int eltype = 0;
  public Symbol(String name, int type, int value, int size, int eltype)
  {
    this.type = type;
    this.value = value;
    this.name = name;
    this.size = size;
    this.eltype = eltype;
  }
  public Symbol()
  {
  }
}