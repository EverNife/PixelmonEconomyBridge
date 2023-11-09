package br.com.finalcraft.pixelmoneconomybridge.common;

public class PokemonWrapper<P> {

    private final P pokemon;

    public PokemonWrapper(P pokemon) {
        this.pokemon = pokemon;
    }

    public static <P> PokemonWrapper<P> of(P pokemon){
        return new PokemonWrapper(pokemon);
    }

    public P getPokemon() {
        return pokemon;
    }
}
