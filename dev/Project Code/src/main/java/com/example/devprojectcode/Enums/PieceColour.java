package com.example.devprojectcode.Enums;

public enum PieceColour {
    BLACK("b"),
    WHITE("w");


    private final String label;

    PieceColour(String label) {
        this.label = label;
    }

    public String getColourLabel() {
        return label;
    }
}
