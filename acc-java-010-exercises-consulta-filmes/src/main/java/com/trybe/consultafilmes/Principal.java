package com.trybe.consultafilmes;

import java.util.List;
import java.util.Set;

/**
 * single custom class.
 *
 */
public class Principal {

  /**
   * Função utilizada apenas para validação da solução do desafio.
   *
   * @param args Não utilizado.
   */
  public static void main(String[] args) {
    Consultas consultas = new Consultas(Filmes.todos());
    Set<String> resultados = consultas.atoresQueInterpretaramSiProprios();
    System.out.println(resultados);

    List<String> atoresDoDiretor =
        consultas.atoresQueAtuaramEmFilmesDoDiretorEmOrdemAlfabetica("Jon Watts");
    System.out.println(atoresDoDiretor);
    List<Filme> filmesComDiretorAtor =
        consultas.filmesEmQuePeloMenosUmDiretorAtuouMaisRecentesPrimeiro();
    System.out.println(filmesComDiretorAtor);
  }
}
