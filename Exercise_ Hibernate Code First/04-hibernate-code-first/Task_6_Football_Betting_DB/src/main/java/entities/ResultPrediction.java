package entities;

import entities.base.BaseEntity;
import jdk.jfr.Enabled;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "result_predictions")
public class ResultPrediction extends BaseEntity {
    private String predictions;

    public ResultPrediction() {
    }

    @Column(name = "prediction")
    public String getPredictions() {
        return predictions;
    }

    public void setPredictions(String predictions) {
        this.predictions = predictions;
    }
}
