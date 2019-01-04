
public class CampoDiGioco
{
	private char [] [] campo = new char [15] [15];
	
	public CampoDiGioco()
	{
		reset();
	}
	
	public void stampa(boolean flag) //se flag true stampa per l'avversario e quindi senza le navi avversarie, altrimenti stampa per te e ti mostra il tuo campo
	{
		int ciclo = 0;
		
		for (int i = 0; i < campo.length; i++)
		{
			if(ciclo == 0)
			{
				System.out.print("\t  ");  //primo spazio iniziale
				
				for (int j = 0; j < campo.length; j++)
				{
					if(j>=9)
					{
						System.out.print((j+1) + "  ");   //spazio ridotto per i numeri a doppia cifra
					}
					else
					{
						System.out.print((j+1) + "   ");
					}
					
				}
				i--;
				ciclo++;
				System.out.println("");
				System.out.println("");
				System.out.println("\t-------------------------------------------------------------");
			}
			else
			{
				for (int j = 0; j < campo.length; j++)
				{
					if(j == 0)
					{
						if(flag && (campo[i][j] == 'B' || campo[i][j] == 'P' || campo[i][j] == 'I' || campo[i][j] == 'S' || campo[i][j] == 'N'))
						{
							System.out.print((i+1)+"\t|   |");
						}
						else
						{
							System.out.print((i+1)+"\t| " + campo[i][j] + " |");//Ogni riga con numero davanti
						}		
					}
					else
					{
						if(flag && (campo[i][j] == 'B' || campo[i][j] == 'P' || campo[i][j] == 'I' || campo[i][j] == 'S' || campo[i][j] == 'N'))
						{
							System.out.print("   |");
						}
						else
						{
							System.out.print(" " + campo[i][j] + " |");	//il resto dei campi
						}			
					}
					
				}
				
				System.out.println("");
				System.out.println("\t-------------------------------------------------------------");   //Separatore tra linee
			}
				
		}
	}

	public boolean checkIn(int x, int y, int x1, int y1, Player p, Nave n)  //routine per controllare se è possibile inserire la nave dove richiesto
	{
		String posizione = "";
		
		
		if(!(x<1 || x>p.getCDG().getCampo().length || x1<1 || x1>p.getCDG().getCampo().length || y<1 || y>p.getCDG().getCampo().length || 
			 y1<1 || y1>p.getCDG().getCampo().length)) //controllo coordinate errate o meno
		{
			
		}
		else
		{
			BattagliaNavale.errore = "Coordinate errate";
			return false;
		}
		if(x == x1)
		{
			if(Math.abs(y-y1) != n.getDimNave()-1)
			{
				BattagliaNavale.errore = "Coordinate e dimensione nave non corrette";
				return false;
			}
			else
			{
				if(y1<y)
				{
					y = y1;
				}
				posizione = "oriz";
			}
		}
		else if(y == y1)
		{
			if(Math.abs(x-x1) != n.getDimNave()-1)
			{
				BattagliaNavale.errore = "Coordinate e dimensione nave non corrette";
				return false;
			}
			else
			{
				if(x1<x)
				{
					x = x1;
				}
				posizione = "vert";
			}
		}
		else
		{
			BattagliaNavale.errore = "Non in diagonale";
			return false;
		}
		
		int var = 0;
		int check = 0;
		
		for(int i = 0; i < n.getDimNave(); i++)
		{
			
			if(var == 0)
			{
				if(posizione.equals("vert"))
				{
					check = x-1+i;
				}
				else
				{
					check = y-1+i;
				}
									
				if(p.getCDG().loopCheck(p.getCDG().getCampo(), x, y, p.getCDG().getCampo().length, check, posizione, n.getPrimoCharNave()))
				{
					var++;
				}
				else
				{
					return false;
				}
			}
			if(var == 1)
			{
				if(posizione.equals("vert"))
				{
					check = x+i;
				}
				else
				{
					check = y+i;
				}
				
				if(p.getCDG().loopCheck(p.getCDG().getCampo(), x, y, p.getCDG().getCampo().length, check, posizione, n.getPrimoCharNave()))
				{
					var++;
				}
				else
				{
					return false;
				}
			}
			if(var == 2)
			{
				if(posizione.equals("vert"))
				{
					check = x+i+1;
				}
				else
				{
					check = y+i+1;
				}
				
				if(p.getCDG().loopCheck(p.getCDG().getCampo(), x, y, p.getCDG().getCampo().length, check, posizione, n.getPrimoCharNave()))
				{
					var = 0;
				}
				else
				{
					return false;
				}
			}	
		}
		
		naveIn(x, y, posizione, n, p);
		return true;
	}
	
	private boolean loopCheck(char[][] campo,int x, int y, int dimCampo, int check, String posizione, char charNave)  //effettua un controllo ripetitivo
	{
		int conta = 0;
		int iteratore = 0;
		int condizione = 0;
		if(posizione.equals("vert"))
		{
			iteratore = y-1;
			condizione = y+1;
		}
		else
		{
			iteratore = x-1;
			condizione = x+1;
		}
		for(int i = iteratore; i <= condizione; i++)
		{
			if(i<1 || i>dimCampo || check < 1 || check>dimCampo)   //grandezza max mat
			{
				conta++;
			}
			else
			{
				if(posizione.equals("vert"))
				{
					if(campo[check-1][i-1] == ' ' || campo[check-1][i-1] == charNave)
					{
						conta++;
					}
					else
					{
						BattagliaNavale.errore = "C'è una nave troppo vicina";
						return false;
					}
				}
				else
				{
					if(campo[i-1][check-1] == ' ' || campo[i-1][check-1] == charNave)
					{
						conta++;
					}
					else
					{
						BattagliaNavale.errore = "C'è una nave troppo vicina";
						return false;
					}
				}
				
			}
			if(conta == 3)
			{
				return true;
			}	
		}
		return false;
	}
	
	private void naveIn(int x, int y, String stringa, Nave n, Player p)  //inserisce l'array della nave e quindi la nave stessa
	{
		if(stringa.equals("oriz"))
		{
			for(int i = 0;i<n.getDimNave();i++)
			{
				p.getCDG().getCampo()[x-1][y+i-1] = n.getArrayNave()[i];
				BattagliaNavale.bariamoSi(x-1, y+i-1);//in questo modo il computer potrà barare e non sbagliare mai un colpo
			}
		}
		else
		{
			for(int i = 0;i<n.getDimNave();i++)
			{
				p.getCDG().getCampo()[x-1+i][y-1] = n.getArrayNave()[i];
				BattagliaNavale.bariamoSi(x-1, y+i-1);//in questo modo il computer potrà barare e non sbagliare mai un colpo
			}
		}
	}
	
	public char getchar(int x, int y)
	{
		return campo[x][y];
	}
	
	public char[][] getCampo()
	{
		return campo;
	}
	
	public void reset()
	{
		for(int i = 0;i<campo.length;i++)
		{
			for(int j = 0;j<campo.length;j++)
			{
				campo[i][j] = ' ';
			}
		}
	}

}
