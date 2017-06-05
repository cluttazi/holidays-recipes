package com.chrisluttazi.holidaysrecipes.model;

public enum Cuisine {
    MEDITERRANEAN(1), AFRICAN(2), ASIAN(3), SOUTHAMERICAN(4), AMERICAN(5);

    private int option;

    Cuisine(int option) {
        if (option > 0 && option < 5)
            this.option = option;
    }

    Cuisine() {
        this.option = 1;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    @Override
    public String toString() {
        switch (option) {
            case 1:
                return "MEDITERRANEAN";
            case 2:
                return "AFRICAN";
            case 3:
                return "ASIAN";
            case 4:
                return "SOUTHAMERICAN";
            case 5:
                return "AMERICAN";
        }
        return "None";
    }
}
