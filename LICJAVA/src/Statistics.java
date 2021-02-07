import java.util.ArrayList;

class Statistics {


    private final static String STATISTICSFILENAME = "Statistics.txt";
    private final static String ROULETTE_STATSFILENAME = "Roulette_Stats.txt";
    static int games;
    static int coins;
    private static String betsWon = "";
    private static String betsWonValue = "";

    public static void init(){
        load();
    }

    public static void addGame(){
        games++;
        save();
    }

    public static void addCoins(int c){
        coins+=c;
        save();
    }
    public static int getGames(){return games;}
    public static int getCoins(){return coins;}

    public static void clear(){
        coins=0;
        games=0;
       //TODO
    }

    //Carrega estatisticas a partir de um ficheiro
    private static void load(){
        clear();

        ArrayList<String> SL=FileAccess.load(STATISTICSFILENAME,2);
        if (SL.size()>=2) {
            games = Integer.parseInt(SL.get(0) );
            coins = Integer.parseInt(SL.get(1) );
        }
        ArrayList<String> RSL = FileAccess.load(ROULETTE_STATSFILENAME,10);
        for(int i = 0; i < 10; i++){
            betsWon = "" + RSL.get(i).charAt(2);
            RouletteGameApp.betsWon[i] = Integer.parseInt(betsWon);
            betsWonValue = "" + RSL.get(i).charAt(4);
            RouletteGameApp.betsWonValue[i] = Integer.parseInt(betsWonValue);
        }
    }

    // Grava as estatisticas
    public static void save(){
        ArrayList<String> SL=new ArrayList<>(2);
        SL.add(""+games);
        SL.add(""+coins);
        FileAccess.save(STATISTICSFILENAME,SL);

        ArrayList<String> RSL = new ArrayList<>(10);
        for(int i = 0; i < 10; i++)
            RSL.add(""+ i +";" + RouletteGameApp.betsWon[i] + ";" + RouletteGameApp.betsWonValue[i]);
        FileAccess.save(ROULETTE_STATSFILENAME,RSL);
    }
}
