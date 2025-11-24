package com.vityarthi.storage;

import com.vityarthi.models.Course;
import com.vityarthi.models.Student;
import com.vityarthi.models.Teacher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataStore implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DATA_DIR = "data";
    private static final String STORE_FILE = DATA_DIR + File.separator + "store.dat";

    public List<Student> students = new ArrayList<>();
    public List<Teacher> teachers = new ArrayList<>();
    public List<Course> courses = new ArrayList<>();

    private transient boolean dirty = false;

    public void save() {
        try {
            File dir = new File(DATA_DIR);
            if (!dir.exists()) dir.mkdirs();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STORE_FILE))) {
                oos.writeObject(this);
            }
            dirty = false;
            System.out.println("Data saved to " + STORE_FILE);
        } catch (Exception e) {
            System.out.println("Failed to save data: " + e.getMessage());
        }
    }

    public static DataStore load() {
        try {
            File f = new File(STORE_FILE);
            if (!f.exists()) return new DataStore();
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                Object obj = ois.readObject();
                if (obj instanceof DataStore) return (DataStore) obj;
            }
        } catch (Exception e) {
            System.out.println("Failed to load data: " + e.getMessage());
        }
        return new DataStore();
    }

    public boolean isDirty() { return dirty; }
    public void markDirty() { dirty = true; }
}
