package eapli.base.warehousemanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;

import java.util.regex.Pattern;

import javax.persistence.*;

@Entity
public class Agv implements AggregateRoot<Long> {

  @Id
  private Long id;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private String model;

  @Column(unique = true, nullable = false)
  private String dockId;

  @Column(nullable = false)
  private Long autonomy;

  @Column(nullable = false)
  private AgvStatus status;

  public Agv(Long id, String description, String model, String dockId, Long autonomy, AgvStatus status) {

    Preconditions.noneNull(id, description, model, dockId, autonomy, status);
    Preconditions.isPositive(id);
    Preconditions.isPositive(autonomy);
    Pattern lenThirty = Pattern.compile("^.{0,30}$");
    Preconditions.matches(lenThirty, description.toString(), "Invalid description, over 30 characters");
    Pattern lenFifty = Pattern.compile("^.{0,50}$");
    Preconditions.matches(lenFifty, model.toString(), "Invalid model, over 50 characters");
    //this.isStatusValid(status);

    this.id = id;
    this.description = description;
    this.model = model;
    this.dockId = dockId;
    this.autonomy = autonomy;
    this.status = status;
  }

  protected Agv() { //For ORM
  }

  public String dock() {
    return this.dockId;
}

 /* public void isStatusValid(String status) {
    String[] array = { "charging", "idle", "working" };
    if (!Arrays.toString(array).contains(status))
      throw new IllegalArgumentException("Invalid AGV status");
  }*/

  public AgvStatus CurrentStatus(){ return status; }

  @Override
  public boolean sameAs(Object other) {
    if (!(other instanceof Agv)) {
      return false;
    }
    final Agv that = (Agv) other;

    return identity().equals(that.identity()) &&
        description.equals(that.description) &&
        model.equals(that.model) &&
        autonomy.equals(that.autonomy) &&
        dockId.equals(that.dockId) &&
        status.equals(that.status);
  }

  @Override
  public Long identity() {
    return id;
  }

  public AgvStatus status() {
    return status;
  }

  public Long batteryAutonomy(){return this.autonomy;}

  @Override
  public String toString() {
    return "Agv [autonomy=" + autonomy + ", description=" + description + ", dockId=" + dockId + ", id=" + id
        + ", model=" + model + ", status=" + status + "]";
  }

  public void changeAgvStatus(AgvStatus agvStatus){
    this.status = agvStatus;
  }


}
