package com.trybe.consultafilmes;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * single custom class.
 *
 */
public class Consultas {

  private final Collection<Filme> filmes;

  public Consultas(Collection<Filme> filmes) {
    this.filmes = filmes;
  }

  /**
   * Consulta 1: a partir da coleção de filmes desta classe, este método retorna o conjunto de
   * atores que interpretaram a si próprios em pelo menos um desses filmes.
   *
   * <p>
   * Considera-se "atores que interpretaram a si próprios" aqueles que têm o seu nome como uma das
   * chaves do Map `atoresPorPersonagem` e também como um dos itens pertencentes ao conjunto
   * associado a esta mesma chave.
   * </p>
   */
  public Set<String> atoresQueInterpretaramSiProprios() {
    Set<String> atoresSiProprios = new HashSet<>();
    filmes.stream().flatMap(filme -> filme.atoresPorPersonagem.entrySet().stream())
        .filter(entry -> entry.getValue().contains(entry.getKey())).map(Map.Entry::getKey)
        .forEach(atoresSiProprios::add);
    return atoresSiProprios;
  }

  /**
   * Consulta 2: a partir da coleção de filmes desta classe, este método retorna a lista de atores
   * que atuaram em pelo menos um filme de um determinado diretor. A lista retornada está disposta
   * em ordem alfabética.
   *
   * <p>
   * Considera-se que um ator tenha atuado em um filme de um determinado diretor se ele tem o seu
   * nome como um dos itens do campo `atores`, ao mesmo tempo em que o diretor em questão tem o seu
   * nome como um dos itens do campo `diretores` do mesmo filme.
   * </p>
   */
  public List<String> atoresQueAtuaramEmFilmesDoDiretorEmOrdemAlfabetica(String diretor) {
    return filmes.stream().filter(filme -> filme.diretores.contains(diretor))
        .flatMap(filme -> filme.atores.stream()).distinct().sorted().collect(Collectors.toList());
  }


  /**
   * Consulta 3: a partir da coleção de filmes desta classe, este método retorna a lista de filmes
   * em que pelo menos um dos diretores tenha atuado. A lista retornada está disposta em ordem de
   * lançamento, com os filmes mais recentes no início.
   *
   * <p>
   * Considera-se "filmes em que pelo menos um dos diretores tenha atuado" aqueles em que pelo menos
   * um dos itens do campo `diretores` também é um item do campo `atores`.
   * </p>
   */
  public List<Filme> filmesEmQuePeloMenosUmDiretorAtuouMaisRecentesPrimeiro() {
    return filmes.stream()
        .filter(
            filme -> filme.diretores.stream().anyMatch(diretor -> filme.atores.contains(diretor)))
        .distinct()
        .sorted(Comparator.comparingInt((Filme filme) -> filme.anoDeLancamento).reversed())
        .collect(Collectors.toList());
  }

  /**
   * Consulta 4: a partir da coleção de filmes desta classe, este método retorna um Map contendo
   * todos os filmes lançados em um determinado ano agrupados por categoria.
   *
   * <p>
   * Cada chave do Map representa uma categoria, enquanto cada valor representa o conjunto de filmes
   * que se encaixam na categoria da chave correspondente.
   * </p>
   */
  public Map<String, Set<Filme>> filmesLancadosNoAnoAgrupadosPorCategoria(int ano) {
    return filmes.stream().filter(filme -> filme.anoDeLancamento == ano)
        .flatMap(filme -> filme.categorias.stream()
            .map(categoria -> new AbstractMap.SimpleEntry<>(categoria, filme)))
        .collect(Collectors.groupingBy(AbstractMap.SimpleEntry::getKey,
            Collectors.mapping(AbstractMap.SimpleEntry::getValue, Collectors.toSet())));
  }
}
