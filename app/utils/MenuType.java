package utils;

public enum MenuType {
    MAP(1),
    STATION(2),
    TEST(3),
    SEARCH(4);

    private int type;

    MenuType(int type) {
        this.type = type;
    }
}
