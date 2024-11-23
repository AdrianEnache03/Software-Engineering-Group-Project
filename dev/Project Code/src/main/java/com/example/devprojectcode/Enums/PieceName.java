package com.example.devprojectcode.Enums;

public enum PieceName {
    PAWN("p"),
    KNIGHT("kn"),
    BISHOP("b"),
    ROOK("r"),
    KING("k"),
    QUEEN("q");

    private final String label;

    PieceName(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
