package com.chrisluttazi.holidaysrecipes.model;

public enum Role {
    USER(1), CHEFF(2), SUPPLIER(3), MODERATOR(4), ADMIN(5), ADVERTISER(6), MANAGER(7);

    private int option;

    Role(int option) {
        if (option > 0 && option < 8)
            this.option = option;
    }

    Role() {
    }

    @Override
    public String toString() {
        switch (option) {
            case 1:
                return "USER";
            case 2:
                return "CHEFF";
            case 3:
                return "SUPPLIER";
            case 4:
                return "MODERATOR";
            case 5:
                return "ADMIN";
            case 6:
                return "ADVERTISER";
            case 7:
                return "MANAGER";
        }
        return "None";
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

}
