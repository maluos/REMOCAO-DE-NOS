/**
 * Implementação da funcionalidade de remoção para a classe ArvoreBinaria.
 * * A remoção em BSTs é dividida em 3 casos principais que cobrem os 4 cenários pedidos:
 * 1. Nó Folha (sem filhos)
 * 2. Nó com um único filho (esquerda ou direita)
 * 3. Nó com dois filhos
 */

public class ArvoreBinaria {
    No raiz;

    public ArvoreBinaria() {
        // Inicializa com um nó vazio conforme o template original
        this.raiz = new No(null);
        System.out.println("Árvore criada com sucesso");
    }

    // --- MÉTODOS DE AUXÍLIO JÁ EXISTENTES ---

    private boolean estaVazia() {
        return this.raiz == null || this.raiz.getConteudo() == null;
    }

    public void inserir(Integer conteudo) {
        No novoNo = new No(conteudo);
        if (estaVazia()) {
            this.raiz = novoNo;
        } else {
            inserirRecursivo(novoNo, this.raiz);
        }
    }

    private void inserirRecursivo(No novoNo, No aux) {
        if (aux.getConteudo() > novoNo.getConteudo()) {
            if (aux.getEsquerda() == null) {
                aux.setEsquerda(novoNo);
            } else {
                inserirRecursivo(novoNo, aux.getEsquerda());
            }
        } else if (aux.getConteudo() < novoNo.getConteudo()) {
            if (aux.getDireita() == null) {
                aux.setDireita(novoNo);
            } else {
                inserirRecursivo(novoNo, aux.getDireita());
            }
        }
    }

    // --- IMPLEMENTAÇÃO DA REMOÇÃO ---

    /**
     * Ponto de entrada público para a remoção.
     * @param valor O conteúdo do nó a ser removido.
     */
    public void remover(Integer valor) {
        if (estaVazia()) {
            System.out.println("Árvore vazia. Não há o que remover.");
            return;
        }
        // A raiz recebe o resultado da recursão, garantindo que se a raiz mudar, 
        // a referência seja atualizada (Cenário: Remoção do nó raiz)
        this.raiz = removerRecursivo(this.raiz, valor);
    }

    /**
     * Método recursivo que navega na árvore e reestrutura os nós.
     * EXPLICACAO DA RECURSAO: 
     * Cada chamada "pergunta" para o filho se ele deve ser removido. 
     * Se sim, o filho retorna o seu substituto para o pai.
     */
    private No removerRecursivo(No atual, Integer valor) {
        // Caso Base: Valor não encontrado na árvore
        if (atual == null) {
            return null;
        }

        // FASE DE BUSCA: Navega na árvore até encontrar o valor
        if (valor < atual.getConteudo()) {
            atual.setEsquerda(removerRecursivo(atual.getEsquerda(), valor));
        } else if (valor > atual.getConteudo()) {
            atual.setDireita(removerRecursivo(atual.getDireita(), valor));
        } 
        
        // FASE DE REMOÇÃO: Valor encontrado (valor == atual.getConteudo())
        else {
            // CENÁRIO 1: Nó Folha
            // Se não tem filhos, simplesmente retornamos null para o pai dele.
            if (atual.getEsquerda() == null && atual.getDireita() == null) {
                System.out.println("Removendo nó folha: " + valor);
                return null;
            }

            // CENÁRIO 2: Nó com apenas UM filho (Direita)
            // Retornamos o filho para o "avô", ignorando o nó atual.
            if (atual.getEsquerda() == null) {
                System.out.println("Removendo nó com filho à direita: " + valor);
                return atual.getDireita();
            }

            // CENÁRIO 2: Nó com apenas UM filho (Esquerda)
            // Mesma lógica: o filho assume o lugar do pai removido.
            if (atual.getDireita() == null) {
                System.out.println("Removendo nó com filho à esquerda: " + valor);
                return atual.getEsquerda();
            }

            // CENÁRIO 3: Nó com DOIS filhos
            /*
             * Lógica: Não podemos simplesmente deletar o nó, pois a árvore quebraria.
             * Precisamos encontrar o "Sucessor In-Order" (o menor valor da subárvore direita).
             * Esse sucessor é o nó que garante que a propriedade da BST continue válida.
             */
            System.out.println("Removendo nó com dois filhos: " + valor);
            
            // 1. Encontra o menor valor à direita
            Integer menorValorDireita = encontrarMinimo(atual.getDireita());
            
            // 2. Substitui o valor do nó atual pelo valor do sucessor
            atual.setConteudo(menorValorDireita);
            
            // 3. Remove o nó sucessor original (que agora está duplicado) da subárvore direita
            atual.setDireita(removerRecursivo(atual.getDireita(), menorValorDireita));
        }

        return atual;
    }

    /**
     * Auxiliar para o cenário de 2 filhos: encontra o valor mais à esquerda 
     * de uma determinada subárvore.
     */
    private Integer encontrarMinimo(No no) {
        Integer min = no.getConteudo();
        while (no.getEsquerda() != null) {
            min = no.getEsquerda().getConteudo();
            no = no.getEsquerda();
        }
        return min;
    }

    // Métodos de percurso omitidos aqui para brevidade, mas devem ser mantidos conforme original.
}