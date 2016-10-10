package gui;

import javafx.beans.property.SimpleStringProperty;

import java.util.List;


public class Row {

    public SimpleStringProperty name = new SimpleStringProperty();
    public SimpleStringProperty type = new SimpleStringProperty();
    public SimpleStringProperty created = new SimpleStringProperty();
    public SimpleStringProperty createdBy = new SimpleStringProperty();
    public SimpleStringProperty lastUpdate = new SimpleStringProperty();
    public SimpleStringProperty lastUpdateBy = new SimpleStringProperty();

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public String getCreated() {
        return created.get();
    }

    public SimpleStringProperty createdProperty() {
        return created;
    }

    public void setCreated(String created) {
        this.created.set(created);
    }

    public String getCreatedBy() {
        return createdBy.get();
    }

    public SimpleStringProperty createdByProperty() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy.set(createdBy);
    }

    public String getLastUpdate() {
        return lastUpdate.get();
    }

    public SimpleStringProperty lastUpdateProperty() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate.set(lastUpdate);
    }

    public String getLastUpdateBy() {
        return lastUpdateBy.get();
    }

    public SimpleStringProperty lastUpdateByProperty() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy.set(lastUpdateBy);
    }

    public String getName() {
        return name.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }
    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public Row(){

    }
}
