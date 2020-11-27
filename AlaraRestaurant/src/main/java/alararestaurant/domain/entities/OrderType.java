package alararestaurant.domain.entities;

public enum OrderType {
    ForHere, ToGo;


    public static OrderType valueOfLabel(String label) {
        for (OrderType e : values()) {
            if (e.name().equals(label)) {
                return e;
            }
        }
        return null;
    }
}
