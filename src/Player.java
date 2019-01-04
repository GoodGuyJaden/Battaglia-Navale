
public class Player 
{
	private String nome = "Giocatore";
	private Nave Portaerei = new Nave(5, "Portaerei");
	private Nave Bombardiere  = new Nave(4, "Bombardiere");
	private Nave Incrociatore  = new Nave(3, "Incrociatore");
	private Nave Sottomarino  = new Nave(2, "Sottomarino");
	private Nave Nave_assalto = new Nave(2, "Nave_assalto");
	//private Nave Ava = new Nave(1,"Ava");
	private CampoDiGioco cdg;
	private int naviAffondate = 0;
	private Nave [] porto = {Portaerei, Bombardiere, Incrociatore, Sottomarino, Nave_assalto,/*Ava*/};
	
	public Player(String nome)
	{
		setNome(nome);
		this.cdg = new CampoDiGioco();
	}
	
	public String getNome()
	{
		return nome;
	}
	
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	
	public Nave getNave(String nomeNave)
	{
		for(Nave n: porto)
		{
			if(n.getNome().equals(nomeNave))
			{
				return n;
			}
		}
		
		return null;
	}
	
	public Nave getNave(char firstChar)
	{
		for(Nave n: porto)
		{
			if(n.getNome().charAt(0) == firstChar)
			{
				return n;
			}
		}
		
		return null;
	}

	public void setNA()
	{
		naviAffondate++;
		if(naviAffondate == porto.length)
		{
			BattagliaNavale.setPartita();
		}
	}
	
	public CampoDiGioco getCDG()
	{
		return this.cdg;
	}

	public Nave [] getPorto()
	{
		return porto;
	}
	
	public void reset()
	{
		for(int i = 0;i<porto.length;i++)
		{
			porto[i].reset();
		}
		this.getCDG().reset();
		naviAffondate = 0;
	}
}

