package aula5;

import java.util.Random;
import java.util.Scanner;

public class Jogo {

    static int[][] marray = null;
    static Random gerador = new Random();
    private static int numPecas = 7;
    public int numPontos;
    static int[][] marrayaux = null;
    private boolean repeticaoencontrada;

    Jogo() {
        marray = new int[8][8];
        marrayaux = new int[8][8];

        numPontos = 0;

        Random myRandom = new Random();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int auxVar = myRandom.nextInt(numPecas) + 1;
                marray[i][j] = auxVar;
                marrayaux[i][j] = auxVar;
            }
        }
        
    }

    public void Imprimir() {
        System.out.println("\n\n** Array Original: **");
        System.out.print("ij ");
        for (int j = 0; j < 8; j++) {
            System.out.print(j + " ");
        }
        for (int i = 0; i < 8; i++) {
            System.out.println("");
            System.out.print("" + i + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print(" " + marray[i][j]);
            }
        }
        System.out.println("\n\n** Auxiliary Array: **");
        System.out.print("ij ");
        for (int j = 0; j < 8; j++) {
            System.out.print(j + " ");
        }
        for (int i = 0; i < 8; i++) {

            System.out.println("");
            System.out.print("" + i + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print(" " + marrayaux[i][j]);
            }
        }
        System.out.println("\nSCORE=" + numPontos + "\n\n");
    }

    boolean repericaolinhas() {
        boolean rep = false;
        marrayaux = marray.clone();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                int contaRep = 0;
                int ii = i + 1;
                while (ii < 8) {
                    if (marray[i][j] == marray[ii][j]) {

                        contaRep++;

                    } else {
                        break;
                    }
                    ii++;
                }
                if (contaRep >= 2) {
                    for (int apagador = i; apagador < ii; apagador++) {
                        marrayaux[apagador][j] = 0;
                        numPontos += 10;
                        System.out.println(numPontos);

                    }
                    rep = true;
                }

            }

        }
        return rep;
    }

    boolean repeticaocolunas() {
        boolean rep = false;
        marrayaux = marray.clone();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                int contaRep = 0;
                int jj = j + 1;
                while (jj < 8) {
                    if (marray[i][j] ==marray[i][jj]) {

                        contaRep++;

                    } else {
                        break;
                    }
                    jj++;
                }
                if (contaRep >= 2) {
                    for (int apagador = j; apagador < jj; apagador++) {
                        marrayaux[i][apagador] = 0;
                        numPontos += 10;

                    }
                    rep = true;
                }

            }

        }
        return rep;
    }

     void Enchercima() {
        Random myRandom = new Random();

        for (int j = 0; j < 8; j++) {
            int i = 0;
            while (i < 8 && marrayaux[i][j] == 0) {
                marrayaux[i][j] = myRandom.nextInt(numPecas) + 1;
                i++;
            }

        }

    }

    void colunaZero(int i, int j, int contaReps) {
        for (int jj = j; jj < (j + contaReps); jj++) {
            marrayaux[i][jj] = 0;
        }
    }

    void linhaZero(int i, int j, int contaReps) {
        for (int ii = i; ii < (i + contaReps); ii++) {
            marrayaux[ii][j] = 0;
        }
    }

    public void Fazercair() {
        for (int j = 0; j < 8; j++) {
            for (int i = 7; i >= 0; i--) {
                if (marrayaux[i][j] == 0) {
                    int ii = i - 1;
                    while (ii >= 0 && marrayaux[ii][j] == 0) {
                        ii--;
                    }
                    if (ii >= 0) {
                        marrayaux[i][j] = marrayaux[ii][j];
                        marrayaux[ii][j] = 0;
                    }
                }

            }
        }
        System.out.println("Antes de preencher a fila de cima");
        this.Imprimir();

        System.out.println("Depois de preencher a fila de cima");
        Enchercima();
        this.copiararrayparaarrayaux();
        this.Imprimir();
    }

    public void copiararrayparaarrayaux() {
        for (int i = 0; i < 8; i++) {
            System.arraycopy(marrayaux[i], 0, marray[i], 0, 8);
        }
    }



    public void moverpeca(int px1, int py1, int px2, int py2) {
    int aux;
        if ((Math.abs(px1-px2)+Math.abs(py1-py2))!=1) {
            System.out.println("Inválido, pá!");
            return;
        }
        
        
        // falta: verificar se movimento é válido
         aux = marray[px1][py1];
        marray[px1][py1] = marray[px2][py2];
        marray[px2][py2] = aux;

        boolean moveencontrado=(this.repericaolinhas() || this.repeticaocolunas());
       this.verificarRep();
        if (!moveencontrado) {
            aux = marray[px1][py1];
            marray[px1][py1] = marray[px2][py2];
            marray[px2][py2] = aux;
            System.out.println("Movimento invalido!!");
        }

        this.Fazercair();
        this.Enchercima();
        while(this.repeticaocolunas() || this.repericaolinhas()){
            this.Fazercair();
            this.Enchercima();
        }
    }

    public boolean getRepetitionFound() {
        return repeticaoencontrada;
    }

    public void verificarRep() {
        repeticaoencontrada = false;
        Imprimir();
        while (this.repeticaocolunas() || this.repericaolinhas()) {
            repeticaoencontrada = true;
            this.Fazercair();
            this.Enchercima();

        }
    }

}
