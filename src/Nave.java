
public class Nave
{
	private char [] nave_arr;
	private String nome;
	
	public Nave (int dim, String nome)
	{
		nave_arr = new char[dim];
		
		for(int i = 0; i < dim; i++)
		{
			this.nave_arr[i] = nome.charAt(0);
		}
		
		this.nome = nome;
	}
		
	public int getDimNave()
	{
		return nave_arr.length;
	}
	
	public char getPrimoCharNave()
	{
		return nome.charAt(0);
	}

	public char [] getArrayNave()
	{
		return nave_arr;
	}

	public String getNome()
	{
		return nome;
	}
	
	public boolean setColpito(Player p)
	{
		for(int i = 0;i < nave_arr.length;i++)
		{
			if(nave_arr[i] != 'X' && i == nave_arr.length - 1)
			{
				nave_arr[i] = 'X';
				System.out.println(getNome() + " affondato/a");
				p.setNA();
				return true;
			}
			else if(nave_arr[i] != 'X')
			{
				nave_arr[i] = 'X';
				return true;
			}
		}
		//System.out.println("Hai già colpito prima questa nave");
		return false;
	}

	public void reset()
	{
		for(int i = 0; i < nave_arr.length; i++)
		{
			this.nave_arr[i] = nome.charAt(0);
		}
	}
	
}
