package pro.sisit.model;


import pro.sisit.adapter.CSV_converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Author implements CSV_converter {

    private String name;
    private String birthPlace;

    public Author(String name, String birthPlace) {
        this.name = name;
        this.birthPlace = birthPlace;
    }

    public Author(){

    }

    public String getName() {
        return name;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Author author = (Author) o;
        return getName().equals(author.getName()) &&
                getBirthPlace().equals(author.getBirthPlace());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getBirthPlace());
    }

    @Override
    public String toString(){
        return name + "\t" + birthPlace;
    }

    @Override
    public List<String> fromFieldsToCSV() {
        List<String> fields = new ArrayList<>();
        fields.add(name);
        fields.add(birthPlace);
        return fields;
    }

    @Override
    public void fromCSVToFields(List<String> fromCSV) {
        name    = fromCSV.get(0);
        birthPlace  = fromCSV.get(1);

    }
}
