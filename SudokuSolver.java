import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
class SPolje{
    int fiksnaStevka=0;
    int izbranaStevka=0;
    boolean[] moznePomozneStevke=new boolean[9];
    int kvadrat;
    public int[] vseMozne(){
        int stevec=0;
        for (boolean b : moznePomozneStevke) {
            if (b) {
                stevec++;
            }
        }
        int [] pom = new int[stevec];
        int stevec2=0;
        for (int i = 0; i < moznePomozneStevke.length; i++) {
            if(moznePomozneStevke[i]){
                pom[stevec2]=i;
                stevec2++;
            }
        }
        return pom;
    }

    public int[] vsePomozneStevke(){
        int stevec=0;
        for (boolean b : moznePomozneStevke) {
            if (b) {
                stevec++;
            }
        }
        int [] pom = new int[stevec];
        int stevec2=0;
        for (int i = 0; i < moznePomozneStevke.length; i++) {
            if(moznePomozneStevke[i]){
                pom[stevec2]=i;
                stevec2++;
            }
        }
        return pom;
    }

    public void ponastaviPomozne(int[] a){
        Arrays.fill(moznePomozneStevke, false);
        for (int j : a) {
            moznePomozneStevke[j] = true;
        }
    }
    public void setMozneStevke(boolean[] mozneStevke) { this.moznePomozneStevke = mozneStevke;}
    public void setMoznePomozneStevke(boolean[] moznePomozneStevke) { this.moznePomozneStevke = moznePomozneStevke; }
    public void setFiksnaStevka(int fiksnaStevka) { this.fiksnaStevka = fiksnaStevka; this.izbranaStevka=this.fiksnaStevka;}
    public void setKvadrat(int x,int y) {
        if (x < 3) {
            if (y < 3) {
                kvadrat = 0;
                return;
            }
            if (y < 6) {
                kvadrat = 1;
                return;
            } else {
                kvadrat = 2;
                return;
            }
        }
        if (x < 6) {
            if (y < 3) {
                kvadrat = 3;
                return;
            }
            if (y < 6) {
                kvadrat = 4;
                return;
            } else {
                kvadrat = 5;
                return;
            }
        }
        if (x < 9) {
            if (y < 3) {
                kvadrat = 6;
                return;
            }
            if (y < 6) {
                kvadrat = 7;
            } else {
                kvadrat = 8;
            }
        }
    }
    public void setIzbranaStevka(int izbranaStevka) {
        this.izbranaStevka = izbranaStevka;
    }

    public int getKvadrat() {return kvadrat;}
    public int getFiksnaStevka() { return fiksnaStevka; }
    public int getIzbranaStevka() {
        return izbranaStevka;
    }

}

public class SudokuSolver {
    static SPolje [][]sudoku=new SPolje[9][9];
    static int dolzina=sudoku.length;

