package org.example;

import java.io.Serializable;

public class Pokemon implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private int numero;
    private String tipo1;
    private String tipo2;
    private int total;
    private int hp;
    private int ataque;
    private int defensa;
    private int ataqueEspecial;
    private int defensaEspecial;
    private int velocidad;
    private int generacion;
    private boolean legendario;
    private String habilidad;

    public Pokemon(String nombre, int numero, String tipo1, String tipo2, int total, int hp, int ataque, int defensa,
                   int ataqueEspecial, int defensaEspecial, int velocidad, int generacion, boolean legendario, String habilidad) {
        this.nombre = nombre;
        this.numero = numero;
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
        this.total = total;
        this.hp = hp;
        this.ataque = ataque;
        this.defensa = defensa;
        this.ataqueEspecial = ataqueEspecial;
        this.defensaEspecial = defensaEspecial;
        this.velocidad = velocidad;
        this.generacion = generacion;
        this.legendario = legendario;
        this.habilidad = habilidad;
    }

    // Getters
    public String getNombre() { return nombre; }
    public int getNumero() { return numero; }
    public String getTipo1() { return tipo1; }
    public String getTipo2() { return tipo2; }
    public int getTotal() { return total; }
    public int getHp() { return hp; }
    public int getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }
    public int getAtaqueEspecial() { return ataqueEspecial; }
    public int getDefensaEspecial() { return defensaEspecial; }
    public int getVelocidad() { return velocidad; }
    public int getGeneracion() { return generacion; }
    public boolean isLegendario() { return legendario; }
    public String getHabilidad() { return habilidad; }

    @Override
    public String toString() {
        return "Pokémon: " + nombre +
                "\nNúmero: " + numero +
                "\nTipo 1: " + tipo1 +
                "\nTipo 2: " + tipo2 +
                "\nTotal: " + total +
                "\nHP: " + hp +
                "\nAtaque: " + ataque +
                "\nDefensa: " + defensa +
                "\nAtaque Especial: " + ataqueEspecial +
                "\nDefensa Especial: " + defensaEspecial +
                "\nVelocidad: " + velocidad +
                "\nGeneración: " + generacion +
                "\nLegendario: " + (legendario ? "Sí" : "No") +
                "\nHabilidad: " + habilidad;
    }
}