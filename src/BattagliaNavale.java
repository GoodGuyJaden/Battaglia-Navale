import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Formatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BattagliaNavale
{
	private static Player player = new Player("");
	private static Player computer = new Player("CPU");
	private static boolean turno = true;
	private static boolean partitaFinita = false;
	private static final File file = new File("Secret\\Classifica.txt");
	private static int vittorieP;
	private static int vittorieC;
	public static String errore = "";    //da sostituire con un metodo che non permette di stampare
	private static boolean colpito = false;		//non stampo quale nave è stata affondata per l'utente perché non si deve sapere
	static Scanner sc = new Scanner(System.in);
	
	private static int [] bariamo = new int [32];  //tutto questo serve al computer per barare
	private static int conta = 0;
	private static boolean tiFaccioPerdere = false;
	
	public static void bariamoSi(int x, int y)
	{
		bariamo[conta++] = x;
		bariamo[conta++] = y;
		if(conta == bariamo.length)
		{
			conta = 0;
		}
	}
	
	
	public static void randomGeneratorCPUMap(Player p) //modificare questo funziona solo per cpu quindi rimuovere player p e avviare direttamente con CPU
	{
		int x = 0;
		int y = 0;
		int x1 = 0;
		int y1 = 0;
		int dimNave;			
		
		for(int i = 0;i < p.getPorto().length;i++)
		{
			dimNave = p.getPorto()[i].getDimNave();
			x = (int)(Math.random()*15+1);
			y = (int)(Math.random()*15+1);
			if((int)(Math.random()*2) == 0)
			{
				x1 = x;
				y1 = y+dimNave-1;
			}
			else
			{
				y1 = y;
				x1 = x+dimNave-1;
			}
			if(insertNave(x,y,x1,y1,p,p.getPorto()[i]))
			{
				
			}
			else
			{
				i--;
				errore = "";
			}
		}
	}
	
	public static boolean insertNave(int x, int y, int x1, int y1, Player p, Nave n)
	{
		if(p.getCDG().checkIn(x, y, x1, y1, p, n))
		{
			errore = "";
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	public static boolean attaccaP(boolean flag) throws InterruptedException, IOException
	{
		cls();
		computer.getCDG().stampa(flag);
		int x = -1;
		int y = -1;
		do //controllo rapido per le coordinate 
		{
			try
			{
				System.out.println("Inserisci le coordinate dove vuoi colpire");
				x = sc.nextInt()-1;
				y = sc.nextInt()-1;
				sc.nextLine();	
				
			}catch(InputMismatchException e)
			{
				sc.nextLine();
			}
			
		}while(x<0 || x>14 || y<0 || y>14);
		
		//SE COLPISCE DI NUOVO IN QUEL PUNTO PERDE IL TURNO
		if(computer.getCDG().getCampo()[x][y] == ' ')				//Se attacca il player devo inserire computer e viceversa
		{
			computer.getCDG().getCampo()[x][y] = 'O';
			
			return false;
		}
		else if(computer.getCDG().getCampo()[x][y] != ' ' && computer.getCDG().getCampo()[x][y] != 'X' && 
				computer.getCDG().getCampo()[x][y] != 'O')
		{
			
			computer.getNave(computer.getCDG().getCampo()[x][y]).setColpito(player);
			computer.getCDG().getCampo()[x][y] = 'X';
			return true;
		}
		return false;
	}
	
	public static boolean attaccaC()
	{
		int x  = 0;
		int y = 0;
		if(tiFaccioPerdere)
		{
			x = bariamo[conta++];
			y = bariamo[conta++];
			
		}
		else
		{
			x = (int)(Math.random()*player.getCDG().getCampo().length);
			y = (int)(Math.random()*player.getCDG().getCampo().length);
		}
		
		if(player.getCDG().getCampo()[x][y] == ' ')				//Se attacca il player devo inserire computer e viceversa
		{
			player.getCDG().getCampo()[x][y] = 'O';
			System.out.println("\nIl computer non ha colpito nessun bersaglio");
			return true;
		}
		else if(player.getCDG().getCampo()[x][y] != ' ' && player.getCDG().getCampo()[x][y] != 'X' && 
				player.getCDG().getCampo()[x][y] != 'O')
		{
			System.out.println("\nIl computer ha colpito un bersaglio");
			player.getNave(player.getCDG().getCampo()[x][y]).setColpito(computer);
			player.getCDG().getCampo()[x][y] = 'X';
			return true;
		}
		
		return false;
	}
	
	public static boolean turniAdmin() throws InterruptedException, IOException
	{
		
		if(turno)
		{
			colpito = attaccaP(false);
			turno=!turno;
			cls();
		}
		else
		{
			if(colpito)
			{
				System.out.println("\nHai colpito un bersaglio");
			}
			else
			{
				System.out.println("\nNon hai colpito nessun bersaglio");
			}
			attaccaC();
			System.out.println("\nInserisci 'y' per visualizzare la tua mappa o qualsiasi altro tasto per proseguire");
			if(sc.nextLine().toLowerCase().equals("y"))
			{
				player.getCDG().stampa(false);
				System.out.println("\nPremi invio quando vuoi proseguire");
				sc.nextLine();
			}
			turno=!turno;;
		}
		return false;
	}
	
	public static void cls() throws InterruptedException, IOException
	{
		int n = new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	}
	
	public static boolean startRoutine() throws InterruptedException, IOException
	{
		cls();
		randomGeneratorCPUMap(computer);
		//randomGeneratorCPUMap(player);
		if(vittorieP != 0 || vittorieC != 0)
		{
			System.out.println("Inserisci il tuo username");
			player.setNome(sc.nextLine());
		}
		System.out.println("\nIl carattere 'X' indica che hai colpito una nave\nIl carattere 'O' vuole dire che non hai colpito niente");
		int x = 0;
		int y = 0;
		int x1 = 0;
		int y1 = 0;
		for(int i = 0;i<player.getPorto().length;i++)
		{
			cls();
			player.getCDG().stampa(false);
			System.out.println(errore + "\n");
			System.out.println("\nLa dimensione di questa nave è: " + player.getPorto()[i].getDimNave());
			System.out.println("Le x sono i numeri laterali, le y quelli in alto");
			System.out.println("Inserisci x iniziale, y iniziale, x finale, y finale separati da un 'INVIO' e rispettando le dimensioni della mappa (min 1 max 15)");
			
			try
			{
				x = sc.nextInt();
				y = sc.nextInt();
				x1 = sc.nextInt();
				y1 = sc.nextInt();
				sc.nextLine();
				
			}catch(InputMismatchException e)
			{
				System.out.println("Inserire solo numeri interi grazie");
				sc.nextLine();
			}
			
			if(!(insertNave(x,y,x1,y1,player,player.getPorto()[i])))
			{
				i--;
			}
			
		}
		return false;
	}
	
	public static void setPartita()
	{
		partitaFinita = !partitaFinita;
	}
	
	public static void creaFile() throws IOException, InterruptedException
	{
		if(!file.exists())
		{
			Formatter x = new Formatter("Secret\\Classifica.txt");
			PrintWriter pw = new PrintWriter(file);
			cls();
			System.out.println("Inserisci il tuo nome");
			player.setNome(sc.nextLine());
			pw.println(player.getNome() + ": 0");
			pw.println(computer.getNome() + ": 0");
			pw.close();
			creaFile();
			x.close();
		}
		else
		{
			String nomeP = "";
			String nomeC = "";
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine())
			{
				for(int i = 0;i<2;i++)
				{
					String [] contenutoFile = sc.nextLine().split(": ",2);
					if(i == 0)
					{
						nomeP = contenutoFile[0];
						vittorieP = Integer.parseInt(contenutoFile[1]);
					}
					else
					{
						nomeC = contenutoFile[0];
						vittorieC = Integer.parseInt(contenutoFile[1]);
					}
				}
			}
			sc.close();
			System.out.println("\nClassifica\n" + nomeP + ": " + vittorieP + "\n" + nomeC + ": " + vittorieC);
		}
	}
	
	public static void scriviFile() throws IOException, InterruptedException
	{
		cls();
		PrintWriter pw = new PrintWriter(file);
		if(!turno && partitaFinita)
		{
			vittorieP++;
			System.out.println("Hai vinto!!!");
		}
		else
		{
			vittorieC++;
			System.out.println("Hai perso!!!");
		}
		pw.println(player.getNome() + ": " + vittorieP);
		pw.println(computer.getNome() + ": " + vittorieC);
		pw.close();
	}
	
	public static void restart()
	{
		player.reset();
		computer.reset();
		setPartita();
		conta = 0;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException 
	{
		String str = "";
		while(!str.equals("X"))
		{
			cls();
			System.out.println("Se vuoi giocare una nuova partita premi 'N', se vuoi uscire premi 'X'");
			str = sc.nextLine().toUpperCase();
			if(str.equals("X"))
			{
				
			}
			else 
			{
				if(str.equals("N"))
				{
					if(partitaFinita)
					{
						restart();
					}
					try
					{
						System.out.println("Hard mode???? true or false  (Verrà impostata a false se si omette)");
						tiFaccioPerdere = sc.nextBoolean();
						sc.nextLine();
					}catch(InputMismatchException e)
					{
						str = "";
						sc.nextLine();
					}
					creaFile();
					startRoutine();
					do {
						turniAdmin();
					}while(!partitaFinita);
					scriviFile();
				}
			}
			
			
		}
		
	}

}