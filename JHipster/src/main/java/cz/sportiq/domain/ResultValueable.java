package cz.sportiq.domain;

public interface ResultValueable {
    void setValue(Float value);
    Float getValue();

    void setCompareValue(Float value);
    Float getCompareValue();
}
