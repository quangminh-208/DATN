package io.github.pudo58.constant;

public enum CustomerConst {
    ADULT("Người lớn", "ADULT"),
    CHILDREN("Trẻ em", "CHILDREN"),
    BABY("Em bé", "BABY");
    public final String label;
    public final String value;

    CustomerConst(String label, String value) {
        this.label = label;
        this.value = value;
    }
}
