/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prova1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Bruno
 */
public class Receita {
  Map<String, String> ingredientes;
  LinkedList passos;
  String nome;

  public Receita() {
    this.ingredientes = new HashMap<>();
    this.passos = new LinkedList();
  }
  
  private boolean adicionarIngrediente (String ingrediente, String quantidade) {
    ingredientes.put(ingrediente, quantidade);
    return ingredientes.containsKey(ingrediente);
  }
  
  private boolean removerIngrediente (String keyIngrediente) {
    ingredientes.remove(keyIngrediente);
    return !ingredientes.containsKey(keyIngrediente);
  }
  
  private boolean adicionarPasso (String passo, int i) {
    passos.add(i, passo);
    return passos.contains(passo);
  }
  
  private boolean removerPasso (int i) {
    String auxiliar = passos.get(i).toString();
    passos.remove(i);
    return !passos.contains(auxiliar);
  }
  
  private void leituraDaReceita () {
    try {
      File file = new File(nome);
      if (file.isFile()) {
        String linha;
        FileReader fReader = new FileReader(nome);
        BufferedReader bReader = new BufferedReader(fReader);
        boolean ehPasso = false;
        linha = bReader.readLine();
        while ((linha = bReader.readLine()) != null) {
          if (linha.contains("Receita:")) {
            ehPasso = true;
            continue;
          }
          if (ehPasso == false) {
            ingredientes.put(linha.substring(0, linha.indexOf(",")).toLowerCase(), 
                             linha.substring(linha.indexOf(" ") + 1));
          } else {
            passos.add(linha);
          }
        }
        bReader.close();
      } else {
        file.createNewFile();
      }
    }
    catch (FileNotFoundException ex) {
      System.out.println("Não foi possível ler o arquivo " + nome);
    }
    catch (IOException ex) {
      System.out.println("Erro ao ler o arquivo " + nome);
    }
  }
  
  private void sobreescritaDaReceita () {
    try {
      FileWriter fWriter = new FileWriter(nome);
      BufferedWriter bWriter = new BufferedWriter(fWriter);
      bWriter.write("Ingredientes:");
      bWriter.newLine();
      ingredientes.entrySet().forEach((entry) -> {
        try {
          bWriter.write(entry.getKey() + ", " + entry.getValue());
          bWriter.newLine();
        } 
        catch (FileNotFoundException ex) {
          System.out.println("Não foi possível ler o arquivo " + nome);
        }
        catch (IOException ex) {
          System.out.println("Erro ao ler o arquivo " + nome);
        } 
      });
      bWriter.write("Receita:");
      bWriter.newLine();
      passos.forEach((passo) -> {
        try {
          bWriter.write(passo.toString());
          bWriter.newLine();
        }
        catch (FileNotFoundException ex) {
          System.out.println("Não foi possível ler o arquivo " + nome);
        }
        catch (IOException ex) {
          System.out.println("Erro ao ler o arquivo " + nome);
        } 
      });
      bWriter.close();
    }
    catch (FileNotFoundException ex) {
      System.out.println("Não foi possível ler o arquivo " + nome);
    }
    catch (IOException ex) {
      System.out.println("Erro ao ler o arquivo " + nome);
    } 
  }
  
  public boolean menu () {
    Scanner scan = new Scanner(System.in);
    System.out.println("Qual operação deseja realizar?\n"
                      +"0-Ler/Criar receita\n"
                      +"1-Adicionar ingrediente\n"
                      +"2-Remover ingrediente\n"
                      +"3-Adicionar passo\n"
                      +"4-Remover passo\n"
                      +"5-Sair\n");
    String op = scan.next();
    if (op.equals("5")) {
      System.out.println("Salvando...");
      sobreescritaDaReceita();
      return true;
    } else {
      if (op.equals("4")) {
        System.out.println("Digite a posição do passo");
        removerPasso(scan.nextInt());
      } else {
        if (op.equals("3")) {
          System.out.println("Digite o nome do passo e  em que deve ser executado (um por vez)");
          adicionarPasso(scan.next(), scan.nextInt());
        } else {
          if (op.equals("2")) {
            System.out.println("Digite o nome do ingrediente ");
            removerIngrediente(scan.next());
          } else {
            if (op.equals("1")) {
              System.out.println("Digite o nome do ingrediente e a quantidade (um por vez)");
              adicionarIngrediente(scan.next(), scan.next());
            } else {
              if (op.equals("0")) {
                System.out.println("Digite o nome do arquivo");
                nome = scan.next();
                leituraDaReceita();
              }
            }
          }
        }
      }
      return false;
    }
  }
}
