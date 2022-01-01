package org.iproute.jvm.classloader;

/**
 * @author winterfell
 **/
public class MyPerson {

    private MyPerson myPerson;

    public void setMyPerson(Object object) {
        this.myPerson = (MyPerson) object;
    }
}
