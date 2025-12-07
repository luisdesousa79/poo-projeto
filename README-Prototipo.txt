--------------------------------------------------------------------
|      ISCTE - PROGRAMAÇÃO ORIENTADA A OBJETOS - PROJETO FINAL     |
--------------------------------------------------------------------

 AUTORES:
 Nome: Luís de Sousa          | Nº: 129329
 Nome: Guilherme Freitas      | Nº: 129883

 DATA: Dezembro 2025
____________________________________________________________________

  1. DESCRIÇÃO GERAL

 Implementação do jogo "Fish Fillets" em Java. Jogar consiste em controlar dois peixes, com capacidades diferentes, 
 e o objetivo é encontrar a saída de "salas" preenchidas com objetos de diferentes tipos.
____________________________________________________________________

  2. DECISÕES DE IMPLEMENTAÇÃO E INTERPRETAÇÃO DE REGRAS

 Durante o desenvolvimento, tomámos as seguintes decisões para resolver
pontos do enunciado que permitiam múltiplas interpretações:

 A. CÁLCULO DE PONTUAÇÃO (HIGHSCORES)
    - Interpretação: O enunciado refere aos "melhores tempos".
    - Decisão: Decidimos que os melhores tempos (pontuação) seria baseada estritamente 
      no NÚMERO DE MOVIMENTOS (Moves). Quanto menor o número de movimentos para 
      os peixes saírem da room, melhor a classificação.
    - Motivo: Considerámos que contar "Ticks" (tempo) seria injusto num jogo
      de puzzle onde o jogador precisa de tempo para pensar. A eficiência
      do movimento é o critério principal na avaliação de uma partida.

 B. CONDIÇÃO DE SAÍDA DE NÍVEL (EXIT)
    - Interpretação: O conceito de "sair da sala".
    - Decisão: Considerámos que um peixe "saiu da room" quando ocupa uma posição numa
      das extremidades da grelha que não contenha uma parede. 
      Não é necessário "sair do mapa" para fora das extremidades delimitadas pelas paredes.
    - Ao pisar nessa célula (que possui o objeto do tipo Door), o peixe é removido
      e considerado salvo.

 C. COMPORTAMENTO DA BOMBA E LIMITES DO MAPA
    - Interpretação: A bomba destrói objetos adjacentes à sua posição.
    - Decisão: Implementámos uma exceção para as Paredes de Limite do Mapa.
      A bomba destrói tudo à volta, EXCETO as paredes que delimitam a Room.
    - Motivo: Se a bomba destruísse paredes de limite, criaria "saídas falsas"
      pelas quais o jogador poderia escapar, contornando o puzzle desenhado
      para aquele nível.
