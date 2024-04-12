/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import adt.LinkedListInterface;
import java.util.Objects;

/**
 *
 * @author Zy
 */
public class Programme {
    private String id;
    private String name;
    private LinkedListInterface<Course> courses;
    
    public Programme() {
    }

    public Programme(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public LinkedListInterface<Course> getCourses() {
        return courses;
    }

    public void setCourses(LinkedListInterface<Course> course) {
        this.courses = course;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Programme other = (Programme) obj;
        return Objects.equals(this.name, other.name);
    }

}