    static void izpisSudokuja(){
        System.out.println("+---+---+---+---+---+---+---+---+---+");
        for (int i = 0; i < dolzina; i++) {
            System.out.print("| ");
            for (int j = 0; j < dolzina; j++) {
                if(sudoku[i][j].getIzbranaStevka()==0){
                    System.out.print(" ");
                }else{
                    System.out.print(sudoku[i][j].getIzbranaStevka());
                }
                if(j%3==2){ System.out.print(" | ");}
                else{System.out.print("   ");}
            }
            System.out.println();
            if(i%3==2){
                System.out.println("+---+---+---+---+---+---+---+---+---+");
            }
        }
    }
    static void uvozSudokuja(String datoteka){
        try {
            Scanner dat = new Scanner(new File(datoteka));
            for (int i = 0; i < dolzina; i++) {
                for (int j = 0; j < dolzina; j++) {
                    sudoku[i][j].setFiksnaStevka(Integer.parseInt(dat.next()));
                    sudoku[i][j].setKvadrat(i,j);
                }
            }
            dat.close();
        } catch (Exception e) {
            System.out.println("Napaka pri branju datoteke !");

        }
    }
    static boolean jeMozna(int stevka, int x,int y){
        if(sudoku[x][y].getIzbranaStevka()!=0){return false;}
        for (int i = 0; i < dolzina; i++) {  //preverimo ce je v vrstici
            if(sudoku[x][i].getIzbranaStevka()==stevka){
                return false;
            }
        }
        for (int i = 0; i < dolzina; i++) {  //preverimo ce je v stolpcu
            if(sudoku[i][y].getIzbranaStevka()==stevka){
                return false;
            }
        }
        int pom=(sudoku[x][y].getKvadrat()%3)*3;
        int pom2=3;
        if (sudoku[x][y].getKvadrat()<3)pom2=0;
        if (sudoku[x][y].getKvadrat()>=6)pom2=6;
        for (int i = pom2; i < pom2+3; i++) { // preverimo v kvadratku
            for (int j = pom; j < pom+3; j++) {
                if (sudoku[i][j].getIzbranaStevka()==stevka){
                    return false;
                }
            }
        }
        return true;
    }
    static boolean[] vseMozne(int x,int y){
        boolean [] pom =new boolean[10];
        for (int i = 1; i <= dolzina; i++) {
            pom[i]= jeMozna(i, x, y);
        }
        return pom;
    }
    static void konfig(String datoteka){
        for (int i = 0; i <dolzina; i++){
            for (int j = 0; j <dolzina; j++){
                sudoku[i][j]=new SPolje();
            }
        }
        uvozSudokuja(datoteka);
        izpisSudokuja();
        nastaviVseMozneZaVsaPolja();
    }
    static void nastaviVseMozneZaVsaPolja(){
        for (int i = 0; i < dolzina; i++) {
            for (int j = 0; j < dolzina; j++) {
                sudoku[i][j].setMozneStevke(vseMozne(i,j));
            }
        }
    }
    static void nastaviVsaMoznaZaDolocenoPolje(int x, int y){
        sudoku[x][y].setMozneStevke(vseMozne(x,y));
    }
    static void nastaviPomozneMozneZaDolocenoPolje(int x,int y){sudoku[x][y].setMoznePomozneStevke(vseMozne(x,y));}
    static boolean fiksirajEnojne(){   //vrne tudi, ali je kaj fiksiral
        boolean pom=false;
        for (int i = 0; i < dolzina; i++) {
            for (int j = 0; j < dolzina; j++) {
                if(sudoku[i][j].vseMozne().length==1 ){
                    sudoku[i][j].setFiksnaStevka(sudoku[i][j].vseMozne()[0]);
                    for (int k = 0; k < dolzina; k++) {
                        nastaviVsaMoznaZaDolocenoPolje(i,k);
                    }
                    for (int k = 0; k < dolzina; k++) {
                        nastaviVsaMoznaZaDolocenoPolje(k,j);          //ne spremijna cele tabele temveč le tista polja ki se potencialno spremenijo
                    }
                    int pom3=(sudoku[i][j].getKvadrat()%3)*3;
                    int pom2=3;
                    if (sudoku[i][j].getKvadrat()<3)pom2=0;
                    if (sudoku[i][j].getKvadrat()>=6)pom2=6;
                    for (int k = pom2; k < pom2+3; k++) {
                        for (int l = pom3; l < pom3+3; l++) {
                            nastaviVsaMoznaZaDolocenoPolje(k,l);
                        }
                    }
                    pom=true;
                }
            }
        }
        return pom;
    }
    static void fiksirajDoklerLahko(){
        while (true){
            boolean pom=fiksirajEnojne();
            if(!pom){
                return;
            }
        }
    }
    static boolean IzberiMoznega(int x, int y,int [] mozneIzPrejsnegaKoraka){
        sudoku[x][y].ponastaviPomozne(mozneIzPrejsnegaKoraka);
        int n1=-1;
        int n2=-1;
        boolean samNaslednji=true;
        int j = y+1;
        for (int i = x; i < dolzina; i++) {
            for (; j < dolzina; j++) {
                if(sudoku[i][j].getFiksnaStevka()==0){          //najde naslednjega za izbiranje
                    n1=i;
                    n2=j;
                    samNaslednji=false;
                    break;
                }
            }
            if(!samNaslednji)break;
            j=0;
        }

        if(n1==-1){
            sudoku[x][y].setIzbranaStevka(sudoku[x][y].vsePomozneStevke()[0]);
            return true;  // ni več naslednjega
        }

        for (int i = 0; i < sudoku[x][y].vsePomozneStevke().length; i++) {
            sudoku[x][y].setIzbranaStevka(sudoku[x][y].vsePomozneStevke()[i]);
            {
                for (int k = 0; k < dolzina; k++) {
                    if(k!=y)
                        nastaviPomozneMozneZaDolocenoPolje(x,k);
                }
                for (int k = 0; k < dolzina; k++) {
                    if(k!=x)
                        nastaviPomozneMozneZaDolocenoPolje(k,y);          //ne spremijna cele tabele temveč le tista polja ki se potencialno spremenijo
                }
                int pom3=(sudoku[x][y].getKvadrat()%3)*3;
                int pom2=3;
                if (sudoku[x][y].getKvadrat()<3)pom2=0;
                if (sudoku[x][y].getKvadrat()>=6)pom2=6;
                for (int k = pom2; k < pom2+3; k++) {
                    for (int l = pom3; l < pom3+3; l++) {
                        if(k!=x && l!=y)
                            nastaviPomozneMozneZaDolocenoPolje(k,l);
                    }
                }
            }//posodabljanje tabel
            boolean neki=IzberiMoznega(n1,n2,sudoku[n1][n2].vsePomozneStevke());
            if(neki) {
                sudoku[x][y].setFiksnaStevka(sudoku[x][y].getIzbranaStevka());
                return true;
            }
            else {
                sudoku[x][y].ponastaviPomozne(mozneIzPrejsnegaKoraka);
                sudoku[x][y].setIzbranaStevka(0);
            }
        }
        return false;
    }
    public static void main(String[] args){
        konfig(args[0]); //ime datoteke .txt
        fiksirajDoklerLahko();
        for (int i = 0; i < dolzina; i++) {
            for (int j = 0; j < dolzina; j++) {
                if(sudoku[i][j].getFiksnaStevka()==0){
                    if(!IzberiMoznega(i,j,sudoku[i][j].vsePomozneStevke())){
                        System.out.println("Ta kombinacija sudokuja je neresljiva.");
                        return;
                    }
                    else {
                        izpisSudokuja();
                        return;
                    }
                }
            }
        }


    }
}