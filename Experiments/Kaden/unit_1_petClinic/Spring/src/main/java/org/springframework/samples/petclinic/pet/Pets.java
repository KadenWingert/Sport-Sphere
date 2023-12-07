package org.springframework.samples.petclinic.pet;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(name = "pets")
public class Pets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @NotFound(action = NotFoundAction.IGNORE)
    int id;
    @Column
    @NotFound(action = NotFoundAction.IGNORE)
    String name;
    @Column(name = "animal_type")
    @NotFound(action = NotFoundAction.IGNORE)
    String animal;

    public Pets(){
    }
    public Pets(int id, String name, String animal){
        this.id = id;
        this.name = name;
        this.animal = animal;

    }

    
    /** 
     * @return int
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    @Override
    public String toString(){
        return "ID: " + this.getId() + " Name: " + this.getName() +
                " Animal Type: " + this.getAnimal();
    }
}
