package ua.com.valexa.common.enums;

public enum StateMapping {

    DE("DELAWARE");

    private  String state;

    StateMapping(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public static StateMapping getByState(String state) {
        for (StateMapping item : values()) {
            if (item.getState().equals(state)) {
                return item;
            }
        }
        return null;
    }
}
