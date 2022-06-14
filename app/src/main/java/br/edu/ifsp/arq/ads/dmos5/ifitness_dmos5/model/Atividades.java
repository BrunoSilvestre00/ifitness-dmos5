package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model;

public enum Atividades {
    CAMINHADA (1, "Caminhada"),
    CICLISMO (2, "Ciclismo"),
    CORRIDA (3, "Corrida"),
    NATACAO (4, "Natação");

    private final int id;
    private final String type;

    Atividades(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId(){
        return this.id;
    }

    public String getType(){
        return this.type;
    }
}
